<template>
  <div class="login-wrapper">
    <a-card class="login-card">
      <h2 class="title">
        <img src="/main-logo.png" class="logo" />
      </h2>

      <a-form layout="vertical">
        <a-form-item label="아이디">
          <a-input placeholder="아이디를 입력하세요" v-model:value="loginId" />
        </a-form-item>

        <a-form-item label="비밀번호">
          <a-input-password placeholder="비밀번호를 입력하세요" v-model:value="password"  @pressEnter="loginUser" />
        </a-form-item>

        <a-form-item>
          <a-button type="primary" block @click="loginUser">
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
import {commonPostLogin} from '@/utils/ShareBuyUtil';
import {ref} from 'vue';
import { useLocationStore } from '@/store/location'; 
import { useUserStore } from '@/store/user'; 

const locationStore = useLocationStore();
const userStore = useUserStore();

const router = useRouter();
const loginId = ref<string|null>('');
const password = ref<string|null>('');

async function loginUser(){
  if (!loginId.value || !password.value) {
    alert("아이디와 비밀번호를 입력해주세요.");
    return;
  }
  try{ 
  const param = new URLSearchParams();
  param.append('username', loginId.value); 
  param.append('password', password.value);
  
   const res= await commonPostLogin(`/auth/login`,param);
   console.log(res);
if (res) {
  // 1. 유저 스토어에 통째로 저장 (좌표 포함)
  userStore.setUserInfo(res);

  // 2. 위치 스토어에도 필요하다면 중복 저장 혹은 동기화
  locationStore.setLocation(res.latitude, res.longitude);

  // 3. 페이지 이동
  router.push({
    path: "/board",
    query: { latitude: res.latitude, longitude: res.longitude }
  });
}
  router.push("/board")
  }
  catch(Error){
    clearData();
    console.log(Error);
  }
}

function clearData(){
  loginId.value = null;
  password.value= null;
}

async function enterAsGuest() {
  if (!navigator.geolocation) {
    alert("위치 정보를 지원하지 않는 브라우저입니다.");
    router.push('/board');
    return;
  }

  // 1. 현재 GPS 좌표 가져오기
  const { latitude, longitude } = await locationStore.syncLocation(true);

  // 2. 🚀 게스트라도 userStore에 위치 정보 저장!
  // 로그인 정보는 없으니 null이나 기본값을 유지하고 좌표만 업데이트합니다.
  userStore.setUserInfo({
    loginId: 'guest',
    roleType: 'GUEST',
    latitude: latitude,
    longitude: longitude
  });

  // 3. 페이지 이동
  router.push({
    path: '/board',
    query: { latitude: latitude, longitude: longitude }
  });
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
