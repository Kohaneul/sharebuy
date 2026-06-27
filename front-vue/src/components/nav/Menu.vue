<template>
  <Button type="text" @click="menuOpen = true" class="nav-icon">
    <MenuOutlined class="nav-icon" />
  </Button>

  <Drawer
    v-model:open="menuOpen"
    placement="right"
  >
    <p>메뉴1</p>
    <p>메뉴2</p>
    <p>메뉴3</p>
  </Drawer>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Button, Drawer } from 'ant-design-vue'
import { MenuOutlined } from '@ant-design/icons-vue'

const menuOpen = ref(false)
</script>

<style lang="css" scoped>
.nav-icon {
  font-size: 20px;   /* 크기 */
  color: #fff;       /* 흰색 */
}

.nav-icon:hover {
  color: #40a9ff; /* ant primary blue */
}
</style>