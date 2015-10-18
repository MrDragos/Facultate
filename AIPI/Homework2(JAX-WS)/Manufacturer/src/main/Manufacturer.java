package main;

import java.util.Scanner;

import javax.xml.ws.Endpoint;

import order.Order;
import registration.RegisterManufacturer;
import registration.Registration;
import registration.Registration_Service;

public class Manufacturer {
	public static void main(String[] args) {
		
		
		//SERVER
		
		//daca doriti sa testati fara a introduce nume si descriere 
		//decomentati randulde mai jos si comnetati cealalta linie cu Endpoint
		Endpoint.publish("http://localhost:8082/order/Order", new Order());
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Pentru a porni serverul intronuceti numele si descrierea...");
		//exemplu: Theranos
		System.out.println("Enter name :");
		//exemplu: Theranos is a privately held health technology and medical laboratory services company based in Palo Alto, California
		String name = scan.nextLine();
		System.out.println("enter description : ");
		String description = scan.nextLine();
		//Endpoint.publish("http://localhost:8082/order/Order", new Order(name,description));
    	
    	
    	//CLIENT
    	//testRegistrationService();
	}
	
	public static void testRegistrationService(){
    	System.out.println("Test client");
    	
    	
        Registration_Service registrationService = new Registration_Service();
        Registration brokerPort = registrationService.getRegistrationPort();
        
        
        int registrationId = brokerPort.registerManufacturer("Nou", "Producator nou");
        System.out.println(registrationId);
        
        boolean unregistered = brokerPort.unregisterManufacturer(registrationId);
        System.out.println(unregistered);
        
        registrationId = brokerPort.registerManufacturer("Nou", "Producator nou");
        System.out.println(registrationId);
        
	}
}
