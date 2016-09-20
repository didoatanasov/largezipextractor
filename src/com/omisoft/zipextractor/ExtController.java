package com.omisoft.zipextractor;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by dido on 9/19/16.
 */
public class ExtController {

    @FXML
    private TextField fileToExtract;
    final DirectoryChooser directoryChooser = new DirectoryChooser();
    final FileChooser fileChooser = new FileChooser();

    private Stage stage;

    @FXML
    private TextField directoryToExtractTo;
    @FXML
    private void chooseDirectoryAction(ActionEvent e) {
        File directory =directoryChooser.showDialog(stage);

        if (directory != null) {
            directoryToExtractTo.setText(directory.getAbsolutePath());
        }
    }

    public void setStageAndSetupListeners(Stage stage) {
        this.stage = stage;
    }

    public void chooseZipFile(ActionEvent actionEvent) {
        fileChooser.setTitle("Choose zip file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ZIP files (*.zip)", "*.zip");

        fileChooser.getExtensionFilters().add(extFilter);
        File file =fileChooser.showOpenDialog(stage);

        if (fileToExtract != null) {
            fileToExtract.setText(file.getAbsolutePath());
        }
    }

    public void extractFiles(ActionEvent actionEvent) {
        Task<Boolean> task=new Task<Boolean>() {
            @Override protected Boolean call() throws Exception {
                boolean result =  CliExtractror.extractZip(fileToExtract.getText(),directoryToExtractTo.getText());

                return result;
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override public void handle(WorkerStateEvent workerStateEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Extraction complete");
                alert.setHeaderText(null);
                alert.setContentText("Completed extracting files");

                alert.showAndWait();

            }
        });
    new Thread(task).start();

    }
}
