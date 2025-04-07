<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import apiCall from "@/scripts/api-call";
import { useAuthStore } from "@/scripts/store-auth";

const authStore = useAuthStore();
const categories = ref([]);
const isDropdownOpen = ref(false);

const fetchCategories = async () => {
  const result = await apiCall.get("/api/item/category", null);
  if (result.result === apiCall.Response.SUCCESS) {
    categories.value = result.data;
  }
};

const closeOnClickOutside = (event) => {
  if (!event.target.closest(".dropdown")) {
    isDropdownOpen.value = false;
  }
};

onMounted(() => {
  fetchCategories();
  document.addEventListener("click", closeOnClickOutside);
});
onBeforeUnmount(() => {
  document.removeEventListener("click", closeOnClickOutside);
});
</script>

<template>
  <div>
    <header>
      <nav class="navbar bg-body-tertiary border-bottom p-0">
        <div class="container-fluid">
          <a></a>
          <div>
            <button
              class="btn btn-sm"
              @click="
                $router.push(authStore.accessToken ? authStore.clearAuth() : '/login')
              "
            >
              {{ authStore.accessToken ? "로그아웃" : "로그인" }}
            </button>
            <button class="btn btn-sm" @click="$router.push('/register')">
              회원가입
            </button>
            <button class="btn btn-sm" @click="$router.push('/register')">
              판매자 가입
            </button>
          </div>
        </div>
      </nav>

      <!-- <nav
        style="
          width: 100%;
          display: flex;
          justify-content: space-between;
          padding: 0.5rem;
          align-items: center;
        "
      >
        <div class="d-flex align-items-center">
          <div class="dropdown">
            <button
              v-if="!showSidebar"
              class="btn dropdown-toggle me-1"
              type="button"
              data-bs-toggle="dropdown"
              @click="showSidebar = true"
              style="font-size: 1.5rem"
            >
              ☰
            </button>
            <button
              v-else
              class="btn-close dropdown-toggle m-3"
              type="button"
              data-bs-toggle="dropdown"
              aria-label="Close"
              @click="showSidebar = false"
            ></button>
            <ul v-if="showSidebar" class="dropdwon-menu p-3">
              <li>
                <div
                  v-for="(category, index) in categories"
                  :key="index"
                  class="mb-3"
                >
                  <button class="dropdown-item" type="button">
                    {{ category.largeCategory }}
                  </button>
                </div>
              </li>
            </ul>
          </div>

          <h3 class="mb-0">마이팡</h3>
        </div>
        <div>
          <button class="btn">
            <i class="bi bi-cart2 fs-4"></i>
          </button>
          <button class="btn">
            <i class="bi bi-person fs-4"></i>
          </button>
        </div>
      </nav> -->

      <nav
        class="d-flex justify-content-between align-items-center px-3 py-2 border-bottom"
        style="width: 100%"
      >
        <!-- 왼쪽: 드롭다운 메뉴 + 로고 -->
        <div class="d-flex align-items-center">
          <div class="dropdown me-1">
            <button
              class="btn dropdown-toggle"
              type="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
              @click.stop="isDropdownOpen = !isDropdownOpen"
            >
              <i v-if="!isDropdownOpen" class="bi bi-list fs-4"></i>
              <i v-else class="bi bi-x-lg fs-4"></i>
            </button>
            <ul class="dropdown-menu">
              <li
                v-for="(category, index) in categories"
                :key="index"
                class="dropdown-submenu"
              >
                <a class="dropdown-item" href="#">
                  {{ category.largeCategory }}
                </a>
                <div
                  v-if="
                    category.smallCategories && category.smallCategories.length
                  "
                  class="submenu bg-white border p-2"
                >
                  <button
                    v-for="(small, idx) in category.smallCategories"
                    :key="idx"
                    class="btn btn-sm w-100 mb-1 text-start"
                  >
                    {{ small }}
                  </button>
                </div>
              </li>
            </ul>
          </div>
          <h3 class="mb-0">마이팡</h3>
        </div>

        <!-- 오른쪽: 장바구니 / 마이페이지 -->
        <div>
          <button class="btn position-relative">
            <i class="bi bi-cart2 fs-4"></i>
            <span
              class="badge rounded-pill bg-primary top-50 start-100 position-absolute translate-middle"
            >
              0
            </span>
          </button>
          <button class="btn">
            <i class="bi bi-person fs-4"></i>
          </button>
        </div>
      </nav>
    </header>

    <!-- 전체 내용 래퍼 -->
    <div>
      <!-- 안내 문구 -->
      <h3 class="text-center mt-5">상품을 검색해보세요</h3>

      <!-- 검색 입력 -->
      <div class="d-flex justify-content-center mt-4">
        <input
          class="form-control form-control-lg w-50"
          type="text"
          placeholder="검색어를 입력해주세요"
        />
        <button class="btn btn-primary ms-2">검색</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
button.dropdown-toggle::after {
  display: none !important;
}

dropdown-submenu {
  position: relative;
}

.dropdown-submenu .submenu {
  position: absolute;
  top: 0;
  left: 100%;
  min-width: 160px;
  display: none;
  z-index: 1000;
}

.dropdown-submenu:hover .submenu {
  display: block;
}
</style>
