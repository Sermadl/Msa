import { createRouter, createWebHistory } from "vue-router";
import Start from "./pages/Start.vue";
import Unknown from "./pages/Unknown.vue";

const routes = [
  { path: "/", component: Start },
  { path: "/:pathMatch(.*)*", component: Unknown }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
