DROP DATABASE IF EXISTS `bbs`;
CREATE DATABASE `bbs` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL PRIVILEGES ON bbs.* to bbs@'%' IDENTIFIED BY 'bbs_password';

use bbs;

DROP TABLE IF EXISTS `bbs_user_info`;
CREATE TABLE `bbs_user_info` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增数据库主键',
  `avatarId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户头像',
  `backgroundId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '背景图片id',
  `userName` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `email` VARCHAR(60) NOT NULL DEFAULT '' COMMENT '邮箱',
  `cellphone` VARCHAR(16) NOT NULL DEFAULT '' COMMENT '手机号',
  `password` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '密码',
  `sex` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0：未知，1：男，2：女',
  `description` VARCHAR(400) DEFAULT NULL COMMENT '关于我',
  `birthday` DATE DEFAULT NULL COMMENT '出生日期',
  `status` TINYINT(10) UNSIGNED NOT NULL DEFAULT '1' COMMENT '用户状态,0：无效，1：有效',
  `userRole` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户角色，0普通用户，1超级管理员',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_userName` (`userName`),
  KEY `idx_password` (`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

DROP TABLE IF EXISTS `bbs_user_login_log`;
CREATE TABLE `bbs_user_login_log`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id',
  `ip` VARCHAR(15) NOT NULL DEFAULT '' COMMENT 'IP地址',
  `loginTime` INT(5) NOT NULL DEFAULT 0 COMMENT '连续登录次数，每天值增加一次',
  `logoutTime` DATETIME DEFAULT NULL COMMENT '退出时间',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录记录表';

DROP TABLE IF EXISTS `bbs_user_role`;
CREATE TABLE `bbs_user_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(10) UNSIGNED NOT NULL COMMENT '用户id',
  `roleId` INT(6) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户角色',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userId_roleId` (`userId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

DROP TABLE IF EXISTS `bbs_user_hobby`;
CREATE TABLE `bbs_user_hobby` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id',
  `categoryId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '分类id',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userId_categoryId` (`userId`,`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户偏好表';

DROP TABLE IF EXISTS `bbs_user_tool`;
CREATE TABLE `bbs_user_tool` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id',
  `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '工具分类，1：相机，2：镜头，3：装备',
  `name` VARCHAR(32) NOT NULL DEFAULT '装备名称',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_name_userId` (`type`,`name`,`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户设备信息表';

DROP TABLE IF EXISTS `bbs_user_follow`;
CREATE TABLE `bbs_user_follow`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `startUserId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '被关注用户id',
  `endUserId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '关注用户id',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关注表';


DROP TABLE IF EXISTS `bbs_account_log`;
CREATE TABLE `bbs_account_log`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id',
  `money` INT(11) NOT NULL DEFAULT 0 COMMENT '变动积分',
  `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '积分变动类型,1:注册奖励：2:充值成功，3：充值失败，4：连续登录，5:上传花费积分，6：被别人下载获得，7：下载扣除',
  `projectId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '相关id,eg:连续登录天数',
  `balance` INT(11) NOT NULL DEFAULT 0 COMMENT '余额',
  #   `accountId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '账户id',
  `remark` VARCHAR(32) DEFAULT NULL COMMENT '备注',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户变更记录表';

DROP TABLE IF EXISTS `bbs_account_pay`;
CREATE TABLE `bbs_account_pay`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `payer` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id',
  `money` INT(11) NOT NULL DEFAULT 0 COMMENT '变动积分',
  `status` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '1:已支付，待充值，2：充值成功，3：充值失败，退款中，4：已退款',
  `remark` VARCHAR(32) DEFAULT NULL COMMENT '备注',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户充值记录表';

DROP TABLE IF EXISTS `bbs_account`;
CREATE TABLE `bbs_account`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id',
  `totalMoney` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '总积分',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId` (`userId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户表';


DROP TABLE IF EXISTS `bbs_tag_category`;
CREATE TABLE `bbs_tag_category`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '标签名称',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签分类表';

DROP TABLE IF EXISTS `bbs_tag_meta`;
CREATE TABLE `bbs_tag_meta`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '标签名称',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签元数据表';

DROP TABLE IF EXISTS `bbs_tag_relation`;
CREATE TABLE `bbs_tag_relation`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `startTagId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '标签ID',
  `endTagId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '标签ID',
  `status` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态',
  `score` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '相关度',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签关系表';

DROP TABLE IF EXISTS `bbs_picture_attachment`;
CREATE TABLE `bbs_picture_attachment`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户ID',
  `filePath` VARCHAR(128) NOT  NULL DEFAULT '' COMMENT '文件路径',
  `fileName` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '附件原始名称',
  `ext` VARCHAR(8) NOT NULL DEFAULT '' COMMENT '文件后缀',
  `length` VARCHAR(28) NOT NULL DEFAULT '' COMMENT '文件大小',
  `uid` CHAR(8) NOT NULL DEFAULT '' COMMENT 'uid，文件名',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片上传附件表';

DROP TABLE IF EXISTS `bbs_picture_exif`;
CREATE TABLE `bbs_picture_exif`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `picId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '附件id',
  `camera` VARCHAR(128) DEFAULT NULL COMMENT '相机',
  `lens` VARCHAR(128) DEFAULT NULL COMMENT '镜头',
  `focalLength` VARCHAR(32) DEFAULT NULL COMMENT '焦距',
  `shutterSpeed` VARCHAR(28) DEFAULT NULL COMMENT '快门速度',
  `aperture` VARCHAR(10) DEFAULT NULL COMMENT '光圈',
  `width` INT(10) NOT NULL DEFAULT 0 COMMENT '宽度',
  `height` INT(10) NOT NULL DEFAULT 0 COMMENT '高度',
  `iso` VARCHAR(10) DEFAULT NULL COMMENT 'ISO',
  `taken` DATETIME DEFAULT NULL COMMENT '拍摄时间',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片EXIF信息表';

DROP TABLE IF EXISTS `bbs_picture_download_log`;
CREATE TABLE `bbs_picture_download_log`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `picId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '图片id',
  `ownerId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '上传者id',
  `downloader` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '下载者id',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片下载表';

DROP TABLE IF EXISTS `bbs_project_info`;
CREATE TABLE `bbs_project_info`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id',
  `picId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '图片id',
  `title` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '标题',
  `auth` TINYINT(2) NOT NULL DEFAULT 0 COMMENT '是否授权可以下载，0不可以，1：授权下载',
  `money` INT(11) NOT NULL DEFAULT 0 COMMENT '下载金额',
  `categoryId` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '分类id',
  `description` VARCHAR(400) NOT NULL DEFAULT '' COMMENT '介绍',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_picId` (`picId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片项目信息表';

DROP TABLE IF EXISTS `bbs_project_view`;
CREATE TABLE `bbs_project_view`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `viewer` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '查看者id',
  `projectId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '项目id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '图片拥有者',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户查看图片项目记录表';

DROP TABLE IF EXISTS `bbs_project_tag`;
CREATE TABLE `bbs_project_tag`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户id',
  `projectId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '图片项目id',
  `tagId` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '标签id',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_projectId_tagId` (`projectId`,`tagId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片项目标签表';

DROP TABLE IF EXISTS `bbs_project_like`;
CREATE TABLE `bbs_project_like`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '喜欢用户id',
  `projectId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '图片项目id',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userId_projectId` (`userId`,`projectId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户喜欢图片记录表';

DROP TABLE IF EXISTS `bbs_project_collection`;
CREATE TABLE `bbs_project_collection`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '收藏者id',
  `projectId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '图片项目id',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userId_projectId_` (`userId`,`projectId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收藏记录表';

DROP TABLE IF EXISTS `bbs_project_comment`;
CREATE TABLE `bbs_project_comment`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论者id',
  `commentId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '回复id，0：未回复他人',
  `projectId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '图片项目id',
  `comments` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '评论',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户图片评论表';

DROP TABLE IF EXISTS `bbs_illegal_info`;
CREATE TABLE `bbs_illegal_info`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '转发者id',
  `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '投诉类型，1：用户，2：动态',
  `projectId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '被举报项目id',
  `reasonId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '举报原因',
  `remark` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '备注',
  `status` TINYINT(3) NOT NULL DEFAULT 0 COMMENT '投诉状态',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户投诉表';

DROP TABLE IF EXISTS `bbs_illegal_solve`;
CREATE TABLE `bbs_illegal_solve`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '处理者id',
  `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '处理结果',
  `projectId` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '举报项目id',
  `remark` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '备注',
  `status` TINYINT(3) NOT NULL DEFAULT 0 COMMENT '处理状态',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员投诉处理表';

DROP TABLE IF EXISTS `bbs_message`;
CREATE TABLE `bbs_message`(
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `sender` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '发送者id,0: 系统',
  `receiver` INT(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '接收id',
  `message` VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '消息内容',
  `type` TINYINT(3) NOT NULL DEFAULT 0 COMMENT '类型，1：点赞，2：关注',
  `projectId` INT(11) NOT NULL DEFAULT 0 COMMENT '当为点赞时，点赞的projectId',
  `status` TINYINT(3) NOT NULL DEFAULT 0 COMMENT '消息状态，0：草稿，1：已发送，待查看，2：已查看',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `modifyTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息信息表';