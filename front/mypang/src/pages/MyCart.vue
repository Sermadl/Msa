<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { Plus, Minus } from "lucide-vue-next";
import apiCall from "@/scripts/api-call";

const router = useRouter();
const cartItems = ref([]);
const loading = ref(true);
const error = ref(null);

const fetchCartItems = async () => {
  try {
    const accessToken = localStorage.getItem("accessToken");
    const res = await apiCall.get("/api/my-cart", {
      Authorization: `Bearer ${accessToken}`,
    });

    if (res.result === apiCall.Response.SUCCESS) {
      cartItems.value = res.data;
    } else {
      error.value = res.message || "장바구니 정보를 불러오지 못했습니다.";
    }
  } catch (e) {
    error.value = "서버 오류가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const updateQuantity = async (itemId, action) => {
  const accessToken = localStorage.getItem("accessToken");
  const url = `/api/order/cart/${itemId}/${action}`;
  await apiCall.post(url, { Authorization: `Bearer ${accessToken}` });
  fetchCartItems();
};

const toggleSelect = async (itemId, selected) => {
  const accessToken = localStorage.getItem("accessToken");
  await apiCall.post(`/api/order/cart/${itemId}?select=${!selected}`, {
    Authorization: `Bearer ${accessToken}`,
  });
  fetchCartItems();
};

const formatPrice = (price) => {
  return new Intl.NumberFormat("ko-KR").format(price) + "원";
};

const goToDetail = (itemId) => {
  router.push({
    name: "OrderPage",
    state: {
      itemList: selectedItems.value,
    },
  });
};

const selectedItems = computed(() =>
  cartItems.value.filter((item) => item.selected)
);
const selectedCount = computed(() => selectedItems.value.length);
const selectedTotalPrice = computed(() =>
  selectedItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
);
const isPurchaseDisabled = computed(() => selectedCount.value === 0);

const totalDiscount = computed(() =>
  selectedItems.value.reduce((sum, item) => sum + (item.discountPrice || 0), 0)
);

const deliveryFee = computed(() => {
  return selectedTotalPrice.value >= 30000 ? 0 : 3000; // 예: 3만원 이상 무료배송
});

const finalAmount = computed(
  () => selectedTotalPrice.value - totalDiscount.value + deliveryFee.value
);

const handlePurchase = () => {
  const selectedItems = cartItems.value.filter((item) => item.selected);
  if (selectedItems.length === 0) {
    alert("구매할 상품을 선택해주세요.");
    return;
  }

  // 예시: 구매 페이지로 이동
  router.push("/order");
};

onMounted(() => {
  fetchCartItems();
});
</script>
<template>
  <div class="min-h-screen bg-gray-50 pt-10 px-4">
    <div class="max-w-6xl mx-auto">
      <!-- ✅ 상단 제목 분리 -->
      <h1 class="text-2xl font-bold mb-6 my-text-primary">장바구니</h1>

      <!-- ✅ 장바구니 리스트 + 요약 카드 같이 배치 -->
      <div class="flex flex-col lg:flex-row items-start gap-8">
        <!-- 왼쪽: 장바구니 리스트 -->
        <div class="flex-1 space-y-4 w-full">
          <template v-if="loading">
            <div class="flex justify-center items-center py-10">
              <div
                class="animate-spin rounded-full h-10 w-10 border-4 border-t-transparent my-border-accent"
              ></div>
            </div>
          </template>

          <template v-else-if="error">
            <div class="text-center text-red-500">{{ error }}</div>
          </template>

          <template v-else-if="cartItems.length === 0">
            <div class="text-center text-gray-400">
              장바구니에 담긴 상품이 없습니다.
            </div>
          </template>

          <template v-else>
            <div
              v-for="item in cartItems"
              :key="item.itemId"
              class="border rounded-lg p-5 shadow-sm hover:shadow-md transition bg-white"
            >
              <div class="flex items-center justify-between gap-4">
                <div class="flex items-start gap-3">
                  <input
                    type="checkbox"
                    class="mt-1 accent-indigo-600"
                    :checked="item.selected"
                    @change="() => toggleSelect(item.itemId, item.selected)"
                  />
                  <div
                    class="w-20 h-20 bg-gray-100 flex-shrink-0 rounded overflow-hidden flex items-center justify-center"
                  >
                    <span class="text-gray-400 text-sm">이미지</span>
                  </div>
                  <div>
                    <p
                      class="font-medium text-gray-700 cursor-pointer hover:underline"
                      @click="() => goToDetail(item.itemId)"
                    >
                      {{ item.name }}
                    </p>
                    <p class="font-medium text-gray-700">
                      {{ formatPrice(item.price) }}
                    </p>
                  </div>
                </div>
                <div
                  class="flex items-center border rounded px-2 py-1 bg-white"
                >
                  <button
                    class="p-1 hover:bg-gray-100 rounded transition"
                    @click="() => updateQuantity(item.itemId, 'decrease')"
                  >
                    <Minus class="w-4 h-4 text-gray-500" />
                  </button>
                  <p class="mx-2 w-5 text-center text-sm text-gray-700">
                    {{ item.quantity }}
                  </p>
                  <button
                    class="p-1 hover:bg-gray-100 rounded transition"
                    @click="() => updateQuantity(item.itemId, 'add')"
                  >
                    <Plus class="w-4 h-4 text-gray-500" />
                  </button>
                </div>
              </div>
            </div>
          </template>
        </div>

        <!-- 오른쪽: 주문 요약 카드 (같은 부모 div 안) -->
        <div
          class="w-full lg:w-[300px] bg-white border rounded-lg shadow px-6 py-5"
        >
          <h2 class="text-lg font-bold text-gray-800 mb-4">주문 예상 금액</h2>

          <div
            class="flex justify-between items-center text-xl font-bold text-gray-800 mb-6"
          >
            <span>총 결제금액</span>
            <span>{{ formatPrice(selectedTotalPrice) }}</span>
          </div>

          <button
            :disabled="isPurchaseDisabled"
            @click="handlePurchase"
            :class="[
              'w-full py-3 rounded-md font-semibold transition',
              isPurchaseDisabled
                ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                : 'bg-indigo-600 text-white hover:bg-indigo-700',
            ]"
          >
            {{
              isPurchaseDisabled
                ? "상품을 선택해주세요"
                : `구매하기 (${selectedCount})`
            }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
