import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  server: {
    proxy: {
      '/api': {
        // target: 'https://sk067-mypang.skala25a.project.skala-ai.com', // 백엔드 API 주소
        target: 'http://localhost:19901', // 백엔드 API 주소
        changeOrigin: true,
        rewrite: path => path.replace(/^\/api/, ''),
      },
    },
    cors: false
  }
});
