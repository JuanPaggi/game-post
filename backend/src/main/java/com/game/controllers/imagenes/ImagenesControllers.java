package com.game.controllers.imagenes;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.services.fileService.FileService;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET})
@RequestMapping("${v1API}")
public class ImagenesControllers {
	
	@Autowired
	FileService fileService;

	@GetMapping(
			value = "/image/{fileID}/{imageSEO}.jpg",
			produces = MediaType.IMAGE_JPEG_VALUE
	)
	public @ResponseBody byte[] getImage(@PathVariable("fileID") long fileID) throws IOException {
		return fileService.getImageFile(fileID);
	}
}
