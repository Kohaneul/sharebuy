<template>
  <Button type="text" @click="onMenuButtonClick" class="nav-icon">
    <MenuOutlined class="nav-icon" />
  </Button>

<Drawer
  v-model:open="menuOpen"
  placement="right"
  title="메뉴"
  :width="300"
>
  <a-menu
    class="drawer-menu"
    mode="inline"
    theme="light"
    v-model:openKeys="openKeys"
    v-model:selectedKeys="selectedKeys"
    @click="onMenuClick">

    <a-sub-menu
      v-for="menu in menus"
      :key="menu.key"
    >
      <template #title>
        {{ menu.title }}
      </template>

      <a-menu-item
        v-for="child in menu.children"
        :key="child.key"
      >
        {{ child.title }}
      </a-menu-item>
    </a-sub-menu>
  </a-menu>
</Drawer>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Button, Drawer } from 'ant-design-vue'
import { MenuOutlined } from '@ant-design/icons-vue'
import { useRouter } from 'vue-router';
import { commonGet } from '@/utils/ShareBuyUtil';
import { useUserStore } from '@/store/user';


const userStore = useUserStore();

const menuOpen = ref(false);
const router = useRouter();


const openKeys = ref(['inventory']) // 처음 펼칠 메뉴
const selectedKeys = ref([])

const menus = ref();

async function onMenuButtonClick(){
  menuOpen.value = true;
  const roleType = userStore.roleType;
  menus.value = await commonGet(`/menu/all`,{roleType:roleType});
  console.log("메뉴클릭");
}
const onMenuClick = ({ key }: { key: string }) => {
  const menu = menus.flatMap(menu=>menu.children).find(child=>child.key===key);
  if(!menu) return;
    router.push({ name: menu.name })

  // 메뉴 클릭 시 Drawer 닫기
  menuOpen.value = false

}
</script>
<style lang="css" scoped>
.nav-icon {
  font-size: 20px;   /* 크기 */
  color: #fff;       /* 흰색 */
}

.nav-icon:hover {
  color: #40a9ff; /* ant primary blue */
}

.drawer-menu {
  border-right: none;
}

/* 대메뉴 */
:deep(.drawer-menu .ant-menu-submenu-title) {
  height: 52px;
  line-height: 52px;
  font-size: 16px;
  font-weight: 600;
}

/* 소메뉴 */
:deep(.drawer-menu .ant-menu-item) {
  height: 46px;
  line-height: 46px;
  font-size: 15px;
  padding-left: 40px !important;
}

/* 선택된 메뉴 */
:deep(.drawer-menu .ant-menu-item-selected) {
  background: #e6f4ff;
  color: #1677ff;
  font-weight: 600;
  border-radius: 8px;
}

/* Hover */
:deep(.drawer-menu .ant-menu-item:hover),
:deep(.drawer-menu .ant-menu-submenu-title:hover) {
  color: #1677ff;
  background: #f5f5f5;
  border-radius: 8px;
}
</style>