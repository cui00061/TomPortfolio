<!--
===============================================================
文件：src/pages/Contact.vue
功能：前台「联系我」页面
- 表单包含 Name / Email / Message
- 使用原生表单校验（reportValidity）与“提交后才显示错误样式”
- 成功与失败提示（submitted / error）
- 使用 axios 向后端 POST /api/contact
===============================================================
File: src/pages/Contact.vue
Purpose: Public "Contact" page
- Form fields: Name / Email / Message
- Uses native form validation (reportValidity) and shows errors only after submit
- Success & failure notices (submitted / error)
- Sends POST /api/contact via axios
===============================================================
-->

<template>
  <section class="mt-16 max-w-lg mx-auto space-y-6">
    <!-- 标题 / Heading -->
    <h2 class="text-4xl md:text-5xl font-extrabold tracking-tight text-center text-nature-brown mb-6">
      Talk to Me
    </h2>

    <!-- 表单：使用原生校验 + 受控状态 / Form: native validation + controlled state -->
    <form ref="formEl" @submit.prevent="handleSubmit" class="space-y-4">
      <!-- Name -->
      <div>
        <label for="name" class="block mb-1">Name</label>
        <input
          id="name"
          v-model.trim="form.name"
          type="text"
          required
          class="w-full px-4 py-2 rounded-lg border border-nature-light
                 focus:ring-2 focus:ring-nature-green outline-none"
          placeholder="Your name"
          autocomplete="name"
        />
      </div>

      <!-- Email：只在“提交过且无效”时显示错误样式 / Email: show error style only after submit & invalid -->
      <div>
        <label for="email" class="block mb-1">Email</label>
        <input
          id="email"
          ref="emailEl"
          v-model.trim="form.email"
          type="email"
          required
          inputmode="email"
          autocomplete="email"
          placeholder="you@example.com"
          class="w-full px-4 py-2 rounded-lg border outline-none focus:ring-2"
          :class="triedSubmit && emailInvalid
            ? 'border-red-400 focus:ring-red-500'
            : 'border-nature-light focus:ring-nature-green'"
        />
        <!-- 提交后无效时的提示 / Helper text when invalid after submit -->
        <p v-if="triedSubmit && emailInvalid" class="text-red-600 text-sm mt-1">
          Please enter a valid email.
        </p>
      </div>

      <!-- Message -->
      <div>
        <label for="message" class="block mb-1">Message</label>
        <textarea
          id="message"
          v-model.trim="form.message"
          rows="5"
          required
          class="w-full px-4 py-2 rounded-lg border border-nature-light
                 focus:ring-2 focus:ring-nature-green outline-none"
          placeholder="How can I help?"
        ></textarea>
      </div>

      <!-- 提交按钮（自定义 Button 组件） / Submit button (custom Button component) -->
      <Button type="submit">Send Message</Button>
    </form>

    <!-- 提交结果提示 / Submission result notices -->
    <p v-if="submitted" class="text-nature-green">✅ Message sent successfully!</p>
    <p v-if="error" class="text-red-600">❌ Failed to send message.</p>
  </section>
</template>

<script setup>
import { ref, computed } from 'vue'
import axios from 'axios'

/* 表单与控件引用 / Refs to form and inputs */
const formEl = ref(null)
const emailEl = ref(null)

/* 受控表单数据 / Controlled form state */
const form = ref({ name: '', email: '', message: '' })

/* 提交状态 / Submission states */
const submitted = ref(false)
const error = ref(false)

/* 仅在“点过提交”后才进行错误可视化 / Visualize error only after attempted submit */
const triedSubmit = ref(false)
const emailInvalid = computed(() =>
  emailEl.value ? !emailEl.value.checkValidity() : false
)

/* 提交处理：
   - 先设置 triedSubmit=true，以驱动错误样式
   - 使用原生校验：不通过则 reportValidity() 显示浏览器内置提示并中断
   - 通过后发送请求；成功则清空表单并显示成功提示，失败则显示错误提示
   Submit handler:
   - Set triedSubmit=true to enable error visuals
   - Use native validation; if invalid, call reportValidity() and return
   - On success, clear form & show success notice; on failure, show error notice */
const handleSubmit = async () => {
  triedSubmit.value = true

  // 原生校验：任一字段不通过则阻止提交并触发原生提示气泡
  // Native validation: block submit and show browser tooltip when invalid
  if (!formEl.value.checkValidity()) {
    formEl.value.reportValidity?.()
    return
  }

  try {
    await axios.post('http://localhost:8080/api/contact', form.value)
    submitted.value = true
    error.value = false
    form.value = { name: '', email: '', message: '' } // 清空表单 / reset form
    triedSubmit.value = false
  } catch (err) {
    console.error('Submission failed:', err)
    submitted.value = false
    error.value = true
  }
}
</script>

<style scoped>
/* 这些是基础样式示例；本页面主要靠 Tailwind 原子类完成布局与样式
   These are baseline examples; most styling relies on Tailwind utilities */
.contact {
  max-width: 600px;
  margin: 0 auto;
  padding: 2rem;
}
form {
  display: flex;
  flex-direction: column;
}
label {
  margin-bottom: 1rem;
}
input,
textarea {
  padding: 0.5rem;
  font-size: 1rem;
  width: 100%;
}
button {
  padding: 0.7rem;
  font-size: 1rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
</style>
