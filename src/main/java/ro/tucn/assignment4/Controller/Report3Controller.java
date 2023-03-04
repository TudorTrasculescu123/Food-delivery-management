package ro.tucn.assignment4.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.tucn.assignment4.Business.Client;
import ro.tucn.assignment4.Business.DeliveryService;
import ro.tucn.assignment4.Business.MenuItem;
import ro.tucn.assignment4.HelloApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Report3Controller {

    @FXML
    private Button filterButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField numberField;

    @FXML
    private TextField priceField;

    @FXML
    private TableView report3Table;

    public void initialize(){
        List<TableColumn<MenuItem, Integer>> lst = new ArrayList<>();
        lst.add(generateColumn("clientID"));
        lst.add(generateColumn("username"));
        report3Table.getColumns().addAll(lst);
        report3Table.getItems().setAll(DeliveryService.getObject().getClients());
        report3Table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public TableColumn<MenuItem, Integer> generateColumn(String fieldName){
        TableColumn<MenuItem, Integer> column =  new TableColumn<>(fieldName);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        return column;
    }

    public void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 1154, 750);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }

    public void filterPressed(){
        if(checkInput()){
            int price = Integer.parseInt(priceField.getText());
            int numberOfTimes = Integer.parseInt(numberField.getText());
            List<Client> clients = DeliveryService.getObject().filterReport3(price, numberOfTimes);
            report3Table.getItems().clear();
            report3Table.getItems().setAll(clients);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Field should not be empty");
            alert.showAndWait();
        }
    }

    public boolean checkInput(){
        if(numberField.getText() == "" || priceField.getText() == "")
            return false;
        return true;
    }
}
