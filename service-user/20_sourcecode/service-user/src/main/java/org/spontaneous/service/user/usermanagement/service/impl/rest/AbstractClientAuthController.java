package org.spontaneous.service.user.usermanagement.service.impl.rest;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.service.user.general.service.api.rest.AuthenticatedUser;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * Superclass for all client controllers used for the mobile client requests.
 * <p>
 * Common functionality to get a logged in user and to validate header data.
 *
 * @author Florian Dondorf
 */
public class AbstractClientAuthController extends AbstractClientController {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractClientAuthController.class);
	
	  /**
	   * Get the authenticated user.
	   * 
	   * @param principal - a spring-security principal
	   * @return an authenticated {@link AuthenticatedUser}
	   */
	 public OAuth2Authentication getAuthUser(Principal principal) {
		  
		 LOG.info("Calling getAuthUser...");
		  
		 //AuthenticatedUser user = null;
		 OAuth2Authentication authentication = null;
		 if (principal instanceof OAuth2Authentication) {
			 authentication = (OAuth2Authentication) principal;
//			 if (authentication.getPrincipal() instanceof AuthenticatedUser) {
//				 user = (AuthenticatedUser) authentication.getPrincipal();
//			 }
		 }

		 if (authentication == null) {
			 throw new UnauthorizedClientException("No logged in user found");
		 }
		 return authentication;
	  }
	 
}
