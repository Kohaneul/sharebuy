<template>
  <div class="mapping-container">
  <div v-for="conf in mappingList" :key="conf.field" :class="['input-wrapper', conf.type.toLowerCase()]">
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

보내주신 이미지를 다시 보면, 상태(STATUS) 항목이 너무 작게 잡히면서 옆에 있는 제목과 장소가 그 옆 빈공간을 채우려고 다닥다닥 붙어버린 게 문제였네요.

지금 코드에서 flex: 1 1 300px 설정 때문에, 화면 너비가 넓으면 한 줄에 다 구겨 넣으려고 할 거예요. 특히 "상태"는 배지만 있어서 너비가 아주 좁은데, 나머지 애들이 그 옆을 억지로 비집고 들어가는 거죠.

조금 더 정돈된 느낌을 주려면 "상태"는 한 줄을 다 쓰게 하거나, "제목과 장소"의 최소 너비를 키우는 게 좋습니다.

🛠️ 추천 스타일 수정
가장 깔끔하게 배치되는 CSS 가이드입니다.

CSS
<style scoped>
.mapping-container {
  display: flex;
  flex-wrap: wrap; 
  gap: 24px;       /* 간격을 조금 더 넓혀서 가독성 확보 */
  width: 100%;
}

.input-wrapper {
  display: flex;
  flex-direction: column;
  /* 핵심: 최소 너비를 넉넉히 주거나 상황에 맞게 조절 */
  flex: 1 1 calc(33.333% - 24px); 
  min-width: 250px; 
}

/* 1. 상태 배지: 텍스트가 짧아도 한 줄을 차지하게 하거나, 
      아예 제목 옆에 붙지 않게 너비를 조절 */
.input-wrapper.status {
  flex: 1 1 100%; /* 상태를 맨 위 한 줄 전체로 빼버리는 게 깔끔할 수 있습니다 */
  margin-bottom: -10px; /* 라벨과 배지 사이 간격이 너무 멀면 조절 */
}

/* 2. 텍스트 영역(상세 내용)은 무조건 한 줄 전체 */
.input-wrapper.textarea {
  flex: 1 1 100%;
}

/* 3. 제목(INPUT)과 장소(INPUT)가 적절히 반반씩 나눠 갖게 함 */
.input-wrapper.input {
  flex: 1 1 calc(45%); 
}

.input-label {
  margin-bottom: 8px;
  font-weight: 600;
  color: #333;
  font-size: 14px;
}

/* Ant Design 박스 스타일 */
:deep(.ant-input), :deep(.ant-input-affix-wrapper) {
  padding: 8px 12px;
  border-color: #d9d9d9;
}

/* 읽기 전용일 때 배경색 강제 (이미지처럼 회색 느낌 주려면) */
:deep(.ant-input[disabled]), :deep(.ant-textarea[disabled]) {
  background-color: #f5f5f5 !important;
  color: #555 !important;
}
</style>