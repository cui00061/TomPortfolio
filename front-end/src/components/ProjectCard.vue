<!--
文件: ProjectCard.vue
语言: Vue 3 单文件组件 (SFC, 使用 <script setup>)
作用: 展示单个项目的卡片，支持折叠/展开、摘要显示、轮播图、详细描述
服务对象: Projects.vue（项目列表页面）或任何需要项目卡片的页面
说明:
- props: project(必需, Object)、src(可选, String, 用作备用单图)
- 收起时只显示摘要（前 1~2 条），避免卡片过高；展开后显示轮播图与完整描述
- 折叠动画采用 max-height + padding 过渡（height:auto 无法平滑过渡）
- 轮播图仅在「展开且图片数量>1」时自动播放；鼠标悬停暂停，移开恢复
- 使用 Composition API（ref、computed、watch、生命周期）组织逻辑
- 可访问性：使用 aria-hidden 与 sr-only 辅助屏幕阅读器
-->

<!--
File: ProjectCard.vue
Language: Vue 3 Single File Component (SFC, <script setup>)
Purpose: Display a single project card with collapsible details, summary view, image carousel, and full description
Used by: Projects.vue or any page that needs a project card
Notes:
- Props: project (required, Object), src (optional, String) used as fallback single image
- When collapsed, shows a compact summary (first 1–2 items); when expanded, shows carousel and full details
- Collapse/expand animation uses max-height + padding transition (height:auto cannot animate)
- Carousel auto-plays only if panel is open and there are >1 images; paused on hover, resumed on mouse leave
- Implements Vue Composition API (ref, computed, watch, lifecycle)
- Accessibility: uses aria-hidden and sr-only for screen readers
-->

<template>
  <article
    class="bg-white/70 border border-nature-brown/10 rounded-2xl p-4 shadow-sm
           transition hover:shadow-lg"
  >
    <header class="flex items-start justify-between gap-3">
      <h3 class="text-xl font-semibold text-nature-brown">
        {{ project.title }}
      </h3>

      <!-- 文案随 open 变化，提高预期 / Label flips with `open` for better UX -->
      <button
        class="shrink-0 rounded-full border border-nature-brown/20 px-3 py-1 text-sm
               hover:bg-nature-brown hover:text-white transition"
        @click="toggle"
      >
        {{ open ? 'Hide' : 'View' }}
      </button>
    </header>

    <!-- 收起时的摘要 / Summary only when collapsed -->
    <p v-if="!open" class="mt-2 text-nature-brown/70 line-clamp-2">
      {{ summaryText }}
    </p>

    <!-- 展开内容容器：max-height + padding 做动画 / Expanded content with animated max-height -->
    <div
      ref="wrapRef"
      class="overflow-hidden transition-[max-height,padding] duration-500 ease-out"
      :style="{ maxHeight, paddingTop }"
      :aria-hidden="!open"
    >
      <div ref="innerRef" class="pt-2">
        <div class="grid gap-6 md:grid-cols-5 items-start">
          <!-- 左侧轮播（可选） / Optional left carousel -->
          <figure v-if="hasImages" class="md:col-span-2">
            <div
              class="relative rounded-xl border border-nature-brown/10 overflow-hidden bg-white"
              @mouseenter="pauseAuto"
              @mouseleave="resumeAuto"
            >
              <div class="w-full h-52 md:h-[220px] overflow-hidden">
                <div
                  class="flex h-full transition-transform duration-500 ease-out"
                  :style="{ transform: `translateX(-${activeIndex * 100}%)` }"
                >
                  <!-- 每张图占 100% 宽度 / Each slide is min-w-full -->
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

              <!-- 圆点导航 / Dots nav -->
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

              <!-- 左右切换仅在多图时显示 / Prev/Next only if multiple images -->
              <button
                v-if="srcList.length > 1"
                class="absolute left-1 top-1/2 -translate-y-1/2 rounded-full bg-white/70
                       border border-nature-brown/20 px-2 py-1 text-xs hover:bg-white"
                @click="prev"
                aria-label="prev"
              >‹</button>
              <button
                v-if="srcList.length > 1"
                class="absolute right-1 top-1/2 -translate-y-1/2 rounded-full bg-white/70
                       border border-nature-brown/20 px-2 py-1 text-xs hover:bg-white"
                @click="next"
                aria-label="next"
              >›</button>
            </div>
          </figure>

          <!-- 右侧描述 / Right-side description -->
          <div
            :class="hasImages ? 'md:col-span-3' : 'md:col-span-5'"
            class="space-y-3"
            :style="hasImages ? { maxHeight: '220px', overflowY: 'auto' } : {}"
          >
            <!-- 优先列表渲染；兼容旧数据为字符串 -->
            <ul
              v-if="descList.length"
              class="text-nature-brown/85 leading-relaxed text-[17px] space-y-2 list-disc pl-5"
            >
              <li v-for="(d, i) in descList" :key="i">{{ d }}</li>
            </ul>
            <p
              v-else
              class="text-nature-brown/85 leading-relaxed text-[17px] whitespace-pre-line"
            >
              {{ typeof project.description === 'string' ? project.description : '' }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </article>
</template>

<script setup>
import { ref, nextTick, computed, watch, onMounted, onBeforeUnmount } from 'vue'

/* props：项目数据 + 备用单图 / Props: project data + fallback single image */
const { project, src } = defineProps({
  project: { type: Object, required: true },
  src: { type: String, default: '' },
})

/* 
  descList：把 description 规范成数组，便于统一渲染
  - Array → trim + 过滤空项
  - String → 按换行/分号/句号分割
*/
const descList = computed(() => {
  const d = project?.description
  if (Array.isArray(d)) return d.map(s => String(s).trim()).filter(Boolean)
  if (typeof d === 'string') {
    return d.split(/\r?\n|[;；]|(?<=\.)\s+/).map(s => s.trim()).filter(Boolean)
  }
  return []
})

/* 收起时的摘要（前 1~2 条），否则兼容旧字符串 */
const summaryText = computed(() =>
  descList.value.length
    ? descList.value.slice(0, 2).join(' • ')
    : (typeof project?.description === 'string' ? project.description : '')
)

/* 折叠/展开动画：maxHeight + paddingTop（height:auto 不能过渡） */
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
const collapse = () => { maxHeight.value = '0px'; paddingTop.value = '0px' }
const toggle = async () => { open.value = !open.value; open.value ? expand() : (stopAuto(), collapse()) }

/* 轮播：多图时自动播放；悬停暂停、移出恢复 */
const srcList = computed(() => {
  const arr = Array.isArray(project?.screenshotUrls) ? project.screenshotUrls : []
  return arr.length > 0 ? arr : (src ? [src] : [])
})
const hasImages = computed(() => srcList.value.length > 0)

const activeIndex = ref(0)
let timer = null
const INTERVAL = 3000

const next = () => { if (srcList.value.length > 1) activeIndex.value = (activeIndex.value + 1) % srcList.value.length }
const prev = () => { if (srcList.value.length > 1) activeIndex.value = (activeIndex.value - 1 + srcList.value.length) % srcList.value.length }
const go = (i) => { if (i >= 0 && i < srcList.value.length) activeIndex.value = i }

const startAuto = () => { if (!timer && srcList.value.length > 1) timer = setInterval(next, INTERVAL) }
const stopAuto = () => { if (timer) { clearInterval(timer); timer = null } }
const pauseAuto = () => stopAuto()
const resumeAuto = () => { if (open.value) startAuto() }

/* open：展开→启动轮播并 expand；收起→停止轮播 */
watch(open, (v) => { if (v) { startAuto(); setTimeout(() => expand(), 0) } else { stopAuto() } })

/* 图片列表变化：重置索引，如在展开中则重启轮播以避免越界/停表 */
watch(srcList, () => { activeIndex.value = 0; if (open.value) { stopAuto(); startAuto() } })

/* 资源清理：避免泄漏 */
onMounted(() => { if (open.value) startAuto() })
onBeforeUnmount(() => stopAuto())
</script>

<style scoped>
/* 两行截断（用于摘要） */
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
