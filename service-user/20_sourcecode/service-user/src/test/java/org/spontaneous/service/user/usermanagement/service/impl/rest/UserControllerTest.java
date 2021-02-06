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
import org.spontaneous.service.user.AbstractIntegrationTest;
import org.spontaneous.service.user.UserBootApp;
import org.spontaneous.service.user.common.builder.UserEntityBuilder;
import org.spontaneous.service.user.general.dataaccess.api.Gender;
import org.spontaneous.service.user.general.service.api.rest.Header;
import org.spontaneous.service.user.usermanagement.dataaccess.api.UserEntity;
import org.spontaneous.service.user.usermanagement.dataaccess.api.repo.UserRepository;
import org.spontaneous.service.user.usermanagement.service.api.UpdateUserRequest;
import org.spontaneous.service.user.usermanagement.service.api.rest.AuthServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@SpringBootTest
@ContextConfiguration(classes = UserBootApp.class, initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration
@TestPropertySource(properties = { "spring.config.location = classpath:application-integration-test.yml" })
public class UserControllerTest extends AbstractIntegrationTest {

	private static final String PASSWORD_TEST_USER = "test";

	private static final String FIRSTNAME_TEST_USER = "TestFirstname";

	private static final String LASTNAME_TEST_USER = "TestLastname";

	private static final String EMAIL_TEST_USER = "test@test.de";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Mapper mapper;

	@MockBean
	private AuthServiceClient authServiceClientMock;

	@Override
	@BeforeEach
	public void setup() throws Exception {

		super.setup();

		// Delete all user
		this.userRepository.deleteAll();

	}

	@AfterEach
	public void tearDown() {
		// Delete all user
		this.userRepository.deleteAll();
	}

	@Test
	public void updateUserTest() throws Exception {

		// Given
		String token = getToken(EMAIL_TEST_USER, PASSWORD_TEST_USER);
		UserEntity userEntity = UserEntityBuilder.aDefaultUserEntity().build();
		userEntity = userRepository.save(userEntity);

		// When
		UpdateUserRequest userRequest = mapper.map(userEntity, UpdateUserRequest.class);
		userRequest = addHeader(userRequest, "android");
		userRequest.setFirstname("UpdatedFirstname");
		userRequest.setLastname("UpdatedLastname");
		userRequest.setGender(Gender.FEMALE.getName());
		userRequest.setImage(loadResource("/images/profile-image.jpg"));

		ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/secure/v1/user/update")
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
		UserEntity userEntity = UserEntityBuilder.aDefaultUserEntity().withEmail(EMAIL_TEST_USER)
				.withLastname(LASTNAME_TEST_USER).withLastname(FIRSTNAME_TEST_USER).build();
		userEntity = userRepository.save(userEntity);
		String token = getToken(EMAIL_TEST_USER, PASSWORD_TEST_USER);

		Header header = new Header();
		header = addHeader(header, "android");

		// Get user info
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/secure/v1/userinfo").with(bearerToken(token))
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
