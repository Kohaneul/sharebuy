import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    username: '',
    token: ''
  }),
  actions: {
    login(name: string, token: string) {
      this.username = name
      this.token = token
    },
    logout() {
      this.username = ''
      this.token = ''
    }
  }
})
