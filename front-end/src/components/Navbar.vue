<!--
文件: Navbar.vue
语言: Vue 3 单文件组件 (SFC, 使用 <script setup>)
作用: 网站的全局导航栏组件，提供页面切换入口
服务对象: App.vue (全局布局)，所有页面都会显示此导航栏
说明:
- 使用 Vue Router 的 <RouterLink> 实现路由跳转
- 使用 TailwindCSS 进行布局与交互动画
- 当前激活的链接会以绿色高亮显示
-->

<!--
File: Navbar.vue
Language: Vue 3 SFC (Single File Component) with <script setup>
Purpose: Global navigation bar component, provides page switching
Used by: App.vue (global layout), displayed on all pages
Notes:
- Uses Vue Router <RouterLink> for navigation
- Styled with TailwindCSS for layout and hover animations
- Active link is highlighted in green
-->

<template>
  <!-- 导航栏容器 / Navigation container -->
  <nav
    class="flex items-center justify-between bg-white/60 backdrop-blur-md rounded-2xl
           px-6 py-4 shadow-lg sticky top-4 mx-4 z-50"
  >
    <!-- 左侧 Logo / Left-side Logo -->
    <div class="text-xl font-semibold text-nature-brown">
      Tom’s Portfolio
    </div>

    <!-- 右侧导航链接 / Right-side navigation links -->
    <ul class="flex space-x-6">
      <!-- 遍历 links 数组生成导航项 / Loop through links array to generate nav items -->
      <li v-for="link in links" :key="link.to" class="group">
        <RouterLink :to="link.to" v-slot="{ href, navigate, isActive }">
          <a
            :href="href"
            @click="navigate"
            class="inline-block transition-all duration-250 transform group-hover:-translate-y-1"
            :class="
              isActive
                ? 'font-extrabold text-nature-green'
                : 'font-medium text-nature-brown/80 group-hover:text-nature-green'
            "
          >
            <!-- 显示导航文字 / Display link name -->
            {{ link.name }}
          </a>
        </RouterLink>
      </li>
    </ul>
  </nav>
</template>

<script setup>
// 导航链接数组 / Navigation links array
const links = [
  { name: 'Home',     to: '/'        }, // 首页 / Home page
  { name: 'About',    to: '/about'   }, // 关于页面 / About page
  { name: 'Contact',  to: '/contact' }, // 联系页面 / Contact page
  { name: 'Projects', to: '/projects'}  // 项目页面 / Projects page
]
</script>
