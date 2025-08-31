<!--
===============================================================
文件：src/pages/admin/AdminLayout.vue
功能：管理员后台的顶层布局与导航容器
- 左侧：Admin 子模块标签导航（Projects / About / Contact）
- 右侧：当前用户名与登出按钮
- 下方：<RouterView /> 承载各子页面
作者：Tom
===============================================================
File: src/pages/admin/AdminLayout.vue
Purpose: Top-level layout and navigation for the admin panel
- Left: tabs for admin submodules (Projects / About / Contact)
- Right: current username and logout button
- Below: <RouterView /> to render nested child routes
Author: Tom
===============================================================
-->

<template>
  <section class="max-w-6xl mx-auto mt-8">
    <!-- 头部：标签式导航 + 用户区 / Header: tabs + user area -->
    <header class="flex items-center justify-between mb-6">
      <!-- 标签导航 / Tab navigation -->
      <nav class="flex gap-2">
        <!-- Projects 标签：isActive 高亮 / Tab with active highlight -->
        <RouterLink :to="{ name: 'admin-projects' }" v-slot="{ isActive }">
          <span
            :class="[
              'rounded-xl border px-4 py-1 text-sm hover:bg-white/70',
              isActive && 'bg-nature-brown text-white border-nature-brown'
            ]"
          >
            Projects
          </span>
        </RouterLink>

        <!-- About 标签 / About tab -->
        <RouterLink :to="{ name: 'admin-about' }" v-slot="{ isActive }">
          <span
            :class="[
              'rounded-xl border px-4 py-1 text-sm hover:bg-white/70',
              isActive && 'bg-nature-brown text-white border-nature-brown'
            ]"
          >
            About
          </span>
        </RouterLink>

        <!-- Contact 标签 / Contact tab -->
        <RouterLink :to="{ name: 'admin-contact' }" v-slot="{ isActive }">
          <span
            :class="[
              'rounded-xl border px-4 py-1 text-sm hover:bg-white/70',
              isActive && 'bg-nature-brown text-white border-nature-brown'
            ]"
          >
            Contact
          </span>
        </RouterLink>
      </nav>

      <!-- 用户区：问候 + 登出 / User area: greet + logout -->
      <div class="flex items-center gap-3">
        <span class="text-nature-brown/80 text-sm">Hi, {{ username || 'admin' }}</span>
        <button class="rounded-lg border px-3 py-1 hover:bg-white" @click="logoutAndGo">
          Logout
        </button>
      </div>
    </header>

    <!-- 子路由出口：渲染当前选中的 Admin 子页 / Nested route outlet -->
    <RouterView />
  </section>
</template>

<script setup lang="ts">
/*
  说明 / Notes:
  - useAuth 提供 username 与 logout；点击登出后跳转到登录页
  - useRouter 用于路由跳转（replace 避免回退到已登出页面）
  - useAuth provides username/logout; after logout redirect to signin
  - useRouter is used to navigate; replace prevents going back to a logged-out page
*/
import { useAuth } from "@/composables/useAuth";
import { useRouter } from "vue-router";

const router = useRouter();
const { username, logout } = useAuth();

function logoutAndGo() {
  // 登出并重定向至登录页 / Logout then redirect to signin
  logout();
  router.replace({ name: "signin" });
}
</script>
