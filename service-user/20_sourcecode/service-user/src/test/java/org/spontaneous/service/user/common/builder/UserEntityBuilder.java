package org.spontaneous.service.user.common.builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.cxf.helpers.IOUtils;
import org.spontaneous.service.user.general.dataaccess.api.Gender;
import org.spontaneous.service.user.usermanagement.dataaccess.api.RoleEntity;
import org.spontaneous.service.user.usermanagement.dataaccess.api.UserEntity;

public class UserEntityBuilder {

	private UserEntity entity = new UserEntity();

	private static Random random = new Random();

	private UserEntityBuilder() {

	}

	public static UserEntityBuilder aDefaultUserEntity(RoleEntity role) {
		UserEntityBuilder userEntityBuilder = new UserEntityBuilder();
		userEntityBuilder.entity.setFirstname("Jonny");
		userEntityBuilder.entity.setLastname("Olsen");
		userEntityBuilder.entity.setEmail("test" + random.nextInt() + "@test.de");
		userEntityBuilder.entity.setGender(Gender.MALE);

		byte[] image = loadResource("/images/profile-image.jpg");
		// userEntityBuilder.entity.setImage(image);

		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		roles.add(role);
		userEntityBuilder.entity.setRoles(roles);

		return userEntityBuilder;
	}

	public UserEntityBuilder withFirstname(String firstname) {
		entity.setFirstname(firstname);
		return this;
	}

	public UserEntityBuilder withLastname(String lastname) {
		entity.setLastname(lastname);
		return this;
	}

	public UserEntityBuilder withEmail(String email) {
		entity.setEmail(email);
		return this;
	}

	public UserEntityBuilder withRole(RoleEntity role) {
		if (entity.getRoles() == null) {
			entity.setRoles(new ArrayList<RoleEntity>());
		}
		entity.getRoles().add(role);
		return this;
	}

	public UserEntity build() {
		return this.entity;
	}

	private static byte[] loadResource(String file) {

		try (InputStream is = UserEntityBuilder.class.getResourceAsStream(file)) {
			return IOUtils.readBytesFromStream(is);
		} catch (final IOException e) {
			return null;
		}

	}
}
