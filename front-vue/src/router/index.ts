import { createRouter, createWebHistory } from 'vue-router'
import BoardPage from '../views/CommonPage.vue';
import LoginPage from '../views/LoginPage.vue';


const routes = [
  { path: '/login', component: LoginPage },
  { path: '/board', component: BoardPage },
  { path: '/page/board', component: BoardPage },
  { path: '/', redirect: '/login' } // 기본 진입
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
