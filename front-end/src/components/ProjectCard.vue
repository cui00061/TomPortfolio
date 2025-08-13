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
    <div
  ref="wrapRef"
  class="overflow-hidden transition-[max-height,padding] duration-500 ease-out"
  :style="{ maxHeight, paddingTop }"
  aria-hidden="!open"
>
  <div ref="innerRef" class="pt-2">
    <!-- 响应式：小屏上下，>=md 左右 -->
    <div class="grid gap-6 md:grid-cols-5 items-start">
      <!-- 左侧图片（可选） -->
      <figure v-if="src" class="md:col-span-2">
        <div class="rounded-xl border border-nature-brown/10 overflow-hidden bg-white">
          <!-- 限高 + contain，避免过大压住文字 -->
          <img
            :src="src"
            alt="project cover"
            class="w-full md:h-[220px] object-contain"
            loading="lazy"
            decoding="async"
          />
        </div>
      </figure>

      <!-- 右侧文字（没有图片时占满整行） -->
      <div :class="src ? 'md:col-span-3' : 'md:col-span-5'" class="space-y-3">
        <p class="text-nature-brown/85 leading-relaxed text-[17px]">
          {{ project.description }}
        </p>
      </div>
    </div>
  </div>
</div>

  </article>
</template>

<script setup>
import { ref, nextTick } from 'vue'

defineProps({
  project: { type: Object, required: true },
  /** 解析好的图片地址（父组件传入，可为空） */
  src: { type: String, default: '' },
})

const open = ref(false)
const wrapRef = ref(null)
const innerRef = ref(null)

// 通过内层真实高度驱动 max-height 动画
const maxHeight = ref('0px')
const paddingTop = ref('0px')

const measure = () => {
  if (!innerRef.value) return 0
  return innerRef.value.scrollHeight
}

const expand = async () => {
  await nextTick()
  const h = measure()
  maxHeight.value = h + 'px'
  paddingTop.value = '8px' // 展开时有一点上内边距，配合过渡更自然
}

const collapse = () => {
  maxHeight.value = '0px'
  paddingTop.value = '0px'
}

const toggle = async () => {
  open.value = !open.value
  open.value ? expand() : collapse()
}
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
