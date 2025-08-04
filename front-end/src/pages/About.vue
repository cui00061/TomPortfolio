<template>
  <section class="about">
    <h1>About Me</h1>

    <div v-if="about">
      <p><strong>{{ about.title }}</strong></p>
      <p>{{ about.bio }}</p>

      
      <section class="info-section">
        <h2>Skills</h2>
        <ul>
          <li v-for="skill in about.skills" :key="skill">{{ skill }}</li>
        </ul>
      </section>

      <section class="info-section">
        <h2>Education</h2>
        <ul>
          <li v-for="edu in about.educations" :key="edu">{{ edu }}</li>
        </ul>
      </section>

      <section class="info-section">
        <h2>Languages</h2>
        <ul>
          <li v-for="lang in about.languages" :key="lang">{{ lang }}</li>
        </ul>
      </section>
    </div>

    <div v-else>
      <p>Loading my info...</p>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const about = ref(null)

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
.about {
  padding: 2rem;
}
.info-section {
  margin-top: 2rem;
}
ul {
  padding-left: 1.5rem;
}
</style>
