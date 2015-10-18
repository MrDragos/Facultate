package controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import dataaccess.DataBaseException;
import dataaccess.DataBaseWrapperImpl;
import general.Constants;
import general.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpecificSchedule {
	
    private Stage applicationStage;
    private Scene applicationScene;
    
    public DataBaseWrapperImpl dbWrapper; 
    
    private String personalIdentifier;
    
    @FXML
    private Label holidayLabel;
    
    @FXML
    private CheckBox checkBox;
	
    @FXML
    private TextField weekdayField;
    
    @FXML
    private TextField timeSlotField; 
    
    @FXML
    private TextField locationField; 
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private DatePicker startDatePicker; 
    
    @FXML
    private DatePicker endDatePicker; 
    
    public SpecificSchedule() {
		this.personalIdentifier = "-";
	}
    
    public void start() {

        try {
            applicationScene = new Scene((Parent) FXMLLoader.load(getClass().getResource(Constants.SPECIFIC_SCHEDULE_FXML)));
        } catch (Exception exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }

        applicationStage = new Stage();
        applicationStage.setScene(applicationScene);
        applicationStage.initModality(Modality.APPLICATION_MODAL);
        applicationStage.show();
    }
    
    
    
    @FXML
    protected void closeButtonHandler(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    @FXML
    protected void insertButtonHandler(ActionEvent event) {

    	String weekday = weekdayField.getText();
    	String timeslot = timeSlotField.getText();
    	String location = locationField.getText(); 
    	
    	LocalDate ld = datePicker.getValue();
    	String date = ld.toString();
    	System.out.println(date);
    	
    	if(weekday == null)
    		weekday = "-";
    	
    	if(timeslot == null)
    		timeslot = "-";
    	
    	if(location == null)
    		location = "-";
    	
    	/*
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	
    	try {
    		//System.out.println(formatter.parse(date));
    		//String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(formatter.parse(date));

    		//System.out.println(dayOfWeek); // Friday
			System.out.println(Utilities.dateToWeekday(formatter.parse(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	*/
    	ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("specific_date");
    	attributes.add("weekday");
    	attributes.add("time_slot");
    	attributes.add("location");
    	attributes.add("user_personal_identifier");
    	attributes.add("holiday");
    	
    	ArrayList<String> values = new ArrayList<String>();
    	values.add(date);
    	values.add(weekday);
    	values.add(timeslot);
    	values.add(location);
    	values.add(personalIdentifier);
    	
    	if(checkBox.isSelected())
    		values.add("T");	//true
    	else
    		values.add("F");	//false
    	
    	try {
			int insert = dbWrapper.insertValuesIntoTable("specific_schedule", attributes, values, true);
		} catch (SQLException | DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    @FXML
    protected void updateButtonHandler(ActionEvent event) {
    	
    	String timeslot = timeSlotField.getText();
    	String location = locationField.getText(); 
    	
    	LocalDate ld = datePicker.getValue();
    	String date = ld.toString();
    	
    	ArrayList<String> attributes = new ArrayList<String>();
    	ArrayList<String> values = new ArrayList<String>();
    	
    	if(timeslot != null){
    		attributes.add("time_slot");
    		values.add(timeslot);
    	}
    	
    	if(location != null){
    		attributes.add("location");
    		values.add(location);
    	}
    	
    	attributes.add("holiday");
    	if(checkBox.isSelected())
    		values.add("T");
    	else
    		values.add("F");
    	
    	String whereClause = "user_personal_identifier='"+personalIdentifier+"' AND specific_date='"+date+"'";
    	
    	try {
			int update = dbWrapper.updateRecordsIntoTable("specific_schedule", attributes, values, whereClause);
		} catch (SQLException | DataBaseException e) {
			e.printStackTrace();
		}
    	
    }
    
    public static ArrayList<Date> getDaysBetweenDates(Date startDate, Date endDate)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
    
    @FXML
    protected void vacationButtonHandler(ActionEvent event) {
    	LocalDate ld = startDatePicker.getValue();
    	String startDate = ld.toString();
    	String weekday;
    	ld = endDatePicker.getValue();
    	String endDate = ld.toString();
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	Date start = null;
    	Date  end = null;
		try {
			start = formatter.parse(startDate);
			end = formatter.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	ArrayList<Date> dates = getDaysBetweenDates(start, end);
    	dates.add(end);
    	
    	for(Date date:dates){
    		String specificDate = formatter.format(date);
        	ArrayList<String> attributes = new ArrayList<String>();
        	attributes.add("specific_date");
        	attributes.add("weekday");
        	attributes.add("time_slot");
        	attributes.add("location");
        	attributes.add("user_personal_identifier");
        	attributes.add("holiday");
        	
        	ArrayList<String> values = new ArrayList<String>();
        	values.add(specificDate);
        	values.add(Utilities.dateToWeekday(date));
        	values.add("-");
        	values.add("-");
        	values.add(personalIdentifier);
        	values.add("T");	//setam flag-ul de concediu ca adevarat
        	
        	try {
    			int insert = dbWrapper.insertValuesIntoTable("specific_schedule", attributes, values, true);
    			
    		} catch (SQLException | DataBaseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
    
    @FXML
    protected void clearButtonHandler(ActionEvent event) {
    	locationField.setText("");
    	weekdayField.setText("");
    	timeSlotField.setText("");
    	locationField.setText("");
    	
    }
    
    @FXML
    protected void showButtonHandler(ActionEvent event) {

    	
    	LocalDate ld = datePicker.getValue();
    	String date = ld.toString();
    	
    	ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
    	ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("weekday");
    	attributes.add("time_slot");
    	attributes.add("location");
    	attributes.add("holiday");
    	
    	String whereClause = "user_personal_identifier='"+personalIdentifier+"' AND specific_date='"+date+"'";
    	String weekday;
    	String timeSlot;
    	String location;
    	String holiday;
    	
    	try {
    		content = dbWrapper.getTableContent("specific_schedule", attributes, whereClause, null, null);
    		if(content.size() > 0){
    			weekday = content.get(0).get(0);
    			timeSlot = content.get(0).get(1);
    			location = content.get(0).get(2);
    			holiday = content.get(0).get(3);
    			
    			weekdayField.setText(weekday);
    			locationField.setText(location);
    			timeSlotField.setText(timeSlot);
    			
    			if(holiday.equals("T")){
    				holidayLabel.setText("YES");
    			}
    			
    			if(holiday.equals("F")){
    				holidayLabel.setText("NO");
    			}
    			
    		}else{
    			//daca nu exista inregistrare in orarul specific , va lua datele din orarul general
    			//TODO
    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    			weekday = Utilities.dateToWeekday(formatter.parse(date));	//obtin ziua saptamanii pe baza datei calendaristice
    			
    			attributes = new ArrayList<String>();
    	    	attributes.add("weekday");
    	    	attributes.add("time_slot");
    	    	attributes.add("location");
    	    	whereClause = "user_personal_identifier='"+personalIdentifier+"' AND weekday='"+weekday+"'";
    			
        		content = dbWrapper.getTableContent("schedule", attributes, whereClause, null, null);

       			weekday = content.get(0).get(0);
    			timeSlot = content.get(0).get(1);
    			location = content.get(0).get(2);
    		
    			weekdayField.setText(weekday);
    			locationField.setText(location);
    			timeSlotField.setText(timeSlot);
        		
    			holidayLabel.setText("NO");
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
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

	public void init() {
		dbWrapper = DataBaseWrapperImpl.getInstance();
	}
    
}
