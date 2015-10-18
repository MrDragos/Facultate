package controller;

import java.sql.SQLException;
import java.util.ArrayList;

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

public class SearchUser {

    private Stage applicationStage;
    private Scene applicationScene;
    
    public String personalIdentifier;
    
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField position;
    
    public String first;
    public String last;
    public String pos;

    //private static String windowTitle;
    //private static String iconLocation;
    //private static String messageContent;

    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label positionLabel;
    
    @FXML
    private Label addressLabel;
    
    @FXML
    private Label usernameLabel;
    
    @FXML
    private Label passwordLabel;
    
    @FXML
    private Label emailLabel;
    
    @FXML
    private Label workingHoursLabel;
    
    @FXML
    private Label ibanLabel;
    
    @FXML
    private Label phoneLabel;
    
    @FXML
    private Label contractNumberLabel;
    
    @FXML
    private Label scientificTitleLabel;

    @FXML
    private Label personalIdentifierLabel;
    
    @FXML
    private Label salaryLabel;
    
    @FXML
    private Label didacticChairLabel;
    
    @FXML
    private Label hireDateLabel;
    
    public String fxmlName;
    
    public void start() {

        try {
            applicationScene = new Scene((Parent) FXMLLoader.load(getClass().getResource(Constants.SEARCH_USER_FXML)));
        } catch (Exception exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }

        applicationStage = new Stage();
        
       
        //applicationStage.getIcons().add(new Image(getClass().getResource(Constants.ICON_FILE_NAME).toExternalForm()));
        applicationStage.setScene(applicationScene);
        applicationStage.initModality(Modality.APPLICATION_MODAL);
        applicationStage.show();
    }


    
    @FXML
    protected void searchButtonHandler(ActionEvent event) {
        //((Node) event.getSource()).getScene().getWindow().hide();
    	System.out.println("Merge");
    	
    	first = firstName.getText();
    	last = lastName.getText();
    	pos = position.getText();
    	
    	DataBaseWrapperImpl dbWrapper = DataBaseWrapperImpl.getInstance();
    	ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
    	ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("personal_identifier");
    	attributes.add("first_name");
    	attributes.add("last_name");
    	attributes.add("address");
    	attributes.add("phone_number");
    	attributes.add("email");
    	attributes.add("IBAN_account");
    	attributes.add("contract_number");
    	attributes.add("hire_date");
    	attributes.add("position");
    	attributes.add("salary");
    	attributes.add("working_hours");
    	attributes.add("paraph_code");
    	attributes.add("scientific_title");
    	attributes.add("didactic_chair");
    	attributes.add("username");
    	attributes.add("password");

    	String whereClause = "first_name='"+first+"' AND last_name='"+last+"' AND position='"+pos+"'";
    	String orderByClause = "";
    	try {
			content = dbWrapper.getTableContent("user", attributes, whereClause, null, null);
			personalIdentifier = content.get(0).get(0);
			System.out.println(personalIdentifier);
			
			personalIdentifierLabel.setText("Personal Identigier: "+content.get(0).get(0));
			
			nameLabel.setText(content.get(0).get(1)+" "+content.get(0).get(2));
			addressLabel.setText("Address: "+content.get(0).get(3));
			phoneLabel.setText("PhoneNumber: "+content.get(0).get(4));
			emailLabel.setText("Email: "+content.get(0).get(5));
			ibanLabel.setText("IBAN: "+content.get(0).get(6));
			contractNumberLabel.setText("Contract Number: "+content.get(0).get(7));
			hireDateLabel.setText("Hire date: "+content.get(0).get(8));
			positionLabel.setText("Position: "+content.get(0).get(9));
			salaryLabel.setText("Salary: "+content.get(0).get(10));
			workingHoursLabel.setText("Working Hours: "+content.get(0).get(11));
			scientificTitleLabel.setText("Scientific title: "+content.get(0).get(13));
			didacticChairLabel.setText("Didactic Chair: "+content.get(0).get(14));
			usernameLabel.setText("Username: "+content.get(0).get(15));
			passwordLabel.setText("Password: "+content.get(0).get(16));

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    @FXML
    public void initialize() {
    
    }
    
    @FXML
    protected void closeButtonHandler(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    @FXML
    protected void generalScheduleButtonHandler(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.GENERAL_SCHEDULE_FXML));
            Parent root = (Parent)loader.load();
            GeneralSchedule controller = loader.<GeneralSchedule>getController();
            controller.setPersonalIdentifier(personalIdentifier);
            controller.init();
            applicationScene = new Scene(root);
        } catch (Exception exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }

        applicationStage = new Stage();
        applicationStage.setScene(applicationScene);
        applicationStage.show();
 
    }
    
    @FXML
    protected void specificScheduleButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.SPECIFIC_SCHEDULE_FXML));
            Parent root = (Parent)loader.load();
            SpecificSchedule controller = loader.<SpecificSchedule>getController();
            controller.setPersonalIdentifier(personalIdentifier);
            controller.init();
            applicationScene = new Scene(root);
        } catch (Exception exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }

        applicationStage = new Stage();
        applicationStage.setScene(applicationScene);
        applicationStage.show();
    }
    
    @FXML
    protected void vacationButtonHandler(ActionEvent event) {
        try {
        	System.out.println(personalIdentifier+" AAAAA");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.CONCEDIU_FXML));
            Parent root = (Parent)loader.load();
            Concediu controller = loader.<Concediu>getController();
            if(controller == null)
            	System.out.println("E NULL");
            else
            	System.out.println("NU E NULL");
            controller.setPersonalIdentifier(personalIdentifier);
            controller.init();
            applicationScene = new Scene(root);
        } catch (Exception exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }
        applicationStage = new Stage();
        applicationStage.setScene(applicationScene);
        applicationStage.show();
    }
    
   
}

