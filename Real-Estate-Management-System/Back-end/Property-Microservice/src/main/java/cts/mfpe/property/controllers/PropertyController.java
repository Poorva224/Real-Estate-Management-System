package cts.mfpe.property.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cts.mfpe.property.clients.AuthorizationClient;
import cts.mfpe.property.entities.Property;
import cts.mfpe.property.exceptions.AuthorizationException;
import cts.mfpe.property.exceptions.PropertyNotFoundException;
import cts.mfpe.property.service.PropertyService;

//Project is developed by INTCDE22IJ046 - POD - 4

@RestController
@CrossOrigin
public class PropertyController {

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private AuthorizationClient authorizationClient;

	//Create new Property.
	@PostMapping("/createProperty")
	public ResponseEntity<String> createProperty(@RequestBody @Valid Property property,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException {
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			propertyService.createProperty(property);
			return new ResponseEntity<>("Property Created Successfully!", HttpStatus.CREATED);
		}
		throw new AuthorizationException("Not Allowed");
	}

	//Get all Properties.
	@GetMapping("/getAllProperties")
	public ResponseEntity<List<Property>> getAllProperties(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException {
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			return ResponseEntity.ok(propertyService.getAllProperties());
		}
		throw new AuthorizationException("Not Allowed");
	}

	//Get all Properties based on Type.
	@GetMapping("/getAllPropertiesByType/{propertyType}")
	public ResponseEntity<List<Property>> getAllPropertiesByType(@PathVariable String propertyType,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
					throws Exception {
		List<Property> properties = propertyService.getAllPropertiesByType(propertyType);
		if (properties.size() == 0) {
			throw new PropertyNotFoundException("Properties of property type " + propertyType + " Not Found!");
		}
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			return ResponseEntity.ok(properties);
		}
		throw new AuthorizationException("Not Allowed");
	}

	//Get all Properties Based on Locality.
	@GetMapping("/getAllPropertiesByLocality/{locality}")
	public ResponseEntity<List<Property>> getAllPropertiesByLocality(@PathVariable String locality,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
					throws Exception {
		List<Property> properties = propertyService.getAllPropertiesByLocality(locality);
		if (properties.size() == 0) {
			throw new PropertyNotFoundException("Properties in locality " + locality + " Not Found!");
		}
		if (authorizationClient.authorizeTheRequest(requestTokenHeader)) {
			return ResponseEntity.ok(properties);
		}
		throw new AuthorizationException("Not Allowed");
	}
}
