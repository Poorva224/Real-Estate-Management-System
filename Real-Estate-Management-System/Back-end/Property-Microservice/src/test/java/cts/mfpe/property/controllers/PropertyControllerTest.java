package cts.mfpe.property.controllers;

import cts.mfpe.property.clients.AuthorizationClient;
import cts.mfpe.property.entities.Property;
import cts.mfpe.property.exceptions.AuthorizationException;
import cts.mfpe.property.exceptions.PropertyNotFoundException;
import cts.mfpe.property.service.PropertyService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PropertyControllerTest {

    private final String requestTokenHeader = "vdgekncr4uliu45otu4864hutigh";

    @InjectMocks
    PropertyController propertyController;

    @Mock
    PropertyService propertyService;

    @Mock
    AuthorizationClient authorizationClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProperty() throws AuthorizationException {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        doNothing().when(propertyService).createProperty(any(Property.class));
        assertEquals(HttpStatus.CREATED, propertyController.createProperty(new Property(), requestTokenHeader).getStatusCode());
    }

    @Test
    void createProperty_Exception() {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        assertThrows(AuthorizationException.class, () -> propertyController.createProperty(new Property(), requestTokenHeader));
    }


    @Test
    void getAllProperties() throws AuthorizationException {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        when(propertyService.getAllProperties()).thenReturn(new ArrayList<Property>());
        assertEquals(HttpStatus.OK, propertyController.getAllProperties(requestTokenHeader).getStatusCode());
    }

    @Test
    void getAllProperties_Exception() {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        assertThrows(AuthorizationException.class, () -> propertyController.getAllProperties(requestTokenHeader));
    }

    @Test
    void getAllPropertiesByType_PropertyNotFoundException() {
        when(propertyService.getAllPropertiesByType(anyString())).thenReturn(new ArrayList<Property>());
        assertThrows(PropertyNotFoundException.class, () -> propertyController.getAllPropertiesByType("Villa", requestTokenHeader));
    }

    @Test
    void getAllPropertiesByType() throws Exception {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        List<Property> p = new ArrayList<Property>();
        p.add(new Property());
        when(propertyService.getAllPropertiesByType(anyString())).thenReturn(p);
        assertEquals(HttpStatus.OK, propertyController.getAllPropertiesByType("", requestTokenHeader).getStatusCode());
    }

    @Test
    void getAllPropertiesByType_AuthException() {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        List<Property> p = new ArrayList<Property>();
        p.add(new Property());
        when(propertyService.getAllPropertiesByType(anyString())).thenReturn(p);
        assertThrows(AuthorizationException.class, () -> propertyController.getAllPropertiesByType("", requestTokenHeader));
    }

    @Test
    void getAllPropertiesByLocality_PropertyNotFoundException() {
        when(propertyService.getAllPropertiesByLocality(anyString())).thenReturn(new ArrayList<Property>());
        assertThrows(PropertyNotFoundException.class, () -> propertyController.getAllPropertiesByLocality("VVIP Mall", requestTokenHeader));
    }

    @Test
    void getAllPropertiesByLocality() throws Exception {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(true);
        List<Property> p = new ArrayList<Property>();
        p.add(new Property());
        when(propertyService.getAllPropertiesByLocality(anyString())).thenReturn(p);
        assertEquals(HttpStatus.OK, propertyController.getAllPropertiesByLocality("", requestTokenHeader).getStatusCode());
    }

    @Test
    void getAllPropertiesByLocality_AuthException() {
        when(authorizationClient.authorizeTheRequest(anyString())).thenReturn(false);
        List<Property> p = new ArrayList<Property>();
        p.add(new Property());
        when(propertyService.getAllPropertiesByLocality(anyString())).thenReturn(p);
        assertThrows(AuthorizationException.class, () -> propertyController.getAllPropertiesByLocality("", requestTokenHeader));
    }
}