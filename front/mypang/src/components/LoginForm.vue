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
  <div class="d-flex justify-content-center align-items-center">
    <div class="card p-4" style="min-width: 300px">
      <h3 class="text-center mb-3">로그인</h3>
      <div class="mb-3">
        <input
          v-model="email"
          type="email"
          class="form-control"
          placeholder="이메일"
        />
        <small class="text-danger" v-if="emailError">{{ emailError }}</small>
      </div>
      <div class="mb-3">
        <input
          v-model="password"
          type="password"
          class="form-control"
          placeholder="비밀번호"
        />
      </div>
      <button class="btn btn-primary w-100" @click="login">로그인</button>
    </div>
  </div>
</template>
