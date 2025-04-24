<template>
  <div id="app" class="min-h-screen bg-gray-50">
    <!-- 로그인 페이지가 아닐 때만 Header 보여줌 -->
    <Header v-if="!$route.meta.hideHeader" @open-menu="openMenu" />

    <!-- 메뉴 배경 (딤처리) -->
    <div
      class="fixed inset-0 z-20 bg-black bg-opacity-50 transition-opacity duration-300"
      :class="menuOpen ? 'opacity-100' : 'opacity-0 pointer-events-none'"
      @click="closeMenu"
    ></div>

    <!-- 사이드 메뉴 -->
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

    <!-- 페이지 콘텐츠 -->
    <RouterView />
  </div>
</template>

<script setup>
import apiCall from "@/scripts/api-call";
import { ref, onMounted } from 'vue'
import { RouterView } from 'vue-router'
import Header from "@/components/Header.vue"
import SideMenu from "@/components/SideMenu.vue"

const menuOpen = ref(false)
const categories = ref([])

const openMenu = () => {
  menuOpen.value = true
  document.body.style.overflow = "hidden"
}

const closeMenu = () => {
  menuOpen.value = false
  document.body.style.overflow = ""
}

const fetchCategories = async () => {
  try {
    const result = await apiCall.get("/api/item/category", null)
    if (result.result === apiCall.Response.SUCCESS) {
      categories.value = result.data
    } else {
      console.error("카테고리 응답 실패", result)
    }
  } catch (error) {
    console.error("카테고리를 불러오는 데 실패했습니다:", error)
  }
}

onMounted(() => {
  fetchCategories()
})
</script>
