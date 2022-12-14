package cts.mfpe.customer.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cts.mfpe.customer.clients.PropertyServiceClient;
import cts.mfpe.customer.entities.Customer;
import cts.mfpe.customer.entities.Property;
import cts.mfpe.customer.entities.Requirement;
import cts.mfpe.customer.exceptions.CustomerAlredyExistsException;
import cts.mfpe.customer.repos.CustomerRepository;
import cts.mfpe.customer.repos.RequirementRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private RequirementRepository requirementRepo;
	
	@Autowired
	private PropertyServiceClient propertyClient;
	
	@Override
	public List<Requirement> getAllRequirements(){
		List<Requirement> requirements = requirementRepo.findAll();
		return requirements;
	}
	
	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerRepo.findAll();
		return customers;
	}
	
	@Transactional
	@Override
	public void createCustomer(Customer customer) throws Exception{
		if(checkIfCustomerAlreadyExists(customer.getName())) {
			throw new CustomerAlredyExistsException("Customer already exists!");
		}
		customerRepo.save(customer);
	}
	
	@Override
	public Customer getCustomerDetails(int id) {
		Optional<Customer> result = customerRepo.findById(id);
		Customer customer = null;
		if(result.isPresent()) {
			customer=result.get();
		}
		return customer;
	}
	
	@Override
	public List<Property> getAllProperties(String token) throws Exception{
		List<Property> properties = propertyClient.getAllProperties(token);
		return properties;
	}

	@Transactional
	@Override
	public void assignRequirements(int custid, int reqid) {
		Customer customer = customerRepo.findById(custid).get();
		Requirement requirement = requirementRepo.findById(reqid).get();
		customer.getRequirements().add(requirement);
		customerRepo.save(customer);
	}
	
	@Override
	public boolean checkIfCustomerAlreadyExists(String customerName) {
		return getAllCustomers().stream().anyMatch(c -> c.getName().equals(customerName));
	}
	
}
