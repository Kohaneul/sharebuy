import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createAnppp(App)
app.use(router)
app.use(createPinia())  //pinia
app.mount('#app')
