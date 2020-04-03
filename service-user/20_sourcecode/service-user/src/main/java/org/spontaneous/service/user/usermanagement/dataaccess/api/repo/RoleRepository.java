package org.spontaneous.service.user.usermanagement.dataaccess.api.repo;

import org.spontaneous.service.user.usermanagement.dataaccess.api.RoleEntity;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<RoleEntity, Long>{

	 RoleEntity findByName(String name);
	 
}
