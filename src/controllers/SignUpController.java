/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Interfaces.Signable;
import Models.User;
import exceptions.ServerErrorException;
import exceptions.SupEmailErrorException;
import exceptions.SupLogErrorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.Mnemonic;
import javafx.stage.Modality;
import javafx.stage.Stage;
import validators.Validators;

/**
 * SignUpController Class
 *
 * @author Yeray
 */
public class SignUpController {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnHelpSignUp;
    @FXML
    private TextField textLogin;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textFullName;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textConfirmPassword;
    @FXML
    private ImageView regImg;
    @FXML
    private ProgressBar progressBar;

    private boolean email = false, passwd = false;

    private static final Logger LOGGER = Logger.getLogger("controllers.SignUpController");

    private Signable client;

    /**
     * Method to initialize JavaFX windows
     *
     * @param root Parent will be used
     * @param client Client will be used
     */
    public void initStage(Parent root, Signable client) {
        this.client = client;
        Scene scene = new Scene(root);

        //The window starts
        Stage stage = new Stage();

        // Keyboard shortcuts
        KeyCombination SignUp = new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN);
        KeyCombination Cancel = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN);
        Mnemonic signUp = new Mnemonic(btnSignUp, SignUp);
        Mnemonic cancel = new Mnemonic(btnCancel, Cancel);
        //Modificaciones din 13/11/2019
        Runnable rnSignUp = () -> {
            LOGGER.info("Acelerator fot SignUp");
            signUp();
        };
        //Modificaciones din 13/11/2019
        Runnable rnCancel = () -> {
            LOGGER.info("Acelerator to close the window");
            closeWindow();
        };
        scene.getAccelerators().put(Cancel, rnCancel);
        scene.getAccelerators().put(SignUp, rnSignUp);

        scene.addMnemonic(cancel);
        scene.addMnemonic(signUp);
        btnSignUp.setDisable(true);
        regImg.setImage(new javafx.scene.image.Image("res/register.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);

        // Tooltips config
        Tooltip backTT = new Tooltip("CANCEL SigningUp");
        btnCancel.setTooltip(backTT);
        Tooltip createTT = new Tooltip("CLICK here for SigningUp");
        btnSignUp.setTooltip(createTT);
        Tooltip loginTT = new Tooltip("Max 40 characters");
        textLogin.setTooltip(loginTT);
        Tooltip nameTT = new Tooltip("Max 40 characters");
        textFullName.setTooltip(nameTT);
        Tooltip emailTT = new Tooltip("Max 40 characters. Format: example@example.exp ");
        textEmail.setTooltip(emailTT);
        Tooltip passTT = new Tooltip("Max 40 characteres in password.Must have\n an uppercase, lowercase and numbers,\n minimum 8 characters");
        textPassword.setTooltip(passTT);
        Tooltip rPassTT = new Tooltip("Max 40 characters. Must match with Password field");
        textConfirmPassword.setTooltip(rPassTT);

        // Buttons
        btnCancel.setOnAction(this::handleButtonAction);
        btnHelpSignUp.setOnAction(this::handleButtonAction);
        btnSignUp.setOnAction(this::handleButtonAction);
        textEmail.textProperty().addListener(this::handleTextChanged);
        textFullName.textProperty().addListener(this::handleTextChanged);
        textLogin.textProperty().addListener(this::handleTextChanged);
        textPassword.textProperty().addListener(this::handleTextChanged);
        //Modificaciones din 13/11/2019
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (evt) -> {
            if (evt.getCode() == KeyCode.F1) {
                LOGGER.info("Pressed F1 loading help window");
                chargeHelp();
            }
        });

        stage.setTitle("SignUp");
        stage.show();
        LOGGER.info("Window loaded correctly.");
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void handleTextChanged(ObservableValue observable, String oldValue, String newValue) {
        // Field validators for the introduction of a new user
        if (textLogin.getText().trim().length() > 40) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Texts cant be longer than 40 characteres\n in login");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
            btnSignUp.setDisable(true);
        } else if (textEmail.getText().trim().length() > 40) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Texts cant be longer than 40 characteres\n in email");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
        } else if (textFullName.getText().trim().length() > 40) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Texts cant be longer than 40 characteres\n in full name");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
        } else if (textPassword.getText().trim().length() > 40) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Texts cant be longer than 40 characteres\n in password. ");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
        } else if (textLogin.getText().trim().length() >= 1 && textEmail.getText().trim().length() >= 1 && textFullName.getText().trim().length() >= 1 && textPassword.getText().trim().length() >= 1) {
            btnSignUp.setDisable(false);// Create user button is enabled
        } else {
            btnSignUp.setDisable(true);// Create user button is disabled
        }
    }

    /**
     * Method to control button events
     *
     * @param event
     */
    public void handleButtonAction(ActionEvent event) {
        if ((Button) event.getSource() == btnCancel) {
            //Closes the Stage
            closeWindow();
        } else if ((Button) event.getSource() == btnHelpSignUp) {
            chargeHelp();

        } else if ((Button) event.getSource() == btnSignUp) {
            LOGGER.info("Launching user creation operation");
            progressBar.setVisible(true);
            progressBar.setProgress(0.25);
            /**
             * Send information to the database
             */
            if (!textConfirmPassword.getText().equals(textPassword.getText())) {
                //If the passwords doesnt match an Error alert appears
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setId("okbutton");
                alert.setContentText("The password doesnt match");
                alert.showAndWait();
                progressBar.setVisible(false);
            } else {
                signUp();
            }

        }

    }

    /**
     * Method to load the help window
     */
    //Modificaciones din 13/11/2019
    public void chargeHelp() {

        try {
            SignUpHelpController controller = new SignUpHelpController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clienteescritorio/HelpSignUp.fxml"));
            Parent root = (Parent) loader.load();
            controller = ((SignUpHelpController) loader.getController());
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to close the window
     */
    //Modificaciones din 13/11/2019

    public void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * method to validate the email, password formats and send the information
     * to the server to signUp
     */
    //Modificaciones din 13/11/2019
    public void signUp() {
        try {
            email = Validators.emailChecker(textEmail.getText());
            passwd = Validators.passwordChecker(textPassword.getText());
            if (email) {//Email pattern validator is correct
                if (passwd) {
                    //if the passwords matches
                    User usu = new User();
                    usu.setLogin(textLogin.getText());
                    usu.setEmail(textEmail.getText());
                    usu.setFullname(textFullName.getText());
                    usu.setPassword(textPassword.getText());
                    progressBar.setProgress(0.75);
                    usu = client.signUp(usu);
                    progressBar.setProgress(1);
                    LOGGER.info("Sign Up made successfully. Loading user profile.");

                    // An alert is issued giving a message that the operation has been a success
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Sign up");
                    alert.setContentText("Signed up correctly");
                    Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setId("okbutton");

                    // The LogOut window with the user profile is launched
                    alert.showAndWait();
                    Stage stage = (Stage) btnCancel.getScene().getWindow();
                    stage.close();
                    LogOutController controller = new LogOutController();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/clienteescritorio/GU03_LogOut.fxml"));
                    Parent root;
                    try {
                        root = (Parent) loader.load();
                        controller = ((LogOutController) loader.getController());
                        controller.initStage(root, client, usu);
                        progressBar.setVisible(false);
                    } catch (IOException ex) {
                        LOGGER.warning(ex.getMessage());
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Password Format Error.");
                    LOGGER.info("Password Format Error.");
                    Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setId("okbutton");
                    alert.showAndWait();
                    progressBar.setVisible(false);
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Email Format Error  ");
                LOGGER.info("Email Format Error ");
                Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setId("okbutton");
                alert.showAndWait();
                progressBar.setVisible(false);
            }

        } catch (SupLogErrorException ex) {
            LOGGER.warning(ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Login is already used.");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("loginAlertButton");
            alert.showAndWait();
            progressBar.setVisible(false);
        } catch (SupEmailErrorException ex) {
            //Modificacion din 13/11/2019
            LOGGER.warning(ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Email is already used.");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            progressBar.setVisible(false);
            alert.showAndWait();
        } catch (ServerErrorException ex) {
            LOGGER.warning(ex.getMessage());
            //Server Error 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Server Error.");
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setId("okbutton");
            alert.showAndWait();
            progressBar.setVisible(false);
        }
    }

}
