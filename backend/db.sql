
-- ---
-- Table 'usuarios'
-- 
-- ---

DROP TABLE IF EXISTS `usuarios`;
		
CREATE TABLE `usuarios` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(64) NOT NULL,
  `apellido` VARCHAR(64) NOT NULL,
  `email` VARCHAR(128) NOT NULL,
  `usuario` VARCHAR(32) NOT NULL,
  `clave` VARCHAR(64) NOT NULL,
  `pais` VARCHAR(128) NOT NULL,
  `nivel` INT NOT NULL,
  `puntos` INT NOT NULL,
  `fecha_inscripcion` TIMESTAMP NOT NULL,
  `email_verificado` bit NOT NULL,
  PRIMARY KEY (`id_usuario`)
);

-- ---
-- Table 'juegos'
-- 
-- ---

DROP TABLE IF EXISTS `juegos`;
		
CREATE TABLE `juegos` (
  `id_juego` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(128) NOT NULL,
  `descripcion` MEDIUMTEXT NOT NULL,
  `genero` VARCHAR(64) NOT NULL,
  `tipo` VARCHAR(128) NOT NULL,
  `desarrollador` VARCHAR(256) NOT NULL,
  `fecha_lanzamiento` DATE NOT NULL,
  `id_requisito` INT NOT NULL,
  `id_admin_creado` INT NOT NULL,
  `analisis_positivos` INT NOT NULL,
  `analisis_negativos` INT NOT NULL,
  PRIMARY KEY (`id_juego`),
KEY (`id_admin_creado`, `id_requisito`)
);

-- ---
-- Table 'analisis'
-- 
-- ---

DROP TABLE IF EXISTS `analisis`;
		
CREATE TABLE `analisis` (
  `id_analisis` INT NOT NULL AUTO_INCREMENT,
  `analisis` MEDIUMTEXT NOT NULL,
  `valoracion` bit NOT NULL,
  `fecha_publicacion` TIMESTAMP NOT NULL,
  `id_juego` INT NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_analisis`),
KEY (`id_juego`, `id_usuario`)
);

-- ---
-- Table 'imagenes'
-- 
-- ---

DROP TABLE IF EXISTS `imagenes`;
		
CREATE TABLE `imagenes` (
  `id_imagen` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(256) NOT NULL,
  `imagen` BLOB NOT NULL,
  `imagen_checksum` BINARY(20) NOT NULL DEFAULT 'NUL',
  `id_juego` INT NOT NULL,
  `id_admin_subido` INT NOT NULL,
  `fecha_subida` DATE NOT NULL,
  PRIMARY KEY (`id_imagen`),
KEY (`id_juego`, `id_admin_subido`)
);

-- ---
-- Table 'admin'
-- 
-- ---

DROP TABLE IF EXISTS `admin`;
		
CREATE TABLE `admin` (
  `id_admin` INT NOT NULL AUTO_INCREMENT,
  `usuario` VARCHAR(64) NOT NULL,
  `clave` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id_admin`)
);

-- ---
-- Table 'privilegios'
-- 
-- ---

DROP TABLE IF EXISTS `privilegios`;
		
CREATE TABLE `privilegios` (
  `id_privilegio` INT NOT NULL AUTO_INCREMENT,
  `privilegio` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`id_privilegio`)
);

-- ---
-- Table 'privilegios_admin'
-- 
-- ---

DROP TABLE IF EXISTS `privilegios_admin`;
		
CREATE TABLE `privilegios_admin` (
  `id_privilegio` INT NOT NULL,
  `id_admin` INT NOT NULL,
  PRIMARY KEY (`id_privilegio`, `id_admin`)
);

-- ---
-- Table 'tag'
-- 
-- ---

DROP TABLE IF EXISTS `tag`;
		
CREATE TABLE `tag` (
  `id_tag` INT NOT NULL AUTO_INCREMENT,
  `etiqueta` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id_tag`)
);

-- ---
-- Table 'juegos_tag'
-- 
-- ---

DROP TABLE IF EXISTS `juegos_tag`;
		
CREATE TABLE `juegos_tag` (
  `id_juego` INT NOT NULL,
  `id_tag` INT NOT NULL,
  PRIMARY KEY (`id_juego`, `id_tag`)
);

-- ---
-- Table 'modos'
-- 
-- ---

DROP TABLE IF EXISTS `modos`;
		
CREATE TABLE `modos` (
  `id_modo` INT NOT NULL AUTO_INCREMENT,
  `modo` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id_modo`)
);

-- ---
-- Table 'juegos_modos'
-- 
-- ---

DROP TABLE IF EXISTS `juegos_modos`;
		
CREATE TABLE `juegos_modos` (
  `id_juego` INT NOT NULL,
  `id_modo` INT NOT NULL,
  PRIMARY KEY (`id_juego`, `id_modo`)
);

-- ---
-- Table 'noticias'
-- 
-- ---

DROP TABLE IF EXISTS `noticias`;
		
CREATE TABLE `noticias` (
  `id_noticia` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(256) NOT NULL,
  `descripcion` VARCHAR(2048) NOT NULL,
  `cuerpo` MEDIUMTEXT NOT NULL,
  `fecha_publicacion` DATE NOT NULL,
  `id_admin_creado` INT NOT NULL,
  PRIMARY KEY (`id_noticia`),
KEY (`id_admin_creado`)
);

-- ---
-- Table 'noticias_imagenes'
-- 
-- ---

DROP TABLE IF EXISTS `noticias_imagenes`;
		
CREATE TABLE `noticias_imagenes` (
  `id_noticia` INT NOT NULL AUTO_INCREMENT,
  `id_imagen` INT NOT NULL,
  PRIMARY KEY (`id_noticia`, `id_imagen`)
);

-- ---
-- Table 'donaciones'
-- 
-- ---

DROP TABLE IF EXISTS `donaciones`;
		
CREATE TABLE `donaciones` (
  `id_donacion` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `monto` DECIMAL NOT NULL,
  `fecha_donacion` TIMESTAMP NOT NULL,
  `comentario` VARCHAR(2048) NULL DEFAULT NULL,
  PRIMARY KEY (`id_donacion`),
KEY (`id_usuario`)
);

-- ---
-- Table 'comentarios'
-- 
-- ---

DROP TABLE IF EXISTS `comentarios`;
		
CREATE TABLE `comentarios` (
  `id_comentario` INT NOT NULL AUTO_INCREMENT,
  `comentario` VARCHAR(2048) NOT NULL,
  `fecha_publicacion` TIMESTAMP NOT NULL,
  `id_usuario` INT NOT NULL,
  `id_noticia` INT NOT NULL,
  PRIMARY KEY (`id_comentario`),
KEY (`id_usuario`, `id_noticia`)
);

-- ---
-- Table 'noticias_tag'
-- 
-- ---

DROP TABLE IF EXISTS `noticias_tag`;
		
CREATE TABLE `noticias_tag` (
  `id_tag` INT NOT NULL,
  `id_noticia` INT NOT NULL,
  PRIMARY KEY (`id_tag`, `id_noticia`)
);

-- ---
-- Table 'usuarios_privilegios'
-- 
-- ---

DROP TABLE IF EXISTS `usuarios_privilegios`;
		
CREATE TABLE `usuarios_privilegios` (
  `id_usuario` INT NOT NULL,
  `id_privilegio` INT NOT NULL,
  PRIMARY KEY (`id_usuario`, `id_privilegio`)
);

-- ---
-- Table 'requisitos'
-- 
-- ---

DROP TABLE IF EXISTS `requisitos`;
		
CREATE TABLE `requisitos` (
  `id_requisito` INT NOT NULL AUTO_INCREMENT,
  `sistema_operativo` VARCHAR(512) NOT NULL,
  `procesador` VARCHAR(512) NOT NULL,
  `memoria` VARCHAR(128) NOT NULL,
  `grafica` VARCHAR(512) NOT NULL,
  `almacenamiento` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id_requisito`)
);

-- ---
-- Table 'lista_amigos'
-- 
-- ---

DROP TABLE IF EXISTS `lista_amigos`;
		
CREATE TABLE `lista_amigos` (
  `id_lista_amigos` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `id_amigo` INT NOT NULL,
  PRIMARY KEY (`id_lista_amigos`),
KEY (`id_usuario`, `id_amigo`)
);

-- ---
-- Table 'solicitudes_amistad'
-- 
-- ---

DROP TABLE IF EXISTS `solicitudes_amistad`;
		
CREATE TABLE `solicitudes_amistad` (
  `id_solicitud_amistad` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `id_amigo` INT NOT NULL,
  `fecha_solicitud` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id_solicitud_amistad`),
KEY (`id_usuario`, `id_amigo`)
);

-- ---
-- Table 'mensajes'
-- 
-- ---

DROP TABLE IF EXISTS `mensajes`;
		
CREATE TABLE `mensajes` (
  `id_mensaje` BIGINT NOT NULL AUTO_INCREMENT,
  `cuerpo` MEDIUMTEXT NOT NULL,
  `fecha` TIMESTAMP NOT NULL,
  `id_escritor` INT NOT NULL,
  `id_lector` INT NOT NULL,
  PRIMARY KEY (`id_mensaje`),
KEY (`id_escritor`, `id_lector`)
);

-- ---
-- Table 'usuarios_bloqueados'
-- 
-- ---

DROP TABLE IF EXISTS `usuarios_bloqueados`;
		
CREATE TABLE `usuarios_bloqueados` (
  `id_usuario_bloqueado` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `id_admin` INT NOT NULL,
  `fecha_bloqueo` TIMESTAMP NOT NULL,
  `motivo` VARCHAR(1024) NOT NULL,
  PRIMARY KEY (`id_usuario_bloqueado`),
KEY (`id_usuario`, `id_admin`)
);

-- ---
-- Table 'notificaciones'
-- 
-- ---

DROP TABLE IF EXISTS `notificaciones`;
		
CREATE TABLE `notificaciones` (
  `id_notificacion` INT NOT NULL AUTO_INCREMENT,
  `notificacion` VARCHAR(2048) NOT NULL,
  `id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_notificacion`)
);

-- ---
-- Foreign Keys 
-- ---

ALTER TABLE `juegos` ADD FOREIGN KEY (id_requisito) REFERENCES `requisitos` (`id_requisito`);
ALTER TABLE `juegos` ADD FOREIGN KEY (id_admin_creado) REFERENCES `admin` (`id_admin`);
ALTER TABLE `analisis` ADD FOREIGN KEY (id_juego) REFERENCES `juegos` (`id_juego`);
ALTER TABLE `analisis` ADD FOREIGN KEY (id_usuario) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `imagenes` ADD FOREIGN KEY (id_juego) REFERENCES `juegos` (`id_juego`);
ALTER TABLE `imagenes` ADD FOREIGN KEY (id_admin_subido) REFERENCES `admin` (`id_admin`);
ALTER TABLE `privilegios_admin` ADD FOREIGN KEY (id_privilegio) REFERENCES `privilegios` (`id_privilegio`);
ALTER TABLE `privilegios_admin` ADD FOREIGN KEY (id_admin) REFERENCES `admin` (`id_admin`);
ALTER TABLE `juegos_tag` ADD FOREIGN KEY (id_juego) REFERENCES `juegos` (`id_juego`);
ALTER TABLE `juegos_tag` ADD FOREIGN KEY (id_tag) REFERENCES `tag` (`id_tag`);
ALTER TABLE `juegos_modos` ADD FOREIGN KEY (id_juego) REFERENCES `juegos` (`id_juego`);
ALTER TABLE `juegos_modos` ADD FOREIGN KEY (id_modo) REFERENCES `modos` (`id_modo`);
ALTER TABLE `noticias` ADD FOREIGN KEY (id_admin_creado) REFERENCES `admin` (`id_admin`);
ALTER TABLE `noticias_imagenes` ADD FOREIGN KEY (id_noticia) REFERENCES `noticias` (`id_noticia`);
ALTER TABLE `noticias_imagenes` ADD FOREIGN KEY (id_imagen) REFERENCES `imagenes` (`id_imagen`);
ALTER TABLE `donaciones` ADD FOREIGN KEY (id_usuario) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `comentarios` ADD FOREIGN KEY (id_usuario) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `comentarios` ADD FOREIGN KEY (id_noticia) REFERENCES `noticias` (`id_noticia`);
ALTER TABLE `noticias_tag` ADD FOREIGN KEY (id_tag) REFERENCES `tag` (`id_tag`);
ALTER TABLE `noticias_tag` ADD FOREIGN KEY (id_noticia) REFERENCES `noticias` (`id_noticia`);
ALTER TABLE `usuarios_privilegios` ADD FOREIGN KEY (id_usuario) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `usuarios_privilegios` ADD FOREIGN KEY (id_privilegio) REFERENCES `privilegios` (`id_privilegio`);
ALTER TABLE `lista_amigos` ADD FOREIGN KEY (id_usuario) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `lista_amigos` ADD FOREIGN KEY (id_amigo) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `solicitudes_amistad` ADD FOREIGN KEY (id_usuario) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `solicitudes_amistad` ADD FOREIGN KEY (id_amigo) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `mensajes` ADD FOREIGN KEY (id_escritor) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `mensajes` ADD FOREIGN KEY (id_lector) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `usuarios_bloqueados` ADD FOREIGN KEY (id_usuario) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `usuarios_bloqueados` ADD FOREIGN KEY (id_admin) REFERENCES `admin` (`id_admin`);
ALTER TABLE `notificaciones` ADD FOREIGN KEY (id_usuario) REFERENCES `usuarios` (`id_usuario`);
