<template>
  <div class="fixed inset-0 z-50">
    <!-- 반투명 오버레이 (페이드 인/아웃 효과) -->
    <div
      class="absolute inset-0 transition-opacity duration-300 ease-in-out"
      @click="$emit('closeMenu')"
    ></div>

    <!-- 사이드 메뉴 패널 (개선된 슬라이딩 효과) -->
    <div
      class="absolute inset-y-0 left-0 bg-white overflow-auto transition-transform duration-300 ease-in-out transform translate-x-0 w-auto max-w-md rounded-r-2xl"
      style="min-width: 320px"
    >
      <div class="flex items-center p-4 border-b">
        <button
          class="p-2 rounded-full hover:bg-gray-100 transition-colors duration-200"
          @click="$emit('closeMenu')"
        >
          <X />
        </button>
      </div>

      <!-- 카테고리 목록 -->
      <div class="flex">
        <!-- 메인 카테고리 -->
        <div
          class="w-1/2 border-r overflow-y-auto"
          style="max-height: calc(100vh - 61px)"
        >
          <button
            v-for="(category, index) in categories"
            :key="index"
            type="button"
            class="w-full text-left px-4 py-3 cursor-pointer transition-colors duration-200"
            :class="{
              'bg-gray-100 border-l-4 my-border-accent':
                hoveredCategory === category.largeCategory,
              'hover:bg-gray-50': hoveredCategory !== category.largeCategory,
            }"
            @mouseenter="hoveredCategory = category.largeCategory"
            @click="findItemByLargeCategory(category.id)"
          >
            {{ category.largeCategory }}
          </button>
        </div>

        <!-- 서브 카테고리 -->
        <div
          class="w-1/2 overflow-y-auto"
          style="max-height: calc(100vh - 61px)"
        >
          <div v-if="hoveredCategory" class="animate-fadeIn">
            <div
              v-for="(subcat, idx) in getSubcategories(hoveredCategory)"
              :key="idx"
              class="px-4 py-3 cursor-pointer hover:bg-gray-100 transition-colors duration-200"
              @click="findItemByCategory(subcat.id)"
            >
              {{ subcat.smallCategory }}
            </div>
          </div>
          <div
            v-else
            class="flex items-center justify-center h-32 text-gray-400 italic"
          >
            카테고리를 선택하세요
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import apiCall from "@/scripts/api-call"
import { X } from "lucide-vue-next";

const props = defineProps({
  categories: {
    type: Array,
    default: () => [],
  },
});

defineEmits(["closeMenu"]);

// 카테고리 관련 상태
const hoveredCategory = ref(null);
// 아이템 관련 상태
const items = ref([])

// 컴포넌트 마운트 시 첫 번째 카테고리 자동 선택
onMounted(() => {
  if (props.categories.length > 0) {
    hoveredCategory.value = props.categories[0].largeCategory;
  }
});

// 카테고리 클릭 시 아이템 불러오기
const findItemByLargeCategory = async (categoryId) => {
  try {
    const res = await apiCall.get(`/api/item/list/l-category/${categoryId}`)
    items.value = res.data
    console.log('불러온 아이템:', items.value)
  } catch (err) {
    console.error('카테고리별 아이템 조회 실패:', err)
  }
}

// 카테고리 클릭 시 아이템 불러오기
const findItemByCategory = async (categoryId) => {
  try {
    const res = await apiCall.get(`/api/item/list/category/${categoryId}`)
    items.value = res.data
    console.log('불러온 아이템:', items.value)
  } catch (err) {
    console.error('카테고리별 아이템 조회 실패:', err)
  }
}

// 서브카테고리 가져오기
const getSubcategories = (categoryName) => {
  const category = props.categories.find(
    (c) => c.largeCategory === categoryName
  );
  return category && category.smallCategories ? category.smallCategories : [];
};
</script>

<style scoped>
.animate-fadeIn {
  animation: fadeIn 0.3s ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateX(10px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

/* 스크롤바 스타일 개선 */
::-webkit-scrollbar {
  width: 5px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
}

::-webkit-scrollbar-thumb {
  background: #ccc;
  border-radius: 5px;
}

::-webkit-scrollbar-thumb:hover {
  background: #aaa;
}
</style>
