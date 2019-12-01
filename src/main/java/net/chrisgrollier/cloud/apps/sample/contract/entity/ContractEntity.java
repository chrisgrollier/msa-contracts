/*
 * Creation : 24 Jun 2019
 */
package net.chrisgrollier.cloud.apps.sample.contract.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "CONTRACT")
public class ContractEntity {

    /** The contract identifier */
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Integer id;

    /** The contract type, possible values {LOA, VAC, LLD} */
    @Column(name = "TYPE", length = 64, nullable = false)
    private ContractType type;

    /** The contract duration in months */
    @Column(name = "DURATION", nullable = false)
    private Integer duration;

    /** The monthly contract price */
    @Column(name = "PRICE", nullable = false)
    private Double price;
    
    /** The contract user ID */
    @Column(name = "USERID", nullable = false)
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
