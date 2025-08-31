<!--
===============================================================
文件：src/pages/admin/AdminAbout.vue
功能：管理员后台 About 模块的编辑页面
- 从后端加载 About 数据（技能、语言、教育等）
- 表单双向绑定 draft 数据，可编辑文本
- 保存时根据 id 决定调用 PUT 或 POST
- 将数组字段和表单文本在字符串/数组之间转换
作者：Tom
===============================================================
File: src/pages/admin/AdminAbout.vue
Purpose: Admin panel page for editing "About" section
- Load "About" data (skills, languages, educations) from backend
- Bind form fields to reactive draft object
- Save changes using PUT (update) or POST (create)
- Convert array fields <-> comma-separated string
Author: Tom
===============================================================
-->

<template>
  <section class="space-y-4">
    <header class="flex items-center justify-between">
      <h2 class="text-2xl font-semibold text-nature-brown">About</h2>
      <button class="rounded-lg bg-nature-brown text-white px-4 py-2" @click="save">
        {{ saving ? 'Saving…' : 'Save' }}
      </button>
    </header>

    <!-- 动态生成表单字段 -->
    <div class="bg-white/80 border rounded-xl p-4 space-y-3">
      <div v-for="k in keys" :key="k">
        <label class="block text-sm text-nature-brown/80">{{ k }}</label>
        <textarea v-model="draft[k]" rows="4" class="mt-1 w-full border rounded-lg p-2"></textarea>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from "vue";
import http from "@/lib/http";

// 表单草稿数据（双向绑定）
const draft = reactive<Record<string, any>>({});
// 后端返回的 id
const id = ref<number | undefined>(undefined);
// 保存按钮 loading 状态
const saving = ref(false);
// 当前表单字段集合
const keys = computed(() => Object.keys(draft));

// 后端数组字段（需要转换为字符串在 textarea 中显示）
const arrayFields = ["skills", "languages", "educations"];

/* 数组 -> 字符串
   将数组字段转成逗号分隔字符串，方便在表单中显示 */
function arraysToText(obj: Record<string, any>) {
  const o = { ...obj };
  for (const k of arrayFields) {
    if (Array.isArray(o[k])) o[k] = o[k].join(","); // ["A","B","C"] → "A,B,C"
  }
  return o;
}

/* 字符串 -> 数组
   将 textarea 的逗号分隔字符串转回数组（自动 trim 去空） */
function textToArrays(obj: Record<string, any>) {
  const o = { ...obj };
  for (const k of arrayFields) {
    if (typeof o[k] === "string") {
      o[k] = o[k]
        .split(",")
        .map(s => s.trim())
        .filter(Boolean);
    }
  }
  return o;
}

/* 加载后端数据 */
async function load() {
  const { data } = await http.get("/api/about");
  const row = Array.isArray(data) ? (data[0] || {}) : data;
  id.value = row.id;
  Object.assign(draft, arraysToText(row)); // 数组 → 文本
}
onMounted(load);

/* 保存编辑内容 */
async function save() {
  saving.value = true;
  try {
    // 1. 深拷贝响应式对象，避免 Vue Proxy 干扰
    const clean = JSON.parse(JSON.stringify(draft));
    // 2. 确保 id 是 number 类型
    const numericId =
      id.value != null
        ? Number(id.value)
        : clean.id != null
        ? Number(clean.id)
        : undefined;
    // 3. 文本字段 → 数组
    const body = textToArrays(clean);
    // 4. 组装请求 payload
    const payload = { ...body, id: numericId };

    // 调用后端接口
    if (numericId) {
      await http.put("/api/about", payload); // 更新
    } else {
      const { data } = await http.post("/api/about", payload); // 新建
      id.value = data?.id;
    }

    await load(); // 刷新数据
  } finally {
    saving.value = false;
  }
}
</script>
