package ro.tucn.assignment4.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.tucn.assignment4.Business.BaseProduct;
import ro.tucn.assignment4.Business.CompositeProduct;
import ro.tucn.assignment4.Business.DeliveryService;
import ro.tucn.assignment4.Business.MenuItem;
import ro.tucn.assignment4.HelloApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    @FXML
    private Button backButton;

    @FXML
    private TableView productsTable;

    @FXML
    private TextField addCompositeField;

    @FXML
    private Button rep1Button;

    @FXML
    private Button rep2Button;

    @FXML
    private Button rep3Button;

    @FXML
    private Button rep4Button;

    public void initialize(){
        productsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<TableColumn<MenuItem, Integer>> lst = new ArrayList<>();
        lst.add(generateColumn("Title"));
        lst.add(generateColumn("Rating"));
        lst.add(generateColumn("Calories"));
        lst.add(generateColumn("Protein"));
        lst.add(generateColumn("Fat"));
        lst.add(generateColumn("Sodium"));
        lst.add(generateColumn("Price"));
        productsTable.getColumns().addAll(lst);
        DeliveryService deliveryService = DeliveryService.getObject();
        List<MenuItem> menuItems =  deliveryService.getMenuItems();
        if(menuItems != null){
            productsTable.getItems().setAll(menuItems);
            productsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
    }

    public void loadPressed(){
        DeliveryService deliveryService = DeliveryService.getObject();
        deliveryService.populateWIthData("products.csv");
        List<MenuItem> menuItems =  deliveryService.getMenuItems();
        productsTable.getItems().setAll(menuItems);
        productsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public TableColumn<MenuItem, Integer> generateColumn(String fieldName){
        TableColumn<MenuItem, Integer> column =  new TableColumn<>(fieldName);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        return column;
    }

    public void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 293, 346);
        stage.setScene(scene);
        stage.setTitle("Login options");
        stage.show();
    }

    public void addPressed() throws IOException {
        handleProduct(0);
    }

    public void editPressed() throws IOException {
        handleProduct(1);
    }

    public void handleProduct(int mode) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("modify-product.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        if(productsTable.getSelectionModel().getSelectedItem() instanceof  CompositeProduct){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("You can not edit a composite product");
            alert.showAndWait();
            return;
        }
        BaseProduct product = (BaseProduct) productsTable.getSelectionModel().getSelectedItem();
        Scene scene = new Scene(loader.load(), 499, 537);
        ((ModifyProductController) loader.getController()).setMode(mode);
        if(mode == 1){
            if(product != null){
                ((ModifyProductController) loader.getController()).setData(product);
                ((ModifyProductController) loader.getController()).setProduct(product);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Please select a row that you want to edit");
                alert.showAndWait();
                return;
            }
        }
        stage.setScene(scene);
        if(mode == 0){
            stage.setTitle("Add product");
        }else{
            stage.setTitle("Edit product");
        }
        stage.show();
    }


    public void removePressed(){
        MenuItem selectedItem = (MenuItem) productsTable.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Please select a row that you want to edit");
            alert.showAndWait();
        }else {
            DeliveryService.getObject().getMenuItems().remove(selectedItem);
            productsTable.getItems().clear();
            productsTable.getColumns().clear();
            initialize();
        }
    }

    public void addCompositePressed(){
        if(addCompositeField.getText() == ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Please enter a title for the composite product");
            alert.showAndWait();
            return;
        }
        ArrayList<MenuItem> menuItems = new ArrayList<>(productsTable.getSelectionModel().getSelectedItems());
        if(menuItems == null || menuItems.size() <= 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Please select at least 2 rows in order to make a composite");
            alert.showAndWait();
            return;
        }

        CompositeProduct compositeProduct = new CompositeProduct(addCompositeField.getText());
        compositeProduct.setListOfBaseProducts(menuItems);
        DeliveryService.getObject().getMenuItems().add(0, compositeProduct);
        productsTable.getColumns().clear();
        productsTable.getItems().clear();
        initialize();
    }

    public void rep1Pressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("report1-view.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 792, 554);
        stage.setScene(scene);
        stage.setTitle("Report 1");
        stage.show();
    }

    public void rep2Pressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("report2-view.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 951, 583);
        stage.setScene(scene);
        stage.setTitle("Report 2");
        stage.show();
    }

    public void rep3Pressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("report3-view.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 652, 562);
        stage.setScene(scene);
        stage.setTitle("Report 3");
        stage.show();
    }

    public void rep4Pressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("report4-view.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(loader.load(), 592, 450);
        stage.setScene(scene);
        stage.setTitle("Report 4");
        stage.show();
    }


}
