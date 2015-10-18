package registration;

import general.Constants;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import dataaccess.DataBaseException;
import dataaccess.DataBaseWrapperImpl;

@WebService(serviceName = "Registration")
public class Registration {
	
	@WebMethod(operationName = "registerManufacturer")
	public int registerManufacturer(@WebParam(name = "name") String name,@WebParam(name = "description") String description){
		
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("id");
		attributes.add("name");
		attributes.add("description");
		attributes.add("state");
		String  whereClause = "name='"+name+"' AND description='"+description+"'";
		
		try {
			content = dbwi.getTableContent("manufacturer", attributes, whereClause, null, null);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(content.size() == 0){
			attributes = new ArrayList<String>();
			attributes.add("name");
			attributes.add("description");
			attributes.add("state");

			ArrayList<String> values = new ArrayList<String>();
			values.add(name);
			values.add(description);
			values.add("registered");
			//int registrationId = 0;
			try {
				int insert =  dbwi.insertValuesIntoTable("manufacturer", attributes, values, true);
				content = new ArrayList<ArrayList<String>>();
				attributes = new ArrayList<String>();
				attributes.add("id");
				attributes.add("name");
				attributes.add("description");
				whereClause = "name='"+name+"' AND description='"+description+"'";

				content = dbwi.getTableContent("manufacturer", attributes, whereClause, null, null);
				return Integer.parseInt(content.get(0).get(0));
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (DataBaseException e) {
				e.printStackTrace();
			}
		}else{
			
			int registrationId = Integer.parseInt(content.get(0).get(0));
			attributes = new ArrayList<String>();
			attributes.add("state");
			whereClause = "id="+content.get(0).get(0);
			ArrayList<String> values = new ArrayList<String>();
			values.add("registered");
			try {
				int update = dbwi.updateRecordsIntoTable("manufacturer", attributes, values, whereClause);
				return registrationId;
			} catch (SQLException | DataBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	@WebMethod(operationName = "unregisterManufacturer")
	public boolean unregisterManufacturer(@WebParam(name = "registrationId") int registrationId){
		DataBaseWrapperImpl dbwi = DataBaseWrapperImpl.getInstance();
		dbwi.setDefaultDatabase(Constants.DATABASE_NAME);
		
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("id");
		attributes.add("state");
		String  whereClause = "id="+registrationId;
		
		try {
			content = dbwi.getTableContent("manufacturer", attributes, whereClause, null, null);
			if(content.size() == 0){
				return false;
			}
			if(content.get(0).get(1).equals("unregistered")){
				return false;
			}
			
			if(content.get(0).get(1).equals("registered")){
				attributes = new ArrayList<String>();
				attributes.add("state");
				whereClause = "id="+registrationId;
				ArrayList<String> values = new ArrayList<String>();
				values.add("unregistered");
				int update = dbwi.updateRecordsIntoTable("manufacturer", attributes, values, whereClause);
				
				return true;
			}
		} catch (SQLException | DataBaseException e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
}
