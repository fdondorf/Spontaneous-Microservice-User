package org.spontaneous.service.user.usermanagement.service.api;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

	private Long userId;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String gender;
	private byte[] image;
	private List<String> roles;

	public UserDto() {
		super();
	}

	// public UserDto(String firstname, String lastname, String email, String
	// password, String gender) {
	// super();
	// this.firstname = firstname;
	// this.lastname = lastname;
	// this.email = email;
	// this.password = password;
	// this.gender = gender;
	// }
	//
	// public UserDto(Long id, String firstname, String lastname, String email,
	// String password, String gender) {
	// super();
	// this.userId = id;
	// this.firstname = firstname;
	// this.lastname = lastname;
	// this.email = email;
	// this.password = password;
	// this.gender = gender;
	// }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<String> getRoles() {
		if (this.roles == null) {
			this.roles = new ArrayList<String>();
		}
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
