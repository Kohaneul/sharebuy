import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    username: '',
    loginId: '',
    roleType: '',
    token: ''
  }),
  actions: {
    setAuthority(permissionMeta: any) {
      this.loginId = permissionMeta.loginId;
      this.roleType = permissionMeta.roleType;
    },
    login(name: string, token: string) {
      this.username = name
      this.token = token
    },
    logout() {
      this.loginId = ''
      this.roleType = 'GUEST'
      this.username = ''
      this.token = ''
    }
  }
})
