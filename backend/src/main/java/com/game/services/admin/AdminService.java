package com.game.services.admin;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.controllers.admin.dto.AdminItem;
import com.game.persistence.models.Admin;
import com.game.persistence.models.Privilegios;
import com.game.persistence.repository.AdminRepository;
import com.game.persistence.repository.PrivilegiosRepository;
import com.game.services.admin.exceptions.AdminNotFound;
import com.game.services.privilegios.exceptions.PrivilegioNotFound;

/**
 * @author pachi
 *
 */

@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	PrivilegiosRepository privilegiosRepository;
	
	public List<AdminItem> getAllAdmin() throws ParseException{
		
		List<Admin> admin = adminRepository.findAll();
		List<AdminItem> out = new ArrayList<AdminItem>();
		for(Admin admin_: admin) {
			AdminItem item = new AdminItem();
			item.id_admin = admin_.getId_admin();
			item.usuario = admin_.getUsuario();
			item.clave = admin_.getClave();
			
			List<Privilegios> privilegio_admin = admin_.getPrivilegios();
			ArrayList<Long> privilegio_id = new ArrayList<>();
			for (Privilegios privilegio : privilegio_admin) {
				privilegio_id.add(privilegio.getId_privilegio());
			}
			item.privilegios = privilegio_id;
			
			out.add(item);
		}
		return out;
		
	}
	
	public AdminItem getAdmin(long id) throws AdminNotFound {
		
		Optional<Admin> admin = adminRepository.findById(id);
		AdminItem adminItem = new AdminItem();
		if(admin.isEmpty()) throw new AdminNotFound();
		adminItem.id_admin = admin.get().getId_admin();
		adminItem.usuario = admin.get().getUsuario();
		adminItem.clave = admin.get().getClave();
		
		List<Privilegios> privilegio_admin = admin.get().getPrivilegios();
		ArrayList<Long> privilegio_id = new ArrayList<>();
		for (Privilegios privilegio : privilegio_admin) {
			privilegio_id.add(privilegio.getId_privilegio());
		}
		adminItem.privilegios = privilegio_id;
		
		return adminItem;
		
	}
	
	public long addAdmin(AdminItem adminIn) throws PrivilegioNotFound {
		
		Admin admin = new Admin();
		List<Privilegios> lista_privilegios= privilegiosRepository.findAllById(adminIn.privilegios);
		if(lista_privilegios.size() != adminIn.privilegios.size()) throw new PrivilegioNotFound();
		admin.setId_admin(adminIn.id_admin);
		admin.setUsuario(adminIn.usuario);
		admin.setClave(adminIn.clave);
		admin.setPrivilegios(lista_privilegios);
		admin = adminRepository.save(admin);
		return admin.getId_admin();
		
	}
	
	public void removeAdmin(String id) throws AdminNotFound, NumberFormatException {
		
		Optional<Admin> admin = adminRepository.findById(Long.parseLong(id));
		if(admin.isEmpty()) throw new AdminNotFound();
		adminRepository.delete(admin.get());
	
	}
	
	public void editAdmin(String id, AdminItem adminIn) throws AdminNotFound,PrivilegioNotFound, NumberFormatException{
		
		Optional<Admin> admin = adminRepository.findById(Long.parseLong(id));
		if(admin.isEmpty()) throw new AdminNotFound();
		Admin adminObj = admin.get();
		adminObj.setUsuario(adminIn.usuario);
		adminObj.setClave(adminIn.clave);
		List<Privilegios> lista_privilegios= privilegiosRepository.findAllById(adminIn.privilegios);
		if(lista_privilegios.size() != adminIn.privilegios.size()) throw new PrivilegioNotFound();
		adminObj.setPrivilegios(lista_privilegios);
		adminRepository.save(adminObj);
		
	}
	
}
