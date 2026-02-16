<template>
  <a-layout-header class="logo-header">
    <img src="/main-logo.png" class="logo" @click="onLogoClick" />
  </a-layout-header>
</template>

<script setup lang="ts">

const emit = defineEmits(['logoClick'])
function onLogoClick() { emit('logoClick') }

</script>
<style scoped>
.logo-header {
  height: 48px;
  background: #1f3b57;
  display: flex;
  align-items: center;
  padding-left: 16px;
}
.logo {
  height: 32px;
}
</style>
