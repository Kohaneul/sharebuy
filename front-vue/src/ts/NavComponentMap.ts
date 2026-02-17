import LocationInfo from '@/components/nav/LocationInfo.vue'
import SearchForm from '@/components/nav/SearchForm.vue'
import Alarm from '@/components/nav/Alarm.vue'
import Menu from '@/components/nav/Menu.vue'
import { TopNavComponent } from './TopNavComponent'
import type { Component } from 'vue'


export const NavComponentMap: Record<TopNavComponent, Component> = {
  [TopNavComponent.LOCATION_INFO]: LocationInfo,
  [TopNavComponent.SEARCH_FORM]: SearchForm,
  [TopNavComponent.ALARM]: Alarm,
  [TopNavComponent.MENU]: Menu
}
