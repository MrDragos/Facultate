package controller;

import general.Interval;
import general.Utilities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dataaccess.DataBaseWrapperImpl;

public class ProfitWindow {
	
    private Stage applicationStage;
    private Scene applicationScene;
    
    public DataBaseWrapperImpl dbWrapper; 
    
    private String personalIdentifier;
    
    @FXML
    TextField yearTextField;

    @FXML
    TextField monthTextField;

    @FXML
    TextArea cheltuieliArea;
    
    @FXML
    TextArea venituriArea;
    
    @FXML
    protected void profitButtonHandler(ActionEvent event) {
    	
    }
    
	public String getPersonalIdentifier() {
		return personalIdentifier;
	}

	public void setPersonalIdentifier(String personalIdentifier) {
		this.personalIdentifier = personalIdentifier;
	}

	public void init() {
		dbWrapper = DataBaseWrapperImpl.getInstance();
	}
	
	public Double computeSalary(String personalIdentifier){
		Integer salary = 0;
		Integer workinHours = 0;
		
		Hashtable<String,Double> generalSchedule = new Hashtable<String,Double>();
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		String whereClause;
		
		//USER
		attributes.add("working_hours");
		attributes.add("salary");
		whereClause = "personal_identifier='"+personalIdentifier+"'";
		
		try {
			content = dbWrapper.getTableContent("user", attributes, whereClause, null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//GENERAL_SCHEDULE
		
				attributes = new ArrayList<String>();
				attributes.add("weekday");
				attributes.add("time_slot");
				whereClause = "user_personal_identifier='"+personalIdentifier+"'";
				
				try {
					content = dbWrapper.getTableContent("schedule", attributes, whereClause, null, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				Interval interval;
				for(ArrayList<String> day:content){
					interval = new Interval(day.get(1));
					generalSchedule.put(day.get(0), interval.getHours());
				}
				

				
		//SPECIFIC_SCHEDULE

				//String year = yearTextField.getText();
				//String month = monthTextField.getText();
				
				ArrayList<String> monthdates = Utilities.generateMonthDates(yearTextField.getText(),monthTextField.getText());
				Hashtable<String, Double> days = new Hashtable<String, Double>();
				
				//initializez hashtabel-ul cu valori din General Schedule
				for(String monthDate:monthdates){
					if(generalSchedule.containsKey(Utilities.dateToWeekday(monthDate)))
						days.put(monthDate, generalSchedule.get(Utilities.dateToWeekday(monthDate)));
					else
						days.put(monthDate, 0.0);
				}
				
				attributes = new ArrayList<String>();
				attributes.add("specific_date");
				attributes.add("time_slot");
				attributes.add("holiday");
				whereClause = "user_personal_identifier='"+personalIdentifier+"'";
				

				try {
					content = dbWrapper.getTableContent("specific_schedule", attributes, whereClause, null, null);
				
					for(ArrayList<String> day:content){
						if(days.containsKey(day.get(0))){
							
							if(day.get(2).equals("F")){
								//daca nu este in concediu
								interval = new Interval(day.get(1));
								days.put(day.get(0), interval.getHours());
							}
							
							if(day.get(2).equals("T")){
								//daca este in concediu
								days.put(day.get(0), -1.0);//daca este concediu va avea valori negative
							}
						}
							
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}		
				

				Double total = 0.0;
				Double hours;
				for(String date: monthdates){
					hours = days.get(date);
					if(hours == -1.0){
		
					}else if(hours == 0){
	
					}else{	
						total += hours;
					}
				}

				Double raport = total/(double)workinHours;
				if(raport > 1.0)
					raport = 1.0;
				Double totalSalary = Math.floor(100*((double) salary) *raport)/100;
				
				return totalSalary;
	}
	
    
}

