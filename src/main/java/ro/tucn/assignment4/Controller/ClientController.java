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
import ro.tucn.assignment4.Business.Order;
import ro.tucn.assignment4.HelloApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    @FXML
    private Button backButton;

    @FXML
    private Button orderButton;

    @FXML
    private TextField searchField;

    @FXML
    private TextField proteinMin;

    @FXML
    private TextField proteinMax;

    @FXML
    private TextField priceMin;

    @FXML
    private TextField priceMax;

    @FXML
    private TextField sodiumMin;

    @FXML
    private TextField sodiumMax;

    @FXML
    private TextField ratingMin;

    @FXML
    private TextField ratingMax;

    @FXML
    private TextField caloriesMin;

    @FXML
    private TextField caloriesMax;

    @FXML
    private TextField fatMin;

    @FXML
    private TextField fatMax;

    @FXML
    private TableView clientTable;

    @FXML
    private Button filterButton;

    private Client client;

    public void initialize(){
        DeliveryService deliveryService = DeliveryService.getObject();
        List<MenuItem> menuItems =  deliveryService.getMenuItems();
        populateTable(menuItems);
    }

    public void populateTable(List<MenuItem> menuItems){
        clientTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<TableColumn<MenuItem, Integer>> lst = new ArrayList<>();
        lst.add(generateColumn("Title"));
        lst.add(generateColumn("Rating"));
        lst.add(generateColumn("Calories"));
        lst.add(generateColumn("Protein"));
        lst.add(generateColumn("Fat"));
        lst.add(generateColumn("Sodium"));
        lst.add(generateColumn("Price"));
        clientTable.getColumns().addAll(lst);
        if(menuItems != null){
            clientTable.getItems().setAll(menuItems);
            clientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
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

    public void orderPressed(){
        List<MenuItem> itemsOrdered = new ArrayList<>(clientTable.getSelectionModel().getSelectedItems());
        if(itemsOrdered == null || itemsOrdered.size() < 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Please select the items which you want to order!");
            alert.showAndWait();
            return;
        }
        DeliveryService deliveryService = DeliveryService.getObject();
        int orderID = deliveryService.getOrdersMapped().size();
        Order order = new Order(orderID + 1, this.client.getClientID());
        deliveryService.makeOrder(order, (ArrayList<MenuItem>) itemsOrdered);
        int price = 0;
        List<String> lst = new ArrayList<>();
        for(MenuItem menuItem: itemsOrdered){
            price += menuItem.computePrice();
            lst.add(menuItem.getTitle());
        }
        generateBill(orderID, client.getUsername(), price, lst);
    }

    public void generateBill(int orderID, String clientName, int price, List<String> products){
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("bill.txt");
            myWriter.write("Order " + orderID + ": The client: " + clientName + " ordered: " + products + ", for a total price of: " + price);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filterPressed(){
        DeliveryService deliveryService = DeliveryService.getObject();
        List<MenuItem> menuItems = deliveryService.filterClient(
                searchField.getText(), returnFloatMintValue(ratingMin.getText()), returnFloatMaxtValue(ratingMax.getText()),
                returnIntMinValue(caloriesMin.getText()), returnIntMaxValue(caloriesMax.getText()), returnIntMinValue(fatMin.getText()),
                returnIntMaxValue(fatMax.getText()), returnIntMinValue(proteinMin.getText()), returnIntMaxValue(proteinMax.getText()),
                returnIntMinValue(priceMin.getText()), returnIntMaxValue(priceMax.getText()), returnIntMinValue(sodiumMin.getText()),
                returnIntMaxValue(sodiumMax.getText()));
        clientTable.getColumns().clear();
        clientTable.getItems().clear();
        populateTable(menuItems);
    }

    public int returnIntMaxValue(String s){
        if(s != "")
            return Integer.parseInt(s);
        return 2000000000;
    }

    public int returnIntMinValue(String s){
        if(s != "")
            return Integer.parseInt(s);
        return 0;
    }

    public float returnFloatMintValue(String s){
        if(s != "")
            return Float.parseFloat(s);
        return 0;
    }

    public float returnFloatMaxtValue(String s){
        if(s != "")
            return Float.parseFloat(s);
        return 2000000000;
    }

    public void setClient(Client client){
        this.client = client;
    }
}
