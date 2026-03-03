<template>
  <div style="background-color: #ececec; min-height: 100vh; padding: 40px 20px;">
    
    <div style="max-width: 1200px; margin: 0 auto;">
      
      <a-row :gutter="[16, 24]"> 
        <a-col 
          v-for="n in 20" 
          :key="n" 
          style="flex: 0 0 20%; max-width: 20%;"
          >          
          <a-card hoverable>
            <template #cover>
              <img
                alt="example"
                src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
              />
            </template>
            
            <template #actions>
              <setting-outlined key="setting" @click="console.log('설정 클릭', n)" />
              <edit-outlined key="edit" @click="console.log('수정 클릭', n)" />
              <ellipsis-outlined key="ellipsis" />
            </template>

            <a-card-meta 
              :title="`Card title ${n}`" 
              :description="`카드 상세 설명입니다.`"
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
</script>

<style scoped>
/* 카드가 부모 Col 너비에 꽉 차도록 설정 */
.ant-card {
  width: 100%;
  border-radius: 8px; /* 살짝 굴곡 주면 더 요즘 느낌 납니다 */
  overflow: hidden;
}
</style>