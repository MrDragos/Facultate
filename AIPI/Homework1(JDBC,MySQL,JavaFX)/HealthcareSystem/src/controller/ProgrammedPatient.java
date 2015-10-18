package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import dataaccess.DataBaseWrapperImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.stage.Stage;

public class ProgrammedPatient {

    private Stage applicationStage;
    private Scene applicationScene;
    
    @FXML
    Label dataLabel;
    
    String personalIdentifier;
    
    @FXML
    ListView listView;
    
    @FXML
    ScrollBar scrollBar;
    
	public static ObservableList patients;

	public void init(){
		LocalDate localDate = LocalDate.now();
		String currentDate = localDate.toString();
		dataLabel.setText("DATA: "+currentDate);
		
		DataBaseWrapperImpl dbWrapper = DataBaseWrapperImpl.getInstance();
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
    	ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("id");
    	attributes.add("reservation_date");
    	attributes.add("patient_id");
    	
    	
    	String whereClause = "reservation_date='"+currentDate+"'";
    		try {
				content = dbWrapper.getTableContent("reservation", attributes, whereClause, null, null);
				ArrayList<String> patientList = new ArrayList<String>();
				String id;
				String patient_id;
				String patient;
				for(ArrayList<String> a:content){
					patient = "";
					id = a.get(0);
					patient_id = a.get(2);
					
					attributes = new ArrayList<String>();
					attributes.add("id");
					attributes.add("first_name");
					attributes.add("last_name");

					whereClause = "id='"+Integer.parseInt(patient_id)+"'";		
					content = dbWrapper.getTableContent("patient", attributes, whereClause, null, null);

					patient += content.get(0).get(0)+"\t"+content.get(0).get(1)+"\t\t"+content.get(0).get(2)+
							"\t\t reservation_id: "+Integer.parseInt(id);
					patientList.add(patient);
					
						
				}
				
				patients = FXCollections.observableArrayList(patientList);
				listView.setItems(patients);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    		
    		public void init2(){
    			LocalDate localDate = LocalDate.now();
    			String currentDate = localDate.toString();
    			dataLabel.setText("DATA: "+currentDate);
    			
    			DataBaseWrapperImpl dbWrapper = DataBaseWrapperImpl.getInstance();
    			ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
    	    	ArrayList<String> attributes = new ArrayList<String>();
    	    	attributes.add("id");
    	    	attributes.add("reservation_date");
    	    	attributes.add("patient_id");
    	    	attributes.add("medic_id");
    	    	
    	    	
    	    	String whereClause = "reservation_date='"+currentDate+"' AND medic_id='"+personalIdentifier+"'";
    	    		try {
    					content = dbWrapper.getTableContent("reservation", attributes, whereClause, null, null);
    					ArrayList<String> patientList = new ArrayList<String>();
    					String id;
    					String patient_id;
    					String patient;
    					for(ArrayList<String> a:content){
    						patient = "";
    						id = a.get(0);
    						patient_id = a.get(2);
    						
    						attributes = new ArrayList<String>();
    						attributes.add("id");
    						attributes.add("first_name");
    						attributes.add("last_name");

    						whereClause = "id='"+Integer.parseInt(patient_id)+"'";		
    						content = dbWrapper.getTableContent("patient", attributes, whereClause, null, null);

    						patient += content.get(0).get(0)+"\t"+content.get(0).get(1)+"\t\t"+content.get(0).get(2)+
    								"\t\t reservation_id: "+Integer.parseInt(id);
    						patientList.add(patient);
    						
    							
    					}
    					
    					patients = FXCollections.observableArrayList(patientList);
    					listView.setItems(patients);
    					
    				} catch (SQLException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
			
	
	}

			public String getPersonalIdentifier() {
				return personalIdentifier;
			}

			public void setPersonalIdentifier(String personalIdentifier) {
				this.personalIdentifier = personalIdentifier;
			}
	
	
}
