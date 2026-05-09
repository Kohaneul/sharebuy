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
import {computed} from 'vue';
import TopNavBarRenderer from '@/components/TopNavBarRenderer.vue';
import { RoleType } from '@/ts/UserType';
import { TopNavItemMeta } from '@/ts/TopNavItemMeta';
import { useUiStore } from '@/store/ui';
const uiStore = useUiStore();

const props = defineProps<{
  topNavMeta?: TopNavItemMeta[];
  roleType?: RoleType;
  latitude?: number;
  longitude?: number;
}>();

const displayNavMeta = computed(() => {
  // 1. props가 넘어오면 최우선 적용
  if (props.topNavMeta && props.topNavMeta.length > 0) {
    return props.topNavMeta;
  }
  // 2. 없으면 파라미터 없이 getDefaultNav 호출!
  return uiStore.getDefaultNav;
});


</script>

<style scoped>
.page-wrapper { display: flex; flex-direction: column; min-height: 100vh; }
.page-content { flex: 1; position: relative; }
.default-top-nav { height: 56px; display: flex; align-items: center; padding: 0 16px; border-bottom: 1px solid #eee; }
</style>