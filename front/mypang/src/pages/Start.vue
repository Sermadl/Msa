<template>
  <div class="h-screen flex flex-col justify-center items-center bg-gray-100">
    <h1 class="text-center">마이팡</h1>
    <MainLoginButton></MainLoginButton>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from "vue-router";
import MainLoginButton from '@/components/MainLoginButton.vue'

</script>
