package cts.mfpe.customer.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cts.mfpe.customer.entities.Customer;
import cts.mfpe.customer.entities.Property;
import cts.mfpe.customer.entities.Requirement;

@Service
public interface CustomerService {

	List<Requirement> getAllRequirements();
	
	List<Customer> getAllCustomers();
	
	void createCustomer(Customer customer) throws Exception;
	
	Customer getCustomerDetails(int id);
	
	List<Property> getAllProperties(String token) throws Exception;
	
	void assignRequirements(int custid, int reqid);
	
	boolean checkIfCustomerAlreadyExists(String customerName);
}
