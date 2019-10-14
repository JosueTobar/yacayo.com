-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-09-2019 a las 16:47:50
-- Versión del servidor: 10.3.16-MariaDB
-- Versión de PHP: 7.3.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `yacayo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aplicacion`
--
use yacayo;

CREATE TABLE `aplicacion` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `publicaciones_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE `ciudad` (
  `id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `idProvincia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ciudad`
--

INSERT INTO `ciudad` (`id`, `nombre`, `idProvincia`) VALUES
(1, 'Ahuachapan', 1),
(2, 'Apaneca', 1),
(3, 'Atiquizaya', 1),
(4, 'Concepción de Ataco', 1),
(5, 'El Refugio', 1),
(6, 'Guaymango', 1),
(7, 'Jujutla', 1),
(8, 'San Francisco Menéndez', 1),
(9, 'San Lorenzo', 1),
(10, 'San Pedro Puxtla', 1),
(11, 'Tacuba', 1),
(12, 'Turín', 1),
(13, 'Candelaria de la Frontera', 2),
(14, 'Chalchuapa', 2),
(15, 'Coatepeque', 2),
(16, 'El Congo', 2),
(17, 'El Provenir', 2),
(18, 'Masahuat', 2),
(19, 'Metapan', 2),
(20, 'San Antonio Pajonal', 2),
(21, 'San Sebastian Salitrillo', 2),
(22, 'Santa Ana', 2),
(23, 'Santa Rosa Guachipilin', 2),
(24, 'Santiago de la Frontera', 2),
(25, 'Texistepeque', 2),
(26, 'Acajutla', 3),
(27, 'Armenia', 3),
(28, 'Caluco', 3),
(29, 'Cuisnahuat', 3),
(30, 'Izalco', 3),
(31, 'Juayua', 3),
(32, 'Nahuizalco', 3),
(33, 'Nahulingo', 3),
(34, 'Salcoatitan', 3),
(35, 'San Antonio del monte', 3),
(36, 'San Julian', 3),
(37, 'Santa Catarina Masahuat', 3),
(38, 'Santa Isabel Ishuat', 3),
(39, 'Santo Domingo de Guzman', 3),
(40, 'Sonsonate', 3),
(41, 'Sonzacate', 3),
(42, 'Agua Caliente', 4),
(43, 'Arcatao', 4),
(44, 'Azacualpa', 4),
(45, ' Cancasque San José Cancasque', 4),
(46, 'Chalatenango', 4),
(47, 'Citalá', 4),
(48, 'Comapala', 4),
(49, 'Concepción Quezaltepeque', 4),
(50, 'Dulce Nombre de María', 4),
(51, 'El Carrizal', 4),
(52, 'El Paraíso', 4),
(53, 'La Laguna', 4),
(54, 'La Palma', 4),
(55, 'La Reina', 4),
(56, 'Las Flores', 4),
(57, 'Las Vueltas', 4),
(58, 'Nombre de Jesús', 4),
(59, 'Nueva Concepción', 4),
(60, 'Nueva Trinidad', 4),
(61, 'Ojos de Agua', 4),
(62, 'Potonico', 4),
(63, 'San Antonio de la Cruz', 4),
(64, 'San Antonio Los Ranchos', 4),
(65, 'San Fernando', 4),
(66, 'San Francisco Lempa', 4),
(67, 'San Francisco Morazán', 4),
(68, 'San Ignacio', 4),
(69, 'San Isidro Labrador', 4),
(70, 'San Luis del Carmen', 4),
(71, 'San Miguel de Mercedes', 4),
(72, 'San Rafael', 4),
(73, 'Santa Rita', 4),
(74, 'Tejutla', 4),
(75, 'Candelaria', 5),
(76, 'Cojutepeque', 5),
(77, 'El Carmen', 5),
(78, 'El Rosario', 5),
(79, 'Monte San Juan', 5),
(80, 'Oratorio de Concepción', 5),
(81, 'San Bartolomé Perulapía', 5),
(82, 'San Cristóbal', 5),
(83, 'San José Guayabal', 5),
(84, 'San Pedro Perulapán', 5),
(85, 'San Rafael Cedros', 5),
(86, 'San Ramon', 5),
(87, 'Santa Cruz Analquito', 5),
(88, 'Santa Cruz Michapa', 5),
(89, 'Suchitoto', 5),
(90, 'Tenancingo', 5),
(91, 'Aguilares', 6),
(92, 'Apopa', 6),
(93, 'Ayutuxtepeque', 6),
(94, 'Cuscatancingo', 6),
(95, 'Ciudad Delgado', 6),
(96, 'El Paisnal', 6),
(97, 'Guazapa', 6),
(98, 'Ilopango', 6),
(99, 'Mejicanos', 6),
(100, 'Nejapa', 6),
(101, 'Panchimalco', 6),
(102, 'Rosario de Mora', 6),
(103, 'San Marcos', 6),
(104, 'San Martín', 6),
(105, 'San Salvador', 6),
(106, 'Santiago Texacuangos', 6),
(107, 'Santo Tomás', 6),
(108, 'Soyapango', 6),
(109, 'Tonacatepeque', 6),
(110, 'Antiguo Cuscatlán', 7),
(111, 'Chiltiupán', 7),
(112, 'Ciudad Arce', 7),
(113, 'Colón', 7),
(114, 'Comasagua', 7),
(115, 'Huizúcar', 7),
(116, 'Jayaque', 7),
(117, 'Jicalapa', 7),
(118, 'La Libertad', 7),
(119, 'Santa Tecla', 7),
(120, 'Nuevo Cuscatlán', 7),
(121, 'San Juan Opico', 7),
(122, 'Quezaltepeque', 7),
(123, 'Sacacoyo', 7),
(124, 'San José Villanueva', 7),
(125, 'San Matias', 7),
(126, 'San Pablo Tacachico', 7),
(127, 'Talnique', 7),
(128, 'Tamanique', 7),
(129, 'Teotepeque', 7),
(130, 'Tepecoyo', 7),
(131, 'Zaragoza', 7),
(132, 'Apastepeque', 8),
(133, 'Guadalupe', 8),
(134, 'San Cayetano Istepeque', 8),
(135, 'San Esteban Catarina', 8),
(136, 'San Ildefonso', 8),
(137, 'San Lorenzo', 8),
(138, 'San Sebastián', 8),
(139, 'San Vicente', 8),
(140, 'Santa Clara', 8),
(141, 'Santo Domingo', 8),
(142, 'Tecoluca', 8),
(143, 'Tepetitán', 8),
(144, 'Verapaz', 8),
(145, 'Cinquera', 9),
(146, 'Dolores', 9),
(147, 'Guacotecti', 9),
(148, 'Ilobasco', 9),
(149, 'Jutiapa', 9),
(150, 'San Isidro', 9),
(151, 'Sensuntepeque', 9),
(152, 'Tejutepeque', 9),
(153, 'Victoria', 9),
(154, 'Zacatecoluca', 10),
(155, 'Cuyultitán', 10),
(156, 'El Rosario', 10),
(157, 'Jerusalén', 10),
(158, 'Mercedes La Ceiba', 10),
(159, 'Olocuilta', 10),
(160, 'Paraíso de Osorio', 10),
(161, 'San Antonio Masahuat', 10),
(162, 'San Emigdio', 10),
(163, 'San Francisco Chinameca', 10),
(164, 'San Pedro Masahuat', 10),
(165, 'San Juan Nonualco', 10),
(166, 'San Juan Talpa', 10),
(167, 'San Juan Tepezontes', 10),
(168, 'San Luis La Herradura', 10),
(169, 'San Luis Talpa', 10),
(170, 'San Miguel Tepezontes', 10),
(171, 'San Pedro Nonualco', 10),
(172, 'San Rafael Obrajuelo', 10),
(173, 'Santa María Ostuma', 10),
(174, 'Santiago Nonualco', 10),
(175, 'Tapalhuaca', 10),
(176, 'Alegría', 11),
(177, 'Berlín', 11),
(178, 'California', 11),
(179, 'Concepción Batres', 11),
(180, 'El Triunfo', 11),
(181, 'Ereguayquín', 11),
(182, 'Estanzuelas', 11),
(183, 'Jiquilisco', 11),
(184, 'Jucuapa', 11),
(185, 'Jucuarán', 11),
(186, 'Mercedes Umaña', 11),
(187, 'Nueva Granada', 11),
(188, 'Ozatlán', 11),
(189, 'Puerto El Triunfo', 11),
(190, 'San Agustín', 11),
(191, 'San Buenaventura', 11),
(192, 'San Dionisio', 11),
(193, 'San Francisco Javier', 11),
(194, 'Santa Elena', 11),
(195, 'Santa María', 11),
(196, 'Santiago de María', 11),
(197, 'Tecapán', 11),
(198, 'Usulután', 11),
(199, 'Carolina', 12),
(200, 'Chapeltique', 12),
(201, 'Chinameca', 12),
(202, 'Chirilagua', 12),
(203, 'Ciudad Barrios', 12),
(204, 'Comacarán', 12),
(205, 'El Tránsito', 12),
(206, 'Lolotique', 12),
(207, 'Moncagua', 12),
(208, 'Nueva Guadalupe', 12),
(209, 'Nuevo Edén de San Juan', 12),
(210, 'Quelepa', 12),
(211, 'San Antonio del Mosco', 12),
(212, 'San Gerardo', 12),
(213, 'San Jorge', 12),
(214, 'San Luis de la Reina', 12),
(215, 'San Miguel', 12),
(216, 'San Rafael Oriente', 12),
(217, 'Sesori', 12),
(218, 'Uluazapa', 12),
(219, 'Arambala', 13),
(220, 'Cacaopera', 13),
(221, 'Chilanga', 13),
(222, 'Corinto', 13),
(223, 'Delicias de Concepción', 13),
(224, 'El Divisadero', 13),
(225, 'El Rosario', 13),
(226, 'Gualococti', 13),
(227, 'Guatajiagua', 13),
(228, 'Joateca', 13),
(229, 'Jocoaitique', 13),
(230, 'Jocoro', 13),
(231, 'Lolotiquillo', 13),
(232, 'Meanguera', 13),
(233, 'Osicala', 13),
(234, 'Perquín', 13),
(235, 'San Carlos', 13),
(236, 'San Fernando', 13),
(237, 'San Francisco Gotera', 13),
(238, 'San Isidro', 13),
(239, 'San Simón', 13),
(240, 'Sensembra', 13),
(241, 'Sociedad', 13),
(242, 'Torola', 13),
(243, 'Yamabal', 13),
(244, 'Yoloaiquín', 13),
(245, 'Anamorós', 14),
(246, 'Bolívar', 14),
(247, 'Concepción de Oriente', 14),
(248, 'Conchagua', 14),
(249, 'El Carmen', 14),
(250, 'El Sauce', 14),
(251, 'Intipucá', 14),
(252, 'La Unión', 14),
(253, 'Lislique', 14),
(254, 'Meanguera del Golfo', 14),
(255, 'Nueva Esparta', 14),
(256, 'Pasaquina', 14),
(257, 'Polorós', 14),
(258, 'San Alejo', 14),
(259, 'San José', 14),
(260, 'Santa Rosa de Lima', 14),
(261, 'Yayantique', 14),
(262, 'Yucuaiquín', 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_aplicacion`
--

CREATE TABLE `detalle_aplicacion` (
  `id` int(11) NOT NULL,
  `pretencion` decimal(11,2) DEFAULT NULL,
  `aplicacion_id` int(11) NOT NULL,
  `documento_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direccion`
--

CREATE TABLE `direccion` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `idCiudad` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `documento`
--

CREATE TABLE `documento` (
  `id` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idDocumento` int(11) NOT NULL,
  `ruta` varchar(255) NOT NULL,
  `estado` enum('Activo','Inactivo') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `email`
--

CREATE TABLE `email` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE `pais` (
  `id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pais`
--

INSERT INTO `pais` (`id`, `nombre`) VALUES
(1, 'El Salvador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provincia`
--

CREATE TABLE `provincia` (
  `id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `idPais` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `provincia`
--

INSERT INTO `provincia` (`id`, `nombre`, `idPais`) VALUES
(1, 'Ahuachapan', 1),
(2, 'Santa Ana', 1),
(3, 'Sonsonate', 1),
(4, 'Chalatenango', 1),
(5, 'Cuscatlan', 1),
(6, 'San Salvador', 1),
(7, 'La Libertad', 1),
(8, 'San Vicente', 1),
(9, 'Cabañas', 1),
(10, 'La Paz', 1),
(11, 'Usulutun', 1),
(12, 'San Miguel', 1),
(13, 'Morazon', 1),
(14, 'La Union', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicaciones`
--

CREATE TABLE `publicaciones` (
  `id` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idRubro` int(11) NOT NULL,
  `vacantes` int(11) DEFAULT NULL,
  `titulo` varchar(100) NOT NULL,
  `fecha_publicacion` date NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  `requerimientos` text NOT NULL,
  `descripcion` text NOT NULL,
  `estado` enum('Activo','Inactivo') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `publicaciones`
--

INSERT INTO `publicaciones` (`id`, `idUsuario`, `idRubro`, `vacantes`, `titulo`, `fecha_publicacion`, `fecha_vencimiento`, `requerimientos`, `descripcion`, `estado`) VALUES
(1, 3, 7, 5, 'PROGRAMADOR JAVA', '2019-09-11', '2019-10-06', '+ Estudiante o egresado de la carrera ing en sistemas o carreras afines.\r\n+ Edad entre 21 a 30 años\r\n+ Conocimientos básicos java\r\n+ Disposición trabajar en equipo.\r\n\r\nOFRECEMOS\r\n+ Crecimiento laboral\r\n+ Excelente ambiente laboral.\r\n+ Viáticos.', 'Área de la Empresa	Servicios\r\n\r\nCargo Solicitado	Analista | Programador\r\n\r\nTipo de Contratación	Tiempo completo\r\n\r\nNivel de Experiencia: sin experiencia\r\n\r\nGénero	Indiferente\r\n\r\nEdad	21 / 30\r\n\r\nSalario máximo (USD)	600\r\n\r\nSalario minimo (USD)	300\r\n\r\nPaís	El Salvador\r\n', 'Activo'),
(2, 2, 1, 2, 'GERENTE DE COMPRAS', '2019-09-11', '2019-10-19', '\r\nGerente de Compras\r\n(Opcional)\r\nEDUCACIÓN SUPERIOR\r\nAdm. de Empresas | Ing. Comercial\r\n(Opcional)	Universidad Completa | Graduado', 'Cargo Solicitado: Gerente de Compras\r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: de tres a cinco años\r\nGénero	Indiferente\r\nEdad	18 / 70\r\nSalario máximo (USD): 1700\r\nSalario minimo (USD): 1700\r\nVehículo	Requerido\r\nPaís	El Salvador\r\nFunciones:\r\n- Análisis de planificación, análisis de consumo, enfoque en producción, abastecimiento y planificación.\r\n- Negociación con proveedores nacionales e internacionales.\r\n- Experiencia laboral como Gerente de Compras.\r\n', 'Activo'),
(3, 4, 1, 5, 'EJECUTIVO DE SERVICIO AL CLIENTE', '2019-09-11', '2019-11-23', 'Atencion al cliente via telefonica\r\nsin problemeas de horario\r\nEXPERIENCIA REQUERIDA\r\nEjecutiv@ de Afiliación | Tarjeta de Crédito\r\n(Opcional)\r\nEjecutiv@ de Call Center\r\n(Opcional)\r\nEjecutiv@ de Servicio al Cliente\r\n(Opcional)\r\nEDUCACIÓN SECUNDARIA\r\nBachiller General\r\n(Opcional)\r\nBachiller Contable\r\n(Opcional)\r\nBachiller en Admón. de Empresas\r\n(Opcional)\r\nBachiller en Comercio\r\n(Opcional)', 'Ejecutiv@ de Servicio al Cliente\r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: de uno a tres años\r\nGénero	Indiferente\r\nEdad	20 / 30\r\nSalario máximo (USD)	\r\nSalario minimo (USD): 350\r\nVehículo	Indiferente\r\nPaís	El Salvador\r\n', 'Activo'),
(4, 1, 4, 3, 'CAJER@', '2019-09-11', '2019-10-31', '-ORIENTADO AL SERVICIO AL CLIENTE\r\n-RESPONSABLE\r\n-ACOSTUMBRADO AL MANEJO DE EFECTIVO O CAJA\r\n-DE PREFERENCIA BACHILLER COMERCIAL\r\nSE OFRECE: ESTABILIDAD LABORAL, PRESTACIONES DE LEY Y EXTRAS A LAS DE LEY, ENTRE OTROS', 'Área de la Empresa	Servicios\r\nCargo Solicitado	Cajer@\r\nPuestos Vacantes	1\r\nTipo de Contratación	Tiempo completo\r\nNivel de Experiencia	de uno a tres años\r\nGénero	Indiferente\r\nEdad	20 / 35\r\nSalario máximo (USD)	\r\nSalario minimo (USD)	304.17\r\nVehículo	Indiferente\r\nPaís El Salvador\r\n', 'Activo'),
(5, 2, 1, 2, 'AGENTE CENTRO DE SOPORTE LIFEMILES', '2019-09-11', '2019-10-08', 'Mínimo 2 años de experiencia en Servicio al Cliente\r\nEstudiante Universitario de 3er año en adelante en carreras como Administración de Empresas o afines\r\nOrientación al Servicio al Cliente\r\nAlto manejo de Office y Sistemas en ambiente Web\r\nConocimientos de uso ARD\r\nIngles: intermedio', 'Cargo Solicitado: Agente de Servicio\r\n\r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: menos de un año\r\nGénero	Indiferente\r\nEdad	18 / 35\r\nSalario máximo (USD)	\r\nSalario minimo (USD)	\r\nVehículo	Indiferente\r\nPaís	El Salvador\r\n\r\nEsta posición será responsable de resolver los casos que requieran soporte de segundo nivel para áreas internas de LifeMiles y aliados comerciales en lo referente a procesos y procedimientos de programa de lealtad, dando solución a las solicitudes recibidas, cumpliendo las métricas del área en cuanto a tiempos de respuesta y calidad.\r\n\r\nResponsabilidades\r\nResolver solicitudes, inquietudes o reclamos de las áreas internas o de los aliados en lo referente al programa de Lealtad, esto puede ser vía llamada o correo electrónico\r\nBuscar oportunidades de automatización de procesos actuales y/o creación de nuevos procesos, creación o modificación a procedimientos en el departamento.\r\nIdentificar y reportar las posibles fallas del sistema o del cumplimiento de las políticas del programa para que sea resuelto por las áreas encargadas\r\nDar retroalimentación directa a las áreas internas y/o aliados cuando sea necesario para reforzar el correcto cumplimiento de las políticas del Programa de Lealtad.\r\n', 'Activo'),
(6, 2, 3, 6, 'CAJEROS SIN EXPERIENCIA ', '2019-09-11', '2019-09-21', '- Edad entre 22 y 35 años.\r\n- Sexo Masculino\r\n- Bachiller\r\n- Con flexibilidad de Horarios.\r\n- Habilidad numérica\r\n- Manejo de paquetes utilitarios Word, Excel\r\n- Manejo de Equipo de oficina. (contometro).\r\n\r\nCOMPETENCIAS:\r\n- Responsable, organizado, capacidad para trabajo en equipo, capacidad para resolver problemas, buenas relaciones interpersonales, buena presentación.', '\r\nCargo Solicitado: Cajer@\r\n\r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: sin experiencia\r\nGénero: Masculino\r\nEdad	21 / 35\r\nSalario máximo (USD)	\r\nSalario minimo (USD)	\r\nPaís	El Salvador', 'Activo'),
(7, 4, 19, 3, 'PUBLICACIONES TÉCNICAS', '2019-09-11', '2019-11-10', '- Estudiante de 3er año Ingeniería Industrial, Licenciatura en Administración de Empresas o carrera afín.\r\n- 3 años de experiencia en soporte administrativo y manejo de manuales regulatorios y de mantenimiento (Deseable)\r\n- Manejo de Microsoft Office y Adobe Intermedio\r\n- Nivel de inglés INTERMEDIO (Requerido)\r\n- Habilidad de comunicación efectiva, capacidad de análisis, mejora continua, trabajo en equipo en funciones operativas y orientación a resultados.\r\n- Disponibilidad de trabajar en turnos rotativos (Indispensable)\r\n\r\nEXPERIENCIA REQUERIDA\r\nAsistente Administrativo\r\nRequerido\r\nEDUCACIÓN SUPERIOR\r\nAdm. de Empresas | Ing. Comercial\r\n(Opcional)	Estudiante Universitario (3er Año)\r\nIndustrial\r\n(Opcional)	Estudiante Universitario (3er Año)\r\nLENGUAJES\r\nInglés\r\nRequerido	Intermedio', 'Cargo Solicitado: Asistente Administrativo. \r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: de uno a tres años\r\nGénero	Indiferente\r\nEdad: 21 / 70\r\nSalario máximo (USD)	\r\nSalario minimo (USD): 1\r\nVehículo	Indiferente\r\nPaís	El Salvador\r\nFunción principal:\r\nControlar y mantener actualizada la información técnica utilizada para proveer soporte a los trabajos de mantenimiento brindados a las aeronaves de nuestros clientes y a todas las operaciones de Aeroman. Mantener contacto con fabricantes, operadores u otros talleres de mantenimiento para asegurar que la información sea la más reciente, así como custodiar manuales, licencias y certificados de las aeronaves para el cumplimiento de las regulaciones y objetivos del negocio. También será responsable por gestionar y dar seguimiento a órdenes de compra de documentación según se requiera.', 'Activo'),
(8, 1, 1, 1, 'GERENTE DE PROYECTOS', '2019-09-12', '2019-11-14', '\r\nGerente de Obras Civiles\r\nRequerido\r\nEDUCACIÓN SUPERIOR\r\nCivil | Obras Civiles\r\n(Opcional)	Universidad Completa | Graduado\r\nArquitectura\r\n(Opcional)	Universidad Completa | Graduado', 'Empresa de construcción solicita Gerente de Proyectos:\r\n-Graduado de Ing. Civil o Arquitectura\r\n-Cinco años de experiencia en puestos similares en proyectos de construcción\r\n-Experiencia en presupuestos de obra, programación y revisiones periódicas del cronograma, suministros con especificaciones técnicas del proyecto, administración de obra, supervisión física de la obra.\r\n-Experiencia en manejo de personal', 'Activo'),
(9, 2, 7, 3, 'DESARROLLADOR IOS', '2019-09-12', '2019-10-18', '-5to año o Graduado de la carrera Ingeniería en Sistemas, Licenciatura en Informática o carreras afines.\r\n-Edad entre 21 a 35 años.\r\n-Experiencia en aplicaciones móviles\r\n-Creatividad\r\n-Conocimientos de Swift\r\n-Conocimiento de Objective – C\r\n-Experiencia en SQL y base de datos no relacionales\r\n\r\nSe Ofrece:\r\n-Prestaciones de Ley\r\n-Estabilidad Laboral\r\n-Crecimiento Profesional', 'Cargo Solicitado: Analista | Programador\r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: de uno a tres años\r\nGénero: Indiferente\r\nEdad: 22 / 35	\r\nPaís	El Salvador\r\n', 'Activo'),
(10, 3, 1, 1, 'RECEPCIONISTA', '2019-09-12', '2019-09-26', 'Experiencia como recepcionista.\r\nExperiencia de 1 a 2 años\r\nEdad: 20-35 años\r\nResidencia San Salvador', 'Cargo Solicitado: Recepcionista\r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: de uno a tres años\r\nGénero: Femenino\r\nEdad: 20 / 35	\r\nVehículo	Indiferente\r\nPaís	El Salvador', 'Activo'),
(11, 4, 1, 5, 'EJECUTIVO DE PUNTO DE VENTA', '2019-09-12', '2019-10-02', '-Experiencia en el área de Ventas\r\n-Disponibilidad de horarios\r\n-Facilidad de expresión\r\n-Disponibilidad para trasladarse a diferentes supermercados\r\n\r\nOfrecemos:\r\n-Salario base\r\n-Comisiones (por metas)\r\n-Prestaciones de ley (AFP-ISSS)', 'Cargo Solicitado: Ejecutiv@ de Ventas\r\nTipo de Contratación	Tiempo completo\r\nNivel de Experiencia: de uno a tres años\r\nGénero: Indiferente\r\nEdad: 18 / 60\r\nSalario minimo (USD): 310\r\nVehículo	Indiferente\r\nPaís	El Salvador', 'Activo'),
(12, 4, 2, 1, 'INGENIERO', '2019-09-12', '2019-09-22', '-Ingeniero Civil o Arquitecto\r\n-Manejo de sistemas de cómputo: Microsoft Office, Microsoft Project, Autocad, Opus\r\n-Conocimientos de formulación y evaluación de proyectos\r\n-Técnicas de facilitación\r\n-5 años de experiencia en puestos similares\r\n-Experiencia en Construcción\r\n-Experiencia en presupuestos, programaciones, suministros y administración de obra\r\nEXPERIENCIA REQUERIDA\r\nIngeniero Civil\r\nEDUCACIÓN SUPERIOR\r\nCivil | Obras Civiles\r\n(Opcional)	Universidad Completa | Graduado\r\nArquitectura\r\n(Opcional)	Universidad Completa | Graduado', 'Cargo Solicitado: Ingeniero Civil\r\n\r\nTipo de Contratación: Cualquier tipo de contratación\r\nNivel de Experiencia: de cinco a diez años\r\nGénero: Indiferente\r\nEdad: 35 / 45	\r\nPaís	El Salvador', 'Activo'),
(13, 2, 1, 2, 'SUPERVISOR DE RESTAURANTE O GERENTE DE RESTAURANTE', '2019-09-12', '2019-09-23', 'Supervición de las sucursales encargadas, manejo y análisis de inventario, planificación de ventas, administración de personal, servicio al cliente, cortes de caja y arqueos de caja.', 'Cargo Solicitado: Gerente de Restaurante\r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: de tres a cinco años\r\nGénero: Indiferente\r\nEdad: 27 / 35\r\nSalario máximo (USD): 550\r\nSalario minimo (USD): 550\r\n\r\nPaís	El Salvador', 'Activo'),
(14, 4, 1, 3, 'GESTOR DE COBRO TELEFONICO', '2019-09-12', '2019-09-28', 'Gestor de Cobros', '• Realizar la cobranza de las cuentas asignadas, registrando en el sistema de cobranza el resultado de sus gestiones.• Verificar y clasificar el tipo de cliente con el que establece contacto y de acuerdo a su historial crediticio, gestiona la cuenta utilizando como herramienta el proceso de cobranza establecido.\r\n• identificar los posibles problemas o circunstancias por las que el cliente ha dejado de pagar, proponiendo esquemas de solución adecuados a la circunstancia.\r\n• Verificar datos telefónicos y demográficos de los clientes. Actualizar nuevos\r\nteléfonos, correo electrónico y datos demográficos de los clientes y capturarlos en los lugares específicos del sistema de cobranza.\r\n• Actuar de manera rápida y eficaz pare identificar el riesgo asociado en todas las cuentas asignadas a la unidad, asegurando rápida acción pare minimizar perdidas y asegurar la recuperación\r\n• Efectuar una eficaz y oportuna actividad vía teléfono, o cualquier otro medo\r\nApropiado, realizando un use efectivo del sistema de resultados inmediatos pare facilitar una rápida resolución y/o finalización de las cuentas en mora\r\n• Mantener integridad y confidencialidad de información de la empresa y de los clientes.\r\n• Atender recomendaciones durante las sesiones de retroalimentación con el Team Leader.\r\n• Realizar todas sus funciones con estricto apego a las pautas de conducta institucionales.', 'Activo'),
(15, 3, 4, 2, 'ANALISTA DE CRÉDITOS Y COBROS', '2019-09-12', '2019-09-28', '2 años de experiencia en puestos similares.\r\nEXPERIENCIA REQUERIDA\r\n- Graduado de Administración de empresas, mercadeo internacional.\r\n- Excel intermedio avanzado.\r\nAsistente de Crédito y Cobro\r\n(Opcional)\r\nEDUCACIÓN SUPERIOR\r\nAdm. de Empresas | Ing. Comercial\r\n(Opcional)	Universidad Completa | Graduado\r\nContabilidad | Auditoría\r\n(Opcional)	Universidad Completa | Graduado\r\nMarketing | Mercadotecnia\r\n(Opcional)	Universidad Completa | Graduado', 'Recordatorios de pagos a clientes, reporte de cobros, proyecciones de los mismos, asignación de ruta a cobradores, liquidación de cobros diarios.\r\n- Experiencia en recuperación de cuentas morosas.', 'Activo'),
(16, 2, 7, 3, 'ANALISTA DE ALIADOS AÉREOS LIFEMILES', '2019-09-12', '2019-09-23', 'Universitario graduado de Administración de Empresas, Economía, Ingeniería Industrial o carreras afines\r\nInglés avanzado\r\nManejo avanzado de Microsoft Excel, Power Point y herramientas de Microsoft Office\r\nDeseable experiencia de 1 año en puestos similares\r\nPensamiento analítico, orientación al detalle, autodidacto, proactivo, alto enfoque en resultados, organizado\r\nLENGUAJES\r\nInglés\r\nRequerido	Avanzado', 'Elaboración y seguimiento de planes de trabajo relacionados con la negociación e implementación de acuerdos de viajero frecuente\r\nConfiguración y actualización de reglas de negocio de acuerdos, tanto en plataformas tecnológicas como en comunicaciones internas y externas\r\nMonitoreo y manejo de métricas e identificación de tendencias con el fin de asegurar el cumplimiento de requerimientos de la alianza\r\nComunicación con aliados para resolución de casos, benchmarks, y coordinación de pruebas o cambios en acuerdos de viajero frecuente\r\nSoporte en resolución de casos de servicio al cliente relacionados a la acumulación o redención en aliados aéreos\r\nAnálisis, definición y coordinación de implementación de mejoras o correcciones a los procesos tecnológicos', 'Activo'),
(17, 1, 1, 3, 'MOTORISTA DE PAQUETERIA ', '2019-09-12', '2019-09-28', 'Experiencia en el manejo de vehículos pesados 3 años o más\r\nEstudios de sexto a noveno grado\r\nConocimiento de nomenclatura de San Salvador', 'Cargo Solicitado: Chofer\r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: menos de un año\r\nGénero: Masculino\r\nEdad: 26 / 49\r\nPaís	El Salvador', 'Activo'),
(18, 2, 7, 2, 'PROGRAMADOR IT', '2019-09-12', '2019-09-28', '- Graduado de Licenciatura o Ingeniería en computación o carrera a fin\r\n- Maestría en Administración de Empresas o Post Grado en Comunicaciones (DESEABLE)\r\n- Experiencia de 2 años en desarrollo de aplicaciones Administración de personal\r\n- Ingles INTERMEDIO\r\n- Conocimientos sólidos en SQL, PL SQL, Java (Web: JSP, JavaScript, Strut), RPG 400, Visual Basic 6.0, Visual .NET, Base de datos y Developer Suite (Report & Form)\r\n- Competencias desarrolladas para el trabajo en equipo, orientación a resultados, toma de decisiones, planificación y organización.\r\n\r\nEXPERIENCIA REQUERIDA\r\nAnalista | Programador\r\n(Opcional)\r\nProgramador\r\n(Opcional)\r\nEDUCACIÓN SUPERIOR\r\nComputación\r\nRequerido	Universidad Completa | Graduado\r\nLENGUAJES\r\nInglés\r\n(Opcional)	Intermedio', 'Cargo Solicitado: Programador\r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: uno a tres años\r\nGénero: Indiferente\r\nEdad: 26 / 70\r\nPaís	Hondura', 'Activo'),
(19, 3, 18, 5, 'ENCUESTADOR', '2019-09-12', '2019-11-04', '- Sexo masculino (preferiblemente soltero o que no tenga inconvenientes para viajar).\r\n- Estudios a nivel de 2° año en Mercadeo, Administración de Empresas o afines (no estar estudiando actualmente).\r\n- Poseer experiencia laboral realizando trabajo de campo al 100% (por ejemplo: encuestador, Vendedor, Display, etc.), sin dificultades para desplazarse en todas las zonas del País.\r\n- Disponibilidad para viajar a Centroamérica consecutivamente y por periodos largos (un mes aproximadamente)\r\n- Poseer disponibilidad de tiempo.\r\n- Manejo de Microsoft Excel básico-intermedio.\r\n- Cualidades: alta persuasión para lograr información efectiva, facilidad de expresión, tolerancia a la frustración, capacidad para trabajar en equipo, facilidad de adaptación.\r\n\r\nEXPERIENCIA REQUERIDA\r\nEncuestador\r\n(Opcional)\r\nVendedor\r\n(Opcional)\r\nEDUCACIÓN SUPERIOR\r\nAdm. de Empresas | Ing. Comercial\r\n(Opcional)	Estudiante Universitario (2do Año)\r\nMarketing | Mercadotecnia\r\n(Opcional)	Estudiante Universitario (2do Año)', 'Cargo Solicitado: Analista de Mercadeo | Investigación de Mercados\r\nTipo de Contratación: Tiempo completo\r\nNivel de Experiencia: uno a tres años\r\nGénero: Masculino\r\nEdad: 24 / 38\r\nPaís: El Salvador', 'Activo'),
(20, 4, 1, 2, 'PASANTE DE INSPECCIÓN DE CALIDAD', '2019-09-12', '2019-11-04', '- Estudiante de Técnico en Laboratorio Químico / Técnico en Química Industrial / Bachiller con experiencia en Control de Calidad\r\n- Experiencia en uso de equipos: pH-metro, Balanza analítica, Desintegrador, Friabilizador, Durometro y Espectrofometro, UV-Visible.\r\n\r\nConocimientos:\r\n- Muestreo de material, materias primas, producto semiterminado y terminado\r\n- Dictamen de materiales\r\n- Inspección de calidad de producto terminado\r\n\r\nHabilidades:\r\n- Orientación a resultados\r\n- Autogestión\r\n- Adaptabilidad\r\n- Espíritu de equipo\r\n- Empatía\r\n- Mejora continua\r\nEDUCACIÓN SUPERIOR\r\nQuímica\r\nRequerido	Diploma Técnico Incompleto\r\nQuímica\r\nRequerido	Estudiante Universitario (3er Año)', 'Cargo Solicitado: Asistente de Control de Calidad\r\nTipo de Contratación: Pasantía\r\nNivel de Experiencia: sin experiencia\r\nGénero: Masculino\r\nEdad: 21 / 25\r\nPaís	El Salvador', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rubros`
--

CREATE TABLE `rubros` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `estado` enum('Activo','Inactivo') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rubros`
--

INSERT INTO `rubros` (`id`, `descripcion`, `estado`) VALUES
(1, 'Informatica', 'Activo'),
(2, 'Servicios', 'Activo'),
(3, 'Industria', 'Activo'),
(4, 'Comercio', 'Activo'),
(5, 'Bancos/Financieras', 'Activo'),
(6, 'Hoteleria/turismos/restuarantes', 'Activo'),
(7, 'Agencia de Reclutamiento', 'Activo'),
(8, 'Informatica', 'Activo'),
(9, 'Comercio Minorista', 'Activo'),
(10, 'Call Center', 'Activo'),
(11, 'Farmacéutica', 'Activo'),
(12, 'Telecomunicaciones', 'Activo'),
(13, 'Logistica/Distrubución', 'Activo'),
(14, 'Comercio Mayorista', 'Activo'),
(15, 'Automotriz', 'Activo'),
(16, 'Agroindustria', 'Activo'),
(17, 'Mantenimiento', 'Activo'),
(18, 'Administrativo', 'Activo'),
(19, 'Logistica', 'Activo'),
(20, 'Publicidad', 'Activo'),
(21, 'Recursos Humanos', 'Activo'),
(22, 'Salud', 'Activo'),
(23, 'Varios', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `telefono`
--

CREATE TABLE `telefono` (
  `id` int(11) NOT NULL,
  `numero` varchar(20) DEFAULT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_documento`
--

CREATE TABLE `tipo_documento` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `estado` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_usuario`
--

CREATE TABLE `tipo_usuario` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(70) NOT NULL,
  `estado` enum('Activo','Inactivo') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipo_usuario`
--

INSERT INTO `tipo_usuario` (`id`, `descripcion`, `estado`) VALUES
(1, 'Administrador', 'Activo'),
(2, 'Empresa', 'Activo'),
(3, 'Postulante', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `idTipo` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `clave` varchar(255) NOT NULL,
  `estado` enum('Activo','Inactivo') DEFAULT NULL,
  `fecha` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `idTipo`, `nombre`, `email`, `clave`, `estado`, `fecha`) VALUES
(1, 2, 'Forza', 'amilcar.flores@proyectosfgk.org', '1234', 'Activo', '2015-06-17'),
(2, 2, 'Global Oustours', 'josue.tobar@proyectosfgk.org', '1234', 'Activo', '2013-08-18'),
(3, 2, 'Urbano', 'jennifer.campos@proyectosfgk.org', '1234', 'Activo', '2011-05-11'),
(4, 2, 'Avianca', 'david.cordova@proyectosfgk.org', '1234', 'Activo', '2009-01-31');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aplicacion`
--
ALTER TABLE `aplicacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_aplicacion_usuario1` (`usuario_id`),
  ADD KEY `fk_aplicacion_publicaciones1` (`publicaciones_id`);

--
-- Indices de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ciudad_ibfk_1` (`idProvincia`);

--
-- Indices de la tabla `detalle_aplicacion`
--
ALTER TABLE `detalle_aplicacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_detalle_aplicacion_aplicacion1` (`aplicacion_id`),
  ADD KEY `fk_detalle_aplicacion_documento1` (`documento_id`);

--
-- Indices de la tabla `direccion`
--
ALTER TABLE `direccion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `direccion_ibfk_1` (`idCiudad`),
  ADD KEY `fk_direccion_usuario1` (`usuario_id`);

--
-- Indices de la tabla `documento`
--
ALTER TABLE `documento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `documento_ibfk_1` (`idUsuario`),
  ADD KEY `documento_ibfk_2` (`idDocumento`);

--
-- Indices de la tabla `email`
--
ALTER TABLE `email`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_email_usuario1` (`id_usuario`);

--
-- Indices de la tabla `pais`
--
ALTER TABLE `pais`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `provincia`
--
ALTER TABLE `provincia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `departamento_ibfk_1` (`idPais`);

--
-- Indices de la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `publicaciones_ibfk_1` (`idUsuario`),
  ADD KEY `publicaciones_ibfk_2` (`idRubro`);

--
-- Indices de la tabla `rubros`
--
ALTER TABLE `rubros`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `telefono`
--
ALTER TABLE `telefono`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_telefono_usuario1` (`id_usuario`);

--
-- Indices de la tabla `tipo_documento`
--
ALTER TABLE `tipo_documento`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipo_usuario`
--
ALTER TABLE `tipo_usuario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `usuario_ibfk_1` (`idTipo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aplicacion`
--
ALTER TABLE `aplicacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=263;

--
-- AUTO_INCREMENT de la tabla `detalle_aplicacion`
--
ALTER TABLE `detalle_aplicacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `direccion`
--
ALTER TABLE `direccion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `documento`
--
ALTER TABLE `documento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `email`
--
ALTER TABLE `email`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pais`
--
ALTER TABLE `pais`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `provincia`
--
ALTER TABLE `provincia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `rubros`
--
ALTER TABLE `rubros`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `telefono`
--
ALTER TABLE `telefono`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipo_documento`
--
ALTER TABLE `tipo_documento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipo_usuario`
--
ALTER TABLE `tipo_usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `aplicacion`
--
ALTER TABLE `aplicacion`
  ADD CONSTRAINT `fk_aplicacion_publicaciones1` FOREIGN KEY (`publicaciones_id`) REFERENCES `publicaciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_aplicacion_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD CONSTRAINT `ciudad_ibfk_1` FOREIGN KEY (`idProvincia`) REFERENCES `provincia` (`id`);

--
-- Filtros para la tabla `detalle_aplicacion`
--
ALTER TABLE `detalle_aplicacion`
  ADD CONSTRAINT `fk_detalle_aplicacion_aplicacion1` FOREIGN KEY (`aplicacion_id`) REFERENCES `aplicacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_detalle_aplicacion_documento1` FOREIGN KEY (`documento_id`) REFERENCES `documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `direccion`
--
ALTER TABLE `direccion`
  ADD CONSTRAINT `direccion_ibfk_1` FOREIGN KEY (`idCiudad`) REFERENCES `ciudad` (`id`),
  ADD CONSTRAINT `fk_direccion_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `documento`
--
ALTER TABLE `documento`
  ADD CONSTRAINT `documento_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `documento_ibfk_2` FOREIGN KEY (`idDocumento`) REFERENCES `tipo_documento` (`id`);

--
-- Filtros para la tabla `email`
--
ALTER TABLE `email`
  ADD CONSTRAINT `fk_email_usuario1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `provincia`
--
ALTER TABLE `provincia`
  ADD CONSTRAINT `departamento_ibfk_1` FOREIGN KEY (`idPais`) REFERENCES `pais` (`id`);

--
-- Filtros para la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  ADD CONSTRAINT `publicaciones_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `publicaciones_ibfk_2` FOREIGN KEY (`idRubro`) REFERENCES `rubros` (`id`);

--
-- Filtros para la tabla `telefono`
--
ALTER TABLE `telefono`
  ADD CONSTRAINT `fk_telefono_usuario1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idTipo`) REFERENCES `tipo_usuario` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

SELECT * FROM usuario;

SELECT p.* from publicaciones p WHERE p.id NOT IN (SELECT a.publicaciones_id FROM aplicacion a WHERE a.usuario_id = 1);
SELECT p.* from publicaciones p;
SELECT * from aplicacion;


-- SELECT p FROM Publicaciones p WHERE p.Id NOT IN (SELECT a.publicacionesId FROM Aplicacion a WHERE a.usuarioId = 1);


SELECT p.id, u.nombre, u.email, r.descripcion rubro, p.vacantes, p.titulo, p.fecha_vencimiento, p.requerimientos, p.descripcion  
from publicaciones p 
INNER JOIN usuario u ON p.idUsuario = u.id
INNER JOIN rubros r ON p.idRubro = r.id
WHERE p.id NOT IN (SELECT a.publicaciones_id FROM aplicacion a WHERE a.usuario_id = 1)



