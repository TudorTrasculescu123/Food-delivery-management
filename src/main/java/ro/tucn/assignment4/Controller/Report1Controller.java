package ro.tucn.assignment4.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.tucn.assignment4.Business.*;
import ro.tucn.assignment4.Business.MenuItem;
import ro.tucn.assignment4.HelloApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Report1Controller {

    @FXML
    private Button backButton;

    @FXML
    private Button filterButton;

    @FXML
    private TextField startField;

    @FXML
    private TextField endField;

    @FXML
    private TableView report1Table;

    public void initialize(){
        List<TableColumn<EmployeePopulation, Integer>> lst = new ArrayList<>();
        report1Table.setColumnResizePolicy((param) -> true);
        lst.add(generateColumn("clientName"));
        lst.add(generateColumn("date"));
        lst.add(generateColumn("price"));
        lst.add(generateColumn("products"));
        report1Table.getColumns().addAll(lst);
        List<EmployeePopulation> population = populateTable();
        report1Table.getItems().setAll(population);
    }

    public TableColumn<EmployeePopulation, Integer> generateColumn(String fieldName){
        TableColumn<EmployeePopulation, Integer> column =  new TableColumn<>(fieldName);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        return column;
    }

    public List<EmployeePopulation> populateTable(){
        DeliveryService deliveryService = DeliveryService.getObject();
        HashMap<Order, ArrayList<MenuItem>> ordersMapped = deliveryService.getOrdersMapped();
        String clientName;
        Date date;
        int price;
        ArrayList<MenuItem> products;
        List<String> productNames;
        List<EmployeePopulation> lst = new ArrayList<>();
        for(Order order: ordersMapped.keySet()){
            clientName = findClientByID(order.getClientID());
            date = order.getOrderDate();
            products = ordersMapped.get(order);
            price = computePrice(products);
            EmployeePopulation employee = new EmployeePopulation(clientName, date, price);
            productNames = getProductNames(products);
            employee.setProducts((ArrayList<String>) productNames);
            lst.add(employee);
        }
        return lst;
    }

    public String findClientByID(int id){
        DeliveryService deliveryService = DeliveryService.getObject();
        ArrayList<Client> clients = (ArrayList<Client>) deliveryService.getClients();
        for(Client client: clients){
            if(client.getClientID() == id){
                return client.getUsername();
            }
        }
        System.out.println("Nu s-a gasit clientul, ceva e gresit in Report1");
        return null;
    }

    public List<String> getProductNames(ArrayList<MenuItem> menuItems){
        List<String> productNames = new ArrayList<>();
        for(MenuItem menuItem: menuItems){
            productNames.add(menuItem.getTitle());
        }
        return productNames;
    }

    public int computePrice(ArrayList<MenuItem> menuItems){
        int sum = 0;
        for(MenuItem menuItem: menuItems){
            sum += menuItem.computePrice();
        }
        return sum;
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
            List<EmployeePopulation> populations = populateTable();
            int minHour = Integer.parseInt(startField.getText().split(":")[0]);
            int minMinute = Integer.parseInt(startField.getText().split(":")[1]);
            int maxHour = Integer.parseInt(endField.getText().split(":")[0]);
            int maxMinute = Integer.parseInt(endField.getText().split(":")[1]);
            List<EmployeePopulation> updateList = DeliveryService.getObject().filterReport1(populations,minHour, maxHour, minMinute, maxMinute);
            report1Table.getItems().clear();
            report1Table.getItems().setAll(updateList);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Field empty or in wrong format");
            alert.showAndWait();
        }
    }

    public boolean checkInput(){
        if(startField.getText() == "" || endField.getText() == "")
            return false;
        if(startField.getText().length() != 5 || endField.getText().length() != 5)
            return false;
        if(startField.getText().charAt(2) != ':' || startField.getText().charAt(2) != ':')
            return false;
        return true;
    }
}

