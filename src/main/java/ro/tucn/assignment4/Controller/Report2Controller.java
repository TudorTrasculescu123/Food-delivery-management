package ro.tucn.assignment4.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.tucn.assignment4.Business.DeliveryService;
import ro.tucn.assignment4.Business.MenuItem;
import ro.tucn.assignment4.HelloApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Report2Controller {

    @FXML
    private Button backButton;

    @FXML
    private TableView report2Table;

    @FXML
    private TextField numberField;

    @FXML
    private Button filterButton;

    public void initialize(){
        List<TableColumn<MenuItem, Integer>> lst = new ArrayList<>();
        lst.add(generateColumn("Title"));
        lst.add(generateColumn("Rating"));
        lst.add(generateColumn("Calories"));
        lst.add(generateColumn("Protein"));
        lst.add(generateColumn("Fat"));
        lst.add(generateColumn("Sodium"));
        lst.add(generateColumn("Price"));
        report2Table.getColumns().addAll(lst);
        DeliveryService deliveryService = DeliveryService.getObject();
        List<MenuItem> menuItems =  deliveryService.getMenuItems();
        if(menuItems != null){
            report2Table.getItems().setAll(menuItems);
            report2Table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
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
            List<MenuItem> updateList= DeliveryService.getObject().filterReport2(Integer.parseInt(numberField.getText()));
            report2Table.getItems().clear();
            report2Table.getItems().setAll(updateList);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Field should not be empty");
            alert.showAndWait();
        }
    }

    public boolean checkInput(){
        if(numberField.getText() == ""){
            return false;
        }
        return true;
    }
}
