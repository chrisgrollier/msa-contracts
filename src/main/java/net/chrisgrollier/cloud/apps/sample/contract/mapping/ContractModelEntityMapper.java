package net.chrisgrollier.cloud.apps.sample.contract.mapping;

import org.springframework.stereotype.Component;

import net.chrisgrollier.cloud.apps.common.util.mapping.AbstractBidiMapper;
import net.chrisgrollier.cloud.apps.sample.contract.entity.ContractEntity;
import net.chrisgrollier.cloud.apps.sample.contract.model.Contract;

@Component
public class ContractModelEntityMapper extends AbstractBidiMapper<Contract, ContractEntity> {

	public ContractModelEntityMapper() {
		super(Contract.class, ContractEntity.class);
	}

	@Override
	public Contract copyFrom(Contract t, ContractEntity s) {
		t.setId(s.getId());
		t.setDuration(s.getDuration());
		t.setPrice(s.getPrice());
		t.setType(s.getType());
		t.setUserId(s.getUserId());
		return t;
	}

	@Override
	public ContractEntity copyTo(Contract t, ContractEntity s) {
		s.setDuration(t.getDuration());
		s.setPrice(t.getPrice());
		s.setType(t.getType());
		s.setUserId(t.getUserId());
		return s;
	}

}
