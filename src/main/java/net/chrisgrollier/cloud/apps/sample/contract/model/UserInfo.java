package net.chrisgrollier.cloud.apps.sample.contract.model;

import com.google.common.base.MoreObjects;

public class UserInfo {

	private String firstName;
	private String lastName;
	private String role;
	private String email;
	private String address;
	private String username;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		// @formatter:off
        return super.toString()
        			.concat(MoreObjects.toStringHelper(this)
                          .add("firstName", firstName)
                          .add("lastName", lastName)
                          .add("role", role)
                          .add("email", email)
                          .add("address", address)
                          .add("username", username)
                          .toString());
        // @formatter:on
	}

}
