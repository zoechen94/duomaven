/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50534
Source Host           : localhost:3306
Source Database       : spring

Target Server Type    : MYSQL
Target Server Version : 50534
File Encoding         : 65001

Date: 2018-07-27 14:27:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_cn` varchar(255) DEFAULT NULL,
  `permission_en` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '删除管理员', 'delete-admin', '/xx/test1');
INSERT INTO `sys_permission` VALUES ('2', '删除用户', 'delete-user', '/xx/test2');
INSERT INTO `sys_permission` VALUES ('3', '根据用户查询', 'test3', '/xx/test3');
INSERT INTO `sys_permission` VALUES ('4', '测试4', 'test4', '/xx/test4');
INSERT INTO `sys_permission` VALUES ('5', '测试5', 'test5', '/xx/test5');
INSERT INTO `sys_permission` VALUES ('6', '测试6', 'test6', '/xx/test6');
INSERT INTO `sys_permission` VALUES ('7', '测试7', 'test7', '/xx/test7');
INSERT INTO `sys_permission` VALUES ('8', '测试8', 'test8', '/xx/test8');
INSERT INTO `sys_permission` VALUES ('9', '测试9', 'test9', '/xx/test9');
INSERT INTO `sys_permission` VALUES ('10', '测试10', 'test10', '/xx/test10');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', '超级管-理员');
INSERT INTO `sys_role` VALUES ('2', '2', '管理员');
INSERT INTO `sys_role` VALUES ('3', '3', '普通用户');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1', '1');
INSERT INTO `sys_role_permission` VALUES ('2', '2', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '3', '1');
INSERT INTO `sys_role_permission` VALUES ('4', '4', '1');
INSERT INTO `sys_role_permission` VALUES ('5', '5', '1');
INSERT INTO `sys_role_permission` VALUES ('6', '6', '1');
INSERT INTO `sys_role_permission` VALUES ('7', '7', '1');
INSERT INTO `sys_role_permission` VALUES ('8', '8', '1');
INSERT INTO `sys_role_permission` VALUES ('9', '9', '1');
INSERT INTO `sys_role_permission` VALUES ('10', '10', '1');
INSERT INTO `sys_role_permission` VALUES ('11', '2', '2');
INSERT INTO `sys_role_permission` VALUES ('12', '3', '2');
INSERT INTO `sys_role_permission` VALUES ('13', '4', '2');
INSERT INTO `sys_role_permission` VALUES ('14', '5', '2');
INSERT INTO `sys_role_permission` VALUES ('15', '6', '2');
INSERT INTO `sys_role_permission` VALUES ('16', '7', '2');
INSERT INTO `sys_role_permission` VALUES ('17', '8', '2');
INSERT INTO `sys_role_permission` VALUES ('18', '9', '2');
INSERT INTO `sys_role_permission` VALUES ('19', '10', '2');
INSERT INTO `sys_role_permission` VALUES ('22', '4', '3');
INSERT INTO `sys_role_permission` VALUES ('23', '5', '3');
INSERT INTO `sys_role_permission` VALUES ('24', '6', '3');
INSERT INTO `sys_role_permission` VALUES ('25', '7', '3');
INSERT INTO `sys_role_permission` VALUES ('26', '8', '3');
INSERT INTO `sys_role_permission` VALUES ('27', '9', '3');
INSERT INTO `sys_role_permission` VALUES ('28', '10', '3');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2', '2');
INSERT INTO `sys_user_role` VALUES ('3', '3', '3');
INSERT INTO `sys_user_role` VALUES ('4', '2', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'xxxx', '超级玛丽', 'super-admin', null);
INSERT INTO `user` VALUES ('2', 'XXX', '玛丽', 'admin', null);
INSERT INTO `user` VALUES ('3', 'dfdf', '路人', 'user', null);
INSERT INTO `user` VALUES ('4', '2343', 'test', 'test', null);
