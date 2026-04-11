<template>
  <div style="background-color: #ececec; min-height: 100vh; padding: 40px 20px;">
    
    <div style="max-width: 1200px; margin: 0 auto;">
      
      <a-row :gutter="[16, 24]"> 
        <a-col 
          v-for="card in cards" 
          :key="card" 
          style="flex: 0 0 20%; max-width: 20%;"
          >          
          <a-card hoverable>
            <template #cover>
              <img
                alt="example"
                :src="card.imgUrl || 'https://images.unsplash.com/photo-1583947215259-38e31be8751f?q=80&w=500'"
              />
            </template>
            
            <template #actions>
              <template v-if="hasAuthority(card)">
                <setting-outlined key="setting" @click="console.log('설정 클릭', card.id)" />
                <edit-outlined key="edit" @click="console.log('수정 클릭', card.id)" />
              </template>
            </template>

            <a-card-meta 
              :title=card.title 
            >
              <template #avatar>
                <a-avatar src="https://xsgames.co/randomusers/avatar.php?g=pixel" />
              </template>
            </a-card-meta>
          </a-card>
        </a-col>
      </a-row>
      
    </div>
  </div>
</template>

<script lang="ts" setup>
import { SettingOutlined, EditOutlined, EllipsisOutlined } from '@ant-design/icons-vue';
import {onMounted,ref} from 'vue';
import {getCurrentLocation} from '@/utils/location';
import { commonGet } from '@/utils/ShareBuyUtil';
import{ CardData } from '@/ts/PageComponent';
import { useUserStore } from '@/store/user';
const userStore = useUserStore();

const props = defineProps<{
  dataUrl: string
  jsonConfig:string
}>();

const config = ref();

const cards = ref<CardData[]>([]);

onMounted(async () => {

   config.value = props.jsonConfig   ? JSON.parse(props.jsonConfig): {};

  let context = {};

  if (config.value.useCurrentLocation) {
    const pos = await getCurrentLocation();
    console.log(pos);

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


</script>

<style scoped>
/* 카드가 부모 Col 너비에 꽉 차도록 설정 */
.ant-card {
  width: 100%;
  border-radius: 8px; /* 살짝 굴곡 주면 더 요즘 느낌 납니다 */
  overflow: hidden;
}
</style>