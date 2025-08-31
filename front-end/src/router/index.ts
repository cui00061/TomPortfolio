// ===============================================================
// 文件：src/router/index.ts
// 功能：应用路由配置（公开页、登录页、管理员后台 + 路由守卫）
// - 公开页：Home / About / Projects / Contact
// - 登录页：/signin
// - 管理后台：/admin，包含 projects / about / contact 三个子路由
// - 路由守卫：需要认证的路由在无 token 时重定向到 /signin
// 作者：Tom
// ===============================================================
//
// File: src/router/index.ts
// Purpose: App routing configuration (public pages, signin, admin area + guard)
// - Public: Home / About / Projects / Contact
// - Sign-in: /signin
// - Admin: /admin with child routes (projects / about / contact)
// - Guard: redirect to /signin if a protected route is accessed without token
// Author: Tom
// ===============================================================

import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

// 公开页组件（按需加载）/ Public pages (lazy-loaded)
const Home     = () => import('@/pages/Home.vue')
const About    = () => import('@/pages/About.vue')
const Projects = () => import('@/pages/Projects.vue')
const Contact  = () => import('@/pages/Contact.vue')

// 登录页与后台布局 + 子页 / Sign-in and admin layout + children
const SignIn         = () => import('@/pages/SignIn.vue')
const AdminLayout    = () => import('@/pages/admin/AdminLayout.vue')
const AdminProjects  = () => import('@/pages/admin/AdminProjects.vue')
const AdminAbout     = () => import('@/pages/admin/AdminAbout.vue')
const AdminContact   = () => import('@/pages/admin/AdminContact.vue')

const routes: RouteRecordRaw[] = [
  // 公开页 / Public routes
  { path: '/',         name: 'home',     component: Home,     meta: { public: true } },
  { path: '/about',    name: 'about',    component: About,    meta: { public: true } },
  { path: '/projects', name: 'projects', component: Projects, meta: { public: true } },
  { path: '/contact',  name: 'contact',  component: Contact,  meta: { public: true } },

  // 登录页 / Sign-in
  { path: '/signin',   name: 'signin',   component: SignIn,   meta: { public: true } },

  // 管理后台（需要登录）/ Admin area (requires auth)
  {
    path: '/admin',
    component: AdminLayout,
    meta: { requiresAuth: true },
    children: [
      { path: '',          redirect: { name: 'admin-projects' } }, // 默认跳转到 projects / default child
      { path: 'projects',  name: 'admin-projects', component: AdminProjects },
      { path: 'about',     name: 'admin-about',    component: AdminAbout },
      { path: 'contact',   name: 'admin-contact',  component: AdminContact },
    ],
  },

  // 兜底：未知路径重定向首页 / Fallback: redirect unknown paths to home
  { path: '/:pathMatch(.*)*', redirect: '/' },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  // 切换路由时回到顶部 / Scroll to top on navigation
  scrollBehavior: () => ({ top: 0 }),
})

// 简单守卫：需要认证且无 token 时跳转登录
// Simple guard: if route requires auth and no token, redirect to signin
router.beforeEach((to) => {
  if (to.meta.requiresAuth && !localStorage.getItem('token')) {
    return { name: 'signin', query: { redirect: to.fullPath } }
  }
})

export default router
