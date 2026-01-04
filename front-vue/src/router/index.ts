import { createRouter, createWebHistory } from 'vue-router'
import BoardPage from '../views/BoardPage.vue';

const routes = [
  { path: '/board', component: BoardPage }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
