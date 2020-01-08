/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * HelpLogin Window Controller
 *
 * @author Andoni
 */
public class LoginHelpController {

    @FXML
    private WebView wViewLogin;
    private static final Logger LOGGER = Logger.getLogger(LoginHelpController.class.getPackage() + "." + LoginHelpController.class.getName());
    

    /**
     * Method to initialize JavaFX windows
     * 
     * @param root Parent will be used
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        
        //The window starts
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Login mnemonics help");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
        LOGGER.info("Window loaded correctly.");
    }

    private void handleWindowShowing(WindowEvent event) {
        WebEngine webEngine = wViewLogin.getEngine();
        //Load help page.
        webEngine.load(getClass()
                .getResource("/helpMenu/HelpLogin.html").toExternalForm());
    }

}
