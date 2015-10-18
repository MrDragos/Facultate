package controller;

import general.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dialog {

    private Stage applicationStage;
    private Scene applicationScene;

    private static String windowTitle;
    private static String iconLocation;
    private static String messageContent;

    @FXML
    private ImageView iconImageView;
    @FXML
    private Label messageLabel;

    public String fxmlName;
    
    public Dialog(){
    	this.fxmlName = Constants.DIALOG_FXML;
    }
    
    public Dialog(String fxmlFileName){
    	this.fxmlName = fxmlFileName;
    }
    
    public void setWindowTitle(String windowTitle) {
        Dialog.windowTitle = windowTitle;
    }

    public void setIconLocation(String iconLocation) {
        Dialog.iconLocation = iconLocation;
    }

    public void setMessageContent(String messageContent) {
        Dialog.messageContent = messageContent;
    }

    public void setProperties(String windowsTitle, String iconLocation, String messageContent) {
        Dialog.windowTitle = windowsTitle;
        Dialog.iconLocation = iconLocation;
        Dialog.messageContent = messageContent;
    }

    public void start() {

        try {
            applicationScene = new Scene((Parent) FXMLLoader.load(getClass().getResource(fxmlName)));
        } catch (Exception exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }

        applicationStage = new Stage();
        applicationStage.setTitle(windowTitle);
        applicationStage.getIcons().add(new Image(getClass().getResource(Constants.ICON_FILE_NAME).toExternalForm()));
        applicationStage.setScene(applicationScene);
        applicationStage.initModality(Modality.APPLICATION_MODAL);
        applicationStage.show();
    }

    @FXML
    private void initialize() {
        iconImageView.setImage(new Image(getClass().getResource(Dialog.iconLocation).toExternalForm()));
        messageLabel.setText(Dialog.messageContent);
    }

    @FXML
    protected void okButtonHandler(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
