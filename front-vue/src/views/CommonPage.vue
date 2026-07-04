<template>
   <PageWrapper
    :topNavMeta="topNavMeta"
    :roleType="roleTypeRef"
    :latitude="latitudeRef"  
    :longitude="longitudeRef"
    >
   <PageRenderer 
   :items="pageMeta"
   :latitude="latitudeRef" 
   :longitude="longitudeRef">
  </PageRenderer>
   </PageWrapper>
</template>

<script setup lang="ts">
import { ref, computed,watch } from 'vue'
import { useRoute } from 'vue-router'
import {commonGet} from '@/utils/ShareBuyUtil';
import PageWrapper from './PageWrapper.vue';
import { PathToPageIdMap } from '@/constants/PageMap'
import PageRenderer from '@/components/PageRenderer.vue';
import { RoleType,ROLES } from '@/ts/UserType';
import { useUserStore } from '@/store/user';
import { useLocationStore } from '@/store/location';
import { useUiStore } from '@/store/ui';

/**
 * store
 */
const userStore = useUserStore();
const locationStore = useLocationStore();
const route = useRoute();
const uiStore = useUiStore();

const pageId = computed(() => {
  return PathToPageIdMap[route.path]
});

const topNavMeta = ref([]);
const pageMeta = ref([]);
const permissionMeta = ref([]);
const roleTypeRef = ref<RoleType>(ROLES.GUEST);

const latitudeRef = ref<number>();
const longitudeRef = ref<number>();


async function fetchData(param: any) {

  // 1. 서버 호출 (캐시가 없거나, 로그인 사용자일 때)
  const res = await commonGet(`/page/${pageId.value}`, param);  
  
  if (res) {
    const navData = res.topNavMeta.topNavItemMetaList || [];
    topNavMeta.value = navData;
    
    // 어떤 유저든 서버에서 받은 최신 상단바를 '전역'으로 설정
    uiStore.setGlobalNav(navData);

    // GUEST라면 다음에 또 쓸 수 있게 경로별 캐시에도 저장
    if (userStore.roleType === ROLES.GUEST) {
      uiStore.setNavMeta(route.path, navData);
    }

    // 2. 나머지 페이지 메타 데이터 셋팅
    pageMeta.value = res.pageMeta.pageItemMetaList;
    permissionMeta.value = res.permissionMeta.permissionItemMetaList;
    roleTypeRef.value = res.permissionMeta.roleType;
    userStore.setAuthority(res.permissionMeta);
  }
}


watch(
  () => route.fullPath,
  async (newPath, oldPath) => {
    if (oldPath && newPath.split('?')[0] === oldPath.split('?')[0]) {
       // 위경도만 바뀐 경우에 대한 처리만 따로 하거나 무시
       return;
    }
    // 1. 우선순위 결정: 스토어(로그인 정보) > 쿼리(GUEST 혹은 이동 시)
    // userStore에 latitude가 저장되어 있다면 그걸 먼저 씁니다.
    let lat = userStore.latitude || (route.query.latitude ? parseFloat(route.query.latitude as string) : undefined);
    let lng = userStore.longitude || (route.query.longitude ? parseFloat(route.query.longitude as string) : undefined);

  if (!lat || !lng) {
        // 방법 A: 다시 위치 권한을 물어본다 (locationStore 활용)
        const coords = await locationStore.syncLocation(true); 
        lat = coords.latitude;
        lng = coords.longitude;
        
        // 스토어에도 저장해줘서 다음 이동 시 편하게 만듭니다.
        userStore.setUserInfo({ latitude: lat, longitude: lng });
    }

    // 2. Ref 업데이트 (자식 컴포넌트들에게 전달됨)
    latitudeRef.value = lat;
    longitudeRef.value = lng;

    // 3. API 호출 파라미터 구성
    const params = {
      ...route.query, // 기존 쿼리 유지 (필요 시)
      latitude: lat,
      longitude: lng
    };

    await fetchData(params);
  },
  { immediate: true }
)

</script>