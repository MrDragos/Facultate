package main;

import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.ws.Endpoint;

import order.MedicalSupply;
import order.Order;
import order.Order_Service;
import registration.Registration;
import reservation.Reservation;

public class MedicalSupplyBroker {
	public static void main(String[] args) {
		
		//SERVER
    	Endpoint.publish("http://localhost:8080/reservation/Reservation", new Reservation());
    	Endpoint.publish("http://localhost:8081/registration/Registration", new Registration());
    	
    	
    	//TEST CLIENT features
    	Scanner input = new Scanner(System.in);
    	System.out.println("Testati apelul metodelor pentru producator din Broker.Apasati ENTER...");
    	//test1();	
    	//test2();

	}
	
	//tests getName and getDescription
	public static void test1(){
    	//First , we must wait for manufacturers to register
    	//In order to test/start CLIENT services , press ENTER...
    	Scanner scan = new Scanner(System.in);
    	String s = scan.nextLine();
    	
    	Order_Service service = new Order_Service();
    	Order port = service.getOrderPort();
    	
    	System.out.println(port.getName());
    	System.out.println(port.getDescription());
	}
	
	//test getMedicalSupplies
	public static void test2(){
    	Order_Service service = new Order_Service();
    	Order port = service.getOrderPort();
    	
    	ArrayList<MedicalSupply> medicalSupplies = (ArrayList<MedicalSupply>) port.getMedicalSupplies();
    	 for(MedicalSupply medicalSupply :medicalSupplies){
         	System.out.println(medicalSupply.getId()+"\t"+
         						medicalSupply.getName()+"\t"+
         						medicalSupply.getDescription()+"\t"+
         						medicalSupply.getManufacturerId()+"\t"+
         						medicalSupply.getPrice()+"\t"+
         						medicalSupply.getStock()+"\t"+
         						medicalSupply.getExecutionTime());
         }
	}
	
	
}
