<template>
  <div class="section-button-wrapper">
    <a-button type="primary" size="large" @click="handleClick">
      {{ title }}
    </a-button>
  </div>
</template>

<script setup lang="ts">
import { ActionType } from '@/ts/PageComponent';
import { useRouter } from 'vue-router';
import {commonPost} from '@/utils/ShareBuyUtil';

// 백엔드에서 내려온 PageSectionMeta의 필드들
const {actionType,dataUrl,targetData, title} = defineProps<{
  title: string;
  actionType:ActionType;
  dataUrl: string; // 이동할 경로
  targetData?: number | string | any[] | Record<string, any>;}>();

const router = useRouter();

const handleClick = async () => {
  if (actionType === ActionType.API) {
    // 1. 단순 ID 치환이 필요한 경우 (기존 방식)
    let finalUrl = props.dataUrl;
    if (typeof props.targetData === 'string' || typeof props.targetData === 'number') {
       finalUrl = props.dataUrl.replace('{id}', String(props.targetData));
    }

    // 2. 배열이나 객체인 경우 (Axios params 활용)
    const config = {
      params: (typeof props.targetData === 'object') ? props.targetData : null
    };

    if (confirm('요청을 진행할까요?')) {
      // Axios가 알아서 ?ids=1,2,3 또는 ?cat=news 형태로 변환해줌!
      await axios.post(finalUrl, null, config); 
      alert('성공!');
    }
  }
};

</script>

<style scoped>
.section-button-wrapper {
  padding: 16px;
  text-align: center;
}
</style>