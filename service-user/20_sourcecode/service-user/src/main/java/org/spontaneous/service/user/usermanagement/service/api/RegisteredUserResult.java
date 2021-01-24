package org.spontaneous.service.user.usermanagement.service.api;

public class RegisteredUserResult {

	private Long id;
	private String userId;

	public RegisteredUserResult(Long id, String userId) {
		super();
		this.id = id;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
