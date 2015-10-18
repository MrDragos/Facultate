package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import dataaccess.DataBaseException;
import dataaccess.DataBaseWrapperImpl;

public class History {
	
    private Stage applicationStage;
    private Scene applicationScene;
    
    public DataBaseWrapperImpl dbWrapper; 
    
    private String personalIdentifier;
    private String paraphCode;
    
    @FXML
    Label paraphCodeLabel;
    
    @FXML
    TextField numePacientLabel;
    
    @FXML
    TextField releaseDateField;
    
    @FXML
    TextField medicalServiceField;
    
    @FXML
    TextField prenumePacientLabel;
    
    @FXML
    TextField numeMedicLabel;
    
    @FXML
    TextField prenumeMedicLabel;
    
    @FXML
    DatePicker datePicker;
    
    @FXML
    TextArea simpotmsArea;
    
    @FXML
    TextArea historyArea;
    
    @FXML
    TextArea diagnosticArea;
    
    @FXML
    TextArea recomandationsArea;
    
    @FXML
    TextArea rapoarteArea;

    public History() {
		//this.personalIdentifier = "-";
	}
  
    @FXML
    protected void arataButtonHandler(ActionEvent event) {
    	String firstName = prenumePacientLabel.getText();
    	String lastName = numePacientLabel.getText();
    	
		DataBaseWrapperImpl dbWrapper = DataBaseWrapperImpl.getInstance();
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
    	ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("reservation_date");
    	String whereClause = "patient_first_name='"+firstName+"' AND patient_last_name='"+lastName+"'";
    	
    	String areaContent = "";
    	try {
			content = dbWrapper.getTableContent("raport", attributes, whereClause, null, null);
			for(ArrayList<String> r:content){
				areaContent += r.get(0)+"\n";
			}
			rapoarteArea.setText(areaContent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    protected void afiseazaButtonHandler(ActionEvent event) {
    	String firstName = prenumePacientLabel.getText();
    	String lastName = numePacientLabel.getText();
    	
    	LocalDate ld = datePicker.getValue();
    	String date = ld.toString();
    	DataBaseWrapperImpl dbWrapper = DataBaseWrapperImpl.getInstance();
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
    	ArrayList<String> attributes = new ArrayList<String>();

    	attributes.add("medic_first_name");
    	attributes.add("medic_last_name");
    	attributes.add("reservation_date");
    	attributes.add("medical_service");
    	attributes.add("istoric");
    	attributes.add("simptome");
    	attributes.add("diagnostic");
    	attributes.add("recomandari");
    	attributes.add("paraph_code");
    	attributes.add("medical_service");

    	String whereClause = "patient_first_name='"+firstName+"' AND patient_last_name='"+lastName+"' AND reservation_date='"+date+"'";
    	
    	try {
			content = dbWrapper.getTableContent("raport", attributes, whereClause, null, null);
	    	prenumeMedicLabel.setText(content.get(0).get(1));
	    	numeMedicLabel.setText(content.get(0).get(1));
	    	releaseDateField.setText(content.get(0).get(2));
	    	historyArea.setText(content.get(0).get(3));
	    	simpotmsArea.setText(content.get(0).get(4));
	    	diagnosticArea.setText(content.get(0).get(5));
	    	recomandationsArea.setText(content.get(0).get(6));
	    	paraphCodeLabel.setText(content.get(0).get(7));
	    	medicalServiceField.setText(content.get(0).get(8));
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    /*
    @FXML
    protected void paraphButtonHandler(ActionEvent event) {
       
    	LocalDate localDate = datePicker.getValue();
    	String date = localDate.toString();
		
		DataBaseWrapperImpl dbWrapper = DataBaseWrapperImpl.getInstance();
		//sArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
    	ArrayList<String> attributes = new ArrayList<String>();
    	ArrayList<String> values = new ArrayList<String>();

    	attributes.add("patient_first_name");
    	attributes.add("patient_last_name");
    	attributes.add("medic_first_name");
    	attributes.add("medic_last_name");
    	attributes.add("reservation_date");
    	attributes.add("medical_service");
    	attributes.add("istoric");
    	attributes.add("simptome");
    	attributes.add("diagnostic");
    	attributes.add("recomandari");
    	attributes.add("paraph_code");
    	
    	values.add(prenumePacientLabel.getText());
    	values.add(numePacientLabel.getText());
    	values.add(numeMedicLabel.getText());
    	values.add(prenumeMedicLabel.getText());
    	values.add(date);
    	values.add(medicalServiceField.getText());
    	values.add(historyArea.getText());
    	values.add(simpotmsArea.getText());
    	values.add(diagnosticArea.getText());
    	values.add(recomandationsArea.getText());
    	values.add(getParaphCode());
    	
    	try {
			int insert = dbWrapper.insertValuesIntoTable("raport", attributes, values, true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	paraphcodeLabel.setText("Paraphcode: "+getParaphCode());
    	
    	prenumeMedicLabel.setEditable(false);
    	numeMedicLabel.setEditable(false);
    	prenumePacientLabel.setEditable(false);
    	numeMedicLabel.setEditable(false);
    	medicalServiceField.setEditable(false);
    	datePicker.setEditable(false);
        investigationsListView.setEditable(false);
        historyArea.setEditable(false);
        diagnosticArea.setEditable(false);
        recomandationsArea.setEditable(false);        
        //daysArea.setEditable(false);
        
        
    }*/
    
    @FXML
    protected void autoButtonHandler(ActionEvent event) {
    	
    }
    
	public String getPersonalIdentifier() {
		return personalIdentifier;
	}

	public void setPersonalIdentifier(String personalIdentifier) {
		this.personalIdentifier = personalIdentifier;
	}

	public void init() {
		
		DataBaseWrapperImpl dbWrapper = DataBaseWrapperImpl.getInstance();
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
    	ArrayList<String> attributes = new ArrayList<String>();
    	attributes.add("first_name");
    	attributes.add("last_name");
    	attributes.add("paraph_code");
    	String whereClause = "personal_identifier='"+personalIdentifier+"'";
    	try {
			content = dbWrapper.getTableContent("user", attributes, whereClause, null, null);
			//numeMedicLabel.setText(content.get(0).get(1));
			//prenumeMedicLabel.setText(content.get(0).get(0));
			setParaphCode(content.get(0).get(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
	}

	public String getParaphCode() {
		return paraphCode;
	}

	public void setParaphCode(String paraphCode) {
		this.paraphCode = paraphCode;
	}
	
    
}
