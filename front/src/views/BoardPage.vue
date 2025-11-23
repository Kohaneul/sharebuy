<template>
  <TopNavBar></TopNavBar>
  <div>
    <h2>게시판 테스트</h2>
    <pre>{{ boardData }}</pre>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import {commonGet, commonPost} from '@/utils/ShareBuyUtil';
import TopNavBar from '@/components/TopNavBar.vue';
const boardData = ref({})

onMounted(async () => {
const res = await commonGet(`/board`);
boardData.value = res;

})
</script>
