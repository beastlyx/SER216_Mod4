package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author borysb
 */
public class Connect4GUI extends Application {
    
    public void start(Stage primaryStage) {

        StackPane pane = new StackPane();  
        Scene scene = new Scene(pane, 400, 300);
        primaryStage.setTitle("Connect 4");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // make a red square
        // make 6x7 (42) white circles where 6 rows and 7 columns
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
