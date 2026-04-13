<template>
   <TopNavBarRenderer :items=topNavMeta :roleType="roleTypeRef"></TopNavBarRenderer>
   <PageRenderer :items="pageMeta"></PageRenderer>
</template>

<script setup lang="ts">
import { ref, onMounted,computed,watch } from 'vue'
import { useRoute } from 'vue-router'
import {commonGet} from '@/utils/ShareBuyUtil';
import TopNavBarRenderer from '@/components/TopNavBarRenderer.vue';
import { PathToMenuIdMap } from '@/constants/MenuMap'
import PageRenderer from '@/components/PageRenderer.vue';
import { RoleType,ROLES } from '@/ts/UserType';
import { useUserStore } from '@/store/user';

const userStore = useUserStore();
const route = useRoute();
const currentPath = route.path;

const menuId = computed(() => {
  return PathToMenuIdMap[route.path]
});

const topNavMeta = ref([]);
const pageMeta = ref([]);
const permissionMeta = ref([]);
const roleTypeRef = ref<RoleType>(ROLES.GUEST);

onMounted(() => {

  const latNum = route.query.lat ? Number(route.query.lat) : null;
  const lngNum = route.query.lng ? Number(route.query.lng) : null;

  fetchData(latNum, lngNum);
});

async function fetchData(lat:Number,lng:Number){
  console.log("menu_id = "+menuId);
  const res = await commonGet(`/page/${menuId.value}`,{lat,lng});
  if(res){
  topNavMeta.value = res.topNavMeta.topNavItemMetaList; 
  pageMeta.value = res.pageMeta.pageItemMetaList;
  permissionMeta.value = res.permissionMeta.permissionItemMetaList;
  roleTypeRef.value =  res.permissionMeta.roleType;
  userStore.setAuthority(res.permissionMeta);
  }
}

watch(
  () => route.path,
  () => {
    fetchData(
      route.query.lat ? Number(route.query.lat) : null,
      route.query.lng ? Number(route.query.lng) : null
    )
  },
  { immediate: true }
)

</script>