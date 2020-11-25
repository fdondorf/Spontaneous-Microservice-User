package org.spontaneous.service.user.usermanagement.service.impl.rest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.cxf.helpers.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spontaneous.service.user.AbstractSpontaneousIntegrationTest;
import org.spontaneous.service.user.UserBootApp;
import org.spontaneous.service.user.common.builder.UserEntityBuilder;
import org.spontaneous.service.user.general.dataaccess.api.Gender;
import org.spontaneous.service.user.general.service.api.rest.Header;
import org.spontaneous.service.user.general.service.api.rest.RegisterUserRequest;
import org.spontaneous.service.user.usermanagement.dataaccess.api.UserEntity;
import org.spontaneous.service.user.usermanagement.dataaccess.api.repo.UserRepository;
import org.spontaneous.service.user.usermanagement.service.api.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.dozermapper.core.Mapper;

/**
 * @author Flo Dondorf
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = UserBootApp.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
public class UserManagementControllerTest extends AbstractSpontaneousIntegrationTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Mapper mapper;

	private UserEntity user;

	@Override
	@BeforeEach
	public void setup() throws Exception {

		super.setup();

		// Delete all user
		this.userRepository.deleteAll();

		// Create user
		this.user = userRepository
				.save(UserEntityBuilder.aDefaultUserEntity(roleRepository.findById(1L).get()).build());

	}

	@AfterEach
	public void tearDown() {
		// Delete all user
		this.userRepository.deleteAll();
	}

	@Test
	public void registerTest() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/spontaneous/register")
						.content(this.json(new RegisterUserRequest("Flo", "Dondorf", "test@test.de", "password",
								Gender.MALE.getName())))
						.contentType(contentType))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void updateUserTest() throws Exception {

		// Given
		String token = getToken(this.user.getEmail(), "test"); // this.user.getPassword());

		// When
		UserEntity userEntity = UserEntityBuilder.aDefaultUserEntity(roleRepository.findById(1L).get()).build();
		userEntity = userRepository.save(userEntity);

		UpdateUserRequest userRequest = mapper.map(userEntity, UpdateUserRequest.class);
		userRequest = addHeader(userRequest, "android");
		userRequest.setFirstname("UpdatedFirstname");
		userRequest.setLastname("UpdatedLastname");
		userRequest.setGender(Gender.FEMALE.getName());
		userRequest.setImage(loadResource("/images/profile-image.jpg"));

		ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/secure/user/update")
				.with(bearerToken(token)).content(json(userRequest)).contentType(contentType));

		// Then
		result.andExpect(MockMvcResultMatchers.status().isOk());

		UserEntity savedUser = userRepository.findByEmail(userEntity.getEmail());
		Assertions.assertEquals(userRequest.getFirstname(), savedUser.getFirstname());
		Assertions.assertEquals(userRequest.getLastname(), savedUser.getLastname());
		Assertions.assertEquals(userRequest.getGender(), savedUser.getGender().getName());

		FileOutputStream fos = new FileOutputStream("test.jpg");
		fos.write(savedUser.getImage());
		fos.close();

		revokeToken(token);
	}

	@Test
	public void getUserInfoTest() throws Exception {

		// Given
		String token = getToken(this.user.getEmail(), "test"); // this.user.getPassword());

		Header header = new Header();
		header = addHeader(header, "android");

		// Get user info
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/spontaneous/secure/userinfo").with(bearerToken(token))
						.content(json(header)).contentType(contentType))
				.andExpect(MockMvcResultMatchers.status().isOk());

		revokeToken(token);
	}

	private static byte[] loadResource(String file) {
		try (InputStream is = UserEntityBuilder.class.getResourceAsStream(file)) {
			return IOUtils.readBytesFromStream(is);
		} catch (final IOException e) {
			return null;
		}
	}
}
