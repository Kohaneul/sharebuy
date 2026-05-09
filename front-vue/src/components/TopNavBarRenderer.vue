<template>
  <a-layout-header class="top-nav-bar">
    <div class="left">
        <Button type="text" @click="onLogoClick" >
        <img 
          src="/main-logo.png"
          class="button-image" 
        />
        </Button>

      <component
        v-for="(item, i) in leftItems"
        :key="'l-' + i"
        :is="NavComponentMap[item.component]"
        v-bind="item.props"
        @action="onAction"
      />
    </div>

    <div class="center">
      <component
        v-for="(item, i) in centerItems"
        :key="'c-' + i"
        :is="NavComponentMap[item.component]"
        v-bind="item.props"
        @action="onAction"
      />
    </div>

    <div class="right">
      <component
        v-for="(item, i) in rightItems"
        :key="'r-' + i"
        :is="NavComponentMap[item.component]"
        v-bind="item.props"
        @action="onAction"
      />

     <div v-if="roleType !== ROLES.GUEST">
      <Button @click="onLogout">로그아웃</Button>
      </div>
      <div v-else>
      <Button @click="onLogin">로그인</Button>
      </div>

    </div>
  </a-layout-header>
</template>


<script setup lang="ts">
import { ref,computed } from 'vue'
import { NavComponentMap} from '@/ts/NavComponentMap'
import { TopNavItemMeta} from '@/ts/TopNavItemMeta'
import { Button } from 'ant-design-vue';
import {commonPost} from '@/utils/ShareBuyUtil';
import { useRouter } from 'vue-router';
import { ROLES,RoleType } from '@/ts/UserType';

import { useUserStore } from '@/store/user';

const userStore = useUserStore();
const route = useRouter();

const {items,roleType,latitude,longitude} = defineProps<{
  items: TopNavItemMeta[],
  roleType:RoleType,
  menuId?: string,
  latitude?: number | null;
  longitude?: number | null;
}>();

const leftItems = computed(() =>
  items.filter(i => i.position === 'LEFT').map(i => ({
    ...i,
    props: { value: i.value,
      latitude,longitude } 
  }))
)

const centerItems = computed(() =>
  items.filter(i => i.position === 'CENTER').map(i => ({
    ...i,
    props: { value: i.value }
  }))
)

const rightItems = computed(() =>
  items.filter(i => i.position === 'RIGHT').map(i => ({
    ...i,
    props: { value: i.value }
  }))
)

const emit = defineEmits(['logoClick', 'search', 'notificationClick', 'menuClick'])

const searchQuery = ref('')

function onLogoClick() { route.replace("/login");}

function onLogin(){
    route.push("/login");
}

async function onLogout(){
  try{
    userStore.logout();
    await commonPost(`/auth/logout`,null);
    route.push("/");
  }
  catch(Error){
    console.log(Error);
  }
}
function onSearch() { emit('search', searchQuery.value) }
function onNotificationClick() { emit('notificationClick') }
function onMenuClick() { emit('menuClick') }

function onAction(payload: NavActionPayload) {
  emit('action', payload)
}
</script>

<style scoped>
.top-nav-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0;
  height: 56px;
  background-color: #1f3b57; /* 이미지 기준 네이비 */
  color: #fff;
}

.left, .center, .right {
  display: flex;
  align-items: center;
}

.left .location {
  margin-left: 8px;
  font-weight: 500;
}

.center {
  flex: 1;
  margin: 0 16px;
}

.center .a-input {
  width: 100%;
  border-radius: 20px;
}

.right > * {
  margin-left: 12px;
}

.button-image {
  /* max-width: 150%;  */
  max-height: 150%;
  padding:0;
  margin-top:-10px;
}

.nav-icon {
  font-size: 20px;   /* 크기 */
  color: #fff;       /* 흰색 */
}

.nav-icon:hover {
  color: #40a9ff; /* ant primary blue */
}


.button-image {
  /* max-width: 150%;  */
  max-height: 150%;
  padding:0;
  margin-top:-10px;
}

</style>