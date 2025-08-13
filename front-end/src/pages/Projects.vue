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
      <ProjectCard
        v-for="(p, i) in projects"
        :key="p.id"
        :project="p"
        :src="imgSrc(p)"
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

// 加了别名的话用上面的写法，否则：import ProjectCard from '../components/ProjectCard.vue'

const projects = ref([])

// 预加载本地图片：src/assets/projects/*
const localImages = import.meta.glob('../assets/projects/*', {
  eager: true,
  import: 'default',
})

const imgSrc = (proj) => {
  const s = proj?.screenshotUrl
  if (!s) return ''
  if (/^https?:\/\//i.test(s) || s.startsWith('/')) return s
  const hit = Object.entries(localImages).find(([p]) => p.endsWith('/' + s))
  return hit ? hit[1] : ''
}

onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/projects')
    projects.value = res.data
  } catch (err) {
    console.error('Failed to fetch projects info:', err)
  }
})
</script>




<style scoped>
.projects {
  padding: 2rem;
}

.project-card {
  border: 1px solid #ddd;
  padding: 1rem;
  margin-bottom: 1.5rem;
  border-radius: 8px;
  background-color: #fafafa;
}

.project-img {
  max-width: 100%;
  height: auto;
  border-radius: 4px;
}

a {
  display: inline-block;
  margin-top: 0.5rem;
  color: #007bff;
  text-decoration: none;
}
</style>