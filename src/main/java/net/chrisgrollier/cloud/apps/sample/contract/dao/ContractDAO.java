package net.chrisgrollier.cloud.apps.sample.contract.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.chrisgrollier.cloud.apps.sample.contract.entity.ContractEntity;


/**
 * A basic contract DAO based spring data
 */
@Repository
public interface ContractDAO extends CrudRepository<ContractEntity, Integer> {
	
	Iterable<ContractEntity> findByUserId(Integer userId);

}
