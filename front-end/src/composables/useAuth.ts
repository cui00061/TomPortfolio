/*
文件: useAuth.ts
语言: TypeScript（注释用 JS 风格）
作用: 提供登录/登出与认证状态管理，读写 localStorage 持久化
服务对象: Navbar、Admin 页面等，通过 useAuth() 获取 { token, username, role, isAuthed, login, logout }
说明:
- isAuthed: 只要有 token 就视为已登录
- login: POST /api/auth/login，预期返回 { token, username, role }，并写入 localStorage
- logout: 清空内存与 localStorage 中的认证信息
*/

/*
File: useAuth.ts
Language: TypeScript (JS-style comments)
Purpose: Auth state management (login/logout) with localStorage persistence
Used by: Navbar, Admin pages via useAuth()
Notes:
- isAuthed: true if token exists
- login: POST /api/auth/login, expects { token, username, role }, then persists to localStorage
- logout: clears refs and localStorage
*/

import { ref, computed } from "vue";
import http from "@/lib/http";

// 初始化为 localStorage 值；显式标注 <string | null>，避免类型歧义
// Initialize from localStorage; explicit <string | null> to avoid ambiguity
const token = ref<string | null>(localStorage.getItem("token"));
const username = ref<string | null>(localStorage.getItem("username"));
const role = ref<string | null>(localStorage.getItem("role"));

export function useAuth() {
  // 有 token 即视为已登录 / Authenticated if a token exists
  const isAuthed = computed(() => !!token.value);

  /*
    登录:
    - 发送账号密码到后端；预期返回 { token, username, role }
    - 保存到 ref 和 localStorage（刷新后仍保持登录态）
    - 这里保留参数类型 (u: string, p: string) 以满足 noImplicitAny
    Login:
    - POST credentials to backend; expect { token, username, role }
    - Save to refs and localStorage (persist across refresh)
    - Keep param types to satisfy noImplicitAny
  */
  async function login(u: string, p: string) {
    const { data } = await http.post("/api/auth/login", { username: u, password: p });
    token.value = data.token;
    username.value = data.username || u;  // 后端没返回时回退为输入账号 / fallback to input username
    role.value = data.role || "ADMIN";    // 给出默认角色 / default role to avoid null

    // 使用非空断言 (!) 因为 setItem 需要 string；此时 token/username/role 已被赋值
    // Use non-null assertion (!) since setItem requires string and values are set above
    localStorage.setItem("token", token.value!);
    localStorage.setItem("username", username.value!);
    localStorage.setItem("role", role.value!);
  }

  /*
    登出:
    - 清空内存中的状态并移除本地存储
    Logout:
    - Reset in-memory state and clear local storage
  */
  function logout() {
    token.value = null;
    username.value = null;
    role.value = null;
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("role");
  }

  // 对外暴露的状态与方法 / Exposed state and actions
  return { token, username, role, isAuthed, login, logout };
}
