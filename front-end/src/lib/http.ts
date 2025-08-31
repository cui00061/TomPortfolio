// ===============================================================
// 文件：src/lib/http.ts
// 功能：统一封装 Axios 实例，提供请求和响应拦截
// - 在请求头中自动附加 token
// - 遇到 401 状态码时清理本地存储并跳转到登录页
// ===============================================================
//
// File: src/lib/http.ts
// Purpose: Encapsulate Axios instance with request/response interceptors
// - Automatically attach token in request headers
// - Handle 401 (Unauthorized) by clearing local storage and redirecting
// ===============================================================

import axios from "axios"

// 创建 axios 实例，统一配置
const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || "http://localhost:8080", // 优先使用环境变量，否则回退本地后端
  timeout: 15000, // 请求超时时间
})

/* 请求拦截器
   - 在每个请求头中自动附加 token（如果存在） */
http.interceptors.request.use((config) => {
  const token = localStorage.getItem("token")
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

/* 响应拦截器
   - 正常响应直接返回
   - 401（未认证/登录过期）时：
       1. 清除本地存储的认证信息
       2. 强制跳转到 /signin 页面 */
http.interceptors.response.use(
  (r) => r,
  (err) => {
    if (err?.response?.status === 401) {
      localStorage.removeItem("token")
      localStorage.removeItem("username")
      localStorage.removeItem("role")
      window.location.href = "/signin"
    }
    return Promise.reject(err)
  }
)

export default http
