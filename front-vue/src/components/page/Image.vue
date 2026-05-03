<template>
  <div class="image-section-container">
    <!-- 이미지가 배열로 들어올 경우 (여러 장) -->
    <div v-if="Array.isArray(images) && images.length > 0" class="image-grid">
      <a-image-preview-group>
        <a-image
          v-for="(url, index) in images"
          :key="index"
          :src="url"
          :alt="'image-' + index"
          class="detail-image"
        />
      </a-image-preview-group>
    </div>

    <!-- 이미지가 단일 문자열일 경우 -->
    <div v-else-if="typeof images === 'string' && images" class="single-image">
      <a-image :src="images" alt="detail-image" />
    </div>

    <!-- 이미지가 없을 때 (Placeholder) -->
    <div v-else class="no-image">
      <a-empty description="이미지가 없습니다" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const props = defineProps<{
  value: string | string[] | null; // 백엔드에서 넘어온 이미지 데이터
}>();

const images = computed(() => props.value);
</script>

<style scoped>
.image-section-container {
  margin-bottom: 24px;
  width: 100%;
}

.image-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

/* 이미지 크기 조절 (상세페이지 스타일에 맞게) */
:deep(.ant-image) {
  width: 120px;
  height: 120px;
  object-fit: cover;
  border-radius: 8px;
  overflow: hidden;
}

.single-image :deep(.ant-image) {
  width: 100%;
  height: auto;
  max-height: 400px;
}

.no-image {
  padding: 40px;
  background-color: #fafafa;
  border-radius: 8px;
}
</style>