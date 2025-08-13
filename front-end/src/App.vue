<script setup>
  import Navbar from './components/NavBar.vue';
</script>

<template>
  <div class="min-h-screen text-nature-brown font-sans bg-transparent">
    <div class="max-w-4xl mx-auto px-4 py-8">
      <Navbar />
      <router-view />
    </div>
  </div>
</template>



<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
