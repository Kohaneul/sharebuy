<template>
  <div class="stats-card">
    <a-row :gutter="16">
      <a-col :span="12">
        <a-statistic
          title="현재 참여"
          :value="counts.current"
          suffix="명"
          :value-style="{ color: isJoinable ? '#1890ff' : '#8c8c8c' }"
        >
          <template #prefix><user-outlined /></template>
        </a-statistic>
      </a-col>

      <a-col :span="12">
        <a-statistic
          title="남은 자리"
          :value="remainingSeats"
          suffix="석"
          :value-style="{ color: statusColor }"
        >
          <template #prefix><team-outlined /></template>
        </a-statistic>
      </a-col>
    </a-row>

    <div class="stats-footer" :class="{ 'is-closed': !isJoinable }">
      <span v-if="currentStatus === 'RECRUITING' && !isFull"> 🚀 ({{ remainingSeats }}명 남음)</span>
      <span v-else-if="currentStatus === 'RECRUITING' && isFull">🈵 정원이 가득 찼습니다.</span>
      <span v-else-if="currentStatus === 'CLOSED'">🔒 모집이 마감되었습니다.</span>
      <span v-else-if="currentStatus === 'CANCELED'">🚫 취소된 게시글입니다.</span>
      <span v-else-if="currentStatus === 'EDITING'">⚙️ 정보를 수정 중입니다.</span>
      <span v-else>입장이 불가능한 상태입니다.</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { UserOutlined, TeamOutlined } from '@ant-design/icons-vue';

const props = defineProps<{
  jsonConfig?: string; 
  value: any; 
}>();

// 1. 숫자 데이터 파싱
const counts = computed(() => {
  try {
    const config = props.jsonConfig ? JSON.parse(props.jsonConfig) : {};
    const current = props.value?.[config.currentField] || 0;
    const max = props.value?.[config.maxField] || 0;
    return { current, max };
  } catch (e) {
    return { current: 0, max: 0 };
  }
});

// 2. 상태값 파싱 (RECRUITING, CLOSED, CANCELED, EDITING)
const currentStatus = computed(() => props.value?.status || 'RECRUITING');

// 3. 상태 판별 로직
const isFull = computed(() => counts.value.current >= counts.value.max && counts.value.max > 0);
// 오직 RECRUITING이면서 자리가 있을 때만 '참여 가능'으로 판단
const isJoinable = computed(() => currentStatus.value === 'RECRUITING' && !isFull.value);

const remainingSeats = computed(() => {
  const diff = counts.value.max - counts.value.current;
  return diff > 0 ? diff : 0;
});

// 4. 색상 가이드
const statusColor = computed(() => {
  if (currentStatus.value === 'RECRUITING') {
    return isFull.value ? '#f5222d' : '#52c41a'; // 가득 차면 빨강, 아니면 초록
  }
  return '#8c8c8c'; // CLOSED, CANCELED, EDITING은 모두 회색 처리
});
</script>

<style scoped>
.stats-card {
  background: #ffffff;
  padding: 20px;
  border-radius: 12px;
  border: 1px solid #f0f0f0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  margin-bottom: 16px;
}

.stats-footer {
  margin-top: 15px;
  padding-top: 10px;
  border-top: 1px dashed #f0f0f0;
  font-size: 13px;
  text-align: center;
  font-weight: 500;
  color: #1890ff; /* 기본 파란색 */
}

.stats-footer.is-closed {
  color: #f5222d; /* 마감/취소 등은 빨간색 계열 */
  font-weight: bold;
}
</style>