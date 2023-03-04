package ro.tucn.assignment4.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.tucn.assignment4.Business.Client;
import ro.tucn.assignment4.Business.DeliveryService;
import ro.tucn.assignment4.HelloApplication;

import java.io.IOException;
import java.util.List;

public class LoginController {

    private int mode;
    private final String EMPLOYEE_ID = "employee";
    private final String EMPLOYEE_PASS = "employee";
    private final String ADMIN_ID = "admin";
    private final String ADMIN_PASS = "admin";
    @FXML
    private Button backButton;

    @FXML
    private Button enterButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label labelText;

    public void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 293, 346);
        stage.setScene(scene);
        stage.setTitle("Login options");
        stage.show();
    }

    public void enterPressed() throws IOException {
        if(check()){
            if(mode == 0){
                // admin view will be popped
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
                Stage stage = (Stage) enterButton.getScene().getWindow();
                Scene scene = new Scene(loader.load(), 1154, 750);
                stage.setScene(scene);
                stage.setTitle("Admin");
                stage.show();
            }else if(mode == 1){
                // client view will be popped
                // I moved the logic inside check, because I needed acces to ClientController
            }else if(mode == 2){
                // employee view will be popped
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("employee-view.fxml"));
                //loader.setController();
                Stage stage = (Stage) enterButton.getScene().getWindow();
                Scene scene = new Scene(loader.load(), 841, 588);
                stage.setScene(scene);
                stage.setTitle("Employee");
                stage.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Wrong ID or password!");
            alert.showAndWait();
        }
    }

    public void registerPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
        Stage stage = (Stage) registerButton.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 363, 335);
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();
    }

    public boolean check() throws IOException {
        if(mode == 0){
            if(!usernameField.getText().equals(ADMIN_ID))
                return false;
            if(!passwordField.getText().equals(ADMIN_PASS))
                return false;
        }else if(mode == 1){
            List<Client> clients = DeliveryService.getObject().getClients();
            for(Client client: clients){
                if(client.getUsername().equals(usernameField.getText()) && client.getPassword().equals(passwordField.getText())){
                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("client-view.fxml"));
                    Stage stage = (Stage) enterButton.getScene().getWindow();
                    Scene scene = new Scene(loader.load(), 1035, 730);
                    ((ClientController)loader.getController()).setClient(client);
                    stage.setScene(scene);
                    stage.setTitle("Client");
                    stage.show();
                    return true;
                }
            }
            return false;
        }else{
            if(!usernameField.getText().equals(EMPLOYEE_ID))
                return false;
            if(!passwordField.getText().equals(EMPLOYEE_PASS))
                return false;
        }
        return true;
    }

    public void setMode(int mode){
        this.mode = mode;
        if(mode != 1){
            registerButton.setVisible(false);
            labelText.setVisible(false);
        }else{
            registerButton.setVisible(true);
            labelText.setVisible(true);
        }
    }

}
