package org.spontaneous.service.user.usermanagement.dataaccess.api;

public interface User {

	  /**
	   * the id for the user
	   * 
	   * @return the id for the user
	   */
	  Long getId();

	  /**
	   * the first name of the user
	   * 
	   * @return the first name of the user
	   */
	  String getFirstname();

	  /**
	   * the last name of the user
	   * 
	   * @return the last name of the user
	   */
	  String getLastname();

	  /**
	   * the email address of the user
	   * 
	   * @return the email address of the user
	   */
	  String getEmail();
}
