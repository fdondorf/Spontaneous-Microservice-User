package org.spontaneous.service.user.usermanagement.service.api.rest;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * This class represents an authenticated Spontaneous User.
 *
 * @author fdondorf
 */
public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 6690403329729012515L;
	private User user;
	private UUID userId;

	/**
	 * Creates a AuthenticatedUser object
	 *
	 * @param userId      uuid of the user
	 * @param username    Username
	 * @param password    Password
	 * @param authorities Authorities
	 */
	public AuthenticatedUser(UUID userId, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UUID getUserId() {
		return userId;
	}

}
