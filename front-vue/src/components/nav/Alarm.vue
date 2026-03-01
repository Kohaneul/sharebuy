<template>
        <a-badge :count=countRef>
          <Button type="text" @click="onNotificationClick">
            <BellOutlined class="nav-icon"/>
          </Button>
        </a-badge>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Button } from 'ant-design-vue';
import { BellOutlined } from '@ant-design/icons-vue'

const props = defineProps({
  location: { type: String, default: '서울시 강남구' },
  notificationCount: { type: Number, default: 0 }
})
const countRef = ref<number>(0);

const emit = defineEmits([ 'notificationClick'])

const searchQuery = ref('')

function onNotificationClick() { emit('notificationClick') }


</script>

<style scoped>
.nav-icon {
  font-size: 20px;   /* 크기 */
  color: #fff;       /* 흰색 */
}

.nav-icon:hover {
  color: #40a9ff; /* ant primary blue */
}
</style>
