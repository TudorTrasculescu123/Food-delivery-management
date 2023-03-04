package ro.tucn.assignment4.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ro.tucn.assignment4.Controller.LoginController;
import ro.tucn.assignment4.HelloApplication;

import java.io.IOException;

public class MainController {

    @FXML
    private Button adminButton;

    @FXML
    private Button clientButton;

    @FXML
    private Button employeeButton;

    public void adminClicked() throws IOException {
        System.out.println("admin");
        handleAction(0, adminButton);
    }

    public void clientClicked() throws IOException {
        System.out.println("client");
        handleAction(1, clientButton);
    }

    public void employeeClicked() throws IOException {
        System.out.println("employee");
        handleAction(2, employeeButton);
    }

    public void handleAction(int mode, Button buttonPressed) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Stage stage = (Stage) buttonPressed.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 425, 387);
        ((LoginController)loader.getController()).setMode(mode);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

}