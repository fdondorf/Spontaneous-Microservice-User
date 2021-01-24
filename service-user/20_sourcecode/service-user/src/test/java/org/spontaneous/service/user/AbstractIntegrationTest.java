package org.spontaneous.service.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.servlet.Filter;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.skyscreamer.jsonassert.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.service.user.general.service.api.ClientProperties;
import org.spontaneous.service.user.general.service.api.rest.AppSystemType;
import org.spontaneous.service.user.general.service.api.rest.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Abstract class for integrative Unit Tests
 * 
 * @author Florian Dondorf
 *
 */
public abstract class AbstractIntegrationTest {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractIntegrationTest.class);

	@Autowired
	private ClientProperties androidClientProperties;

	@Autowired
	private ClientProperties iosClientProperties;

	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	protected MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Autowired
	private Filter springSecurityFilterChain;

	@Autowired
	protected ConsumerTokenServices consumerTokenServices;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain)
				.build();
	}

	protected <T extends Header> T addHeader(T header, String appSystem) {
		header.setApiVersion(androidClientProperties.getApiVersion());
		header.setAppVersion(androidClientProperties.getRecommendedAppVersion());
		header.setAppKey(androidClientProperties.getValidAppKeys().get(0));
		header.setAppSystem(appSystem);

		return header;
	}

	protected ClientProperties getClientProperties(String appSystemString) {
		AppSystemType appSystem = AppSystemType.fromName(appSystemString);
		switch (appSystem) {
		case ANDROID:
			return androidClientProperties;
		case IOS:
			return iosClientProperties;
		default:
			throw new IllegalArgumentException("not a valid app system " + appSystem);
		}
	}

	protected String getToken(String username, String password) throws Exception {
		ResultActions resultLogin = this.mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
				.with(httpBasic("testId", "testSecret")).param("client_id", "testId")
				.param("client_secret", "testSecret").param("grant_type", "password").param("username", username)
				.param("password", password).contentType(contentType));

		// Assertions
		resultLogin.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("bearer")))
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("access_token")))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Get token
		String tokenRepsonse = resultLogin.andReturn().getResponse().getContentAsString();
		String token = tokenRepsonse.substring(tokenRepsonse.indexOf(':') + 2, tokenRepsonse.indexOf(',') - 1);

		return token;

	}

	protected void revokeToken(String token) throws Exception {

		boolean revokeResult = consumerTokenServices.revokeToken(token);
		if (revokeResult) {
			LOG.debug("Token {} revoked.", token);
		} else {
			LOG.error("Token {} is already revoked.", token);
		}

		// Assertions
		assertTrue(revokeResult);
	}

	protected RequestPostProcessor bearerToken(final String token) {
		return mockRequest -> {
			mockRequest.addHeader("Authorization", "Bearer " + token);
			return mockRequest;
		};
	}

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		Assertions.assertNotNull(this.mappingJackson2HttpMessageConverter,
				"The JSON message converter must not be null");
	}

	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		String jsonString = mockHttpOutputMessage.getBodyAsString();
		return jsonString;
	}

	protected JSONObject stringToJSON(String stringToParse) throws JSONException {
		JSONObject json = (JSONObject) JSONParser.parseJSON(stringToParse);

		return json;
	}

}
