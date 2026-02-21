<template>
<div>
      <a-button type="text" @click="onLogoClick" >
      <img 
          src="/location.png"
          alt="버튼 이미지" 
           class="w-5 h-5 mr-1"
        />
        <span style="color:white; padding-left:5px;"class="w-5 h-5 mr-1">{{ displayLocation }}</span>
      </a-button>
    </div>


</template>

<script setup lang="ts">
import { ref,computed } from 'vue'


const {value} = defineProps<{
  value:string;
  menuId?: string;
}>();

const displayLocation = computed(() => {
  return value || '위치 확인 중...';
});
const loading = ref(false)
const error = ref<string | null>(null)


const emit = defineEmits(['logoClick'])
function onLogoClick() { emit('logoClick') }


</script>

<style scoped>
.top-nav-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0;
  height: 56px;
  background-color: #1f3b57; /* 이미지 기준 네이비 */
  color: #fff;
}

.left, .center, .right {
  display: flex;
  align-items: center;
}

.left .location {
  margin-left: 8px;
  font-weight: 500;
}

.center {
  flex: 1;
  margin: 0 16px;
}

.center .a-input {
  width: 100%;
  border-radius: 20px;
}

.right > * {
  margin-left: 12px;
}

.button-image {
  max-width: 150%; 
  max-height: 150%;
  padding:0;
  margin-top:-10px;
}
</style>
