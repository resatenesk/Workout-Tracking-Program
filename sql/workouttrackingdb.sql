-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 27 Tem 2025, 15:06:25
-- Sunucu sürümü: 10.4.32-MariaDB
-- PHP Sürümü: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `workouttrackingdb`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `antrenman_programlari_tur_gun`
--

CREATE TABLE `antrenman_programlari_tur_gun` (
  `id` int(11) NOT NULL,
  `antrenman_tipi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `antrenman_programlari_tur_gun`
--

INSERT INTO `antrenman_programlari_tur_gun` (`id`, `antrenman_tipi`) VALUES
(1, 'PPL'),
(2, 'UL'),
(3, 'FULLBODY'),
(4, 'BROSPLIT');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `daily_food_values`
--

CREATE TABLE `daily_food_values` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `calorie` float NOT NULL,
  `fat` float NOT NULL,
  `carb` float NOT NULL,
  `prot` float NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `daily_food_values`
--

INSERT INTO `daily_food_values` (`id`, `user_id`, `calorie`, `fat`, `carb`, `prot`, `date`) VALUES
(43, 1, 2232, 80, 214, 157, '2025-07-14'),
(45, 1, 2350, 81, 200, 160, '2025-07-15'),
(46, 1, 2397, 85, 246, 158, '2025-07-17'),
(47, 1, 2474, 79, 267, 171, '2025-07-18'),
(48, 1, 1982, 69, 206, 130, '2025-07-19');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `eklenen_antrenman_programlari`
--

CREATE TABLE `eklenen_antrenman_programlari` (
  `id` int(11) NOT NULL,
  `antrenman_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `username` text NOT NULL,
  `antrenman_tipi` text NOT NULL,
  `gun_sayisi` int(11) NOT NULL,
  `gun_no` int(11) NOT NULL,
  `hareket_adi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `eklenen_antrenman_programlari`
--

INSERT INTO `eklenen_antrenman_programlari` (`id`, `antrenman_id`, `user_id`, `username`, `antrenman_tipi`, `gun_sayisi`, `gun_no`, `hareket_adi`) VALUES
(293, 282, 1, 'resat', 'PPL', 5, 1, 'Bench Press'),
(294, 282, 1, 'resat', 'PPL', 5, 1, 'Incline Dumbell Press'),
(295, 282, 1, 'resat', 'PPL', 5, 1, 'High Cable Crossover'),
(296, 282, 1, 'resat', 'PPL', 5, 1, 'Dumbell Shoulder Press'),
(297, 282, 1, 'resat', 'PPL', 5, 1, 'Lateral Raise'),
(298, 282, 1, 'resat', 'PPL', 5, 1, 'Triceps Dips'),
(299, 282, 1, 'resat', 'PPL', 5, 1, 'Triceps Pushdown'),
(300, 282, 1, 'resat', 'PPL', 5, 2, 'Wide Grip Lat Pulldown'),
(301, 282, 1, 'resat', 'PPL', 5, 2, 'Machine Row'),
(302, 282, 1, 'resat', 'PPL', 5, 2, 'Close Grip Lat Pulldown'),
(303, 282, 1, 'resat', 'PPL', 5, 2, 'Rope Facepull'),
(304, 282, 1, 'resat', 'PPL', 5, 2, 'Alternate Incline Dumbell Curl'),
(305, 282, 1, 'resat', 'PPL', 5, 2, 'Hammer Curl'),
(306, 282, 1, 'resat', 'PPL', 5, 3, 'Dumbell Walking Lunge'),
(307, 282, 1, 'resat', 'PPL', 5, 4, 'Bench Press'),
(308, 282, 1, 'resat', 'PPL', 5, 4, 'Incline Dumbell Press'),
(309, 282, 1, 'resat', 'PPL', 5, 4, 'High Cable Crossover'),
(310, 282, 1, 'resat', 'PPL', 5, 4, 'Dumbell Shoulder Press'),
(311, 282, 1, 'resat', 'PPL', 5, 4, 'Lateral Raise'),
(312, 282, 1, 'resat', 'PPL', 5, 4, 'Triceps Dips'),
(313, 282, 1, 'resat', 'PPL', 5, 4, 'Dumbell Skull Crusher'),
(314, 282, 1, 'resat', 'PPL', 5, 5, 'Wide Grip Lat Pulldown'),
(315, 282, 1, 'resat', 'PPL', 5, 5, 'Machine Row'),
(316, 282, 1, 'resat', 'PPL', 5, 5, 'Close Grip Lat Pulldown'),
(317, 282, 1, 'resat', 'PPL', 5, 5, 'Rope Facepull'),
(318, 282, 1, 'resat', 'PPL', 5, 5, 'Alternate Incline Dumbell Curl'),
(319, 282, 1, 'resat', 'PPL', 5, 5, 'Hammer Curl'),
(395, 397, 1, 'resat', 'PPL', 3, 1, 'Bench Press'),
(396, 397, 1, 'resat', 'PPL', 3, 2, 'Wide Grip Lat Pulldown'),
(397, 397, 1, 'resat', 'PPL', 3, 3, 'Dumbell Walking Lunge'),
(398, 398, 1, 'resat', 'FULLBODY', 4, 1, 'Bench Press'),
(399, 398, 1, 'resat', 'FULLBODY', 4, 1, 'Incline Dumbell Press'),
(400, 398, 1, 'resat', 'FULLBODY', 4, 2, 'Bench Press'),
(401, 398, 1, 'resat', 'FULLBODY', 4, 3, 'Bench Press'),
(402, 398, 1, 'resat', 'FULLBODY', 4, 4, 'Bench Press'),
(403, 400, 1, 'resat', 'FULLBODY', 3, 1, 'Bench Press'),
(404, 400, 1, 'resat', 'FULLBODY', 3, 2, 'Bench Press'),
(405, 400, 1, 'resat', 'FULLBODY', 3, 3, 'Bench Press'),
(406, 415, 1, 'resat', 'BROSPLIT', 5, 1, 'Bench Press'),
(407, 415, 1, 'resat', 'BROSPLIT', 5, 2, 'Machine Row'),
(408, 415, 1, 'resat', 'BROSPLIT', 5, 3, 'Triceps Dips'),
(409, 415, 1, 'resat', 'BROSPLIT', 5, 4, 'Leg Extension'),
(410, 415, 1, 'resat', 'BROSPLIT', 5, 5, 'Cable Lateral Raise');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `eklenen_antrenman_sablonlari`
--

CREATE TABLE `eklenen_antrenman_sablonlari` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `username` text NOT NULL,
  `antrenman_tipi` text NOT NULL,
  `gun_sayisi` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `eklenen_antrenman_sablonlari`
--

INSERT INTO `eklenen_antrenman_sablonlari` (`id`, `user_id`, `username`, `antrenman_tipi`, `gun_sayisi`) VALUES
(282, 1, 'resat', 'PPL', 5),
(416, 1, 'resat', 'FULLBODY', 3);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hareketler`
--

CREATE TABLE `hareketler` (
  `id` int(11) NOT NULL,
  `antrenman_tipi` varchar(50) NOT NULL,
  `antrenman_tur_kategori` text NOT NULL,
  `hareket_adi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `hareketler`
--

INSERT INTO `hareketler` (`id`, `antrenman_tipi`, `antrenman_tur_kategori`, `hareket_adi`) VALUES
(1, 'PPL', 'Push', 'Bench Press'),
(2, 'PPL', 'Push', 'Incline Dumbell Press'),
(3, 'PPL', 'Push', 'High Cable Crossover'),
(4, 'PPL', 'Push', 'Dumbell Shoulder Press'),
(5, 'PPL', 'Push', 'Lateral Raise'),
(6, 'PPL', 'Push', 'Triceps Dips'),
(7, 'PPL', 'Push', 'Triceps Pushdown'),
(8, 'PPL', 'Push', 'Overhead Dumbell Triceps Extension'),
(9, 'PPL', 'Push', 'Dumbell Press'),
(10, 'PPL', 'Pull', 'Wide Grip Lat Pulldown'),
(11, 'PPL', 'Pull', 'Machine Row'),
(12, 'PPL', 'Pull', 'Close Grip Lat Pulldown'),
(13, 'PPL', 'Pull', 'Rope Facepull'),
(14, 'PPL', 'Pull', 'Alternate Incline Dumbell Curl'),
(15, 'PPL', 'Pull', 'Hammer Curl'),
(16, 'PPL', 'Pull', 'Barfiks'),
(17, 'PPL', 'Legs', 'Dumbell Walking Lunge'),
(18, 'PPL', 'Legs', 'Leg Extension'),
(19, 'PPL', 'Legs', 'Lying Leg Curl'),
(20, 'PPL', 'Legs', 'Leg Press'),
(21, 'PPL', 'Legs', 'Leg Press Calf Raise'),
(22, 'PPL', 'Legs', 'Hyper Extension'),
(23, 'PPL', 'Pull', 'Deadlift'),
(24, 'PPL', 'Pull', 'Barbell Row'),
(25, 'PPL', 'Legs', 'Squat'),
(26, 'PPL', 'Pull', 'Dumbell Curl'),
(28, 'UL', 'Upper', 'Bench Press'),
(29, 'UL', 'Upper', 'Incline Dumbell Press'),
(30, 'UL', 'Upper', 'High Cable Crossover'),
(31, 'UL', 'Upper', 'Dumbell Shoulder Press'),
(32, 'UL', 'Upper', 'Lateral Raise'),
(33, 'UL', 'Upper', 'Triceps Dips'),
(34, 'UL', 'Upper', 'Triceps Pushdown'),
(35, 'UL', 'Upper', 'Overhead Dumbell Triceps Extension'),
(36, 'UL', 'Upper', 'Dumbell Press'),
(37, 'UL', 'Upper', 'Wide Grip Lat Pulldown'),
(38, 'UL', 'Upper', 'Machine Row'),
(39, 'UL', 'Upper', 'Close Grip Lat Pulldown'),
(40, 'UL', 'Upper', 'Rope Facepull'),
(41, 'UL', 'Upper', 'Altrnate Incline Dumbell Curl'),
(42, 'UL', 'Upper', 'Hammer Curl'),
(43, 'UL', 'Upper', 'Barfiks'),
(44, 'UL', 'Lower', 'Dumbell Walking Lunge'),
(45, 'UL', 'Lower', 'Leg Extension'),
(46, 'UL', 'Lower', 'Lying Leg Curl'),
(47, 'UL', 'Lower', 'Leg Press'),
(48, 'UL', 'Lower', 'Leg Press Calf Raise'),
(49, 'UL', 'Lower', 'Hyper Extension'),
(50, 'UL', 'Lower', 'Deadlift'),
(51, 'UL', 'Lower', 'Barbell Row'),
(52, 'UL', 'Lower', 'Squat'),
(53, 'UL', 'Upper', 'Dumbell Biceps Curl'),
(54, 'FULLBODY', 'FULLBODY', 'Bench Press'),
(55, 'FULLBODY', 'FULLBODY', 'Incline Dumbell Press'),
(56, 'FULLBODY', 'FULLBODY', 'High Cable Crossover'),
(57, 'FULLBODY', 'FULLBODY', 'Dumbell Shoulder Press'),
(58, 'FULLBODY', 'FULLBODY', 'Lateral Raise'),
(59, 'FULLBODY', 'FULLBODY', 'Triceps Dips'),
(60, 'FULLBODY', 'FULLBODY', 'Triceps Pushdown'),
(61, 'FULLBODY', 'FULLBODY', 'Overhead Dumbell Triceps Extension'),
(62, 'FULLBODY', 'FULLBODY', 'Dumbell Press'),
(63, 'FULLBODY', 'FULLBODY', 'Wide Grip Lat Pulldown'),
(64, 'FULLBODY', 'FULLBODY', 'Machine Row'),
(65, 'FULLBODY', 'FULLBODY', 'Close Grip Lat Pulldown'),
(66, 'FULLBODY', 'FULLBODY', 'Rope Facepull'),
(67, 'FULLBODY', 'FULLBODY', 'Alternate Incline Dumbell Curl'),
(68, 'FULLBODY', 'FULLBODY', 'Hammer Curl'),
(69, 'FULLBODY', 'FULLBODY', 'Barfiks'),
(70, 'FULLBODY', 'FULLBODY', 'Dumbell Walking Lunge'),
(71, 'FULLBODY', 'FULLBODY', 'Leg Extension'),
(72, 'FULLBODY', 'FULLBODY', 'Lying Leg Curl'),
(73, 'FULLBODY', 'FULLBODY', 'Leg Press'),
(74, 'FULLBODY', 'FULLBODY', 'Leg Press Calf Raise'),
(75, 'FULLBODY', 'FULLBODY', 'Hyper Extension'),
(76, 'FULLBODY', 'FULLBODY', 'Deadlift'),
(77, 'FULLBODY', 'FULLBODY', 'Barbell Row'),
(78, 'FULLBODY', 'FULLBODY', 'Squat'),
(79, 'FULLBODY', 'FULLBODY', 'Dumbell Curl'),
(80, 'BROSPLIT', 'Gogus', 'Bench Press'),
(81, 'BROSPLIT', 'Gogus', 'Incline Dumbell Press'),
(82, 'BROSPLIT', 'Gogus', 'High Cable Crossover'),
(83, 'BROSPLIT', 'Gogus', 'Dumbell Press'),
(84, 'BROSPLIT', 'Gogus', 'Chest Press'),
(85, 'BROSPLIT', 'Gogus', 'Dumbell Fly'),
(86, 'BROSPLIT', 'Gogus', 'Peck Deck Fly'),
(87, 'BROSPLIT', 'Kol', 'Triceps Dips'),
(88, 'BROSPLIT', 'Kol', 'Triceps Pushdown'),
(89, 'BROSPLIT', 'Kol', 'Overhead Dumbell Triceps Extension'),
(90, 'BROSPLIT', 'Kol', 'Altrenate Incline Dumbell Curl'),
(91, 'BROSPLIT', 'Kol', 'Dumbell Biceps Curl'),
(92, 'BROSPLIT', 'Kol', 'Barbell Curl'),
(93, 'BROSPLIT', 'Kol', 'Preacher Curl'),
(94, 'BROSPLIT', 'Kol', 'Ez bar curl'),
(95, 'BROSPLIT', 'Sırt', 'Wide Grip Lat Pulldown'),
(96, 'BROSPLIT', 'Sırt', 'Machine Row'),
(97, 'BROSPLIT', 'Sırt', 'Chest Supported Row'),
(98, 'BROSPLIT', 'Sırt', 'T-bar Row'),
(99, 'BROSPLIT', 'Sırt', 'Seated Cable Row'),
(100, 'BROSPLIT', 'Sırt', 'Deadlift'),
(101, 'BROSPLIT', 'Sırt', 'Barfiks'),
(102, 'BROSPLIT', 'Sırt', 'Assisted Pull Up'),
(103, 'BROSPLIT', 'Sırt', 'Bent Over Row'),
(104, 'BROSPLIT', 'Sırt', 'Chin Up'),
(105, 'BROSPLIT', 'Sırt', 'Rope Facepull'),
(106, 'BROSPLIT', 'Omuz', 'Dumbell Shoulder Press'),
(107, 'BROSPLIT', 'Omuz', 'Dumbell Lateral Raise'),
(108, 'BROSPLIT', 'Omuz', 'Cable Lateral Raise'),
(109, 'BROSPLIT', 'Omuz', 'Smith Machine Overhead Press'),
(110, 'BROSPLIT', 'Omuz', 'Front Raise'),
(111, 'BROSPLIT', 'Omuz', 'Plate Raise'),
(112, 'BROSPLIT', 'Omuz', 'Rear Delt Fly'),
(113, 'BROSPLIT', 'Omuz', 'Rope Facepull'),
(114, 'BROSPLIT', 'Bacak', 'Squat'),
(115, 'BROSPLIT', 'Bacak', 'Leg Extension'),
(116, 'BROSPLIT', 'Bacak', 'Leg Curl'),
(117, 'BROSPLIT', 'Bacak', 'Deadlift'),
(118, 'BROSPLIT', 'Bacak', 'Hyper Extension'),
(119, 'BROSPLIT', 'Bacak', 'Leg Press'),
(120, 'BROSPLIT', 'Bacak', 'Leg Press Calf Raise'),
(121, 'BROSPLIT', 'Bacak', 'Dumbell Walking Lunge'),
(122, 'PPL', 'Push', 'Dumbell Skull Crusher'),
(123, 'UL', 'Upper', 'Dumbell Skull Crusher'),
(124, 'FULLBODY', 'FULLBODY', 'Dumbell Skull Crusher'),
(125, 'BROSPLIT', 'Kol', 'Dumbell Skull Crusher');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `kayitlar`
--

CREATE TABLE `kayitlar` (
  `id` int(11) NOT NULL,
  `username` text NOT NULL,
  `antrenman_id` int(11) NOT NULL,
  `gun_no` int(11) NOT NULL,
  `hareket_adi` text NOT NULL,
  `set_no` int(11) NOT NULL,
  `agirlik` double NOT NULL,
  `tekrar` int(11) NOT NULL,
  `tarih` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `kayitlar`
--

INSERT INTO `kayitlar` (`id`, `username`, `antrenman_id`, `gun_no`, `hareket_adi`, `set_no`, `agirlik`, `tekrar`, `tarih`) VALUES
(211, 'resat', 282, 4, 'Bench Press', 1, 70, 10, '2025-05-03'),
(212, 'resat', 282, 4, 'Bench Press', 2, 70, 10, '2025-05-03'),
(213, 'resat', 282, 4, 'Bench Press', 3, 70, 10, '2025-05-03'),
(214, 'resat', 282, 4, 'Incline Dumbell Press', 1, 30, 10, '2025-05-03'),
(215, 'resat', 282, 4, 'Incline Dumbell Press', 2, 30, 10, '2025-05-03'),
(216, 'resat', 282, 4, 'Incline Dumbell Press', 3, 30, 10, '2025-05-03'),
(217, 'resat', 282, 4, 'High Cable Crossover', 1, 25, 12, '2025-05-03'),
(218, 'resat', 282, 4, 'High Cable Crossover', 2, 25, 10, '2025-05-03'),
(219, 'resat', 282, 4, 'High Cable Crossover', 3, 25, 8, '2025-05-03'),
(220, 'resat', 282, 4, 'Dumbell Shoulder Press', 1, 0, 0, '2025-05-03'),
(221, 'resat', 282, 4, 'Dumbell Shoulder Press', 2, 0, 0, '2025-05-03'),
(222, 'resat', 282, 4, 'Dumbell Shoulder Press', 3, 0, 0, '2025-05-03'),
(223, 'resat', 282, 4, 'Lateral Raise', 1, 7.5, 16, '2025-05-03'),
(224, 'resat', 282, 4, 'Lateral Raise', 2, 7.5, 14, '2025-05-03'),
(225, 'resat', 282, 4, 'Lateral Raise', 3, 7.5, 14, '2025-05-03'),
(226, 'resat', 282, 4, 'Triceps Dips', 1, 0, 6, '2025-05-03'),
(227, 'resat', 282, 4, 'Triceps Dips', 2, 0, 5, '2025-05-03'),
(228, 'resat', 282, 4, 'Dumbell Skull Crusher', 1, 10, 12, '2025-05-03'),
(229, 'resat', 282, 4, 'Dumbell Skull Crusher', 2, 10, 10, '2025-05-03'),
(230, 'resat', 282, 4, 'Dumbell Skull Crusher', 3, 10, 8, '2025-05-03'),
(231, 'resat', 282, 4, 'Bench Press', 1, 70, 10, '2025-05-23'),
(232, 'resat', 282, 4, 'Bench Press', 2, 80, 8, '2025-05-23'),
(233, 'resat', 282, 4, 'Bench Press', 3, 80, 9, '2025-05-23'),
(234, 'resat', 282, 4, 'Incline Dumbell Press', 1, 30, 12, '2025-05-23'),
(235, 'resat', 282, 4, 'Incline Dumbell Press', 2, 30, 10, '2025-05-23'),
(236, 'resat', 282, 4, 'Incline Dumbell Press', 3, 30, 10, '2025-05-23'),
(237, 'resat', 282, 4, 'High Cable Crossover', 1, 25, 11, '2025-05-23'),
(238, 'resat', 282, 4, 'High Cable Crossover', 2, 25, 8, '2025-05-23'),
(239, 'resat', 282, 4, 'High Cable Crossover', 3, 20, 10, '2025-05-23'),
(240, 'resat', 282, 4, 'Dumbell Shoulder Press', 1, 0, 0, '2025-05-23'),
(241, 'resat', 282, 4, 'Dumbell Shoulder Press', 2, 0, 0, '2025-05-23'),
(242, 'resat', 282, 4, 'Dumbell Shoulder Press', 3, 0, 0, '2025-05-23'),
(243, 'resat', 282, 4, 'Lateral Raise', 1, 10, 15, '2025-05-23'),
(244, 'resat', 282, 4, 'Lateral Raise', 2, 10, 14, '2025-05-23'),
(245, 'resat', 282, 4, 'Lateral Raise', 3, 10, 10, '2025-05-23'),
(246, 'resat', 282, 4, 'Triceps Dips', 1, 0, 0, '2025-05-23'),
(247, 'resat', 282, 4, 'Dumbell Skull Crusher', 1, 10, 12, '2025-05-23'),
(248, 'resat', 282, 4, 'Dumbell Skull Crusher', 2, 10, 12, '2025-05-23'),
(249, 'resat', 282, 4, 'Dumbell Skull Crusher', 3, 10, 10, '2025-05-23'),
(250, 'resat', 282, 4, 'Bench Press', 1, 70, 12, '2025-05-28'),
(251, 'resat', 282, 4, 'Bench Press', 2, 80, 6, '2025-05-28'),
(252, 'resat', 282, 4, 'Bench Press', 3, 70, 10, '2025-05-28'),
(253, 'resat', 282, 4, 'High Cable Crossover', 1, 25, 12, '2025-05-28'),
(254, 'resat', 282, 4, 'High Cable Crossover', 2, 25, 11, '2025-05-28'),
(255, 'resat', 282, 4, 'High Cable Crossover', 3, 25, 9, '2025-05-28'),
(256, 'resat', 282, 4, 'Dumbell Shoulder Press', 1, 0, 0, '2025-05-28'),
(257, 'resat', 282, 4, 'Lateral Raise', 1, 12.5, 14, '2025-05-28'),
(258, 'resat', 282, 4, 'Lateral Raise', 2, 12.5, 12, '2025-05-28'),
(259, 'resat', 282, 4, 'Lateral Raise', 3, 12.5, 14, '2025-05-28'),
(260, 'resat', 282, 4, 'Triceps Dips', 1, 0, 8, '2025-05-28'),
(261, 'resat', 282, 4, 'Triceps Dips', 2, 0, 7, '2025-05-28'),
(262, 'resat', 282, 4, 'Dumbell Skull Crusher', 1, 12.5, 12, '2025-05-28'),
(263, 'resat', 282, 4, 'Dumbell Skull Crusher', 2, 12.5, 9, '2025-05-28'),
(264, 'resat', 282, 4, 'Dumbell Skull Crusher', 3, 12.5, 8, '2025-05-28'),
(265, 'resat', 282, 1, 'Bench Press', 1, 80, 8, '2025-06-05'),
(266, 'resat', 282, 1, 'Bench Press', 2, 75, 8, '2025-06-05'),
(267, 'resat', 282, 1, 'Bench Press', 3, 70, 9, '2025-06-05'),
(268, 'resat', 282, 1, 'Incline Dumbell Press', 1, 35, 8, '2025-06-05'),
(269, 'resat', 282, 1, 'Incline Dumbell Press', 2, 30, 9, '2025-06-05'),
(270, 'resat', 282, 1, 'Incline Dumbell Press', 3, 30, 8, '2025-06-05'),
(271, 'resat', 282, 1, 'High Cable Crossover', 1, 25, 12, '2025-06-05'),
(272, 'resat', 282, 1, 'High Cable Crossover', 2, 25, 11, '2025-06-05'),
(273, 'resat', 282, 1, 'High Cable Crossover', 3, 25, 11, '2025-06-05'),
(274, 'resat', 282, 1, 'Dumbell Shoulder Press', 1, 20, 12, '2025-06-05'),
(275, 'resat', 282, 1, 'Dumbell Shoulder Press', 2, 20, 12, '2025-06-05'),
(276, 'resat', 282, 1, 'Dumbell Shoulder Press', 3, 20, 10, '2025-06-05'),
(277, 'resat', 282, 1, 'Lateral Raise', 1, 12.5, 12, '2025-06-05'),
(278, 'resat', 282, 1, 'Lateral Raise', 2, 10, 14, '2025-06-05'),
(279, 'resat', 282, 1, 'Lateral Raise', 3, 10, 12, '2025-06-05'),
(280, 'resat', 282, 1, 'Triceps Dips', 1, 0, 11, '2025-06-05'),
(281, 'resat', 282, 1, 'Triceps Dips', 2, 0, 8, '2025-06-05'),
(282, 'resat', 282, 1, 'Triceps Pushdown', 1, 60, 10, '2025-06-05'),
(283, 'resat', 282, 1, 'Triceps Pushdown', 2, 60, 7, '2025-06-05'),
(284, 'resat', 282, 1, 'Triceps Pushdown', 3, 55, 8, '2025-06-05'),
(285, 'resat', 282, 4, 'Bench Press', 1, 80, 7, '2025-06-08'),
(286, 'resat', 282, 4, 'Bench Press', 2, 75, 9, '2025-06-08'),
(287, 'resat', 282, 4, 'Bench Press', 3, 75, 8, '2025-06-08'),
(288, 'resat', 282, 4, 'Incline Dumbell Press', 1, 30, 12, '2025-06-08'),
(289, 'resat', 282, 4, 'Incline Dumbell Press', 2, 30, 11, '2025-06-08'),
(290, 'resat', 282, 4, 'Incline Dumbell Press', 3, 30, 10, '2025-06-08'),
(291, 'resat', 282, 4, 'High Cable Crossover', 1, 25, 12, '2025-06-08'),
(292, 'resat', 282, 4, 'High Cable Crossover', 2, 25, 12, '2025-06-08'),
(293, 'resat', 282, 4, 'High Cable Crossover', 3, 25, 12, '2025-06-08'),
(294, 'resat', 282, 4, 'Dumbell Shoulder Press', 1, 25, 10, '2025-06-08'),
(295, 'resat', 282, 4, 'Dumbell Shoulder Press', 2, 25, 8, '2025-06-08'),
(296, 'resat', 282, 4, 'Dumbell Shoulder Press', 3, 25, 8, '2025-06-08'),
(297, 'resat', 282, 4, 'Lateral Raise', 1, 10, 14, '2025-06-08'),
(298, 'resat', 282, 4, 'Lateral Raise', 2, 10, 14, '2025-06-08'),
(299, 'resat', 282, 4, 'Lateral Raise', 3, 10, 14, '2025-06-08'),
(300, 'resat', 282, 4, 'Triceps Dips', 1, 0, 12, '2025-06-08'),
(301, 'resat', 282, 4, 'Triceps Dips', 2, 0, 9, '2025-06-08'),
(302, 'resat', 282, 4, 'Dumbell Skull Crusher', 1, 12.5, 12, '2025-06-08'),
(303, 'resat', 282, 4, 'Dumbell Skull Crusher', 2, 12.5, 10, '2025-06-08'),
(304, 'resat', 282, 4, 'Dumbell Skull Crusher', 3, 12.5, 8, '2025-06-08'),
(305, 'resat', 282, 1, 'Bench Press', 1, 80, 9, '2025-06-11'),
(306, 'resat', 282, 1, 'Bench Press', 2, 80, 9, '2025-06-11'),
(307, 'resat', 282, 1, 'Bench Press', 3, 80, 8, '2025-06-11'),
(308, 'resat', 282, 1, 'Incline Dumbell Press', 1, 30, 11, '2025-06-11'),
(309, 'resat', 282, 1, 'Incline Dumbell Press', 2, 30, 10, '2025-06-11'),
(310, 'resat', 282, 1, 'Incline Dumbell Press', 3, 30, 10, '2025-06-11'),
(311, 'resat', 282, 1, 'High Cable Crossover', 1, 25, 12, '2025-06-11'),
(312, 'resat', 282, 1, 'High Cable Crossover', 2, 30, 10, '2025-06-11'),
(313, 'resat', 282, 1, 'High Cable Crossover', 3, 30, 9, '2025-06-11'),
(314, 'resat', 282, 1, 'Dumbell Shoulder Press', 1, 25, 10, '2025-06-11'),
(315, 'resat', 282, 1, 'Dumbell Shoulder Press', 2, 25, 8, '2025-06-11'),
(316, 'resat', 282, 1, 'Dumbell Shoulder Press', 3, 22.5, 7, '2025-06-11'),
(317, 'resat', 282, 1, 'Lateral Raise', 1, 10, 12, '2025-06-11'),
(318, 'resat', 282, 1, 'Lateral Raise', 2, 10, 13, '2025-06-11'),
(319, 'resat', 282, 1, 'Lateral Raise', 3, 10, 12, '2025-06-11'),
(320, 'resat', 282, 1, 'Triceps Dips', 1, 0, 12, '2025-06-11'),
(321, 'resat', 282, 1, 'Triceps Dips', 2, 0, 9, '2025-06-11'),
(322, 'resat', 282, 1, 'Triceps Pushdown', 1, 60, 12, '2025-06-11'),
(323, 'resat', 282, 1, 'Triceps Pushdown', 2, 60, 10, '2025-06-11'),
(324, 'resat', 282, 1, 'Triceps Pushdown', 3, 60, 9, '2025-06-11'),
(325, 'resat', 282, 1, 'Bench Press', 1, 80, 10, '2025-06-15'),
(326, 'resat', 282, 1, 'Bench Press', 2, 80, 10, '2025-06-15'),
(327, 'resat', 282, 1, 'Bench Press', 3, 80, 9, '2025-06-15'),
(328, 'resat', 282, 1, 'Incline Dumbell Press', 1, 30, 11, '2025-06-15'),
(329, 'resat', 282, 1, 'Incline Dumbell Press', 2, 30, 10, '2025-06-15'),
(330, 'resat', 282, 1, 'Incline Dumbell Press', 3, 30, 10, '2025-06-15'),
(331, 'resat', 282, 1, 'High Cable Crossover', 1, 25, 12, '2025-06-15'),
(332, 'resat', 282, 1, 'High Cable Crossover', 2, 25, 12, '2025-06-15'),
(333, 'resat', 282, 1, 'High Cable Crossover', 3, 25, 12, '2025-06-15'),
(334, 'resat', 282, 1, 'Dumbell Shoulder Press', 1, 25, 10, '2025-06-15'),
(335, 'resat', 282, 1, 'Dumbell Shoulder Press', 2, 25, 9, '2025-06-15'),
(336, 'resat', 282, 1, 'Dumbell Shoulder Press', 3, 25, 8, '2025-06-15'),
(337, 'resat', 282, 1, 'Lateral Raise', 1, 7.5, 16, '2025-06-15'),
(338, 'resat', 282, 1, 'Lateral Raise', 2, 7.5, 16, '2025-06-15'),
(339, 'resat', 282, 1, 'Lateral Raise', 3, 7.5, 16, '2025-06-15'),
(340, 'resat', 282, 1, 'Triceps Dips', 1, 0, 12, '2025-06-15'),
(341, 'resat', 282, 1, 'Triceps Dips', 2, 0, 9, '2025-06-15'),
(342, 'resat', 282, 1, 'Triceps Pushdown', 1, 0, 0, '2025-06-15'),
(343, 'resat', 282, 1, 'Bench Press', 1, 80, 10, '2025-06-18'),
(344, 'resat', 282, 1, 'Bench Press', 2, 80, 10, '2025-06-18'),
(345, 'resat', 282, 1, 'Bench Press', 3, 80, 9, '2025-06-18'),
(346, 'resat', 282, 1, 'Incline Dumbell Press', 1, 35, 9, '2025-06-18'),
(347, 'resat', 282, 1, 'Incline Dumbell Press', 2, 30, 10, '2025-06-18'),
(348, 'resat', 282, 1, 'Incline Dumbell Press', 3, 30, 9, '2025-06-18'),
(349, 'resat', 282, 1, 'High Cable Crossover', 1, 25, 12, '2025-06-18'),
(350, 'resat', 282, 1, 'High Cable Crossover', 2, 25, 12, '2025-06-18'),
(351, 'resat', 282, 1, 'High Cable Crossover', 3, 25, 10, '2025-06-18'),
(352, 'resat', 282, 1, 'Dumbell Shoulder Press', 1, 25, 10, '2025-06-18'),
(353, 'resat', 282, 1, 'Dumbell Shoulder Press', 2, 25, 10, '2025-06-18'),
(354, 'resat', 282, 1, 'Dumbell Shoulder Press', 3, 25, 8, '2025-06-18'),
(355, 'resat', 282, 1, 'Lateral Raise', 1, 7.5, 18, '2025-06-18'),
(356, 'resat', 282, 1, 'Lateral Raise', 2, 7.5, 14, '2025-06-18'),
(357, 'resat', 282, 1, 'Lateral Raise', 3, 7.5, 14, '2025-06-18'),
(358, 'resat', 282, 1, 'Triceps Dips', 1, 0, 12, '2025-06-18'),
(359, 'resat', 282, 1, 'Triceps Dips', 2, 0, 10, '2025-06-18'),
(360, 'resat', 282, 1, 'Triceps Pushdown', 1, 0, 0, '2025-06-18'),
(361, 'resat', 282, 1, 'Bench Press', 1, 80, 10, '2025-06-22'),
(362, 'resat', 282, 1, 'Bench Press', 2, 90, 8, '2025-06-22'),
(363, 'resat', 282, 1, 'Bench Press', 3, 80, 10, '2025-06-22'),
(364, 'resat', 282, 1, 'Incline Dumbell Press', 1, 27.5, 12, '2025-06-22'),
(365, 'resat', 282, 1, 'Incline Dumbell Press', 2, 27.5, 11, '2025-06-22'),
(366, 'resat', 282, 1, 'Incline Dumbell Press', 3, 27.5, 10, '2025-06-22'),
(367, 'resat', 282, 1, 'High Cable Crossover', 1, 25, 12, '2025-06-22'),
(368, 'resat', 282, 1, 'High Cable Crossover', 2, 25, 12, '2025-06-22'),
(369, 'resat', 282, 1, 'High Cable Crossover', 3, 25, 12, '2025-06-22'),
(370, 'resat', 282, 1, 'Dumbell Shoulder Press', 1, 25, 10, '2025-06-22'),
(371, 'resat', 282, 1, 'Dumbell Shoulder Press', 2, 25, 9, '2025-06-22'),
(372, 'resat', 282, 1, 'Dumbell Shoulder Press', 3, 25, 9, '2025-06-22'),
(373, 'resat', 282, 1, 'Lateral Raise', 1, 7.5, 16, '2025-06-22'),
(374, 'resat', 282, 1, 'Lateral Raise', 2, 7.5, 15, '2025-06-22'),
(375, 'resat', 282, 1, 'Lateral Raise', 3, 7.5, 14, '2025-06-22'),
(376, 'resat', 282, 1, 'Triceps Dips', 1, 0, 10, '2025-06-22'),
(377, 'resat', 282, 1, 'Triceps Dips', 2, 0, 10, '2025-06-22'),
(378, 'resat', 282, 1, 'Triceps Pushdown', 1, 0, 0, '2025-06-22'),
(379, 'resat', 282, 4, 'Bench Press', 1, 80, 10, '2025-06-25'),
(380, 'resat', 282, 4, 'Bench Press', 2, 90, 8, '2025-06-25'),
(381, 'resat', 282, 4, 'Bench Press', 3, 80, 10, '2025-06-25'),
(382, 'resat', 282, 4, 'Incline Dumbell Press', 1, 30, 12, '2025-06-25'),
(383, 'resat', 282, 4, 'Incline Dumbell Press', 2, 30, 11, '2025-06-25'),
(384, 'resat', 282, 4, 'Incline Dumbell Press', 3, 30, 10, '2025-06-25'),
(385, 'resat', 282, 4, 'High Cable Crossover', 1, 25, 12, '2025-06-25'),
(386, 'resat', 282, 4, 'High Cable Crossover', 2, 25, 12, '2025-06-25'),
(387, 'resat', 282, 4, 'High Cable Crossover', 3, 25, 12, '2025-06-25'),
(388, 'resat', 282, 4, 'Dumbell Shoulder Press', 1, 25, 10, '2025-06-25'),
(389, 'resat', 282, 4, 'Dumbell Shoulder Press', 2, 25, 10, '2025-06-25'),
(390, 'resat', 282, 4, 'Dumbell Shoulder Press', 3, 25, 9, '2025-06-25'),
(391, 'resat', 282, 4, 'Lateral Raise', 1, 7.5, 16, '2025-06-25'),
(392, 'resat', 282, 4, 'Lateral Raise', 2, 7.5, 16, '2025-06-25'),
(393, 'resat', 282, 4, 'Lateral Raise', 3, 7.5, 16, '2025-06-25'),
(394, 'resat', 282, 4, 'Triceps Dips', 1, 0, 12, '2025-06-25'),
(395, 'resat', 282, 4, 'Triceps Dips', 2, 0, 10, '2025-06-25'),
(396, 'resat', 282, 4, 'Dumbell Skull Crusher', 1, 12.5, 12, '2025-06-25'),
(397, 'resat', 282, 4, 'Dumbell Skull Crusher', 2, 12.5, 8, '2025-06-25'),
(398, 'resat', 282, 4, 'Dumbell Skull Crusher', 3, 10, 9, '2025-06-25'),
(399, 'resat', 282, 1, 'Bench Press', 1, 80, 10, '2025-07-29'),
(400, 'resat', 282, 1, 'Bench Press', 2, 90, 6, '2025-07-29'),
(401, 'resat', 282, 1, 'Bench Press', 3, 80, 9, '2025-07-29'),
(402, 'resat', 282, 1, 'Incline Dumbell Press', 1, 30, 12, '2025-07-29'),
(403, 'resat', 282, 1, 'Incline Dumbell Press', 2, 30, 11, '2025-07-29'),
(404, 'resat', 282, 1, 'Incline Dumbell Press', 3, 30, 10, '2025-07-29'),
(405, 'resat', 282, 1, 'High Cable Crossover', 1, 25, 12, '2025-07-29'),
(406, 'resat', 282, 1, 'High Cable Crossover', 2, 25, 12, '2025-07-29'),
(407, 'resat', 282, 1, 'High Cable Crossover', 3, 25, 12, '2025-07-29'),
(408, 'resat', 282, 1, 'Dumbell Shoulder Press', 1, 25, 10, '2025-07-29'),
(409, 'resat', 282, 1, 'Dumbell Shoulder Press', 2, 25, 9, '2025-07-29'),
(410, 'resat', 282, 1, 'Dumbell Shoulder Press', 3, 25, 8, '2025-07-29'),
(411, 'resat', 282, 1, 'Lateral Raise', 1, 7.5, 16, '2025-07-29'),
(412, 'resat', 282, 1, 'Lateral Raise', 2, 7.5, 12, '2025-07-29'),
(413, 'resat', 282, 1, 'Lateral Raise', 3, 7.5, 12, '2025-07-29'),
(414, 'resat', 282, 1, 'Triceps Dips', 1, 0, 12, '2025-07-29'),
(415, 'resat', 282, 1, 'Triceps Dips', 2, 0, 10, '2025-07-29'),
(416, 'resat', 282, 1, 'Triceps Pushdown', 1, 60, 12, '2025-07-29'),
(417, 'resat', 282, 1, 'Triceps Pushdown', 2, 60, 10, '2025-07-29'),
(418, 'resat', 282, 1, 'Triceps Pushdown', 3, 60, 8, '2025-07-29');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `saved_meals`
--

CREATE TABLE `saved_meals` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `meal_name` text NOT NULL,
  `total_cal` float NOT NULL,
  `total_fat` float NOT NULL,
  `total_carb` float NOT NULL,
  `total_prot` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `saved_meals`
--

INSERT INTO `saved_meals` (`id`, `user_id`, `meal_name`, `total_cal`, `total_fat`, `total_carb`, `total_prot`) VALUES
(29, 1, 'dwdwd', 175, 5.37, 0.34, 28.54);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `saved_special_foods`
--

CREATE TABLE `saved_special_foods` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `food_name` text NOT NULL,
  `calorie` float NOT NULL,
  `fat` float NOT NULL,
  `carb` float NOT NULL,
  `prot` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `saved_special_foods`
--

INSERT INTO `saved_special_foods` (`id`, `user_id`, `food_name`, `calorie`, `fat`, `carb`, `prot`) VALUES
(7, 1, 'Tavuk Göğsü(100 gr)', 110, 1, 0, 23),
(8, 1, 'Yumurta(Orta)', 65, 4.37, 0.34, 5.54),
(10, 1, 'Zeytinyağı(5gr)', 44, 5, 0, 0),
(13, 1, 'Yulaf(100 gr)', 360, 7.5, 53, 14),
(14, 1, 'Pirinç Unu(Protein Ocean)(50 gr)', 174, 0.4, 37.9, 4.6);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`) VALUES
(1, 'resat', 'resat123'),
(2, 'Akif', 'Akif!123'),
(3, 'Sevgi', 'Sevgi123!'),
(7, 'Yusuf', 'Yusuf123!'),
(8, 'Tarık', '!Tarik123'),
(9, 'Ayse', '!Ayse123'),
(10, 'Yusra', '!Yusra123'),
(11, 'Pelin', '!Pelin123'),
(12, 'Kamil', 'Kamil123!');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `antrenman_programlari_tur_gun`
--
ALTER TABLE `antrenman_programlari_tur_gun`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `daily_food_values`
--
ALTER TABLE `daily_food_values`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `eklenen_antrenman_programlari`
--
ALTER TABLE `eklenen_antrenman_programlari`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `eklenen_antrenman_sablonlari`
--
ALTER TABLE `eklenen_antrenman_sablonlari`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `hareketler`
--
ALTER TABLE `hareketler`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `kayitlar`
--
ALTER TABLE `kayitlar`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `saved_meals`
--
ALTER TABLE `saved_meals`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `saved_special_foods`
--
ALTER TABLE `saved_special_foods`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `antrenman_programlari_tur_gun`
--
ALTER TABLE `antrenman_programlari_tur_gun`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Tablo için AUTO_INCREMENT değeri `daily_food_values`
--
ALTER TABLE `daily_food_values`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- Tablo için AUTO_INCREMENT değeri `eklenen_antrenman_programlari`
--
ALTER TABLE `eklenen_antrenman_programlari`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=411;

--
-- Tablo için AUTO_INCREMENT değeri `eklenen_antrenman_sablonlari`
--
ALTER TABLE `eklenen_antrenman_sablonlari`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=417;

--
-- Tablo için AUTO_INCREMENT değeri `hareketler`
--
ALTER TABLE `hareketler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=130;

--
-- Tablo için AUTO_INCREMENT değeri `kayitlar`
--
ALTER TABLE `kayitlar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=451;

--
-- Tablo için AUTO_INCREMENT değeri `saved_meals`
--
ALTER TABLE `saved_meals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Tablo için AUTO_INCREMENT değeri `saved_special_foods`
--
ALTER TABLE `saved_special_foods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Tablo için AUTO_INCREMENT değeri `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
