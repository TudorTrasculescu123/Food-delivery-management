package ro.tucn.assignment4.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.tucn.assignment4.Business.Client;
import ro.tucn.assignment4.Business.DeliveryService;
import ro.tucn.assignment4.HelloApplication;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void initialize(){

    }

    public void cancelPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 425, 387);
        ((LoginController)loader.getController()).setMode(1);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public void createPressed() throws IOException {
        if(checkInput()){
            ArrayList<Client>clients = (ArrayList<Client>) DeliveryService.getObject().getClients();
            System.out.println("current username: " + usernameField.getText());
            for(Client client: clients){
                System.out.println(client.getUsername());
                if(client.getUsername().equals(usernameField.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("The username already exists!");
                    alert.showAndWait();
                    return;
                }
            }
            int len = clients.size();
            Client client = new Client(len + 1, usernameField.getText(), passwordField.getText());
            DeliveryService.getObject().getClients().add(client);
            System.out.println("Client with id: " + client.getClientID() + " and username: " +  client.getUsername() + " was successfully added!");
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Some field is empty");
            alert.showAndWait();
            return;
        }
        cancelPressed();
    }

    public boolean checkInput(){
        if(usernameField.getText() == "" || passwordField.getText() == "")
            return false;
        return true;
    }
}
