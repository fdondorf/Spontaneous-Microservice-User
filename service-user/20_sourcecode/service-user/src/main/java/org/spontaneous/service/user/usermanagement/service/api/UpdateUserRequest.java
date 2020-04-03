package org.spontaneous.service.user.usermanagement.service.api;

import org.spontaneous.service.user.general.service.api.rest.Header;

/**
 * Class containing the data within a userinfo request
 * 
 * @author Florian Dondorf
 *
 */
public class UpdateUserRequest extends Header {

	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private String gender;
	private byte[] image;

	public UpdateUserRequest() {
		;
	}

	public UpdateUserRequest(Long id, String firstname, String lastname, String email, String gender, byte[] image) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.gender = gender;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
