package com.game.controllers.lista_amigos;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.controllers.lista_amigos.dto.ListaAmigosItem;
import com.game.exceptions.ApiException;
import com.game.services.lista_amigos.ListaAmigosService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi. Controlador de ListaAmigos con get, post y delete.
 */

@RestController
@RequestMapping("/amigos")
public class ListaAmigosControllers {

	@Autowired
	ListaAmigosService listaAmigosService;

	public static final Logger logger = LoggerFactory.getLogger(ListaAmigosControllers.class);

	@GetMapping(path = "{idUsuario}")
	public @ResponseBody ResponseEntity<List<ListaAmigosItem>> getUsuarios(
			@PathVariable("idUsuario") String idUsuario) {
		try {
			return new ResponseEntity<List<ListaAmigosItem>>(listaAmigosService.getAllAmigos(Long.parseLong(idUsuario)),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addAmigos(@RequestBody ListaAmigosItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			listaAmigosService.addAmigo(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Amigo agregado correctamente");
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.NOT_FOUND);
			} else if (e.getCode() == 409) {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.CONFLICT);
			} else {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			respuesta.codigo("ERROR");
			respuesta.descripcion(e.getMessage());
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeUsuario(@RequestBody ListaAmigosItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			listaAmigosService.removeAmigo(body.id_usuario, body.id_amigo);
			respuesta.codigo("OK");
			respuesta.descripcion("Amigo borrado correctamente");
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				respuesta.codigo("ERROR");
				respuesta.descripcion(e.getMessage());
				return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			respuesta.codigo("ERROR");
			respuesta.descripcion(e.getMessage());
			return new ResponseEntity<ModelApiResponse>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
