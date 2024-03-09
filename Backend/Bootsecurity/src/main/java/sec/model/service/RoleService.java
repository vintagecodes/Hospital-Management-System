package sec.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import sec.model.Role;
import sec.repository.RoleRepository;


@Service
public class RoleService {
	
	@Autowired
	@Lazy
	private RoleRepository repository;
	
	public Role createNewRole(Role role) {
		return repository.save(role);
		
	}

}
