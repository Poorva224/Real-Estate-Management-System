package cts.mfpe.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//Project is developed by INTCDE22IJ046 - POD - 4

@SpringBootApplication
@EnableFeignClients
public class ManagerMicroserviceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ManagerMicroserviceApplication.class, args);
	}

}
