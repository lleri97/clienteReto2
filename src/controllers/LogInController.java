/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Interfaces.Signable;
import Models.User;
import exceptions.LoginErrorException;
import exceptions.PasswErrorException;
import exceptions.ServerErrorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;

/**
 * LoginController Class
 *
 * @author Fran y Andoni
 */
public class LogInController {

    @FXML
    private Button btnLogIn;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private CheckBox checkBoxRemeberMe;
    @FXML
    private Button btnHelpLogIn;
    @FXML
    private ImageView userImg;
    @FXML
    private ImageView passImg;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Hyperlink hplRecoverPassword;

    private Signable client;

    private Preferences pref;

    private User usu;

    private Stage stage;

    private static final Logger LOGGER = Logger.getLogger(LogInController.class.getPackage() + "." + LogInController.class.getName());

    private Stage getStage() {
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
     * Method to initialize JavaFX windows
     *
     * @param root Parent will be used
     * @param client Client will be used
     */
    public void initStage(Parent root, Signable client) {
        Scene scene2 = new Scene(root);
        this.client = client;
        //The window starts
        stage = new Stage();

        // Keyboard shortcuts
        KeyCombination LogIn = new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN);

        KeyCombination Check = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN);
        KeyCombination Help = new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN);
        Mnemonic login = new Mnemonic(btnLogIn, LogIn);

        Mnemonic check = new Mnemonic(checkBoxRemeberMe, Check);
        Mnemonic help = new Mnemonic(btnHelpLogIn, Help);
        scene2.addMnemonic(login);
        scene2.addMnemonic(check);

        scene2.addMnemonic(help);

        stage.setScene(scene2);
        stage.setResizable(false);
        userImg.setImage(new javafx.scene.image.Image("res/icon_usuario.png"));
        passImg.setImage(new javafx.scene.image.Image("res/icon_candado.png"));

        //controlling preferences node in Prefs
        pref = Preferences.userNodeForPackage(LogInController.class);
        textFieldUsername.setText(pref.get("login", ""));
        textFieldPassword.setText(pref.get("password", ""));
        if (textFieldUsername.getText().compareTo("") != 0 && textFieldPassword.getText().compareTo("") != 0) {
            checkBoxRemeberMe.setSelected(true);
        }

        // Tooltips config
        Tooltip usernameTT = new Tooltip("INSERT your user LOGIN");
        textFieldUsername.setTooltip(usernameTT);
        Tooltip passwdTT = new Tooltip("INSERT your user PASSWORD");
        textFieldPassword.setTooltip(passwdTT);

        Tooltip loginTT = new Tooltip("CLICK here for LOGIN");
        btnLogIn.setTooltip(loginTT);

        // Buttons
        btnHelpLogIn.setOnAction(this::handleButtonAction);
        btnLogIn.setOnAction(this::handleButtonAction);
        hplRecoverPassword.setOnAction(this::eventHandler);

        stage.setTitle("Login");
        stage.show();
        LOGGER.info("Window loaded correctly.");
    }

    /**
     * Method to control buttons events
     *
     * @param event
     */
    public void handleButtonAction(ActionEvent event) {

        try {

            Parent root = null;
            FXMLLoader loader = null;

            if ((Button) event.getSource() == btnLogIn) {// Login Button Settings
                progressBar.setVisible(true);
                progressBar.setProgress(0.25);
                //It is verified that the fields cannot be empty
                if (textFieldPassword.getText().equals("") || textFieldUsername.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("Uncompleted data");
                    Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setId("okbutton");
                    alert.showAndWait();
                    progressBar.setVisible(false);
                } else {// The user is built
                    usu = new User();
                    usu.setLogin(textFieldUsername.getText());
                    usu.setPassword(textFieldPassword.getText());

                    // Settings to remember credentials
                  
                    progressBar.setProgress(0.5);
                 //   usu = client.login(usu);
                    progressBar.setProgress(1);

                    //If the credentials are correct, if not go to exceptions
                    LOGGER.info("Login made successfully. Loading user profile.");
                    LogOutController controller = new LogOutController();
                    loader = new FXMLLoader(getClass().getResource("/clienteescritorio/GU03_LogOut.fxml"));
                    root = (Parent) loader.load();
                    controller = ((LogOutController) loader.getController());
                    controller.initStage(root, client, usu);
                    progressBar.setVisible(false);

                }

            } else if ((Button) event.getSource() == btnHelpLogIn) {// Help button configuration
                LOGGER.info("Loading help window.");
                LoginHelpController controller = new LoginHelpController();
                loader = new FXMLLoader(getClass().getResource("/clienteescritorio/HelpLogin.fxml"));
                root = (Parent) loader.load();
                controller = ((LoginHelpController) loader.getController());
                controller.initStage(root);
            }

/*        } catch (LoginErrorException ex) {
            LOGGER.warning(ex.getMessage());
            //if the login does not exist
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The login does not exist.");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
            progressBar.setVisible(false);
        } catch (PasswErrorException ex) {
            LOGGER.warning(ex.getMessage());
            //if there is an error entering the password
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Incorrect password.");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
            progressBar.setVisible(false);
        } catch (ServerErrorException ex) {
            LOGGER.warning(ex.getMessage());
            //Server Error 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Server Error.");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
            progressBar.setVisible(false);
*/
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }

    }

    public void eventHandler(ActionEvent ev) {
        try {
            LOGGER.info("Loading Recover Password window.");
            RecoverPasswordController controller = new RecoverPasswordController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clienteescritorio/GU_RecoverPassword.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((RecoverPasswordController) loader.getController());
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
