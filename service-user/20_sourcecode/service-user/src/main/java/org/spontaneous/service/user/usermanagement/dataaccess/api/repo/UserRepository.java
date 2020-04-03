package org.spontaneous.service.user.usermanagement.dataaccess.api.repo;

import java.util.List;

import org.spontaneous.service.user.usermanagement.dataaccess.api.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

	List<UserEntity> findByLastname(String lastname);
}
