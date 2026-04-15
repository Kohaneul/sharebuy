import { defineStore } from 'pinia'

export const useLocationStore = defineStore('location', {
  state: () => ({
    latitude: null as number | null,
    longitude: null as number | null
  }),
  actions: {
    setLocation(lat: number, lng: number) {
      this.latitude = lat
      this.longitude = lng
    }
  }
})