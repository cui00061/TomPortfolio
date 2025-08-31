<!-- ===========================================================
 文件：App.vue
 功能：应用根组件，负责整体布局和路由出口
 - 引入全局导航栏 Navbar
 - 提供 <router-view> 显示不同页面
 - 定义全局背景、字体、颜色等样式
 作者：Tom
 ===========================================================
 File: App.vue
 Purpose: Root component of the application
 - Imports global Navbar
 - Provides <router-view> for rendering pages
 - Defines base layout, background, and typography
 Author: Tom
=========================================================== -->

<script setup>
  // 引入全局导航栏组件 / Import global navigation bar component
  import Navbar from './components/NavBar.vue';
</script>

<template>
  <!-- 根容器：设置全局最小高度、字体、颜色等 -->
  <!-- Root container: defines min height, font, text color -->
  <div class="min-h-screen text-nature-brown font-sans bg-transparent">

    <!-- 内容容器：设置最大宽度、内边距 -->
    <!-- Content wrapper: sets max width, padding -->
    <div class="max-w-4xl mx-auto px-4 py-8">
      
      <!-- 全局导航栏 / Global navigation bar -->
      <Navbar />
      
      <!-- 路由出口：动态切换页面 -->
      <!-- Router outlet: render active route's component -->
      <router-view />
    </div>
  </div>
</template>

<style scoped>
/* Logo 样式（示例，可用于 hover 动效） */
/* Logo styles (example, with hover effect) */
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
