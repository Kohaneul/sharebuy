<template>
  <div class="input-wrapper">
    <label class="input-label">{{ title }}</label>
    <input
      type="text"
      v-model="inputValue"
      class="input-box"
      @input="onInput"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const props = defineProps({
  title: { type: String, default: '' },
  modelValue: { type: String, default: '' }
})

const emit = defineEmits(['update:modelValue'])

const inputValue = ref(props.modelValue)

function onInput() {
  emit('update:modelValue', inputValue.value)
}
</script>

<style scoped>
.input-wrapper {
  display: flex;
  flex-direction: column;
  margin-bottom: 16px;
}

.input-label {
  font-size: 14px;
  margin-bottom: 6px;
  font-weight: 500;
}

.input-box {
  padding: 8px 10px;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  font-size: 14px;
}

.input-box:focus {
  border-color: #40a9ff;
  outline: none;
}
</style>