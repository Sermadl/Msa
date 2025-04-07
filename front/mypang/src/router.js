import { createRouter, createWebHistory } from "vue-router";
import Start from "./pages/Start.vue";
import Unknown from "./pages/Unknown.vue";
import Home from "./pages/Home.vue";
import Login from "./pages/Login.vue";
import Register from "./pages/Register.vue";

const routes = [
  { path: "/", component: Home },
  { path: "/start", component: Start },
  { path: "/login", component: Login },
  { path: "/register", component: Register },
  { path: "/:pathMatch(.*)*", component: Unknown }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
