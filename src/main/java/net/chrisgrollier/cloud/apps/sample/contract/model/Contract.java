package net.chrisgrollier.cloud.apps.sample.contract.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;

import io.swagger.annotations.ApiModelProperty;
import net.chrisgrollier.cloud.apps.sample.contract.entity.ContractType;

public class Contract {

	@ApiModelProperty(position = 1, required = false, value = "The contract identifier")
	private Integer id;

	@ApiModelProperty(position = 2, required = true, value = "The contract type, possible values {LOA, VAC, LLD}")
	@NotNull(message = "{contract.validation.type.mandatory}")
	private ContractType type;

	@ApiModelProperty(position = 3, required = true, value = "The contract duration in months")
	@NotNull(message = "{contract.validation.duration.mandatory}")
	@Max(value = 36, message = "{contract.validation.duration.maxed}")
	private Integer duration;

	@ApiModelProperty(position = 4, required = true, value = "The monthly contract price ")
	@NotNull(message = "{contract.validation.price.mandatory}")
	private Double price;

	@ApiModelProperty(position = 4, required = true, value = "The contract user ID ")
	@NotNull(message = "{contract.validation.userid.mandatory}")
	private Integer userId;

	public ContractType getType() {
		return type;
	}

	public void setType(ContractType type) {
		this.type = type;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		// @formatter:off
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("type", type)
                          .add("duration", duration)
                          .add("price", price)
                          .add("userId", userId)
                          .toString();
        // @formatter:on
	}

}
