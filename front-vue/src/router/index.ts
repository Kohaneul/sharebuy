import { createRouter, createWebHistory } from 'vue-router'
import CommonPage from '../views/CommonPage.vue';
import LoginPage from '../views/LoginPage.vue';
import PostDetailPage from '../views/order/PostDetailPage.vue';
import RegisterNewPage from '../views/new/RegisterNewPage.vue';

import { useUserStore } from '@/store/user';
import { useLocationStore } from '@/store/location';
import { useUiStore } from '@/store/ui';


const routes = [
  { path: '/', redirect: '/login' }, // 기본 진입
  { path: '/login', component: LoginPage },
  { path: '/board', component: CommonPage },
  { path: '/new', component: RegisterNewPage },
  { path: '/detail/post', component: PostDetailPage }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// router/index.ts
router.beforeEach(async (to, from, next) => {
  //  1. 예외 처리: 로그인 페이지로 가는 경우는 그냥 보내준다.
  if (to.name === 'Login' || to.path === '/login'||to.path === '/') {
    return next();
  }
  const userStore = useUserStore();
  const locationStore = useLocationStore();

  // 1. userStore에 이미 위치 정보가 있는지 확인
  if (userStore.latitude && userStore.longitude) {
    // 이미 값이 있다면 추가 측정 없이 통과 (가장 빠름)
    // locationStore에도 동기화해주면 다른 컴포넌트에서 쓰기 편합니다.
    locationStore.setLocation(userStore.latitude, userStore.longitude);
  } else {
    // 2. 위치 정보가 없다면 (새로고침, 직접 진입 등) 새로 측정
    // syncLocation(true)를 통해 확실히 위치를 잡고 스토어에 셋팅
    const coords = await locationStore.syncLocation(true);
    
    // 3. 받아온 정보를 userStore에도 업데이트 (기본값 guest 유지)
    userStore.setUserInfo({
      ...userStore.$state,
      latitude: coords.latitude,
      longitude: coords.longitude
    });
  }

  next();
});
export default router
