<!--
===============================================================
文件：src/pages/About.vue
功能：前台「关于我」页面
- 顶部大标题，与 Contact 页统一风格
- 左侧头像 + 右侧标题与简介
- 下方三块信息（技能 / 教育 / 语言）
- 使用 axios 从后端拉取 About 信息，支持头像、技能等
===============================================================
File: src/pages/About.vue
Purpose: Frontend "About Me" page
- Top heading (same style as Contact page)
- Left: profile photo + Right: title & bio
- Bottom three sections (Skills / Education / Languages)
- Fetch about info from backend via axios
===============================================================
-->

<template>
  <section class="mt-16 max-w-5xl mx-auto space-y-10">
    <!-- 标题：保持与 Contact 一致的风格 / Title: consistent with Contact -->
    <h2 class="text-4xl md:text-5xl font-extrabold tracking-tight text-center text-nature-brown">
      About Me
    </h2>

    <!-- 个人信息：头像（左） + 标题/简介（右） -->
    <!-- Personal info: avatar (left) + title/bio (right) -->
    <div v-if="about" class="flex flex-col md:flex-row items-start gap-8">
      <!-- 头像：固定宽高，不被压缩 / Avatar: fixed size, non-shrink -->
      <img
        :src="photoSrc"
        alt="Profile photo"
        class="w-40 h-40 md:w-52 md:h-52 rounded-2xl object-cover shadow-lg
              ring-2 ring-nature-brown/20 shrink-0"
      />

      <!-- 右侧：标题在上，简介在下 / Right: title above, bio below -->
      <div class="flex-1 space-y-3">
        <h3 class="text-2xl md:text-3xl font-bold text-nature-brown">
          {{ about.title }}
        </h3>
        <p class="text-nature-brown/80 leading-relaxed max-w-3xl">
          {{ about.bio }}
        </p>
      </div>
    </div>

    <!-- 加载中提示 / Loading placeholder -->
    <div v-else class="text-center text-nature-brown/70">Loading my info...</div>

    <!-- 三块信息：Skills / Education / Languages -->
    <!-- Three sections: Skills / Education / Languages -->
    <div v-if="about" class="grid gap-8 md:grid-cols-3">
      <!-- Skills：徽章样式 / Skills: badge style -->
      <section>
        <h4 class="text-lg font-semibold mb-3">Skills</h4>
        <ul class="flex flex-wrap gap-2">
          <li
            v-for="skill in about.skills"
            :key="skill"
            class="px-3 py-1 rounded-full bg-nature-light/60 border border-nature-brown/20 text-sm text-nature-brown"
          >
            {{ skill }}
          </li>
        </ul>
      </section>

      <!-- Education：柔和卡片 / Education: soft cards -->
      <section>
        <h4 class="text-lg font-semibold mb-3">Education</h4>
        <ul class="space-y-3">
          <li
            v-for="edu in about.educations"
            :key="edu"
            class="p-3 rounded-xl bg-white/60 border border-nature-brown/10 text-nature-brown/90"
          >
            {{ edu }}
          </li>
        </ul>
      </section>

      <!-- Languages：简洁徽章 / Languages: simple badges -->
      <section>
        <h4 class="text-lg font-semibold mb-3">Languages</h4>
        <ul class="flex flex-wrap gap-2">
          <li
            v-for="lang in about.languages"
            :key="lang"
            class="px-3 py-1 rounded-md bg-white/60 border border-nature-brown/10 text-sm"
          >
            {{ lang }}
          </li>
        </ul>
      </section>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'

/* 响应式对象：后端返回的 About 数据
   Reactive state: About data from backend */
const about = ref(null)

/* 默认头像路径 / Default avatar */
const defaultAvatar = new URL('../assets/me.png', import.meta.url).href

/* 优先使用后端头像 / Use backend photo if available */
const photoSrc = computed(() => about.value?.photo || defaultAvatar)

/* 生命周期：挂载时拉取数据 / Lifecycle: fetch on mount */
onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/about')
    about.value = res.data
  } catch (err) {
    console.error('Failed to fetch about info:', err)
  }
})
</script>

<style scoped>
/* 样式全部由 Tailwind 原子类完成，无需额外 CSS */
/* All styling handled by Tailwind utility classes */
</style>
