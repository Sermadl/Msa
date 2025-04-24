import { defineStore } from 'pinia';
import { ref, watch } from 'vue';

export const useAuthStore = defineStore('auth', () => {
  const accessToken = ref(localStorage.getItem('accessToken') || '');
  const refreshToken = ref(localStorage.getItem('refreshToken') || '');
  const role = ref(localStorage.getItem('role') || '');
  const username = ref(localStorage.getItem('username') || '');

  const setAuth = (data) => {
    accessToken.value = data.accessToken;
    refreshToken.value = data.refreshToken;
    role.value = data.role;
    username.value = data.username;

    localStorage.setItem('accessToken', accessToken.value)
    // localStorage.setItem('refreshToken', val)
    localStorage.setItem('role', role.value)
    localStorage.setItem('username', username.value)
  };

  const clearAuth = () => {
    accessToken.value = '';
    refreshToken.value = '';
    role.value = '';
    username.value = '';

    localStorage.removeItem('accessToken');
    // localStorage.removeItem('refreshToken');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
  };

  const getAuth = () => {
    return {
      accessToken: accessToken.value,
      refreshToken: refreshToken.value,
      role: role.value,
      username: username.value
    };
  };

  const getName = () => {
    return username.value
  }

  // Todo: refreshToken은 https로 통신할 때 저장
  // watch(refreshToken, (val) => localStorage.setItem('refreshToken', val));

  return {
    accessToken,
    refreshToken,
    role,
    username,
    setAuth,
    clearAuth,
    getAuth,
    getName,
  };
});
