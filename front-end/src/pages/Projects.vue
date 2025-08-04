<!-- Projects.vue -->
<template>
    <section class="projects">
        <h1>My Works</h1>

        <div v-if="projects.length">
            <div class="project-card" v-for="proj in projects" :key="proj.id">
                <img :src="proj.image" alt="project image" class="project-img" />
                <h2>{{ proj.title }}</h2>
                <p>{{ proj.description }}</p>
                <a :href="proj.link" target="_blank">View on GitHub</a>
            </div>
        </div>

        <div v-else>
            <p>Loading my projects...</p>
        </div>
    </section>
</template>


<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const projects = ref([]);

onMounted(async () => {
    try{
        const res = await axios.get('http://localhost:8080/api/projects');
        projects.value=res.data;
    }catch(err){
        console.error('Failed to fetch projects info:', err)
    }

})
</script>


<style scoped>
.projects {
  padding: 2rem;
}

.project-card {
  border: 1px solid #ddd;
  padding: 1rem;
  margin-bottom: 1.5rem;
  border-radius: 8px;
  background-color: #fafafa;
}

.project-img {
  max-width: 100%;
  height: auto;
  border-radius: 4px;
}

a {
  display: inline-block;
  margin-top: 0.5rem;
  color: #007bff;
  text-decoration: none;
}
</style>