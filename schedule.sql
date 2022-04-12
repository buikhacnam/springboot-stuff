-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: schedule
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_user`
--

use heroku_f82e0f2be0ede12;

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `app_user` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (31,'Bui Nam','$2a$10$uf7rRRgx.heMemRkiIP7kODJOAX4XgAcjYSRh1c1vcH7wKaIDqtSq','buinam'),(32,'Frank Lampard','$2a$10$YOQilvfTo30rGfQTrpcooeNsdzhynpWTMNFxN17Yr8WH8/cCbSwua','lampard'),(33,'John Terry','$2a$10$2XXG8wSTixppEOT6XCAkZO0KmeJ6ye/Pz.AHtwWNB/ayaeSjz2Dee','terry');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_user_roles`
--

DROP TABLE IF EXISTS `app_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `app_user_roles` (
  `app_user_id` bigint NOT NULL,
  `roles_id` bigint NOT NULL,
  KEY `FK23e7b5jyl3ql41rk3566gywdd` (`roles_id`),
  KEY `FKkwxexnudtp5gmt82j0qtytnoe` (`app_user_id`),
  CONSTRAINT `FK23e7b5jyl3ql41rk3566gywdd` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKkwxexnudtp5gmt82j0qtytnoe` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user_roles`
--

LOCK TABLES `app_user_roles` WRITE;
/*!40000 ALTER TABLE `app_user_roles` DISABLE KEYS */;
INSERT INTO `app_user_roles` VALUES (32,29),(33,29),(31,29),(31,28);
/*!40000 ALTER TABLE `app_user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (61);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `map_schedule`
--

DROP TABLE IF EXISTS `map_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `map_schedule` (
  `id` bigint NOT NULL,
  `create_user` varchar(255) NOT NULL,
  `update_user` varchar(255) NOT NULL,
  `category_id` bigint DEFAULT NULL,
  `schedule_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `map_schedule`
--

LOCK TABLES `map_schedule` WRITE;
/*!40000 ALTER TABLE `map_schedule` DISABLE KEYS */;
INSERT INTO `map_schedule` VALUES (13,'buinam','buinam',28,31),(14,'buinam','buinam',30,31),(35,'buinam','buinam',28,34),(36,'buinam','buinam',28,35),(37,'buinam','buinam',28,36),(46,'buinam','buinam',28,45),(47,'buinam','buinam',30,45),(54,'buinam','buinam',28,53),(55,'buinam','buinam',29,53),(56,'buinam','buinam',30,53),(58,'buinam','buinam',29,48),(59,'buinam','buinam',30,48),(60,'buinam','buinam',57,48);
/*!40000 ALTER TABLE `map_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (28,'ROLE_ADMIN'),(29,'ROLE_USER'),(30,'ROLE_MANAGER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `id` bigint NOT NULL,
  `create_user` varchar(255) NOT NULL,
  `update_user` varchar(255) NOT NULL,
  `create_date_time` datetime(6) DEFAULT NULL,
  `description` varchar(4000) DEFAULT NULL,
  `end_date_time` datetime(6) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `participator` varchar(255) DEFAULT NULL,
  `start_date_time` datetime(6) DEFAULT NULL,
  `update_date_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (15,'buinam','buinam','2022-04-05 06:18:56.266674','same 20th date','2022-03-20 09:00:00.000000','vinh phuc','same 20th date','ddsd@df.com,ssa@fs.com','2022-03-20 07:33:33.000000','2022-04-05 06:18:56.266674'),(31,'buinam','buinam','2022-03-27 06:35:52.474072','tow two two','2022-03-10 09:00:00.000000','vinh phuc nha','222222','ddsd@df.com,ssa@fs.com','2022-03-01 07:33:33.000000','2022-04-04 15:22:41.050816'),(34,'buinam','buinam','2022-04-09 05:08:31.745771','hello there','2022-04-09 09:00:00.000000','vinh phuc','april 9','ddsd@df.com,ssa@fs.com','2022-04-09 07:33:33.000000','2022-04-09 05:08:31.745771'),(35,'buinam','buinam','2022-03-27 06:37:28.051923','tow two two','2022-03-10 09:00:00.000000','vinh phuc','222222','ddsd@df.com,ssa@fs.com','2022-03-01 07:33:33.000000','2022-03-27 06:37:28.051923'),(36,'buinam','buinam','2022-04-09 05:10:39.180368','hello there','2022-04-07 09:00:00.000000','vinh phuc','april 9','ddsd@df.com,ssa@fs.com','2022-04-05 07:33:33.000000','2022-04-09 05:10:39.180368'),(38,'buinam','buinam','2022-04-09 05:33:55.593106','sad asds dderet rryyjkjui gk','2022-04-04 12:30:00.000000','sahara','hahahah','nam@nam.com,bui@nam.com','2022-04-04 09:30:00.000000','2022-04-09 05:34:05.790437'),(39,'buinam','buinam','2022-04-09 05:36:42.346465','vỀ QUÊ CHƠI','2022-04-09 12:00:00.000000','Hà Trung','Ăn cơm nha','huongntl@gmail.com,bui@nam.com','2022-04-09 11:00:00.000000','2022-04-09 05:36:48.624396'),(40,'buinam','buinam','2022-04-11 10:40:33.308505','nothing','2022-04-15 13:40:05.000000','hanoi','from april 4 to april 15','','2022-04-11 10:40:02.000000','2022-04-11 10:40:33.308505'),(41,'buinam','buinam','2022-04-11 13:30:42.390620','','2022-04-15 09:30:00.000000','','aaaaaaaaaaaa','','2022-04-15 09:00:00.000000','2022-04-11 13:30:42.390620'),(42,'buinam','buinam','2022-04-11 13:31:26.003836','','2022-04-20 11:30:00.000000','','bbbbbb','','2022-04-13 11:00:00.000000','2022-04-11 13:31:26.003836'),(43,'buinam','buinam','2022-04-11 13:55:14.239794','','2022-07-05 00:00:00.000000','','JULY','','2022-07-04 00:00:00.000000','2022-04-11 13:55:14.239794'),(44,'buinam','buinam','2022-04-11 14:10:51.200025','','2022-06-06 01:00:00.000000','','yyyy','','2022-05-31 00:00:00.000000','2022-04-11 14:13:51.358401'),(45,'buinam','buinam','2022-04-11 14:57:35.668351','hello there','2022-04-12 09:00:00.000000','vinh phuc','april 9','ddsd@df.com,ssa@fs.com','2022-04-12 07:33:33.000000','2022-04-11 14:57:35.668351'),(48,'buinam','buinam','2022-04-11 16:57:21.426994','1111','2022-04-10 10:30:00.000000','home','category meeting','','2022-04-10 10:00:00.000000','2022-04-12 07:18:05.817475'),(53,'buinam','buinam','2022-04-11 17:21:44.077384','','2022-04-12 11:00:00.000000','','gkhgkjghjhg','','2022-04-12 10:30:00.000000','2022-04-11 17:21:44.077384');
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_categories`
--

DROP TABLE IF EXISTS `schedule_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `schedule_categories` (
  `id` bigint NOT NULL,
  `color_schedule` varchar(255) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_categories`
--

LOCK TABLES `schedule_categories` WRITE;
/*!40000 ALTER TABLE `schedule_categories` DISABLE KEYS */;
INSERT INTO `schedule_categories` VALUES (28,'blue','2022-03-27 06:28:49.256463','playing game nhe','game','2022-04-12 16:46:30.754855'),(29,'yellow','2022-03-27 06:30:04.078564','meeting description','meeting','2022-04-12 16:21:09.299354');
/*!40000 ALTER TABLE `schedule_categories` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-13  5:47:46
