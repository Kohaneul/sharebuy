import { defineStore } from 'pinia';
import { getCurrentLocation } from '@/utils/CurrentLocationUtil';
// store/location.ts
export const useLocationStore = defineStore('location', {
  state: () => ({
    latitude: null as number | null,
    longitude: null as number | null,
    updatedAt: 0, // timestamp 추가
  }),

  actions: {
    async syncLocation(forceRefresh = false) {
      const TTL = 5 * 60 * 1000; // 5분
      const isFresh = Date.now() - this.updatedAt < TTL;

      if (!forceRefresh && this.latitude && this.longitude && isFresh) {
        return { latitude: this.latitude, longitude: this.longitude };
      }

      try {
        const pos = await getCurrentLocation();
        this.setLocation(pos.latitude, pos.longitude);
        return { latitude: pos.latitude, longitude: pos.longitude };
      } catch (error) {
        console.error("위치 획득 실패:", error);
        // 실패해도 기존 캐시가 있으면 그걸 씀 (완전 실패 아님)
        return { latitude: this.latitude, longitude: this.longitude };
      }
    },

    setLocation(lat: number | null, lng: number | null) {
      this.latitude = lat;
      this.longitude = lng;
      this.updatedAt = Date.now();
    }
  },

  persist: {
    storage: sessionStorage,
    pick: ['latitude', 'longitude', 'updatedAt'],
  },
});