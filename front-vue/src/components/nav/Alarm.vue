<template>
        <a-badge :count=value  :show-zero="true">
          <Button type="text" @click="onNotificationClick">
            <BellOutlined class="nav-icon"/>
          </Button>
        </a-badge>
</template>

<script setup lang="ts">
import { ref,onMounted } from 'vue'
import { Button } from 'ant-design-vue';
import { BellOutlined } from '@ant-design/icons-vue'

const {value} = defineProps({
  value: { type: Number, default: 0 }
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
