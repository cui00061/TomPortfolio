// ===========================================================
// 文件：vite.config.js
// 功能：Vite 项目的配置文件，控制构建与开发行为
// - 配置 Vue 插件和 TailwindCSS 插件
// - 设置路径别名（简化 import 引用）
// Author: Tom
// ===========================================================
// File: vite.config.js
// Purpose: Vite configuration file, controls build & dev settings
// - Configures Vue plugin and TailwindCSS plugin
// - Defines path aliases (simplifies import statements)
// Author: Tom
// ===========================================================

import { defineConfig } from 'vite'         // Vite 配置方法 / Vite config function
import vue from '@vitejs/plugin-vue'        // Vue 插件支持 .vue 文件 / Vue plugin for .vue files
import tailwindcss from '@tailwindcss/vite' // TailwindCSS 插件 / TailwindCSS plugin
import { fileURLToPath, URL } from 'node:url' // Node 工具：处理路径 / Node utils for file paths

// 导出 Vite 配置
// Export Vite configuration
export default defineConfig({
  // 插件：启用 Vue 与 TailwindCSS
  // Plugins: enable Vue and TailwindCSS
  plugins: [vue(), tailwindcss()],

  // 路径解析设置
  // Path resolution settings
  resolve: {
    alias: {
      // @ → src 目录
      // @ → src directory
      '@': fileURLToPath(new URL('./src', import.meta.url)),

      // @components → src/components 目录
      // @components → src/components directory
      '@components': fileURLToPath(new URL('./src/components', import.meta.url)),

      // @pages → src/pages 目录
      // @pages → src/pages directory
      '@pages': fileURLToPath(new URL('./src/pages', import.meta.url)),

      // @assets → src/assets 目录
      // @assets → src/assets directory
      '@assets': fileURLToPath(new URL('./src/assets', import.meta.url)),
    }
  }
})
