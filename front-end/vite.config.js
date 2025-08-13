// vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import tailwindcss from '@tailwindcss/vite'
import { fileURLToPath, URL } from 'node:url'

export default defineConfig({
  plugins: [vue(), tailwindcss()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      '@components': fileURLToPath(new URL('./src/components', import.meta.url)), // 可选
      '@pages': fileURLToPath(new URL('./src/pages', import.meta.url)),           // 可选
      '@assets': fileURLToPath(new URL('./src/assets', import.meta.url)),         // 可选
    }
  }
})
