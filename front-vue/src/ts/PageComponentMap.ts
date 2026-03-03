import Card from '@/components/page/CardList.vue'
import SearchForm from '@/components/page/SearchForm.vue'
import Input from '@/components/page/Input.vue'
import Grid from '@/components/page/Grid.vue'
import { PageComponent } from './PageComponent'
import type { Component } from 'vue'


export const PageComponentMap: Record<PageComponent, Component> = {
  [PageComponent.CARD]: Card,
  // [PageComponent.SEARCH_FORM]: SearchForm,
  // [PageComponent.INPUT]: Input,
  // [PageComponent.GRID]: Grid
}
