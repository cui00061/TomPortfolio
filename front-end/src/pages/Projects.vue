<template>
  <section class="mt-16 max-w-4xl mx-auto space-y-8">
    <h2 class="text-4xl md:text-5xl font-extrabold tracking-tight text-center text-nature-brown">
      My Works
    </h2>

    <div v-if="projects.length">
      <!-- 关键：transition-group + appear 触发入场动画 -->
      <transition-group
        name="fade-up"
        tag="div"
        class="space-y-6"
        appear
      >
        <!-- [CHANGED] 去掉 :src，ProjectCard 直接从 project.screenshotUrls 读取 -->
        <ProjectCard
          v-for="(p, i) in projects"
          :key="p.id"
          :project="p"
          :style="{ transitionDelay: (i * 80) + 'ms' }"
          class="will-change-transform"
        />
      </transition-group>
    </div>

    <div v-else class="text-center text-nature-brown/70">
      Loading my projects...
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import ProjectCard from '@/components/ProjectCard.vue'

const projects = ref([])

/* 预加载本地图片：src/assets/projects/* （保留你原来的写法） */
const localImages = import.meta.glob('../assets/projects/*', {
  eager: true,
  import: 'default',
})

/* [ADDED] 把字符串或文件名解析成可用 URL（支持 http(s)、以 / 开头的静态路径、以及 assets 中文件名） */
const resolveUrl = (s) => {
  if (!s) return ''
  if (/^https?:\/\//i.test(s) || s.startsWith('/')) return s
  const hit = Object.entries(localImages).find(([p]) => p.endsWith('/' + s))
  return hit ? hit[1] : s // 找不到就原样返回，便于排查
}

/* [ADDED] 兼容旧数据：把 screenshotUrl(单个) 归一化为 screenshotUrls(数组)，并解析成真实 URL */
const normalizeProject = (p) => {
  const urls = Array.isArray(p.screenshotUrls)
    ? p.screenshotUrls
    : (p.screenshotUrl ? [p.screenshotUrl] : [])
  return {
    ...p,
    screenshotUrls: urls.map(resolveUrl),
  }
}

onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/projects')
    /* [CHANGED] 拉到数据后先归一化 */
    projects.value = Array.isArray(res.data) ? res.data.map(normalizeProject) : []
  } catch (err) {
    console.error('Failed to fetch projects info:', err)
  }
})
</script>

<style scoped>
/* 这些旧的示例样式可留可删，不影响当前卡片样式 */
.projects { padding: 2rem; }
.project-card { border: 1px solid #ddd; padding: 1rem; margin-bottom: 1.5rem; border-radius: 8px; background-color: #fafafa; }
.project-img { max-width: 100%; height: auto; border-radius: 4px; }
a { display: inline-block; margin-top: 0.5rem; color: #007bff; text-decoration: none; }
</style>
