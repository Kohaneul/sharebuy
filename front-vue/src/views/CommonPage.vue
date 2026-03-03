<template>
   <TopNavBarRenderer :items=topNavMeta></TopNavBarRenderer>
   <PageRenderer :items="pageMeta"></PageRenderer>
</template>

<script setup lang="ts">
import { ref, onMounted,watch } from 'vue'
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

async function fetchData(lat:Number,lng:Number){
  const res = await commonGet(`/rest/page/${menuId}`,{lat,lng});
  if(res){
  topNavMeta.value = res.topNavMeta.topNavItemMetaList; 
  pageMeta.value = res.pageMeta.pageItemMetaList;
  permissionMeta.value = res.permissionMeta.permissionItemMetaList;
  }
}

watch(() => route.query, (newQuery) => {
  if (newQuery.lat && newQuery.lng) {
    const latNum = Number(newQuery.lat);
    const lngNum = Number(newQuery.lng);

    // 유효한 숫자인지 확인 후 호출
    if (!isNaN(latNum) && !isNaN(lngNum)) {
      fetchData(latNum, lngNum);
    }
  }
}, { immediate: true, deep: true });

</script>