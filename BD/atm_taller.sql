-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 30-04-2014 a las 05:20:56
-- Versión del servidor: 5.1.53
-- Versión de PHP: 5.3.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `atm_taller`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bodega`
--

CREATE TABLE IF NOT EXISTS `bodega` (
  `idBodega` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `direccion` varchar(500) COLLATE utf8_spanish_ci DEFAULT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'0',
  `principal` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`idBodega`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=2 ;

--
-- Volcar la base de datos para la tabla `bodega`
--

INSERT INTO `bodega` (`idBodega`, `descripcion`, `direccion`, `estado`, `principal`) VALUES
(1, 'Bodega Principal', 'Las Heras', b'1', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombreCliente` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `telefono` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `rutCliente` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,
  `celular` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `estado` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=14 ;

--
-- Volcar la base de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idCliente`, `nombreCliente`, `email`, `telefono`, `rutCliente`, `celular`, `estado`) VALUES
(12, 'test', 'test', '234', '1-9', '234', 1),
(13, 'teste', 'yrdt', '7657', '12312312-3', '7657', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_orden`
--

CREATE TABLE IF NOT EXISTS `estado_orden` (
  `idEstadoOrden` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `finalizacion` bit(1) NOT NULL,
  `estado` bit(1) NOT NULL,
  PRIMARY KEY (`idEstadoOrden`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=9 ;

--
-- Volcar la base de datos para la tabla `estado_orden`
--

INSERT INTO `estado_orden` (`idEstadoOrden`, `descripcion`, `finalizacion`, `estado`) VALUES
(1, 'Ingresada', b'0', b'1'),
(2, 'En Trabajo', b'0', b'1'),
(3, 'En Servicio Tercero', b'0', b'1'),
(4, 'En DyP', b'0', b'1'),
(5, 'Pendiente', b'0', b'1'),
(6, 'Terminada', b'1', b'1'),
(7, 'Cancelada', b'1', b'1'),
(8, 'Eliminada', b'1', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_trabajo`
--

CREATE TABLE IF NOT EXISTS `estado_trabajo` (
  `idEstadoTrabajo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`idEstadoTrabajo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=5 ;

--
-- Volcar la base de datos para la tabla `estado_trabajo`
--

INSERT INTO `estado_trabajo` (`idEstadoTrabajo`, `descripcion`, `estado`) VALUES
(1, 'Ingresado', b'1'),
(2, 'En Proceso', b'1'),
(3, 'Finalizado', b'1'),
(4, 'Cancelado', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `forma_pago`
--

CREATE TABLE IF NOT EXISTS `forma_pago` (
  `idFormaPago` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`idFormaPago`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=6 ;

--
-- Volcar la base de datos para la tabla `forma_pago`
--

INSERT INTO `forma_pago` (`idFormaPago`, `descripcion`, `estado`) VALUES
(1, 'Efectivo', b'1'),
(2, 'Tarjeta Debito', b'1'),
(3, 'Tarjeta Credito', b'1'),
(5, 'Otro', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mantencion_programada`
--

CREATE TABLE IF NOT EXISTS `mantencion_programada` (
  `idMantencionProgramada` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `descripcion` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`idMantencionProgramada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=1 ;

--
-- Volcar la base de datos para la tabla `mantencion_programada`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mantencion_programada_trabajo`
--

CREATE TABLE IF NOT EXISTS `mantencion_programada_trabajo` (
  `idMantencionProgramada` int(11) NOT NULL,
  `idTrabajo` int(11) NOT NULL,
  PRIMARY KEY (`idTrabajo`,`idMantencionProgramada`),
  KEY `fk_mantencion_programada_trabajo_mantencion_programada1` (`idMantencionProgramada`),
  KEY `fk_mantencion_programada_trabajo_trabajo1` (`idTrabajo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcar la base de datos para la tabla `mantencion_programada_trabajo`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE IF NOT EXISTS `marca` (
  `idMarca` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`idMarca`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=7 ;

--
-- Volcar la base de datos para la tabla `marca`
--

INSERT INTO `marca` (`idMarca`, `descripcion`, `estado`) VALUES
(5, 'Movo', b'1'),
(6, 'Valeo', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca_vehiculo`
--

CREATE TABLE IF NOT EXISTS `marca_vehiculo` (
  `idMarcaVehiculo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`idMarcaVehiculo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=646 ;

--
-- Volcar la base de datos para la tabla `marca_vehiculo`
--

INSERT INTO `marca_vehiculo` (`idMarcaVehiculo`, `descripcion`, `estado`) VALUES
(1, 'Abat', b'1'),
(2, 'Acadian', b'1'),
(3, 'Acura', b'1'),
(4, 'Adventure', b'1'),
(5, 'Africa', b'1'),
(6, 'Agrale', b'1'),
(7, 'Agromaq', b'1'),
(8, 'Agusta', b'1'),
(9, 'AJS', b'1'),
(10, 'Alba Motors', b'1'),
(11, 'Alfa Romeo', b'1'),
(12, 'Allmand', b'1'),
(13, 'Altec', b'1'),
(14, 'Amazone', b'1'),
(15, 'AMC', b'1'),
(16, 'American Motors', b'1'),
(17, 'Ammann', b'1'),
(18, 'Aprilia', b'1'),
(19, 'APT', b'1'),
(20, 'Aqualine', b'1'),
(21, 'Aro', b'1'),
(22, 'Articat', b'1'),
(23, 'Artigiana', b'1'),
(24, 'Asia Motors', b'1'),
(25, 'Aston Martin', b'1'),
(26, 'Atlas', b'1'),
(27, 'ATV', b'1'),
(28, 'Audi', b'1'),
(29, 'Aupa', b'1'),
(30, 'Ausa', b'1'),
(31, 'Austin', b'1'),
(32, 'Automoto', b'1'),
(33, 'Autorrad', b'1'),
(34, 'Aveling-barford', b'1'),
(35, 'Axion', b'1'),
(36, 'Baja', b'1'),
(37, 'Bajaj', b'1'),
(38, 'Baldan', b'1'),
(39, 'Baolat', b'1'),
(40, 'Baotion', b'1'),
(41, 'Barford', b'1'),
(42, 'Barko', b'1'),
(43, 'Bartell', b'1'),
(44, 'Bashan', b'1'),
(45, 'BAW', b'1'),
(46, 'Bayliner', b'1'),
(47, 'Belarus', b'1'),
(48, 'Belav - Tomav', b'1'),
(49, 'Belda', b'1'),
(50, 'Benelli', b'1'),
(51, 'Benford', b'1'),
(52, 'Benyi', b'1'),
(53, 'Benzhou', b'1'),
(54, 'Bertone', b'1'),
(55, 'beta', b'1'),
(56, 'Bianchi', b'1'),
(57, 'Bigfoot', b'1'),
(58, 'Bimota', b'1'),
(59, 'Black Diamond', b'1'),
(60, 'BMW', b'1'),
(61, 'Bobcat', b'1'),
(62, 'Bohemia', b'1'),
(63, 'Bohemia', b'1'),
(64, 'Bomag', b'1'),
(65, 'Bombard', b'1'),
(66, 'Bombardier', b'1'),
(67, 'Bonluck', b'1'),
(68, 'Borgward', b'1'),
(69, 'Bortex', b'1'),
(70, 'Breuer', b'1'),
(71, 'Breviglieri', b'1'),
(72, 'Brias', b'1'),
(73, 'Brilliance', b'1'),
(74, 'Brilliant', b'1'),
(75, 'Brillon', b'1'),
(76, 'Bros', b'1'),
(77, 'BRP CAN-AM', b'1'),
(78, 'BSA', b'1'),
(79, 'Buell', b'1'),
(80, 'Bugatti', b'1'),
(81, 'Buggy', b'1'),
(82, 'Buick', b'1'),
(83, 'Busscar', b'1'),
(84, 'BYD', b'1'),
(85, 'Caburga', b'1'),
(86, 'Cadillac', b'1'),
(87, 'Cagina', b'1'),
(88, 'Cagiva', b'1'),
(89, 'Caky', b'1'),
(90, 'Can-Am', b'1'),
(91, 'Canoa', b'1'),
(92, 'Carraro', b'1'),
(93, 'Case', b'1'),
(94, 'Catalina', b'1'),
(95, 'Caterpillar', b'1'),
(96, 'CF Moto', b'1'),
(97, 'CGB Trailer', b'1'),
(98, 'Challenger', b'1'),
(99, 'Champion', b'1'),
(100, 'Chandler', b'1'),
(101, 'Changan', b'1'),
(102, 'Changhe', b'1'),
(103, 'Changjeng', b'1'),
(104, 'Changlin', b'1'),
(105, 'ChengGong', b'1'),
(106, 'Cherokee', b'1'),
(107, 'Chery', b'1'),
(108, 'Chevrolet', b'1'),
(109, 'Chituma', b'1'),
(110, 'Choice', b'1'),
(111, 'Chrysler', b'1'),
(112, 'Cima', b'1'),
(113, 'Citroen', b'1'),
(114, 'Coachmen', b'1'),
(115, 'Cobalt', b'1'),
(116, 'Cobra', b'1'),
(117, 'Coleman', b'1'),
(118, 'Coloso', b'1'),
(119, 'Comil', b'1'),
(120, 'Compair', b'1'),
(121, 'Comuta-Car', b'1'),
(122, 'CRG', b'1'),
(123, 'Cross', b'1'),
(124, 'Curiram', b'1'),
(125, 'Custom', b'1'),
(126, 'Dadyw', b'1'),
(127, 'Daelim', b'1'),
(128, 'Daewoo', b'1'),
(129, 'Daf', b'1'),
(130, 'Daihatsu', b'1'),
(131, 'Datsun', b'1'),
(132, 'Dax', b'1'),
(133, 'Dayun', b'1'),
(134, 'Demag', b'1'),
(135, 'Deutz-Fahr', b'1'),
(136, 'DFM', b'1'),
(137, 'DFSK', b'1'),
(138, 'Dimex', b'1'),
(139, 'Dingo', b'1'),
(140, 'Divermaq', b'1'),
(141, 'DKW', b'1'),
(142, 'Dodge', b'1'),
(143, 'Dongfeng', b'1'),
(144, 'Doosan', b'1'),
(145, 'Ducar', b'1'),
(146, 'Ducati', b'1'),
(147, 'Duroboat', b'1'),
(148, 'E-One', b'1'),
(149, 'Eager Beaver', b'1'),
(150, 'Eagle', b'1'),
(151, 'Ecoline', b'1'),
(152, 'Elho', b'1'),
(153, 'Escape', b'1'),
(154, 'Estuder', b'1'),
(155, 'Euromot', b'1'),
(156, 'Europard', b'1'),
(157, 'Evinrude', b'1'),
(158, 'Exel', b'1'),
(159, 'Fada', b'1'),
(160, 'Falc', b'1'),
(161, 'Farmtrac', b'1'),
(162, 'Faw', b'1'),
(163, 'Fayser', b'1'),
(164, 'Feri', b'1'),
(165, 'Fernandez', b'1'),
(166, 'Ferrari', b'1'),
(167, 'Fiat', b'1'),
(168, 'Fiatallis', b'1'),
(169, 'Fibermold', b'1'),
(170, 'Fibertex', b'1'),
(171, 'Fina', b'1'),
(172, 'Firemax', b'1'),
(173, 'Fisher Sportscars', b'1'),
(174, 'Fleewood', b'1'),
(175, 'FMA', b'1'),
(176, 'Focus Bikes', b'1'),
(177, 'Ford', b'1'),
(178, 'Forest River', b'1'),
(179, 'Forson', b'1'),
(180, 'Foton', b'1'),
(181, 'Four', b'1'),
(182, 'Free Way', b'1'),
(183, 'Freightliner', b'1'),
(184, 'Fruehauf', b'1'),
(185, 'Fullcar', b'1'),
(186, 'Fuma', b'1'),
(187, 'Fuso', b'1'),
(188, 'Futong', b'1'),
(189, 'Galion', b'1'),
(190, 'Gama', b'1'),
(191, 'Garant Kotte', b'1'),
(192, 'Gardella', b'1'),
(193, 'Garelli', b'1'),
(194, 'Gas Gas', b'1'),
(195, 'Geely', b'1'),
(196, 'Geo', b'1'),
(197, 'Geoagric', b'1'),
(198, 'Giant', b'1'),
(199, 'Gilera', b'1'),
(200, 'Glastrom', b'1'),
(201, 'GMC', b'1'),
(202, 'Golden Dragon', b'1'),
(203, 'Goldini', b'1'),
(204, 'Goren', b'1'),
(205, 'Great Dane', b'1'),
(206, 'Great Wall', b'1'),
(207, 'Greenmaster', b'1'),
(208, 'Gribaldi & Salvia', b'1'),
(209, 'Grimme', b'1'),
(210, 'Grove', b'1'),
(211, 'Grua', b'1'),
(212, 'GT Bicycles', b'1'),
(213, 'Gtop', b'1'),
(214, 'Guowei', b'1'),
(215, 'Guzzi', b'1'),
(216, 'Hafei', b'1'),
(217, 'Haima', b'1'),
(218, 'Hammel', b'1'),
(219, 'Hardi', b'1'),
(220, 'Harley-Davidson', b'1'),
(221, 'Hartford', b'1'),
(222, 'Hechizo', b'1'),
(223, 'Heli', b'1'),
(224, 'Hensim', b'1'),
(225, 'Hero Puch', b'1'),
(226, 'Hidromek', b'1'),
(227, 'Higer', b'1'),
(228, 'Hino', b'1'),
(229, 'Hitachi', b'1'),
(230, 'Honda', b'1'),
(231, 'Honex', b'1'),
(232, 'Hood', b'1'),
(233, 'Howard', b'1'),
(234, 'Huanghai - SG', b'1'),
(235, 'Huatai', b'1'),
(236, 'Hudson', b'1'),
(237, 'Hummer', b'1'),
(238, 'Hunter', b'1'),
(239, 'Husaberg', b'1'),
(240, 'Husqvarna', b'1'),
(241, 'Hyosung', b'1'),
(242, 'Hyster', b'1'),
(243, 'Hyundai', b'1'),
(244, 'Ibiza', b'1'),
(245, 'Ika', b'1'),
(246, 'Indusa', b'1'),
(247, 'Infiniti', b'1'),
(248, 'Ingersoll Rand', b'1'),
(249, 'Inrecar', b'1'),
(250, 'International', b'1'),
(251, 'Invermoto', b'1'),
(252, 'Isuzu', b'1'),
(253, 'Iveco', b'1'),
(254, 'Ivesa', b'1'),
(255, 'Jaar', b'1'),
(256, 'JAC Motors', b'1'),
(257, 'Jacto', b'1'),
(258, 'Jaguar', b'1'),
(259, 'Jan', b'1'),
(260, 'Jawa', b'1'),
(261, 'Jayco', b'1'),
(262, 'JBC', b'1'),
(263, 'JCB', b'1'),
(264, 'Jeep', b'1'),
(265, 'JF', b'1'),
(266, 'Jialing', b'1'),
(267, 'Jianshe', b'1'),
(268, 'Jiapeng', b'1'),
(269, 'Jinbei', b'1'),
(270, 'Jincheng', b'1'),
(271, 'Jinlun', b'1'),
(272, 'Jinma', b'1'),
(273, 'JLG', b'1'),
(274, 'JMC', b'1'),
(275, 'John Deere', b'1'),
(276, 'John Henry', b'1'),
(277, 'Jonway', b'1'),
(278, 'Joyride', b'1'),
(279, 'Jumil', b'1'),
(280, 'Jurmar', b'1'),
(281, 'Kamaz', b'1'),
(282, 'Karmann Caravan', b'1'),
(283, 'Kaufman', b'1'),
(284, 'Kawasaki', b'1'),
(285, 'Kayak', b'1'),
(286, 'Keeway', b'1'),
(287, 'Kenworth', b'1'),
(288, 'Keystone', b'1'),
(289, 'Kia', b'1'),
(290, 'Kia Motors', b'1'),
(291, 'Kingyard', b'1'),
(292, 'Kinlon', b'1'),
(293, 'Kinroad', b'1'),
(294, 'Knigth', b'1'),
(295, 'Kobelco', b'1'),
(296, 'Komatsu', b'1'),
(297, 'Koshin', b'1'),
(298, 'Krone', b'1'),
(299, 'KTM', b'1'),
(300, 'Kubota', b'1'),
(301, 'Kuhn', b'1'),
(302, 'Kvernerland', b'1'),
(303, 'Kydron', b'1'),
(304, 'Kymco', b'1'),
(305, 'KZ', b'1'),
(306, 'Labounty', b'1'),
(307, 'Lada', b'1'),
(308, 'Lamborghini', b'1'),
(309, 'Lambretta', b'1'),
(310, 'Lancia', b'1'),
(311, 'Land Mark', b'1'),
(312, 'Land Rover', b'1'),
(313, 'Landini', b'1'),
(314, 'Larson', b'1'),
(315, 'Laverda', b'1'),
(316, 'Leike', b'1'),
(317, 'Lely', b'1'),
(318, 'Lemken', b'1'),
(319, 'Lerpain', b'1'),
(320, 'Lexus', b'1'),
(321, 'Leyland', b'1'),
(322, 'Lian Mei', b'1'),
(323, 'Librelato', b'1'),
(324, 'Liebherr', b'1'),
(325, 'Lievers', b'1'),
(326, 'Lifan', b'1'),
(327, 'Lifeng Group', b'1'),
(328, 'Lihhai', b'1'),
(329, 'Lincoln', b'1'),
(330, 'Linde', b'1'),
(331, 'Link-Belt', b'1'),
(332, 'Lishan Bus', b'1'),
(333, 'Litostrog', b'1'),
(334, 'LML', b'1'),
(335, 'Loncin', b'1'),
(336, 'Longgong', b'1'),
(337, 'Longstar', b'1'),
(338, 'Lonking', b'1'),
(339, 'Lotus', b'1'),
(340, 'Luojia', b'1'),
(341, 'Luzhong', b'1'),
(342, 'Machile', b'1'),
(343, 'Mack', b'1'),
(344, 'Madal', b'1'),
(345, 'Magirus Deutz', b'1'),
(346, 'Magnum', b'1'),
(347, 'Magrinsa', b'1'),
(348, 'Mahindra', b'1'),
(349, 'Maico', b'1'),
(350, 'MainMotor', b'1'),
(351, 'Majesa', b'1'),
(352, 'Malbec', b'1'),
(353, 'Man', b'1'),
(354, 'Manac', b'1'),
(355, 'Manitou', b'1'),
(356, 'Marchesan', b'1'),
(357, 'Marcopolo', b'1'),
(358, 'Mariah', b'1'),
(359, 'Mariner', b'1'),
(360, 'Maserati', b'1'),
(361, 'Massey ferguson', b'1'),
(362, 'Mathews Company', b'1'),
(363, 'Maximal', b'1'),
(364, 'Maxum', b'1'),
(365, 'Maxus', b'1'),
(366, 'Mayov', b'1'),
(367, 'Mazda', b'1'),
(368, 'Megelli', b'1'),
(369, 'Mercedes Benz', b'1'),
(370, 'Mercruiser', b'1'),
(371, 'Mercury', b'1'),
(372, 'MG', b'1'),
(373, 'Michigan', b'1'),
(374, 'Mikilon', b'1'),
(375, 'Mini Cooper', b'1'),
(376, 'Mits', b'1'),
(377, 'Mitsubishi', b'1'),
(378, 'Monosem', b'1'),
(379, 'Monster', b'1'),
(380, 'Montelli', b'1'),
(381, 'Montenegro', b'1'),
(382, 'Monterrey', b'1'),
(383, 'Montesa', b'1'),
(384, 'Morbidelli', b'1'),
(385, 'Morris', b'1'),
(386, 'Moskito', b'1'),
(387, 'Motar', b'1'),
(388, 'Moto ABC', b'1'),
(389, 'Moto-Vitale', b'1'),
(390, 'Motobi', b'1'),
(391, 'Motofarm', b'1'),
(392, 'Motoguzzi', b'1'),
(393, 'Motomel', b'1'),
(394, 'Motorhome', b'1'),
(395, 'Motorrad', b'1'),
(396, 'Motosport', b'1'),
(397, 'Movil', b'1'),
(398, 'MPT', b'1'),
(399, 'MSK', b'1'),
(400, 'MTD', b'1'),
(401, 'Multitruck', b'1'),
(402, 'Mussre', b'1'),
(403, 'Mustang', b'1'),
(404, 'MV Agusta', b'1'),
(405, 'National Crane', b'1'),
(406, 'Nautic', b'1'),
(407, 'Nauticat', b'1'),
(408, 'New Holland', b'1'),
(409, 'Nichiyu', b'1'),
(410, 'Nissan', b'1'),
(411, 'Nitro', b'1'),
(412, 'Niubo', b'1'),
(413, 'Nobel', b'1'),
(414, 'Nordkapp', b'1'),
(415, 'NSU', b'1'),
(416, 'Nux', b'1'),
(417, 'Oayun', b'1'),
(418, 'Oldsmobile', b'1'),
(419, 'Olympian', b'1'),
(420, 'Omega', b'1'),
(421, 'Opel', b'1'),
(422, 'Orion', b'1'),
(423, 'Ossa', b'1'),
(424, 'Ovlac', b'1'),
(425, 'Oxford', b'1'),
(426, 'Packard', b'1'),
(427, 'Palfinger', b'1'),
(428, 'Peerless', b'1'),
(429, 'Pegaso', b'1'),
(430, 'Peterbilt', b'1'),
(431, 'Peugeot', b'1'),
(432, 'PGO', b'1'),
(433, 'Piaggio', b'1'),
(434, 'Pioneer', b'1'),
(435, 'Pit Bike', b'1'),
(436, 'PKM', b'1'),
(437, 'Plymouth', b'1'),
(438, 'Polaris', b'1'),
(439, 'Polini', b'1'),
(440, 'Pontiac', b'1'),
(441, 'Porsche', b'1'),
(442, 'Poseidon', b'1'),
(443, 'Powerscreen', b'1'),
(444, 'Pramac', b'1'),
(445, 'Predictor', b'1'),
(446, 'Prelude', b'1'),
(447, 'Prentice', b'1'),
(448, 'Prodeco', b'1'),
(449, 'Proton', b'1'),
(450, 'Prott', b'1'),
(451, 'Pumar', b'1'),
(452, 'PYH', b'1'),
(453, 'PZ', b'1'),
(454, 'Quingri', b'1'),
(455, 'Quingui', b'1'),
(456, 'Rabalme', b'1'),
(457, 'RAM', b'1'),
(458, 'Randon', b'1'),
(459, 'Range Rover', b'1'),
(460, 'Ranger', b'1'),
(461, 'Rauch', b'1'),
(462, 'Rautop', b'1'),
(463, 'Regal', b'1'),
(464, 'Regal Raptor', b'1'),
(465, 'Regnicoli', b'1'),
(466, 'Regnicoli', b'1'),
(467, 'Renault', b'1'),
(468, 'Rermann', b'1'),
(469, 'Rexton', b'1'),
(470, 'Rhino', b'1'),
(471, 'Rino', b'1'),
(472, 'Riotecnia', b'1'),
(473, 'Rizato', b'1'),
(474, 'Robot', b'1'),
(475, 'Rolls-Royce', b'1'),
(476, 'Rondini', b'1'),
(477, 'Rotax', b'1'),
(478, 'Rover', b'1'),
(479, 'Royal Enfield', b'1'),
(480, 'Saab', b'1'),
(481, 'Sabatch', b'1'),
(482, 'Sachs', b'1'),
(483, 'Saleen', b'1'),
(484, 'Same', b'1'),
(485, 'Samsung', b'1'),
(486, 'Sanlg', b'1'),
(487, 'Sanya', b'1'),
(488, 'Sargent Agricola', b'1'),
(489, 'Saturn', b'1'),
(490, 'Scania', b'1'),
(491, 'Schwarze', b'1'),
(492, 'SDLG', b'1'),
(493, 'Sea-Doo', b'1'),
(494, 'Sea-Ray', b'1'),
(495, 'Seat', b'1'),
(496, 'Semirigido', b'1'),
(497, 'Shaanxi', b'1'),
(498, 'Shacman', b'1'),
(499, 'Shangai', b'1'),
(500, 'Shelby', b'1'),
(501, 'Shenyang', b'1'),
(502, 'Sherco', b'1'),
(503, 'Shifeng', b'1'),
(504, 'Shineray', b'1'),
(505, 'Shiyam', b'1'),
(506, 'Silver Marine', b'1'),
(507, 'Silverado', b'1'),
(508, 'Simca', b'1'),
(509, 'Simson', b'1'),
(510, 'Sin marca', b'1'),
(511, 'Singer', b'1'),
(512, 'Sinotruck', b'1'),
(513, 'Sinski', b'1'),
(514, 'Ski-Doo', b'1'),
(515, 'Skoda', b'1'),
(516, 'Skua', b'1'),
(517, 'Skygo', b'1'),
(518, 'SMA', b'1'),
(519, 'Smart', b'1'),
(520, 'Snorkel', b'1'),
(521, 'Socage', b'1'),
(522, 'Sonalika', b'1'),
(523, 'Sonyen', b'1'),
(524, 'South Marine', b'1'),
(525, 'Soyoda', b'1'),
(526, 'Speedo', b'1'),
(527, 'Spitz', b'1'),
(528, 'Sport Voad', b'1'),
(529, 'Sportbas', b'1'),
(530, 'Sportboat', b'1'),
(531, 'Ssangyong', b'1'),
(532, 'Stallion', b'1'),
(533, 'Standard', b'1'),
(534, 'Stara', b'1'),
(535, 'Starcraft RV', b'1'),
(536, 'Status', b'1'),
(537, 'Sterling', b'1'),
(538, 'Stingray', b'1'),
(539, 'Stoll', b'1'),
(540, 'Subaru', b'1'),
(541, 'Sukida', b'1'),
(542, 'Sullair', b'1'),
(543, 'Sullivan', b'1'),
(544, 'Sumo', b'1'),
(545, 'Sunlong', b'1'),
(546, 'Super Hawai', b'1'),
(547, 'Super Products', b'1'),
(548, 'Surco', b'1'),
(549, 'Suzuki', b'1'),
(550, 'SYM', b'1'),
(551, 'Taarup', b'1'),
(552, 'Tahoe', b'1'),
(553, 'Takasaki', b'1'),
(554, 'Takeuchi', b'1'),
(555, 'Tata', b'1'),
(556, 'Tatu', b'1'),
(557, 'TCM', b'1'),
(558, 'Tecnomad', b'1'),
(559, 'Tennant', b'1'),
(560, 'Terex', b'1'),
(561, 'TGB', b'1'),
(562, 'Thompson', b'1'),
(563, 'Thwaites', b'1'),
(564, 'Tiger', b'1'),
(565, 'TM', b'1'),
(566, 'Tohatsu', b'1'),
(567, 'Torito', b'1'),
(568, 'Toro', b'1'),
(569, 'Toyota', b'1'),
(570, 'Trailer', b'1'),
(571, 'Transpak', b'1'),
(572, 'Travin', b'1'),
(573, 'Trayers', b'1'),
(574, 'Trek', b'1'),
(575, 'Tremac', b'1'),
(576, 'Triumph', b'1'),
(577, 'Trophy', b'1'),
(578, 'Tsunami', b'1'),
(579, 'TVS', b'1'),
(580, 'United Motors', b'1'),
(581, 'Universal', b'1'),
(582, 'Ural', b'1'),
(583, 'Utility', b'1'),
(584, 'Valmet', b'1'),
(585, 'Valpadana', b'1'),
(586, 'Valtra', b'1'),
(587, 'Van Hool', b'1'),
(588, 'Vargas', b'1'),
(589, 'Vento', b'1'),
(590, 'Vermeer', b'1'),
(591, 'Verona', b'1'),
(592, 'Vespa', b'1'),
(593, 'Vicon', b'1'),
(594, 'Victoria', b'1'),
(595, 'Victory', b'1'),
(596, 'Vitalmetal', b'1'),
(597, 'Volare', b'1'),
(598, 'Volkswagen', b'1'),
(599, 'Volvo', b'1'),
(600, 'Vulcan', b'1'),
(601, 'Wabash', b'1'),
(602, 'Wagner', b'1'),
(603, 'Walker Bay', b'1'),
(604, 'Wanch', b'1'),
(605, 'Wangye', b'1'),
(606, 'Water Scooter', b'1'),
(607, 'Western Star', b'1'),
(608, 'Willys', b'1'),
(609, 'Winnebago', b'1'),
(610, 'Witzco', b'1'),
(611, 'Wolken', b'1'),
(612, 'Wuhlmaus', b'1'),
(613, 'Wuling', b'1'),
(614, 'XCMG', b'1'),
(615, 'Xgjao', b'1'),
(616, 'Xinfu', b'1'),
(617, 'Xingyue', b'1'),
(618, 'Xmotor', b'1'),
(619, 'Xpress International', b'1'),
(620, 'Yale', b'1'),
(621, 'Yamaha', b'1'),
(622, 'Yanmar', b'1'),
(623, 'Yarara', b'1'),
(624, 'Yaxing', b'1'),
(625, 'Yepear', b'1'),
(626, 'Yingang', b'1'),
(627, 'Yinxiang', b'1'),
(628, 'Yomel', b'1'),
(629, 'Youyi', b'1'),
(630, 'Yuejin', b'1'),
(631, 'Yugo', b'1'),
(632, 'Zastava', b'1'),
(633, 'Zeppelin', b'1'),
(634, 'Zetor', b'1'),
(635, 'Zhongtong', b'1'),
(636, 'ZNA', b'1'),
(637, 'Znen Group', b'1'),
(638, 'Zodiac', b'1'),
(639, 'Zongshen', b'1'),
(640, 'Zoomlion', b'1'),
(641, 'Zotye', b'1'),
(642, 'Zueger', b'1'),
(643, 'Zumotho', b'1'),
(644, 'Zundapp', b'1'),
(645, 'ZX Auto', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento`
--

CREATE TABLE IF NOT EXISTS `movimiento` (
  `idMovimiento` int(11) NOT NULL AUTO_INCREMENT,
  `idStock` int(11) NOT NULL,
  `idProveedor` int(11) DEFAULT NULL,
  `tipo` int(11) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cantidad` int(11) NOT NULL DEFAULT '0',
  `valorUnidad` int(11) DEFAULT NULL,
  `observacion` varchar(500) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idMovimiento`),
  KEY `fk_movimiento_stock1` (`idStock`),
  KEY `fk_movimiento_proveedor` (`idProveedor`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=83 ;

--
-- Volcar la base de datos para la tabla `movimiento`
--

INSERT INTO `movimiento` (`idMovimiento`, `idStock`, `idProveedor`, `tipo`, `fecha`, `cantidad`, `valorUnidad`, `observacion`) VALUES
(55, 9, 2, 0, '2014-04-10 02:58:41', 5, 6, NULL),
(56, 10, 2, 0, '2014-04-10 02:58:50', 6, 6, NULL),
(57, 9, 2, 0, '2014-04-14 11:08:04', 2, 500, NULL),
(59, 9, 2, 0, '2014-04-14 13:03:13', 3, 30000, NULL),
(60, 9, 2, 0, '2014-04-14 17:12:01', 50, 400, NULL),
(61, 10, 2, 0, '2014-04-14 17:12:13', 50, 500, NULL),
(62, 11, 2, 0, '2014-04-14 17:12:24', 50, 4000, NULL),
(63, 12, 2, 0, '2014-04-14 17:12:37', 50, 40000, NULL),
(64, 9, 1, 0, '2014-04-18 11:22:42', 23, 500, 'esta es una prueba de observacion al modimviento'),
(65, 9, NULL, 1, '2014-04-18 16:22:37', 4, 60000, NULL),
(66, 11, NULL, 1, '2014-04-18 16:25:26', 4, 8000, NULL),
(67, 12, NULL, 1, '2014-04-18 16:25:26', 12, 80000, NULL),
(68, 9, NULL, 1, '2014-04-18 16:25:50', 2, 60000, NULL),
(69, 10, NULL, 1, '2014-04-18 16:25:51', 3, 1000, NULL),
(70, 9, NULL, 1, '2014-04-18 16:32:20', 4, 60000, NULL),
(71, 10, NULL, 1, '2014-04-18 16:32:21', 3, 1000, NULL),
(72, 9, 2, 2, '2014-04-18 17:24:21', -2, 2, 'Se descuentan por perdida'),
(73, 9, 2, 2, '2014-04-18 17:32:30', -1, 2, 'test'),
(75, 9, 1, 2, '2014-04-27 19:59:42', -1, 123, 'test'),
(77, 10, 1, 2, '2014-04-27 20:00:02', -1, 123, '2123123'),
(78, 9, NULL, 2, '2014-04-27 20:01:40', -1, 0, 'Ajuste por perdida de producto'),
(79, 13, 2, 0, '2014-04-27 20:40:07', 3, 9000, 'gestet'),
(80, 13, NULL, 2, '2014-04-27 20:40:23', -5, 0, 'prueba '),
(81, 13, 1, 0, '2014-04-27 20:40:58', 4, 8000, 'test'),
(82, 13, NULL, 2, '2014-04-27 20:44:24', -2, 0, 'test');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento_documento`
--

CREATE TABLE IF NOT EXISTS `movimiento_documento` (
  `idMovimientoDocumento` int(11) NOT NULL AUTO_INCREMENT,
  `rutaNombre` varchar(500) COLLATE utf8_spanish_ci DEFAULT NULL,
  `idMovimiento` int(11) NOT NULL,
  PRIMARY KEY (`idMovimientoDocumento`),
  KEY `fk_movimiento_documento_movimiento1` (`idMovimiento`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=2 ;

--
-- Volcar la base de datos para la tabla `movimiento_documento`
--

INSERT INTO `movimiento_documento` (`idMovimientoDocumento`, `rutaNombre`, `idMovimiento`) VALUES
(1, 'D:\\ArchivosAtm\\upload\\mov\\64\\ordenes_trabajo.xls', 64);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento_ingreso`
--

CREATE TABLE IF NOT EXISTS `movimiento_ingreso` (
  `idMovimientoIngreso` int(11) NOT NULL AUTO_INCREMENT,
  `idMovimiento` int(11) NOT NULL,
  `valorVenta` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  PRIMARY KEY (`idMovimientoIngreso`),
  KEY `fk_movimiento_ingreso_movimiento1` (`idMovimiento`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=39 ;

--
-- Volcar la base de datos para la tabla `movimiento_ingreso`
--

INSERT INTO `movimiento_ingreso` (`idMovimientoIngreso`, `idMovimiento`, `valorVenta`, `cantidad`) VALUES
(28, 55, 12, 0),
(29, 56, 12, 0),
(30, 57, 1000, 1),
(31, 59, 60000, 3),
(32, 60, 800, 50),
(33, 61, 1000, 50),
(34, 62, 8000, 46),
(35, 63, 80000, 38),
(36, 64, 750, 23),
(37, 79, 18000, 3),
(38, 81, 12000, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden`
--

CREATE TABLE IF NOT EXISTS `orden` (
  `idOrden` int(11) NOT NULL AUTO_INCREMENT,
  `idVehiculoOrden` int(11) NOT NULL,
  `nombreUsuario` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `idFormaPago` int(11) DEFAULT NULL,
  `fechaOrden` datetime NOT NULL,
  PRIMARY KEY (`idOrden`),
  KEY `fk_orden_trabajo_vehiculo_mantencion1` (`idVehiculoOrden`),
  KEY `fk_orden_trabajo_usuario1` (`nombreUsuario`),
  KEY `fk_orden_trabajo_forma_pago1` (`idFormaPago`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=64 ;

--
-- Volcar la base de datos para la tabla `orden`
--

INSERT INTO `orden` (`idOrden`, `idVehiculoOrden`, `nombreUsuario`, `idFormaPago`, `fechaOrden`) VALUES
(50, 51, 'admin', NULL, '2014-04-10 02:49:02'),
(51, 52, 'admin', NULL, '2014-04-10 17:45:34'),
(52, 53, 'admin', NULL, '2014-04-10 23:53:41'),
(53, 54, 'admin', NULL, '2014-04-11 00:37:49'),
(54, 55, 'admin', NULL, '2014-04-11 00:44:23'),
(55, 56, 'admin', NULL, '2014-04-14 10:14:41'),
(56, 57, 'admin', 1, '2014-03-14 10:15:43'),
(57, 58, 'admin', NULL, '2014-04-14 12:53:00'),
(58, 59, 'admin', NULL, '2014-04-14 12:59:20'),
(59, 60, 'admin', NULL, '2014-04-14 13:02:02'),
(60, 61, 'admin', 2, '2014-02-14 15:42:27'),
(61, 62, 'admin', NULL, '2014-04-14 17:20:53'),
(62, 63, 'admin', NULL, '2014-04-18 11:46:50'),
(63, 64, 'admin', NULL, '2014-04-22 17:29:33');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_documento`
--

CREATE TABLE IF NOT EXISTS `orden_documento` (
  `idOrdenDocumento` int(11) NOT NULL AUTO_INCREMENT,
  `idOrden` int(11) NOT NULL,
  `rutaNombre` varchar(500) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idOrdenDocumento`),
  KEY `fk_orden_trabajo_documento_orden_trabajo1` (`idOrden`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=2 ;

--
-- Volcar la base de datos para la tabla `orden_documento`
--

INSERT INTO `orden_documento` (`idOrdenDocumento`, `idOrden`, `rutaNombre`) VALUES
(1, 54, 'D:\\ArchivosAtm\\upload\\54\\contactoEnjoy.txt');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_estado`
--

CREATE TABLE IF NOT EXISTS `orden_estado` (
  `idOrdenEstado` int(11) NOT NULL AUTO_INCREMENT,
  `idEstadoOrden` int(11) NOT NULL,
  `idOrden` int(11) NOT NULL,
  `fechaInicio` datetime NOT NULL,
  `fechaTermino` datetime DEFAULT NULL,
  `observacion` varchar(500) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idOrdenEstado`),
  KEY `fk_orden_trabajo_estado_estado_orden_trabajo1` (`idEstadoOrden`),
  KEY `fk_orden_trabajo_estado_orden_trabajo1` (`idOrden`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=76 ;

--
-- Volcar la base de datos para la tabla `orden_estado`
--

INSERT INTO `orden_estado` (`idOrdenEstado`, `idEstadoOrden`, `idOrden`, `fechaInicio`, `fechaTermino`, `observacion`) VALUES
(52, 1, 50, '2014-04-10 02:49:02', '2014-04-14 10:00:01', NULL),
(53, 1, 51, '2014-04-10 17:45:34', '2014-04-14 09:59:56', NULL),
(54, 1, 52, '2014-04-10 23:53:41', '2014-04-14 09:59:51', NULL),
(55, 1, 53, '2014-04-11 00:37:49', '2014-04-14 09:59:46', NULL),
(56, 1, 54, '2014-04-11 00:44:23', '2014-04-14 09:57:10', NULL),
(57, 7, 54, '2014-04-14 09:57:10', NULL, 'Por stock'),
(58, 7, 53, '2014-04-14 09:59:46', NULL, 'Por stock'),
(59, 7, 52, '2014-04-14 09:59:51', NULL, 'Por stock'),
(60, 7, 51, '2014-04-14 09:59:56', NULL, 'Por stock'),
(61, 7, 50, '2014-04-14 10:00:01', NULL, 'Por stock'),
(62, 1, 55, '2014-04-14 10:14:41', '2014-04-14 10:16:02', NULL),
(63, 1, 56, '2014-04-14 10:15:44', '2014-04-18 16:33:00', NULL),
(64, 5, 55, '2014-04-14 10:16:02', NULL, 'Por falta de mecanicos'),
(65, 1, 57, '2014-04-14 12:53:00', NULL, NULL),
(66, 1, 58, '2014-04-14 12:59:20', NULL, NULL),
(67, 1, 59, '2014-04-14 13:02:02', '2014-04-18 16:42:36', NULL),
(68, 1, 60, '2014-04-14 15:42:27', '2014-04-18 16:26:22', NULL),
(69, 1, 61, '2014-04-14 17:20:53', NULL, NULL),
(70, 1, 62, '2014-04-18 11:46:50', '2014-04-18 16:47:43', NULL),
(71, 6, 60, '2014-04-18 16:26:22', NULL, 'Observacion de cierre'),
(72, 6, 56, '2014-04-18 16:33:00', NULL, 'Paga con efectivo en RedCompra\r\nNro boleta 11232323'),
(73, 7, 59, '2014-04-18 16:42:36', NULL, 'Se cancelar porque cliente no tiene presupuesto para arreglar el vehículo.\r\nNo se realizó trabajo alguno'),
(74, 8, 62, '2014-04-18 16:47:43', NULL, 'Se elimina por ingreso erroneo'),
(75, 1, 63, '2014-04-22 17:29:33', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_observacion`
--

CREATE TABLE IF NOT EXISTS `orden_observacion` (
  `idOrdenObservacion` int(11) NOT NULL AUTO_INCREMENT,
  `idOrden` int(11) NOT NULL,
  `fechaRegistro` datetime NOT NULL,
  `observacion` varchar(500) NOT NULL,
  `tipoObservacion` int(11) NOT NULL,
  PRIMARY KEY (`idOrdenObservacion`),
  KEY `fk_orden_observacion_orden1` (`idOrden`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

--
-- Volcar la base de datos para la tabla `orden_observacion`
--

INSERT INTO `orden_observacion` (`idOrdenObservacion`, `idOrden`, `fechaRegistro`, `observacion`, `tipoObservacion`) VALUES
(1, 54, '2014-04-11 00:44:23', 'test demo prueba', 0),
(3, 54, '2014-04-11 01:53:43', 'otra prueba de observacion', 1),
(4, 54, '2014-04-14 09:57:10', 'Por stock', 2),
(5, 53, '2014-04-14 09:59:46', 'Por stock', 2),
(6, 52, '2014-04-14 09:59:51', 'Por stock', 2),
(7, 51, '2014-04-14 09:59:56', 'Por stock', 2),
(8, 50, '2014-04-14 10:00:01', 'Por stock', 2),
(9, 55, '2014-04-14 10:14:41', 'test', 0),
(10, 56, '2014-04-14 10:15:43', 'test 2 stock', 0),
(11, 55, '2014-04-14 10:16:02', 'Por falta de mecanicos', 2),
(12, 57, '2014-04-14 12:53:00', 'test', 0),
(13, 58, '2014-04-14 12:59:20', 'tes', 0),
(14, 59, '2014-04-14 13:02:02', 'tes', 0),
(15, 60, '2014-04-14 15:42:27', 'test', 0),
(16, 61, '2014-04-14 17:20:53', 'test', 0),
(17, 62, '2014-04-18 11:46:50', 'test', 0),
(18, 60, '2014-04-18 16:26:22', 'Observacion de cierre', 3),
(19, 56, '2014-04-18 16:33:00', 'Paga con efectivo en RedCompra\r\nNro boleta 11232323', 3),
(20, 59, '2014-04-18 16:42:36', 'Se cancelar porque cliente no tiene presupuesto para arreglar el vehículo.\r\nNo se realizó trabajo alguno', 3),
(21, 62, '2014-04-18 16:47:43', 'Se elimina por ingreso erroneo', 3),
(22, 63, '2014-04-22 17:29:33', 'test', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_servicio_terceros`
--

CREATE TABLE IF NOT EXISTS `orden_servicio_terceros` (
  `idOrdenServicioTerceros` int(11) NOT NULL AUTO_INCREMENT,
  `idOrden` int(11) NOT NULL,
  `idServiciosTerceros` int(11) NOT NULL,
  `valorServicio` bigint(20) NOT NULL,
  `fechaServicio` datetime NOT NULL,
  PRIMARY KEY (`idOrdenServicioTerceros`),
  KEY `fk_orden_trabajo_servicio_terceros_orden_trabajo1` (`idOrden`),
  KEY `fk_orden_trabajo_servicio_terceros_servicios_terceros1` (`idServiciosTerceros`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=1 ;

--
-- Volcar la base de datos para la tabla `orden_servicio_terceros`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_trabajo`
--

CREATE TABLE IF NOT EXISTS `orden_trabajo` (
  `idOrdenTrabajo` int(11) NOT NULL AUTO_INCREMENT,
  `idOrden` int(11) NOT NULL,
  `idTrabajo` int(11) NOT NULL,
  `precioManoObra` bigint(20) NOT NULL,
  `hhEstimada` int(11) DEFAULT NULL,
  `fechaInicio` datetime DEFAULT NULL,
  `fechaTermino` datetime DEFAULT NULL,
  `garantia` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`idOrdenTrabajo`),
  KEY `fk_orden_trabajo_mantencion_orden_trabajo1` (`idOrden`),
  KEY `fk_orden_trabajo_mantencion_mantencion1` (`idTrabajo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=108 ;

--
-- Volcar la base de datos para la tabla `orden_trabajo`
--

INSERT INTO `orden_trabajo` (`idOrdenTrabajo`, `idOrden`, `idTrabajo`, `precioManoObra`, `hhEstimada`, `fechaInicio`, `fechaTermino`, `garantia`) VALUES
(83, 50, 5, 80000, 13, '2014-04-10 00:00:00', NULL, b'0'),
(84, 51, 6, 12000, 6, '2014-04-10 00:00:00', NULL, b'0'),
(85, 52, 6, 12000, 6, '2014-04-10 00:00:00', NULL, b'0'),
(86, 53, 6, 12000, 6, '2014-04-11 00:00:00', NULL, b'0'),
(87, 54, 6, 12000, 6, '2014-04-11 00:00:00', NULL, b'0'),
(88, 55, 5, 80000, 13, '2014-04-14 00:00:00', NULL, b'0'),
(89, 56, 5, 80000, 13, '2014-04-14 00:00:00', '2014-04-18 00:00:00', b'0'),
(90, 57, 5, 80000, 13, '2014-04-14 00:00:00', '2014-04-18 00:00:00', b'0'),
(91, 58, 5, 80000, 13, '2014-04-14 00:00:00', NULL, b'0'),
(92, 59, 5, 80000, 13, '2014-04-14 00:00:00', NULL, b'0'),
(93, 60, 5, 80000, 13, '2014-04-14 00:00:00', '2014-04-18 00:00:00', b'0'),
(94, 60, 6, 12000, 6, '2014-04-14 00:00:00', '2014-04-18 00:00:00', b'0'),
(95, 60, 7, 21000, 3, '2014-04-14 00:00:00', '2014-04-18 00:00:00', b'0'),
(96, 61, 5, 80000, 13, '2014-04-14 00:00:00', '2014-04-29 00:00:00', b'0'),
(97, 61, 6, 12000, 6, '2014-04-14 00:00:00', NULL, b'0'),
(98, 61, 7, 21000, 3, '2014-04-14 00:00:00', NULL, b'0'),
(99, 61, 8, 14000, 2, '2014-04-14 00:00:00', NULL, b'0'),
(100, 62, 5, 80000, 13, '2014-04-18 00:00:00', NULL, b'0'),
(101, 62, 6, 12000, 6, '2014-04-18 00:00:00', NULL, b'0'),
(102, 62, 7, 21000, 3, '2014-04-18 00:00:00', NULL, b'0'),
(103, 62, 8, 14000, 2, '2014-04-18 00:00:00', NULL, b'0'),
(104, 62, 9, 49000, 7, '2014-04-18 00:00:00', NULL, b'0'),
(105, 62, 10, 21000, 3, '2014-04-18 00:00:00', NULL, b'0'),
(106, 63, 10, 12, 3, '2014-04-22 00:00:00', NULL, b'0'),
(107, 63, 8, 13, 2, '2014-04-22 00:00:00', NULL, b'0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_trabajo_estado`
--

CREATE TABLE IF NOT EXISTS `orden_trabajo_estado` (
  `idOrdenTrabajoEstado` int(11) NOT NULL AUTO_INCREMENT,
  `idOrdenTrabajo` int(11) NOT NULL,
  `idEstadoTrabajo` int(11) NOT NULL,
  `fechaInicio` datetime NOT NULL,
  `fechaTermino` datetime DEFAULT NULL,
  PRIMARY KEY (`idOrdenTrabajoEstado`),
  KEY `fk_orden_trabajo_estado_estado_trabajo1` (`idEstadoTrabajo`),
  KEY `fk_orden_trabajo_estado_orden_trabajo2` (`idOrdenTrabajo`),
  KEY `Fk_idOrdenTrabajo_OrdenTrabajo` (`idOrdenTrabajo`),
  KEY `Fk_idEstadoTrabajo_EstadoTrabajo` (`idEstadoTrabajo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=36 ;

--
-- Volcar la base de datos para la tabla `orden_trabajo_estado`
--

INSERT INTO `orden_trabajo_estado` (`idOrdenTrabajoEstado`, `idOrdenTrabajo`, `idEstadoTrabajo`, `fechaInicio`, `fechaTermino`) VALUES
(2, 83, 1, '2014-04-10 00:00:00', '2014-04-10 00:00:00'),
(3, 83, 2, '2014-04-10 00:00:00', '2014-04-10 00:00:00'),
(4, 83, 2, '2014-04-10 00:00:00', NULL),
(5, 84, 1, '2014-04-10 00:00:00', '2014-04-10 00:00:00'),
(6, 84, 2, '2014-04-10 00:00:00', NULL),
(7, 85, 1, '2014-04-10 23:53:41', NULL),
(8, 86, 1, '2014-04-11 00:37:49', NULL),
(9, 87, 1, '2014-04-11 00:44:23', NULL),
(10, 88, 1, '2014-04-14 10:14:41', NULL),
(11, 89, 1, '2014-04-14 10:15:43', '2014-04-18 16:31:34'),
(12, 90, 1, '2014-04-14 12:53:00', '2014-04-18 16:32:20'),
(13, 91, 1, '2014-04-14 12:59:20', NULL),
(14, 92, 1, '2014-04-14 13:02:02', NULL),
(15, 93, 1, '2014-04-14 15:42:27', '2014-04-18 16:22:37'),
(16, 94, 1, '2014-04-14 15:42:27', '2014-04-18 16:25:26'),
(17, 95, 1, '2014-04-14 15:42:27', '2014-04-18 16:25:50'),
(18, 96, 1, '2014-04-14 17:20:53', '2014-04-29 02:11:59'),
(19, 97, 1, '2014-04-14 17:20:53', NULL),
(20, 98, 1, '2014-04-14 17:20:53', NULL),
(21, 99, 1, '2014-04-14 17:20:53', NULL),
(22, 100, 1, '2014-04-18 11:46:50', NULL),
(23, 101, 1, '2014-04-18 11:46:50', NULL),
(24, 102, 1, '2014-04-18 11:46:50', NULL),
(25, 103, 1, '2014-04-18 11:46:50', NULL),
(26, 104, 1, '2014-04-18 11:46:50', NULL),
(27, 105, 1, '2014-04-18 11:46:50', NULL),
(28, 93, 3, '2014-04-18 16:22:37', '2014-04-18 16:22:37'),
(29, 94, 3, '2014-04-18 16:25:26', '2014-04-18 16:25:26'),
(30, 95, 3, '2014-04-18 16:25:50', '2014-04-18 16:25:50'),
(31, 89, 3, '2014-04-18 16:31:34', '2014-04-18 16:31:34'),
(32, 90, 3, '2014-04-18 16:32:20', '2014-04-18 16:32:20'),
(33, 106, 1, '2014-04-22 17:29:33', NULL),
(34, 107, 1, '2014-04-22 17:29:33', NULL),
(35, 96, 4, '2014-04-29 02:11:59', '2014-04-29 02:11:59');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_trabajo_producto`
--

CREATE TABLE IF NOT EXISTS `orden_trabajo_producto` (
  `idOrdenTrabajoProducto` int(11) NOT NULL AUTO_INCREMENT,
  `idOrdenTrabajo` int(11) DEFAULT NULL,
  `idProducto` int(11) NOT NULL,
  `idMovimiento` int(11) DEFAULT NULL,
  `traidoCliente` bit(1) DEFAULT b'0',
  `cantidad` int(11) NOT NULL DEFAULT '0',
  `valor` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idOrdenTrabajoProducto`),
  KEY `fk_orden_trabajo_producto_orden_trabajo1` (`idOrdenTrabajo`),
  KEY `fk_orden_trabajo_producto_producto1` (`idProducto`),
  KEY `fk_orden_trabajo_producto_movimiento1` (`idMovimiento`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=333 ;

--
-- Volcar la base de datos para la tabla `orden_trabajo_producto`
--

INSERT INTO `orden_trabajo_producto` (`idOrdenTrabajoProducto`, `idOrdenTrabajo`, `idProducto`, `idMovimiento`, `traidoCliente`, `cantidad`, `valor`) VALUES
(216, 83, 6, NULL, b'0', 4, 12),
(217, 83, 7, NULL, b'0', 3, 12),
(220, 84, 8, NULL, b'0', 4, 0),
(221, 84, 9, NULL, b'0', 12, 0),
(222, 85, 8, NULL, b'0', 4, 0),
(223, 85, 9, NULL, b'0', 12, 0),
(224, 86, 8, NULL, b'0', 4, 0),
(225, 86, 9, NULL, b'0', 12, 0),
(228, 87, 8, NULL, b'0', 4, 0),
(229, 87, 9, NULL, b'0', 12, 0),
(230, 88, 6, NULL, b'0', 5, 12),
(231, 88, 7, NULL, b'0', 6, 12),
(244, 91, 6, NULL, b'0', 4, 60000),
(245, 91, 7, NULL, b'0', 3, 1000),
(246, 92, 6, NULL, b'0', 4, 60000),
(247, 92, 7, NULL, b'0', 3, 1000),
(278, 96, 6, NULL, b'1', 4, 60000),
(279, 96, 7, NULL, b'1', 3, 1000),
(280, 97, 8, NULL, b'0', 4, 8000),
(281, 97, 9, NULL, b'0', 12, 80000),
(282, 98, 6, NULL, b'0', 2, 60000),
(283, 98, 7, NULL, b'0', 3, 1000),
(302, 100, 6, NULL, b'0', 4, 0),
(303, 100, 7, NULL, b'0', 3, 0),
(304, 100, 8, NULL, b'0', 1, 80000),
(305, 100, 9, NULL, b'0', 1, 80000),
(306, 101, 8, NULL, b'0', 4, 8000),
(307, 101, 9, NULL, b'0', 12, 80000),
(308, 101, 6, NULL, b'0', 1, 12000),
(309, 101, 7, NULL, b'0', 1, 12000),
(310, 102, 6, NULL, b'0', 2, 60000),
(311, 102, 7, NULL, b'0', 3, 1000),
(312, 102, 8, NULL, b'0', 1, 21000),
(313, 102, 9, NULL, b'0', 1, 21000),
(314, 104, 6, NULL, b'0', 1, 0),
(315, 104, 9, NULL, b'0', 1, 0),
(316, 104, 7, NULL, b'0', 1, 49000),
(317, 104, 8, NULL, b'0', 1, 49000),
(318, 105, 8, NULL, b'0', 6, 8000),
(319, 105, 6, NULL, b'0', 1, 21000),
(320, 105, 7, NULL, b'0', 1, 21000),
(321, 105, 9, NULL, b'0', 1, 21000),
(322, 93, 6, NULL, b'0', 4, 60000),
(323, 93, 7, NULL, b'0', 3, 1000),
(324, 94, 8, NULL, b'0', 4, 8000),
(325, 94, 9, NULL, b'0', 12, 80000),
(326, 95, 6, NULL, b'0', 2, 60000),
(327, 95, 7, NULL, b'0', 3, 1000),
(328, 89, 6, NULL, b'1', 7, 0),
(329, 89, 7, NULL, b'1', 6, 0),
(330, 90, 6, NULL, b'0', 4, 60000),
(331, 90, 7, NULL, b'0', 3, 1000),
(332, 106, 8, NULL, b'0', 6, 8000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_trabajo_solicitud`
--

CREATE TABLE IF NOT EXISTS `orden_trabajo_solicitud` (
  `idOrdenTrabajoSolicitud` int(11) NOT NULL AUTO_INCREMENT,
  `idOrdenTrabajo` int(11) DEFAULT NULL,
  `observacion` varchar(200) COLLATE utf8_spanish_ci DEFAULT NULL,
  `fechaSolicitud` datetime NOT NULL,
  `fechaTermino` datetime DEFAULT NULL,
  `estado` bit(1) NOT NULL,
  `nombreUsuario` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`idOrdenTrabajoSolicitud`),
  KEY `fk_orden_trabajo_solicitud_producto_orden_trabajo1` (`idOrdenTrabajo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=4 ;

--
-- Volcar la base de datos para la tabla `orden_trabajo_solicitud`
--

INSERT INTO `orden_trabajo_solicitud` (`idOrdenTrabajoSolicitud`, `idOrdenTrabajo`, `observacion`, `fechaSolicitud`, `fechaTermino`, `estado`, `nombreUsuario`) VALUES
(1, 89, 'Prueba de solicitud', '2014-04-14 11:04:47', NULL, b'1', 'admin'),
(2, NULL, '123 etst eese', '2014-04-14 12:59:41', NULL, b'1', 'admin'),
(3, NULL, 'por ingreso de vehiculo para bla bla bla', '2014-04-14 13:02:27', NULL, b'1', 'admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_trabajo_solicitud_producto`
--

CREATE TABLE IF NOT EXISTS `orden_trabajo_solicitud_producto` (
  `idOrdenTrabajoSolicitud` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `estado` bit(1) NOT NULL,
  PRIMARY KEY (`idOrdenTrabajoSolicitud`,`idProducto`),
  KEY `fk_orden_trabajo_solicitud_producto_orden_trabajo_solicitud1` (`idOrdenTrabajoSolicitud`),
  KEY `fk_orden_trabajo_solicitud_producto_producto1` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcar la base de datos para la tabla `orden_trabajo_solicitud_producto`
--

INSERT INTO `orden_trabajo_solicitud_producto` (`idOrdenTrabajoSolicitud`, `idProducto`, `cantidad`, `estado`) VALUES
(1, 6, 1, b'0'),
(1, 9, 4, b'1'),
(2, 6, 2, b'1'),
(2, 7, 2, b'1'),
(3, 6, 5, b'0'),
(3, 7, 2, b'1'),
(3, 8, 12, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_trabajo_usuario`
--

CREATE TABLE IF NOT EXISTS `orden_trabajo_usuario` (
  `nombreUsuario` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `idOrdenTrabajo` int(11) NOT NULL,
  PRIMARY KEY (`nombreUsuario`,`idOrdenTrabajo`),
  KEY `fk_orden_trabajo_mantencion_usuario_usuario1` (`nombreUsuario`),
  KEY `fk_orden_trabajo_mantencion_usuario_orden_trabajo_mantencion1` (`idOrdenTrabajo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcar la base de datos para la tabla `orden_trabajo_usuario`
--

INSERT INTO `orden_trabajo_usuario` (`nombreUsuario`, `idOrdenTrabajo`) VALUES
('admin', 83),
('admin', 84),
('demo', 87),
('admin', 89),
('admin', 90),
('admin', 93),
('admin', 94),
('admin', 95),
('admin', 96),
('admin', 97),
('admin', 98);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parametro_general`
--

CREATE TABLE IF NOT EXISTS `parametro_general` (
  `codigo` varchar(300) NOT NULL,
  `grupo` varchar(200) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `valor` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `parametro_general`
--

INSERT INTO `parametro_general` (`codigo`, `grupo`, `descripcion`, `valor`) VALUES
('porcentaje.iva', 'valores', 'Porcentaje IVA', '19'),
('ruta.archivos', 'archivo', 'Ruta de subida de archivos', 'D:\\ArchivosAtm\\upload\\'),
('ruta.reportes', 'reporte', 'Ruta plantilla de reportes Jasper', 'D:\\ArchivosAtm\\reportes\\'),
('valor.hh', 'personal', 'Valor HH para calculo de valor en Trabajo', '7000'),
('version.app', 'app', 'Versión de la Aplicación', '1.0.1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil`
--

CREATE TABLE IF NOT EXISTS `perfil` (
  `idPerfil` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`idPerfil`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=4 ;

--
-- Volcar la base de datos para la tabla `perfil`
--

INSERT INTO `perfil` (`idPerfil`, `descripcion`, `estado`) VALUES
(1, 'Super-Administrador', b'1'),
(2, 'Administrador', b'1'),
(3, 'Mecanico', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permiso`
--

CREATE TABLE IF NOT EXISTS `permiso` (
  `idPermiso` int(11) NOT NULL AUTO_INCREMENT,
  `idPerfil` int(11) DEFAULT NULL,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `codigo` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idPermiso`),
  KEY `fk_permiso_perfil1` (`idPerfil`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=10 ;

--
-- Volcar la base de datos para la tabla `permiso`
--

INSERT INTO `permiso` (`idPermiso`, `idPerfil`, `descripcion`, `codigo`) VALUES
(1, 1, 'Administrar Personal', 'ROLE_ADM_PERSONAL'),
(2, 1, 'Administrar Usuarios', 'ROLE_ADM_USUARIOS'),
(3, 2, 'Administrar Parametros', 'ROLE_ADM_PARAM'),
(4, 2, 'Administrar Stock', 'ROLE_ADM_STOCK'),
(5, 2, 'Ordenes de Trabajo', 'ROLE_OT'),
(6, 3, 'Realizar Trabajo', 'ROLE_TRABAJO'),
(7, NULL, 'Rol Usuario', 'ROLE_USUARIO'),
(8, 2, 'Consultas', 'ROLE_CONSULTAS'),
(9, 1, 'Administrador OTs', 'ROLE_ADM_OT');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE IF NOT EXISTS `persona` (
  `idPersona` int(11) NOT NULL AUTO_INCREMENT,
  `rut` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `nombres` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `fechaNacimiento` datetime NOT NULL,
  `fechaContrato` datetime NOT NULL,
  `fechaFinal` datetime DEFAULT NULL,
  `fechaRegistro` datetime NOT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`idPersona`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=3 ;

--
-- Volcar la base de datos para la tabla `persona`
--

INSERT INTO `persona` (`idPersona`, `rut`, `nombres`, `apellidos`, `fechaNacimiento`, `fechaContrato`, `fechaFinal`, `fechaRegistro`, `estado`) VALUES
(1, '1-9', 'Administrador', ' ', '1900-00-01 00:00:00', '1900-00-01 00:00:00', NULL, '1900-00-01 00:00:00', b'1'),
(2, '15682702-9', 'Jangenni Gerard', 'Foix Villalobos', '1983-07-11 00:00:00', '0200-07-11 00:00:00', NULL, '2014-04-13 00:00:00', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE IF NOT EXISTS `producto` (
  `idProducto` int(11) NOT NULL AUTO_INCREMENT,
  `idMarca` int(11) DEFAULT NULL,
  `codigo` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `codigo_proveedor` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `estado` bit(1) NOT NULL,
  PRIMARY KEY (`idProducto`),
  KEY `fk_producto_marca1` (`idMarca`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=12 ;

--
-- Volcar la base de datos para la tabla `producto`
--

INSERT INTO `producto` (`idProducto`, `idMarca`, `codigo`, `codigo_proveedor`, `descripcion`, `estado`) VALUES
(6, 5, 'PRO0002', NULL, 'Pastillas', b'1'),
(7, 5, 'PRO0003', NULL, 'Liquido Freno', b'1'),
(8, 5, 'PRO0004', NULL, 'Bujias', b'1'),
(9, 5, 'PRO0005', NULL, 'Pistones', b'1'),
(10, 5, 'AMP0012', NULL, 'Ampolleta ', b'1'),
(11, 6, 'TRE1234', NULL, 'Disco embrague', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE IF NOT EXISTS `proveedor` (
  `idProveedor` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `telefono` varchar(200) COLLATE utf8_spanish_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `direccion` varchar(200) COLLATE utf8_spanish_ci DEFAULT NULL,
  `estado` bit(1) NOT NULL,
  `porcentajeGanancia` decimal(15,3) NOT NULL,
  PRIMARY KEY (`idProveedor`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=4 ;

--
-- Volcar la base de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`idProveedor`, `codigo`, `descripcion`, `telefono`, `email`, `direccion`, `estado`, `porcentajeGanancia`) VALUES
(1, 'PRV0001', 'Proveedor Prueba', '223344', 'jangenni@jfoix.cl', 'direccion', b'1', '50.000'),
(2, 'PRV0002', 'Proveedor 100%', '222', 'prov@gmail.com', 'las heras', b'1', '100.000'),
(3, 'PRO4534', 'prueba pfranco', '2233', 'test@gmail.com', 'las heras 1419', b'1', '50.000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicios_terceros`
--

CREATE TABLE IF NOT EXISTS `servicios_terceros` (
  `idServiciosTerceros` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `valorServicio` bigint(20) NOT NULL DEFAULT '0',
  `estado` bit(1) NOT NULL,
  PRIMARY KEY (`idServiciosTerceros`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=1 ;

--
-- Volcar la base de datos para la tabla `servicios_terceros`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stock`
--

CREATE TABLE IF NOT EXISTS `stock` (
  `idStock` int(11) NOT NULL AUTO_INCREMENT,
  `idProducto` int(11) NOT NULL,
  `idBodega` int(11) NOT NULL,
  `cantidad` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idStock`),
  KEY `fk_stock_producto1` (`idProducto`),
  KEY `fk_stock_bodega1` (`idBodega`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=14 ;

--
-- Volcar la base de datos para la tabla `stock`
--

INSERT INTO `stock` (`idStock`, `idProducto`, `idBodega`, `cantidad`) VALUES
(9, 6, 1, 68),
(10, 7, 1, 49),
(11, 8, 1, 46),
(12, 9, 1, 38),
(13, 10, 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajo`
--

CREATE TABLE IF NOT EXISTS `trabajo` (
  `idTrabajo` int(11) NOT NULL AUTO_INCREMENT,
  `idTrabajoSubTipo` int(11) NOT NULL,
  `codigo` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `hhEstimada` int(11) NOT NULL,
  `precioManoObra` bigint(20) NOT NULL,
  `estado` bit(1) NOT NULL,
  PRIMARY KEY (`idTrabajo`),
  KEY `fk_trabajo_trabajo_sub_tipo1` (`idTrabajoSubTipo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=11 ;

--
-- Volcar la base de datos para la tabla `trabajo`
--

INSERT INTO `trabajo` (`idTrabajo`, `idTrabajoSubTipo`, `codigo`, `descripcion`, `hhEstimada`, `precioManoObra`, `estado`) VALUES
(5, 6, 'TTRA0001', 'Cambio de Pastillas', 13, 80000, b'1'),
(6, 7, 'TTRA0002', 'Cambio Bujias', 6, 12000, b'1'),
(7, 6, 'TBUJ0039', 'Trabajo bujias', 3, 21000, b'1'),
(8, 8, 'SAIR0001', 'Aire Acondicionado', 2, 14000, b'1'),
(9, 6, 'TTRA0011', 'trabajo', 7, 49000, b'1'),
(10, 7, 'MTRA9299', 'Trabajo 233', 3, 21000, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajo_producto`
--

CREATE TABLE IF NOT EXISTS `trabajo_producto` (
  `idTrabajo` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `cantidadProducto` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idTrabajo`,`idProducto`),
  KEY `fk_mantencion_producto_mantencion1` (`idTrabajo`),
  KEY `fk_mantencion_producto_producto1` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcar la base de datos para la tabla `trabajo_producto`
--

INSERT INTO `trabajo_producto` (`idTrabajo`, `idProducto`, `cantidadProducto`) VALUES
(5, 6, 4),
(5, 7, 3),
(6, 8, 4),
(6, 9, 12),
(7, 6, 2),
(7, 7, 3),
(9, 6, 1),
(9, 9, 1),
(10, 8, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajo_sub_tipo`
--

CREATE TABLE IF NOT EXISTS `trabajo_sub_tipo` (
  `idTrabajoSubTipo` int(11) NOT NULL AUTO_INCREMENT,
  `idTrabajoTipo` int(11) NOT NULL,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `externo` bit(1) NOT NULL DEFAULT b'1',
  `estado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`idTrabajoSubTipo`),
  KEY `fk_trabajo_sub_tipo_trabajo_tipo1` (`idTrabajoTipo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=9 ;

--
-- Volcar la base de datos para la tabla `trabajo_sub_tipo`
--

INSERT INTO `trabajo_sub_tipo` (`idTrabajoSubTipo`, `idTrabajoTipo`, `descripcion`, `externo`, `estado`) VALUES
(6, 1, 'Mantención Frenos', b'0', b'1'),
(7, 1, 'Mantención Bujias', b'0', b'1'),
(8, 2, 'Taller Limache', b'1', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajo_tipo`
--

CREATE TABLE IF NOT EXISTS `trabajo_tipo` (
  `idTrabajoTipo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`idTrabajoTipo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=4 ;

--
-- Volcar la base de datos para la tabla `trabajo_tipo`
--

INSERT INTO `trabajo_tipo` (`idTrabajoTipo`, `descripcion`, `estado`) VALUES
(1, 'Taller', b'1'),
(2, 'Servicio Tercero', b'1'),
(3, 'DYP', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `nombreUsuario` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `clave` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `fechaIngreso` datetime NOT NULL,
  `estado` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  PRIMARY KEY (`nombreUsuario`),
  KEY `fk_usuario_persona1` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcar la base de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`nombreUsuario`, `clave`, `fechaIngreso`, `estado`, `idPersona`) VALUES
('admin', '123', '2013-01-01 00:00:00', 0, 1),
('demo', '123', '2014-04-12 11:49:34', 0, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_permiso`
--

CREATE TABLE IF NOT EXISTS `usuario_permiso` (
  `nombreUsuario` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `idPermiso` int(11) NOT NULL,
  PRIMARY KEY (`nombreUsuario`,`idPermiso`),
  KEY `fk_usuario_permiso_usuario1` (`nombreUsuario`),
  KEY `fk_usuario_permiso_permiso1` (`idPermiso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcar la base de datos para la tabla `usuario_permiso`
--

INSERT INTO `usuario_permiso` (`nombreUsuario`, `idPermiso`) VALUES
('admin', 1),
('demo', 1),
('admin', 2),
('demo', 2),
('admin', 3),
('admin', 4),
('admin', 5),
('admin', 6),
('demo', 6),
('admin', 7),
('demo', 7),
('admin', 8),
('admin', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE IF NOT EXISTS `vehiculo` (
  `idVehiculo` int(11) NOT NULL AUTO_INCREMENT,
  `idMarcaVehiculo` int(11) NOT NULL,
  `patente` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `chasis` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `color` varchar(200) COLLATE utf8_spanish_ci DEFAULT NULL,
  `acno` int(11) DEFAULT NULL,
  `modelo` varchar(200) COLLATE utf8_spanish_ci DEFAULT NULL,
  `nroMotor` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idVehiculo`),
  KEY `fk_vehiculo_modelo1` (`idMarcaVehiculo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=23 ;

--
-- Volcar la base de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`idVehiculo`, `idMarcaVehiculo`, `patente`, `chasis`, `color`, `acno`, `modelo`, `nroMotor`) VALUES
(20, 431, 'QW-11-11', '123', 'rojo', 2001, '201', '23123'),
(21, 369, 'QW-12-34', '123', 'Verde', 2013, 'Benzzzo', '123123'),
(22, 2, 'AS-WE-12', 'we', 'rojo', 9871, '987', '987');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo_orden`
--

CREATE TABLE IF NOT EXISTS `vehiculo_orden` (
  `idVehiculoOrden` int(11) NOT NULL AUTO_INCREMENT,
  `idVehiculo` int(11) NOT NULL,
  `idCliente` int(11) DEFAULT NULL,
  `kilometraje` bigint(20) NOT NULL,
  PRIMARY KEY (`idVehiculoOrden`),
  KEY `fk_vehiculo_mantencion_vehiculo` (`idVehiculo`),
  KEY `fk_vehiculo_mantencion_cliente1` (`idCliente`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=65 ;

--
-- Volcar la base de datos para la tabla `vehiculo_orden`
--

INSERT INTO `vehiculo_orden` (`idVehiculoOrden`, `idVehiculo`, `idCliente`, `kilometraje`) VALUES
(51, 20, 12, 12),
(52, 20, 12, 123),
(53, 20, 12, 123),
(54, 20, 12, 123),
(55, 20, 12, 123),
(56, 20, 12, 123),
(57, 20, 12, 123),
(58, 20, 12, 123),
(59, 20, 12, 123),
(60, 20, 12, 123),
(61, 20, 12, 123),
(62, 21, 12, 12333),
(63, 20, 12, 120000),
(64, 21, 12, 432423);

--
-- Filtros para las tablas descargadas (dump)
--

--
-- Filtros para la tabla `mantencion_programada_trabajo`
--
ALTER TABLE `mantencion_programada_trabajo`
  ADD CONSTRAINT `fk_mantencion_programada_trabajo_mantencion_programada1` FOREIGN KEY (`idMantencionProgramada`) REFERENCES `mantencion_programada` (`idMantencionProgramada`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_mantencion_programada_trabajo_trabajo1` FOREIGN KEY (`idTrabajo`) REFERENCES `trabajo` (`idTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `movimiento`
--
ALTER TABLE `movimiento`
  ADD CONSTRAINT `fk_movimiento_proveedor` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_movimiento_stock1` FOREIGN KEY (`idStock`) REFERENCES `stock` (`idStock`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `movimiento_documento`
--
ALTER TABLE `movimiento_documento`
  ADD CONSTRAINT `fk_movimiento_documento_movimiento1` FOREIGN KEY (`idMovimiento`) REFERENCES `movimiento` (`idMovimiento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `movimiento_ingreso`
--
ALTER TABLE `movimiento_ingreso`
  ADD CONSTRAINT `fk_movimiento_ingreso_movimiento1` FOREIGN KEY (`idMovimiento`) REFERENCES `movimiento` (`idMovimiento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden`
--
ALTER TABLE `orden`
  ADD CONSTRAINT `fk_orden_trabajo_forma_pago1` FOREIGN KEY (`idFormaPago`) REFERENCES `forma_pago` (`idFormaPago`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_orden_trabajo_usuario1` FOREIGN KEY (`nombreUsuario`) REFERENCES `usuario` (`nombreUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_orden_trabajo_vehiculo_mantencion1` FOREIGN KEY (`idVehiculoOrden`) REFERENCES `vehiculo_orden` (`idVehiculoOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden_documento`
--
ALTER TABLE `orden_documento`
  ADD CONSTRAINT `fk_orden_trabajo_documento_orden_trabajo1` FOREIGN KEY (`idOrden`) REFERENCES `orden` (`idOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden_estado`
--
ALTER TABLE `orden_estado`
  ADD CONSTRAINT `fk_orden_trabajo_estado_estado_orden_trabajo1` FOREIGN KEY (`idEstadoOrden`) REFERENCES `estado_orden` (`idEstadoOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_orden_trabajo_estado_orden_trabajo1` FOREIGN KEY (`idOrden`) REFERENCES `orden` (`idOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden_observacion`
--
ALTER TABLE `orden_observacion`
  ADD CONSTRAINT `fk_orden_observacion_orden1` FOREIGN KEY (`idOrden`) REFERENCES `orden` (`idOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden_servicio_terceros`
--
ALTER TABLE `orden_servicio_terceros`
  ADD CONSTRAINT `fk_orden_trabajo_servicio_terceros_orden_trabajo1` FOREIGN KEY (`idOrden`) REFERENCES `orden` (`idOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_orden_trabajo_servicio_terceros_servicios_terceros1` FOREIGN KEY (`idServiciosTerceros`) REFERENCES `servicios_terceros` (`idServiciosTerceros`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden_trabajo`
--
ALTER TABLE `orden_trabajo`
  ADD CONSTRAINT `fk_orden_trabajo_mantencion_mantencion1` FOREIGN KEY (`idTrabajo`) REFERENCES `trabajo` (`idTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_orden_trabajo_mantencion_orden_trabajo1` FOREIGN KEY (`idOrden`) REFERENCES `orden` (`idOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden_trabajo_estado`
--
ALTER TABLE `orden_trabajo_estado`
  ADD CONSTRAINT `Fk_idEstadoTrabajo_EstadoTrabajo` FOREIGN KEY (`idEstadoTrabajo`) REFERENCES `estado_trabajo` (`idEstadoTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Fk_idOrdenTrabajo_OrdenTrabajo` FOREIGN KEY (`idOrdenTrabajo`) REFERENCES `orden_trabajo` (`idOrdenTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden_trabajo_producto`
--
ALTER TABLE `orden_trabajo_producto`
  ADD CONSTRAINT `fk_orden_trabajo_producto_movimiento1` FOREIGN KEY (`idMovimiento`) REFERENCES `movimiento` (`idMovimiento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_orden_trabajo_producto_orden_trabajo1` FOREIGN KEY (`idOrdenTrabajo`) REFERENCES `orden_trabajo` (`idOrdenTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_orden_trabajo_producto_producto1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden_trabajo_solicitud_producto`
--
ALTER TABLE `orden_trabajo_solicitud_producto`
  ADD CONSTRAINT `fk_orden_trabajo_solicitud_producto_orden_trabajo_solicitud1` FOREIGN KEY (`idOrdenTrabajoSolicitud`) REFERENCES `orden_trabajo_solicitud` (`idOrdenTrabajoSolicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_orden_trabajo_solicitud_producto_producto1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden_trabajo_usuario`
--
ALTER TABLE `orden_trabajo_usuario`
  ADD CONSTRAINT `fk_orden_trabajo_mantencion_usuario_orden_trabajo_mantencion1` FOREIGN KEY (`idOrdenTrabajo`) REFERENCES `orden_trabajo` (`idOrdenTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_orden_trabajo_mantencion_usuario_usuario1` FOREIGN KEY (`nombreUsuario`) REFERENCES `usuario` (`nombreUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `permiso`
--
ALTER TABLE `permiso`
  ADD CONSTRAINT `fk_permiso_perfil1` FOREIGN KEY (`idPerfil`) REFERENCES `perfil` (`idPerfil`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `fk_producto_marca1` FOREIGN KEY (`idMarca`) REFERENCES `marca` (`idMarca`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `fk_stock_bodega1` FOREIGN KEY (`idBodega`) REFERENCES `bodega` (`idBodega`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_stock_producto1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `trabajo`
--
ALTER TABLE `trabajo`
  ADD CONSTRAINT `fk_trabajo_trabajo_sub_tipo1` FOREIGN KEY (`idTrabajoSubTipo`) REFERENCES `trabajo_sub_tipo` (`idTrabajoSubTipo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `trabajo_producto`
--
ALTER TABLE `trabajo_producto`
  ADD CONSTRAINT `fk_mantencion_producto_mantencion1` FOREIGN KEY (`idTrabajo`) REFERENCES `trabajo` (`idTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_mantencion_producto_producto1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `trabajo_sub_tipo`
--
ALTER TABLE `trabajo_sub_tipo`
  ADD CONSTRAINT `fk_trabajo_sub_tipo_trabajo_tipo1` FOREIGN KEY (`idTrabajoTipo`) REFERENCES `trabajo_tipo` (`idTrabajoTipo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_persona1` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario_permiso`
--
ALTER TABLE `usuario_permiso`
  ADD CONSTRAINT `fk_usuario_permiso_permiso1` FOREIGN KEY (`idPermiso`) REFERENCES `permiso` (`idPermiso`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuario_permiso_usuario1` FOREIGN KEY (`nombreUsuario`) REFERENCES `usuario` (`nombreUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD CONSTRAINT `fk_vehiculo_modelo1` FOREIGN KEY (`idMarcaVehiculo`) REFERENCES `marca_vehiculo` (`idMarcaVehiculo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `vehiculo_orden`
--
ALTER TABLE `vehiculo_orden`
  ADD CONSTRAINT `fk_vehiculo_mantencion_cliente1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_vehiculo_mantencion_vehiculo` FOREIGN KEY (`idVehiculo`) REFERENCES `vehiculo` (`idVehiculo`) ON DELETE NO ACTION ON UPDATE NO ACTION;
