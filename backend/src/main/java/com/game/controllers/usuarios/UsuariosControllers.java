package com.game.controllers.usuarios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.controllers.usuarios.dto.UsuarioInput;
import com.game.controllers.usuarios.dto.UsuarioItem;
import com.game.controllers.usuarios.dto.UsuarioLogin;
import com.game.exceptions.ApiException;
import com.game.services.usuarios.UsuariosService;
import com.game.utils.ModelApiResponse;

/**
 * @author Juan Paggi. Controlador de Usuarios con get, post, put, y delete.
 *         Tenemos dos Get, uno para devolver un usuario seleccionado por su id
 *         y otro get para devolver todos los usuarios.
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RequestMapping("/usuarios")
public class UsuariosControllers {

	public static final Logger logger = LoggerFactory.getLogger(UsuariosControllers.class);

	@Autowired
	UsuariosService usuariosService;

	@GetMapping(path = "/{idUsuario}")
	public @ResponseBody ResponseEntity<UsuarioItem> getUsuarioByID(@PathVariable("idUsuario") String idUsuario) {
		try {
			return new ResponseEntity<UsuarioItem>(usuariosService.getUsuario(Long.parseLong(idUsuario)),
					HttpStatus.OK);
		} catch (ApiException e) {
			if (e.getCode() == 404) {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<UsuarioItem>(HttpStatus.NOT_FOUND);
			} else {
				logger.error(e.getMessage(), e);
				return new ResponseEntity<UsuarioItem>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<UsuarioItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "")
	public @ResponseBody ResponseEntity<List<UsuarioItem>> getUsuarios() {
		try {
			return new ResponseEntity<List<UsuarioItem>>(usuariosService.getAllUsuarios(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/login")
	public @ResponseBody ResponseEntity<Long> verificarUsuario(@RequestBody UsuarioLogin body) {
		try {
			return new ResponseEntity<Long>(usuariosService.verificarLogin(body.usuario, body.clave), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("El servidor encontró una condición inesperada, no se pudo cumplir la solicitud", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<ModelApiResponse> addUsuarios(@RequestBody UsuarioInput body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			usuariosService.addUsuario(body);
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

	@DeleteMapping(path = "/{idUsuario}")
	public @ResponseBody ResponseEntity<ModelApiResponse> removeUsuario(@PathVariable("idUsuario") String idUsuario) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			usuariosService.removeUsuario(idUsuario);
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

	@PutMapping(path = "/{idUsuario}")
	public @ResponseBody ResponseEntity<ModelApiResponse> editUsuario(@PathVariable("idUsuario") String idUsuario,
			@RequestBody UsuarioItem body) {
		ModelApiResponse respuesta = new ModelApiResponse();
		try {
			usuariosService.editUsuario(idUsuario, body);
			respuesta.codigo("OK");
			respuesta.descripcion("Usuario editado correctamente");
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
