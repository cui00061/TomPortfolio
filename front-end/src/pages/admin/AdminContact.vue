<!--
===============================================================
文件：src/pages/admin/AdminContact.vue
功能：管理员后台 Contact 模块的消息管理页面
- 展示所有访客提交的联系消息
- 支持刷新列表和删除单条消息
- 表格展示消息详情：姓名、邮箱、内容、创建时间
作者：Tom
===============================================================
File: src/pages/admin/AdminContact.vue
Purpose: Admin panel page for managing contact messages
- Display all messages submitted by visitors
- Support refresh list and delete specific messages
- Show details in table: name, email, message, created time
Author: Tom
===============================================================
-->

<template>
  <section class="space-y-4">
    <header class="flex items-center justify-between">
      <h2 class="text-2xl font-semibold text-nature-brown">Contact Messages</h2>
      <!-- 刷新按钮 / Refresh button -->
      <button class="rounded-lg border px-3 py-1 hover:bg-white" @click="fetchList" :disabled="loading">
        {{ loading ? 'Loading…' : 'Refresh' }}
      </button>
    </header>

    <!-- 消息表格 / Table of messages -->
    <div class="bg-white/80 border rounded-xl overflow-x-auto">
      <table class="w-full text-sm">
        <thead>
          <tr class="text-left border-b">
            <th class="p-3">Name</th>
            <th class="p-3">Email</th>
            <th class="p-3">Message</th>
            <th class="p-3 whitespace-nowrap">Created</th>
            <th class="p-3 text-right">Action</th>
          </tr>
        </thead>
        <tbody>
          <!-- 循环渲染消息 / Render messages in loop -->
          <tr v-for="m in list" :key="m.id" class="border-b align-top">
            <td class="p-3 font-medium">{{ m.name }}</td>
            <td class="p-3">{{ m.email }}</td>
            <td class="p-3 whitespace-pre-wrap">{{ m.message }}</td>
            <td class="p-3 whitespace-nowrap">{{ formatDate(m.createdAt) }}</td>
            <td class="p-3 text-right">
              <!-- 删除按钮 / Delete button -->
              <button
                class="rounded-lg border px-3 py-1 border-red-300 text-red-600 hover:bg-red-50"
                @click="remove(m.id)"
              >
                Delete
              </button>
            </td>
          </tr>

          <!-- 无消息提示 / No messages placeholder -->
          <tr v-if="!loading && list.length === 0">
            <td class="p-6 text-center text-nature-brown/60" colspan="5">No messages yet.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import http from '@/lib/http'

// Contact 消息类型定义 / Type definition for contact messages
type ContactMsg = {
  id: number
  name: string
  email: string
  message: string
  createdAt?: string
}

// 消息列表 / Message list
const list = ref<ContactMsg[]>([])
// 加载状态 / Loading state
const loading = ref(false)

/* 获取消息列表 / Fetch message list */
async function fetchList() {
  loading.value = true
  try {
    const { data } = await http.get('/api/contact/admin')
    list.value = Array.isArray(data) ? data : []
  } finally {
    loading.value = false
  }
}

/* 删除消息 / Delete a message */
async function remove(id: number) {
  if (!confirm('Delete this message?')) return
  await http.delete(`/api/contact/admin/${id}`)
  // 本地列表同步更新 / Update local list after deletion
  list.value = list.value.filter(x => x.id !== id)
}

/* 格式化日期 / Format date */
function formatDate(s?: string) {
  if (!s) return ''
  try { 
    return new Date(s).toLocaleString() 
  } catch { 
    return s 
  }
}

// 页面挂载时加载消息 / Fetch list on component mount
onMounted(fetchList)
</script>
