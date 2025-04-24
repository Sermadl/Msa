<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/scripts/store-auth";
import apiCall from "@/scripts/api-call";

const email = ref("");
const password = ref("");
const router = useRouter();

const login = async () => {
  if (!email.value || !password.value) {
    alert("이메일과 비밀번호를 입력해주세요.");
    return;
  }

  const requestBody = {
    email: email.value,
    password: password.value,
  };

  const authStore = useAuthStore();

  const url = "/api/user/login";
  const result = await apiCall.post(url, null, requestBody);

  if (result.result === apiCall.Response.SUCCESS) {
    alert("로그인 성공");

    authStore.setAuth(result.data);

    router.push("/");
  } else {
    console.log(result.status);

    if (result.status < 500) {
      alert("이메일 또는 비밀번호가 잘못되었습니다.");
    } else {
      alert("로그인에 실패했습니다.");
    }
  }
};

const emailError = computed(() => {
  if (!email.value) return '';
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email.value) ? '' : '올바른 이메일 형식을 입력해주세요.';
});
</script>

<template>
  <div class="flex justify-center items-center">
    <div class="mt-20 bg-white rounded-lg shadow-md p-6 w-80">
      <h3 class="text-xl font-semibold text-center mb-4 my-text-dark">로그인</h3>
      <div class="mb-4">
        <input
          v-model="email"
          type="email"
          class="w-full px-3 py-2 border my-border rounded-md 
          focus:outline-none focus:my-ring focus:ring-2 my-placeholder"
          placeholder="이메일"
        />
        <div class="h-5">
          <small class="text-red-500 text-sm" v-if="emailError">{{ emailError }}</small>
        </div>
      </div>
      <div class="mb-4">
        <input
          v-model="password"
          type="password"
          class="w-full px-3 py-2 border my-border rounded-md 
          focus:outline-none focus:my-ring focus:ring-2 my-placeholder"
          placeholder="비밀번호"
        />
      </div>
      <button 
        class="w-full my-bg-primary hover:my-bg-primary-hover text-white font-medium py-2 px-4 rounded-md transition-colors" 
        @click="login"
      >
        로그인
      </button>
    </div>
  </div>
</template>