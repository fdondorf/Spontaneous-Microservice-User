package org.spontaneous.service.user.usermanagement.service.impl.rest;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.service.user.general.service.api.rest.RegisterUserRequest;
import org.spontaneous.service.user.usermanagement.dataaccess.api.UserEntity;
import org.spontaneous.service.user.usermanagement.dataaccess.api.repo.RoleRepository;
import org.spontaneous.service.user.usermanagement.dataaccess.api.repo.UserRepository;
import org.spontaneous.service.user.usermanagement.service.api.RegisteredUserResult;
import org.spontaneous.service.user.usermanagement.service.api.UserDto;
import org.spontaneous.service.user.usermanagement.service.api.rest.AuthServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.dozermapper.core.Mapper;

/**
 * 
 * @author Florian Dondorf
 *
 */
@RestController
public class RegisterController extends AbstractClientController {

	private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

	private static final String ROLE_USER = "ROLE_USER";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthServiceClient authServiceClient;

	@Autowired
	private Mapper mapper;

	/**
	 * Method for providing a rest interface for register a new user
	 *
	 * @param userTO The user to register
	 * @return Returns a ResponseEntity
	 */
	@PostMapping("/v1/register")
	@Transactional
	public ResponseEntity<RegisteredUserResult> register(@RequestBody RegisterUserRequest registerUserRequest) {

		LOG.info("Register user!");

		checkHeader(registerUserRequest);

		// Create user in user service db
		UserEntity userEntity = new UserEntity();
		mapper.map(registerUserRequest, userEntity);
		userEntity.setRoles(Arrays.asList(roleRepository.findByName(ROLE_USER)));
		userEntity = userRepository.save(userEntity);

		// Create user in auth service db
		UserDto userDto = new UserDto();
		mapper.map(registerUserRequest, userDto);
		this.authServiceClient.createUser(userDto);

		RegisteredUserResult result = new RegisteredUserResult(userEntity.getId());
		ResponseEntity<RegisteredUserResult> response = new ResponseEntity<>(result, HttpStatus.OK);

		return response;
	}
}
