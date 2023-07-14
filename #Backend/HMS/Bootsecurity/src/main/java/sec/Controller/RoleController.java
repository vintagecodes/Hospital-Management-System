package sec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sec.model.Role;
import sec.model.service.RoleService;


@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService service;
	
	@PostMapping({"/createRole"})
	public Role createNewRole(@RequestBody Role role) {
		return service.createNewRole(role);
		
	}

}
