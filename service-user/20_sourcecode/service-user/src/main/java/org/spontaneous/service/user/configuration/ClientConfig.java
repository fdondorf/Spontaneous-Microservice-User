package org.spontaneous.service.user.configuration;

import org.spontaneous.service.user.general.service.api.ClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Client config.
 */
@Configuration
public class ClientConfig {

	/**
	 * Defines the androidClientProperties bean.
	 *
	 * @return the androidClientProperties bean
	 */
	@Bean
	@ConfigurationProperties(prefix = "spontaneous.client.android")
	public ClientProperties androidClientProperties() {
	  return new ClientProperties();
	}

//  /**
//   * Defines the revokeTokenController bean.
//   *
//   * @return the revokeTokenController bean
//   */
//  @Bean
//  public RevokeTokenController revokeTokenController() {
//    return new RevokeTokenController();
//  }
//
//  /**
//   * Defines the userManagementController bean.
//   *
//   * @return the userManagementController bean
//   */
//  @Bean
//  public UserManagementController userManagementController() {
//    return new UserManagementController();
//  }
//
//  /**
//   * Defines the trackManagementController bean.
//   *
//   * @return the trackManagementController bean
//   */
//  @Bean
//  public TrackManagementController trackManagementController() {
//    return new TrackManagementController();
//  }
//  
//  /**
//   * Defines the registerController bean.
//   *
//   * @return the registerController bean
//   */
//  @Bean
//  public RegisterController registerController() {
//    return new RegisterController();
//  }
//
//  /**
//   * Defines the registerController bean.
//   *
//   * @return the registerController bean
//   */
//  @Bean
//  public TestServerPingController pingController() {
//    return new TestServerPingController();
//  }
//  
}
