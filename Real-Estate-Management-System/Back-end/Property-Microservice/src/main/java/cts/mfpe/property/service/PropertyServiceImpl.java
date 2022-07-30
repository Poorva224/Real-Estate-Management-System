package cts.mfpe.property.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cts.mfpe.property.entities.Property;
import cts.mfpe.property.repos.PropertyRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PropertyServiceImpl implements PropertyService{

	@Autowired
	private PropertyRepository propertyRepo;
	
	@Transactional
	@Override
	public void createProperty(Property property) {
		propertyRepo.save(property);
		log.info("Property {} created", property.toString());
	}
	
	@Override
	public List<Property> getAllProperties(){
		List<Property> properties = propertyRepo.findAll();
		log.info("Properties: {}", properties);
		return properties;
	}
	
	@Override
	public List<Property> getAllPropertiesByType(String propertyType){
		List<Property> properties = propertyRepo.findByPropertyType(propertyType);
		log.info("Properties with property type {}: {}", propertyType, properties);
		return properties;
	}
	
	@Override
	public List<Property> getAllPropertiesByLocality(String locality){
		List<Property> properties = propertyRepo.findByLocality(locality);
		log.info("Properties in locality {}: {}", locality, properties);
		return properties;
	}
	
}
