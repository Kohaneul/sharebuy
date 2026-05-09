export interface TopNavItemMeta {
  component: TopNavComponent
  position: 'LEFT' | 'CENTER' | 'RIGHT'
  value?: any
}



export enum TopNavComponent {
  LOCATION_INFO = 'LOCATION_INFO',
  SEARCH_FORM = 'SEARCH_FORM',
  ALARM = 'ALARM',
  MENU = 'MENU'
}