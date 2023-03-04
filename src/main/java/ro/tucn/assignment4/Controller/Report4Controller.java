package ro.tucn.assignment4.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.tucn.assignment4.Business.DeliveryService;
import ro.tucn.assignment4.Business.MenuItem;
import ro.tucn.assignment4.Business.Order;
import ro.tucn.assignment4.Business.Report4Product;
import ro.tucn.assignment4.HelloApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Report4Controller {
    @FXML
    private Button backButton;

    @FXML
    private Button filterButton;

    @FXML
    private TableView report4Table;

    @FXML
    private TextField dayField;

    @FXML
    private TextField monthField;

    public void initialize(){
        List<TableColumn<MenuItem, Integer>> lst = new ArrayList<>();
        lst.add(generateColumn("product"));
        lst.add(generateColumn("numberOfOrders"));
        report4Table.getColumns().addAll(lst);
        report4Table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public TableColumn<MenuItem, Integer> generateColumn(String fieldName){
        TableColumn<MenuItem, Integer> column =  new TableColumn<>(fieldName);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        return column;
    }

    public boolean checkInput(){
        if(dayField.getText() == "" && monthField.getText() == "")
            return false;
        return true;
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
            HashMap<MenuItem, Integer> map = DeliveryService.getObject().filterReport4(Integer.parseInt(dayField.getText()), Integer.parseInt(monthField.getText()));
            List<Report4Product> population = new ArrayList<>();
            for(MenuItem menuItem: map.keySet()){
                Report4Product obj = new Report4Product(menuItem.getTitle(), map.get(menuItem));
                population.add(obj);
            }
            report4Table.getItems().setAll(population);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Field should not be empty");
            alert.showAndWait();
        }
    }

}
