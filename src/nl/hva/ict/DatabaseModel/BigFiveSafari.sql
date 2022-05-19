-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 192.168.1.61
-- Generation Time: May 19, 2022 at 11:49 AM
-- Server version: 10.7.3-MariaDB-1:10.7.3+maria~focal
-- PHP Version: 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `BigFiveSafari`
--

-- --------------------------------------------------------

--
-- Table structure for table `Accommodatie`
--

CREATE TABLE `Accommodatie` (
  `accommodatie_code` varchar(5) NOT NULL,
  `naam` varchar(45) NOT NULL,
  `stad` varchar(45) NOT NULL,
  `land` varchar(45) NOT NULL,
  `kamer` varchar(45) NOT NULL,
  `personen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `Hotel`
--

CREATE TABLE `Hotel` (
  `accommodatie_code` varchar(5) NOT NULL,
  `prijs_per_nacht` double NOT NULL,
  `ontbijt` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `Lodge`
--

CREATE TABLE `Lodge` (
  `accommodatie_code` varchar(5) NOT NULL,
  `prijs_per_week` double NOT NULL,
  `autohuur` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `Reiziger`
--

CREATE TABLE `Reiziger` (
  `reiziger_code` varchar(45) NOT NULL,
  `voornaam` varchar(45) NOT NULL,
  `achternaam` varchar(45) NOT NULL,
  `adres` varchar(45) NOT NULL,
  `postcode` varchar(7) NOT NULL,
  `plaats` varchar(45) NOT NULL,
  `land` varchar(45) NOT NULL,
  `gezinshoofd` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `Reservering`
--

CREATE TABLE `Reservering` (
  `id_reservering` int(11) NOT NULL,
  `accommodatie_code` varchar(5) NOT NULL,
  `reizigers_code` varchar(45) NOT NULL,
  `aankomst_datum` date NOT NULL,
  `vertrek_datum` date NOT NULL,
  `betaald` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Accommodatie`
--
ALTER TABLE `Accommodatie`
  ADD PRIMARY KEY (`accommodatie_code`);

--
-- Indexes for table `Hotel`
--
ALTER TABLE `Hotel`
  ADD PRIMARY KEY (`accommodatie_code`);

--
-- Indexes for table `Lodge`
--
ALTER TABLE `Lodge`
  ADD PRIMARY KEY (`accommodatie_code`);

--
-- Indexes for table `Reiziger`
--
ALTER TABLE `Reiziger`
  ADD PRIMARY KEY (`reiziger_code`),
  ADD KEY `fk_Reiziger_Reiziger1_idx` (`gezinshoofd`);

--
-- Indexes for table `Reservering`
--
ALTER TABLE `Reservering`
  ADD PRIMARY KEY (`id_reservering`),
  ADD KEY `fk_Reservering_Accommodatie1_idx` (`accommodatie_code`),
  ADD KEY `fk_Reservering_Reiziger1_idx` (`reizigers_code`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Hotel`
--
ALTER TABLE `Hotel`
  ADD CONSTRAINT `fk_Hotel_Accommodatie1` FOREIGN KEY (`accommodatie_code`) REFERENCES `Accommodatie` (`accommodatie_code`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Lodge`
--
ALTER TABLE `Lodge`
  ADD CONSTRAINT `fk_Lodge_Accommodatie1` FOREIGN KEY (`accommodatie_code`) REFERENCES `Accommodatie` (`accommodatie_code`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Reiziger`
--
ALTER TABLE `Reiziger`
  ADD CONSTRAINT `fk_Reiziger_Reiziger1` FOREIGN KEY (`gezinshoofd`) REFERENCES `Reiziger` (`reiziger_code`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Reservering`
--
ALTER TABLE `Reservering`
  ADD CONSTRAINT `fk_Reservering_Accommodatie1` FOREIGN KEY (`accommodatie_code`) REFERENCES `Accommodatie` (`accommodatie_code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Reservering_Reiziger1` FOREIGN KEY (`reizigers_code`) REFERENCES `Reiziger` (`reiziger_code`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
