package org.spontaneous.service.user.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService to load the user details from the database and store it in the UserDetails-object.
 * 
 * @author Florian Dondorf
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails userDetails = new User("test@test.de", this.passwordEncoder.encode("test"), AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));

		return userDetails;
	}

}