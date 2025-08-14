<template>
  <article
    class="bg-white/70 border border-nature-brown/10 rounded-2xl p-4 shadow-sm
           transition hover:shadow-lg"
  >
    <!-- 标题 + 按钮 -->
    <header class="flex items-start justify-between gap-3">
      <h3 class="text-xl font-semibold text-nature-brown">
        {{ project.title }}
      </h3>
      <button
        class="shrink-0 rounded-full border border-nature-brown/20 px-3 py-1 text-sm
               hover:bg-nature-brown hover:text-white transition"
        @click="toggle"
      >
        {{ open ? 'Hide' : 'View' }}
      </button>
    </header>

    <!-- 摘要（收起时显示 1~2 行） -->
    <p v-if="!open" class="mt-2 text-nature-brown/70 line-clamp-2">
      {{ project.description }}
    </p>

    <!-- 折叠内容：max-height + padding 过渡，丝滑展开 -->
     <!-- [CHANGED] 绑定为布尔 -->
    <div
      ref="wrapRef"
      class="overflow-hidden transition-[max-height,padding] duration-500 ease-out"
      :style="{ maxHeight, paddingTop }"
      :aria-hidden="!open"  
    >
    
      <div ref="innerRef" class="pt-2">
        <!-- 响应式：小屏上下，>=md 左右 -->
        <div class="grid gap-6 md:grid-cols-5 items-start">
          <!-- 左侧图片区域：改为自动滚动轮播 -->
           <!-- [ADDED] 悬停暂停 -->
            <!-- [ADDED] 恢复播放 -->
          <figure v-if="hasImages" class="md:col-span-2"> <!-- [CHANGED] -->
            <div
              class="relative rounded-xl border border-nature-brown/10 overflow-hidden bg-white"
              @mouseenter="pauseAuto"   
              @mouseleave="resumeAuto"  
            >
              <!-- 轮播视窗 -->
               <!-- [CHANGED] 固定高，避免抖动 -->
              <div
                class="w-full h-52 md:h-[220px] overflow-hidden"  
              >
                <!-- 轮播轨道 -->
                <div
                  class="flex h-full transition-transform duration-500 ease-out"
                  :style="{ transform: `translateX(-${activeIndex * 100}%)` }"  
                >
                  <!-- 每一页 -->
                  <div
                    v-for="(u, i) in srcList"
                    :key="i"
                    class="min-w-full h-full flex items-center justify-center bg-white"
                  >
                    <img
                      :src="u"
                      alt="project cover"
                      class="w-full h-full object-contain"
                      loading="lazy"
                      decoding="async"
                    />
                  </div>
                </div>
              </div>

              <!-- 底部小圆点指示器 -->
               <!-- [ADDED] 点击跳转 -->
              <div class="absolute bottom-2 inset-x-0 flex justify-center gap-1.5">
                <button
                  v-for="(u, i) in srcList"
                  :key="`dot-${i}`"
                  type="button"
                  class="h-1.5 rounded-full transition"
                  :class="i === activeIndex
                    ? 'w-5 bg-nature-brown'
                    : 'w-2.5 bg-nature-brown/30 hover:bg-nature-brown/60'"
                  @click="go(i)"
                  :aria-label="`go to slide ${i + 1}`"
                >
                  <span class="sr-only">go to slide {{ i + 1 }}</span>
                </button>
              </div>


              <!-- 左右切换（可选） -->
              <button
                v-if="srcList.length > 1"
                class="absolute left-1 top-1/2 -translate-y-1/2 rounded-full bg-white/70 border border-nature-brown/20 px-2 py-1 text-xs hover:bg-white"
                @click="prev"
                aria-label="prev"
              >‹</button>
              <button
                v-if="srcList.length > 1"
                class="absolute right-1 top-1/2 -translate-y-1/2 rounded-full bg-white/70 border border-nature-brown/20 px-2 py-1 text-xs hover:bg-white"
                @click="next"
                aria-label="next"
              >›</button>
            </div>
          </figure>

          <!-- 右侧文字（没有图片时占满整行） -->
          <!-- 右侧文字（没有图片时占满整行） -->
           <!-- [ADDED] 固定高度+滚动 -->
          <div
            :class="hasImages ? 'md:col-span-3' : 'md:col-span-5'"
            class="space-y-3"
            :style="hasImages ? { maxHeight: '220px', overflowY: 'auto' } : {}"  
          >
            <p class="text-nature-brown/85 leading-relaxed text-[17px] whitespace-pre-line">
              {{ project.description }}
            </p>
          </div>
        </div>
      </div>
    </div>

  </article>
</template>

<script setup>
import { ref, nextTick, computed, watch, onMounted, onBeforeUnmount } from 'vue'

// ✅ 解构 props，避免 "project is not defined"
const { project, src } = defineProps({
  project: { type: Object, required: true },
  src: { type: String, default: '' }, // 兼容旧用法：单图地址
})

// 折叠控制
const open = ref(false)
const wrapRef = ref(null)
const innerRef = ref(null)
const maxHeight = ref('0px')
const paddingTop = ref('0px')
const measure = () => (innerRef.value ? innerRef.value.scrollHeight : 0)
const expand = async () => {
  await nextTick()
  maxHeight.value = measure() + 'px'
  paddingTop.value = '8px'
}
const collapse = () => {
  maxHeight.value = '0px'
  paddingTop.value = '0px'
}
const toggle = async () => {
  open.value = !open.value
  open.value ? expand() : (stopAuto(), collapse())
}

// 轮播图逻辑
const srcList = computed(() => {
  const arr = Array.isArray(project?.screenshotUrls) ? project.screenshotUrls : []
  if (arr.length > 0) return arr
  return src ? [src] : []
})
const hasImages = computed(() => srcList.value.length > 0)

const activeIndex = ref(0)
let timer = null
const INTERVAL = 3000

const next = () => {
  if (srcList.value.length <= 1) return
  activeIndex.value = (activeIndex.value + 1) % srcList.value.length
}
const prev = () => {
  if (srcList.value.length <= 1) return
  activeIndex.value = (activeIndex.value - 1 + srcList.value.length) % srcList.value.length
}
const go = (i) => {
  if (i < 0 || i >= srcList.value.length) return
  activeIndex.value = i
}

const startAuto = () => {
  if (timer || srcList.value.length <= 1) return
  timer = setInterval(next, INTERVAL)
}
const stopAuto = () => {
  if (timer) {
    clearInterval(timer)
    timer = null
  }
}
const pauseAuto = () => stopAuto()
const resumeAuto = () => {
  if (open.value) startAuto()
}

watch(open, (v) => {
  if (v) {
    startAuto()
    setTimeout(() => expand(), 0)
  } else {
    stopAuto()
  }
})

watch(srcList, () => {
  activeIndex.value = 0
  if (open.value) {
    stopAuto()
    startAuto()
  }
})

onMounted(() => {
  if (open.value) startAuto()
})
onBeforeUnmount(() => stopAuto())
</script>


<style scoped>
/* 2 行摘要（Tailwind v4 没内置 line-clamp，简易实现） */
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
