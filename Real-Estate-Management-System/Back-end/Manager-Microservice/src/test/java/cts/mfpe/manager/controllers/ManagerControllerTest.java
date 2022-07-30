package cts.mfpe.manager.controllers;

import cts.mfpe.manager.clients.AuthorizationClient;
import cts.mfpe.manager.entities.Customer;
import cts.mfpe.manager.entities.Executive;
import cts.mfpe.manager.exceptions.AuthorizationException;
import cts.mfpe.manager.exceptions.CustomerNotFoundException;
import cts.mfpe.manager.exceptions.ExecutiveNotFoundException;
import cts.mfpe.manager.services.ManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ManagerControllerTest {

    private final String requestTokenHeader = "vdgekncr4uliu45otu4864hutigh";

    @InjectMocks
    ManagerController managerController;

    @Mock
    ManagerService managerService;

    @Mock
    AuthorizationClient authorizationClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createExecutive() throws Exception {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        Executive executive = mockExecutive();
        doNothing().when(managerService).createExecutive(any(Executive.class));
        assertEquals(HttpStatus.CREATED, managerController.createExecutive(executive, requestTokenHeader).getStatusCode());
    }

    @Test
    void createExecutive_Exception() {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        assertThrows(AuthorizationException.class, () -> managerController.createExecutive(mockExecutive(), requestTokenHeader));
    }

    @Test
    void getAllExecutives_requestForManager_valid_requestForCustomer_valid_success() throws AuthorizationException {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        when(managerService.getAllExecutives()).thenReturn(new ArrayList<Executive>());
        assertEquals(HttpStatus.OK, managerController.getAllExecutives(requestTokenHeader).getStatusCode());
    }

    @Test
    void getAllExecutives_requestForManager_valid_requestForCustomer_invalid_success() throws AuthorizationException {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        when(managerService.getAllExecutives()).thenReturn(new ArrayList<Executive>());
        assertEquals(HttpStatus.OK, managerController.getAllExecutives(requestTokenHeader).getStatusCode());
    }


    @Test
    void getAllExecutives_requestForManager_invalid_requestForCustomer_invalid_Exception() {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        when(managerService.getAllExecutives()).thenReturn(new ArrayList<Executive>());
        assertThrows(AuthorizationException.class, () -> managerController.getAllExecutives(requestTokenHeader));
    }

    @Test
    void getAllExecutives_Exception() {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        assertThrows(AuthorizationException.class, () -> managerController.createExecutive(mockExecutive(), requestTokenHeader));
    }

    private Executive mockExecutive() {
        Executive e = new Executive();
        e.setId(1);
        e.setName("ABCD");
        e.setLocality("VVIP Mall");
        e.setContactNumber(987654321L);
        e.setEmailId("test@example.com");
        return e;
    }

    @Test
    void getAllExecutivesByLocalityNotFoundException() throws Exception {
        when(managerService.getAllExecutivesByLocality(anyString())).thenReturn(new ArrayList<Executive>());
        assertThrows(ExecutiveNotFoundException.class, () -> managerController.getAllExecutivesByLocality("VVIP Mall", ""));
    }

    @Test
    void getAllExecutivesByLocality_requestForManager_valid_requestForExecutive_valid_success() throws Exception {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        List<Executive> p = new ArrayList<Executive>();
        p.add(new Executive());
        when(managerService.getAllExecutivesByLocality(anyString())).thenReturn(p);
        assertEquals(HttpStatus.OK, managerController.getAllExecutivesByLocality("", requestTokenHeader).getStatusCode());
    }

    @Test
    void getAllExecutivesByLocality_requestForManager_valid_requestForExecutive_invalid_success() throws Exception {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        List<Executive> p = new ArrayList<Executive>();
        p.add(new Executive());
        when(managerService.getAllExecutivesByLocality(anyString())).thenReturn(p);
        assertEquals(HttpStatus.OK, managerController.getAllExecutivesByLocality("", requestTokenHeader).getStatusCode());
    }

    @Test
    void getAllExecutivesByLocality_requestForManager_invalid_requestForExecutive_invalid_Exception() throws Exception {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        List<Executive> p = new ArrayList<Executive>();
        p.add(new Executive());
        when(managerService.getAllExecutivesByLocality(anyString())).thenReturn(p);
        assertThrows(AuthorizationException.class, () -> managerController.getAllExecutivesByLocality("", requestTokenHeader));
    }

    @Test
    void getAllCustomers() throws Exception {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        when(managerService.getAllCustomers(anyString())).thenReturn(new ArrayList<Customer>());
        assertEquals(HttpStatus.OK, managerController.getAllCustomers(requestTokenHeader).getStatusCode());
    }

    @Test
    void getAllCustomers_Exception() {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        assertThrows(AuthorizationException.class, () -> managerController.getAllCustomers(requestTokenHeader));
    }

    @Test
    void getCustomerByIdCustomerNotFoundException() throws Exception {
        when(managerService.getCustomerById(anyInt(), anyString())).thenReturn(null);
        assertThrows(CustomerNotFoundException.class, () -> managerController.getCustomerById(1, requestTokenHeader));
    }

    @Test
    void getCustomerById() throws Exception {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        when(managerService.getCustomerById(anyInt(), anyString())).thenReturn(mockCustomer());
        assertEquals(HttpStatus.OK, managerController.getCustomerById(1, requestTokenHeader).getStatusCode());
    }

    @Test
    void getCustomerById_AuthException() throws Exception {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        when(managerService.getCustomerById(anyInt(), anyString())).thenReturn(mockCustomer());
        assertThrows(AuthorizationException.class, () -> managerController.getCustomerById(1, requestTokenHeader));
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
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        doNothing().when(managerService).assignExecutive(anyInt(), anyInt(), anyString());
        assertEquals(HttpStatus.OK, managerController.assignExecutive(1, 1, requestTokenHeader).getStatusCode());
    }

    @Test
    void assignExecutive_Exception() {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        assertThrows(AuthorizationException.class, () -> managerController.assignExecutive(1, 1, requestTokenHeader));
    }
}