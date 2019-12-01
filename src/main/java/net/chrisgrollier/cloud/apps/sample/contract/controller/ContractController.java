package net.chrisgrollier.cloud.apps.sample.contract.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.ApiOperation;
import net.chrisgrollier.cloud.apps.common.exception.service.EntityNotFoundUnrecoverableException;
import net.chrisgrollier.cloud.apps.common.log.LogUtil;
import net.chrisgrollier.cloud.apps.common.log.aop.Loggable;
import net.chrisgrollier.cloud.apps.sample.contract.model.Contract;
import net.chrisgrollier.cloud.apps.sample.contract.model.ContractInfo;
import net.chrisgrollier.cloud.apps.sample.contract.model.UserInfo;
import net.chrisgrollier.cloud.apps.sample.contract.service.ContractService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/contracts")
@Loggable(debug = true, service = "contractService")
public class ContractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);
	@Autowired
	private RestTemplate restTemplate;

	private final ContractService contractService;

	@Value("${usersservice.url}")
	private String usersServiceUrl;

	@Autowired
	public ContractController(final ContractService contractService) {
		this.contractService = contractService;
	}

	@ApiOperation("Find all contracts.")
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public Collection<Contract> getContracts() {
		return contractService.findAllContracts();
	}

	@ApiOperation("Find contract by the given identifier.")
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE, value = "/{id}")
	public ContractInfo getContractById(@RequestHeader HttpHeaders headers, @PathVariable("id") Integer contractId) {
		final Contract contract = contractService.findContract(contractId);
		final ContractInfo contractInfo = new ContractInfo(contract);
		// complete Contract by user data
		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.addAll("Authorization", headers.get("Authorization"));
		Boolean exist = restTemplate.exchange(usersServiceUrl + "/exists/id/" + contract.getUserId(), HttpMethod.GET,
				new HttpEntity<>(requestHeader), Boolean.class).getBody();
		if (exist) {
			UserInfo result = restTemplate.exchange(usersServiceUrl + "/" + contract.getUserId(), HttpMethod.GET,
					new HttpEntity<>(requestHeader), UserInfo.class).getBody();
			contractInfo.setUser(result);
		}
		return contractInfo;
	}

	@ApiOperation("Get contracts info for the given user ID .")
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE, value = "/userId/{userId}")
	public Collection<Contract> getContractsByUserId(@PathVariable("userId") Integer userId) {
		return contractService.findContractsByUserId(userId);
	}

	@ApiOperation("Add a new contract.")
	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public Contract addContract(@RequestHeader HttpHeaders headers,
			@RequestBody @Valid @NotNull final Contract contract) {
		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.addAll("Authorization", headers.get("Authorization"));
		String role = restTemplate.exchange(usersServiceUrl + "/role/" + contract.getUserId(), HttpMethod.GET,
				new HttpEntity<>(requestHeader), String.class).getBody();
		Contract newContract = contractService.addContract(contract, role);
		LogUtil.business(LOGGER, "Contract with id= {} has been created", newContract.getId());
		return newContract;
	}

	@ApiOperation("Update contract.")
	@PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_UTF8_VALUE, value = "/{id}")
	public Contract updateContract(@PathVariable("id") Integer id, @RequestBody @Valid @NotNull final Contract contract)
			throws EntityNotFoundUnrecoverableException {
		return contractService.updateContract(id, contract);
	}

	@ApiOperation("Delete contract.")
	@DeleteMapping(produces = APPLICATION_JSON_UTF8_VALUE, value = "/{id}")
	public void deleteContract(@PathVariable("id") Integer id) {
		contractService.deleteContract(id);
		LogUtil.business(LOGGER, "Contract with id= {} has been deleted", id);
	}

}
