package com.game.services.noticias;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.noticias.dto.NoticiaInput;
import com.game.controllers.noticias.dto.NoticiaItem;
import com.game.exceptions.ApiException;
import com.game.persistence.models.Comentarios;
import com.game.persistence.models.Imagenes;
import com.game.persistence.models.Noticias;
import com.game.persistence.models.Privilegios;
import com.game.persistence.models.Tag;
import com.game.persistence.models.Usuarios;
import com.game.persistence.repository.ComentariosRepository;
import com.game.persistence.repository.NoticiasRepository;
import com.game.persistence.repository.TagRepository;
import com.game.persistence.repository.UsuariosRepository;
import com.game.services.fileservice.FileService;

/**
 * @author Juan Paggi. Logica de servicio para las noticias.
 *
 */

@Service
public class NoticiasService {

	@Autowired
	NoticiasRepository noticiasRepository;

	@Autowired
	UsuariosRepository usuarioRepository;

	@Autowired
	TagRepository tagRepository;

	@Autowired
	ComentariosRepository comentariosRepository;

	@Autowired
	FileService fileService;

	public String formatSeo(String text) {
		return StringUtils.strip(text.replaceAll("([^a-zA-Z0-9]+)", "-"), "-");
	}

	public NoticiaItem getNoticias(long id) {

		try {
			Optional<Noticias> noticia = noticiasRepository.findById(id);
			NoticiaItem noticiaItem = new NoticiaItem();
			if (noticia.isPresent()) {
				noticiaItem.id_noticia = noticia.get().getId_noticia();
				noticiaItem.titulo = noticia.get().getTitulo();
				noticiaItem.descripcion = noticia.get().getDescripcion();
				noticiaItem.cuerpo = noticia.get().getCuerpo();
				noticiaItem.fecha_publicacion = noticia.get().getFecha_publicacion();
				noticiaItem.id_usuario_noticia = noticia.get().getId_usuario_noticia().getId_usuario();
				List<Tag> tag_noticia = noticia.get().getTags();
				List<Comentarios> comentarios_noticia = noticia.get().getComentarios();
				ArrayList<Long> tag_id = new ArrayList<>();
				ArrayList<Long> comentario_id = new ArrayList<>();
				for (Tag tag : tag_noticia) {
					tag_id.add(tag.getId_tag());
				}
				for (Comentarios comentario : comentarios_noticia) {
					comentario_id.add(comentario.getId_comentario());
				}
				noticiaItem.tags = tag_id;
				noticiaItem.comentarios = comentario_id;

				ArrayList<String> imagenes = new ArrayList<String>();
				for (Imagenes imagen : noticia.get().getImagenes()) {
					if (imagen != null) {
						imagenes.add("/image/" + imagen.getId_imagen() + "/" + formatSeo(noticia.get().getTitulo())
								+ ".jpg");
					}
				}
				noticiaItem.imagenes = imagenes;
			} else {
				throw new ApiException(404, "No existe la noticia");
			}

			return noticiaItem;
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

	public List<NoticiaItem> getAllNoticias() {

		List<Noticias> noticias = noticiasRepository.findAll();
		List<NoticiaItem> out = new ArrayList<NoticiaItem>();
		for (Noticias noticia : noticias) {
			NoticiaItem item = new NoticiaItem();
			item.id_noticia = noticia.getId_noticia();
			item.titulo = noticia.getTitulo();
			item.descripcion = noticia.getDescripcion();
			item.cuerpo = noticia.getCuerpo();
			item.fecha_publicacion = noticia.getFecha_publicacion();
			item.id_usuario_noticia = noticia.getId_usuario_noticia().getId_usuario();
			List<Tag> tag_noticia = noticia.getTags();
			List<Comentarios> comentario_noticia = noticia.getComentarios();
			ArrayList<Long> tag_id = new ArrayList<>();
			ArrayList<Long> comentario_id = new ArrayList<>();
			for (Tag tag : tag_noticia) {
				tag_id.add(tag.getId_tag());
			}
			for (Comentarios comentario : comentario_noticia) {
				comentario_id.add(comentario.getId_comentario());
			}
			item.tags = tag_id;
			item.comentarios = comentario_id;

			ArrayList<String> imagenes = new ArrayList<String>();
			for (Imagenes imagen : noticia.getImagenes()) {
				if (imagen != null) {
					imagenes.add("/image/" + imagen.getId_imagen() + "/" + formatSeo(noticia.getTitulo()) + ".jpg");
				}
			}
			item.imagenes = imagenes;

			out.add(item);
		}
		return out;

	}

	public long addNoticia(NoticiaInput noticiaIn) throws NoSuchAlgorithmException {

		try {
			Optional<Usuarios> usuario = usuarioRepository.findById(noticiaIn.id_usuario_noticia);
			if (!usuario.isPresent()) {
				throw new ApiException(404, "No existe el usuario");
			}
			if (obtenerAcceso(usuario.get())) {
				List<Tag> lista_tag = tagRepository.findAllById(noticiaIn.tags);
				Noticias noticia = new Noticias();
				if (lista_tag.size() != noticiaIn.tags.size()) {
					throw new ApiException(404, "Error al cargar los tags");
				}
				noticia.setTitulo(noticiaIn.titulo);
				noticia.setDescripcion(noticiaIn.descripcion);
				noticia.setCuerpo(noticiaIn.cuerpo);
				noticia.setFecha_publicacion(noticiaIn.fecha_publicacion);
				noticia.setId_usuario_noticia(usuario.get());
				noticia.setTags(lista_tag);

				Set<Imagenes> imagenes = new HashSet<Imagenes>();
				for (byte[] imagen : noticiaIn.archivoImagen) {
					Optional<Imagenes> aux = fileService.selectImageFile(imagen);
					if (aux.isPresent()) {
						imagenes.add(aux.get());
					} else {
						imagenes.add(fileService.uploadImageFile(imagen, noticiaIn.nombreImagen, usuario.get()));
					}
				}
				noticia.setImagenes(imagenes);

				noticia = noticiasRepository.save(noticia);
				return noticia.getId_noticia();
			} else {
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
			if (const_agregar.equals(aux)) {
				acceso = true;
			}
		}
		return acceso;
	}

	public void removeNoticia(String id) {

		try {
			Optional<Noticias> noticia = noticiasRepository.findById(Long.parseLong(id));
			if (noticia.isPresent()) {
				noticiasRepository.delete(noticia.get());
			} else {
				throw new ApiException(404, "No existe la noticia");
			}
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

	public void editNoticia(String id, NoticiaInput noticiaIn) throws NoSuchAlgorithmException {

		try {
			Optional<Noticias> noticia = noticiasRepository.findById(Long.parseLong(id));
			Optional<Usuarios> usuario = usuarioRepository.findById(noticiaIn.id_usuario_noticia);
			if (!noticia.isPresent()) {
				throw new ApiException(404, "No existe la noticia");
			}
			if (!usuario.isPresent()) {
				throw new ApiException(404, "No existe el usuario");
			}
			Noticias noticiaObj = noticia.get();
			noticiaObj.setTitulo(noticiaIn.titulo);
			noticiaObj.setDescripcion(noticiaIn.descripcion);
			noticiaObj.setCuerpo(noticiaIn.cuerpo);
			noticiaObj.setFecha_publicacion(noticiaIn.fecha_publicacion);
			List<Tag> lista_tag = tagRepository.findAllById(noticiaIn.tags);
			if (lista_tag.size() != noticiaIn.tags.size()) {
				throw new ApiException(404, "Error al cargar los Tags");
			}
			noticiaObj.setTags(lista_tag);

			Set<Imagenes> imagenes = new HashSet<Imagenes>();
			for (byte[] imagen : noticiaIn.archivoImagen) {
				imagenes.add(fileService.uploadImageFile(imagen, noticiaIn.nombreImagen, usuario.get()));
			}

			for (Imagenes imagen : noticia.get().getImagenes()) {
				fileService.removeImage(imagen.getId_imagen());
			}

			noticiaObj.setImagenes(imagenes);

			noticiasRepository.save(noticiaObj);
		} catch (ApiException e) {
			throw e;
		} catch (Exception exception) {
			throw exception;
		}

	}

}
