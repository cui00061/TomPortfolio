# Portfolio :re

----------

-------------------

# 设计阶段：

[^]: (Last modified 2025-07-18)



```
portfolio-site/
├── backend/                    ← Spring Boot 项目（使用 IntelliJ IDEA）
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/yourname/portfolio/
│   │   │   │   ├── controller/              ← 接收前端请求（Spring MVC @RestController）
│   │   │   │   ├── service/                 ← 业务处理逻辑（Spring @Service）
│   │   │   │   ├── repository/              ← 数据访问层（Spring Data JPA）
│   │   │   │   ├── model/                   ← 数据实体类（@Entity）
│   │   │   │   ├── config/                  ← 邮件、跨域等配置类
│   │   │   │   └── PortfolioApplication.java← Spring Boot 启动入口
│   │   │   └── resources/
│   │   │       ├── application.yml          ← 配置数据库、端口、邮箱等
│   │   │       └── static/                  ← 可选：用于部署前端静态文件（非必须）
│   └── pom.xml                             ← Maven 项目依赖管理
│
├── frontend/                   ← Vue 项目（使用 VS Code）
│   ├── public/                 ← 静态资源（favicon、index.html 模板）
│   ├── src/
│   │   ├── assets/             ← 图片、样式等资源文件
│   │   ├── components/         ← 可复用的组件（卡片、导航栏、页脚等）
│   │   ├── views/              ← 各页面视图（About、Projects、Contact）
│   │   ├── router/             ← 前端路由设置（Vue Router）
│   │   ├── App.vue             ← 根组件
│   │   └── main.js             ← Vue 项目入口
│   └── package.json           ← Node 包配置，包含依赖和启动命令
│
└── README.md                  ← 项目说明文档
```



---------

## 🗂️ 每一部分详细说明

### 🔹 backend/（用 IntelliJ IDEA）

> **技术**：Java + Spring Boot + Spring MVC + Spring Data JPA + MySQL/H2
>  **作用**：提供 REST API 接口，处理数据、表单、邮件发送

| 目录                        | 作用                                                |
| --------------------------- | --------------------------------------------------- |
| `controller/`               | 定义 REST API（如 `/api/projects`, `/api/contact`） |
| `service/`                  | 实现业务逻辑（如：保存数据、发邮件）                |
| `repository/`               | 操作数据库（基于 Spring Data JPA）                  |
| `model/`                    | 数据模型（实体类，对应数据库表）                    |
| `config/`                   | 配置类，如跨域、邮件服务、登录控制（可选）          |
| `application.yml`           | 数据源配置、邮箱设置、端口设置等                    |
| `PortfolioApplication.java` | 应用启动主类                                        |

### 🔹 frontend/（用 VS Code）

> **技术**：HTML + CSS + Vue.js + Axios + Vue Router
>  **作用**：构建前端页面，发起请求，渲染后端返回的数据

| 目录                | 作用                                               |
| ------------------- | -------------------------------------------------- |
| `public/index.html` | 页面入口模板，挂载 Vue                             |
| `views/`            | 页面结构（如 Home.vue、Projects.vue、About.vue）   |
| `components/`       | 小组件（如 ProjectCard.vue, ContactForm.vue）      |
| `router/`           | 定义页面路由，如 `/projects`, `/about`, `/contact` |
| `assets/`           | 静态资源（头像、图片、图标等）                     |
| `App.vue`           | 根组件，通常包含 `<router-view />`                 |
| `main.js`           | Vue 项目初始化、路由挂载、axios 全局配置等         |



----

### 🔹 开发工具推荐

| 部分             | 工具                                    | 原因                                                   |
| ---------------- | --------------------------------------- | ------------------------------------------------------ |
| 后端 Spring Boot | **IntelliJ IDEA**（Community/Ultimate） | 最强大的 Java IDE，集成 Maven、运行服务、调试          |
| 前端 Vue         | **VS Code**                             | 快速启动、轻量、丰富插件（Vetur / Vue Language Tools） |
| 接口测试         | **Postman** / **REST Client 插件**      | 手动测试 REST API 接口请求                             |
| 数据库管理       | **DBeaver / Navicat**                   | 可视化查看数据                                         |
| 打包部署         | **终端**（SCP、Nginx）                  | 上传 JAR、部署静态文件                                 |

## 🗂️ 各模块的功能职责一览

| 模块             | 技术                             | 说明                                        |
| ---------------- | -------------------------------- | ------------------------------------------- |
| 项目展示         | Vue 页面 + Spring API + MySQL    | `/api/projects` 获取数据，展示卡片列表      |
| 个人简介         | Vue 页面 / JSON API              | `/api/about` 可返回介绍内容或纯前端写死     |
| 联系我           | Vue 表单 + POST API + 邮件服务   | `/api/contact` 收集信息并发送邮件或入库     |
| 后台管理（可选） | Vue Admin 页面 + Spring Security | 登录后可以增删改项目、简历、博客等          |
| 部署             | AWS EC2 + Nginx + systemd        | Vue 构建为静态资源；后端用 `java -jar` 运行 |



--------

## 🗂️ 项目开发周期规

| 阶段                                             | 目标                     | 时间预估 | 前端任务                                                | 后端任务                                       | 备注                            |
| ------------------------------------------------ | ------------------------ | -------- | ------------------------------------------------------- | ---------------------------------------------- | ------------------------------- |
| **阶段 1：需求 & 设计**                          | 明确功能、页面和接口     | 0.5 天   | 确定页面结构、UI 框架                                   | 设计 API 路由、数据模型                        | 可使用画图工具 + Markdown       |
| **阶段 2：基础搭建**                             | 项目能跑起来             | 1 天     | 创建 Vue 项目、设置 Router、页面骨架                    | 创建 Spring Boot 项目、初始化 Controller       | mock 数据可开始                 |
| **阶段 3：接口设计 + mock 并行**                 | 前后端解耦开发           | 1 天     | 根据接口文档编写 axios 请求，mock 数据展示              | 创建 Controller、Service 接口，返回静态数据    | 接口命名规范、数据结构对齐      |
| **阶段 4：功能实现（About、Projects、Contact）** | 核心功能开发             | 4～5 天  | 完善页面 UI、调用接口显示数据、表单校验                 | 实现 Service + Repository + Entity、数据库配置 | 建议每完成一个功能就联调一次    |
| **阶段 5：联调测试**                             | 前后端通信完整           | 2 天     | 调用真实后端接口、处理响应                              | 调整接口返回、处理异常、加日志                 | 关注状态码、错误处理、跨域配置  |
| **阶段 6：样式和交互优化**                       | 页面美化、移动端适配     | 1～2 天  | 使用 CSS 框架（Tailwind/Element Plus），动画优化        | 无                                             | 用户体验阶段                    |
| **阶段 7：部署**                                 | 上线并测试               | 1～2 天  | 前端构建 `dist`，部署到 Nginx 或放入 Spring Boot static | 后端部署到 EC2，配置邮件服务、数据库           | 可用 Github Actions 自动部署    |
| **阶段 8：Bug 修复 & 收尾**                      | 清理代码、文档、优化体验 | 1 天     | 修复 UI Bug，更新 README                                | 日志、异常、接口文档                           | 项目可加 Portfolio 项目展示自己 |