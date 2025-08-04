<template>
  <section class="home">
    <h1>👋 Welcome to My Portfolio</h1>
    <h1 class="text-3xl font-bold text-blue-500">Hello Tailwind</h1>
    <p>I’m Tom — a full-stack developer specializing in Spring Boot and Vue.js.</p>
    <router-link to="/projects" class="cta">View My Work</router-link>
  </section>
</template>


<script setup></script>

<style scoped>
.home {
  text-align: center;
  padding: 3rem 1rem;
}

.cta {
  display: inline-block;
  margin-top: 2rem;
  padding: 0.6rem 1.2rem;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 6px;
  transition: background-color 0.3s;
}
.cta:hover {
  background-color: #0056b3;
}
</style>
