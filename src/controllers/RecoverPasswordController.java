/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Yeray
 */
public class RecoverPasswordController {

    @FXML
    private TextField TextFieldEmail;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSend;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Recover Password");
        stage.show();

        btnSend.setOnAction(this::handleButtonAction);
        btnBack.setOnAction(this::handleButtonAction);

    }

    public void handleButtonAction(ActionEvent event) {
        if ((Button) event.getSource() == btnBack) {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();
        }else if((Button)event.getSource()== btnSend){
            /**
             * Aqui ira el codigo para comprobar el email y mandar la contraseña
             */
        }
    }
}
