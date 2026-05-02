<template>
   <TopNavBarRenderer :items=topNavMeta :roleType="roleTypeRef"></TopNavBarRenderer>
   <PageRenderer :items="pageMeta"></PageRenderer>
</template>

<script setup lang="ts">
import { ref, onMounted,computed,watch } from 'vue'
import { useRoute } from 'vue-router'
import {commonGet} from '@/utils/ShareBuyUtil';
import TopNavBarRenderer from '@/components/TopNavBarRenderer.vue';
import { PathToPageIdMap } from '@/constants/PageMap'
import PageRenderer from '@/components/PageRenderer.vue';
import { RoleType,ROLES } from '@/ts/UserType';
import { useUserStore } from '@/store/user';

const userStore = useUserStore();
const route = useRoute();
const currentPath = route.path;

const pageId = computed(() => {
  return PathToPageIdMap[route.path]
});

const topNavMeta = ref([]);
const pageMeta = ref([]);
const permissionMeta = ref([]);
const roleTypeRef = ref<RoleType>(ROLES.GUEST);


async function fetchData(param:any){
  const res = await commonGet(`/page/${pageId.value}`,param);
  if(res){
  topNavMeta.value = res.topNavMeta.topNavItemMetaList; 
  pageMeta.value = res.pageMeta.pageItemMetaList;
  permissionMeta.value = res.permissionMeta.permissionItemMetaList;
  roleTypeRef.value =  res.permissionMeta.roleType;
  userStore.setAuthority(res.permissionMeta);
  }
}

watch(
  () => route.fullPath,
  () => {
    fetchData(route.query);
  },
  { immediate: true }
)

</script>