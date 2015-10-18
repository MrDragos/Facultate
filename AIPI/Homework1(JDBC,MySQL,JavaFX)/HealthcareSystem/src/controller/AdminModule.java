package controller;

import general.Constants;
import general.Utilities;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Model;
import model.User;
import dataaccess.DataBaseWrapperImpl;

public class AdminModule {
    private String tableName;

    private ArrayList<Label> attributesLabels;
    private ArrayList<Control> attributesControls;
    
    private Stage applicationStage;
    private Scene applicationScene;

    private DataBaseWrapperImpl databaseWrapper;

    @FXML
    private TableView<Model> tableContentTableView;
    @FXML
    private GridPane attributesGridPane;

    
    
    public AdminModule() {
        this.tableName = "user";
        databaseWrapper = DataBaseWrapperImpl.getInstance();
    }
    
    public void start() {

        try {
            //applicationScene = new Scene((Parent) FXMLLoader.load(getClass().getResource(Constants.CONTAINER_FXML)));
            applicationScene = new Scene((Parent) FXMLLoader.load(getClass().getResource(Constants.ADMIN_FXML)));
           // System.out.println(username+" ##### "+password);
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
        applicationStage.setMaximized(true);
        applicationStage.show();
        //System.out.println("A ajuns aici");
        try {
			initialize();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
        System.out.println("A ajuns aici");
       
       /* 
        try {
			initialize();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		*/
       // System.out.println("A ajuns aici");
    }

    @FXML
    public void initialize() throws SQLException {

        if (tableName == null || tableName.equals("")) {
            return;
        }

        if (Constants.DEBUG) {
            System.out.println("starting initialize " + tableName + "...");
        }

        ArrayList<String> attributes = databaseWrapper.getTableColumns(tableName);

        attributesLabels = new ArrayList<>();
        attributesControls = new ArrayList<>();

        tableContentTableView.setItems(null);
        if (tableContentTableView.getColumns() != null) {
            tableContentTableView.getColumns().clear();
        }

        tableContentTableView.setEditable(true);
        int currentIndex = 0;
        for (String attribute : attributes) {

            TableColumn<Model, String> column = new TableColumn<Model, String>(attribute);
            column.setMinWidth(tableContentTableView.getPrefWidth() / attributes.size());
            column.setCellValueFactory(new PropertyValueFactory<Model, String>(attribute));
            tableContentTableView.getColumns().add(column);

            Label label = new Label(attribute);
            GridPane.setConstraints(label, 0, currentIndex);
            //aici voi modifica labelurile
            attributesLabels.add(label);
            attributesGridPane.getChildren().add(label);

            String parentTable = databaseWrapper.getForeignKeyParentTable(tableName, attribute);
            
            if (parentTable != null) {
                ComboBox<String> attributeComboBox = new ComboBox<String>();
                attributeComboBox.setMinWidth(Constants.DEFAULT_COMBOBOX_WIDTH);
                attributeComboBox.setMaxWidth(Constants.DEFAULT_COMBOBOX_WIDTH);
                ArrayList<ArrayList<String>> parentTableContent = databaseWrapper.getTableContent(parentTable, null, null, null, null);
                for (ArrayList<String> parentTableRecord : parentTableContent) {
                    attributeComboBox.getItems().add(Utilities.compress(parentTableRecord));
                }
                
                GridPane.setConstraints(attributeComboBox, 1, currentIndex);
                attributesControls.add(attributeComboBox);
                attributesGridPane.getChildren().add(attributeComboBox);
            } else {
                TextField attributeTextField = new TextField();
                attributeTextField.setMinWidth(Constants.DEFAULT_TEXTFIELD_WIDTH);
                attributeTextField.setPromptText(attribute);
                if (currentIndex == 0 && databaseWrapper.isAutoGeneratedPrimaryKey(tableName)) {
                    attributeTextField.setText(Integer.toString(databaseWrapper.getTablePrimaryKeyMaximumValue(tableName) + 1));
                    attributeTextField.setEditable(false);
                }
                GridPane.setConstraints(attributeTextField, 1, currentIndex);
                attributesControls.add(attributeTextField);
                attributesGridPane.getChildren().add(attributeTextField);
            }
            // TO DO: exercise 3

            currentIndex++;
        }
        populateTableView(null);

        if (Constants.DEBUG) {
            System.out.println("finishing initialize " + tableName + "...");
        }
        
        // TO DO: exercise 8

    }
    
    //TODO
    //pe parcurs mai adaug modele aici
    private Model getCurrentEntity(ArrayList<String> values) {
        switch (tableName) {
            case "user":
                return new User(values);

        }
        return null;
    }

    public void populateTableView(String whereClause) {
        try {
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
        	attributes.add("username");
        	attributes.add("password");

            ArrayList<ArrayList<String>> values = DataBaseWrapperImpl.getInstance().getTableContent(tableName,
            		attributes,
                    (whereClause == null || whereClause.isEmpty()) ? null : whereClause,
                    null,
                    null);
            ObservableList<Model> data = FXCollections.observableArrayList();
            for (ArrayList<String> record : values) {
                data.add(getCurrentEntity(record));
            }
            tableContentTableView.setItems(data);
        } catch (SQLException sqlException) {
            System.out.println("An exception had occured: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void tableContentTableViewHandler(MouseEvent mouseEvent) {
        ArrayList<String> tableViewRecord = ((Model) tableContentTableView.getSelectionModel().getSelectedItem()).getValues();
        int currentIndex = 0;
        for (String value : tableViewRecord) {
            if (attributesControls.get(currentIndex) instanceof ComboBox) {
                try {
                    String parentTable = databaseWrapper.getForeignKeyParentTable(tableName, attributesLabels.get(currentIndex).getText());
                    if (parentTable != null) {
                        ArrayList<ArrayList<String>> parentTableReferrence = null;
                        parentTableReferrence = databaseWrapper.getTableContent(parentTable,
                                null,
                                databaseWrapper.getTablePrimaryKey(parentTable) + "=" + tableViewRecord.get(currentIndex),
                                null,
                                null);
                        ((ComboBox<String>) attributesControls.get(currentIndex)).setValue(Utilities.compress(parentTableReferrence.get(0)));
                    }
                } catch (SQLException sqlException) {
                    System.out.println("An exception had occured: " + sqlException.getMessage());
                    if (Constants.DEBUG) {
                        sqlException.printStackTrace();
                    }
                }
            } else {
                ((TextField) attributesControls.get(currentIndex)).setText(value);
            }
            currentIndex++;
        }
    }

    @SuppressWarnings("unchecked")
    private boolean checkAllFieldsCompletion() {
        for (Control attributeControl : attributesControls) {
            String value = null;
            if (attributeControl instanceof ComboBox) {
                value = ((ComboBox<String>) attributeControl).getValue();
            } else {
                value = ((TextField) attributeControl).getText();
            }
            if (value == null || value.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void insertButtonHandler(MouseEvent mouseEvent) {
    	
        if (!checkAllFieldsCompletion()) {
            Dialog dialog = new Dialog();
            dialog.setProperties(Constants.ERROR_WINDOW_TITLE,
                    Constants.ERROR_ICON_LOCATION,
                    Constants.ERROR_MESSAGE_CONTENT);
            dialog.start();
            return;
        }
        int currentIndex = 0;
        ArrayList<String> values = new ArrayList<>();
        for (Control attributeControl : attributesControls) {
            if (attributeControl instanceof ComboBox) {
                try {
                    values.add(databaseWrapper.getForeignKeyValue(tableName,
                            attributesLabels.get(currentIndex).getText(),
                            Utilities.decompress(((ComboBox<String>) attributeControl).getValue())));
                } catch (Exception sqlException) {
                    System.out.println("An exception had occured: " + sqlException.getMessage());
                    if (Constants.DEBUG) {
                        sqlException.printStackTrace();
                    }
                }
            } else {
                values.add(((TextField) attributeControl).getText());
            }
            currentIndex++;
        }
        try {
            databaseWrapper.insertValuesIntoTable(tableName,
                    null,
                    values,
                    false);
        } catch (Exception sqlException) {
            System.out.println("An exception had occured: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        }
        populateTableView(null);
        tableContentTableView.getSelectionModel().select(tableContentTableView.getItems().size() - 1);
        tableContentTableView.scrollTo(tableContentTableView.getItems().size() - 1);
        clearButtonHandler(null);
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void updateButtonHandler(MouseEvent mouseEvent) {
        int currentIndex = 0;
        ArrayList<String> attributes = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        for (Control attributeControl : attributesControls) {
            String value = null;
            if (attributeControl instanceof ComboBox) {
                try {
                    String parentTableValues = ((ComboBox<String>) attributeControl).getValue();
                    if (parentTableValues != null && !parentTableValues.isEmpty()) {
                        value = databaseWrapper.getForeignKeyValue(tableName,
                                attributesLabels.get(currentIndex).getText(),
                                Utilities.decompress(parentTableValues));
                    }
                } catch (Exception sqlException) {
                    System.out.println("An exception had occured: " + sqlException.getMessage());
                    if (Constants.DEBUG) {
                        sqlException.printStackTrace();
                    }
                }
            } else {
                value = ((TextField) attributeControl).getText();
            }
            if (value != null && !value.isEmpty()) {
                attributes.add(attributesLabels.get(currentIndex).getText());
                values.add(value);
            }
            currentIndex++;
        }
        try {
            if (attributes != null && values != null && !attributes.isEmpty() && !values.isEmpty()) {
                databaseWrapper.updateRecordsIntoTable(tableName, attributes, values, null);
            	//databaseWrapper.deleteRecordsFromTable(tableName,
                //        attributes,
                  //      values,
                    //    null);
            }
        } catch (Exception sqlException) {
            System.out.println("An exception had occured: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        }
        populateTableView(null);
        //populateTableView();
        tableContentTableView.getSelectionModel().select(0);
        tableContentTableView.scrollTo(0);
        clearButtonHandler(null);
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void deleteButtonHandler(MouseEvent mouseEvent) {
    	 int currentIndex = 0;
         ArrayList<String> attributes = new ArrayList<>();
         ArrayList<String> values = new ArrayList<>();
         for (Control attributeControl : attributesControls) {
             String value = null;
             if (attributeControl instanceof ComboBox) {
                 try {
                     String parentTableValues = ((ComboBox<String>) attributeControl).getValue();
                     if (parentTableValues != null && !parentTableValues.isEmpty()) {
                         value = databaseWrapper.getForeignKeyValue(tableName,
                                 attributesLabels.get(currentIndex).getText(),
                                 Utilities.decompress(parentTableValues));
                     }
                 } catch (Exception sqlException) {
                     System.out.println("An exception had occured: " + sqlException.getMessage());
                     if (Constants.DEBUG) {
                         sqlException.printStackTrace();
                     }
                 }
             } else {
                 value = ((TextField) attributeControl).getText();
             }
             if (value != null && !value.isEmpty()) {
                 attributes.add(attributesLabels.get(currentIndex).getText());
                 values.add(value);
             }
             currentIndex++;
         }
         try {
             if (attributes != null && values != null && !attributes.isEmpty() && !values.isEmpty()) {
                 databaseWrapper.deleteRecordsFromTable(tableName,
                         attributes,
                         values,
                         null);
             }
         } catch (Exception sqlException) {
             System.out.println("An exception had occured: " + sqlException.getMessage());
             if (Constants.DEBUG) {
                 sqlException.printStackTrace();
             }
         }
         populateTableView(null);
         tableContentTableView.getSelectionModel().select(0);
         tableContentTableView.scrollTo(0);
         clearButtonHandler(null);
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void searchButtonHandler(MouseEvent mouseEvent) {
	   	 int currentIndex = 0;
	     ArrayList<String> attributes = new ArrayList<>();
	     ArrayList<String> values = new ArrayList<>();
	     for (Control attributeControl : attributesControls) {
	         String value = null;
	         if (attributeControl instanceof ComboBox) {
	             try {
	                 String parentTableValues = ((ComboBox<String>) attributeControl).getValue();
	                 if (parentTableValues != null && !parentTableValues.isEmpty()) {
	                     value = databaseWrapper.getForeignKeyValue(tableName,
	                             attributesLabels.get(currentIndex).getText(),
	                             Utilities.decompress(parentTableValues));
	                 }
	             } catch (Exception sqlException) {
	                 System.out.println("An exception had occured: " + sqlException.getMessage());
	                 if (Constants.DEBUG) {
	                     sqlException.printStackTrace();
	                 }
	             }
	         } else {
	             value = ((TextField) attributeControl).getText();
	         }
	         if (value != null && !value.isEmpty()) {
	             attributes.add(attributesLabels.get(currentIndex).getText());
	             values.add(value);
	         }
	         currentIndex++;
	     }
	     try {
	         if (attributes != null && values != null && !attributes.isEmpty() && !values.isEmpty()) {

	         }
	     } catch (Exception sqlException) {
	         System.out.println("An exception had occured: " + sqlException.getMessage());
	         if (Constants.DEBUG) {
	             sqlException.printStackTrace();
	         }
	     }
	     
	     String query = new String("");
	     

	     int k = 0;
	     //if(attributes.get(0).equals("id") && attributes.size() > 1)
	     if(attributes.get(0).equals("id"))
	    	 k = 1;
	     
	     for(int i=k;i<attributes.size();i++){
	    	 
	    	 //if(!values.get(i).matches("[a-zA-Z]+"))
	    		 query += attributes.get(i) + " = \"" + values.get(i) + "\" AND ";
	    	 //else
	    		// query += attributes.get(i) + " = " + values.get(i) + " AND ";
	     }
	     
	     query = (String) query.subSequence(0, query.length()-4);
	     System.out.println("rrrrrrrrrrrrrrrrrrr"+query);
	    	 
	     
	     populateTableView(query);
	     System.out.println("Stuff");
	     tableContentTableView.getSelectionModel().select(0);
	     tableContentTableView.scrollTo(0);
	     //clearButtonHandler(null);
    }
    
    @SuppressWarnings("unchecked")
    @FXML
    private void clearButtonHandler(MouseEvent mouseEvent) {
	   	 
   	 	int currentIndex = 0;
	    ArrayList<String> attributes = new ArrayList<>();
	    ArrayList<String> values = new ArrayList<>();
    	
    	if(mouseEvent != null){

		     populateTableView(null);
		     tableContentTableView.getSelectionModel().select(0);
		     tableContentTableView.scrollTo(0);
		     tableContentTableView.setItems(null);
		     
		     for (Control attributeControl : attributesControls){
		    	 if (attributeControl instanceof ComboBox) 
		    		 ((ComboBox<String>) attributeControl).setValue(null);
		         else
		    		 ((TextField) attributeControl).clear();
		     }
	     //clearButtonHandler(null);
    	}else{
		     for (Control attributeControl : attributesControls){
		    	 if (attributeControl instanceof ComboBox) 
		    		 ((ComboBox<String>) attributeControl).setValue(null);
		         else
		    		 ((TextField) attributeControl).clear();
		     }
    	}
    }

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
