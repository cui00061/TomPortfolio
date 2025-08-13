<template>
  <section class="mt-16 max-w-lg mx-auto space-y-6">
    <h2 class="text-4xl md:text-5xl font-extrabold tracking-tight text-center text-nature-brown mb-6">
      Talk to Me
    </h2>


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

      <!-- Email：只在“提交后且无效”时变红 -->
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

      <Button type="submit">Send Message</Button>
    </form>

    <p v-if="submitted" class="text-nature-green">✅ Message sent successfully!</p>
    <p v-if="error" class="text-red-600">❌ Failed to send message.</p>
  </section>
</template>

<script setup>
import { ref, computed } from 'vue'
import axios from 'axios'

const formEl = ref(null)
const emailEl = ref(null)

const form = ref({ name: '', email: '', message: '' })
const submitted = ref(false)
const error = ref(false)

// 只有“点过提交”才去显示错误样式/提示
const triedSubmit = ref(false)
const emailInvalid = computed(() => emailEl.value ? !emailEl.value.checkValidity() : false)

const handleSubmit = async () => {
  triedSubmit.value = true

  // 原生校验：任一字段不通过则阻止提交并触发原生提示气泡
  if (!formEl.value.checkValidity()) {
    formEl.value.reportValidity?.()
    return
  }

  try {
    await axios.post('http://localhost:8080/api/contact', form.value)
    submitted.value = true
    error.value = false
    form.value = { name: '', email: '', message: '' }
    triedSubmit.value = false
  } catch (err) {
    console.error('Submission failed:', err)
    submitted.value = false
    error.value = true
  }
}
</script>

<style scoped>
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
