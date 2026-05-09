<template>
  <div class="page-wrapper">
    <!-- 1. 모든 아이템을 sortOrder 순서대로 렌더링 -->
    <div 
      v-for="(item, i) in items" 
      :key="'item-' + i" 
      :class="['section-row', item.position?.toLowerCase() || 'left']"
    >
      <component
        :is="PageComponentMap[item.type]"
        v-bind="item"
        :latitude="latitude"
        :longitude="longitude"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { PageComponent, ActionType } from '@/ts/PageComponent';
import { PageComponentMap } from '../ts/PageComponentMap';

const props = defineProps<{
  items: {
    type: PageComponent;
    position: 'LEFT' | 'CENTER' | 'RIGHT'
    title: string | null;
    actionType: ActionType;
    routeUrl: string | null;
    dataSourceUrl:string | null;
    mappingKey:string|null;
  }[];
  latitude?: number | null;
  longitude?: number | null;
}>();

// 버튼들만 필터링
const buttons = computed(() => props.items.filter(item => item.type === 'BUTTON'));

</script>

<style scoped>
.page-wrapper {
  display: flex;
  flex-direction: column;
  gap: 16px; /* 간격을 조금 더 넓히면 시원해 보입니다 */
  padding: 12px; /* 좌우 여백 확보 */
  background-color: #ececec;
  min-height: 100vh;
}
/* 🌟 각 컴포넌트를 감싸는 줄(Row) */
.section-row {
  display: flex;
  width: 100%;
}

/* 🌟 서버에서 내려준 position 값에 따라 정렬 결정 */
.section-row.left { justify-content: flex-start; }
.section-row.center { justify-content: center; }
.section-row.right { justify-content: flex-end; }

/* 버튼 같은 작은 컴포넌트들이 가로 전체를 차지하지 않게 설정 */
.section-row > * {
  flex: 1; /* 자식이 가능한 너비를 다 차지하게 함 */
  width: 100%; 
  max-width: 100%;
}
.section-row.center > .ant-btn {
  flex: none; /* 버튼은 꽉 차지 않게 */
}
</style>