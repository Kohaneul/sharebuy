<template>
  <PageWrapper>
  <div class="post-detail-wrapper" v-if="post">
    <!-- 1. 상단 이미지 & 헤더 -->
    <div class="header-section">
      <img   :src="post.imgUrl ? post.imgUrl : '/default.png'"  class="main-image" />
      <div class="header-content">
        <a-avatar :src="post.userAvatar" :size="40" />
        <span class="nickname">{{ post.nickName }}</span>
        <a-tag :color="getStatusColor(post.status)">{{ getStatusLabel(post.status) }}</a-tag>
      </div>
    </div>

    <!-- 2. 본문 내용 -->
    <div class="content-section">
      <h1 class="post-title">{{ post.title }}</h1>
      <p class="post-body">{{ post.content }}</p>
      <a-divider />
    </div>

    <!-- 3. 지도 섹션 (가칭) -->
    <div class="map-section">
      <h3>거래 희망 장소</h3>
      <!-- 기존에 만드신 지도 컴포넌트가 있다면 여기에 교체하세요 -->
       <KakaoMap 
        :latitude="post.latitude" 
        :longitude="post.longitude" 
      />
    </div>

    <!-- 4. 참여 인원 통계 카드 (우리가 만든 것) -->
    <div class="stats-section">
      <a-card class="stats-card">
        <a-row :gutter="16">
          <a-col :span="12">
            <a-statistic title="현재 참여" :value="post.currentCount" suffix="명">
              <template #prefix><user-outlined /></template>
            </a-statistic>
          </a-col>
          <a-col :span="12">
            <a-statistic title="모집 정원" :value="post.maxCount" suffix="명" />
          </a-col>
        </a-row>
      </a-card>
    </div>

    <!-- 5. 하단 고정 액션 바 -->
    <div class="action-bar">
      <a-button 
        type="primary" 
        size="large" 
        block 
        :disabled="!isJoinable"
        @click="openJoinModal"
      >
        {{ getButtonText }}
      </a-button>
    </div>

    <!-- 6. 참여 확인 팝업 (모달) -->
    <a-modal
      v-model:visible="modalVisible"
      title="공동구매 참여"
      @ok="handleJoin"
      :confirm-loading="loading"
      ok-text="참여하기"
      cancel-text="취소"
    >
      <div class="modal-content">
        <p><strong>"{{ post.title }}"</strong> 공동구매에 참여하시겠습니까?</p>
        <p class="warning">* 참여 후 무단 취소 시 서비스 이용이 제한될 수 있습니다.</p>
      </div>
    </a-modal>
  </div>

  <!-- 로딩 스켈레톤 -->
  <div v-else class="loading-state">
    <a-spin size="large" tip="데이터를 불러오는 중..." />
  </div>
  </PageWrapper>
</template>

<script setup lang="ts">
import { ref,  computed,onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { UserOutlined } from '@ant-design/icons-vue';
import { commonGet, commonPost } from '@/utils/ShareBuyUtil'; // 기존 유틸 활용
import { message } from 'ant-design-vue';
import { useUserStore } from '@/store/user';
import PageWrapper from '@/views/PageWrapper.vue';

import { useLocationStore } from '@/store/location';
import KakaoMap from '@/components/page/KakaoMap.vue';
const locationStore = useLocationStore();
const userStore = useUserStore();


const route = useRoute();
const post = ref<any>(null);
const modalVisible = ref(false);
const loading = ref(false);
const latitude = ref();
const longitude = ref();

onMounted(async ()=>{
    const coords = await locationStore.syncLocation(true); 
    modalVisible.value = userStore.isLoggedIn;
    latitude.value = coords.latitude;
    longitude.value = coords.longitude;
    loadDetail();
});

// 1. 데이터 로드
const loadDetail = async () => {
  const id = route.query.id;

  try {
    // 상세 조회를 위한 API 호출 (엔드포인트는 실제에 맞게 수정)
    const res = await commonGet(`/post/${id}`, {latitude:latitude.value, longitude:longitude.value});
    post.value = res;
  } catch (error) {
    message.error('데이터를 불러오는데 실패했습니다.');
  }
};

// 2. 상태 관련 로직
const isJoinable = computed(() => {
  return post.value?.status === 'RECRUITING' && post.value?.currentCount < post.value?.maxCount;
});

const getButtonText = computed(() => {
  if (post.value?.status !== 'RECRUITING') return '모집 종료';
  if (post.value?.currentCount >= post.value?.maxCount) return '인원 마감';
  return '참여하기';
});

const getStatusColor = (status: string) => {
  const colors: any = { RECRUITING: 'green', CLOSED: 'red', EDITING: 'orange' };
  return colors[status] || 'default';
};

const getStatusLabel = (status: string) => {
  const labels: any = { RECRUITING: '모집중', CLOSED: '마감', EDITING: '수정중' };
  return labels[status] || status;
};

// 3. 참여 액션
const openJoinModal = () => {
  modalVisible.value = true;
};

const handleJoin = async () => {
  loading.value = true;
  try {
    // 참여 API 호출
    await commonPost(`/api/posts/${post.value.id}/join`, {});
    message.success('성공적으로 참여되었습니다! 🎉');
    modalVisible.value = false;
    await loadDetail(); // 데이터 새로고침 (인원수 업데이트)
  } catch (error) {
    message.error('참여 처리 중 오류가 발생했습니다.');
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.post-detail-wrapper {
  padding-bottom: 80px; /* 액션바 공간 */
  background: #fff;
}

.header-section {
  position: relative;
}

.main-image {
  width: 100%;
  height: 250px;
  object-fit: cover;
}

.header-content {
  padding: 16px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.nickname {
  font-weight: bold;
  font-size: 16px;
}

.content-section {
  padding: 0 16px;
}

.post-title {
  font-size: 22px;
  font-weight: 800;
  margin-bottom: 12px;
}

.post-body {
  font-size: 16px;
  line-height: 1.6;
  color: #333;
}

.map-placeholder {
  width: 100%;
  height: 150px;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  margin: 10px 0;
}

.stats-section {
  padding: 16px;
}

.action-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 16px;
  background: #fff;
  box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
  z-index: 100;
}

.warning {
  color: #ff4d4f;
  font-size: 12px;
  margin-top: 8px;
}

.loading-state {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>