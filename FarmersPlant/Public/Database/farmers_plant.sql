-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 15, 2022 at 11:38 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `farmers_plant`
--

-- --------------------------------------------------------

--
-- Table structure for table `addresses`
--

CREATE TABLE `addresses` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `house_no` varchar(100) NOT NULL,
  `street_1` varchar(100) NOT NULL,
  `street_2` varchar(100) NOT NULL,
  `barangay` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `zip` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `addresses`
--

INSERT INTO `addresses` (`id`, `user_id`, `house_no`, `street_1`, `street_2`, `barangay`, `city`, `zip`, `created_at`, `updated_at`) VALUES
(1, 1, 'Blk27a Lot25', 'Armstrong Avenue', 'Kalayaan Village', '201', 'Pasay', 1300, '2022-03-01 16:55:29', '2022-03-01 16:55:29'),
(2, 4, 'Blk27a Lot25', 'Armstrong Avenue', 'Kalayaan Village', '201', 'Pasay', 1300, '2022-03-02 02:44:32', '2022-03-02 02:44:32');

-- --------------------------------------------------------

--
-- Table structure for table `admin_table`
--

CREATE TABLE `admin_table` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `adminNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin_table`
--

INSERT INTO `admin_table` (`username`, `password`, `full_name`, `adminNo`) VALUES
('admin', '21232f297a57a5a743894a0e4a801fc3', 'admin1', 2),
('asd', '7815696ecbf1c96e6894b779456d330e', 'asd', 16);

-- --------------------------------------------------------

--
-- Table structure for table `feedbacks`
--

CREATE TABLE `feedbacks` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `feedback` text NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `feedbacks`
--

INSERT INTO `feedbacks` (`id`, `user_id`, `task_id`, `feedback`, `created_at`, `updated_at`) VALUES
(2, 1, 7, 'passed', '2022-03-12 16:40:49', '2022-03-12 16:40:49');

-- --------------------------------------------------------

--
-- Table structure for table `file_uploads`
--

CREATE TABLE `file_uploads` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `file_path` text NOT NULL,
  `file_name` varchar(200) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `file_uploads`
--

INSERT INTO `file_uploads` (`id`, `user_id`, `file_path`, `file_name`, `created_at`, `updated_at`) VALUES
(37, 1, '1/Session_1_Seed_Preparations_Seed_sowing_Nursery.pdf', 'Session_1_Seed_Preparations_Seed_sowing_Nursery.pdf', '2022-03-01 17:15:56', '2022-03-01 17:15:56'),
(46, 1, '1/Session_3_Transplanting_Water_Management.pdf', 'Session_3_Transplanting_Water_Management.pdf', '2022-03-01 17:23:48', '2022-03-01 17:23:48'),
(47, 1, '1/Session_4_Plant_Fertilization_Nutrition.pdf', 'Session_4_Plant_Fertilization_Nutrition.pdf', '2022-03-01 17:23:55', '2022-03-01 17:23:55'),
(48, 1, '1/Session_5_Trellising.pdf', 'Session_5_Trellising.pdf', '2022-03-01 17:23:58', '2022-03-01 17:23:58'),
(49, 1, '1/Session_6_Pest_Management.pdf', 'Session_6_Pest_Management.pdf', '2022-03-01 17:24:01', '2022-03-01 17:24:01'),
(50, 1, '1/Session_7_Environmental_Factors_Affecting_Production.pdf', 'Session_7_Environmental_Factors_Affecting_Production.pdf', '2022-03-01 17:24:04', '2022-03-01 17:24:04');

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` text NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`id`, `user_id`, `name`, `created_at`, `updated_at`) VALUES
(7, 1, 'Take a picture of your carrot farm', '2022-03-12 15:27:21', '2022-03-12 15:27:21');

-- --------------------------------------------------------

--
-- Table structure for table `task_uploads`
--

CREATE TABLE `task_uploads` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `task_path` text NOT NULL,
  `task_name` varchar(255) NOT NULL,
  `is_graded` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `task_uploads`
--

INSERT INTO `task_uploads` (`id`, `user_id`, `task_id`, `task_path`, `task_name`, `is_graded`, `created_at`, `updated_at`) VALUES
(7, 3, 3, '5/shutterstock_553686271-768x513.jpg', 'shutterstock_553686271-768x513.jpg', 0, '2022-03-05 19:34:31', '2022-03-05 19:34:31'),
(8, 6, 3, '6/shutterstock_553686271-768x513.jpg', 'shutterstock_553686271-768x513.jpg', 0, '2022-03-05 19:37:21', '2022-03-05 19:37:21'),
(9, 6, 6, '6/ricefield-768x576.jpg', 'ricefield-768x576.jpg', 0, '2022-03-05 19:38:12', '2022-03-05 19:38:12'),
(10, 6, 6, '6/shutterstock_553686271-768x513.jpg', 'shutterstock_553686271-768x513.jpg', 0, '2022-03-05 19:40:39', '2022-03-05 19:40:39'),
(11, 1, 3, '1/ricefield-768x576.jpg', 'ricefield-768x576.jpg', 0, '2022-03-12 15:21:04', '2022-03-12 15:21:04'),
(12, 1, 7, '1/ricefield-768x576.jpg', 'ricefield-768x576.jpg', 1, '2022-03-12 15:27:35', '2022-03-12 16:40:49'),
(13, 1, 7, '1/shutterstock_553686271-768x513.jpg', 'shutterstock_553686271-768x513.jpg', 1, '2022-03-12 15:27:37', '2022-03-12 16:40:49');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `place_enrolled` varchar(500) NOT NULL,
  `bdate` date DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `full_name`, `place_enrolled`, `bdate`, `username`, `email`, `password`, `created_at`, `updated_at`) VALUES
(1, 'Kyle Roy Guzman Robles', 'Sitio Balingkupang, Brgy. Biak na bato, San Miguel, Bulacan', '2000-04-15', 'royz415', 'kyleroy@yahoo.com', '123', '2022-02-27 13:53:39', '2022-03-01 16:55:29'),
(3, 'Juan Dela Cruz', 'Brgy. 198, Pasay City', NULL, NULL, NULL, NULL, '2022-03-01 09:28:19', '2022-03-01 09:28:19'),
(4, 'John Paul Villanueva', 'Brgy. 198, Pasay City', '2000-04-15', NULL, NULL, NULL, '2022-03-02 02:43:34', '2022-03-02 02:44:32');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `admin_table`
--
ALTER TABLE `admin_table`
  ADD PRIMARY KEY (`adminNo`);

--
-- Indexes for table `feedbacks`
--
ALTER TABLE `feedbacks`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `file_uploads`
--
ALTER TABLE `file_uploads`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `task_uploads`
--
ALTER TABLE `task_uploads`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `full_name` (`full_name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `addresses`
--
ALTER TABLE `addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `admin_table`
--
ALTER TABLE `admin_table`
  MODIFY `adminNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `feedbacks`
--
ALTER TABLE `feedbacks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `file_uploads`
--
ALTER TABLE `file_uploads`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `task_uploads`
--
ALTER TABLE `task_uploads`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `addresses`
--
ALTER TABLE `addresses`
  ADD CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
