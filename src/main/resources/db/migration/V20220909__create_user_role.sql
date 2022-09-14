DROP TABLE if exists user;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `headurl` varchar(255) NULL COMMENT '头像地址',
  `enable` tinyint(3) NULL DEFAULT 1 COMMENT '是否有效：0无效，1有效',
  `locked` tinyint(3) NULL DEFAULT 0 COMMENT '是否锁住：0否，1是',
  PRIMARY KEY (`id`)
) COMMENT = '用户表，包含所有用户';

DROP TABLE if exists role;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(32) NULL DEFAULT NULL COMMENT '角色编码',
  `nameZh` varchar(32) NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) COMMENT = '角色表';

DROP TABLE if exists user_role;
CREATE TABLE `user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `uid` int NULL DEFAULT NULL COMMENT '用户ID',
  `rid` int NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) COMMENT = '用户角色表';

ALTER TABLE `user_role` ADD CONSTRAINT `user_role_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`id`);
ALTER TABLE `user_role` ADD CONSTRAINT `user_role_rid` FOREIGN KEY (`rid`) REFERENCES `role` (`id`);

INSERT INTO `role` VALUES (1, 'ROLE_user', '用户');
INSERT INTO `role` VALUES (2, 'ROLE_admin', '管理员');
INSERT INTO `role` VALUES (3, 'ROLE_superadmin', '超级管理员');