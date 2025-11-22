import { defineStore } from 'pinia'

export const useSettingsStore = defineStore('settings', {
  state: () => ({
    darkMode: false,
    language: 'ko',
    itemsPerPage: 20
  }),
  actions: {
    toggleDarkMode() {
      this.darkMode = !this.darkMode
    },
    setLanguage(lang: string) {
      this.language = lang
    },
    setItemsPerPage(count: number) {
      this.itemsPerPage = count
    }
  }
})
