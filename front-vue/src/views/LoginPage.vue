<template>
  <div class="login-wrapper">
    <a-card class="login-card">
      <h2 class="title">
        <img src="/main-logo.png" class="logo" />
      </h2>

      <a-form layout="vertical">
        <a-form-item label="아이디">
          <a-input placeholder="아이디를 입력하세요" />
        </a-form-item>

        <a-form-item label="비밀번호">
          <a-input-password placeholder="비밀번호를 입력하세요" />
        </a-form-item>

        <a-form-item>
          <a-button type="primary" block>
            로그인
          </a-button>
        </a-form-item>

        <div class="guest-area">
          <a-divider>또는</a-divider>
          <a-button block @click="enterAsGuest">
            게스트로 입장
          </a-button>
        </div>
      </a-form>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

function enterAsGuest() {
// 1. 브라우저 위치 정보 지원 여부 확인
  if (!navigator.geolocation) {
    alert("위치 정보를 지원하지 않는 브라우저입니다.");
    router.push('/board'); // 좌표 없이 그냥 이동
    return;
  }

  // 2. 현재 위치 가져오기
  navigator.geolocation.getCurrentPosition(
    (position) => {
      const lat = position.coords.latitude;
      const lng = position.coords.longitude;

      // 좌표를 쿼리 스트링에 실어서 보냄 (예: /board?lat=37.1&lng=127.1)
      router.push({
        path: '/board',
        query: { lat, lng }
      });
    },
    (error) => {
      console.error("위치 획득 실패:", error);
      // 권한 거부 등을 했을 경우에도 일단 입장은 시켜줌
      router.push('/board');
    }
  );


  // 게시글 조회 페이지로 이동
  router.push('/board');
}
</script>
<style scoped>
.login-wrapper {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f0f2f5;
}

.login-card {
  width: 360px;
}

.title {
  text-align: center;
  margin-bottom: 24px;
}

.logo {
  height: 32px;
}
</style>
