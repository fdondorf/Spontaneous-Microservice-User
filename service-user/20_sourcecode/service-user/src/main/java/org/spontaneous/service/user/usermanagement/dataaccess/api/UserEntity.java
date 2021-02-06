package org.spontaneous.service.user.usermanagement.dataaccess.api;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.spontaneous.service.user.general.dataaccess.api.Gender;

@Entity
@Table(name = "USERS")
public class UserEntity implements User, Serializable {

	private static final long serialVersionUID = -7447873502288352919L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "user_id")
	@Type(type = "uuid-char")
	private UUID userId;

	private String firstname;

	private String lastname;

	@Column(unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Lob
	@Column(length = 100000)
	private byte[] image;

	public UserEntity() {
		this.userId = UUID.randomUUID();
	}

	@Override
	public Long getId() {
		return id;
	}

	public UUID getUserId() {
		return userId;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
