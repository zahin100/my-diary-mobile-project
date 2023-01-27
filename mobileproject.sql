-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 27, 2023 at 04:17 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mobileproject`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `ID` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Email` varchar(80) NOT NULL,
  `Password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`ID`, `Username`, `Email`, `Password`) VALUES
(1, 'nabil', 'nabil@gmail.com', 'abc123'),
(2, 'billy', 'billy@gmail.com', 'abc123'),
(3, 'sammy', 'bil@gmail.com', '123'),
(4, 'syahirah', 'syahirah@gmail.com', 'abc123');

-- --------------------------------------------------------

--
-- Table structure for table `customer_Profile`
--

CREATE TABLE `customer_Profile` (
  `ID` int(11) NOT NULL,
  `FirstName` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) DEFAULT NULL,
  `PhoneNum` varchar(12) DEFAULT NULL,
  `Birthdate` varchar(20) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `Gender` varchar(10) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer_Profile`
--

INSERT INTO `customer_Profile` (`ID`, `FirstName`, `LastName`, `PhoneNum`, `Birthdate`, `Address`, `Gender`, `Username`) VALUES
(1, 'Nabil', 'Shukri', '019-9362101', '21/1/2023', 'Puncak Alam', 'Male', 'nabil');

-- --------------------------------------------------------

--
-- Table structure for table `Notes`
--

CREATE TABLE `Notes` (
  `ID` int(11) NOT NULL,
  `Notes` varchar(2000) DEFAULT NULL,
  `Date` varchar(2000) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Notes`
--

INSERT INTO `Notes` (`ID`, `Notes`, `Date`, `Username`) VALUES
(1, 'Today is Good day.', 'Wed Jan 25 01:11:10 GMT+08:00 2023', 'nabil'),
(2, 'Today is a Horrible day. Something Bad happen to me today.', 'Wed Jan 25 22:39:51 GMT+08:00 2023', 'nabil'),
(6, 'I did a 10km Run today.', 'Thu Jan 26 00:48:38 GMT+08:00 2023', 'nabil'),
(7, 'I went to Sushi King with my Father and my Sister for dinner today', 'Thu Jan 26 01:02:00 GMT+08:00 2023', 'nabil'),
(8, 'Tiring day', 'Fri Jan 27 19:32:58 GMT+08:00 2023', 'nabil'),
(9, 'I didn\'t eat for lunch today.', 'Fri Jan 27 19:33:43 GMT+08:00 2023', 'nabil');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `customer_Profile`
--
ALTER TABLE `customer_Profile`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Notes`
--
ALTER TABLE `Notes`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `customer_Profile`
--
ALTER TABLE `customer_Profile`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Notes`
--
ALTER TABLE `Notes`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
