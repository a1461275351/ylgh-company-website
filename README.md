# 杨凌国合跨境贸易官网

公司官方网站项目，由前端展示站点和后台管理系统组成，前后端分离架构、单仓统一管理。

**主营业务**：跨境农产品贸易、二手车出口、中欧班列冷链物流、海外仓储。
依托杨凌自贸片区、上合组织农业基地及综合保税区，业务覆盖全球 20+ 国家。

---

## 项目结构

```
dev_local_gong/
├── my-website/                 # 前端：静态 HTML/CSS/JS + Express 代理
│   ├── index.html              # 首页
│   ├── about.html              # 关于我们
│   ├── products.html           # 产品中心
│   ├── global.html             # 全球布局
│   ├── news.html               # 新闻动态
│   ├── contact.html            # 联系我们
│   ├── css/                    # 样式
│   ├── js/                     # 脚本（按页面分模块）
│   │   ├── api.js              # API 请求封装
│   │   ├── i18n.js             # 国际化
│   │   ├── main.js             # 全局逻辑
│   │   └── *-page.js           # 各页面专属脚本
│   ├── images/                 # 静态资源
│   ├── server.js               # 开发服务器（端口 8080，代理 /api → 8081）
│   └── package.json
│
├── my-website-admin/           # 后端：Spring Boot 管理系统 + REST API
│   ├── src/main/java/com/ylguohe/admin/
│   │   ├── AdminApplication.java
│   │   ├── controller/admin/   # 后台 Thymeleaf 页面（/admin/**）
│   │   ├── controller/api/     # 公开 REST API（/api/**）
│   │   ├── entity/             # JPA 实体
│   │   ├── repository/         # 数据访问层
│   │   ├── service/            # 业务逻辑
│   │   ├── config/             # CORS、数据初始化、WebMvc 配置
│   │   └── exception/          # 全局异常处理
│   ├── src/main/resources/
│   │   ├── application.yml         # 主配置
│   │   ├── application-dev.yml     # 开发环境（H2）
│   │   ├── templates/admin/        # Thymeleaf 后台模板
│   │   └── static/admin/           # 后台静态资源
│   ├── data/                       # H2 数据库文件（已 gitignore）
│   ├── uploads/                    # 用户上传文件（已 gitignore）
│   └── pom.xml
│
├── .gitignore
└── README.md
```

---

## 技术栈

### 前端（my-website）

| 项 | 说明 |
|---|---|
| 框架 | 无（纯静态 HTML / CSS / 原生 JS） |
| 开发服务器 | Express 4 + http-proxy-middleware |
| 字体 | Google Fonts（Noto Sans/Serif SC） |

### 后端（my-website-admin）

| 项 | 版本 / 说明 |
|---|---|
| JDK | 1.8 |
| Spring Boot | 2.7.18 |
| 持久层 | Spring Data JPA |
| 数据库 | H2 文件库（开发）/ MySQL 8（生产，依赖已就绪） |
| 视图层 | Thymeleaf + Layout Dialect |
| 其它 | Lombok、Validation、DevTools |

---

## 快速开始

### 环境要求

| 组件 | 版本 |
|---|---|
| JDK | 1.8+ |
| Maven | 3.6+ |
| Node.js | 16+ |
| npm | 8+ |

### 一、启动后端

```bash
cd my-website-admin
mvn spring-boot:run
```

**首次启动会自动完成**：

1. 创建 `data/guohe-admin.mv.db` 数据库文件
2. 根据 `@Entity` 自动建表（11 张业务表）
3. `DataInitializer` 灌入种子数据（Banner、产品、新闻、物流路线等）
4. 创建 `uploads/` 上传目录

启动成功后可访问：

- 后台管理：<http://localhost:8081/admin/dashboard>
- H2 控制台：<http://localhost:8081/h2-console>
  - JDBC URL：`jdbc:h2:file:./data/guohe-admin`
  - 用户名：`sa`，密码留空

### 二、启动前端

```bash
cd my-website
npm install        # 首次需要装依赖
npm run dev
```

访问：<http://localhost:8080>

前端通过 Express 把 `/api/**` 代理到后端 8081，本地开发无跨域问题。

### 三、推荐启动顺序

**先启动后端，再启动前端**。首页、产品、新闻等页面会在加载时调用 `/api/**`，后端未启动会有数据空缺。

---

## 端口与服务

| 服务 | 端口 | 用途 |
|---|---|---|
| 前端 dev server | 8080 | 静态资源 + API 代理 |
| 后端 Spring Boot | 8081 | 后台管理页面 + REST API |
| H2 控制台 | 8081 | `/h2-console`（仅 dev profile 开启） |

---

## 业务模块清单

| 模块 | 实体 | 后台路径 | 公开 API |
|---|---|---|---|
| 首页 Banner | `HeroBanner` | `/admin/banners` | `GET /api/banners` |
| 首页统计 | `Statistic` | `/admin/statistics` | `GET /api/statistics` |
| 产品中心 | `Product` | `/admin/products` | `GET /api/products` |
| 二手车出口 | `VehicleDestination` | `/admin/vehicles` | `GET /api/vehicles/destinations` |
| 公司发展史 | `CompanyTimeline` | `/admin/company/timeline` | `GET /api/company/timeline` |
| 资质荣誉 | `Qualification` | `/admin/company/qualifications` | `GET /api/company/qualifications` |
| 物流路线 | `LogisticsRoute` | `/admin/global/routes` | `GET /api/global/routes` |
| 海外仓 | `Warehouse` | `/admin/global/warehouses` | `GET /api/global/warehouses` |
| 新闻动态 | `NewsArticle` | `/admin/news` | `GET /api/news` |
| 联系信息 | `ContactInfo` | `/admin/contact/info` | `GET /api/contact` |
| 客户询盘 | `ContactInquiry` | `/admin/contact/inquiries` | `POST /api/contact/inquiry` |

**双层 Controller 设计**：

- `/admin/**`：返回 Thymeleaf 页面，供管理员使用
- `/api/**`：返回 JSON，供前端站点消费

---

## 数据初始化机制

后端启动时 `DataInitializer`（实现 `CommandLineRunner`）会执行：

```java
if (productRepository.count() > 0) {
    log.info("Database already initialized, skipping seed data.");
    return;
}
// 灌入 Banner / 统计 / 产品 / 新闻 / 路线 / 海外仓 / 联系信息等
```

**关键特性**：

- 只在产品表为空时执行，重启应用不会重复灌数据
- **重置数据**：停应用 → 删除 `data/guohe-admin.mv.db` → 重启
- **新增种子数据**：编辑 `config/DataInitializer.java`

---

## 文件上传

- 上传目录：`my-website-admin/uploads/`（应用启动时由 `FileStorageService` 自动创建）
- 单文件上限：10MB（见 `application.yml` 的 `spring.servlet.multipart.max-file-size`）
- 访问方式：`http://localhost:8081/uploads/<filename>`
- 文件命名：UUID + 原扩展名

---

## 开发约定

### Git 工作流

- 主分支：`main`，受保护，**禁止直接 push**
- 功能分支命名：`feat/xxx`、`fix/xxx`、`chore/xxx`、`docs/xxx`
- 所有变更通过 Pull Request 合并到 `main`
- commit message 描述"做了什么 + 为什么"，中英文皆可

### 忽略文件规则

项目根 `.gitignore` 已覆盖：

- Java 构建产物：`target/`、`*.class`、`*.jar`
- IDE 配置：`.idea/`、`.vscode/`、`*.iml`
- 数据库文件：`*.mv.db`、`*.trace.db`
- 用户上传：`my-website-admin/uploads/`
- Node 依赖：`node_modules/`、构建产物 `dist/`、`.vite/`
- 系统元数据：`.DS_Store`、`._*`、`Thumbs.db`
- 凭证密钥：`*.pem`、`*.key`、`*.jks`、`secrets/`

### 前端开发

- API 调用统一通过 `js/api.js` 封装（`api.get()` / `api.post()`）
- 国际化文案集中放在 `js/i18n.js`
- 各页面专属脚本命名为 `<page>-page.js`
- 注意：6 个 HTML 页面间的导航/页脚目前未做模板复用，修改导航需同步多个文件

### 后端开发

- Controller 严格分层：
  - `controller/admin/*` 返回 Thymeleaf 页面
  - `controller/api/*` 返回 JSON
- 实体统一使用 Lombok `@Data` + `@Builder`
- 时间戳通过 `@PrePersist` / `@PreUpdate` 自动维护 `createdAt` / `updatedAt`
- 枚举值使用 `@Enumerated(EnumType.STRING)` 存字符串（便于阅读）

---

## 常见问题（FAQ）

**Q: `mvn spring-boot:run` 报"端口被占用"？**
A: 8081 被其他进程占用。Windows 用 `netstat -ano | findstr 8081` 查 PID，然后 `taskkill /F /PID <PID>` 结束。

**Q: 前端 `/api/**` 调用返回 404？**
A: 检查后端是否已启动；检查 `my-website/server.js` 中代理目标是否为 `localhost:8081`。

**Q: 想清空所有数据重新开始？**
A: 停应用 → 删除 `my-website-admin/data/` 整个目录 → 重启，会自动重建并播种。

**Q: 后台访问需要登录吗？**
A: **当前没有鉴权**（见下文已知问题）。本地开发可直接访问 `/admin/**`。

**Q: H2 控制台连不上？**
A: 确认 JDBC URL 填 `jdbc:h2:file:./data/guohe-admin`（不是默认的 `jdbc:h2:~/test`），驱动选 `Generic H2 (Embedded)`。

**Q: 在 IDE 里启动后端，数据库文件跑到了奇怪的位置？**
A: H2 用的是相对路径 `./data/`，相对于 JVM 工作目录。在 IDE 中把 Run Configuration 的 Working Directory 设为 `$MODULE_DIR$`（即 `my-website-admin/` 模块根）。

**Q: 改了 Java 代码不想重启应用？**
A: 已引入 spring-boot-devtools，IDE 中改完代码触发 Build（IntelliJ 默认 Ctrl+F9）即可热重载。

---

## 已知问题 / TODO

- [ ] **无鉴权**：`/admin/**` 完全开放，生产部署前必须补 Spring Security 或 Nginx Basic Auth
- [ ] **CORS 配置歧义**：`application.yml` 中的 `app.cors.allowed-origins` 未被代码读取，`CorsConfig.java` 硬编码了 localhost，生产环境跨域会失败
- [ ] **MySQL 切换**：生产应从 H2 切换到 MySQL，需补 `application-prod.yml`
- [ ] **前端模板复用**：6 个 HTML 页面各自维护导航/页脚，扩展性差
- [ ] **询盘通知**：客户提交询盘后没有邮件/短信通知机制
- [ ] **图片优化**：上传图片未做压缩、缩略图、CDN

---

## 生产部署建议

1. **数据库**：切换到 MySQL，配置 `application-prod.yml`，启动加 `--spring.profiles.active=prod`
2. **鉴权**：引入 Spring Security 给 `/admin/**` 加登录页和会话管理
3. **CORS**：修复 `CorsConfig` 读取 `app.cors.allowed-origins`，按白名单允许生产域名
4. **Nginx 反代**：统一 80/443 端口，前端走静态目录、`/api` 反代到后端 8081
5. **HTTPS**：通过 Nginx 配置 SSL 证书（Let's Encrypt 免费）
6. **关闭 H2 控制台**：生产 profile 设置 `spring.h2.console.enabled=false`
7. **日志**：配置 `logback-spring.xml`，日志归档到 `/var/log/guohe-admin/`
8. **打包**：
   - 后端：`mvn clean package` 生成 `target/admin-1.0.3.jar`
   - 前端：直接拷贝 `my-website/` 下静态文件（除 `node_modules` 和 `server.js`）到 Nginx 站点目录

---

## 许可证

内部项目，未公开授权。
