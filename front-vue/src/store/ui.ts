import { defineStore } from 'pinia'
import { TopNavItemMeta, TopNavComponent } from '@/ts/TopNavItemMeta'

export const useUiStore = defineStore('ui', {
  state: () => ({
    // 시스템 초기값
    initialTopNav: [
      { component: TopNavComponent.LOCATION_INFO, position: 'LEFT', value: '위치 파악 중...' },
      { component: TopNavComponent.SEARCH_FORM, position: 'CENTER', value: null },
      { component: TopNavComponent.MENU, position: 'RIGHT', value: null }
    ] as TopNavItemMeta[],

    navCache: {} as Record<string, TopNavItemMeta[]>
  }),

  getters: {
    /**
     * 🌟 파라미터 없이 호출하는 기본 네비게이션 Getter
     * /board 캐시가 있으면 그걸 주고, 없으면 시스템 초기값을 줍니다.
     */
    getDefaultNav: (state): TopNavItemMeta[] => {
      return state.navCache['/board'] || state.initialTopNav;
    },

    /**
     * 특정 경로의 데이터를 가져올 때 사용 (기존 로직)
     */
    getCurrentNav: (state) => (path: string): TopNavItemMeta[] => {
      // 캐시가 없으면 위에서 만든 getDefaultNav를 활용합니다.
      return state.navCache[path] || (state as any).getDefaultNav;
    }
  },

  actions: {
    setNavMeta(path: string, meta: TopNavItemMeta[]) {
      this.navCache[path] = meta;
    },
    clearUiCache() {
      this.navCache = {};
    }
  }
});