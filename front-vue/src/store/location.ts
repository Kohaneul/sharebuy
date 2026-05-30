import { defineStore } from 'pinia';
import { getCurrentLocation } from '@/utils/CurrentLocationUtil';

export const useLocationStore = defineStore('location', {
  state: () => ({
    latitude: null as number | null,
    longitude: null as number | null,
  }),

  actions: {
    // 🌟 위치 정보를 업데이트하는 통합 액션
    async syncLocation(forceRefresh = false) {
      // 이미 저장된 좌표 정보가 있다면 그대로 사용
      if (!forceRefresh && this.latitude && this.longitude) {
        return { latitude: this.latitude, longitude: this.longitude };
      }

      try {
        const pos = await getCurrentLocation();
        this.setLocation(pos.latitude, pos.longitude);
        return { latitude: pos.latitude, longitude: pos.longitude };
      } catch (error) {
        console.error("위치 획득 실패:", error);
        // 실패 시 기본값 세팅 (선택 사항)
        return { latitude: this.latitude, longitude: this.longitude };
      }
    },

    setLocation(lat: number | null, lng: number | null) {
      this.latitude = lat;
      this.longitude = lng;
    }
  }
});