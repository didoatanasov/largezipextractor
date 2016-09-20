package com.omisoft.zipextractor;/**
 * Created by dido on 9/19/16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Zip extractor GUI
 *
 */
public class OmisoftZipExtractor extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage primaryStage) {
        VBox page = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("extractor.fxml"));
            page = (VBox) loader.load();
            ExtController controller = (ExtController)loader.getController();
            controller.setStageAndSetupListeners(primaryStage); // or what you want to do
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zip Extractor");
        primaryStage.show();
    }
}
