# Knowledge-Graph-Management-System-back-end

## 知识图谱管理系统后台

### 主要使用技术
|框架|版本|作用|
|-|:------:|:------:|
|java|8|开发语言|
|springboot|2.6.3|——|
|maven|3.6+|包管理|
|redis|5.0.14|数据库缓存|
|mysql|5.7|数据库|
|redis|5.0.14|数据缓存|
|neo4j|4.4.8-community|图数据库|
|ELK|7.6.2|日志收集|
|kafka|2.8.1|消息队列|
### flyway 数据库版本控制命名规范
1. 位于db/migration 文件夹下，启动项目自动运行，数据库名为kgms
2. 仅需要被执行一次的SQL命名以大写的"V"开头，V+版本号(版本号的数字间以”.“或”_“分隔开)+双下划线(用来分隔版本号和描述)+文件描述+后缀名。例如：  V20201100__create_user.sql、V2.1.5__create_user_ddl.sql、V4.1_2__add_user_dml.sql  。
3. 可重复运行的SQL，则以大写的“R”开头，后面再以两个下划线分割，其后跟文件名称，最后以.sql结尾。（不推荐使用）比如： R__truncate_user_dml.sql 。

### 项目启动说明
(第一次启动项目需要建立数据库，数据库名为kgms,编码为utf-8)
1. 启动redis 和 mysql 服务
2. 更改redis数据库和mysql数据库连接配置连接配置(如端口号等，一般都是默认)
3. 启动项目
### 更新日期
2022.9.13