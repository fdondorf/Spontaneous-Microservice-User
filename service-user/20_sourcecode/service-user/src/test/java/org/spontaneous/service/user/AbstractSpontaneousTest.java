package org.spontaneous.service.user;

import java.io.IOException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;

public abstract class AbstractSpontaneousTest {

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		Assertions.assertNotNull(this.mappingJackson2HttpMessageConverter,
				"The JSON message converter must not be null");
	}

	@SuppressWarnings("unchecked")
	public String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		String jsonString = mockHttpOutputMessage.getBodyAsString();
		return jsonString;
	}

	public JSONObject stringToJSON(String stringToParse) throws JSONException {
		JSONObject json = (JSONObject) JSONParser.parseJSON(stringToParse);

		return json;
	}

}
