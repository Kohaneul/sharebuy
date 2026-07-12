<template>
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

let staticMap: any = null;

const drawMap = () => {
  const { kakao } = window as any;

  if (!kakao?.maps || !mapContainer.value) return;

  const position = new kakao.maps.LatLng(
    props.latitude,
    props.longitude
  );

  const marker = {
    position
  };

  const options = {
    center: position,
    level: props.level ?? 3,
    marker
  };

  // 기존 내용 제거
  mapContainer.value.innerHTML = '';

  staticMap = new kakao.maps.StaticMap(
    mapContainer.value,
    options
  );
};

onMounted(() => {
  kakao.maps.load(() => {
    drawMap();
  });
});

watch(
  () => [props.latitude, props.longitude],
  () => {
    drawMap();
  }
);
</script>

<style scoped>
.kakao-map {
  width: 100%;
  height: 200px;
}
</style>