<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import apiCall from "@/scripts/api-call";
const username = ref("");
const password = ref("");
const email = ref("");
const phone = ref("");
const router = useRouter();
const register = async () => {
  if (!username.value || !password.value || !email.value || !phone.value) {
    alert("모든 필드를 입력해주세요.");
    return;
  }
  const requestBody = {
    username: username.value,
    password: password.value,
    email: email.value,
    phone: phone.value,
  };
  const url = "/api/user/register";
  const result = await apiCall.post(url, null, requestBody);
  if (result.result === apiCall.Response.SUCCESS) {
    alert("회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.");
    router.push("/login");
  } else {
    console.log(result.status);
    if (result.status === 409) {
      alert("이미 사용 중인 아이디 또는 이메일입니다.");
    } else if (result.status < 500) {
      alert("입력 정보를 확인해주세요.");
    } else {
      alert("회원가입에 실패했습니다. 잠시 후 다시 시도해주세요.");
    }
  }
};
const emailError = computed(() => {
  if (!email.value) return "";
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email.value)
    ? ""
    : "올바른 이메일 형식을 입력해주세요.";
});
const phoneError = computed(() => {
  if (!phone.value) return "";
  const phoneRegex = /^[0-9]{10,11}$/;
  return phoneRegex.test(phone.value)
    ? ""
    : "올바른 전화번호 형식을 입력해주세요.";
});
// const passwordError = computed(() => {
//   if (!password.value) return "";
//   return password.value.length >= 8 ? "" : "비밀번호는 8자 이상이어야 합니다.";
// });
</script>
<template>
  <div class="flex justify-center items-center">
    <div class="mt-20 bg-white rounded-lg shadow-md p-6 w-96">
      <h3 class="text-xl font-semibold text-center mb-4 my-text-dark">
        회원가입
      </h3>
      <div class="mb-4">
        <input
          v-model="username"
          type="text"
          class="w-full px-3 py-2 border my-border rounded-md focus:outline-none focus:my-ring focus:ring-2 my-placeholder"
          placeholder="사용자 이름"
        />
      </div>

      <div class="mb-4">
        <input
          v-model="email"
          type="email"
          class="w-full px-3 py-2 border my-border rounded-md focus:outline-none focus:my-ring focus:ring-2 my-placeholder"
          placeholder="이메일"
        />
        <small class="text-red-500 text-sm" v-if="emailError">{{
          emailError
        }}</small>
      </div>

      <div class="mb-4">
        <input
          v-model="password"
          type="password"
          class="w-full px-3 py-2 border my-border rounded-md focus:outline-none focus:my-ring focus:ring-2 my-placeholder"
          placeholder="비밀번호"
        />
        <small class="text-red-500 text-sm" v-if="passwordError">{{
          passwordError
        }}</small>
      </div>

      <div class="mb-4">
        <input
          v-model="phone"
          type="tel"
          class="w-full px-3 py-2 border my-border rounded-md focus:outline-none focus:my-ring focus:ring-2 my-placeholder"
          placeholder="전화번호 (- 없이 입력)"
        />
        <small class="text-red-500 text-sm" v-if="phoneError">{{
          phoneError
        }}</small>
      </div>

      <button
        class="w-full my-bg-primary hover:my-bg-primary-hover text-white font-medium py-2 px-4 rounded-md transition-colors"
        @click="register"
      >
        회원가입
      </button>

      <div class="mt-4 text-center">
        <p class="text-sm text-gray-600">
          이미 계정이 있으신가요?
          <router-link to="/login" class="text-blue-500 hover:underline"
            >로그인</router-link
          >
        </p>
      </div>
    </div>
  </div>
</template>
