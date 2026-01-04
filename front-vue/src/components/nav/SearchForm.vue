<template>
  <a-input
    v-model:value="searchQuery"
    placeholder="상품명, 공동구매명, 카테고리 등을 검색하세요"
    @input="onInput"
    @pressEnter="onEnter"
  >
    <template #prefix>
      <SearchOutlined />
    </template>
  </a-input>
</template>

<script setup lang="ts">
import { ref, onBeforeUnmount } from 'vue'
import { SearchOutlined } from '@ant-design/icons-vue'
import debounce from 'lodash/debounce'

const searchQuery = ref('')

const emit = defineEmits<{
  (e: 'search', value: string): void
}>()

const emitDebounced = debounce((value: string) => {
  emit('search', value)
}, 300)

function onInput(e: Event) {
  const value = (e.target as HTMLInputElement).value.trim()
  emitDebounced(value)
}

function onEnter() {
  emitDebounced.cancel()   // 🔥 중요
  emit('search', searchQuery.value.trim())
}

onBeforeUnmount(() => {
  emitDebounced.cancel()
})
</script>
