import Card from '@/components/page/CardList.vue'
import Button from '@/components/page/Button.vue'
import SearchForm from '@/components/page/SearchForm.vue'
import InputGroup from '@/components/page/InputGroup.vue'
import Input from '@/components/page/Input.vue'
import Text from '@/components/page/Text.vue'
import Image from '@/components/page/Image.vue'
import Grid from '@/components/page/Grid.vue'
import { PageComponent } from './PageComponent'
import type { Component } from 'vue'
import Participants from '@/components/page/Participants.vue'



export const PageComponentMap: Record<PageComponent, Component> = {
  [PageComponent.CARD]: Card,
  [PageComponent.BUTTON]: Button,
  // [PageComponent.SEARCH_FORM]: SearchForm,
  [PageComponent.INPUT]: Input,
  [PageComponent.TEXT]: Text,
  [PageComponent.IMAGE]: Image,
  [PageComponent.PARTICIPANTS]: Participants,
  [PageComponent.INPUT_GROUP]:InputGroup

  // [PageComponent.GRID]: Grid
}
