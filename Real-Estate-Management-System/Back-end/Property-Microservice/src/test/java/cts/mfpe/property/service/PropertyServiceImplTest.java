package cts.mfpe.property.service;

import cts.mfpe.property.entities.Property;
import cts.mfpe.property.repos.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

class PropertyServiceImplTest {

    @InjectMocks
    PropertyServiceImpl propertyServiceImpl;

    @Mock
    PropertyRepository propertyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProperty() {
        Property pr = mockProperty();
        propertyServiceImpl.createProperty(pr);
        verify(propertyRepository, times(1)).save(pr);
    }

    @Test
    void getAllProperties() {
        when(propertyRepository.findAll()).thenReturn(new ArrayList<Property>());
        propertyServiceImpl.getAllProperties();
        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    void getAllPropertiesByType() {
        when(propertyRepository.findByPropertyType(anyString())).thenReturn(new ArrayList<Property>());
        propertyServiceImpl.getAllPropertiesByType(anyString());
        verify(propertyRepository, times(1)).findByPropertyType("");
    }

    @Test
    void getAllPropertiesByLocality() {
        when(propertyRepository.findByLocality(anyString())).thenReturn(new ArrayList<Property>());
        propertyServiceImpl.getAllPropertiesByLocality(anyString());
        verify(propertyRepository, times(1)).findByLocality("");
    }

    private Property mockProperty() {
        Property p = new Property();
        p.setPropertyType("Villa");
        p.setBudget(500000.0);
        p.setLocality("Shastri Nagar");
        p.setId(1);
        return p;
    }
}