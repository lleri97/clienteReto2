/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio;



import Interfaces.Signable;
import Logic.ClientFactory;

import controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Class that loads the application and the first window.
 * 
 * @author Yeray
 */
public class ClienteEscritorio extends Application {
    /**
     * Method to create the window and start the JavaFX applicaction
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Signable client = ClientFactory.getClient();
     //Load node graph from fxml file
        FXMLLoader loader=new FXMLLoader(
                getClass().getResource("GU01_Login.fxml"));
        Parent root = (Parent)loader.load();
        //Get controller for graph 
        LogInController stageController=
                ((LogInController)loader.getController());
                
        //Set a reference for Stage
        stageController.setStage(stage);
        //Initializes primary stage
       stageController.initStage(root,client);
    }

    /**
     * Main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
 
}
