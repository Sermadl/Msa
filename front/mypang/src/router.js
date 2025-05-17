import { createRouter, createWebHistory } from "vue-router";
import Start from "./pages/Start.vue";
import Unknown from "./pages/Unknown.vue";
import Home from "./pages/Home.vue";
import Login from "./pages/Login.vue";
import Register from "./pages/Register.vue";
import ItemDetail from "./pages/ItemDetail.vue";
import MyCart from "./pages/MyCart.vue";
import MyPage from "./pages/MyPage.vue";
import MyOrder from "./pages/MyOrder.vue";

const routes = [
  { path: "/", component: Home },
  { path: "/start", component: Start },
  { path: "/login", component: Login, meta: { hideHeader: true } },
  { path: "/register", component: Register, meta: { hideHeader: true } },
  { path: "/item/:itemId", component: ItemDetail },
  { path: "/my-cart", component: MyCart },
  { path: "/my-page", component: MyPage },
  {  path: "/order", component: MyOrder },
  { path: "/:pathMatch(.*)*", component: Unknown },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return { top: 0 };
  },
});

export default router;
