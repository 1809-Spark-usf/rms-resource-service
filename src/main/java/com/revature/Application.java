package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * The Class Application.
 * Boot strap application that deals 
 * with handling resources.
 * 
 * This includes the creation, updating,
 * and deletion of any object involving the
 * resource service.
 * 
 * The objects for this service are: the building
 * object, the campus object, and the resource object.
 * 
 * @author 1811-Java-Nick | 12/27/2018
 * 
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
