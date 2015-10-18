package order;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import dataaccess.DataBaseException;
import dataaccess.DataBaseWrapperImpl;
import entities.MedicalSupply;
import entities.Offer;
import general.Constants;

@WebService(serviceName = "Order")
public class Order{
	
	String name;
	String description;
	//String databaseName;
	  
	public Order(){
		this.name = null;
		this.description = null;
	}
	
	public Order(String name,String description){
		this.name = name;
		this.description = description;
	}
	
	@WebMethod(operationName = "getName")
	public String getName(){
		return this.name;
	}
	
	@WebMethod(operationName = "getDescription")
	public String getDescription(){
		return this.description;
	}
	
	@WebMethod(operationName = "getMedicalSupplies")
	public MedicalSupply[] getMedicalSupplies(){
		String manufacturerName = this.name;
		String manufacturerDescription = this.description;
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		String  whereClause = "name='"+manufacturerName+"' AND description='"+manufacturerDescription+"'";
		attributes.add("id");
		attributes.add("name");
		attributes.add("description");
		try {
			content = dbwi.getTableContent("manufacturer", attributes, whereClause, null, null);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MedicalSupply[] medicalSupplies = null;
		if(content != null && content.size() > 0){
					
			Integer manufacturerId = Integer.parseInt(content.get(0).get(0)); 

			content = new ArrayList<ArrayList<String>>();
			attributes = new ArrayList<String>();
			attributes.add("id");
			attributes.add("name");
			attributes.add("manufacturer_id");
			attributes.add("description");
			attributes.add("price");
			attributes.add("stock");
			attributes.add("execution_time");
			whereClause = "manufacturer_id='"+manufacturerId+"'";
			
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
				e.printStackTrace();
			}
		}
			
		return medicalSupplies;
	}
	
	@WebMethod(operationName = "makeReservation")
	public Offer makeReservation(@WebParam(name = "reservationId") int reservationId,
						  @WebParam(name = "manufacturer_id") String manufacturerId,
						  @WebParam(name = "medicalSupplyName") String medicalSupplyName,
						  @WebParam(name = "medicalSupplyQuantity") int medicalSupplyQuantity){
		
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		Offer offer = new Offer();
		ArrayList<String> attributes = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);

		Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String reservationDate = format.format(currentDate);
		
		attributes.add("id");
		attributes.add("medical_supply_name");
		attributes.add("medical_supply_quantity");
		attributes.add("state");
		attributes.add("manufacturer_id");
		attributes.add("date");
		
		values.add(reservationId+"");
		values.add(medicalSupplyName);
		values.add(medicalSupplyQuantity+"");
		values.add("active");
		values.add(manufacturerId);
		values.add(reservationDate);
		
		//ADAUG NOUA REZERVARE IN BAZA DE DATE
		try {
			int insert = dbwi.insertValuesIntoTable("reservation", attributes, values, false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (DataBaseException e1) {
			e1.printStackTrace();
		}
		
		attributes = new ArrayList<String>();
		attributes.add("manufacturer_id");
		attributes.add("name");
		attributes.add("price");
		attributes.add("stock");
		attributes.add("execution_time");
		String whereClause = "name='"+medicalSupplyName+"' AND manufacturer_id="+manufacturerId;
		
		try {
			content = dbwi.getTableContent("medical_supply", attributes, whereClause, null, null);
			
			if(content.size() == 0){
				return null;
			}
			
			Integer price = Integer.parseInt(content.get(0).get(2));
			Integer stock = Integer.parseInt(content.get(0).get(3));
			Integer executionTime = Integer.parseInt(content.get(0).get(4));
			
			offer.setId(reservationId);
			offer.setPrice(price*medicalSupplyQuantity);
			
			values = new ArrayList<String>();
			if(stock < medicalSupplyQuantity){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
				long time = (long) executionTime*(medicalSupplyQuantity-stock);
				//calendar.add(Calendar.DATE, );
				//Date newDate = calendar.getTime();
				//System.out.println(time);
				//updatam noua data
				Date newDate = new Date(currentDate.getTime()+time*(1000L*60L*60L*24L));
				offer.setDeliveryDate(format.format(newDate));
				values.add("0");
			
			}else{
				//daca exista in stoc , produsele pot fi livrate chiar in ziua curenta
				offer.setDeliveryDate(reservationDate);
				values.add(""+(stock-medicalSupplyQuantity));

			}
			
			//scadem cantitatea din stoc
			attributes = new ArrayList<String>();

			attributes.add("stock");
			
			whereClause = "name='"+medicalSupplyName+"' AND manufacturer_id="+manufacturerId;
			int update = dbwi.updateRecordsIntoTable("medical_supply", attributes, values, whereClause);
			
		} catch (SQLException | DataBaseException e) {
			e.printStackTrace();
		}
		
		return offer;
	}
	
	@WebMethod(operationName = "cancelReservation")
	public boolean cancelReservation(@WebParam(name = "reservationId") int reservationId){
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		String whereClause = "id="+reservationId;
		attributes.add("id");
		attributes.add("state");
		attributes.add("date");
		attributes.add("medical_supply_quantity");
		attributes.add("medical_supply_name");
		attributes.add("manufacturer_id");
		
		try {
			content = dbwi.getTableContent("reservation", attributes, whereClause, null, null);
			if(content.size() == 0){
				return false;
			}
			if(!content.get(0).get(1).equals("active")){
				//rezervarea a fost deja anulata sau a fost onorata
				return false;
			}
			
			//Verific daca rezervarea nu a expirat deja
			SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
			Date reservationDate = formatter.parse(content.get(0).get(2));
			Date currentDate = new Date();
			
			long diff = currentDate.getTime() - reservationDate.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			int daysDiff = (int) diffDays;
			
			//rezervarea a expirat
			if(daysDiff > Constants.DAYS_LIMIT){
				return false;
			}
			//System.out.println(1);
			//marcam in tabela Reservation rezervatea noastra ca fiin anulata
			Integer quantity = Integer.parseInt(content.get(0).get(3));
			ArrayList<String> values = new ArrayList<String>();
			attributes = new ArrayList<String>();
			attributes.add("state");
			values.add("canceled");
			whereClause = "id="+reservationId;
			int update = dbwi.updateRecordsIntoTable("reservation", attributes, values, whereClause);
			
			//eliberam cantitatea de produse din rezervare si le adaugam inapoi in stoc
			String medicalSupplyName = content.get(0).get(4);
			String manufacturerId = content.get(0).get(5);
			
			attributes = new ArrayList<String>();
			attributes.add("name");
			attributes.add("manufacturer_id");
			attributes.add("stock");
			content = new ArrayList<ArrayList<String>>();
			whereClause = "name='"+medicalSupplyName+"' AND manufacturer_id="+manufacturerId;
			
			content = dbwi.getTableContent("medical_supply", attributes, whereClause, null, null);
			
			//obtinem vechiul stoc
			Integer stock = Integer.parseInt(content.get(0).get(2));
			
			values = new ArrayList<String>();
			attributes = new ArrayList<String>();
			attributes.add("stock");
			values.add(""+(stock+quantity));
			whereClause = "name='"+medicalSupplyName+"' AND manufacturer_id="+manufacturerId;
			update = dbwi.updateRecordsIntoTable("medical_supply", attributes, values, whereClause);
			
			
			//System.out.println(2);
		} catch (SQLException e) {		
			e.printStackTrace();
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
		
		//System.out.println(3);
		
		return true;
	}
	
	@WebMethod(operationName = "makeOrder")
	public boolean makeOrder(@WebParam(name = "reservationId") int reservationId){
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		String whereClause = "id="+reservationId;
		attributes.add("id");
		attributes.add("state");
		
		try {
			content = dbwi.getTableContent("reservation", attributes, whereClause, null, null);
			//nu mai exista rezervarea
			if(content.size() == 0){
				return false;
			}
			
			//rezerarea a fost deja anualata/procesata
			if(!content.get(0).get(1).equals("active")){
				return false;
			}
			
			SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			String orderDate = formatter.format(currentDate);
			
			ArrayList<String> values = new ArrayList<String>();
			attributes = new ArrayList<String>();
			attributes.add("reservation_id");
			attributes.add("state");
			attributes.add("date");
			values.add(""+reservationId);
			values.add("active");
			values.add(orderDate);
			
			//adaugam comanda in baza de date
			int insert = dbwi.insertValuesIntoTable("product_order", attributes, values, true);
			
		} catch (SQLException | DataBaseException e) {
			e.printStackTrace();
			return false;
		}

		return true;	
	}
	
	@WebMethod(operationName = "cancelOrder")
	public boolean cancelOrder(@WebParam(name = "reservationId") int reservationId){
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		String whereClause = "reservation_id="+reservationId;
		attributes.add("id");
		attributes.add("reservation_id");
		attributes.add("state");
		attributes.add("date");
		
		try {
			content = dbwi.getTableContent("product_order", attributes, whereClause, null, null);
			
			
			//verific daca comanda exista
			if(content.size() == 0){
				return false;
			}
			
			//daca comanda a fost deja anulata sau a fost onorata
			if(!content.get(0).get(2).equals("active")){
				return false;
			}
			
			//Verific daca comanda nu a expirat deja
			SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
			Date reservationDate = formatter.parse(content.get(0).get(3));
			Date currentDate = new Date();
			
			long diff = currentDate.getTime() - reservationDate.getTime();
			long diffDays = diff / (24L * 60L * 60L * 1000L);
			int daysDiff = (int) diffDays;
			
			//comanda a nu mai poate fi anulata
			if(daysDiff > Constants.DAYS_LIMIT){
				return false;
			}
			
			
			//daca comanda va fi anulata vom anula si rezervarea
			
			ArrayList<String> values = new ArrayList<String>();
			attributes = new ArrayList<String>();
			attributes.add("state");
			values.add("canceled");
			whereClause = "id="+reservationId;
			
			int update = dbwi.updateRecordsIntoTable("reservation", attributes, values, whereClause);

			
			content = new ArrayList<ArrayList<String>>();
			attributes = new ArrayList<String>();
			whereClause = "id="+reservationId;
			attributes.add("id");
			attributes.add("state");
			attributes.add("date");
			attributes.add("medical_supply_quantity");
			attributes.add("medical_supply_name");
			attributes.add("manufacturer_id");
			content = dbwi.getTableContent("reservation", attributes, whereClause, null, null);
			
			//eliberam cantitatea de produse din rezervare si le adaugam inapoi in stoc
			Integer quantity = Integer.parseInt(content.get(0).get(3));
			String medicalSupplyName = content.get(0).get(4);
			String manufacturerId = content.get(0).get(5);
			
			attributes = new ArrayList<String>();
			attributes.add("name");
			attributes.add("manufacturer_id");
			attributes.add("stock");
			content = new ArrayList<ArrayList<String>>();
			whereClause = "name='"+medicalSupplyName+"' AND manufacturer_id="+manufacturerId;
			
			content = dbwi.getTableContent("medical_supply", attributes, whereClause, null, null);
			
			//obtinem vechiul stoc
			Integer stock = Integer.parseInt(content.get(0).get(2));
			
			values = new ArrayList<String>();
			attributes = new ArrayList<String>();
			attributes.add("stock");
			values.add(""+(stock+quantity));
			whereClause = "name='"+medicalSupplyName+"' AND manufacturer_id="+manufacturerId;
			update = dbwi.updateRecordsIntoTable("medical_supply", attributes, values, whereClause);
	
		} catch (SQLException | ParseException | DataBaseException e) {
			e.printStackTrace();
			return false;
		}
		

		
		return true;
		
		
	}
}
