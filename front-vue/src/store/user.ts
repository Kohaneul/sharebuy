import { defineStore } from 'pinia'
import { ROLES, type RoleType } from '@/ts/UserType';

interface UserState {
  loginId: string;
  roleType: RoleType;
  latitude: number | null;
  longitude: number | null;
}
export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    loginId: 'guest',
    roleType: ROLES.GUEST,
    latitude: null,
    longitude: null
  }),

  actions: {
    setUserInfo(data: { loginId: string; roleType: RoleType; latitude: number; longitude: number }) {
      this.loginId = data.loginId;
      this.roleType = data.roleType;
      this.latitude = data.latitude;
      this.longitude = data.longitude;
    },

    setAuthority(permissionMeta: any) {
      this.loginId = permissionMeta.loginId || 'guest';
      this.roleType = permissionMeta.roleType || ROLES.GUEST;
    },

    logout() {
      this.loginId = 'guest'
      this.roleType = ROLES.GUEST
      this.latitude = null
      this.longitude = null
    }
  },

  getters: {
    isLoggedIn: (state) => state.roleType !== ROLES.GUEST,   
    isAdmin: (state) => state.roleType === ROLES.ADMIN       
  }
})