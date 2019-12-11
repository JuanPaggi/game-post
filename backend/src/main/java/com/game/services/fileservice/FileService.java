package com.game.services.fileservice;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.exceptions.ApiException;
import com.game.persistence.models.Imagenes;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.ImagenesRepository;

/**
 * @author Juan Paggi
 * Logica de servicio para las imagenes
 */

@Service
public class FileService { 

	@Autowired
	ImagenesRepository imagenesRepository;
	
	public byte[] getImageFile(long id) {
		Optional<Imagenes> imagen = imagenesRepository.findById(id);
		if(imagen.isPresent()) {
			return imagen.get().getImagen();
		}
		return new byte[0];			
	}
	
	public void removeImage(long id) {
		imagenesRepository.deleteById(id);			
	}
	
	public Imagenes getImage(long id) {
		try {
			Optional<Imagenes> imageCdn = imagenesRepository.findById(id);
			if(imageCdn.isPresent()) {
				return imageCdn.get();
			}else {
				throw new ApiException(404, "No existe la imagen");			
			}			
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
	}
	
	public Imagenes uploadImageFile(byte[] image_file, String name, Usuarios usuario) throws NoSuchAlgorithmException {
		Imagenes img = new Imagenes();
		img.setImagen(image_file);
		img.setNombre(name);
		img.setFecha_subida(new Date());
		img.setId_usuario_subido(usuario);
		return imagenesRepository.save(img);
	}
	
	public Optional<Imagenes> selectImageFile(byte[] image_file) {
		return imagenesRepository.findByImgen(image_file);
	}
	
}
