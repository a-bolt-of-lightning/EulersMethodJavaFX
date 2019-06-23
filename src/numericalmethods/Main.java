package numericalmethods;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Driver class used in the implementation of Euler's method
 * for visualizing first order differential equations
 * @author F Mahvari
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        EulerVisualization graph = new EulerVisualization(0, 0);

        Scene scene =  new Scene(graph, 1000, 650);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Euler's Method");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
