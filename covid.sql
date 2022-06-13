/*
 Navicat MySQL Data Transfer

 Source Server         : db
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : localhost:3306
 Source Schema         : covid

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 10/06/2022 23:30:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `class_num` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`class_num`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '软工193');

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `college_num` int(11) NOT NULL AUTO_INCREMENT,
  `college_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`college_num`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (0, 'ADMIN');
INSERT INTO `college` VALUES (1, '信息学院');
INSERT INTO `college` VALUES (2, '艺术学院');
INSERT INTO `college` VALUES (3, '生化学院');
INSERT INTO `college` VALUES (4, '电气学院');
INSERT INTO `college` VALUES (5, '经管学院');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `major_num` int(11) NOT NULL AUTO_INCREMENT,
  `college_num` int(11) NOT NULL,
  `major_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`major_num`) USING BTREE,
  INDEX `collegeNum`(`college_num`) USING BTREE,
  CONSTRAINT `major_ibfk_1` FOREIGN KEY (`college_num`) REFERENCES `college` (`college_num`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, 1, '软件工程');
INSERT INTO `major` VALUES (2, 2, '服装设计');
INSERT INTO `major` VALUES (3, 3, '化工制药');
INSERT INTO `major` VALUES (4, 4, '自动化');
INSERT INTO `major` VALUES (5, 5, '经济学');
INSERT INTO `major` VALUES (6, 1, '计算机科学');
INSERT INTO `major` VALUES (7, 1, '数字媒体');
INSERT INTO `major` VALUES (8, 4, '建筑智能及其自动化');

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double NULL DEFAULT NULL,
  `longitude` double NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO `position` VALUES (41, 30.221475985798897, 120.02876150319588, '西和公寓北门', '123');
INSERT INTO `position` VALUES (42, 30.225873282083743, 120.03409435923325, '图书馆东区', '浙江科技学院图书馆东楼');
INSERT INTO `position` VALUES (43, 30.225585750597013, 120.03351671669098, '图书馆西区', '浙江科技学院图书馆西楼');
INSERT INTO `position` VALUES (44, 30.228846953730717, 120.03050027248118, '教学楼-A楼', '浙江科技学院教学楼A楼');
INSERT INTO `position` VALUES (45, 30.224159303489014, 120.03805577651976, '东和公寓', NULL);

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `user_num` char(9) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `pid` int(11) NULL DEFAULT NULL,
  `check_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (72, '10000002', 41, '2022-06-08 22:39:04');
INSERT INTO `record` VALUES (73, '10000002', 42, '2022-06-08 22:41:11');
INSERT INTO `record` VALUES (74, '10000001', 41, '2022-06-08 22:41:54');
INSERT INTO `record` VALUES (75, '10000003', 42, '2022-06-08 22:42:03');

-- ----------------------------
-- Table structure for status
-- ----------------------------
DROP TABLE IF EXISTS `status`;
CREATE TABLE `status`  (
  `stid` int(11) NOT NULL AUTO_INCREMENT,
  `user_num` char(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `state_time` date NULL DEFAULT NULL,
  `state` int(1) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  PRIMARY KEY (`stid`) USING BTREE,
  INDEX `status_ibfk_1`(`user_num`) USING BTREE,
  CONSTRAINT `status_ibfk_1` FOREIGN KEY (`user_num`) REFERENCES `user` (`user_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of status
-- ----------------------------
INSERT INTO `status` VALUES (27, '10000002', '2022-06-08', 1);
INSERT INTO `status` VALUES (28, '10000001', '2022-06-08', 2);
INSERT INTO `status` VALUES (29, '10000003', '2022-06-08', 2);
INSERT INTO `status` VALUES (30, '10000002', '2022-06-08', 0);
INSERT INTO `status` VALUES (31, '10000001', '2022-06-08', 0);
INSERT INTO `status` VALUES (32, 'admin', '2022-06-08', 1);
INSERT INTO `status` VALUES (33, 'admin', '2022-06-08', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_num` char(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `college_num` int(11) NULL DEFAULT NULL,
  `major_num` int(11) NULL DEFAULT NULL,
  `class_num` int(11) NULL DEFAULT NULL,
  `user_type` int(11) NOT NULL,
  `user_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sex` tinyint(1) NOT NULL,
  `telephone` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_num`) USING BTREE,
  INDEX `collegeNum`(`college_num`) USING BTREE,
  INDEX `majorNum`(`major_num`) USING BTREE,
  INDEX `classNum`(`class_num`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`college_num`) REFERENCES `college` (`college_num`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`major_num`) REFERENCES `major` (`major_num`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_ibfk_3` FOREIGN KEY (`class_num`) REFERENCES `class` (`class_num`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('10000001', 1, 1, 1, 0, '吴楚111', '123', 1, '13780166111');
INSERT INTO `user` VALUES ('10000002', 2, 2, 1, 1, '郭敏黄', '123', 0, NULL);
INSERT INTO `user` VALUES ('10000003', 3, 3, 1, 1, '张语', '123', 0, NULL);
INSERT INTO `user` VALUES ('10000004', 4, 7, NULL, 1, '1234', '123', 1, NULL);
INSERT INTO `user` VALUES ('10000005', 5, NULL, NULL, 1, 'Jiangh', '123', 1, NULL);
INSERT INTO `user` VALUES ('10000006', 1, 1, 1, 1, '张杰诚', '2', 0, '13780166214');
INSERT INTO `user` VALUES ('20000001', 1, 1, 1, 2, '马海泊', '123', 1, NULL);
INSERT INTO `user` VALUES ('20000003', 1, 1, 1, 2, '赵峰', '2', 1, '13517249182');
INSERT INTO `user` VALUES ('20000004', 1, 1, 1, 2, '郭发', '2', 0, NULL);
INSERT INTO `user` VALUES ('20000005', 1, 1, 1, 2, '刘雨', '2', 0, NULL);
INSERT INTO `user` VALUES ('20000006', 1, 1, 1, 2, '张峰杰', '2', 1, NULL);
INSERT INTO `user` VALUES ('20000007', 2, 2, 1, 2, '马杰黄', '2', 1, '13197824792');
INSERT INTO `user` VALUES ('20000008', 2, 2, 1, 2, '林怡海', '2', 0, NULL);
INSERT INTO `user` VALUES ('20000009', 2, 2, 1, 2, '赵峰', '20c18451b72b8c3a25e7cd08a4263400', 0, NULL);
INSERT INTO `user` VALUES ('20000010', 2, 2, 1, 2, '孙杰峰', '972330ecd9b758659ecc43b1b07625f6', 0, NULL);
INSERT INTO `user` VALUES ('20000011', 2, 2, 1, 2, '马语', '630b7ac00527981796782c3fdb76263e', 0, NULL);
INSERT INTO `user` VALUES ('20000012', 2, 2, 1, 2, '吴芳发', 'bf8bbafc67b5569f0b2087a3c6da6dff', 1, NULL);
INSERT INTO `user` VALUES ('20000013', 3, 3, 1, 2, '林峰怡', 'edf0ea15bb9c2e87fc037938f9936387', 1, NULL);
INSERT INTO `user` VALUES ('20000014', 3, 3, 1, 2, '赵雨楚', '328c9c67c9d17cffa3275edb18e2b955', 1, NULL);
INSERT INTO `user` VALUES ('20000015', 3, 3, 1, 2, '王诚', 'e32470e0ddb6310c3651aeb3e9459e70', 1, NULL);
INSERT INTO `user` VALUES ('20000016', 3, 3, 1, 2, '张峰', '8935319b77b3d529474b74da103f44df', 0, NULL);
INSERT INTO `user` VALUES ('20000017', 3, 3, 1, 2, '陈楚', '704495770545441003084d566f9bb593', 0, NULL);
INSERT INTO `user` VALUES ('20000018', 3, 3, 1, 2, '孙芳海', '53f17bfe25737ada3c9a3470f79cd55b', 1, NULL);
INSERT INTO `user` VALUES ('20000019', 4, 4, 1, 2, '何怡海', 'b2da979df0dd3968e04b800a895d7261', 0, NULL);
INSERT INTO `user` VALUES ('20000020', 4, 4, 1, 2, '马楚楚', '825f929a27af655b80730b95d38b3819', 1, NULL);
INSERT INTO `user` VALUES ('20000021', 4, 4, 1, 2, '张黄泊', '3048c43149c8200f7185c2595530171d', 0, NULL);
INSERT INTO `user` VALUES ('20000022', 4, 4, 1, 2, '杨诚楚', 'c8344d2301b08227ce0c8371ff3c4552', 0, NULL);
INSERT INTO `user` VALUES ('20000023', 4, 4, 1, 2, '杨怡', 'd3e17e3e8e361d0db21883389c4957e7', 1, NULL);
INSERT INTO `user` VALUES ('20000024', 4, 4, 1, 2, '马发', '38def3dcc44b04720540cb52b9343225', 0, NULL);
INSERT INTO `user` VALUES ('20000025', 5, 5, 1, 2, '赵黄', '486a0ba0e5b41743411b57d372b6ab99', 1, NULL);
INSERT INTO `user` VALUES ('20000026', 5, 5, 1, 2, '杨杰诚', '0f22e6baf0390d4b7ad068d2388961f1', 0, NULL);
INSERT INTO `user` VALUES ('20000027', 5, 5, 1, 2, '马敏泊', 'db61fcc6477f7f82fb9e08de0409f8f4', 0, NULL);
INSERT INTO `user` VALUES ('20000028', 5, 5, 1, 2, '郭黄楚', '233fcc64958f2a8d780b048dbbb13b94', 1, NULL);
INSERT INTO `user` VALUES ('20000029', 5, 5, 1, 2, '高语', '08e53c15b1b2193a5442a505e20fe7fa', 1, NULL);
INSERT INTO `user` VALUES ('20000030', 5, 5, 1, 2, '黄芳泊', '35c2aa90f1de3f38d3001be5cf98e5db', 0, NULL);
INSERT INTO `user` VALUES ('20000123', 4, 8, NULL, 2, '余将虎', '0123', 1, NULL);
INSERT INTO `user` VALUES ('admin', 0, NULL, NULL, 0, 'admin', 'admin', 0, '13780166214');
INSERT INTO `user` VALUES ('adminadm', NULL, NULL, NULL, 0, '余将虎', 'nadm', 1, NULL);

-- ----------------------------
-- Table structure for vacation
-- ----------------------------
DROP TABLE IF EXISTS `vacation`;
CREATE TABLE `vacation`  (
  `vacation_num` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_num` char(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `request_time` datetime NOT NULL,
  `way` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`vacation_num`) USING BTREE,
  INDEX `userNum`(`user_num`) USING BTREE,
  CONSTRAINT `vacation_ibfk_1` FOREIGN KEY (`user_num`) REFERENCES `user` (`user_num`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vacation
-- ----------------------------
INSERT INTO `vacation` VALUES (1, 'admin', 'no reason', '2022-06-09 00:00:00', '2022-06-09 00:00:01', '2022-06-09 22:48:44', 'no way', 2);
INSERT INTO `vacation` VALUES (5, 'admin', '123', '2022-06-03 16:00:00', '2022-06-09 16:00:00', '2022-06-10 22:09:57', '123', 2);
INSERT INTO `vacation` VALUES (6, 'admin', '123', '2022-06-09 16:00:00', '2022-06-17 16:00:00', '2022-06-10 23:03:43', '123', 1);
INSERT INTO `vacation` VALUES (7, 'admin', '43342324', '2022-06-01 16:00:00', '2022-06-23 16:00:00', '2022-06-10 23:22:39', '234432243', 0);

SET FOREIGN_KEY_CHECKS = 1;
