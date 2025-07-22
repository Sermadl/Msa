<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import apiCall from "@/scripts/api-call";

const route = useRoute();

const item = ref(null);
const loading = ref(true);
const error = ref(null);

const fetchItem = async () => {
  try {
    const itemId = route.params.itemId;
    const res = await apiCall.get(`/api/item/${itemId}`);
    console.log(itemId);
    if (res.result === apiCall.Response.SUCCESS) {
      item.value = res.data;
      console.log(item.value);
    } else {
      error.value = "상품 정보를 불러오지 못했습니다.";
    }
  } catch (e) {
    error.value = "서버 오류가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const addToCart = async () => {
  try {
    const accessToken = localStorage.getItem("accessToken");
    console.log(accessToken)

    const res = await apiCall.post(
      "/api/order/cart",
      {
          Authorization: `Bearer ${accessToken}`
      },
      {
        itemId: item.value.id,
        quantity: 1
      }
    );

    if (res.result === apiCall.Response.SUCCESS) {
      alert("장바구니에 추가되었습니다!");
    } else {
      alert("장바구니 추가에 실패했습니다.");
    }
  } catch (e) {
    alert("서버 오류가 발생했습니다.");
  }
};

onMounted(() => {
  fetchItem();
});

const formatPrice = (price) => {
  return new Intl.NumberFormat("ko-KR").format(price) + "원";
};
</script>
<template>
  <div class="min-h-screen bg-gray-50 pt-10">
    <div class="max-w-6xl mx-auto px-6 py-10 bg-white rounded-lg shadow-md">
      <div v-if="loading" class="flex justify-center items-center py-10">
        <div
          class="animate-spin rounded-full h-10 w-10 border-4 border-t-transparent my-border-accent"
        ></div>
      </div>

      <!-- 에러 상태 -->
      <div v-else-if="error" class="bg-red-100 text-red-700 p-4 rounded-md">
        {{ error }}
      </div>

      <!-- 상품이 없는 경우 -->
      <div
        v-else-if="item.length === 0"
        class="text-center py-10 text-gray-500"
      >
        상품이 없습니다.
      </div>
      <div v-else class="grid grid-cols-1 md:grid-cols-2 gap-10 items-start">
        <!-- 이미지 영역 -->
        <div
          class="w-full bg-gray-100 aspect-square flex items-center justify-center rounded-md"
        >
          <span class="text-gray-400">상품 이미지</span>
        </div>

        <!-- 상세 정보 -->
        <div class="space-y-6">
          <h1 class="text-3xl font-bold my-text-secondary">{{ item.name }}</h1>
          <p class="text-gray-600">{{ item.description }}</p>

          <!-- 가격 -->
          <div class="text-2xl font-bold">
            {{ formatPrice(item.price) }}
          </div>

          <!-- 수량 / 재고 -->
          <div class="text-sm text-gray-500">
            재고 수량: {{ item.quantity }}개
          </div>

          <!-- 옵션 및 버튼 -->
          <div class="mt-6 flex flex-col sm:flex-row items-stretch gap-4">
            <button
              class="w-full sm:w-auto px-6 py-3 rounded-md border my-border text-gray-700 hover:my-text-primary"
              @click="addToCart"
            >
              장바구니 담기
            </button>
            <button
              class="w-full sm:w-auto px-6 py-3 rounded-md my-bg-primary hover:my-bg-primary-hover text-white font-semibold"
            >
              바로 구매
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
