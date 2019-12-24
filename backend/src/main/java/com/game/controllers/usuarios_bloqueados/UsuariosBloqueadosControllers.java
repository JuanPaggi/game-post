package com.game.controllers.usuarios_bloqueados;

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

import com.game.controllers.usuarios_bloqueados.dto.UsuarioBloqueadoItem;
import com.game.exceptions.ApiException;
import com.game.services.usuarios_bloqueados.UsuariosBloqueadosService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi. Controlador de Usuarios_bloqueados con get, post y
 *         delete. Tenemos dos Get, uno para devolver un juego seleccionado por
 *         su id y otro get para devolver todos los juegos.
 */

@RestController
@RequestMapping("/usuariosBloqueados")
public class UsuariosBloqueadosControllers {

	public static final Logger logger = LoggerFactory.getLogger(UsuariosBloqueadosControllers.class);

	@Autowired
	UsuariosBloqueadosService usuariosBloqueadosService;

	@GetMapping(path = "")
	public @ResponseBody ResponseEntity<List<UsuarioBloqueadoItem>> getUsuariosBloqueados() {
		try {
			return new ResponseEntity<List<UsuarioBloqueadoItem>>(usuariosBloqueadosService.getAllUsuarios(),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addUsuarioBloqueado(@RequestBody UsuarioBloqueadoItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			usuariosBloqueadosService.addUsuarioBloqueado(body);
			respuesta.codigo("OK");
			respuesta.descripcion("Usuario agregado correctamente");
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

	@DeleteMapping(path = "/{idUsuarioBloqueado}")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeUsuarioBloqueado(
			@PathVariable("idUsuarioBloqueado") String idUsuarioBloqueado) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			usuariosBloqueadosService.removeUsuarioBloqueado(idUsuarioBloqueado);
			respuesta.codigo("OK");
			respuesta.descripcion("Usuario borrado correctamente");
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
