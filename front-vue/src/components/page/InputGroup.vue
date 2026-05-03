<template>
  <div v-for="conf in mappingList" :key="conf.field" class="input-wrapper">
    <label class="input-label">{{ conf.label || title }}</label>
    
    <!-- 1. 상세 내용 (TEXTAREA) -->
   <a-textarea
    v-if="conf.type === 'TEXTAREA'"
    v-model:value="value[toCamelCase(conf.field)]"
    :read-only="conf.readOnly"
    :disabled="conf.readOnly"
    :rows="4" 
    placeholder="내용을 입력해주세요"
    class="input-box"
    :auto-size="{ minRows: 4, maxRows: 10 }"
  />
    <!-- 2. 상태 (STATUS) -->
    <div v-else-if="conf.type === 'STATUS'" class="status-badge-container">
       <a-tag color="blue">{{ formatStatus(value[toCamelCase(conf.field)]) }}</a-tag>
    </div>

    <!-- 3. 일반 입력 (INPUT, NUMBER 등) -->
    <a-input
      v-else
      :type="conf.type === 'NUMBER' ? 'number' : 'text'"
      v-model:value="value[toCamelCase(conf.field)]"
      :read-only="conf.readOnly"
      class="input-box"
      :disabled="conf.readOnly"
    />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

// interface 정의 (타입 안정성)
interface MappingConfig {
  field: string;
  type: string;
  label: string;
  readOnly: boolean;
}

const props = defineProps<{
  title?: string;
  mappingKey: string | null;
  value: any; 
}>()

const mappingList = computed<MappingConfig[]>(() => {
  if (!props.mappingKey) return [];
  try {
    return JSON.parse(props.mappingKey);
  } catch (e) {
    console.error("JSON 파싱 에러:", e);
    return [];
  }
});

const toCamelCase = (str: string) => {
  return str.replace(/([-_][a-z])/g, (group) =>
    group.toUpperCase().replace('-', '').replace('_', '')
  );
};

const formatStatus = (status: string) => {
  const statusMap: Record<string, string> = {
    'RECRUITING': '모집 중',
    'COMPLETED': '모집 완료',
    'EXPIRED': '기간 만료'
  };
  return statusMap[status] || status;
};
</script>
<style scoped>
/* 1. 일반 input (a-input) 흰색 배경 강제 적용 */
:deep(.ant-input-disabled),
:deep(.ant-input[disabled]),
:deep(.ant-input[readonly]) {
  background-color: #ffffff !important; /* 흰색 배경 */
  color: #333333 !important;           /* 글자색 (너무 연하면 안보이니까) */
  cursor: default !important;           /* 금지 표시 대신 기본 커서 */
  border-color: #d9d9d9 !important;     /* 테두리 색상 유지 */
}

/* 2. textarea (a-textarea) 흰색 배경 강제 적용 */
:deep(.ant-textarea-disabled),
:deep(.ant-textarea[disabled]),
:deep(.ant-textarea[readonly]) {
  background-color: #ffffff !important;
  color: #333333 !important;
  cursor: default !important;
  border-color: #d9d9d9 !important;
}

/* 3. 포커스 시 파란색 테두리 생기는 거 방지 (선택 사항) */
:deep(.ant-input-disabled:focus),
:deep(.ant-input[readonly]:focus) {
  border-color: #d9d9d9 !important;
  box-shadow: none !important;
}

/* 기존에 만든 스타일이 있다면 아래에 추가... */
.input-wrapper {
  /* margin-bottom: 10px; */
  display: flex;
  flex-direction: column;
}
</style>