<template>
  <!-- <TopNavBar></TopNavBar> -->
   <TopNavBarRenderer :items=topNavMeta></TopNavBarRenderer>
  <div>
    <pre></pre>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

import {commonGet} from '@/utils/ShareBuyUtil';
import TopNavBarRenderer from '@/components/TopNavBarRenderer.vue';
import { PathToMenuIdMap } from '@/constants/MenuMap'

const route = useRoute()
const currentPath = route.path

const menuId = PathToMenuIdMap[currentPath] // path → UUID

const topNavMeta = ref([]);
const pageMeta = ref([]);
const permissionMeta = ref([]);

onMounted(async () => {
const res = await commonGet(`/rest/page/${menuId}`);
console.log(res);
if(res){
topNavMeta.value = res.top_nav_meta; 
pageMeta.value = res.page_meta;
permissionMeta.value = res.permission_meta;
}
})
</script>