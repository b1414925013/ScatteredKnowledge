/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.23 : Database - mybatis_plus
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mybatis_plus` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mybatis_plus`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `version` int(10) DEFAULT '1' COMMENT '乐观锁',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除字段',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1269806860853084168 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`age`,`email`,`version`,`deleted`,`create_time`,`update_time`) values (1,'kuangshen',18,'24736743@qq.com',3,1,'2020-06-08 10:06:44','2020-06-08 10:35:03'),(2,'Jack',20,'test2@baomidou.com',1,0,'2020-06-08 10:06:44','2020-06-08 10:06:44'),(3,'Tom',28,'test3@baomidou.com',1,0,'2020-06-08 10:06:44','2020-06-08 10:06:44'),(4,'Sandy',21,'test4@baomidou.com',1,0,'2020-06-08 10:06:44','2020-06-08 10:06:44'),(5,'Billie',24,'test5@baomidou.com',1,0,'2020-06-08 10:06:44','2020-06-08 10:06:44'),(1269806860853084162,'aaaa',22,'24736743@qq.com',1,0,'2020-06-08 10:06:44','2020-06-08 10:09:49'),(1269806860853084163,'aaaa',22,'24736743@qq.com',1,0,'2020-06-08 10:18:58','2020-06-08 10:20:15'),(1269806860853084164,'aaaa',3,'24736743@qq.com',1,0,'2020-06-08 10:28:57','2020-06-08 10:28:57'),(1269806860853084165,'aaaa',3,'24736743@qq.com',1,0,'2020-06-08 10:29:53','2020-06-08 10:29:53'),(1269806860853084166,'aaaa',3,'24736743@qq.com',1,0,'2020-06-08 10:30:37','2020-06-08 10:30:37'),(1269806860853084167,'aaaa',3,'24736743@qq.com',1,0,'2020-06-08 10:58:41','2020-06-08 10:58:41');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
