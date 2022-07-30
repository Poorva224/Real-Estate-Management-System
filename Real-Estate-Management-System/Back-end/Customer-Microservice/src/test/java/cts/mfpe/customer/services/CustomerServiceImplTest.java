package cts.mfpe.customer.services;

import cts.mfpe.customer.clients.PropertyServiceClient;
import cts.mfpe.customer.entities.Customer;
import cts.mfpe.customer.entities.Requirement;
import cts.mfpe.customer.exceptions.CustomerAlredyExistsException;
import cts.mfpe.customer.repos.CustomerRepository;
import cts.mfpe.customer.repos.RequirementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerServiceImpl;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    RequirementRepository requirementRepository;

    @Mock
    PropertyServiceClient propertyServiceClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRequirements() {
        when(requirementRepository.findAll()).thenReturn(new ArrayList<>());
        customerServiceImpl.getAllRequirements();
        verify(requirementRepository, times(1)).findAll();
    }

    private Requirement mockRequirement() {
        Requirement r = new Requirement();
        r.setId(1);
        r.setLocality("Park");
        r.setPropertyType("Villa");
        r.setBudget(500000.0);
        return r;
    }

    @Test
    void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        customerServiceImpl.getAllCustomers();
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void createCustomerWhenCustomerDoesNotExist() throws Exception {
        Customer mockCustomer = mockCustomer();
        customerServiceImpl.createCustomer(mockCustomer);
        verify(customerRepository, times(1)).save(mockCustomer);
    }

    @Test
    void createCustomerWhenCustomerAlreadyExists() {
        Customer customer = mockCustomer();
        when(customerServiceImpl.getAllCustomers()).thenReturn(mockCustomerList());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        assertThrows(CustomerAlredyExistsException.class, () -> customerServiceImpl.createCustomer(mockCustomer()));
    }

    private List<Customer> mockCustomerList() {
        List<Customer> l = new ArrayList<>();
        l.add(mockCustomer());
        return l;
    }

    @Test
    void getCustomerDetails_result_is_not_empty() {
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(mockCustomer()));
        assertEquals(mockCustomer(), customerServiceImpl.getCustomerDetails(1));

    }

    @Test
    void getCustomerDetails_result_is_empty() {
        when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertNull(customerServiceImpl.getCustomerDetails(1));

    }

    @Test
    void getAllProperties() throws Exception {
        when(propertyServiceClient.getAllProperties(anyString())).thenReturn(new ArrayList<>());
        String requestTokenHeader = "ewgc4fjkjjkjr34f34r";
        customerServiceImpl.getAllProperties(requestTokenHeader);
        verify(propertyServiceClient, times(1)).getAllProperties(requestTokenHeader);
    }

    @Test
    void assignRequirements() {
        Customer c = mockCustomer();
        Requirement r = mockRequirement();
        when(customerRepository.findById(Optional.of(anyInt()).get())).thenReturn(Optional.of(mockCustomer()));
        when(requirementRepository.findById(Optional.of(anyInt()).get())).thenReturn(Optional.of(r));
        when(customerRepository.save(any(Customer.class))).thenReturn(c);
        customerServiceImpl.assignRequirements(1, 1);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    private Customer mockCustomer() {
        Customer c = new Customer();
        c.setId(1);
        c.setName("ABCD");
        c.setEmailid("test@example.com");
        c.setAddress("VVIP Mall");
        c.setContactNumber(987654321L);

        c.setRequirements(new HashSet<>(Set.of(new Requirement())));
        return c;
    }
}