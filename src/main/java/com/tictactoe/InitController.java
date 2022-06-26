package com.tictactoe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InitController {
    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void start(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(InitController.class.getResource("UserName.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
