package org.spontaneous.service.user.usermanagement.service.impl.rest;

import java.security.Principal;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.service.user.general.dataaccess.api.Gender;
import org.spontaneous.service.user.general.service.api.exception.UserNotFoundException;
import org.spontaneous.service.user.general.service.api.rest.Header;
import org.spontaneous.service.user.usermanagement.dataaccess.api.UserEntity;
import org.spontaneous.service.user.usermanagement.dataaccess.api.repo.UserRepository;
import org.spontaneous.service.user.usermanagement.service.api.UpdateUserRequest;
import org.spontaneous.service.user.usermanagement.service.api.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.dozermapper.core.Mapper;

@RestController
@RequestMapping("/secure")
public class UserController extends AbstractClientAuthController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Mapper mapper;

	@Autowired
	private AuthorizationServerTokenServices tokenServices;

	@GetMapping("/v1/current")
	public Principal getUser(Principal principal) {
		return principal;
	}

	/**
	 * Returns UserInfo, wrapped in a ResponseEntity. Will send a request against
	 * PartnerPortal to get required information.
	 *
	 * @param headerData the HeaderData
	 * @param principal  the requested Principal
	 * @return UserInfo wrapped in a ResponseEntitiy
	 */
	@RequestMapping(value = "/v1/userinfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> getUserInfo(@RequestBody Header headerData, Principal principal) {

		LOG.debug("Calling Controller 'getUserInfo'");

		checkHeader(headerData);

		// AuthenticatedUser authUser = getAuthUser(principal)
		UserEntity userEntity = userRepository.findByEmail(getAuthUser(principal).getName());
		if (userEntity != null) {
			UserDto userInfo = mapper.map(userEntity, UserDto.class);

			LOG.debug("userinfo: " + userInfo);

			return new ResponseEntity<UserDto>(userInfo, HttpStatus.OK);
		} else {
			LOG.error("No user found.");
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/v1/user/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest updateUserRequest, Principal principal) {

		LOG.debug("Calling Controller 'updateUser'");

		checkHeader(updateUserRequest);

		OAuth2AccessToken accessToken = tokenServices.getAccessToken((OAuth2Authentication) principal);
		UUID userId = (UUID) accessToken.getAdditionalInformation().get("userId");

		UserEntity userEntity = userRepository.findByEmail(updateUserRequest.getEmail());
		if (userEntity != null) {

			// TODO: New email need to be validated
			// user.setEmail(userModel.getEmail());
			if (updateUserRequest.getFirstname() != null)
				userEntity.setFirstname(updateUserRequest.getFirstname());

			if (updateUserRequest.getLastname() != null)
				userEntity.setLastname(updateUserRequest.getLastname());

			if (updateUserRequest.getGender() != null)
				userEntity.setGender(Gender.fromName(updateUserRequest.getGender()));

			if (updateUserRequest.getImage() != null)
				userEntity.setImage(updateUserRequest.getImage());

			UserEntity savedUser = userRepository.save(userEntity);
			if (savedUser == null)
				throw new ApplicationContextException("Error during update of new user...");

			UserDto userInfo = mapper.map(userEntity, UserDto.class);

			LOG.debug("userinfo: " + userInfo);

			return new ResponseEntity<>(userInfo, HttpStatus.OK);

		} else {
			LOG.error("No user found.");
			throw new UserNotFoundException("User with the id " + updateUserRequest.getId() + "not found!");
		}

	}

}
