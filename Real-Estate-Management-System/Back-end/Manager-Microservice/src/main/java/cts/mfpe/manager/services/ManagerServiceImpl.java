package cts.mfpe.manager.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cts.mfpe.manager.clients.CustomerServiceClient;
import cts.mfpe.manager.entities.Customer;
import cts.mfpe.manager.entities.Executive;
import cts.mfpe.manager.exceptions.ExecutiveAlredyExistsException;
import cts.mfpe.manager.repos.ExecutiveRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private ExecutiveRepository executiveRepo;
	
	@Autowired
	private CustomerServiceClient customerClient;
	
	@Transactional
	@Override
	public void createExecutive(Executive executive) throws Exception{
		if(checkIfExecutiveAlreadyExists(executive.getName())) {
			throw new ExecutiveAlredyExistsException("Executive Already Exists");
		}
		executiveRepo.save(executive);
		log.info("Exceutive {} created", executive.toString());
	}

	@Override
	public Executive getExecutiveDetails(int id) throws Exception {
		Executive executive = executiveRepo.findById(id).get();
		log.info("Executive: {}", executive.toString());
		return executive;
	}
	
	@Override
	public List<Executive> getAllExecutives() {
		List<Executive> executives = executiveRepo.findAll();
		log.info("Exceutives: {}", executives);
		return executives;
	}

	@Override
	public List<Executive> getAllExecutivesByLocality(String locality) throws Exception{
		List<Executive> executives = executiveRepo.findByLocality(locality);
		log.info("Exceutives with locality {}: {}", locality, executives);
		return executives;
	}

	@Override
	public List<Customer> getAllCustomers(String token) throws Exception {
		List<Customer> customers = customerClient.getAllCustomers(token);
		log.info("Customers: {}", customers);
		return customers;
	}

	@Override
	public Customer getCustomerById(int id, String token) throws Exception{
		Customer customer = customerClient.getCustomerDetails(id, token);
		log.info("Customer: {}", customer.toString());
		return customer;
	}

	@Transactional
	@Override
	public void assignExecutive(int executiveid, int customerid, String token) throws Exception{
		Executive executive = executiveRepo.findById(executiveid).get();
		Customer customer = customerClient.getCustomerDetails(customerid, token);
		executive.getCustomers().add(customer);
		executiveRepo.save(executive);
		log.info("Customer with id {} assigned to Exceutive with id {}", customerid, executiveid);
	}

	public boolean checkIfExecutiveAlreadyExists(String executiveName) {
		return getAllExecutives().stream().anyMatch(e -> e.getName().equals(executiveName));
	}
	
}
