/*
Import this sql file to create new database in MySQL.
Use import wizard to import data for tables from text
files in data folder. Note all tests use data from the
database, so we need to to these above stuffs before
executing those tests.

Source Server         : MySQL Connection
Source Server Version : 50621
Source Host           : 127.0.0.1:3306
Source Database       : skyline

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-01-05 22:36:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for medium-anti-correlated-2d-points-100000
-- ----------------------------
DROP TABLE IF EXISTS `medium-anti-correlated-2d-points-100000`;
CREATE TABLE `medium-anti-correlated-2d-points-100000` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` double(255,20) NOT NULL,
  `y` double(255,20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for medium-correlated-2d-points-100000
-- ----------------------------
DROP TABLE IF EXISTS `medium-correlated-2d-points-100000`;
CREATE TABLE `medium-correlated-2d-points-100000` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` double(255,20) NOT NULL,
  `y` double(255,20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for medium-uniformly-distributed-2d-points-100000
-- ----------------------------
DROP TABLE IF EXISTS `medium-uniformly-distributed-2d-points-100000`;
CREATE TABLE `medium-uniformly-distributed-2d-points-100000` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` double(255,20) NOT NULL,
  `y` double(255,20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for small-anti-correlated-2d-points-10000
-- ----------------------------
DROP TABLE IF EXISTS `small-anti-correlated-2d-points-10000`;
CREATE TABLE `small-anti-correlated-2d-points-10000` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` double(255,20) NOT NULL,
  `y` double(255,20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for small-correlated-2d-points-10000
-- ----------------------------
DROP TABLE IF EXISTS `small-correlated-2d-points-10000`;
CREATE TABLE `small-correlated-2d-points-10000` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` double(255,20) NOT NULL,
  `y` double(255,20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for small-uniformly-distributed-2d-points-10000
-- ----------------------------
DROP TABLE IF EXISTS `small-uniformly-distributed-2d-points-10000`;
CREATE TABLE `small-uniformly-distributed-2d-points-10000` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` double(255,20) NOT NULL,
  `y` double(255,20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
