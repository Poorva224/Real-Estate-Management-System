package cts.mfpe.manager.entities;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Executive {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull(message="Customer name cannot be null")
	private String name;
	@NotNull(message="Contact number cannot be null")
	private Long contactNumber;
	@NotNull(message="Locality cannot be null")
	private String locality;
	@NotNull(message="Email Id cannot be null")
	@Email(message="Invalid Email Entered")
	private String emailId;
	@ElementCollection
	private Set<Customer> customers;
}
