<template>
  <section class="contact">
    <h1>Contact Me</h1>

    <form @submit.prevent="handleSubmit">
      <label>
        Name:
        <input v-model="form.name" type="text" required />
      </label>

      <label>
        Email:
        <input v-model="form.email" type="email" required />
      </label>

      <label>
        Message:
        <textarea v-model="form.message" required></textarea>
      </label>

      <button type="submit">Send Message</button>
    </form>

    <p v-if="submitted">✅ Message sent successfully!</p>
    <p v-if="error" style="color: red;">❌ Failed to send message.</p>
  </section>
</template>

<script setup>
    import { ref } from 'vue'
    import axios from 'axios'

    // 创建响应式表单对象
    const form = ref({
    name: '',
    email: '',
    message: ''
    })

    const submitted = ref(false)
    const error = ref(false)

    // 表单提交处理函数
    const handleSubmit = async () => {
    try {
        await axios.post('http://localhost:8080/api/contact', form.value)
        submitted.value = true
        error.value = false
        form.value = { name: '', email: '', message: '' } // 清空表单
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
