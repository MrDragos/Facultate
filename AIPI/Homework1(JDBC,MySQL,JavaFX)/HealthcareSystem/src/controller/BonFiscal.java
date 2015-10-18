package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import dataaccess.DataBaseWrapperImpl;

public class BonFiscal {
	
    private Stage applicationStage;
    private Scene applicationScene;
    
    public DataBaseWrapperImpl dbWrapper; 
    
    private Integer medicalServiceId;
    private String date;
    
    
    @FXML
    Label totalLabel;
    
    @FXML
    Label dateLabel;
    
    @FXML
    TextArea textArea;
    

    public BonFiscal() {
		//this.personalIdentifier = "-";
	}

	public void init() {
		
		dateLabel.setText("DATA: "+date);
		dbWrapper = DataBaseWrapperImpl.getInstance();
		ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
		ArrayList<String> attributes = new ArrayList<String>();
		String whereClause = "id="+medicalServiceId;
		
		attributes.add("speciality");
		try {
			content = dbWrapper.getTableContent("medical_service", attributes, whereClause, null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String areaContent="";
		areaContent = "MEDICAL SERVICE : "+content.get(0).get(0)+"\n\n";
		areaContent += "Investigations: \n\n";
		
		attributes = new ArrayList<String>();
		attributes.add("i.name");
		attributes.add("i.price");
		whereClause = "ia.medical_service_id="+medicalServiceId+" AND i.id=ia.investigation_id";
		
		Integer total = 0;
		try {
			content = dbWrapper.getTableContent("investigation i,investigations_assoc ia", attributes, whereClause, null, null);
			for(ArrayList<String> investigation:content){
				areaContent += investigation.get(0)+"\t"+investigation.get(1)+" RON\n";
				total += Integer.parseInt(investigation.get(1));
			}
			textArea.setText(areaContent);
			textArea.setEditable(false);
			totalLabel.setText("TOTAL: "+total+" RON");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getMedicalServiceId() {
		return medicalServiceId;
	}

	public void setMedicalServiceId(Integer medicalServiceId) {
		this.medicalServiceId = medicalServiceId;
	}
	
    
}
