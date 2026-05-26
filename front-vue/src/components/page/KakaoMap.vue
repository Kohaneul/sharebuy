<template>
  <!-- 1. ref를 사용하여 DOM에 접근합니다 -->
  <div ref="mapContainer" class="kakao-map"></div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, onUnmounted } from 'vue';

const props = defineProps<{
  latitude: number;
  longitude: number;
  level?: number;
}>();

const mapContainer = ref<HTMLElement | null>(null);

let map: any = null;
let marker: any = null;

const initMap = () => {
  const { kakao } = window as any;

  if (!kakao || !kakao.maps) {
    console.error('카카오맵 스크립트가 아직 로드되지 않았습니다.');
    setTimeout(initMap, 500);
    return;
  }

  kakao.maps.load(() => {
    if (!mapContainer.value) return;

    const center = new kakao.maps.LatLng(
      props.latitude,
      props.longitude
    );

    const options = {
      center,
      level: props.level || 3
    };

    // ❌ const map = ...
    // ❌ const marker = ...

    map = new kakao.maps.Map(mapContainer.value, options);

    marker = new kakao.maps.Marker({
      position: center
    });

    marker.setMap(map);
  });
};

const handleResize = () => {
  if (!map) return;

  map.relayout();

  const moveLatLon = new (window as any).kakao.maps.LatLng(
    props.latitude,
    props.longitude
  );

  map.setCenter(moveLatLon);
};

onMounted(() => {
  initMap();

  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
});

watch(
  () => [props.latitude, props.longitude],
  ([newLat, newLng]) => {
    if (!map || !marker) return;

    const moveLatLon = new (window as any).kakao.maps.LatLng(
      newLat,
      newLng
    );

    map.setCenter(moveLatLon);
    marker.setPosition(moveLatLon);
  }
);
</script>

<style scoped>
.kakao-map {
  width: 100%;    /* 너비는 부모에 맞춰서 */
  height: 200px;  /* 🌟 높이를 반드시 'px' 단위로 명시하세요! */
  background-color: #f0f0f0; /* 지도가 안 뜰 때 영역 확인용 */
}
</style>