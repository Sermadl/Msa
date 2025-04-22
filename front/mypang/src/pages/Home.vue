<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import apiCall from "@/scripts/api-call";
import { useAuthStore } from "@/scripts/store-auth";
import Header from "@/components/Header.vue";
import SideMenu from "@/components/SideMenu.vue";

const authStore = useAuthStore();

// 메뉴 상태 관리
const menuOpen = ref(false);
const categories = ref([]);
const searchQuery = ref("");

// 메뉴 열기
const openMenu = () => {
  menuOpen.value = true;
  // 메뉴가 열릴 때 body 스크롤 방지
  document.body.style.overflow = "hidden";
};

// 메뉴 닫기
const closeMenu = () => {
  menuOpen.value = false;
  // 메뉴가 닫힐 때 body 스크롤 허용
  document.body.style.overflow = "";
};

// 카테고리 데이터 가져오기
const fetchCategories = async () => {
  try {
    const result = await apiCall.get("/api/item/category", null);
    if (result.result === apiCall.Response.SUCCESS) {
      categories.value = result.data;
    }
  } catch (error) {
    console.error("카테고리를 불러오는 데 실패했습니다:", error);
  }
};

// 검색 기능
const handleSearch = () => {
  if (searchQuery.value.trim()) {
    console.log("검색어:", searchQuery.value);
    // 여기에 검색 API 호출 로직 추가
  }
};

// ESC 키로 메뉴 닫기
const handleKeyDown = (event) => {
  if (event.key === "Escape" && menuOpen.value) {
    closeMenu();
  }
};

onMounted(() => {
  fetchCategories();
  window.addEventListener("keydown", handleKeyDown);
});

onBeforeUnmount(() => {
  window.removeEventListener("keydown", handleKeyDown);
});
</script>

<template>
  <div class="min-h-screen bg-gray-50">
    <!-- 헤더 컴포넌트 -->
    <Header @open-menu="openMenu" />

    <!-- 사이드 메뉴 컴포넌트 -->
    <div
      class="fixed inset-0 z-20 bg-black bg-opacity-50 transition-opacity duration-300"
      :class="menuOpen ? 'opacity-100' : 'opacity-0 pointer-events-none'"
      @click="closeMenu"
    ></div>

    <Transition
      enter-active-class="transition-transform duration-300 ease-out"
      leave-active-class="transition-transform duration-300 ease-in"
      enter-from-class="-translate-x-full"
      enter-to-class="translate-x-0"
      leave-from-class="translate-x-0"
      leave-to-class="-translate-x-full"
    >
      <SideMenu
        v-if="menuOpen"
        @close-menu="closeMenu"
        :categories="categories"
        class="fixed top-0 left-0 z-30 h-full shadow-xl"
      />
    </Transition>

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
  </div>
</template>

<style scoped>
body {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    "Helvetica Neue", Arial, sans-serif;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

/* 드롭다운 스타일 */
button.dropdown-toggle::after {
  display: none !important;
}

.dropdown-submenu {
  position: relative;
}

.dropdown-submenu .submenu {
  position: absolute;
  top: 0;
  left: 100%;
  min-width: 160px;
  display: none;
  z-index: 1000;
}

.dropdown-submenu:hover .submenu {
  display: block;
}
</style>
