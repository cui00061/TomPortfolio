<template>
  <nav class="flex items-center justify-between bg-white/60 backdrop-blur-md rounded-2xl
                 px-6 py-4 shadow-lg sticky top-4 mx-4 z-50">
    <div class="text-xl font-semibold text-nature-brown">
      Tom’s Portfolio
    </div>
    <ul class="flex space-x-6">
      <li v-for="link in links" :key="link.to" class="group">
        <RouterLink
          :to="link.to"
          v-slot="{ href, navigate, isActive }"
        >
          <a
            :href="href"
            @click="navigate"
            class="inline-block transition-all duration-250
                   transform group-hover:-translate-y-1"
            :class="isActive
              ? 'font-extrabold text-nature-green'
              : 'font-medium text-nature-brown/80 group-hover:text-nature-green'
            "

          >
            {{ link.name }}
          </a>
        </RouterLink>
      </li>
    </ul>
  </nav>
</template>

<script setup>
const links = [
  { name: 'Home',    to: '/'       },
  { name: 'About',   to: '/about'  },
  { name: 'Contact', to: '/contact'},
  { name: 'Projects',to: '/projects'},
]
</script>
