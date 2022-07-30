package cts.mfpe.manager.services;

import cts.mfpe.manager.clients.CustomerServiceClient;
import cts.mfpe.manager.entities.Customer;
import cts.mfpe.manager.entities.Executive;
import cts.mfpe.manager.exceptions.ExecutiveAlredyExistsException;
import cts.mfpe.manager.repos.ExecutiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ManagerServiceImplTest {

    @InjectMocks
    ManagerServiceImpl managerServiceImpl;

    @Mock
    ExecutiveRepository executiveRepository;

    @Mock
    CustomerServiceClient customerServiceClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createExecutiveWhenExecutiveDoesNotExist() throws Exception {
        Executive executive = mockExecutive();
        managerServiceImpl.createExecutive(executive);
        verify(executiveRepository, times(1)).save(executive);
    }

    @Test
    void createExecutiveWhenExecutiveExist() {
        Executive executive = mockExecutive();
        when(managerServiceImpl.getAllExecutives()).thenReturn(mockExecutiveList());
        when(executiveRepository.save(any(Executive.class))).thenReturn(executive);
        assertThrows(ExecutiveAlredyExistsException.class, () -> managerServiceImpl.createExecutive(mockExecutive()));
    }

    private List<Executive> mockExecutiveList() {
        List<Executive> l = new ArrayList<>();
        l.add(mockExecutive());
        return l;
    }

    private Executive mockExecutive() {
        Executive e = new Executive();
        e.setId(1);
        e.setName("ABCD");
        e.setLocality("VVIP Mall");
        e.setContactNumber(987654321L);
        e.setEmailId("test@example.com");
        e.setCustomers(new HashSet<Customer>(Set.of(new Customer())));
        return e;
    }

    @Test
    void getAllExecutives() {
        when(executiveRepository.findAll()).thenReturn(new ArrayList<Executive>());
        managerServiceImpl.getAllExecutives();
        verify(executiveRepository, times(1)).findAll();
    }

    @Test
    void getAllExecutivesByLocality() throws Exception {
        when(executiveRepository.findByLocality(anyString())).thenReturn(new ArrayList<Executive>());
        managerServiceImpl.getAllExecutivesByLocality(anyString());
        verify(executiveRepository, times(1)).findByLocality("");
    }

    @Test
    void getAllCustomers() throws Exception {
        when(customerServiceClient.getAllCustomers(anyString())).thenReturn(new ArrayList<Customer>());
        managerServiceImpl.getAllCustomers("");
        verify(customerServiceClient, times(1)).getAllCustomers("");
    }

    @Test
    void getCustomerById() throws Exception {

        when(customerServiceClient.getCustomerDetails(anyInt(), anyString())).thenReturn(mockCustomer());
        assertEquals(mockCustomer(), managerServiceImpl.getCustomerById(1, "tokenxurn43iqr"));
    }

    private Customer mockCustomer() {
        Customer c = new Customer();
        c.setId(1);
        c.setName("ABCD");
        c.setEmailid("test@example.com");
        c.setAddress("VVIP Mall");
        return c;
    }

    @Test
    void assignExecutive() throws Exception {
        Executive c = mockExecutive();
        Customer r = mockCustomer();
        when(executiveRepository.findById(Optional.of(anyInt()).get())).thenReturn(Optional.of(mockExecutive()));
        when(customerServiceClient.getCustomerDetails(anyInt(), anyString())).thenReturn(r);
        when(executiveRepository.save(any(Executive.class))).thenReturn(c);
        managerServiceImpl.assignExecutive(1, 1, "");
        verify(executiveRepository, times(1)).save(any(Executive.class));
    }
}