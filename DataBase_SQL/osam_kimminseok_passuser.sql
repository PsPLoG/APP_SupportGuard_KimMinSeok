-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: osam
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `kimminseok_passuser`
--

DROP TABLE IF EXISTS `kimminseok_passuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kimminseok_passuser` (
  `IdCode` int(11) NOT NULL AUTO_INCREMENT,
  `ID` int(11) NOT NULL COMMENT '출입코드',
  `Pass` text NOT NULL COMMENT '비밀번호',
  `Rank` int(11) NOT NULL COMMENT '계급',
  `aType` text NOT NULL COMMENT '육해군 소속',
  `affiliation` text NOT NULL COMMENT '소속부대',
  `aCode` text NOT NULL COMMENT '부대코드',
  `pName` text NOT NULL COMMENT '이름',
  `ImgSrc` text NOT NULL COMMENT '이미지 경로',
  PRIMARY KEY (`IdCode`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kimminseok_passuser`
--

LOCK TABLES `kimminseok_passuser` WRITE;
/*!40000 ALTER TABLE `kimminseok_passuser` DISABLE KEYS */;
INSERT INTO `kimminseok_passuser` VALUES (2,15123456,'8219',3,'공군','61사단','100','김육군','desert.jpg'),(4,1676017574,'8219',10,'육군','61사단','100','김민석','desert.jpg');
/*!40000 ALTER TABLE `kimminseok_passuser` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-20  3:35:02
