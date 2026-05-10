<template>
  <!-- 1. ref를 사용하여 DOM에 접근합니다 -->
  <div ref="mapContainer" class="kakao-map"></div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
const props = defineProps<{
  latitude: number;
  longitude: number;
  level?: number;
}>();

const mapContainer = ref<HTMLElement | null>(null);
let map: any = null;
let marker: any = null;

const initMap = () => {
  // 1. window에서 kakao 객체를 가져옵니다.
  const { kakao } = window as any;

  // 2. 만약 스크립트 자체가 로드 안되었다면 재시도 로직이나 에러 처리를 합니다.
  if (!kakao || !kakao.maps) {
    console.error('카카오맵 스크립트가 아직 로드되지 않았습니다. 500ms 후 재시도합니다.');
    setTimeout(initMap, 500);
    return;
  }

  // 3. autoload=false 설정 시 필수인 load 함수 호출
  kakao.maps.load(() => {
    if (!mapContainer.value) return;

    const options = {
      center: new kakao.maps.LatLng(props.latitude, props.longitude),
      level: props.level || 3
    };

    const map = new kakao.maps.Map(mapContainer.value, options);
    
    const marker = new kakao.maps.Marker({
      position: new kakao.maps.LatLng(props.latitude, props.longitude)
    });
    marker.setMap(map);
  });
};

// 5. 컴포넌트가 마운트된 후 지도를 초기화합니다.
onMounted(() => {
  initMap();
});

// 6. 혹시 위도/경도가 중간에 바뀔 경우를 대비한 Watcher
watch(() => [props.latitude, props.longitude], ([newLat, newLng]) => {
  if (map) {
    const moveLatLon = new (window as any).kakao.maps.LatLng(newLat, newLng);
    map.setCenter(moveLatLon);
    marker.setPosition(moveLatLon);
  }
});
</script>

<style scoped>
.kakao-map {
  width: 100%;    /* 너비는 부모에 맞춰서 */
  height: 200px;  /* 🌟 높이를 반드시 'px' 단위로 명시하세요! */
  background-color: #f0f0f0; /* 지도가 안 뜰 때 영역 확인용 */
}
</style>