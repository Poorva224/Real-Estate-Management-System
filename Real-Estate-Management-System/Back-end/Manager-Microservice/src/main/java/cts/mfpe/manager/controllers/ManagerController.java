package cts.mfpe.manager.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cts.mfpe.manager.clients.AuthorizationClient;
import cts.mfpe.manager.entities.Customer;
import cts.mfpe.manager.entities.Executive;
import cts.mfpe.manager.exceptions.AuthorizationException;
import cts.mfpe.manager.exceptions.CustomerNotFoundException;
import cts.mfpe.manager.exceptions.ExecutiveNotFoundException;
import cts.mfpe.manager.services.ManagerService;

//Project is developed by INTCDE22IJ046 - POD - 4

@RestController
@CrossOrigin
public class ManagerController {

	@Autowired
	private ManagerService executiveService;

	@Autowired
	private AuthorizationClient authorizationClient;

	//Creating new Executive.
	@PostMapping("/createExecutive")
	public ResponseEntity<String> createExecutive(@RequestBody @Valid Executive executive,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws Exception {
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			executiveService.createExecutive(executive);
			return new ResponseEntity<String>("Executive Created Successfully!", HttpStatus.CREATED);
		}
		throw new AuthorizationException("Not Allowed");
	}

	//Get Executive details through Id.
	@GetMapping("/getExecutiveDetails/{id}")
	public ResponseEntity<Executive> getExecutiveDetails(@PathVariable int id,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws Exception {
		Executive executive = executiveService.getExecutiveDetails(id);
		if (executive == null) {
			throw new CustomerNotFoundException("Executive with id " + id + " not found");
		}
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			return ResponseEntity.ok(executive);
		}
		throw new AuthorizationException("Not Allowed");
	}
	
	//Get All Executive
	@GetMapping("/getAllExecutives")
	public ResponseEntity<List<Executive>> getAllExecutives(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException {
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			return ResponseEntity.ok(executiveService.getAllExecutives());
		}
		throw new AuthorizationException("Not Allowed");
	}

	//Get all Executive based on their Locality.
	@GetMapping("/getAllExecutivesByLocality/{locality}")
	public ResponseEntity<List<Executive>> getAllExecutivesByLocality(@PathVariable String locality,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws Exception {
		List<Executive> executives = executiveService.getAllExecutivesByLocality(locality);
		if (executives.size() == 0) {
			throw new ExecutiveNotFoundException("Executives in locality " + locality + " not found");
		}
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			return ResponseEntity.ok(executives);
		}
		throw new AuthorizationException("Not Allowed");
	}

	//Get All Customers.
	@GetMapping("/getAllCustomers")
	public ResponseEntity<List<Customer>> getAllCustomers(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws Exception {
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			return ResponseEntity.ok(executiveService.getAllCustomers(requestTokenHeader));
		}
		throw new AuthorizationException("Not Allowed");
	}

	//Get Customer through Id.
	@GetMapping("/getCustomersById/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws Exception {
		Customer customer = executiveService.getCustomerById(id, requestTokenHeader);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer with id " + id + " not found");
		}
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			return ResponseEntity.ok(customer);
		}
		throw new AuthorizationException("Not Allowed");
	}

	//Assigning Executive with Customers.
	@PutMapping("/{executiveId}/assignExecutive/{customerId}")
	public ResponseEntity<String> assignExecutive(@PathVariable("executiveId") int execid,
			@PathVariable("customerId") int custid,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws Exception {
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			executiveService.assignExecutive(execid, custid, requestTokenHeader);
			return new ResponseEntity<>("Executive Assigned Successfully!", HttpStatus.OK);
		}
		throw new AuthorizationException("Not Allowed");
	}
}
