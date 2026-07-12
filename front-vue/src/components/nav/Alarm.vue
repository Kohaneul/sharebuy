<template>
  <a-badge :dot="hasNotification" :offset="[-2, 5]">
    <Button type="text" @click="onNotificationClick">
      <BellOutlined class="nav-icon"/>
    </Button>
  </a-badge>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { Button, Badge as ABadge } from 'ant-design-vue'; // Ant Design 컴포넌트 명시적 임포트
import { BellOutlined } from '@ant-design/icons-vue'

// 상단바 메타데이터에서 내려오는 value를 받습니다.
const props = defineProps({
  value: { type:Boolean, default: false }
})

const emit = defineEmits(['notificationClick'])

// Dot을 보여줄지 결정하는 계산된 속성
const hasNotification = computed(() => {
    return props.value ;
});

function onNotificationClick() { 
  emit('notificationClick') 
}
</script>

<style scoped>
.nav-icon {
  font-size: 22px;   /* 점 방식은 아이콘이 조금 더 커도 예쁩니다 */
  color: #fff;
  transition: color 0.3s;
}

.nav-icon:hover {
  color: #40a9ff;
}

/* Ant Design Badge 점 색상 커스텀이 필요하다면 사용하세요 */
:deep(.ant-badge-dot) {
  background-color: #ff4d4f; /* 쨍한 레드 */
  box-shadow: 0 0 0 1px #223344; /* 배경색과 맞춘 보더 (선택사항) */
}
</style>