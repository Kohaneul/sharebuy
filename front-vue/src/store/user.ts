import { defineStore } from 'pinia'
import { ROLES } from '@/ts/UserType';

export const useUserStore = defineStore('user', {
  state: () => ({
    loginId: 'guest', // 기본값 guest
    roleType: ROLES.GUEST,
    // 필요하다면 위도/경도도 유저 정보의 일부로 포함 가능
    latitude: null as number | null,
    longitude: null as number | null
  }),

  actions: {

//     {
//     "loginId": "admin",
//     "latitude": 37.3721,
//     "roleType": "ADMIN",
//     "longitude": 126.9389
// }
    // 로그인 성공 시 서버 데이터를 한 번에 세팅
    setUserInfo(data: { loginId: string; roleType: string; latitude: number; longitude: number }) {
      this.loginId = data.loginId;
      this.roleType = data.roleType;
      this.latitude = data.latitude;
      this.longitude = data.longitude;
    },
    // CommonPage 등에서 권한 정보만 업데이트할 때 사용
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
  // 유효성 검사 등을 위한 getters
  getters: {
    isLoggedIn: (state) =>  state.roleType !== ROLES.GUEST,
    isAdmin: (state) => state.roleType === ROLES.ADMIN
  }
})