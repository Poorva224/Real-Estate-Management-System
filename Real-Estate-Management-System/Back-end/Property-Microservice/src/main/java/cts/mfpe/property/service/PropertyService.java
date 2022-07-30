package cts.mfpe.property.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cts.mfpe.property.entities.Property;

@Service
public interface PropertyService {

	void createProperty(Property property);
	
	List<Property> getAllProperties();
	
	List<Property> getAllPropertiesByType(String propertyType);
	
	List<Property> getAllPropertiesByLocality(String locality);
}
