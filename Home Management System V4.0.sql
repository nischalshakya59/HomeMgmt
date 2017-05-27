-- MySQL dump 10.13  Distrib 5.6.24, for Win32 (x86)
--
-- Host: localhost    Database: homemgmt
-- ------------------------------------------------------
-- Server version	5.5.47

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
-- Table structure for table `contactgrouptbl`
--

DROP TABLE IF EXISTS `contactgrouptbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactgrouptbl` (
  `contactgroupid` int(11) NOT NULL AUTO_INCREMENT,
  `contactgroupname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`contactgroupid`),
  UNIQUE KEY `contactgroupname_UNIQUE` (`contactgroupname`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactgrouptbl`
--

LOCK TABLES `contactgrouptbl` WRITE;
/*!40000 ALTER TABLE `contactgrouptbl` DISABLE KEYS */;
INSERT INTO `contactgrouptbl` VALUES (11,'Family'),(10,'Friend');
/*!40000 ALTER TABLE `contactgrouptbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactmgmttbl`
--

DROP TABLE IF EXISTS `contactmgmttbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactmgmttbl` (
  `contactid` int(11) NOT NULL,
  `contactfname` varchar(45) DEFAULT NULL,
  `contactlname` varchar(45) DEFAULT NULL,
  `contactrelation` varchar(45) DEFAULT NULL,
  `contactaddress` varchar(45) DEFAULT NULL,
  `contactno` varchar(45) DEFAULT NULL,
  `contactgroup` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`contactid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactmgmttbl`
--

LOCK TABLES `contactmgmttbl` WRITE;
/*!40000 ALTER TABLE `contactmgmttbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `contactmgmttbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventmgmttbl`
--

DROP TABLE IF EXISTS `eventmgmttbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventmgmttbl` (
  `eventid` int(11) NOT NULL,
  `entrydate` varchar(45) DEFAULT NULL,
  `eventdate` varchar(45) DEFAULT NULL,
  `eventname` varchar(45) DEFAULT NULL,
  `noofdays` int(11) DEFAULT NULL,
  PRIMARY KEY (`eventid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventmgmttbl`
--

LOCK TABLES `eventmgmttbl` WRITE;
/*!40000 ALTER TABLE `eventmgmttbl` DISABLE KEYS */;
INSERT INTO `eventmgmttbl` VALUES (1,'Mar 19  2016','Mar 25 2016','Marriage',6),(2,'Mar 18  2016','Mar 26 2016','Convocation',8),(3,'Mar 17  2016','Mar 25 2016','Presentation',8),(4,'Mar 18  2016','Mar 19 2016','Test',1);
/*!40000 ALTER TABLE `eventmgmttbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logintbl`
--

DROP TABLE IF EXISTS `logintbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logintbl` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `usertype` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  KEY `username_idx` (`username`),
  KEY `usertype_idx` (`usertype`),
  KEY `password` (`password`),
  CONSTRAINT `password` FOREIGN KEY (`password`) REFERENCES `userinfotbl` (`userinfopassword`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `userinfotbl` (`userinfousername`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logintbl`
--

LOCK TABLES `logintbl` WRITE;
/*!40000 ALTER TABLE `logintbl` DISABLE KEYS */;
INSERT INTO `logintbl` VALUES (1,'nischal','nischal','admin'),(2,'naruto','uzumaki','user');
/*!40000 ALTER TABLE `logintbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `particulartbl`
--

DROP TABLE IF EXISTS `particulartbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `particulartbl` (
  `pid` int(11) NOT NULL,
  `pname` varchar(45) NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `pname_UNIQUE` (`pname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `particulartbl`
--

LOCK TABLES `particulartbl` WRITE;
/*!40000 ALTER TABLE `particulartbl` DISABLE KEYS */;
INSERT INTO `particulartbl` VALUES (6,'Bank'),(5,'Bill'),(2,'Electricity'),(3,'Housing'),(4,'Insurance'),(1,'Rent');
/*!40000 ALTER TABLE `particulartbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactiontbl`
--

DROP TABLE IF EXISTS `transactiontbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactiontbl` (
  `entryid` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(45) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `particular` varchar(45) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `remark` longtext,
  PRIMARY KEY (`entryid`),
  UNIQUE KEY `entryid_UNIQUE` (`entryid`),
  KEY `catid_idx` (`pid`),
  KEY `pid_idx` (`pid`,`particular`),
  CONSTRAINT `pid` FOREIGN KEY (`pid`) REFERENCES `particulartbl` (`pid`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactiontbl`
--

LOCK TABLES `transactiontbl` WRITE;
/*!40000 ALTER TABLE `transactiontbl` DISABLE KEYS */;
INSERT INTO `transactiontbl` VALUES (1,'Mar 26 2016',NULL,'Electricity',89000,'\r\r\r\r\r\r\r'),(2,'Mar 19 2016',NULL,'Housing',90000,'\r\r\r\r\r\r'),(3,'Mar 19 2016',NULL,'Electricity',560000,'\r\r\r\r\r\r\r'),(4,'Mar 19 2016',NULL,'Machine',890000,'\r\r\r\r\r\r'),(8,'Mar 12 2016',NULL,'Electricity',67000,'\r\r\r\r\r\r\r'),(9,'Mar 13 2016',NULL,'Machine',80000,'\r\r\r\r'),(10,'2016/03/16 ',NULL,'Hu',90000,''),(11,'MMM d, yyyy',NULL,'Electricity',67,''),(12,'MMM d, yyyy',NULL,'Housing',87,''),(13,'2016/03/16 ',NULL,'Housing',89000,''),(14,'Mar 16 2016',3,'Housing',90000,'\r'),(15,'Mar 18 2016',4,'Insurance',90000,'\r'),(16,'Mar 31 2016',3,'Housing',21000,'\r'),(17,'Mar 26 2016',3,'Housing',56000,'\r'),(18,'Mar 26 2016',6,'Bank',98000,''),(19,'Mar 19 2016',3,'Housing',98000,''),(20,'Mar 26 2016',1,'Rent',89000,'');
/*!40000 ALTER TABLE `transactiontbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfotbl`
--

DROP TABLE IF EXISTS `userinfotbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userinfotbl` (
  `userinfoid` int(11) NOT NULL,
  `userinfofname` varchar(45) DEFAULT NULL,
  `userinfolname` varchar(45) DEFAULT NULL,
  `userinfocontact` varchar(45) DEFAULT NULL,
  `userinfousername` varchar(45) DEFAULT NULL,
  `userinfopassword` varchar(45) DEFAULT NULL,
  `userinfousertype` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userinfoid`),
  UNIQUE KEY `userinfousername_UNIQUE` (`userinfousername`),
  KEY `userinfousertype` (`userinfousertype`),
  KEY `userinfopassword` (`userinfopassword`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfotbl`
--

LOCK TABLES `userinfotbl` WRITE;
/*!40000 ALTER TABLE `userinfotbl` DISABLE KEYS */;
INSERT INTO `userinfotbl` VALUES (1,'Shrejan','Maharjan','9808977210','nischal','nischal','admin'),(2,'Nischal','Shakya','9841808707','naruto','uzumaki','user');
/*!40000 ALTER TABLE `userinfotbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-18 18:53:45
