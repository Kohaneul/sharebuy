<template>
  <div>
    <h2>게시판 테스트</h2>
    <pre>{{ boardData }}</pre>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

const boardData = ref({})

onMounted(async () => {
const res = await axios.get('http://localhost:9011/board')
  boardData.value = res.data

})
</script>
