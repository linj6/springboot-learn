/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : springboot_learn

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-05-12 19:54:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_category`;
CREATE TABLE `t_goods_category` (
  `id` bigint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '分类名称',
  `level` int(4) DEFAULT '0' COMMENT '等级 ',
  `parent_id` bigint(4) DEFAULT '0' COMMENT '父类id',
  `sort` int(4) NOT NULL DEFAULT '0' COMMENT '排序',
  `del` smallint(1) NOT NULL DEFAULT '0' COMMENT '删除标识 1:正常 0:已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(4) NOT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` bigint(4) DEFAULT NULL COMMENT '修改人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='商品分类';

-- ----------------------------
-- Records of t_goods_category
-- ----------------------------
INSERT INTO `t_goods_category` VALUES ('1', '生鲜水果', '1', '0', '1', '1', '2018-05-11 10:18:23', '1', '2018-05-11 10:18:23', '1');
INSERT INTO `t_goods_category` VALUES ('2', '新鲜蔬菜', '2', '1', '1', '1', '2018-05-11 10:22:02', '1', '2018-05-11 10:22:02', '1');
INSERT INTO `t_goods_category` VALUES ('3', '土豆', '3', '2', '1', '1', '2018-05-11 10:22:50', '1', '2018-05-11 14:28:56', '1');

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` bigint(4) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip',
  `ip_address` varchar(128) DEFAULT NULL COMMENT 'ip所在地区',
  `description` varchar(63) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL COMMENT '操作用户',
  `request_uri` varchar(255) DEFAULT NULL COMMENT 'URI',
  `request_url` varchar(255) DEFAULT NULL COMMENT 'URL',
  `request_method` varchar(10) DEFAULT NULL COMMENT '请求类型',
  `class_name` varchar(255) DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名称',
  `arguments` mediumtext COMMENT '参数',
  `result` mediumtext COMMENT '返回结果',
  `start_time` int(11) DEFAULT NULL COMMENT '开始时间',
  `end_time` int(11) DEFAULT NULL COMMENT '结束时间',
  `spend_time` int(11) DEFAULT NULL COMMENT '消耗时间',
  `del` smallint(1) DEFAULT '1' COMMENT '删除标识 1:正常 0:已删除',
  `create_user_id` bigint(4) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_user_id` bigint(4) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of t_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(4) NOT NULL AUTO_INCREMENT COMMENT '用户编号，主键',
  `username` varchar(32) NOT NULL,
  `password` char(164) NOT NULL COMMENT '密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `realname` varchar(11) DEFAULT NULL COMMENT '真实姓名',
  `mobile` char(11) NOT NULL COMMENT '手机号码',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `register_ip` varchar(64) DEFAULT NULL COMMENT '注册ip',
  `last_login_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `last_login_ip` varchar(64) DEFAULT NULL COMMENT '最后登录ip',
  `last_login_address` varchar(64) DEFAULT NULL COMMENT '最后登录地区',
  `del` char(1) DEFAULT '1' COMMENT '是否有效,1:有效;0:无效',
  `create_user_id` bigint(4) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_user_id` bigint(4) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('7', '', '123456', null, null, '18900000000', null, null, null, null, null, null, '1', null, '2018-03-05 16:24:02', null, '2018-03-05 16:24:02');
