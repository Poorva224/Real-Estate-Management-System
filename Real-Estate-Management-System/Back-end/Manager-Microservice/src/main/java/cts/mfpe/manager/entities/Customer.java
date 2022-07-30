package cts.mfpe.manager.entities;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Customer {
	private int id;
	private String name;
	private String address;
	private String emailid;
	private String contactNumber;
}
