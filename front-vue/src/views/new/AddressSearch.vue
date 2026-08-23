<template>
  <a-button size="small" @click="openAddressSearch" class="button-style">
    주소 찾기
  </a-button>
</template>

<script setup lang="ts">

interface AddressResult {
  address: string;
  zonecode: string;
}

const emit = defineEmits<{
  select: [address: AddressResult];
}>();

const openAddressSearch = () => {
  new window.daum.Postcode({
    oncomplete: (data: any) => {
      emit('select', {
        address: data.roadAddress || data.jibunAddress,
        zonecode: data.zonecode
      });
    }
  }).open();
};

</script>
<style scoped>
.button-style{
    margin-left:10px;
}
</style>