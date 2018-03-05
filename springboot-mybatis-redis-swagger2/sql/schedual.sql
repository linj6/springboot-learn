/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : poi_learn

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-12-18 16:45:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `product_name` varchar(50) NOT NULL COMMENT '产品名称',
  `product_code` varchar(50) DEFAULT NULL COMMENT '产品编号',
  `brand` varchar(50) DEFAULT NULL COMMENT '品牌',
  `modelnumber` varchar(32) DEFAULT NULL COMMENT '型号/规格',
  `price` decimal(12,2) NOT NULL COMMENT '产品价格',
  `inventory_quantity` int(11) NOT NULL COMMENT '库存数量',
  `product_unit` varchar(8) NOT NULL COMMENT '产品单位',
  `status` smallint(1) NOT NULL DEFAULT '0' COMMENT '状态, 0:已保存; 1:待审核; 2:审核通过; 3:审核不通过;  4:上架; 5:下架;',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '小米Note', '1001', '小米', '2G', '2000.00', '100000', '台', '2', '2017-12-28 15:30:17');
INSERT INTO `product` VALUES ('2', '魅蓝Note', '1002', '魅族', '3G', '2300.00', '800000', '台', '2', '2017-12-16 15:30:17');
INSERT INTO `product` VALUES ('3', '小米Note', '1001', '小米', '2G', '2000.00', '100000', '台', '2', '2017-12-28 15:30:17');
INSERT INTO `product` VALUES ('4', '魅蓝Note', '1002', '魅族', '3G', '2300.00', '800000', '台', '2', '2017-12-16 15:30:17');
INSERT INTO `product` VALUES ('5', '小米Note', '1001', '小米', '2G', '2000.00', '100000', '台', '2', '2017-12-28 15:30:17');
INSERT INTO `product` VALUES ('6', '魅蓝Note', '1002', '魅族', '3G', '2300.00', '800000', '台', '2', '2017-12-16 15:30:17');
INSERT INTO `product` VALUES ('7', '小米Note', '1001', '小米', '2G', '2000.00', '100000', '台', '2', '2017-12-28 15:30:17');
INSERT INTO `product` VALUES ('8', '魅蓝Note', '1002', '魅族', '3G', '2300.00', '800000', '台', '2', '2017-12-16 15:30:17');
