/*
Navicat MySQL Data Transfer

Source Server         : localhost:3306
Source Server Version : 50045
Source Host           : localhost:3306
Source Database       : planegame

Target Server Type    : MYSQL
Target Server Version : 50045
File Encoding         : 65001

Date: 2019-06-28 20:14:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` char(20) character set utf8 NOT NULL default '',
  `password` char(20) character set utf8 default NULL,
  `level` int(20) default NULL,
  `grade` int(20) default NULL,
  `time` int(11) default NULL,
  `maxgrade` int(11) default NULL,
  `blood` int(11) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('123', '123', null, null, null, null, null);
