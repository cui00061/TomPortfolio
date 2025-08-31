<!--
===============================================================
文件：src/pages/Projects.vue
功能：前台「作品集」页面
- 显示项目列表，每个项目以 ProjectCard 呈现
- 使用 <transition-group> 增加入场动画
- axios 拉取项目数据，支持本地和远程图片
- 对 screenshotUrl 做兼容处理，统一成 screenshotUrls 数组
===============================================================
File: src/pages/Projects.vue
Purpose: Public "My Works" page
- Display projects, each rendered via ProjectCard
- Add enter animation with <transition-group>
- Fetch project data via axios, support local & remote images
- Normalize screenshotUrl to screenshotUrls array
===============================================================
-->

<template>
  <section class="mt-16 max-w-4xl mx-auto space-y-8">
    <h2 class="text-4xl md:text-5xl font-extrabold tracking-tight text-center text-nature-brown">
      My Works
    </h2>

    <div v-if="projects.length">
      <!-- 列表入场动画 / List enter animation -->
      <transition-group
        name="fade-up"
        tag="div"
        class="space-y-6"
        appear
      >
        <!-- 项目卡片循环渲染 / Render each project card -->
        <ProjectCard
          v-for="(p, i) in projects"
          :key="p.id"
          :project="p"
          :style="{ transitionDelay: (i * 80) + 'ms' }"  
          class="will-change-transform"
        />
      </transition-group>
    </div>

    <!-- 加载时提示 / Loading placeholder -->
    <div v-else class="text-center text-nature-brown/70">
      Loading my projects...
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import ProjectCard from '@/components/ProjectCard.vue'

/* 项目列表状态 / Reactive project list */
const projects = ref([])

/* 本地图片预加载（src/assets/projects/*）/ Preload local images */
const localImages = import.meta.glob('../assets/projects/*', {
  eager: true,
  import: 'default',
})

/* URL 解析：支持 http(s)、绝对路径、本地 assets 文件
   Resolve URL: support http(s), absolute paths, local assets */
const resolveUrl = (s) => {
  if (!s) return ''
  if (/^https?:\/\//i.test(s) || s.startsWith('/')) return s
  const hit = Object.entries(localImages).find(([p]) => p.endsWith('/' + s))
  return hit ? hit[1] : s // 找不到则原样返回 / fallback for debugging
}

/* 项目数据归一化：
   - 单个 screenshotUrl 转换为 screenshotUrls 数组
   - 转换为真实可用的 URL
   Normalize project data:
   - Convert single screenshotUrl → screenshotUrls[]
   - Resolve into usable URLs */
const normalizeProject = (p) => {
  const urls = Array.isArray(p.screenshotUrls)
    ? p.screenshotUrls
    : (p.screenshotUrl ? [p.screenshotUrl] : [])
  return {
    ...p,
    screenshotUrls: urls.map(resolveUrl),
  }
}

/* 页面挂载时拉取数据 / Fetch data on mount */
onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/projects')
    projects.value = Array.isArray(res.data) ? res.data.map(normalizeProject) : []
  } catch (err) {
    console.error('Failed to fetch projects info:', err)
  }
})
</script>

<style scoped>
/* 示例样式，可留可删 / Legacy demo styles (optional) */
.projects { padding: 2rem; }
.project-card { border: 1px solid #ddd; padding: 1rem; margin-bottom: 1.5rem; border-radius: 8px; background-color: #fafafa; }
.project-img { max-width: 100%; height: auto; border-radius: 4px; }
a { display: inline-block; margin-top: 0.5rem; color: #007bff; text-decoration: none; }
</style>
