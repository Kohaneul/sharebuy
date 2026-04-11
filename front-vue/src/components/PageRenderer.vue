<template>
  <div class="page-wrapper">
    <div v-if="buttons.length > 0" class="button-group">
      <component
        v-for="(item, i) in buttons"
        :key="'btn-' + i"
        :is="PageComponentMap[item.type]"
        v-bind="item"
      />
    </div>

    <div class="content-body">
      <component
        v-for="(item, i) in nonButtons"
        :key="'comp-' + i"
        :is="PageComponentMap[item.type]"
        v-bind="item"
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
    title: string | null;
    actionType: ActionType;
    dataUrl: string | null;
  }[]
}>();

// 버튼들만 필터링
const buttons = computed(() => props.items.filter(item => item.type === 'BUTTON'));

// 버튼이 아닌 것들만 필터링
const nonButtons = computed(() => props.items.filter(item => item.type !== 'BUTTON'));
</script>

<style scoped>
.page-wrapper {
  background-color: #ececec;
  padding: 10px; 
}

/* 🌟 버튼들을 가로로 정렬해주는 핵심 스타일 */
.button-group {
  display: flex;
  gap: 5px;           /* 버튼 사이 간격 */
  margin-bottom: 10px; /* 카드 리스트와의 간격 */
  justify-content: flex-end; /* 왼쪽 정렬 */
}

/* 버튼이 너무 찰싹 붙지 않게 함 */
.button-group :deep(.ant-btn) {
  height: 40px;
  padding: 0 20px;
  border-radius: 6px;
}

.content-body {
  display: flex;
  flex-direction: column;
  gap: 20px; /* 컴포넌트 간 수직 간격 */
}
</style>