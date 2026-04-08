<template>
  <div class="section-button-wrapper">
    <a-button type="primary" size="large" @click="handleClick">
      {{ title }}
    </a-button>
  </div>
</template>

<script setup lang="ts">
import { ActionType ,JsonConfig} from '@/ts/PageComponent';
import { useRouter } from 'vue-router';
import { commonPost } from '@/utils/ShareBuyUtil';

const props = defineProps<{
  title: string;
  actionType: ActionType;
  dataUrl: string;
  jsonConfig?: string; 
  targetData?: any;
}>();

const router = useRouter();

const handleClick = async () => {
  // MOVE 타입이면 그냥 이동 (기존 로직 유지)
  if (props.actionType === ActionType.MOVE) {
    router.push(props.dataUrl);
    return;
  }

  if (props.actionType === ActionType.API) {
    // 2. JSON 파싱
    const config: JsonConfig = props.jsonConfig ? JSON.parse(props.jsonConfig) : {};

    // 3. [Confirm] 설정된 문구가 있으면 띄우기
    if (config.confirm && !confirm(config.confirm)) return;

    try {
      // 4. {id} 처리
      let finalUrl = props.dataUrl;
      if (typeof props.targetData === 'string' || typeof props.targetData === 'number') {
        finalUrl = props.dataUrl.replace('{id}', String(props.targetData));
      }

      // 5. [Payload 조립] payloadKey가 있으면 감싸서 보냄
      const requestData = config.payloadKey 
        ? { [config.payloadKey]: props.targetData } 
        : props.targetData;

      // 6. [API 호출] commonPost 대신 조금 더 유연하게 호출하거나, 
      // 현재 commonPost가 POST 전용이면 config.method에 따라 분기 처리
      await commonPost(finalUrl, requestData); 

      // 7. [후처리] 성공 메시지 & 새로고침
      if (config.msg) alert(config.msg);
      if (config.refresh) {
        window.location.reload(); // 또는 부모에게 emit('refresh')
      }

    } catch (error) {
      console.error("Error:", error);
      alert("처리 중 오류가 발생했습니다.");
    }
  }
};
</script>
<style lang="css" scoped>
.section-button-wrapper {
  display: inline-flex;
  margin-right: 8px; /* 간격 */
}
</style>