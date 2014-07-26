-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 24, 2014 at 06:54 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `library`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookentry`
--

CREATE TABLE IF NOT EXISTS `bookentry` (
  `accNo` int(11) NOT NULL DEFAULT '0',
  `title` varchar(45) NOT NULL,
  `author` varchar(45) NOT NULL,
  `subject` varchar(45) NOT NULL,
  `edition` varchar(45) NOT NULL,
  `publication` varchar(45) NOT NULL,
  `price` int(11) NOT NULL,
  `isbn` varchar(45) NOT NULL,
  `totalCount` int(11) DEFAULT NULL,
  `issuedCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`accNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bookentry`
--

INSERT INTO `bookentry` (`accNo`, `title`, `author`, `subject`, `edition`, `publication`, `price`, `isbn`, `totalCount`, `issuedCount`) VALUES
(1, 'c programming by balaguruswamy', 'balaguruswamy', 'computers', '1', 'pearson', 200, '1234-5678-8910', 10, 4),
(2, 'let us c', 'yeswanth kanithkar', 'computers', '1', 'pearson', 300, '1234-5678-8910', 15, 1),
(3, 'head first java', 'james gosling', 'computers', '3', 'headfirst', 450, '1234-567-1234', 5, 2),
(4, 'object oriented programming', 'Balaguruswamy', 'computers', '5', 'pearson', 500, '34213-423-2132', 2, 2),
(5, 'Organigational Behaviour', 'Mogli', 'Management', '2', 'pearson', 210, '11-20-1223', 6, 4),
(6, 'Commutative Algebra', 'Ramanujan', 'Maths', '5', 'indian', 410, '132-34-56', 10, 2),
(7, 'physics experiment-2', 'cv.raman', 'physics', '2', 'indian', 315, '111-22-33-0', 7, 2),
(8, 'Data structures', 'kanithkar', 'computers', '2', 'pearson', 220, '22-02-331', 5, 1),
(9, 'Elements Of The Theory Of Computation', 'Harry R Lewis', 'Theory Of Computation', '1', 'pearson', 310, '10:0-07-107283-7', 10, 1),
(10, 'Introduction to Automata Theory', 'Hopcroft.J.E', 'Theory Of Computation', '2', 'Higher Education', 212, '13:978-07-10783-0', 5, 0),
(11, 'Computer Graphics', 'Donald Hearn', 'computers', 'C Version', 'pearson', 250, '11:00-20-03451', 9, 1),
(12, 'An Object Oriented Approach with C++', 'Bill Zoellick and Greg Riccardi', 'File Structures', '5', 'pearson', 350, '12:11-230-34671', 3, 3),
(13, 'Management Accounts', 'Michael J.Folk', 'Accounts', '4', 'Higher Education', 415, '10:11-138-001-0', 5, 2),
(14, 'Fundamentals Of Algorithms', 'kanithkar', 'Design And Analysis Of Algorithms', '2', 'Himalayas', 225, '5:01-34-267-1', 3, 3),
(15, 'OOSE', 'yeswanth kanithkar', 'computers', '2nd', 'pearson', 350, '22-02-331', 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `clerkinfo`
--

CREATE TABLE IF NOT EXISTS `clerkinfo` (
  `id` int(11) NOT NULL DEFAULT '0',
  `fullName` varchar(100) DEFAULT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `emailId` varchar(50) DEFAULT NULL,
  `contactNo` varchar(10) DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `usersTableClerkId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clerkinfo`
--

INSERT INTO `clerkinfo` (`id`, `fullName`, `qualification`, `address`, `emailId`, `contactNo`, `gender`, `usersTableClerkId`) VALUES
(1, 'rajesh', 'asdfasdf', 'asdfadsf', 'asdfadsf', 'asdfadsf', 'm', 2),
(2, 'bharathi', 'mca', 'chengalaraopeta', 'bharathidevi29@gmail.com', '9885626037', 'F', 3),
(3, 'N.Kiran Kumar', 'Bcom', 'ChengalaRaopeta,\r\nVisakhapatnam-1', 'kiran.nistala@gmail.com', '9885953153', 'm', 4);

-- --------------------------------------------------------

--
-- Table structure for table `issues`
--

CREATE TABLE IF NOT EXISTS `issues` (
  `id` int(11) DEFAULT NULL,
  `stuid` int(11) DEFAULT NULL,
  `bookid` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `isstudent` int(1) DEFAULT NULL,
  `clerkid` int(11) DEFAULT NULL,
  `startdate` date DEFAULT NULL,
  `enddate` date DEFAULT NULL,
  `fine` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `issues`
--

INSERT INTO `issues` (`id`, `stuid`, `bookid`, `status`, `isstudent`, `clerkid`, `startdate`, `enddate`, `fine`) VALUES
(1, 1, 1, 0, 1, 2, '2014-03-10', '2014-04-09', 30),
(2, 2, 1, 0, 1, 2, '2014-03-10', '2014-04-09', 30),
(3, 3, 4, 1, 1, 3, '2014-03-10', NULL, 0),
(4, 1, 4, 1, 1, 3, '2014-04-06', NULL, 0),
(5, 5, 6, 0, 1, 1, '2014-04-07', '2014-04-19', 0),
(6, 7, 6, 0, 0, 1, '2014-04-07', '2014-04-19', 0),
(7, 6, 5, 0, 1, 1, '2014-04-07', '2014-04-24', 17),
(8, 4, 14, 1, 0, 1, '2014-04-07', NULL, 0),
(9, 3, 14, 1, 1, 1, '2014-04-07', NULL, 0),
(10, 5, 14, 1, 1, 1, '2014-04-07', NULL, 0),
(11, 6, 13, 0, 0, 1, '2014-04-07', '2014-04-09', 0),
(12, 5, 3, 1, 0, 1, '2014-04-07', NULL, 0),
(13, 7, 13, 1, 1, 1, '2014-04-07', NULL, 0),
(14, 8, 7, 1, 1, 1, '2014-04-07', NULL, 0),
(15, 4, 9, 1, 1, 1, '2014-04-07', NULL, 0),
(16, 1, 12, 0, 1, 1, '2014-04-08', '2014-04-09', 0),
(17, 6, 1, 0, 0, 1, '2014-04-08', '2014-04-09', 0),
(18, 6, 11, 0, 0, 1, '2014-04-08', '2014-04-09', 0),
(19, 6, 5, 1, 0, 1, '2014-04-08', NULL, 0),
(20, 6, 7, 1, 0, 1, '2014-04-08', NULL, 0),
(21, 6, 8, 1, 1, 1, '2014-04-08', NULL, 0),
(22, 6, 2, 1, 0, 1, '2014-04-09', NULL, 0),
(23, 1, 1, 0, 1, 1, '2014-04-09', '2014-04-09', 0),
(24, 2, 3, 1, 1, 1, '2014-04-09', NULL, 0),
(25, 5, 12, 1, 1, 1, '2014-04-15', NULL, 0),
(26, 3, 15, 1, 1, 1, '2014-04-22', NULL, 0),
(27, 2, 12, 1, 0, 1, '2014-04-22', NULL, 0),
(28, 3, 5, 1, 0, 1, '2014-04-20', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `pages`
--

CREATE TABLE IF NOT EXISTS `pages` (
  `id` int(11) NOT NULL,
  `pagename` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pages`
--

INSERT INTO `pages` (`id`, `pagename`) VALUES
(1, 'CreateClerk'),
(2, 'StudentEntry'),
(3, 'StaffEntry'),
(4, 'BookEntry'),
(5, 'Issues'),
(6, 'Returns'),
(9, 'AllReports');

-- --------------------------------------------------------

--
-- Table structure for table `returns`
--

CREATE TABLE IF NOT EXISTS `returns` (
  `Accno` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `staffentry`
--

CREATE TABLE IF NOT EXISTS `staffentry` (
  `mcode` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Contact` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Qualification` varchar(45) NOT NULL,
  `desig` varchar(45) NOT NULL,
  `Department` varchar(45) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `Gender` varchar(45) NOT NULL,
  `DOB` varchar(45) NOT NULL,
  `DOJ` varchar(45) NOT NULL,
  PRIMARY KEY (`mcode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staffentry`
--

INSERT INTO `staffentry` (`mcode`, `Name`, `Contact`, `Email`, `Qualification`, `desig`, `Department`, `Address`, `Gender`, `DOB`, `DOJ`) VALUES
(1, 'rajesh', '9642566568', 'asdfasdfsdf', 'Mtech', 'faculty', 'Computer Science', 'telakalaveedhi\r\nvsp', 'm', '13-7-1990', '12-09-2011'),
(2, 'kiran', '9642566568', 'kiran.nistala@gmail.com', 'mcom', 'Asst.Professor', 'physics', 'visakhapatnam', 'm', '28-08-1985', '28-08-2010'),
(3, 'B.v.p.Latha', '9885953153', 'lathabvp@gmail.com', 'Mtech', 'Asst.Professor', 'Computerscience', 'Doctors colony\r\nVisakhapatnam', 'F', '16-08-1984', '12-06-2009'),
(4, 'M.A.Prasad', '9988776655', 'prasadma@gamail.com', 'Mtech', 'Asst.Professor', 'Computer Science', 'Chaitanya nagar\r\nVsp', 'm', '15-09-1980', '12-06-2004'),
(5, 'U.Padma Mohan', '9885626037', 'Padmamohan@gmail.com', 'Phd', 'Head of Dept', 'Computer Science', 'Doctors colony\r\nVisakhapatnam', 'F', '15-8-1979', '12-06-2000'),
(6, 'Harikrishna', '9885953153', 'harikrishna123@gmail.com', 'M.Com.', 'faculty', 'Arts dept', 'mdp\r\nvsp', 'm', '30-1-1975', '24-6-2004'),
(7, 'virendranath', '9988512341', 'veeru@gmail.com', 'Mphil', 'Lecturer', 'Maths dept', 'eenadu\r\nvsp', 'm', '12-4-1978', '13-6-2006'),
(8, 'Sharukhan', '9988776656', 'sharukh@gamil.com', 'Mphil', 'faculty', 'Chemistry', 'Doctors colony\r\nvsp', 'm', '12-6-81', '15-8-2010'),
(9, 'Harish', '9988512342', 'harish34@gmail.com', 'Mtech', 'Asst.Profficer', 'Computer Science', 'seethammadhara\r\nVsp', 'm', '13-4-1982', '13-6-2006'),
(10, 'pravallika', '1245252', 'pravallika@gmail.com', 'Mtech', 'Asst.Profficer', 'Computer Science', 'rayagada', 'F', '20-05-1989', '15-6-2009'),
(11, 'K.Sathish', '9642566568', 'sathish33@gmail.com', 'M.TECH', 'Asst.Professor', 'Computer Science', 'Maddilapalem,\r\nvisakhapatnam', 'm', '12-06-1983', '15-08-2006');

-- --------------------------------------------------------

--
-- Table structure for table `studententry`
--

CREATE TABLE IF NOT EXISTS `studententry` (
  `mcode` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Branch` varchar(45) NOT NULL,
  `Contact` varchar(20) NOT NULL,
  `batch` varchar(45) NOT NULL,
  `emailId` varchar(45) NOT NULL,
  `admNo` varchar(20) NOT NULL,
  `Address` varchar(45) NOT NULL,
  `Gender` varchar(45) NOT NULL,
  `DOB` varchar(45) NOT NULL,
  PRIMARY KEY (`mcode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `studententry`
--

INSERT INTO `studententry` (`mcode`, `Name`, `Branch`, `Contact`, `batch`, `emailId`, `admNo`, `Address`, `Gender`, `DOB`) VALUES
(1, 'rajesh', 'mca', '9885953153', '2011-2013', 'rajesh.chaganti18@gmail.com', '123123', 'asdfadsfadsf', 'm', '28-03-1990'),
(2, 'K.Gayatri', 'Mca', '9988553344', '2011-2014', 'gayatrik@gmail.com', '111', 'chaitanyanagar\r\nvisakhapatnam', 'F', '12-3-1992'),
(3, 'k.Bharathi Devi', 'Mca', '9885953153', '2011-2014', 'bharathidevi29@gmail.com', '333', 'Dr.No:20-134-1\r\nkotha agraharam\r\nvisakha-1', 'F', '12-6-1989'),
(4, 'chaturay', 'msc', '9948162740', '2011-2014', 'chaturya.nishtala@gmail.com', '136', 'changalaraopeta\r\nvsp', 'F', '15-12-1990'),
(5, 'Sathvika', 'msc.maths', '9885953153', '2009-2011', 'sathvika.nishtala@gmail.com', '192', 'mvp colony\r\nvsp', 'F', '16-09-1989'),
(6, 'Raghuramrajan', 'Mcom', '9988553344', '2011-2014', 'raghu.rr@gmail.com', '201', 'mdp\r\nvsp-13', 'm', '14-04-1990'),
(7, 'Aswini', 'computerscience', '9988512341', '2011-2014', 'aswini.123@gmail.com', '2593', 'Burujupeta,\r\nVisakhapatnam-1', 'F', '29-11-1991');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'admin', 'admin123', 1),
(2, 'rajesh', '123456', 2),
(3, 'bharathi', 'chaturya', 2),
(4, 'Kiran', 'vagdevi', 2);

-- --------------------------------------------------------

--
-- Table structure for table `userspagesmapping`
--

CREATE TABLE IF NOT EXISTS `userspagesmapping` (
  `id` int(11) DEFAULT NULL,
  `pageId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userspagesmapping`
--

INSERT INTO `userspagesmapping` (`id`, `pageId`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(1, 9);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
