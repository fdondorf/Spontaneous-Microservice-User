package org.spontaneous.service.user.usermanagement.service.impl.rest;

import java.nio.charset.Charset;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.spontaneous.service.user.AbstractIntegrationTest;
import org.spontaneous.service.user.UserBootApp;
import org.spontaneous.service.user.common.builder.UserEntityBuilder;
import org.spontaneous.service.user.general.dataaccess.api.Gender;
import org.spontaneous.service.user.general.service.api.rest.RegisterUserRequest;
import org.spontaneous.service.user.usermanagement.dataaccess.api.repo.UserRepository;
import org.spontaneous.service.user.usermanagement.service.api.UserDto;
import org.spontaneous.service.user.usermanagement.service.api.rest.AuthServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.dozermapper.core.Mapper;

/**
 * @author Flo Dondorf
 */
@SpringBootTest
@ContextConfiguration(classes = UserBootApp.class, initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration
@TestPropertySource(properties = { "spring.config.location = classpath:application-test.yml" })
public class RegisterControllerTest extends AbstractIntegrationTest {

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
	public void registerTest() throws Exception {

		// GIVEN
		UserDto userTO = mapper.map(UserEntityBuilder.aDefaultUserEntity().build(), UserDto.class);
		Mockito.when(authServiceClientMock.createUser(Mockito.any())).thenReturn(userTO);

		RegisterUserRequest registerUserRequest = new RegisterUserRequest("Flo", "Dondorf", EMAIL_TEST_USER, "password",
				Gender.MALE.getName());
		addHeader(registerUserRequest, "android");
		this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/register").content(this.json(registerUserRequest))
				.contentType(contentType)).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
