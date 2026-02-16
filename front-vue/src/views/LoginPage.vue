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
