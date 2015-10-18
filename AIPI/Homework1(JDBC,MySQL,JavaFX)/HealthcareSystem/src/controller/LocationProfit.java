package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import dataaccess.DataBaseWrapperImpl;

public class LocationProfit {
	
    private Stage applicationStage;
    private Scene applicationScene;
    
    public DataBaseWrapperImpl dbWrapper; 
    
    private String personalIdentifier;
    
    @FXML
    Label totalProfitLabel;
    
    @FXML
    ComboBox comboBox;

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
	
    
}
