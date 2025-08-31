/* ===========================================================
 文件：env.d.ts
 功能：TypeScript 环境声明文件
 - 为 Vite 项目提供类型提示
 - 声明对 .vue 文件的模块支持，避免 TS 报错
 作者：Tom
 ===========================================================
 File: env.d.ts
 Purpose: TypeScript environment declaration file
 - Provides type hints for Vite projects
 - Declares module support for .vue files to prevent TS errors
 Author: Tom
=========================================================== */

/// <reference types="vite/client" />
// 引入 Vite 默认类型定义，提供环境变量和 HMR 的类型提示
// Reference Vite default type definitions (env vars, HMR, etc.)

declare module '*.vue' {
  // 导入 Vue 的 DefineComponent 类型
  // Import DefineComponent type from Vue
  import type { DefineComponent } from 'vue'

  // 定义所有以 .vue 结尾的文件模块类型
  // Define type for all .vue file modules
  const component: DefineComponent<{}, {}, any>

  // 默认导出组件
  // Default export of the component
  export default component
}
