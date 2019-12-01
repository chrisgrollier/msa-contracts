package net.chrisgrollier.cloud.apps.sample.contract.model;

import javax.validation.constraints.NotNull;
import com.google.common.base.MoreObjects;

import io.swagger.annotations.ApiModelProperty;

public class ContractInfo extends Contract {

	@ApiModelProperty(position = 2, required = true, value = "The user firstName")
	@NotNull(message = "The user firstName is mandatory")
	private String firstName;

	@ApiModelProperty(position = 3, required = true, value = "The user lastName")
	@NotNull(message = "The user lastName is mandatory")
	private String lastName;

	@ApiModelProperty(position = 4, required = true, value = "The user role, possible values {ADMIN, USER}")
	@NotNull(message = "user role type is mandatory")
	private String role;

	@ApiModelProperty(position = 5, required = true, value = "The user Email")
	@NotNull(message = "The user email is mandatory")
	private String email;

	@ApiModelProperty(position = 6, value = "The user address")
	private String address;

	@ApiModelProperty(position = 7, required = true, value = "username")
	private String username;

	public ContractInfo(Contract contract) {
		this.setContract(contract);

	}

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

	public void setContract(Contract contract) {
		this.setId(contract.getId());
		this.setType(contract.getType());
		this.setDuration(contract.getDuration());
		this.setPrice(contract.getPrice());
		this.setUserId(contract.getUserId());
	}

	public void setUser(UserInfo user) {
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setRole(user.getRole());
		this.setEmail(user.getEmail());
		this.setAddress(user.getAddress());
		this.setUsername(user.getUsername());
	}

	@Override
	public String toString() {
		// @formatter:off
		return super.toString().concat(
				MoreObjects.toStringHelper(this)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("role", role)
				.add("email", email)
				.add("address", address)
				.add("username", username).toString());
		// @formatter:on
	}

}
