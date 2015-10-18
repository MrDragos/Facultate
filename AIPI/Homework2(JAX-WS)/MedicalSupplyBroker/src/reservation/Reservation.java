package reservation;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import java.rmi.server.UID;

import order.Offer;
import order.Order;
import order.Order_Service;
import dataaccess.DataBaseException;
import dataaccess.DataBaseWrapperImpl;
import entities.Manufacturer;
import entities.MedicalSupply;
import general.Constants;
 
@WebService(serviceName = "Reservation")
public class Reservation {
	
	@WebMethod(operationName = "getManufacturers")
	public Manufacturer[] getManufacturers(){
		Manufacturer[] manufacturers = null;
		
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("id");
		attributes.add("name");
		attributes.add("description");
		attributes.add("state");
		String  whereClause = "state='registered'";
		
		try {
			content = dbwi.getTableContent("manufacturer", attributes, whereClause, null, null);
			manufacturers = new Manufacturer[content.size()];
			int index = 0;

			for(ArrayList<String> m:content){
				manufacturers[index++] = new Manufacturer(Integer.parseInt(m.get(0)),m.get(1),m.get(2),m.get(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return manufacturers;	
	}
	
	@WebMethod(operationName = "getMedicalSupplies")
	public MedicalSupply[] getMedicalSupplies(@WebParam(name = "manufacturer") Manufacturer manufacturer){
		MedicalSupply[] medicalSupplies = null;
		
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("id");
		attributes.add("name");
		attributes.add("manufacturer_id");
		attributes.add("description");
		attributes.add("price");
		attributes.add("stock");
		attributes.add("execution_time");
		String  whereClause = "manufacturer_id='"+manufacturer.getId()+"'";
		
		try {
			content = dbwi.getTableContent("medical_supply", attributes, whereClause, null, null);
			medicalSupplies = new MedicalSupply[content.size()];
			int index = 0;

			for(ArrayList<String> ms:content){
				medicalSupplies[index++] = new MedicalSupply(Integer.parseInt(ms.get(0)),
																ms.get(1),
																Integer.parseInt(ms.get(2)),
																ms.get(3),
																Integer.parseInt(ms.get(4)),
																Integer.parseInt(ms.get(5)),
																Integer.parseInt(ms.get(6)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return medicalSupplies;
	}
	
	@WebMethod(operationName = "makeReservation")
	public int makeReservation(@WebParam(name = "manufacturer") String manufacturer,
						@WebParam(name = "medicalSupply") String medicalSupply,
						@WebParam(name = "quantitys") int quantity){
		
		Order_Service service = new Order_Service();
		Order port = service.getOrderPort();
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("id");
		attributes.add("name");
		attributes.add("state");
		String whereClause = "name='"+manufacturer+"' AND state='registered'";
		try {
			content = dbwi.getTableContent("manufacturer", attributes, whereClause, null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(content.size() == 0){
			return -1;	//producator inexistent
		}
		
		int manufacturerId = Integer.parseInt(content.get(0).get(0));
		
		Random rand = new Random();
		Integer reservationId = 10000000+ rand.nextInt(9999999);
		
		content = new ArrayList<ArrayList<String>>();
		attributes = new ArrayList<String>();
		attributes.add("reservation_id");
		whereClause = "reservation_id="+reservationId;
		try {
			content = dbwi.getTableContent("unique_ids", attributes, whereClause, null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//verific sa fie unic
		if(content.size() > 0){
			//idul exista deja asa ca mai generam odata
			reservationId = 10000000+rand.nextInt(9999999);
		}
		
		attributes = new ArrayList<String>();
		attributes.add("reservation_id");
		ArrayList<String> values = new ArrayList<String>();
		values.add(reservationId+"");
		
		try {
			int insert = dbwi.insertValuesIntoTable("unique_ids", attributes, values, true);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
		
		//System.out.println("LO: "+reservationId);
		Offer offer = port.makeReservation(reservationId, manufacturerId+"", medicalSupply, quantity);
		
		if(offer == null)
			return -1;
		 
		attributes = new ArrayList<String>();
		attributes.add("reservation_id");
		attributes.add("delivery_date");
		attributes.add("price");
		
		values = new ArrayList<String>();
		values.add(reservationId+"");
		values.add(offer.getDeliveryDate());
		values.add(offer.getPrice()+"");
		
		try {
			int insert = dbwi.insertValuesIntoTable("offer", attributes, values, true);
		} catch (SQLException | DataBaseException e) {
			e.printStackTrace();
		}
		
		return reservationId;
	}
	
	@WebMethod(operationName = "cancelReservation")
	public boolean cancelReservation(@WebParam(name = "reservationId") int reservationId){
		Order_Service service = new Order_Service();
		Order port = service.getOrderPort();
		
		return port.cancelReservation(reservationId);
	}
	
	@WebMethod(operationName = "getReservationStatus")
	public Offer getReservationStatus(@WebParam(name = "reservationId") int reservationId){
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		String whereClause = "reservation_id="+reservationId;
		
		attributes.add("reservation_id");
		attributes.add("delivery_date");
		attributes.add("price");
		Offer offer = new Offer();
		try {
			content = dbwi.getTableContent("offer", attributes, whereClause, null, null);
			
			offer.setId(Integer.parseInt(content.get(0).get(0)));
			offer.setDeliveryDate(content.get(0).get(1));
			offer.setPrice(Integer.parseInt(content.get(0).get(2)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return offer;
	}
	
	@WebMethod(operationName = "makeOrder")
	public boolean makeOrder(@WebParam(name = "reservationId") int reservationId){
		Order_Service service = new Order_Service();
		Order port = service.getOrderPort();
		boolean result = port.makeOrder(reservationId);
		
		if(result){
			ArrayList<String> values = new ArrayList<String>();
			ArrayList<String> attributes = new ArrayList<String>();
			attributes.add("reservation_id");
			attributes.add("state");
	
			values.add(""+reservationId);
			values.add("placed");
	
			//adaugam comanda in baza de date
			DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
			dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
			try {
				int insert = dbwi.insertValuesIntoTable("product_order", attributes, values, true);
			} catch (SQLException | DataBaseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@WebMethod(operationName = "cancelOrder")
	public boolean cancelOrder(@WebParam(name = "reservationId") int reservationId){
		Order_Service service = new Order_Service();
		Order port = service.getOrderPort();
		
		boolean result = port.cancelOrder(reservationId);
		
		//marcam comanda ca fiin anulata in baza de date
		if(result){
			DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
			dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
			ArrayList<String> values = new ArrayList<String>();
			ArrayList<String> attributes = new ArrayList<String>();
			attributes.add("state");
			values.add("canceled");
			String whereClause = "reservation_id="+reservationId;
			try {
				int update = dbwi.updateRecordsIntoTable("product_order", attributes, values, whereClause);
			} catch (SQLException | DataBaseException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	
	}
	
	@WebMethod(operationName = "getOrderStatus")
	public int getOrderStatus(@WebParam(name = "reservationId") int reservationId){
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		String whereClause = "reservation_id="+reservationId;
		attributes.add("reservation_id");
		attributes.add("state");
		
		try {
			content = dbwi.getTableContent("product_order", attributes, whereClause, null, null);
			if(content.size() == 0){
				return Constants.INVALID_REGISTRATION_ID;
			}else{
				if(content.get(0).get(1).equals("placed")){
					return Constants.ORDER_PLACED;
				}
				
				if(content.get(0).get(1).equals("canceled")){
					return Constants.ORDER_CANCELLED;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Constants.INVALID_REGISTRATION_ID;
	}
}
