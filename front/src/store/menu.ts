import { defineStore } from 'pinia'

interface MenuItem {
  name: string
  path: string
  roles: string[]  // 접근 가능한 권한
}

export const useMenuStore = defineStore('menu', {
  state: () => ({
    menuItems: [] as MenuItem[]
  }),
  actions: {
    setMenu(items: MenuItem[]) {
      this.menuItems = items
    },
    filterByRole(role: string) {
      this.menuItems = this.menuItems.filter(item => item.roles.includes(role))
    }
  }
})
