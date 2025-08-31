<!--
===============================================================
文件：src/pages/Home.vue
功能：前台首页（欢迎页）
- 使用 Vue Transition 提供进入时的动画
- 展示欢迎标题、个人简介和“查看作品”按钮
- 作为整个 Portfolio 的入口
===============================================================
File: src/pages/Home.vue
Purpose: Frontend landing page (home)
- Uses Vue Transition for enter animation
- Shows welcome heading, personal intro, and "View My Work" button
- Serves as entry point of the portfolio
===============================================================
-->

<template>
  <!-- 页面淡入动画 / Page enter animation -->
  <transition
    appear
    enter-active-class="transition-all duration-1000 ease-out"
    enter-from-class="opacity-0 translate-y-16"
    enter-to-class="opacity-100 translate-y-0"
  >
    <section class="mt-16 text-center space-y-8">
      <!-- 主标题 / Main heading -->
      <h1 class="text-6xl font-extrabold leading-tight text-nature-brown">
        🌱 Welcome to My Portfolio
      </h1>

      <!-- 简短个人介绍 / Short personal intro -->
      <p class="text-xl font-medium text-nature-brown/80 max-w-xl mx-auto">
        I’m Tom — a full-stack developer specializing in Spring Boot and Vue.js.
      </p>

      <!-- 导航按钮：跳转到项目页 / Navigation button: go to Projects page -->
      <router-link
        to="/projects"
        class="inline-block px-6 py-3 rounded-xl font-semibold text-nature-brown
               border-2 border-nature-brown bg-transparent
               hover:bg-nature-brown hover:text-white
               transition duration-200 ease-in-out transform hover:scale-105
               focus:outline-none focus:ring-2 focus:ring-nature-green"
      >
        View My Work
      </router-link>
    </section>
  </transition>
</template>

<script setup>
// 本页面无逻辑，仅展示静态内容与导航
// No logic here; just static content and navigation
</script>

<style scoped>
/* 以下样式多数可由 Tailwind 替代，这里保留基础样式
   Most can be replaced by Tailwind, kept here as fallback */

.home {
  text-align: center;
  padding: 3rem 1rem;
}

.cta {
  display: inline-block;
  margin-top: 2rem;
  padding: 0.6rem 1.2rem;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 6px;
  transition: background-color 0.3s;
}
.cta:hover {
  background-color: #0056b3;
}
</style>
