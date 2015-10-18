package controller;

import general.Interval;
import general.Utilities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import dataaccess.DataBaseWrapperImpl;

public class SalaryWindow {
	
    private Stage applicationStage;
    private Scene applicationScene;
    
    public DataBaseWrapperImpl dbWrapper; 
    
    private String personalIdentifier;
    
    @FXML
    Label firstNameLabel;
    
    @FXML
    TextField yearTextField;
    
    @FXML
    TextField monthTextField;
    
    @FXML
    Label lastNameLabel;
    
    @FXML
    Label salaryLabel;
    
    @FXML
    Label workingHoursLabel;
    
    @FXML
    Label totalHoursLabel;
    
    @FXML
    Label totalSalaryLabel;
    
    @FXML
    TextArea daysArea;
    
    public void populateArea(ArrayList<String> content){
    	String list = new String("");
    	for(String line:content){
    		list += line + "\n";
    	}
    	daysArea.setText(list);
    }

    public SalaryWindow() {
		//this.personalIdentifier = "-";
	}
  
	public String getPersonalIdentifier() {
		return personalIdentifier;
	}

	public void setPersonalIdentifier(String personalIdentifier) {
		this.personalIdentifier = personalIdentifier;
	}
	
	@FXML
	public void salaryButtonHandler(ActionEvent event){
		
		Integer salary = Integer.parseInt(salaryLabel.getText().substring(8));
		System.out.println(salary);
		Integer workinHours = Integer.parseInt(workingHoursLabel.getText().substring(15));
		System.out.println(workinHours);
		
		Hashtable<String,Double> generalSchedule = new Hashtable<String,Double>();
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		String whereClause;
		
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
				if(yearTextField.getText() == null)
					System.out.println("NASOL");
				String year = yearTextField.getText();
				String month = monthTextField.getText();
				System.out.println(year);
				System.out.println(month);
				
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
				
				String areaContent = "";
				Double total = 0.0;
				Double hours;
				for(String date: monthdates){
					hours = days.get(date);
					if(hours == -1.0){
						areaContent += date + "\t"+"CONCEDIU\n";
					}else if(hours == 0){
						areaContent += date + "\t"+"ZI NELUCRATOARE\n";
					}else{
						areaContent += date + "\t"+(Math.floor(hours*100)/100)+" ore\n";
						total += hours;
					}
				}

				daysArea.setText(areaContent);
				Double raport = total/(double)workinHours;
				if(raport > 1.0)
					raport = 1.0;
				Double totalSalary = Math.floor(100*((double) salary) *raport)/100;
				totalHoursLabel.setText("Total Hours: "+Math.floor(100*total)/100+"");
				totalSalaryLabel.setText("Total Salary:"+totalSalary+"");

	}
	


	public void init() {
		dbWrapper = DataBaseWrapperImpl.getInstance();
				
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		String whereClause;
		ArrayList<String> attributes = new ArrayList<String>();		
		
		//USER
		attributes.add("first_name");
		attributes.add("last_name");
		attributes.add("working_hours");
		attributes.add("salary");
		whereClause = "personal_identifier='"+personalIdentifier+"'";
		
		try {
			content = dbWrapper.getTableContent("user", attributes, whereClause, null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		firstNameLabel.setText("First Name: "+content.get(0).get(0));
		lastNameLabel.setText("Last Name: "+content.get(0).get(1));
		workingHoursLabel.setText("Working Hours: "+content.get(0).get(2));
		salaryLabel.setText("Salary: "+content.get(0).get(3));
	
	}
	
    
}
