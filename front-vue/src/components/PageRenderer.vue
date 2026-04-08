<template>
  <div class="page-wrapper">
      <component
      v-for="(item, i) in items"
      :key="i"
      :is="PageComponentMap[item.type]"
      v-bind="item" 
    />
  </div>
</template>

<script setup lang="ts">
import { PageComponent,ActionType } from '@/ts/PageComponent';
import { PageComponentMap } from '../ts/PageComponentMap'

const props = defineProps<{
  items: {
    type: PageComponent;
    title: string | null;
    actionType:ActionType;
    dataUrl: string | null;
  }[]
}>();
</script>

<style scoped>
.page-wrapper {
  background-color: #ececec; 
}
</style>