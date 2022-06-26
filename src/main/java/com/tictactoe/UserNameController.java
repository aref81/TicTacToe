package com.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UserNameController {


    @FXML
    private TextField user;

    @FXML
    void enter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)){
            if (!user.getText().isEmpty()) {
                try {
                    FXMLLoader loader = new FXMLLoader(InitController.class.getResource("Game.fxml"));
                    Parent root = loader.load();
                    GameController gameController = loader.getController();
                    gameController.setUser(user.getText());
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
