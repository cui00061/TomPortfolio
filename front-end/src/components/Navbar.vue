<!-- src/components/Navbar-->
 <template>
    <nav class = "navbar">
        <ul>
            <li><RouterLink to="/">Home</RouterLink></li>
            <li><RouterLink to="/about">About</RouterLink></li>
            <li><RouterLink to="/contact">Contact</RouterLink></li>
            <li><RouterLink to="/projects">Projects</RouterLink></li>
        </ul>
    </nav>
 </template>

<!--For script use-->
 <script setup>

</script>


<style scoped>
.navbar {
  background-color: #f5f5f5;
  padding: 10px;
}
.navbar ul {
  display: flex;
  list-style: none;
  gap: 20px;
}
.navbar a {
  text-decoration: none;
  color: #333;
}
.navbar a.router-link-active {
  font-weight: bold;
  color: #007BFF;
}
</style>