<template>
  <transition
    appear
    enter-active-class="transition-all duration-1000 ease-out"
    enter-from-class="opacity-0 translate-y-16"
    enter-to-class="opacity-100 translate-y-0"
  >
    <section class="mt-16 text-center space-y-8">
      <h1 class="text-6xl font-extrabold leading-tight text-nature-brown">
        👋 Welcome to My Portfolio
      </h1>
      <p class="text-xl font-medium text-nature-brown/80 max-w-xl mx-auto">
        I’m Tom — a full-stack developer specializing in Spring Boot and Vue.js.
      </p>
      <router-link
        to="/projects"
        class="inline-block px-6 py-3 rounded-xl font-semibold text-nature-brown
               border-2 border-nature-brown bg-transparent
               hover:bg-nature-brown hover:text-white
               transition duration-200 ease-in-out transform hover:scale-105
               focus:outline-none focus:ring-2 focus:ring-nature-green"
      >
        View My Work
      </router-link>
    </section>
  </transition>
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
