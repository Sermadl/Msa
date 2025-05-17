<template>
  <header>
    <!-- 첫 번째 네비게이션 바 -->
    <nav class="border-b text-sm">
      <div class="container mx-auto px-4 py-1 flex justify-end gap-4">
        <div class="flex" v-if="authStore.accessToken">
          <p class="font-medium my-text-primary">
            {{ authStore.getName() }}
          </p>
          님
        </div>
        <button
          class="hover:my-text-primary"
          @click="
            authStore.accessToken
              ? authStore.clearAuth()
              : $router.push('/login')
          "
        >
          {{ authStore.accessToken ? "로그아웃" : "로그인" }}
        </button>
        <button
          v-if="!authStore.accessToken"
          class="hover:my-text-primary"
          @click="$router.push('/register')"
        >
          회원가입
        </button>
        <button class="hover:my-text-primary" @click="showSellerModal = true">
          판매자 가입
        </button>
      </div>
    </nav>

    <!-- 두 번째 네비게이션 바 -->
    <nav class="border-b">
      <div
        class="container mx-auto px-4 py-3 flex justify-between items-center"
      >
        <!-- 왼쪽: 햄버거 메뉴와 로고 -->
        <div class="flex items-center gap-3">
          <button @click="$emit('openMenu')" class="p-1">
            <Menu class="h-6 w-6" />
          </button>
          <span
            class="flex font-bold text-xl my-text-primary gap-1"
            @click="$router.push('/')"
          >
            <img src="/my-pang.png" alt="Logo" class="h-7 w-7" />
            마이팡</span
          >
        </div>

        <!-- 오른쪽: 장바구니 및 마이페이지 아이콘 -->
        <div class="flex items-center gap-4">
          <button class="relative" @click="$router.push('/my-cart')">
            <ShoppingCart class="h-6 w-6" />
            <span
              class="absolute -top-2 -right-2 my-bg-primary text-white rounded-full text-[11px] w-5 h-4 items-center justify-center"
            >
              0
            </span>
          </button>
          <button @click="$router.push('/my-page')">
            <User class="h-6 w-6" />
          </button>
        </div>
      </div>
    </nav>
    <div
      v-if="showSellerModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-white rounded-lg shadow-xl p-6 max-w-sm w-full">
        <div class="text-center">
          <h3 class="text-lg font-medium mb-2 my-text-primary">안내</h3>
          <p class="mb-4">개발중입니다 🐇</p>
          <button
            @click="showSellerModal = false"
            class="px-4 py-2 my-bg-primary text-white rounded-md hover:my-bg-primary-hover transition-colors"
          >
            확인
          </button>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from "vue";
import { Menu, ShoppingCart, User } from "lucide-vue-next";
import { useAuthStore } from "@/scripts/store-auth";

const authStore = useAuthStore();
const showSellerModal = ref(false);
defineEmits(["openMenu"]);
</script>
