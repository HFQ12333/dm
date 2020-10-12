/*
Navicat MySQL Data Transfer

Source Server         : zg
Source Server Version : 50045
Source Host           : localhost:3306
Source Database       : planegame

Target Server Type    : MYSQL
Target Server Version : 50045
File Encoding         : 65001

Date: 2018-01-01 19:50:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `level` int(11) default NULL,
  `grade` int(11) default NULL,
  `time` int(11) default NULL,
  `maxgrade` int(11) default NULL,
  `blood` int(11) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('ljr', '123456', '1', '90', '0', '0','0');
