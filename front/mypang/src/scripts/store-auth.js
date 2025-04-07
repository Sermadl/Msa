import { defineStore } from 'pinia';
import { ref, watch } from 'vue';

export const useAuthStore = defineStore('auth', () => {
  const accessToken = ref(localStorage.getItem('accessToken') || '');
  const refreshToken = ref(localStorage.getItem('refreshToken') || '');
  const role = ref(localStorage.getItem('role') || '');

  const setAuth = (data) => {
    accessToken.value = data.accessToken;
    refreshToken.value = data.refreshToken;
    role.value = data.role;

    localStorage.setItem('accessToken', accessToken.value)
    // localStorage.setItem('refreshToken', val)
    localStorage.setItem('role', role.value)
  };

  const clearAuth = () => {
    accessToken.value = '';
    refreshToken.value = '';
    role.value = '';

    localStorage.removeItem('accessToken');
    // localStorage.removeItem('refreshToken');
    localStorage.removeItem('role');
  };

  const getAuth = () => {
    return {
      accessToken: accessToken.value,
      refreshToken: refreshToken.value,
      role: role.value,
    };
  };

  // Todo: refreshToken은 https로 통신할 때 저장
  // watch(refreshToken, (val) => localStorage.setItem('refreshToken', val));

  return {
    accessToken,
    refreshToken,
    role,
    setAuth,
    clearAuth,
    getAuth,
  };
});
