package org.spontaneous.service.user.usermanagement.dataaccess.api.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spontaneous.service.user.common.builder.UserEntityBuilder;
import org.spontaneous.service.user.general.dataaccess.api.Gender;
import org.spontaneous.service.user.usermanagement.dataaccess.api.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

/**
 * Unit Test for the User-Repository
 * 
 * @author fdondorf
 *
 */
@DataJpaTest
@TestPropertySource(properties = { "spring.config.location = classpath:application-test.yml" })
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@MockBean
	private ResourceServerProperties resourceServerProperties;

	@BeforeEach
	public void setUp() {
		userRepository.deleteAll();
	}

	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	public void testSaveUser() {

		// GIVEN
		UserEntity userEntity = UserEntityBuilder.aDefaultUserEntity().withFirstname("Florian").withLastname("Dondorf")
				.withEmail("test@test.de").withGender(Gender.MALE).build();

		// WHEN
		UserEntity saveUserEntity = userRepository.save(userEntity);

		// THEN
		assertEquals("Florian", saveUserEntity.getFirstname());
		assertEquals("Dondorf", saveUserEntity.getLastname());
		assertEquals(Gender.MALE, saveUserEntity.getGender());
		assertEquals("test@test.de", saveUserEntity.getEmail());
		assertNotNull(saveUserEntity.getUserId());
	}

	@Test
	public void testFindUserByEmail() {

		// Given (10 user)
		createUser();

		// When
		UserEntity foundUser = userRepository.findByEmail("test@test3.de");

		// Then
		assertNotNull(foundUser);
		assertEquals("test@test3.de", foundUser.getEmail());
		assertEquals("Firstname3", foundUser.getFirstname());
		assertEquals("Lastname3", foundUser.getLastname());
		assertEquals(Gender.FEMALE, foundUser.getGender());
		assertNotNull(foundUser.getImage());
		assertNotNull(foundUser.getUserId());

	}

	@Test
	public void testFindUserByLastname() {

		// Given (10 user)
		createUser();

		// When
		List<UserEntity> foundUser = userRepository.findByLastname("Lastname5");

		// Then
		assertNotNull(foundUser);
		assertEquals(1, foundUser.size());
		assertEquals("test@test5.de", foundUser.get(0).getEmail());
		assertEquals("Firstname5", foundUser.get(0).getFirstname());
		assertEquals("Lastname5", foundUser.get(0).getLastname());
		assertEquals(Gender.FEMALE, foundUser.get(0).getGender());
		assertNotNull(foundUser.get(0).getImage());
		assertNotNull(foundUser.get(0).getUserId());

	}

	private List<UserEntity> createUser() {
		List<UserEntity> user = new ArrayList<UserEntity>();
		UserEntity userEntity;
		for (int i = 0; i < 10; i++) {
			userEntity = userRepository.save(UserEntityBuilder.aDefaultUserEntity().withEmail("test@test" + i + ".de")
					.withFirstname("Firstname" + i).withGender(Gender.FEMALE).withLastname("Lastname" + i).build());
			user.add(userEntity);
		}

		return user;
	}
}
