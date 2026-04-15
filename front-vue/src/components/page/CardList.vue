<template>
  <div style="background-color: #ececec; min-height: 100vh; padding: 0px;">
    
    <div style="margin: 0 auto;">
      
     <a-col 
        v-for="card in cards" 
        :key="card.id"
        :span="24">          
          <a-card hoverable class="horizontal-card" @click="goDetail(card.id)">

            <div class="card-body">
              
              <!-- 왼쪽 이미지 -->
              <img
                  class="card-image"
                  :src="card.imgUrl || '/default.png'"
                />

              <!-- 오른쪽 내용 -->
             <div class="card-content">

              <!-- 👇 상단: 아바타 + 닉네임 -->
              <div class="user-info">
                <a-avatar src="https://xsgames.co/randomusers/avatar.php?g=pixel" />
                <span class="nickname">{{ card.nickName }}</span>
                 <a-tag :color="getStatusColor(card.status)">
                  {{ getStatusLabel(card.status) }}
                </a-tag>
              </div>

              <!-- 👇 제목 -->
              <div class="title-row">
                <div class="title">{{ card.title }}</div>
                <div class="participants">
                  {{ card.currentParticipants }} / {{ card.maxParticipants }}
                </div>
              </div>
              
              <!-- 👇 내용 -->
              <div class="content">{{ card.content }}</div>
              

              <!-- 👇 액션 -->
              <div class="card-actions" v-if="hasAuthority(card)">
                <setting-outlined @click="console.log('설정 클릭', card.id)" />
                <edit-outlined @click="console.log('수정 클릭', card.id)" />
              </div>

            </div>
            </div>

          </a-card>
    </a-col>
      
    </div>
  </div>
</template>

<script lang="ts" setup>
import { SettingOutlined, EditOutlined } from '@ant-design/icons-vue';
import {onMounted,ref} from 'vue';
import {getCurrentLocation} from '@/utils/CurrentLocationUtil';
import { commonGet } from '@/utils/ShareBuyUtil';
import{ CardData } from '@/ts/PageComponent';
import { useUserStore } from '@/store/user';
import { useLocationStore } from '@/store/location';

import { useRouter } from 'vue-router';
const userStore = useUserStore();

const locationStore = useLocationStore();

const router = useRouter();

const props = defineProps<{
  dataUrl: string
  jsonConfig:string
}>();

const config = ref();

const cards = ref<CardData[]>([]);

onMounted(async () => {
  
  config.value = props.jsonConfig ? JSON.parse(props.jsonConfig): {};
  
  let context = {};

  if (config.value.useCurrentLocation) {
    const pos = await getCurrentLocation();
    locationStore.setLocation(pos.latitude, pos.longitude);

    context = {
      latitude: pos.latitude,
      longitude: pos.longitude
    }

  }
  await bindCard(context);
})

const hasAuthority = (card: CardData) => {
  if (userStore.roleType === 'ADMIN') return true;
  return userStore.userId !== 'guest' && userStore.loginId === card.loginId;
};

async function bindCard(context:any){
  try{
    const res:CardData[] = await commonGet(props.dataUrl,context);
    if(res){
      cards.value =res;
    }
  }
  catch(Error){
    console.log(Error);
  }
}

const getStatusColor = (status: string) => {
  switch (status) {
    case 'RECRUITING':
      return 'green';
    case 'CLOSED':
      return 'red';
    case 'EDITING':
      return 'orange';
    case 'CANCELED':
      return 'default';
    default:
      return 'default';
  }
};

const getStatusLabel = (status: string) => {
  switch (status) {
    case 'RECRUITING':
      return '모집중';
    case 'CLOSED':
      return '마감';
    case 'EDITING':
      return '수정중';
    case 'CANCELED':
      return '취소';
    default:
      return status;
  }
};

const goDetail = (id: string) => {
  router.push(`/card/${id}`);
};
</script>

<style scoped>
/* 카드가 부모 Col 너비에 꽉 차도록 설정 */
.ant-card {
  width: 100%;
  border-radius: 8px; 
  overflow: hidden;
}

.horizontal-card {
  width: 100%;
}

.card-body {
  display: flex;
  align-items: center; /* 🔥 중요 */
  gap: 10px;
  padding: 8px;
}

/* 이미지 */
.card-image {
  width: 60px;
  height: 60px;
  border-radius: 6px;
  object-fit: cover;
}

/* 내용 */
.card-content {
  flex: 1;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 4px; 
}

/* 버튼 */
.card-actions {
  display: flex;
  gap: 12px;
  margin-top: 8px;
}

/* 아바타 + 닉네임 */
.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
}
.ant-tag {
  margin-left: 6px;
}

.nickname {
  font-weight: 600;
}

/* 제목 */
.title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 4px;
}

/* 내용 */
.content {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;

  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.participants {
  font-size: 1rem;
  font-weight: 600;
  color: #1890ff;
}
:deep(.ant-card-body) {
  padding: 5px;
}

.title-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

</style>