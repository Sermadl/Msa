<script setup>
import { ref, onMounted, computed } from "vue";
import apiCall from "@/scripts/api-call";
import { useRouter } from "vue-router";

const router = useRouter();
const address = ref("");
const description = ref("");
const cartItems = ref([]);
const itemList = ref([]);
const loading = ref(true);

const fetchCartItems = async () => {
  try {
    const accessToken = localStorage.getItem("accessToken");

    const res = await apiCall.get("/api/my-cart", {
      Authorization: `Bearer ${accessToken}`,
    });

    if (res.result === apiCall.Response.SUCCESS) {
      cartItems.value = res.data.filter(item => item.selected);

      // itemId마다 상품 정보 조회
      const detailedItems = await Promise.all(
        cartItems.value.map(async (cartItem) => {
          const res = await apiCall.get(`/api/item/${cartItem.itemId}`, {
            Authorization: `Bearer ${accessToken}`,
          });

          if (res.result === apiCall.Response.SUCCESS) {
            const item = res.data;
            return {
              itemId: item.id,
              sellerId: item.sellerId,
              name: item.name,
              price: item.price,
              quantity: cartItem.quantity
            };
          } else {
            return null;
          }
        })
      );

      itemList.value = detailedItems.filter(Boolean);
    } else {
      alert("장바구니 정보를 불러오지 못했습니다.");
    }
  } catch (e) {
    alert("서버 오류 발생");
  } finally {
    loading.value = false;
  }
};

const totalPrice = computed(() =>
  itemList.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
);

const submitting = ref(false);
const submitOrder = async () => {
  submitting.value = true;

  const body = {
    address: address.value,
    description: description.value,
    totalPrice: totalPrice.value,
    itemList: itemList.value
  };

  try {
    const accessToken = localStorage.getItem("accessToken");
    const res = await apiCall.post("/api/order/register", {
      Authorization: `Bearer ${accessToken}`
    }, body);

    if (res.result === apiCall.Response.SUCCESS) {
      alert("주문이 완료되었습니다!");
      console.log("응답 결과:", res);
      await router.push("/");
    } else {
      alert(res.message || "주문에 실패했습니다.");
    }
  } catch (err) {
    alert("서버 오류가 발생했습니다.");
  } finally {
    submitting.value = false;
  }
};

onMounted(() => {
  fetchCartItems();
});
</script>
<template>
    <div class="max-w-3xl mx-auto px-4 py-10">
      <h1 class="text-2xl font-bold mb-6">주문하기</h1>
  
      <div v-if="loading">장바구니 정보를 불러오는 중입니다...</div>
  
      <div v-else>
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-1">배송지</label>
          <input
            v-model="address"
            type="text"
            class="w-full border rounded px-3 py-2"
            placeholder="배송 받을 주소를 입력하세요"
          />
        </div>
  
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-1">요청사항</label>
          <textarea
            v-model="description"
            rows="3"
            class="w-full border rounded px-3 py-2"
            placeholder="배송 요청사항을 입력하세요 (선택)"
          ></textarea>
        </div>
  
        <div class="mb-6">
          <h2 class="text-lg font-semibold mb-2">주문 상품</h2>
          <ul class="divide-y border rounded-md">
            <li
              v-for="item in itemList"
              :key="item.itemId"
              class="p-4 flex justify-between items-center"
            >
              <div>
                <p class="text-gray-800 font-medium">{{ item.name }}</p>
                <p class="text-sm text-gray-500">수량: {{ item.quantity }}</p>
              </div>
              <p class="text-right font-semibold text-gray-800">
                {{ new Intl.NumberFormat('ko-KR').format(item.price * item.quantity) }}원
              </p>
            </li>
          </ul>
        </div>
  
        <div class="text-right text-xl font-bold mb-6">
          총 결제금액: {{ new Intl.NumberFormat("ko-KR").format(totalPrice) }}원
        </div>
  
        <button
          @click="submitOrder"
          :disabled="submitting"
          class="w-full bg-indigo-600 text-white py-3 rounded hover:bg-indigo-700 disabled:opacity-50"
        >
          {{ submitting ? "주문 처리 중..." : "주문하기" }}
        </button>
      </div>
    </div>
  </template>
  