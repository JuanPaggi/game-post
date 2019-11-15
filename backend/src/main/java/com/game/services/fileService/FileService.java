package com.game.services.fileService;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.persistence.models.Imagenes;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.ImagenesRepository;
import com.game.services.fileService.exceptions.ImagenNotFound;

@Service
public class FileService {

	@Autowired
	ImagenesRepository imagenesRepository;
	
	public byte[] getImageFile(long id) {
		Optional<Imagenes> imagen = imagenesRepository.findById(id);
		if(imagen.isPresent()) {
			return imagen.get().getImagen();
		}
		return null;
	}
	
	public void removeImage(long id) {
		imagenesRepository.deleteById(id);
	}
	
	public Imagenes getImage(long id) throws ImagenNotFound {
		Optional<Imagenes> imageCdn = imagenesRepository.findById(id);
		if(imageCdn.isPresent()) {
			return imageCdn.get();
		}
		throw new ImagenNotFound();
	}
	
	public Imagenes uploadImageFile(byte[] image_file, String name, Usuarios usuario) throws NoSuchAlgorithmException {
		Imagenes img = new Imagenes();
		img.setImagen(image_file);
		img.setNombre(name);
		img.setFecha_subida(new Date());
		img.setId_usuario_subido(usuario);
		return imagenesRepository.save(img);
	}
	
	public Optional<Imagenes> selectImageFile(byte[] image_file) throws NoSuchAlgorithmException {
		return imagenesRepository.findByImgen(image_file);
	}
	
}
