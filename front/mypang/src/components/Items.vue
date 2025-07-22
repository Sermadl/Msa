<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import apiCall from '@/scripts/api-call';

const router = useRouter();

// 상품 목록을 저장할 반응형 변수
const items = ref([]);
const loading = ref(false);
const error = ref(null);

// API에서 상품 목록을 가져오는 함수
const fetchItems = async () => {
  loading.value = true;
  
  const url = '/api/item/list';
  try {
    const result = await apiCall.get(url);
    
    if (result.result === apiCall.Response.SUCCESS) {
      items.value = result.data;
    } else {
      console.error('상품 목록을 가져오는 중 오류 발생:', result.status);
      error.value = '상품 목록을 불러오는데 실패했습니다.';
    }
  } catch (err) {
    console.error('API 호출 중 오류 발생:', err);
    error.value = '서버와 통신 중 오류가 발생했습니다.';
  } finally {
    loading.value = false;
  }
};

// 가격 포맷 함수
const formatPrice = (price) => {
  return new Intl.NumberFormat('ko-KR').format(price) + '원';
};

const goToDetail = (itemId) => {
    router.push(`/item/${itemId}`)
}

// 컴포넌트가 마운트될 때 상품 목록 가져오기
onMounted(() => {
  fetchItems();
});
</script>

<template>
  <div class="container mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6 my-text-primary">상품 목록</h1>
    
    <!-- 로딩 상태 -->
    <div v-if="loading" class="flex justify-center items-center py-10">
      <div class="animate-spin rounded-full h-10 w-10 border-4 border-t-transparent my-border-accent"></div>
    </div>
    
    <!-- 에러 상태 -->
    <div v-else-if="error" class="bg-red-100 text-red-700 p-4 rounded-md">
      {{ error }}
    </div>
    
    <!-- 상품이 없는 경우 -->
    <div v-else-if="items.length === 0" class="text-center py-10 text-gray-500">
      상품이 없습니다.
    </div>
    
    <!-- 상품 목록 -->
    <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      <div 
        v-for="item in items" 
        :key="item.id" 
        class="bg-white rounded-lg shadow-md overflow-hidden border my-border hover:shadow-lg transition-shadow"
        @click="goToDetail(item.id)"
      >
        <!-- 상품 이미지 대체 영역 -->
        <div class="h-48 bg-gray-200 flex items-center justify-center">
          <span class="text-gray-400">상품 이미지</span>
        </div>
        
        <!-- 상품 정보 -->
        <div class="p-4">
          <h2 class="text-lg font-semibold my-text-dark">{{ item.name }}</h2>
          <div class="mt-4 flex justify-between items-center">
            <span class="text-lg font-bold">{{ formatPrice(item.price) }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>