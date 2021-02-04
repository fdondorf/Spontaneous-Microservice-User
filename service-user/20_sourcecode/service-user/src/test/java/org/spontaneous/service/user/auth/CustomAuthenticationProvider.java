package org.spontaneous.service.user.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final String KEY_USER_ID = "userId";

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		Map<String, Object> details = new HashMap<String, Object>();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(name,
				passwordEncoder.encode(password), AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
		details.put(KEY_USER_ID, UUID.randomUUID());
		authToken.setDetails(details);

		return authToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}