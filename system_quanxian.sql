/*
 Navicat Premium Data Transfer

 Source Server         : luoxin
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 127.0.0.1:3306
 Source Schema         : system_quanxian

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 21/02/2020 15:15:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `name` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `acl_module_id` int(11) NOT NULL DEFAULT '0' COMMENT 'acl  的ID',
  `url` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '请求的URL。可以是正则表达式',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '1 表单 ，2 按钮 3 其他',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1 正常 0 冻结',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '权限在当前模块下的顺序',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT '',
  `operator` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operate_ip` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for sys_acl_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_module`;
CREATE TABLE `sys_acl_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `level` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '',
  `seq` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '1有效，0 冻结',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT '',
  `operator` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operate_ip` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限模块表';

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `level` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '',
  `seq` int(11) NOT NULL DEFAULT '0',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT '',
  `operator` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operate_ip` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '权限更新的类型 1：权限 2 ：部门 3: 权限模块 4:权限 5: 角色 6: 角色用户关系\n7角色权限关系',
  `targer_id` int(11) NOT NULL COMMENT '基于type后指定的对象ID ，比如用户，权限 ，角色等表',
  `old_value` text COLLATE utf8_bin,
  `new_value` text COLLATE utf8_bin,
  `operator` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operate_ip` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '是否复原过 0: 没有 1复原过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '角色名称',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '角色类型 1 管理员 2 其他',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '角色状态 ： 1 有效 0: 无效',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT '' COMMENT '注释',
  `operator` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作者最后操作时间',
  `operate_ip` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '操作者IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for sys_role_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `acl_id` int(11) NOT NULL,
  `operator` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operate_ip` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `operator` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operate_ip` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `telephone` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `mail` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT '',
  `password` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1有效 0无效 2删除',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT '',
  `operator` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operate_ip` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
