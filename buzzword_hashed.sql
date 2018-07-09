CREATE DATABASE  IF NOT EXISTS `buzzwordbingodb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `buzzwordbingodb`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: buzzwordbingodb
-- ------------------------------------------------------
-- Server version	5.7.22

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
-- Table structure for table `ergebnis`
--

DROP TABLE IF EXISTS `ergebnis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ergebnis` (
  `Punktzahl` int(10) NOT NULL,
  `SpielID` int(10) NOT NULL,
  PRIMARY KEY (`Punktzahl`,`SpielID`),
  KEY `FKErgebnis180137` (`SpielID`),
  CONSTRAINT `FKErgebnis180137` FOREIGN KEY (`SpielID`) REFERENCES `spiel` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ergebnis`
--

LOCK TABLES `ergebnis` WRITE;
/*!40000 ALTER TABLE `ergebnis` DISABLE KEYS */;
/*!40000 ALTER TABLE `ergebnis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ergebnis_spieler`
--

DROP TABLE IF EXISTS `ergebnis_spieler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ergebnis_spieler` (
  `ErgebnisPunktzahl` int(10) NOT NULL,
  `ErgebnisSpielID` int(10) NOT NULL,
  `SpielerID` int(10) NOT NULL,
  PRIMARY KEY (`ErgebnisPunktzahl`,`ErgebnisSpielID`,`SpielerID`),
  KEY `FKErgebnis_S242244` (`ErgebnisPunktzahl`,`ErgebnisSpielID`),
  KEY `FKErgebnis_S109089` (`SpielerID`),
  CONSTRAINT `FKErgebnis_S109089` FOREIGN KEY (`SpielerID`) REFERENCES `spieler` (`ID`),
  CONSTRAINT `FKErgebnis_S242244` FOREIGN KEY (`ErgebnisPunktzahl`, `ErgebnisSpielID`) REFERENCES `ergebnis` (`Punktzahl`, `SpielID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ergebnis_spieler`
--

LOCK TABLES `ergebnis_spieler` WRITE;
/*!40000 ALTER TABLE `ergebnis_spieler` DISABLE KEYS */;
/*!40000 ALTER TABLE `ergebnis_spieler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spiel`
--

DROP TABLE IF EXISTS `spiel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spiel` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `IstAktiv` bit(1) NOT NULL,
  `WortkategorieName` char(255) CHARACTER SET utf8mb4 NOT NULL,
  `AdminID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKSpiel936764` (`WortkategorieName`),
  CONSTRAINT `FKSpiel936764` FOREIGN KEY (`WortkategorieName`) REFERENCES `wortkategorie` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spiel`
--

LOCK TABLES `spiel` WRITE;
/*!40000 ALTER TABLE `spiel` DISABLE KEYS */;
/*!40000 ALTER TABLE `spiel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spiel_spieler`
--

DROP TABLE IF EXISTS `spiel_spieler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spiel_spieler` (
  `SpielID` int(10) NOT NULL,
  `SpielerID` int(10) NOT NULL,
  PRIMARY KEY (`SpielID`,`SpielerID`),
  KEY `FKSpiel_Spie45551` (`SpielID`),
  KEY `FKSpiel_Spie638846` (`SpielerID`),
  CONSTRAINT `FKSpiel_Spie45551` FOREIGN KEY (`SpielID`) REFERENCES `spiel` (`ID`),
  CONSTRAINT `FKSpiel_Spie638846` FOREIGN KEY (`SpielerID`) REFERENCES `spieler` (`ID`),
  CONSTRAINT `spiel_spieler_ibfk_1` FOREIGN KEY (`SpielerID`) REFERENCES `spieler` (`ID`),
  CONSTRAINT `spiel_spieler_ibfk_2` FOREIGN KEY (`SpielID`) REFERENCES `spiel` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spiel_spieler`
--

LOCK TABLES `spiel_spieler` WRITE;
/*!40000 ALTER TABLE `spiel_spieler` DISABLE KEYS */;
/*!40000 ALTER TABLE `spiel_spieler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spieler`
--

DROP TABLE IF EXISTS `spieler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spieler` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Name` char(255) CHARACTER SET utf8mb4 NOT NULL,
  `Kennwort` char(255) CHARACTER SET utf8mb4 NOT NULL,
  `Punkte` int(10) NOT NULL,
  `IstAdmin` bit(1) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spieler`
--

LOCK TABLES `spieler` WRITE;
/*!40000 ALTER TABLE `spieler` DISABLE KEYS */;
INSERT INTO `spieler` VALUES (1,'Jesse','20f4901e7b029f13bc7b62b711efd3a37cc8474c48f8c4f95982779731567021',999999,''),(2,'Andi','0a9f173d20ecd8450fd138fdc56adf7cc3348292dd915ca92d262a3fdc347e97',0,''),(3,'Fabi','6b1245053632bab4ccbe77fd355289ebaed2d3e382f8eff0d7db12a62c79247b',0,''),(4,'Tuyet','00c85b4ea2e9e2e60eba1da1c189f79e4864125fdc7b47c38e820545a296fcb6',0,''),(5,'test','937e8d5fbb48bd4949536cd65b8d35c426b80d2f830c5c308e2cdec422ae2244',0,''),(6,'gast','d4759ff101186fe13f946d2683eaf5a9a5d947dcffd8ce38600565fa55c1e2b7',0,'\0');
/*!40000 ALTER TABLE `spieler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spielfeld`
--

DROP TABLE IF EXISTS `spielfeld`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spielfeld` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Woerter` text NOT NULL,
  `SpielerID` int(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spielfeld`
--

LOCK TABLES `spielfeld` WRITE;
/*!40000 ALTER TABLE `spielfeld` DISABLE KEYS */;
/*!40000 ALTER TABLE `spielfeld` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wort`
--

DROP TABLE IF EXISTS `wort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wort` (
  `Name` char(255) CHARACTER SET utf8mb4 NOT NULL,
  `Kategorie` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wort`
--

LOCK TABLES `wort` WRITE;
/*!40000 ALTER TABLE `wort` DISABLE KEYS */;
INSERT INTO `wort` VALUES ('360 Grad','Digitalisierung'),('3D','Digitalisierung'),('3D-Druck','Digitalisierung'),('5G','Digitalisierung'),('a','Klimawandel (coming soon)'),('Access','Digitalisierung'),('Agenda','Digitalisierung'),('Agil','Digitalisierung'),('Anstoss','Fussball'),('Apps','Digitalisierung'),('Augmented Reality','Digitalisierung'),('Ausgleich','Fussball'),('auswechseln','Fussball'),('Automatisierung','Digitalisierung'),('Autonomes Fahren','Digitalisierung'),('b','New Work (coming soon)'),('Ball','Fussball'),('Bier','Fussball'),('Big Data','Digitalisierung'),('Blockchain','Digitalisierung'),('Blog','Digitalisierung'),('c','Vorlesung (coming soon)'),('Cload Computing','Digitalisierung'),('Crowdsourcing','Digitalisierung'),('Cyber Security','Digitalisierung'),('Datenschutz','Digitalisierung'),('Debakel','Fussball'),('Deep Learning','Digitalisierung'),('Digitale Kluft','Digitalisierung'),('Digitalisierung','Digitalisierung'),('Drohnen','Digitalisierung'),('E-Agriculture','Digitalisierung'),('E-Governance','Digitalisierung'),('E-Health','Digitalisierung'),('E-Learning','Digitalisierung'),('E-Payment','Digitalisierung'),('Eckball','Fussball'),('Eigentor','Fussball'),('Einwurf','Fussball'),('Elfmeter','Fussball'),('Fairness','Fussball'),('Foul','Fussball'),('Freistoß','Fussball'),('gelbe Karte','Fussball'),('gestrecktes Bein','Fussball'),('Hackathon','Digitalisierung'),('Halbzeit','Fussball'),('Industrie 4.0','Digitalisierung'),('Information Management System','Digitalisierung'),('Innovation Hubs','Digitalisierung'),('Internet','Digitalisierung'),('Internet of Things','Digitalisierung'),('Kapitaen','Fussball'),('Kommentator','Fussball'),('kuenstliche Intelligenz','Digitalisierung'),('Linienrichter','Fussball'),('Merged reality','Digitalisierung'),('Mixed Reality','Digitalisierung'),('Mobilfunk','Digitalisierung'),('Netzneutralität','Digitalisierung'),('Niederlage','Fussball'),('Notbremse','Fussball'),('Open Government','Digitalisierung'),('Open Source','Digitalisierung'),('Pokal','Fussball'),('rote Karte','Fussball'),('Rückpass','Fussball'),('Sieger','Fussball'),('Smart Cities','Digitalisierung'),('Smart Factory','Digitalisierung'),('Smartphones','Digitalisierung'),('Social Media','Digitalisierung'),('Stadion','Fussball'),('Stuermer','Fussball'),('Taktik','Fussball'),('Tech Start-Ups','Digitalisierung'),('Torschuss','Fussball'),('Torwart','Fussball'),('Trainer','Fussball'),('Verlaengerung','Fussball'),('Verletzung','Fussball'),('Verteidiger','Fussball'),('Virtual Reality','Digitalisierung'),('Weltmeister','Fussball'),('Werbung','Fussball'),('zirkulieren','Fussball');
/*!40000 ALTER TABLE `wort` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-09 15:34:47
