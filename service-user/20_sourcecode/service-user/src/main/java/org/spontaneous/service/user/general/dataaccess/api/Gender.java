package org.spontaneous.service.user.general.dataaccess.api;

import java.io.Serializable;

public enum Gender implements Serializable {

	MALE("MALE"),
	FEMALE("FEMALE");
	
	private final String name;

	/**
	 * Instantiates a new Gender type
	 *
	 * @param id the id
	 */
	Gender(String name) {
	  this.name = name;
	}

	public String getName() {
	  return name;
	}

	/**
	 * Get a valid gender type for the given name.
	 *
	 * @param name
	 * @return a valid gender type
	 */
	public static Gender fromName(String name) {
	  for (Gender gender : Gender.values()) {
	    if (gender.name.equals(name)) {
	      return gender;
	    }
	  }
	  throw new IllegalArgumentException("Not a valid  gender name: " + name);
	}

}
