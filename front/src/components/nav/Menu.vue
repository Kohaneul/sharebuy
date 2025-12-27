<template>
 <Button type="text" @click="onMenuClick">
   <MenuOutlined class="nav-icon" />
  </Button>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits } from 'vue'
import { Button } from 'ant-design-vue';
import { SearchOutlined,BellOutlined,MenuOutlined } from '@ant-design/icons-vue'

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
.nav-icon {
  font-size: 20px;   /* 크기 */
  color: #fff;       /* 흰색 */
}

.nav-icon:hover {
  color: #40a9ff; /* ant primary blue */
}
</style>
