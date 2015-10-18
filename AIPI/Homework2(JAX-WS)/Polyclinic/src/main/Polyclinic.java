package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import reservation.GetManufacturers;
import reservation.Manufacturer;
import reservation.MedicalSupply;
import reservation.Offer;
import reservation.Reservation;
import reservation.Reservation_Service;
import general.Constants;
import general.Utilities;
import insurance.Insurance;
import insurance.Insurance_Service;

public class Polyclinic {
	public static void main(String[] args) {
		
		//TEST Health Insurance Provider
		//testHealthInsuranceProvier();
		
    	//TEST Medical Supply Broker
        //testMedicalSupplyBrokers();

        }
	
	public static void testHealthInsuranceProvier(){
		
        Insurance_Service serviciu = new Insurance_Service();
        Insurance port = serviciu.getInsurancePort();
        int result;
        try {
			result = port.redeemMedicalService(1, 2, DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 15, 0)), 2, 1.0);
			Utilities.printResult(result);
			
			result = port.redeemMedicalService(1, 1, DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 15, 0)), 2, 1.0);
			Utilities.printResult(result);
			
			result = port.redeemMedicalService(1, 1, DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 15, 0)), 1, 1.0);
			Utilities.printResult(result);
			
			result = port.redeemMedicalService(1, 1, DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 15, 0)), 1, 4700.0);
			Utilities.printResult(result);
			
			result = port.redeemMedicalService(1, 1, DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2016, 10, 17, 15, 0)), 1, 4600.0);
			Utilities.printResult(result);
			
			result = port.redeemMedicalService(1, 11, DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2016, 10, 17, 15, 0)), 1, 4600.0);
			Utilities.printResult(result);
			
			
        } catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		
        
	}
	
	public static void testMedicalSupplyBrokers(){
        Reservation_Service reservationService = new Reservation_Service();
        Reservation brokerPort = reservationService.getReservationPort();
        ArrayList<Manufacturer> manufacturers = (ArrayList<Manufacturer>) brokerPort.getManufacturers();
        for(Manufacturer manufacturer : manufacturers){
        	System.out.println("id: "+ manufacturer.getId()+"\tname: "+manufacturer.getName()+"\tdescription: "+manufacturer.getDescription());
        }
        
        Manufacturer manufacturer = manufacturers.get(0);
        ArrayList<MedicalSupply> medicalSupplies = (ArrayList<MedicalSupply>) brokerPort.getMedicalSupplies(manufacturer);
        for(MedicalSupply medicalSupply :medicalSupplies){
        	System.out.println(medicalSupply.getId()+"\t"+
        						medicalSupply.getName()+"\t"+
        						medicalSupply.getDescription()+"\t"+
        						medicalSupply.getManufacturerId()+"\t"+
        						medicalSupply.getPrice()+"\t"+
        						medicalSupply.getStock()+"\t"+
        						medicalSupply.getExecutionTime());
        	
        }
        
        MedicalSupply medicalSupply = medicalSupplies.get(1);
        int reservationId = brokerPort.makeReservation(manufacturer.getName(), medicalSupply.getName(), 41);
        System.out.println("ReservationID = "+reservationId);
              
        Offer offer = brokerPort.getReservationStatus(reservationId);
        System.out.println(offer.getDeliveryDate()+"\t"+offer.getPrice());
        
        //System.out.println("Canceled? "+brokerPort.cancelReservation(reservationId));      
        System.out.println("Ordered? "+brokerPort.makeOrder(reservationId));
        
        reservationId = 18934106;
        System.out.println("Order Canceled? "+brokerPort.cancelOrder(reservationId));
        
        int orderStatus = brokerPort.getOrderStatus(reservationId);
        switch (orderStatus) {
			case Constants.ORDER_CANCELLED:
				System.out.println("ORDER CANCELED");
				break;
			case Constants.ORDER_PLACED:
				System.out.println("ORDER PLACED");
				break;
			case Constants.INVALID_REGISTRATION_ID:
				System.out.println("INVALID REGISTRATION ID");
			break;
		default:
			break;
		}
        
	}
}

