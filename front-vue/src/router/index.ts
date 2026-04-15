import { createRouter, createWebHistory } from 'vue-router'
import CommonPage from '../views/CommonPage.vue';
import LoginPage from '../views/LoginPage.vue';


const routes = [
  { path: '/login', component: LoginPage },
  { path: '/board', component: CommonPage },
  { path: '/new', component: CommonPage },
  { path: '/detail', component: CommonPage },
  { path: '/', redirect: '/login' } // 기본 진입
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
