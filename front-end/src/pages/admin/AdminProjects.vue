<!--
===============================================================
文件：src/pages/admin/AdminProjects.vue
功能：管理员后台的「项目」管理页
- 列表展示所有项目（标题 + 描述要点）
- 新建/编辑使用右侧抽屉（表单提交）
- 删除单个项目，提交后刷新列表
- 文本框按“每行一项”转换为数组；保存时再转回数组
作者：Tom
===============================================================
File: src/pages/admin/AdminProjects.vue
Purpose: Admin page for managing Projects
- List all projects (title + bullet descriptions)
- Create/Edit via a drawer form on the right
- Delete a project; refresh list after mutations
- Textareas map "one item per line" to arrays for saving
Author: Tom
===============================================================
-->

<template>
  <div class="space-y-6">
    <!-- 顶部：标题 + 新建按钮 / Header: title + create button -->
    <div class="flex justify-between items-center">
      <h2 class="text-2xl font-semibold text-nature-brown">Projects</h2>
      <button class="rounded-lg bg-nature-brown text-white px-4 py-2" @click="startCreate">
        + New
      </button>
    </div>

    <!-- 列表 / Projects list -->
    <div class="grid md:grid-cols-2 gap-4">
      <article
        v-for="p in list"
        :key="p.id"
        class="bg-white/80 border rounded-xl p-4 shadow-sm"
      >
        <header class="flex justify-between items-start">
          <h3 class="font-semibold text-lg">{{ p.title }}</h3>
          <div class="flex gap-2">
            <!-- 编辑按钮 / Edit button -->
            <button
              class="rounded-lg border px-3 py-1 text-sm hover:bg-white"
              @click="startEdit(p)"
            >
              Edit
            </button>
            <!-- 删除按钮 / Delete button -->
            <button
              class="rounded-lg border px-3 py-1 text-sm hover:bg-white border-red-300 text-red-600 hover:bg-red-50"
              @click="del(p.id!)"
            >
              Delete
            </button>
          </div>
        </header>

        <!-- 描述要点（数组）/ Bullet list (array) -->
        <ul class="mt-2 list-disc pl-5 text-nature-brown/85">
          <li v-for="(d,i) in p.description" :key="i">{{ d }}</li>
        </ul>
      </article>
    </div>

    <!-- 抽屉式编辑 / Drawer editor -->
    <div v-if="editing" class="fixed inset-0 bg-black/20 flex justify-end">
      <form
        class="w-full max-w-lg h-full bg-white p-6 overflow-y-auto space-y-4"
        @submit.prevent="save"
      >
        <h3 class="text-xl font-semibold">
          {{ form.id ? 'Edit Project' : 'Create Project' }}
        </h3>

        <!-- 标题 / Title -->
        <label class="block">
          <span class="text-sm">Title</span>
          <input v-model="form.title" class="mt-1 w-full border rounded-lg p-2" required />
        </label>

        <!-- 描述：每行一项 / Description: one item per line -->
        <label class="block">
          <span class="text-sm">Description (1 item per line)</span>
          <textarea
            v-model="descText"
            rows="6"
            class="mt-1 w-full border rounded-lg p-2"
            placeholder="- bullet 1&#10;- bullet 2"
          ></textarea>
        </label>

        <!-- 截图链接：每行一条 / Screenshot URLs: one per line -->
        <label class="block">
          <span class="text-sm">Screenshot URLs (1 per line)</span>
          <textarea
            v-model="shotText"
            rows="4"
            class="mt-1 w-full border rounded-lg p-2"
            placeholder="img1.png&#10;img2.png"
          ></textarea>
        </label>

        <!-- 底部操作 / Footer actions -->
        <div class="flex justify-end gap-2 pt-2">
          <button
            type="button"
            class="rounded-lg border px-3 py-1 text-sm hover:bg-white"
            @click="close"
          >
            Cancel
          </button>
          <button class="rounded-lg bg-nature-brown text-white px-4 py-2">
            {{ saving ? 'Saving…' : 'Save' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import http from "@/lib/http";

/* 数据模型 / Data model */
type Project = {
  id?: number;
  title: string;
  description: string[];      // 描述要点数组 / bullet points
  screenshotUrls: string[];   // 截图链接数组 / screenshot URLs
};

/* 列表状态 / List state */
const list = ref<Project[]>([]);
const loading = ref(false);

/* 拉取项目列表 / Fetch project list */
async function fetchList() {
  loading.value = true;
  try {
    const { data } = await http.get<Project[]>("/api/projects");
    list.value = data;
  } finally {
    loading.value = false;
  }
}
onMounted(fetchList);

/* ---- 编辑抽屉状态 / Drawer state ---- */
const editing = ref(false);
const saving = ref(false);
const form = reactive<Project>({ title: "", description: [], screenshotUrls: [] });
const descText = ref("");
const shotText = ref("");

/* 辅助：文本↔数组 / Helpers: text ↔ array */
function toLines(s: string) {
  // 文本 → 数组（按行拆分，去空格、过滤空行）
  // Text → array (split by line, trim, filter empties)
  return s.split(/\r?\n/).map(v => v.trim()).filter(Boolean);
}
function toText(arr: string[]) {
  // 数组 → 文本（每项一行）
  // Array → text (one per line)
  return (arr || []).join("\n");
}

/* 新建流程 / Start creating */
function startCreate() {
  // 清空表单并打开抽屉 / Reset form and open drawer
  Object.assign(form, { id: undefined, title: "", description: [], screenshotUrls: [] });
  descText.value = "";
  shotText.value = "";
  editing.value = true;
}

/* 编辑流程 / Start editing */
function startEdit(p: Project) {
  // 深拷贝到 form，避免直接引用列表项 / Deep copy to avoid mutating list item
  Object.assign(form, JSON.parse(JSON.stringify(p)));
  // 将数组填入文本域（每行一项）/ Fill textarea with one item per line
  descText.value = toText(form.description);
  shotText.value = toText(form.screenshotUrls);
  editing.value = true;
}

/* 关闭抽屉 / Close drawer */
function close() {
  editing.value = false;
}

/* 保存 / Save (create or update) */
async function save() {
  saving.value = true;
  try {
    // 组装 payload：把文本域还原成数组 / Assemble payload: convert text back to arrays
    const payload: Project = {
      ...form,
      description: toLines(descText.value),
      screenshotUrls: toLines(shotText.value),
    };

    // 有 id 走更新，否则创建 / Update if id exists; otherwise create
    if (payload.id) {
      await http.put(`/api/projects/${payload.id}`, payload);
    } else {
      await http.post(`/api/projects`, payload);
    }

    // 刷新列表并关闭抽屉 / Refresh list and close drawer
    await fetchList();
    editing.value = false;
  } finally {
    saving.value = false;
  }
}

/* 删除 / Delete */
async function del(id: number) {
  if (!confirm("Delete this project?")) return;
  await http.delete(`/api/projects/${id}`);
  await fetchList();
}
</script>
