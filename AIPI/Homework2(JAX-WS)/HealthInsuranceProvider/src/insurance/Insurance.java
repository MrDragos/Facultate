package insurance;

import general.Constants;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import dataaccess.DataBaseException;
import dataaccess.DataBaseWrapperImpl;

@WebService(serviceName = "Insurance")
public class Insurance {

	@WebMethod(operationName = "redeemMedicalService")
	public int redeemMedicalService(@WebParam(name = "physicianId") int physicianId,
								@WebParam(name = "patientId") int patientId,
								@WebParam(name = "date") GregorianCalendar date,
								@WebParam(name = "medicalServiceId") int medicalServiceId,
								@WebParam(name = "price") double price){
		
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		String  whereClause;
		
		//CHECK PATIENT
		attributes.add("patient_id");
		attributes.add("insurance"); 
		whereClause = "patient_id='"+patientId+"'";
		
		try {
			content = dbwi.getTableContent("patient_state", attributes, whereClause, null, null);
			if(content.size() == 0){
				return Constants.INFORMATION_NOT_AVAILABLE;
			}
			
			String insurance = content.get(0).get(1);
			if(!insurance.equals("T")){
				return Constants.PATIENT_NOT_INSURED;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constants.INFORMATION_NOT_AVAILABLE;
		}
		
		//CHECK PHYSICIAN
				
		content = new ArrayList<ArrayList<String>>();
		attributes = new ArrayList<String>();
				
		attributes.add("id");
		attributes.add("physician_id");
		attributes.add("expiration_date"); 
		whereClause = "physician_id='"+physicianId+"'";
		try {
			content = dbwi.getTableContent("physician_contract", attributes, whereClause, null, null);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			if(content.size() == 0){
				return Constants.INFORMATION_NOT_AVAILABLE;
			}
			
			Date dateTime = format.parse(content.get(0).get(2));
			GregorianCalendar expirationDate = new GregorianCalendar();
			expirationDate.setTime(dateTime);
			
			if(expirationDate.before(date)){
				return Constants.PHYSICIAN_NOT_IN_CONTRACTUAL_RELATIONSHIP;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return Constants.INFORMATION_NOT_AVAILABLE;
		} catch (ParseException e) {
			e.printStackTrace();
			return Constants.INFORMATION_NOT_AVAILABLE;
		}
		
		
		
		content = new ArrayList<ArrayList<String>>();
		attributes = new ArrayList<String>();
		
		attributes.add("medical_service_id");
		attributes.add("discount");
		attributes.add("supported"); 
		whereClause = "medical_service_id='"+medicalServiceId+"'";
		
		try {
			content = dbwi.getTableContent("medical_service_state", attributes, whereClause, null, null);
			if(content.size() == 0){
				return Constants.INFORMATION_NOT_AVAILABLE;
			}
			
			if(!content.get(0).get(2).equals("T")){
				return Constants.UNSUPPORTED_MEDICAL_SERVICE;
			}
			//plafonul
			Integer discount = Integer.parseInt(content.get(0).get(1));
			
			content = new ArrayList<ArrayList<String>>();
			attributes = new ArrayList<String>();
			
			Integer totalExpenses = 0;
			
			attributes.add("medical_service_id");
			attributes.add("expense"); 
			whereClause = "medical_service_id='"+medicalServiceId+"'";
			content = dbwi.getTableContent("expenses_history", attributes, whereClause, null, null);

			for(ArrayList<String> expense :content){
				totalExpenses += Integer.parseInt(expense.get(1));
			}
			
			if(discount < (totalExpenses+price)){
				return Constants.FOUNDS_EXCEEDED;
			}else{
				attributes = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();
				
				attributes.add("medical_service_id");
				attributes.add("discount_date");
				attributes.add("expense");
				
				values.add(medicalServiceId+"");
				Date newDate = date.getTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				values.add(formatter.format(newDate));
				values.add(""+price);
				
				int insert = dbwi.insertValuesIntoTable("expenses_history", attributes, values, true);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constants.INFORMATION_NOT_AVAILABLE;
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Constants.SUCCESS;		
	}
}
