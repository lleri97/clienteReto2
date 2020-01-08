/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Interfaces.Signable;
import Models.User;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * LogOutController Class
 *
 * @author Ruben
 */
public class LogOutController {

    @FXML
    private Button btnCloseConnection;
    @FXML
    private Button btnExit;
    @FXML
    private Label nombreUsu;
    @FXML
    private Label textLogin;
    @FXML
    private Label textFullName;
    @FXML
    private Label textPrivilege;
    @FXML
    private Label textEntity;
    @FXML
    private MenuItem closeSessionItem;
    @FXML
    private MenuItem exitItem;
    @FXML
    private TabPane tabs;
    @FXML
    private Tab tabUsers;
    @FXML
    private Tab tabDocuments;
    @FXML
    private Tab tabEntity;
    @FXML
    private Tab tabDepartment;
     @FXML
    private Tab tabAreas;

    private static final Logger LOGGER = Logger.getLogger(LogOutController.class.getPackage() + "." + LogOutController.class.getName());

    Stage stage;

    private Signable client;

    /**
     * Get a stage attribute
     *
     * @return stage data
     */
    public Stage getStage() {

        return stage;
    }

    /**
     * Set a Stage in stage attribute
     *
     * @param stage The stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method to initialize JavaFX window
     *
     * @param root Parent will be used
     * @param client Client will be used
     * @param user User that contains the profile data
     */
    public void initStage(Parent root, Signable client, User user) {
        Scene scene = new Scene(root);
        this.client = client;

        //The window starts
        stage = new Stage();
       
        // Keyboard shortcuts
        KeyCombination CloseSession = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN);
        KeyCombination Exit = new KeyCodeCombination(KeyCode.X, KeyCombination.ALT_DOWN);
        Mnemonic closeSession = new Mnemonic(btnCloseConnection, CloseSession);
        Mnemonic exit = new Mnemonic(btnExit, Exit);
        scene.addMnemonic(exit);
        scene.addMnemonic(closeSession);
       
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("LogOut");
        stage.setResizable(false);
        nombreUsu.setText("Hello " + user.getFullname());
        stage.show();

      
       

        LOGGER.info("Profile loaded successfully.");

        //Menu events
        closeSessionItem.setOnAction((event) -> {
            LOGGER.info("Closing user profile.");
            stage.close();
        });

        exitItem.setOnAction((event) -> {
            LOGGER.info("Closing application");
            Platform.exit();
        });
    
    }

 

}
