<template>
   <TopNavBarRenderer :items=topNavMeta></TopNavBarRenderer>
   <PageRenderer :items="pageMeta"></PageRenderer>
</template>

<script setup lang="ts">
import { ref, watch,onMounted } from 'vue'
import { useRoute } from 'vue-router'
import {commonGet} from '@/utils/ShareBuyUtil';
import TopNavBarRenderer from '@/components/TopNavBarRenderer.vue';
import { PathToMenuIdMap } from '@/constants/MenuMap'
import PageRenderer from '@/components/PageRenderer.vue';
const route = useRoute();
const currentPath = route.path;
const menuId = PathToMenuIdMap[currentPath] 

const topNavMeta = ref([]);
const pageMeta = ref([]);
const permissionMeta = ref([]);

onMounted(() => {
  const latNum = route.query.lat ? Number(route.query.lat) : null;
  const lngNum = route.query.lng ? Number(route.query.lng) : null;
  fetchData(latNum, lngNum);
});

async function fetchData(lat:Number,lng:Number){
  const res = await commonGet(`/page/${menuId}`,{lat,lng});
  if(res){
  topNavMeta.value = res.topNavMeta.topNavItemMetaList; 
  pageMeta.value = res.pageMeta.pageItemMetaList;
  permissionMeta.value = res.permissionMeta.permissionItemMetaList;
  }
}

// watch(() => route.query, (newQuery) => {
//   console.log(route.query);
//   //현재 위치 정보가 없으면 내 현위치 기반으로 위치 호출 
//   if (newQuery.lat && newQuery.lng) {
//     const latNum = Number(newQuery.lat);
//     const lngNum = Number(newQuery.lng);

//     // 유효한 숫자인지 확인 후 호출
//     if (!isNaN(latNum) && !isNaN(lngNum)) {
//       fetchData(latNum, lngNum);
//     }
//   }
// }, { immediate: true, deep: true });

</script>