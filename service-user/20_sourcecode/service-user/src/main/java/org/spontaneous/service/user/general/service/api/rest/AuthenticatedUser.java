package org.spontaneous.service.user.general.service.api.rest;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * This class represents an authenticated Spontaneous User.
 *
 * @author fdondorf
 */
public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {

  private static final long serialVersionUID = 6690403329729012515L;
  //private User user;

  /**
   * Creates a AuthenticatedUser object
   *
   * @param username Username
   * @param password Password
   * @param authorities Authorities
   */
  public AuthenticatedUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

//  public User getUser() {
//    return user;
//  }
//
//  public void setUser(User user) {
//    this.user = user;
//  }

}
