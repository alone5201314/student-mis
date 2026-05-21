

## 技术栈

- 后端：Spring Boot 2.2、MyBatis、PageHelper、JWT、MySQL
- 前端：Vue 2、Vue Router、Vuex、Element UI、Axios、ECharts
- 数据库：MySQL，初始化脚本为 `student_mis.sql`

## 功能模块

- 登录认证：按管理员、教师、学生三类角色登录
- 用户管理：管理员、教师、学生账号的新增、修改、删除和查询
- 课程管理：课程信息维护、教师授课信息维护
- 成绩管理：成绩录入、查询、导出和统计
- 课表管理：按专业、年级、学期维护课程表
- 个人中心：个人信息维护、密码修改、头像上传

## 后端启动

1. 使用 IDEA 或其他 Java IDE 打开项目根目录。
2. 确认本机已安装 JDK 8 或更高版本，并正确配置 `JAVA_HOME`。
3. 导入 Maven 依赖。
4. 在 MySQL 中执行根目录下的 `student_mis.sql` 初始化数据库。
5. 根据本机环境调整 `src/main/resources/application.properties`。
6. 启动 `StudentMisApplication`。
7. 浏览器访问 `http://localhost:8443/`。

## 前端开发启动

进入前端目录：

```bash
cd sms
npm install
npm run dev
```

开发环境默认请求地址配置在 `sms/src/axios/axiosHelper.js`。

## 配置说明

数据库连接默认读取以下环境变量；未配置时使用本地开发默认值：

- `DB_USERNAME`，默认值：`root`
- `DB_PASSWORD`，默认值：`root`

邮件服务读取以下环境变量：

- `MAIL_USERNAME`
- `MAIL_PASSWORD`

如果未配置邮件环境变量，发送邮件相关功能不可用，但不影响用户、课程、成绩、课表等核心功能运行。

## 默认账号

初始化 SQL 中包含演示账号，导入数据库后可用于本地测试：

- 管理员：`root` / `password`

其他教师、学生演示账号可在数据库对应表中查看。
