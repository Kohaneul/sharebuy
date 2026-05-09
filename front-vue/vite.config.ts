import { fileURLToPath, URL } from 'node:url'
import { defineConfig, loadEnv } from 'vite' // 👈 loadEnv 추가
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import { createHtmlPlugin } from 'vite-plugin-html' // 👈 추가

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd());
  console.log(env);

  return {
    plugins: [
      vue(),
      vueDevTools(),
      // 🌟 HTML 플러그인 설정 추가
      createHtmlPlugin({
        inject: {
          data: {
            // .env의 KAKAO_MAP_KEY를 index.html에서 kakaoKey로 쓰겠다고 정의
            kakaoKey: env.VITE_KAKAO_MAP_KEY,
          },
        },
      }),
    ],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url))
      },
    },
  }
})