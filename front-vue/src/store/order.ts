import { defineStore } from 'pinia'

export interface OrderItem {
id: string
productName: string
quantity: number
price: number
}

export const useOrderStore = defineStore('order', {
state: () => ({
    orders: [] as OrderItem[],
  }),
  getters: {
    totalAmount: (state) => state.orders.reduce((sum, item) => sum + item.price * item.quantity, 0),
  },
  actions: {
    addOrder(order: OrderItem) {
      this.orders.push(order)
    },
    removeOrder(id: string) {
      this.orders = this.orders.filter(item => item.id !== id)
    },
    fetchOrdersFromAPI(data: OrderItem[]) {
      this.orders = data
    },
  },
})
