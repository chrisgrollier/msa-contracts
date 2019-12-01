package net.chrisgrollier.cloud.apps.sample.contract.service;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;

import net.chrisgrollier.cloud.apps.common.exception.UnrecoverableFunctionalException;
import net.chrisgrollier.cloud.apps.common.exception.service.EntityNotFoundUnrecoverableException;
import net.chrisgrollier.cloud.apps.common.i18n.MessageManager;
import net.chrisgrollier.cloud.apps.common.log.LogData;
import net.chrisgrollier.cloud.apps.common.log.LogUtil;
import net.chrisgrollier.cloud.apps.common.log.aop.Loggable;
import net.chrisgrollier.cloud.apps.common.util.mapping.BidiMapper;
import net.chrisgrollier.cloud.apps.sample.contract.dao.ContractDAO;
import net.chrisgrollier.cloud.apps.sample.contract.entity.ContractEntity;
import net.chrisgrollier.cloud.apps.sample.contract.model.Contract;

@Service
@Loggable(debug = true)
public class ContractService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractService.class);

	private static final String USER_ID = "userId";
	private final ContractDAO contractDAO;
	private final BidiMapper<Contract, ContractEntity> mapper;
	private final MessageManager messageManager;

	@Autowired
	public ContractService(final ContractDAO contractDAO,
			final BidiMapper<Contract, ContractEntity> contractEntityMapper, final MessageManager messageManager) {
		this.contractDAO = contractDAO;
		this.mapper = contractEntityMapper;
		this.messageManager = messageManager;
	}

	/**
	 * Find all existing contracts.
	 * 
	 * @return a collection of {@code Contract}
	 */
	public Collection<Contract> findAllContracts() {
		return mapper.froms(contractDAO.findAll());
	}

	/**
	 * Find contract by id.
	 * 
	 * @param id the contract identifier as {@code Integer}
	 * @return a retrieved {@code Contract}
	 */
	public Contract findContract(final Integer id) {
		LogData.currentBuilder().context(ImmutableMap.of("id", Integer.toString(id)));
		// Technical log with debug level and context data
		LogUtil.debug(LOGGER, "Trying to retrieve contract from data repository, id={}", id);
		this.messageManager.getMessage("test", id);
		return contractDAO.findById(id).map(mapper::from)
				.orElseThrow(() -> new EntityNotFoundUnrecoverableException("Could not find contract with id = {0}",
						"contract.not.found", id));
	}

	/**
	 * Find contracts associated to the given user ID.
	 * 
	 * @param userId the contract user identifier as {@code Integer}
	 * @return a retrieved {@code Contract}
	 */
	public Collection<Contract> findContractsByUserId(final Integer userId) {
		LogData.currentBuilder().context(ImmutableMap.of(USER_ID, Integer.toString(userId)));
		// Technical log with debug level
		LogUtil.debug(LOGGER, "Trying to retrieve contracts from data repository, userId={}", userId);
		final Collection<Contract> contracts = Optional.ofNullable(mapper.froms(contractDAO.findByUserId(userId)))
				.filter(a -> !a.isEmpty()).orElseThrow(() -> new EntityNotFoundUnrecoverableException(
						"Could not find contract for this userId = {0}", "contract.not.found.for.user", userId));
		// Technical log with debug level
		LogData.currentBuilder().context(ImmutableMap.of("contracts", contracts.toString()));
		LogUtil.debug(LOGGER, "Found contracts  [{}]", contracts);
		return contracts;
	}

	/**
	 * Saves a given contract to user with specific role User with USER role can
	 * only have LOA, LLD contracts type, User with ADMIN ROLE can only have LLD,
	 * VAC contracts type
	 * 
	 * @param contract the {@code Contract} to be added in database
	 * @param role     the {@code Role} to be added in database
	 * @return the saved contract
	 */
	@Transactional
	public Contract addContract(final Contract contract, String role) {
		String contractType = contract.getType().toString();
		if (("USER".equals(role) && "VAC".equals(contractType))
				|| ("ADMIN".equals(role) && "LOA".equals(contractType))) {
			LogUtil.business(LOGGER, ImmutableMap.of(USER_ID, Integer.toString(contract.getUserId())),
					"This contract type {} can't be added for this user {} role ", contract.getType(), role);
			throw new UnrecoverableFunctionalException("Contract type {0} can't be added by a user having {1} role",
					"add.contract.not.allowed", contract.getType(), role);
		}
		final Contract addedContract = mapper.from(contractDAO.save(mapper.to(contract)));
		LogUtil.business(LOGGER, ImmutableMap.of(USER_ID, Integer.toString(contract.getUserId())),
				"New contract with id {} has been added", addedContract.getId());
		return addedContract;
	}

	/**
	 * Updates the contract with the given id .
	 * 
	 * @param id       contract identifier
	 * @param contract the {@code Contract} to be updated.
	 * @return the saved contract
	 */
	@Transactional
	public Contract updateContract(final Integer id, final Contract contract) {
		ContractEntity contractEntity = contractDAO.findById(id)
				.orElseThrow(() -> new EntityNotFoundUnrecoverableException(
						"Unable to update contract with id = {0} cause could not find it", "update.contract.not.found",
						id));
		return mapper.from(contractDAO.save(mapper.copyTo(contract, contractEntity)));
	}

	/**
	 * Delete contract by id.
	 * 
	 * @param id the contract identifier as {@code Integer}
	 */
	@Transactional
	public void deleteContract(final Integer id) {
		try {
			contractDAO.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundUnrecoverableException(
					"Unable to delete contract with id = {0} cause could not find it", e, "delete.contract.not.found",
					id);
		}

	}
}
