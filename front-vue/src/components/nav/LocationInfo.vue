<template>
<div>
      <a-button type="text" @click="onLogoClick" >
      <img 
          src="/location.png"
          alt="버튼 이미지" 
           class="w-5 h-5 mr-1"
        />
        <span style="color:white; padding-left:5px;"class="w-5 h-5 mr-1">{{ displayLocation }}</span>
      </a-button>
    </div>


</template>

<script setup lang="ts">
import { ref,onMounted } from 'vue'
import { SearchOutlined,BellOutlined,MenuOutlined } from '@ant-design/icons-vue'
import axios from 'axios'


const {value} = defineProps<{
  value:string;
  menuId?: string;
}>();

interface LocationData {
  latitude: number
  longitude: number
  locationInfo:string
}

interface LocationResponse {
  recommendedSections: any[]  // API에서 반환하는 구조에 맞게 타입 지정
}

const displayLocation = ref<string>();

const loading = ref(false)
const error = ref<string | null>(null)

onMounted(async () => {
  //guest로 진입하면 (=props.value = null) 내 현위치 기반으로 정보 표시한다.
  if(!value){
    await fetchCurrentGPSLocation();
  }
  else{
    displayLocation.value = value;
  }
})

const fetchCurrentGPSLocation = () => {
  if (!navigator.geolocation) {
    displayLocation.value = "위치 정보 미지원";
    return;
  }

  loading.value = true;

  navigator.geolocation.getCurrentPosition(
    async (position) => {
      const { latitude, longitude } = position.coords;
      
      // 구글 Geocoding API 등을 통해 좌표를 "주소 문자열"로 변환
      // (기존에 만드신 getAddressFromCoords 함수 활용)
      const addressText = await getAddressFromCoords(latitude, longitude);
      
      displayLocation.value = addressText;
      loading.value = false;
    },
    (err) => {
      console.error("GPS 획득 실패:", err);
      displayLocation.value = "위치 확인 실패";
      loading.value = false;
    },
    { enableHighAccuracy: true, timeout: 5000 }
  );
};

// 좌표 -> 주소 변환 함수 (기존 코드 유지)
const getAddressFromCoords = async (lat: number, lng: number) => {
  const apiKey = import.meta.env.VITE_GOOGLE_MAP_KEY;  
  const url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${apiKey}&language=ko`;

  try {
    const response = await fetch(url);
    const data = await response.json();
    if (data.status === 'OK') {
      const fullAddress =data.results[0].formatted_address;
      const parts = fullAddress.split(" ").filter(p => p !== "대한민국");
      const resultAddress =  parts.length <= 3 ? parts.join(" ") : parts.slice(0, 3).join(" ");
      return resultAddress;
    }
    return '현 위치 확인 불가';
  } catch (err) {
    return '주소 변환 실패';
  }
};

const emit = defineEmits(['logoClick', 'search', 'notificationClick', 'menuClick'])

const searchQuery = ref('')

function onLogoClick() { emit('logoClick') }


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
  max-width: 150%; 
  max-height: 150%;
  padding:0;
  margin-top:-10px;
}
</style>
