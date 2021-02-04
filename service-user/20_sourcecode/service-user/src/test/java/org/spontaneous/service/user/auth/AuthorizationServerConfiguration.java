package org.spontaneous.service.user.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Configuration for the OAuth2 AuthorizationServer.
 * <p>
 * The AuthorizationServer uses an InMemoryTokenStore and the
 * {@link TimeToLiveTokenServices}. The AuthenticationManager needs to be set
 * for the AuthorizationServerEndpoint, otherwise you cannot use the "password"
 * grant. It uses the URL "/spontaneous/secure/auth/token" for the
 * AuthorizationServerEndpoint for the token-request.
 *
 * @author Florian Dondorf
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Value("${security.oauth2.resourceId}")
	private String resourceId;

	@Value("${security.oauth2.clientId}")
	private String clientId;

	@Value("${security.oauth2.clientSecret}")
	private String clientSecret;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		String encodedClientSecret = this.passwordEncoder.encode(this.clientSecret);

		clients.inMemory().withClient(this.clientId).secret(encodedClientSecret).authorizedGrantTypes("password")
				.authorities("ROLE_USER", "ROLE_ADMIN", "ROLE_SUPERADMIN").scopes("tracking")
				.resourceIds(this.resourceId);
	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.passwordEncoder(this.passwordEncoder).allowFormAuthenticationForClients();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints.tokenStore(tokenStore()).authenticationManager(this.authenticationManager)
				.tokenEnhancer(tokenEnhancer());
		// .userDetailsService(this.userDetailsService); // .pathMapping("/oauth/token",
		// "/spontaneous/secure/auth/token");
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	@Bean
	@Primary
	public AuthorizationServerTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setTokenEnhancer(tokenEnhancer());
		return tokenServices;
	}

}
