package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import controller.Authentication;


public class Main extends Application {

    @Override
    public void start(Stage mainStage) throws Exception {
        new Authentication().start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
