package controller;

import java.sql.SQLException;
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

public class Concediu {

    private Stage applicationStage;
    private Scene applicationScene;
    
    public String personalIdentifier;
    
    @FXML
    Label daysNumberLabel;
    
    @FXML
    ListView listView;
    
    @FXML
    ScrollBar scrollBar;
    
	public static ObservableList days;
   
	public Concediu() {
	
	}
	
	public void init(){
		DataBaseWrapperImpl dbWrapper = DataBaseWrapperImpl.getInstance();
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
    	ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("specific_date");
    	
    	
    	String whereClause = "user_personal_identifier='"+personalIdentifier+"' AND holiday='T'";
    	
    		try {
				content = dbWrapper.getTableContent("specific_schedule", attributes, whereClause, null, null);
				ArrayList<String> dates = new ArrayList<String>();
				for(ArrayList<String> a:content){
					dates.add(a.get(0));
				}
				days = FXCollections.observableArrayList(dates);
				listView.setItems(days);
				daysNumberLabel.setText("TOTAL ZILE : "+days.size());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
  
	public String getPersonalIdentifier() {
		return personalIdentifier;
	}

	public void setPersonalIdentifier(String personalIdentifier) {
		if(personalIdentifier == null)
			this.personalIdentifier = "2271104128886";
		else
			this.personalIdentifier = personalIdentifier;
	}
    
	
	
}
