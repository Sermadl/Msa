<script setup>
import { ref, onMounted } from "vue";
import apiCall from "@/scripts/api-call";

const items = ref([]);
const loading = ref(true);
const error = ref(null);

const fetchMyPage = async () => {
  try {
    const accessToken = localStorage.getItem("accessToken");
    const res = await apiCall.get("/api/my-page", {
      Authorization: `Bearer ${accessToken}`,
    });

    if (res.result === apiCall.Response.SUCCESS) {
      items.value = res.data;
    } else {
      error.value = res.message || "내 상품을 불러오지 못했습니다.";
    }
  } catch (e) {
    error.value = "서버 오류가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const formatPrice = (price) => {
  return new Intl.NumberFormat("ko-KR").format(price) + "원";
};

onMounted(() => {
  fetchMyPage();
});
</script>

<style scoped>
.my-text-primary {
  @apply text-indigo-600;
}
</style>
<template>
  <div class="min-h-screen bg-gray-50 pt-10">
    <div class="max-w-5xl mx-auto px-4 py-8 bg-white rounded-lg shadow-md">
      <h1 class="text-2xl font-bold mb-6 my-text-primary">최근 구매한 상품</h1>

      <div v-if="loading" class="text-center text-gray-500">불러오는 중...</div>
      <div v-else-if="error" class="text-center text-red-500">{{ error }}</div>
      <div v-else-if="items.length === 0" class="text-center text-gray-400">
        등록된 상품이 없습니다.
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div
          v-for="item in items"
          :key="item.id"
          class="border rounded-lg p-5 shadow-sm hover:shadow-md transition"
        >
          <h2 class="text-lg font-semibold text-gray-800">{{ item.name }}</h2>
          <p class="text-sm text-gray-500 mb-2">
            {{ item.description || "설명이 없습니다." }}
          </p>
          <p class="text-sm text-gray-600">재고: {{ item.quantity }}개</p>
          <p class="text-base font-bold my-text-primary mt-2">
            {{ formatPrice(item.price) }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
