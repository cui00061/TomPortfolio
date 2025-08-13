<template>
  <section class="mt-16 max-w-5xl mx-auto space-y-10">
    <!-- 标题，保持与 Contact 一致的风格 -->
    <h2 class="text-4xl md:text-5xl font-extrabold tracking-tight text-center text-nature-brown">
      About Me
    </h2>

    <!-- 个人信息：头像（左） + 标题/简介（右） -->
  <div v-if="about" class="flex flex-col md:flex-row items-start gap-8">
    <!-- 头像：固定宽高，不被压缩 -->
    <img
      :src="photoSrc"
      alt="Profile photo"
      class="w-40 h-40 md:w-52 md:h-52 rounded-2xl object-cover shadow-lg
            ring-2 ring-nature-brown/20 shrink-0"
    />

  <!-- 右侧：标题在上，简介在下 -->
  <div class="flex-1 space-y-3">
    <h3 class="text-2xl md:text-3xl font-bold text-nature-brown">
      {{ about.title }}
    </h3>
    <p class="text-nature-brown/80 leading-relaxed max-w-3xl">
      {{ about.bio }}
    </p>
  </div>
</div>


    <div v-else class="text-center text-nature-brown/70">Loading my info...</div>

    <!-- 三块信息：更规整的布局 -->
    <div v-if="about" class="grid gap-8 md:grid-cols-3">
      <!-- Skills：用徽章样式，紧凑有节奏 -->
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

      <!-- Education：柔和的卡片 -->
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

      <!-- Languages：小徽章，和 Skills 呼应但更简洁 -->
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

const about = ref(null)

// 本地占位头像（如有后端 about.photo 字段，会优先使用）
const defaultAvatar = new URL('../assets/me.png', import.meta.url).href
const photoSrc = computed(() => about.value?.photo || defaultAvatar)

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
/* 无需额外样式，全部用 Tailwind 原子类完成 */
</style>
