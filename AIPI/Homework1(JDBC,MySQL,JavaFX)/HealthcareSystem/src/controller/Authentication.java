package controller;

import general.Constants;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import dataaccess.DataBaseException;
import dataaccess.DataBaseWrapper;
import dataaccess.DataBaseWrapperImpl;

public class Authentication {

	//luiza.voinea
	//giw2G:-t/|
	
    private Stage applicationStage;
    private Scene applicationScene;
    
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    
    String personal_identifier;
	
    public void start() {

        try {
            applicationScene = new Scene((Parent) FXMLLoader.load(getClass().getResource(Constants.AUTHENTICATION_FXML)));
        } catch (Exception exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }

        applicationStage = new Stage();
        applicationStage.setTitle(Constants.APPLICATION_NAME);
        applicationStage.getIcons().add(new Image(getClass().getResource(Constants.ICON_FILE_NAME).toExternalForm()));
        applicationStage.setScene(applicationScene);
        applicationStage.show();

    }
    
    @FXML
    protected void loginButtonHandler(ActionEvent event) throws Exception {
    	String userName = usernameTextField.getText();
    	String password = passwordField.getText();
    	
    	System.out.println(userName+password);
    	
    	DataBaseWrapperImpl dataBaseWrapper = DataBaseWrapperImpl.getInstance();
    	dataBaseWrapper.setDefaultDatabase(Constants.DATABASE_NAME);
    	ArrayList<String> columnNames = new ArrayList<String>();
    	columnNames.add("position");
    	columnNames.add("personal_identifier");
    	//FadeTransition
    	String whereClause = "password=\""+password+"\" AND username=\""+userName+"\"";
    	//System.out.println(whereClause);[]
    	String orderByClause = null;
    	String groupByClause = null;
    	//System.out.println("Debug");
    	try {
			ArrayList<ArrayList<String>> tableContent  = new ArrayList<ArrayList<String>>();
			tableContent =  dataBaseWrapper.getTableContent("user", columnNames, whereClause, orderByClause, groupByClause);
			//System.out.println(tableContent.get(0).get(0));
			personal_identifier = tableContent.get(0).get(1);
			
			if(tableContent.get(0).get(0).equals("administrator")){
				//System.out.println("True");
				Container container = new Container();
				container.start();

				
				Node node = (Node) event.getSource();
				node.getScene().getWindow().hide();
				
			}else if(tableContent.get(0).get(0).equals("super-administrator")){
				//System.out.println("True");
				Container container = new Container();
				container.start();
				
				Node node = (Node) event.getSource();
				node.getScene().getWindow().hide();
			}else if(tableContent.get(0).get(0).equals("medic")){
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MEDIC_FXML));
		        try {
					Parent root = (Parent)loader.load();
		            MedicWindow controller = loader.<MedicWindow>getController();
		            controller.setPersonalIdentifier(personal_identifier);
		            controller.init();
		            applicationScene = new Scene(root);
		            
		            applicationStage = new Stage();
		            applicationStage.setScene(applicationScene);
		            applicationStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Node node = (Node) event.getSource();
				node.getScene().getWindow().hide();
			}else if(tableContent.get(0).get(0).equals("inspector resurse umane")){

		    	FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.HR_FXML));
		        try {
					Parent root = (Parent)loader.load();
		            HrWindow controller = loader.<HrWindow>getController();
		            controller.setPersonalIdentifier(personal_identifier);
		            controller.init();
		            applicationScene = new Scene(root);
		            
		            applicationStage = new Stage();
		            applicationStage.setScene(applicationScene);
		            applicationStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Node node = (Node) event.getSource();
				node.getScene().getWindow().hide();
			}else if(tableContent.get(0).get(0).equals("receptioner")){
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.RECEPTIONER_FXML));
		        try {
					Parent root = (Parent)loader.load();
		            ReceptionerWindow controller = loader.<ReceptionerWindow>getController();
		            controller.setPersonalIdentifier(personal_identifier);
		            controller.init();
		            applicationScene = new Scene(root);
		            
		            applicationStage = new Stage();
		            applicationStage.setScene(applicationScene);
		            applicationStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Node node = (Node) event.getSource();
				node.getScene().getWindow().hide();
			}else if(tableContent.get(0).get(0).equals("expert financiar-contabil")){
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.FINANCE_FXML));
		        try {
					Parent root = (Parent)loader.load();
		            FinanceWindow controller = loader.<FinanceWindow>getController();
		            controller.setPersonalIdentifier(personal_identifier);
		            controller.init();
		            applicationScene = new Scene(root);
		            
		            applicationStage = new Stage();
		            applicationStage.setScene(applicationScene);
		            applicationStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Node node = (Node) event.getSource();
				node.getScene();
				
			}else if(tableContent.get(0).get(0).equals("asistent medical")){
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.ASISTENT_FXML));
		        try {
					Parent root = (Parent)loader.load();
		            AsistentWindow controller = loader.<AsistentWindow>getController();
		            controller.setPersonalIdentifier(personal_identifier);
		            controller.init();
		            applicationScene = new Scene(root);
		            
		            applicationStage = new Stage();
		            applicationStage.setScene(applicationScene);
		            applicationStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Node node = (Node) event.getSource();
				node.getScene();				
			}else{
				 throw new DataBaseException();
			}
		} catch (Exception e) {
			
			usernameTextField.clear();
			passwordField.clear();
			System.out.println(e.getMessage());
			errorLabel.setText(e.getMessage());
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(5000),errorLabel);
			fadeTransition.setFromValue(1.0);
			fadeTransition.setToValue(0.0);
			fadeTransition.play();		
		}
    }
    
    @FXML
    protected void cancelButtonHandler(ActionEvent event) {

    	Platform.exit();

    }
}
