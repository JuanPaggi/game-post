package com.game.controllers.imagenes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.services.fileservice.FileService;

/**
 * @author Juan Paggi
 * Controlador de Imagenes con get.
 * Las imagenes se añaden con los juegos o noticias.
 */

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET})
@RequestMapping("${v1API}")
public class ImagenesControllers {
	
	public static final Logger logger = LoggerFactory.getLogger(ImagenesControllers.class);
	
	@Autowired
	FileService fileService;

	@GetMapping(
			value = "/image/{fileID}/{imageSEO}.jpg",
			produces = MediaType.IMAGE_JPEG_VALUE
	)
	public @ResponseBody ResponseEntity<byte[]> getImage(@PathVariable("fileID") long fileID) {
		try {
			return new ResponseEntity<byte[]>(fileService.getImageFile(fileID),HttpStatus.OK);			
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
