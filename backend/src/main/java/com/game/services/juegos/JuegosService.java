package com.game.services.juegos;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.juegos.dto.JuegoInput;
import com.game.controllers.juegos.dto.JuegoItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.Analisis;
import com.game.persistence.models.Imagenes;
import com.game.persistence.models.Juegos;
import com.game.persistence.models.Modos;
import com.game.persistence.models.Privilegios;
import com.game.persistence.models.Tag;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.AnalisisRepository;
import com.game.persistence.repository.JuegosRepository;
import com.game.persistence.repository.ModosRepository;
import com.game.persistence.repository.TagRepository;
import com.game.persistence.repository.UsuariosRepository;
import com.game.services.fileservice.FileService;

/**
 * @author Juan Paggi
 * Logica de servicio para los juegos
 */

@Service
@Transactional
public class JuegosService {

	@Autowired
	JuegosRepository juegosRepository;
	
	@Autowired
	AnalisisRepository analisisRepository;
	
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	ModosRepository modosRepository;
	
	@Autowired
	UsuariosRepository usuarioRepository;
	
	@Autowired
	FileService fileService;
	
	public String formatSeo(String text) {
		return StringUtils.strip(text.replaceAll("([^a-zA-Z0-9]+)", "-"), "-");
	}

	public JuegoItem getJuego(long id) {
		
		try {
			Optional<Juegos> juego = juegosRepository.findById(id);
			JuegoItem juegoItem = new JuegoItem();
			if(juego.isPresent()) {
				juegoItem.id_juego = juego.get().getId_juego();
				juegoItem.titulo = juego.get().getTitulo();
				juegoItem.descripcion = juego.get().getDescripcion();
				juegoItem.genero = juego.get().getGenero();
				juegoItem.desarrollador = juego.get().getDesarrollador();
				juegoItem.sistema_operativo = juego.get().getSistema_operativo();
				juegoItem.procesador = juego.get().getProcesador();
				juegoItem.memoria = juego.get().getMemoria();
				juegoItem.grafica = juego.get().getGrafica();
				juegoItem.almacenamiento = juego.get().getAlmacenamiento();
				juegoItem.fecha_lanzamiento = juego.get().getFecha_lanzamiento();
				juegoItem.analisis_positivos = juego.get().getAnalisis_positivos();
				juegoItem.analisis_negativos = juego.get().getAnalisis_negativos();
				juegoItem.id_usuario_juego = juego.get().getId_usuario_juego().getId_usuario();
				List<Tag> tag_juego = juego.get().getTag();
				List<Modos> modo_juego = juego.get().getModos();
				ArrayList<Long> tag_id = new ArrayList<>();
				for (Tag tag : tag_juego) {
					tag_id.add(tag.getId_tag());
				}
				ArrayList<Long> modo_id = new ArrayList<>();
				for (Modos modo : modo_juego) {
					modo_id.add(modo.getId_modo());
				}
				juegoItem.tags = tag_id;
				juegoItem.modos = modo_id;
				
				List<Analisis> analisis_juego = juego.get().getAnalisis();
				ArrayList<Long> analisis_id = new ArrayList<>();
				
				for (Analisis analisis : analisis_juego) {
					analisis_id.add(analisis.getId_analisis());
				}
				
				juegoItem.analisis = analisis_id;
				
				ArrayList<String> imagenes = new ArrayList<String>();
				for (Imagenes imagen : juego.get().getImagenes()) {
					if(imagen != null) {
						imagenes.add("/image/"+imagen.getId_imagen()+"/"+formatSeo(juego.get().getTitulo())+".jpg");
					}
				}
				juegoItem.archivoImagen = imagenes;
				
				return juegoItem;
			}else {
				throw new ApiException(404, "No existe el juego");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public List<JuegoItem> getAllJuegos() {
		
		try {
			List<Juegos> juegos = juegosRepository.findAll();
			List<JuegoItem> out = new ArrayList<JuegoItem>();
			for(Juegos juego: juegos) {
				JuegoItem item = new JuegoItem();
				item.id_juego = juego.getId_juego();
				item.titulo = juego.getTitulo();
				item.descripcion = juego.getDescripcion();
				item.genero = juego.getGenero();
				item.desarrollador = juego.getDesarrollador();
				
				item.sistema_operativo = juego.getSistema_operativo();
				item.procesador = juego.getProcesador();
				item.memoria = juego.getMemoria();
				item.grafica = juego.getGrafica();
				item.almacenamiento = juego.getAlmacenamiento();
				
				item.fecha_lanzamiento = juego.getFecha_lanzamiento();
				item.analisis_positivos = juego.getAnalisis_positivos();
				item.analisis_negativos = juego.getAnalisis_negativos();
				item.id_usuario_juego = juego.getId_usuario_juego().getId_usuario();
				List<Tag> tag_juego = juego.getTag();
				List<Modos> modo_juego = juego.getModos();
				ArrayList<Long> tag_id = new ArrayList<>();
				for (Tag tag : tag_juego) {
					tag_id.add(tag.getId_tag());
				}
				ArrayList<Long> modo_id = new ArrayList<>();
				for (Modos modo : modo_juego) {
					modo_id.add(modo.getId_modo());
				}
				item.tags = tag_id;
				item.modos = modo_id;
				
				ArrayList<String> imagenes = new ArrayList<String>();
				for (Imagenes imagen : juego.getImagenes()) {
					if(imagen != null) {
						imagenes.add("/image/"+imagen.getId_imagen()+"/"+formatSeo(juego.getTitulo())+".jpg");
					}
				}
				item.archivoImagen = imagenes;
				
				out.add(item);
			}
			return out;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public long addJuego(JuegoInput juegoIn)throws NoSuchAlgorithmException {
		
		try {
			Optional<Usuarios> usuario = usuarioRepository.findById(juegoIn.id_usuario_juego);
			if (!usuario.isPresent()) {
				throw new ApiException(404, "No existe el usuario");								
			}
			if(obtenerAcceso(usuario.get())) {
				Juegos juego = new Juegos();
				List<Tag> lista_tag= tagRepository.findAllById(juegoIn.tags);
				List<Modos> lista_modo= modosRepository.findAllById(juegoIn.modos);
				if(lista_tag.size() != juegoIn.tags.size()) throw new ApiException(404, "Error al cargar los Tags");
				juego.setTag(lista_tag);
				if(lista_modo.size() != juegoIn.modos.size()) throw new ApiException(404, "Error al cargar los Modos");
				juego.setModos(lista_modo);
				juego.setTitulo(juegoIn.titulo);
				juego.setDescripcion(juegoIn.descripcion);
				juego.setGenero(juegoIn.genero);
				juego.setDesarrollador(juegoIn.desarrollador);
				
				juego.setSistema_operativo(juegoIn.sistema_operativo);
				juego.setProcesador(juegoIn.procesador);
				juego.setMemoria(juegoIn.memoria);
				juego.setGrafica(juegoIn.grafica);
				juego.setAlmacenamiento(juegoIn.almacenamiento);
				
				juego.setFecha_lanzamiento(juegoIn.fecha_lanzamiento);
				juego.setAnalisis_positivos(juegoIn.analisis_positivos);
				juego.setAnalisis_negativos(juegoIn.analisis_negativos);
				juego.setId_usuario_juego(usuario.get());
				juego.setTag(lista_tag);
				juego.setModos(lista_modo);
				
				Set<Imagenes> imagenes = new HashSet<Imagenes>();
				for (byte[] imagen : juegoIn.archivoImagen) {
					Optional<Imagenes> aux = fileService.selectImageFile(imagen);
					if (aux.isPresent()) {
						imagenes.add(aux.get());
					}else {
						imagenes.add(fileService.uploadImageFile(imagen, juegoIn.nombreImagen, usuario.get()));				
					}
				}
				juego.setImagenes(imagenes);	
				
				juego = juegosRepository.save(juego);
				return juego.getId_juego();	
			}else {
				throw new ApiException(404, "Acceso denegado");	
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
	
	}
	
	private boolean obtenerAcceso(Usuarios usuario) {
		List<Privilegios> usuario_privilegios = usuario.getPrivilegios();
		boolean acceso = false;
		String const_agregar = "AGREGAR_JUEGO";
		for (Privilegios privilegios : usuario_privilegios) {
			String aux = privilegios.getPrivilegio();
			if(const_agregar.equals(aux)) {
				acceso = true;
			}
		}
		return acceso;
	}
	
	public void removeJuego(String id) {
		
		try {
			Optional<Juegos> juego = juegosRepository.findById(Long.parseLong(id));
			if (juego.isPresent()) {
				juegosRepository.delete(juego.get());
			}else {
				throw new ApiException(404, "No existe el analisis");
			}		
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
	
	}
	
	public void editJuego(String id, JuegoInput juegoIn) throws NoSuchAlgorithmException{
		
		try {
			Optional<Juegos> juego = juegosRepository.findById(Long.parseLong(id));
			Optional<Usuarios> usuario = usuarioRepository.findById(juegoIn.id_usuario_juego);
			if(!juego.isPresent()) {
				throw new ApiException(404, "No existe el juego");
			}
			if(!usuario.isPresent()) {
				throw new ApiException(404, "No existe el usuario");
			}
			Juegos juegoObj = juego.get();
			juegoObj.setTitulo(juegoIn.titulo);
			juegoObj.setDescripcion(juegoIn.descripcion);
			juegoObj.setGenero(juegoIn.genero);
			juegoObj.setDesarrollador(juegoIn.desarrollador);
			juegoObj.setSistema_operativo(juegoIn.sistema_operativo);
			juegoObj.setProcesador(juegoIn.procesador);
			juegoObj.setMemoria(juegoIn.memoria);
			juegoObj.setGrafica(juegoIn.grafica);
			juegoObj.setAlmacenamiento(juegoIn.almacenamiento);
			juegoObj.setFecha_lanzamiento(juegoIn.fecha_lanzamiento);
			juegoObj.setAnalisis_positivos(juegoIn.analisis_positivos);
			juegoObj.setAnalisis_negativos(juegoIn.analisis_negativos);
			List<Tag> lista_tag= tagRepository.findAllById(juegoIn.tags);
			List<Modos> lista_modo= modosRepository.findAllById(juegoIn.modos);
			if(lista_tag.size() != juegoIn.tags.size()) throw new ApiException(404, "Error al cargar los Tags");
			juegoObj.setTag(lista_tag);
			if(lista_modo.size() != juegoIn.modos.size()) throw new ApiException(404, "Error al cargar los Modos");
			juegoObj.setModos(lista_modo);
			
			Set<Imagenes> imagenes = new HashSet<Imagenes>();
			for (byte[] imagen : juegoIn.archivoImagen) {
				imagenes.add(fileService.uploadImageFile(imagen, juegoIn.nombreImagen, usuario.get()));
			}
			
			for (Imagenes imagen : juego.get().getImagenes()) {
				fileService.removeImage(imagen.getId_imagen());
			}
			
			juegoObj.setImagenes(imagenes);	
			
			juegosRepository.save(juegoObj);
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception; 
		}
		
	}
	
	public void agregarValocacion(long id_juego, boolean valoracion) {
		if(valoracion) {
			juegosRepository.agregarValoracionPositiva(id_juego);
		}else {
			juegosRepository.agregarValoracionNegativa(id_juego);
		}
	}
	
}
