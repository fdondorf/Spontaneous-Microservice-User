package org.spontaneous.service.user.usermanagement.dataaccess.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ROLE")
public class RoleEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	public RoleEntity() {
		;
	}

	public RoleEntity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
