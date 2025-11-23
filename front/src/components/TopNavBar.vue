<template>
  <a-layout-header class="top-nav-bar">
    <!-- 왼쪽: 로고 + 위치 -->
    <div class="left">
      <div>
      <a-button type="text" @click="onLogoClick" >
        <img 
          src="/main-logo.png"
          alt="버튼 이미지" 
          class="button-image" 
        />
        </a-button>
      </div>

      <LocationInfo ></LocationInfo>
    
    </div>

    <!-- 가운데: 검색창 -->
    <div class="center">
      <a-input
        v-model="searchQuery"
        placeholder="상품명, 공동구매명, 카테고리 등을 검색하세요"
        prefix-icon="search"
        @pressEnter="onSearch"
      />
    </div>

    <!-- 오른쪽: 알림 + 메뉴 -->
    <div class="right">
      <a-badge :count="notificationCount">
        <a-button type="text" icon="bell" @click="onNotificationClick" />
      </a-badge>
      <a-button type="text" icon="menu" @click="onMenuClick" />
    </div>
  </a-layout-header>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits } from 'vue'
import { Button } from 'ant-design-vue';
import LocationInfo from './LocationInfo.vue';

const props = defineProps({
  location: { type: String, default: '서울시 강남구' },
  notificationCount: { type: Number, default: 0 }
})

const emit = defineEmits(['logoClick', 'search', 'notificationClick', 'menuClick'])

const searchQuery = ref('')

function onLogoClick() { emit('logoClick') }
function onSearch() { emit('search', searchQuery.value) }
function onNotificationClick() { emit('notificationClick') }
function onMenuClick() { emit('menuClick') }
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
