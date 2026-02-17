<template>
<div>
      <a-button type="text" @click="onLogoClick" >
      <img 
          src="/location.png"
          alt="버튼 이미지" 
           class="w-5 h-5 mr-1"
        />
        <span style="color:white; padding-left:5px;"class="w-5 h-5 mr-1">{{ locationDisplayData?.locationInfo }}</span>
      </a-button>
    </div>


</template>

<script setup lang="ts">
import { ref,onMounted } from 'vue'
import { SearchOutlined,BellOutlined,MenuOutlined } from '@ant-design/icons-vue'
import axios from 'axios'


const {locaionData} = defineProps<{
  locaionData: LocationData;
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

const location = ref<LocationData | null>(null);
const locationDisplayData =ref<LocationData | null>(null);

const loading = ref(false)
const error = ref<string | null>(null)

onMounted(async () => {
  
if(!locaionData){
  // props 에서 받는 정보가 없으면 -> 현위치기반 geometric 호출
  if (location.value) {
    locationDisplayData.value = await getAddressFromCoords(location.value.latitude, location.value.longitude)
  }

  if (!navigator.geolocation) {
    error.value = '위치 정보 사용 불가'
    return
  }

  loading.value = true;

  navigator.geolocation.getCurrentPosition(
    async (position) => {
      locationDisplayData.value = {
        latitude: position.coords.latitude,
        longitude: position.coords.longitude
      }

      try {
        // 위치 기반 API 호출
        const response = await axios.post<LocationResponse>('/api/location-based-data', {
          latitude: location.value.latitude,
          longitude: location.value.longitude
        })
        locationData.value = response.data
      } catch (err) {
        console.error(err)
        error.value = '데이터 조회 실패'
      } finally {
        loading.value = false
      }
    },
    (err) => {
      console.error(err)
      error.value = '위치 정보를 가져올 수 없습니다'
      loading.value = false
    },
    { enableHighAccuracy: true, timeout: 10000 }
  )
}

locationDataProps.value.locationInfo = locationDisplayData.value;


})

const emit = defineEmits(['logoClick', 'search', 'notificationClick', 'menuClick'])

const searchQuery = ref('')

function onLogoClick() { emit('logoClick') }
function onSearch() { emit('search', searchQuery.value) }
function onNotificationClick() { emit('notificationClick') }
function onMenuClick() { emit('menuClick') }


const getAddressFromCoords = async (lat: number, lng: number) => {
  const apiKey = 'YOUR_GOOGLE_API_KEY'  
  const url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${apiKey}`

  try {
    const response = await fetch(url)
    const data = await response.json()
    if (data.status === 'OK') {
      return data.results[0].formatted_address
    }
    return '주소를 찾을 수 없음'
  } catch (err) {
    console.error(err)
    return '주소 변환 실패'
  }
}


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
