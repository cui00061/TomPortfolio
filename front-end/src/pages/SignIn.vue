<!--
===============================================================
文件：src/pages/Signin.vue
功能：管理员登录页
- 表单字段：用户名 / 密码
- 提交时调用 useAuth().login，成功后按 redirect 或默认跳转到 /admin
- loading 与错误信息（err）用于按钮禁用与提示
===============================================================
File: src/pages/Signin.vue
Purpose: Admin sign-in page
- Form fields: username / password
- On submit: call useAuth().login; redirect to query.redirect or /admin
- Uses loading and err to control button state and error message
===============================================================
-->

<template>
  <main class="max-w-sm mx-auto mt-24 bg-white/80 border rounded-2xl p-6 shadow">
    <h1 class="text-2xl font-semibold text-nature-brown mb-4">Admin Sign in</h1>

    <!-- 登录表单 / Sign-in form -->
    <form @submit.prevent="onSubmit" class="space-y-4">
      <!-- 用户名 / Username -->
      <label class="block">
        <span class="text-sm text-nature-brown/80">Username</span>
        <input v-model.trim="form.username" class="mt-1 w-full rounded-lg border p-2"
               placeholder="admin" autocomplete="username" />
      </label>

      <!-- 密码 / Password -->
      <label class="block">
        <span class="text-sm text-nature-brown/80">Password</span>
        <input v-model="form.password" type="password" class="mt-1 w-full rounded-lg border p-2"
               placeholder="********" autocomplete="current-password" />
      </label>

      <!-- 提交按钮：受 loading 影响禁用 / Submit button: disabled while loading -->
      <button :disabled="loading"
              class="w-full rounded-lg bg-nature-brown text-white py-2 hover:opacity-90 disabled:opacity-50">
        {{ loading ? 'Signing in…' : 'Sign in' }}
      </button>

      <!-- 错误提示 / Error message -->
      <p v-if="err" class="text-red-600 text-sm">{{ err }}</p>
    </form>
  </main>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useAuth } from "@/composables/useAuth";

/* 依赖注入：认证与路由
   Auth & routing utilities */
const { login } = useAuth();
const router = useRouter();
const route = useRoute();

/* 表单与状态 / Form and state */
const form = reactive({ username: "", password: "" });
const loading = ref(false);
const err = ref("");

/* 提交处理：
   - 重置错误并置 loading
   - 调用 login(username, password)
   - 成功：根据 ?redirect=xxx 跳转，否则 /admin
   - 失败：展示后端返回的 message 或默认“Login failed”
   Submit handler:
   - Reset error and set loading
   - Call login(username, password)
   - On success: redirect to ?redirect or /admin
   - On failure: show backend message or default text */
async function onSubmit() {
  err.value = ""; loading.value = true;
  try {
    await login(form.username, form.password);
    const to = (route.query.redirect as string) || "/admin";
    router.replace(to);
  } catch (e: any) {
    err.value = e?.response?.data?.message || "Login failed";
  } finally {
    loading.value = false;
  }
}
</script>
