<template>
    <div>
      <component
        v-for="(item, i) in pageItems"
        :key="'l-' + i"
        :is="PageComponentMap[item.type]"
        v-bind="item.componentProps"  />
    </div>
</template>

<script setup lang="ts">
import { ref,computed } from 'vue'
import { PageComponentMap} from '../ts/PageComponentMap'
import { PageComponent} from '../ts/PageComponent'

interface PageItemMeta {
  type: PageComponent; // "CARD", "INPUT" 등
  dataUrl: string | null;  // 데이터를 가져올 API 주소
}
const props = defineProps<{
  items: PageItemMeta[] 
}>();

const pageItems = computed(() => {
  return props.items.map(item => {
    return {
      ...item,
      componentProps: {
        dataUrl: item.dataUrl
      }
    }
  })
})

const searchQuery = ref('')

</script>

<style scoped>


</style>