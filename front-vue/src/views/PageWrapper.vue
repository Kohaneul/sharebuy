<template>
  <div class="page-wrapper">
    <!-- 2. v-if 조건이 맞는지 확인 (데이터가 빈 배열([])이면 안 나올 수 있음) -->
    <TopNavBarRenderer 
      v-if="displayNavMeta && displayNavMeta.length > 0"
      :items="displayNavMeta"
      :roleType="roleType"
      :latitude="latitude"
      :longitude="longitude"
    />
    <!-- 데이터가 없을 때를 대비한 디폴트 바가 작동하는지 확인 -->
    <header v-else class="fallback-nav">ShareBuy</header>
    
    <slot></slot>
  </div>
</template>

<script setup lang="ts">
import {computed,onMounted} from 'vue';
import TopNavBarRenderer from '@/components/TopNavBarRenderer.vue';
import { RoleType } from '@/ts/UserType';
import { TopNavItemMeta } from '@/ts/TopNavItemMeta';
import { useUiStore } from '@/store/ui';
import { commonGet } from '@/utils/ShareBuyUtil';
const uiStore = useUiStore();

const props = defineProps<{
  topNavMeta?: TopNavItemMeta[];
  roleType?: RoleType;
  latitude?: number;
  longitude?: number;
}>();

const displayNavMeta = computed(() => {
  // 1순위: 부모(CommonPage 등)가 직접 Props로 내려준 데이터 (가장 정확함)
  if (props.topNavMeta && props.topNavMeta.length > 0) {
    return props.topNavMeta;
  }

  // 2순위: 스토어에 저장된 Global 데이터 (로그인 후 위치 정보 등이 반영된 데이터)
  // 직접 만든 페이지들은 보통 여기서 걸러집니다.
  if (uiStore.globalTopNav && uiStore.globalTopNav.length > 0) {
    return uiStore.globalTopNav;
  }
  // 3순위: 이도 저도 없으면 시스템 초기값 (위치 파악 중...)
  return uiStore.initialTopNav;
});

onMounted(async () => {
  // 다른 페이지로 넘어가서 PageWrapper가 새로 마운트되더라도,
  // 이미 uiStore에 데이터가 있다면 아래 코드를 실행하지 않고 바로 리턴합니다.
  if (uiStore.globalTopNav && uiStore.globalTopNav.length > 0) {
    return;
  }

  // 데이터가 없을 때만 백엔드 호출
  try{
    const topNav =  await commonGet(`/page/default/top_nav`);
    uiStore.setGlobalNav(topNav.topNavItemMetaList);
  }
  catch(Error){
    console.log(Error);
  }

});


</script>

<style scoped>
.page-wrapper { display: flex; flex-direction: column; min-height: 100vh; }
.page-content { flex: 1; position: relative; }
.default-top-nav { height: 56px; display: flex; align-items: center; padding: 0 16px; border-bottom: 1px solid #eee; }
</style>