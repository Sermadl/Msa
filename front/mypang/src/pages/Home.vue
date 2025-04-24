<script setup>
import Items from "@/components/Items.vue"

// 검색 기능
const handleSearch = () => {
  if (searchQuery.value.trim()) {
    console.log("검색어:", searchQuery.value);
    // 여기에 검색 API 호출 로직 추가
  }
};
</script>

<template>
  <div>
    <!-- 메인 컨텐츠 -->
    <div class="flex flex-col items-center justify-center my-20">
      <h1 class="text-2xl font-bold mb-6 text-text">상품을 검색해보세요</h1>

      <!-- 수정된 검색 부분 -->
      <div class="w-full max-w-md relative flex my-5">
        <input
          type="text"
          placeholder="검색어를 입력해주세요"
          class="w-full h-12 px-4 my-border border border-border rounded-l-md focus:outline-none focus:ring-2 focus:ring-primary my-placeholder"
          v-model="searchQuery"
          @keyup.enter="handleSearch"
        />
        <button
          @click="handleSearch"
          class="h-12 min-w-[60px] my-bg-primary hover:my-bg-primary-hover text-white rounded-r-md transition-colors duration-200 flex items-center justify-center whitespace-nowrap px-4"
        >
          검색
        </button>
      </div>
    </div>
    <Items />
  </div>
</template>
