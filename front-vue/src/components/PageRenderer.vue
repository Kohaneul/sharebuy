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

// 버튼이 아닌 것들만 필터링
const nonButtons = computed(() => props.items.filter(item => item.type !== 'BUTTON'));

const getButtonsByPos = (pos: 'LEFT' | 'CENTER' | 'RIGHT') => {
  return buttons.value.filter(item => item.position === pos);
};

</script>

<style scoped>
.page-wrapper {
  display: flex;
  flex-direction: column;
  gap: 12px; /* 섹션 간의 간격 */
  padding: 10px;
  background-color: #ececec;
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
  max-width: 100%;
}
</style>