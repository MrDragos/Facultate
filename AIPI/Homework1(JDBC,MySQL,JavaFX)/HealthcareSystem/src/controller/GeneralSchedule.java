package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import dataaccess.DataBaseException;
import dataaccess.DataBaseWrapperImpl;
import general.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GeneralSchedule {

    private Stage applicationStage;
    private Scene applicationScene;
   
    
    public DataBaseWrapperImpl dbWrapper; 
    
    public String personalIdentifier;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private TextField timeSlotField;
    
    @FXML
    private TextField locationField;
    
    @FXML
    private TextField weekdayField;
    
    @FXML
    private Label mondayLabel;
    
    @FXML
    private Label tuesdayLabel;
    
    @FXML
    private Label wednesdayLabel;
    
    @FXML
    public Label thursdayLabel;
    
    @FXML
    private Label fridayLabel;
    
    @FXML
    private Label saturdayLabel;
    
    @FXML
    private Label sundayLabel;
    
    @FXML
    private Label mondayLocation;
    
    @FXML
    private Label tuesdayLocation;
    
    @FXML
    private Label wednesdayLocation;
    
    @FXML
    public Label thursdayLocation;
    
    @FXML
    private Label fridayLocation;
    
    @FXML
    private Label saturdayLocation;
    
    @FXML
    private Label sundayLocation;
    
    
	public GeneralSchedule() {
	
	}

    
    public void setLabels(ArrayList<String> schedule){
    	String weekday = schedule.get(0);
    	String timeSlot = schedule.get(1);
    	String location = schedule.get(2);
    	
    	switch (weekday) {
			case "luni":{
				mondayLabel.setText(timeSlot);
				mondayLocation.setText(location);
			}
				break;
				
			case "marti":{
				tuesdayLabel.setText(timeSlot);
				tuesdayLocation.setText(location);
			}
				break;
				
			case "miercuri":{
				wednesdayLabel.setText(timeSlot);
				wednesdayLocation.setText(location);
			}
				break;
				
			case "joi":{
				thursdayLabel.setText(timeSlot);
				thursdayLocation.setText(location);
			}
				break;
				
			case "vineri":{
				fridayLabel.setText(timeSlot);
				fridayLocation.setText(location);
			}
				break;
				
			case "sambata":{
				saturdayLabel.setText(timeSlot);
				saturdayLocation.setText(location);
			}
				break;
				
			case "duminica":{
				sundayLabel.setText(timeSlot);
				sundayLocation.setText(location);
			}
				break;

			default:
				break;
		}
    	
    	
    }
    
    public void setTime(String weekday,String timeSlot){
  	
    	switch (weekday) {
			case "luni":{
				mondayLabel.setText(timeSlot);
			}
				break;
				
			case "marti":{
				tuesdayLabel.setText(timeSlot);
			}
				break;
				
			case "miercuri":{
				wednesdayLabel.setText(timeSlot);
			}
				break;
				
			case "joi":{
				System.out.println("DC nu mergi");
				thursdayLabel.setText(timeSlot);
			}
				break;
				
			case "vineri":{
				fridayLabel.setText(timeSlot);
			}
				break;
				
			case "sambata":{
				saturdayLabel.setText(timeSlot);
			}
				break;
				
			case "duminica":{
				sundayLabel.setText(timeSlot);
			}
				break;

			default:
				break;
		}	
    }
   
    public void setLocation(String weekday,String location){

    	switch (weekday) {
			case "luni":{
				mondayLocation.setText(location);
			}
				break;
				
			case "marti":{
				tuesdayLocation.setText(location);
			}
				break;
				
			case "miercuri":{
				wednesdayLocation.setText(location);
			}
				break;
				
			case "joi":{
				System.out.println("DC nu mergi");
				thursdayLocation.setText(location);
			}
				break;
				
			case "vineri":{
				fridayLocation.setText(location);
			}
				break;
				
			case "sambata":{
				saturdayLocation.setText(location);
			}
				break;
				
			case "duminica":{
				sundayLocation.setText(location);
			}
				break;

			default:
				break;
		}	
    }
    
    
    public void init(){
    	dbWrapper = DataBaseWrapperImpl.getInstance();
    	ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
    	ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("weekday");
    	attributes.add("time_slot");
    	attributes.add("location");
    	String whereClause = "user_personal_identifier='"+personalIdentifier+"'";
    	
    	try {
			content = dbWrapper.getTableContent("schedule", attributes, whereClause, null, null);
			
	    	for(ArrayList<String> schedule: content){
	    		setLabels(schedule);
	    	}
	    	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
    }
    
    @FXML
    protected void updateButtonHandler(ActionEvent event) {
    	
    	String weekday = weekdayField.getText();
    	String timeslot = timeSlotField.getText();
    	String location = locationField.getText(); 
    	
    	ArrayList<String> attributes = new ArrayList<String>();
    	ArrayList<String> values = new ArrayList<String>();
    	
    	if(timeslot != null){
    		attributes.add("time_slot");
    		values.add(timeslot);
    		setTime(weekday, timeslot);
    	}
    	
    	if(location != null){
        	attributes.add("location");
        	values.add(location);
        	setLocation(weekday, location);
    	}
    	
    	String whereClause = "user_personal_identifier='"+personalIdentifier+"' AND weekday='"+weekday+"'";
    	
    	try {
			int update = dbWrapper.updateRecordsIntoTable("schedule", attributes, values, whereClause);
		} catch (SQLException | DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    protected void insertButtonHandler(ActionEvent event) {
    	
    	
    	//System.out.println("INSERT "+personalIdentifier);
    	String weekday = weekdayField.getText();
    	String timeslot = timeSlotField.getText();
    	String location = locationField.getText();

    	if(timeslot == null){
    		timeslot = "-";
    	}
    	
    	if(location == null){
    		location = "-";
    	}
    	
    	ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("weekday");
    	attributes.add("time_slot");
    	attributes.add("location");
    	attributes.add("user_personal_identifier");
    	
    	ArrayList<String> values = new ArrayList<String>();
    	values.add(weekday);
    	values.add(timeslot);
    	values.add(location);
    	values.add(personalIdentifier);
    	
    	try {
			int insert = dbWrapper.insertValuesIntoTable("schedule", attributes, values, true);
			setLocation(location, location);
			setTime(weekday, timeslot);
			
		} catch (SQLException | DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    @FXML
    protected void closeButtonHandler(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

	public String getPersonalIdentifier() {
		return personalIdentifier;
	}

	public void setPersonalIdentifier(String personalIdentifier) {
		this.personalIdentifier = personalIdentifier;
	}
    
	
	
}
