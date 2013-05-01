-- MySQL dump 10.13  Distrib 5.5.28, for osx10.6 (i386)
--
-- Host: localhost    Database: biblibouille
-- ------------------------------------------------------
-- Server version	5.5.28

DROP DATABASE IF EXISTS `biblibouilleDB`;

CREATE DATABASE `biblibouilleDB`;

USE `biblibouilleDB`;

--
-- Table structure for table `Author`
--

DROP TABLE IF EXISTS `Author`;

CREATE TABLE `Author` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `Book`
--

DROP TABLE IF EXISTS `Book`;

CREATE TABLE `Book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `collection` varchar(255) DEFAULT NULL,
  `shelf` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `OWNER_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1FAF09CA67E77F` (`author_id`),
  KEY `FK1FAF0918F694D7` (`OWNER_ID`),
  CONSTRAINT `FK1FAF0918F694D7` FOREIGN KEY (`OWNER_ID`) REFERENCES `User` (`id`),
  CONSTRAINT `FK1FAF09CA67E77F` FOREIGN KEY (`author_id`) REFERENCES `Author` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

