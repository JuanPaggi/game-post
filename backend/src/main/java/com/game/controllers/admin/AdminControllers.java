package com.game.controllers.admin;

import java.text.ParseException;
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

import com.game.controllers.admin.dto.AdminInput;
import com.game.controllers.admin.dto.AdminItem;
import com.game.controllers.admin.dto.AdminLogin;
import com.game.services.admin.AdminService;
import com.game.services.admin.exceptions.AdminNotFound;
import com.game.services.privilegios.exceptions.PrivilegioNotFound;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pachi
 * Controlador de Admins con get, post, put, y delete.
 * Tenemos dos Get, uno para devolver un admin seleccionado
 * por su id y otro get para devolver todos los admins.
 */

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("${v1API}/admin")
public class AdminControllers {

	public static Logger logger = LoggerFactory.getLogger(AdminControllers.class);
	
	@Autowired
	AdminService adminService;
	
	@GetMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<List<AdminItem>> getAdmin(){
		try {
			return new ResponseEntity<List<AdminItem>>(adminService.getAllAdmin(), HttpStatus.OK);
		} catch(ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{idAdmin}")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Not found."),
		@ApiResponse(code = 500, message = "Unexpected error.") })
	public @ResponseBody ResponseEntity<AdminItem> getAdminByID(
			@PathVariable("idAdmin") String idAdmin){
		try {
			return new ResponseEntity<AdminItem>(
					adminService.getAdmin(Long.parseLong(idAdmin)),
					HttpStatus.OK);
		} catch (AdminNotFound e) {
			return new ResponseEntity<AdminItem>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<AdminItem>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="/login")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id del admin"),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Long> verificarAdmin( @RequestBody AdminLogin body){
		try {
			return new ResponseEntity<Long>(adminService.verificarLogin(body.usuario, body.clave), HttpStatus.OK);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path="")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK, devuelve el id del admin insertado"),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Long> addAdmin( @RequestBody AdminInput body){
		try {
			return new ResponseEntity<Long>(adminService.addAdmin(body), HttpStatus.OK);
		} catch(PrivilegioNotFound e) {
			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path="/{idAdmin}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "borrado OK"),
		@ApiResponse(code = 404, message = "Admin inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> removeAdmin(@PathVariable("idAdmin") String idAdmin) {
		try {
			adminService.removeAdmin(idAdmin);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(AdminNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path="/{idAdmin}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Editado OK"),
		@ApiResponse(code = 404, message = "Admin inexistente."),
		@ApiResponse(code = 400, message = "Id no valido."),
		@ApiResponse(code = 500, message = "Unexpected error.")
		})
	public @ResponseBody ResponseEntity<Void> editAdmin(@PathVariable("idAdmin") String idAdmin, @RequestBody AdminItem body) {
		try {
			adminService.editAdmin(idAdmin, body);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(AdminNotFound e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch(NumberFormatException e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			logger.error("Internal server error", e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
