package ro.tucn.assignment4.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.tucn.assignment4.Business.*;
import ro.tucn.assignment4.HelloApplication;

import java.io.IOException;
import java.util.*;

public class EmployeeController implements Observer {

    @FXML
    private Button backButton;

    @FXML
    private TableView employeeTable;

    public void initialize(){

        List<TableColumn<EmployeePopulation, Integer>> lst = new ArrayList<>();
        employeeTable.setColumnResizePolicy((param) -> true);
        lst.add(generateColumn("clientName"));
        lst.add(generateColumn("date"));
        lst.add(generateColumn("price"));
        lst.add(generateColumn("products"));
        employeeTable.getColumns().addAll(lst);
        populateTable();
    }

    public void populateTable(){
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
        employeeTable.getItems().setAll(lst);
    }

    public String findClientByID(int id){
        DeliveryService deliveryService = DeliveryService.getObject();
        ArrayList<Client> clients = (ArrayList<Client>) deliveryService.getClients();
        for(Client client: clients){
            if(client.getClientID() == id){
                return client.getUsername();
            }
        }
        System.out.println("Nu s-a gasit clientul, ceva e gresit in Employee controller");
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

    public TableColumn<EmployeePopulation, Integer> generateColumn(String fieldName){
        TableColumn<EmployeePopulation, Integer> column =  new TableColumn<>(fieldName);
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

    @Override
    public void update(Observable o, Object arg) {
        DeliveryService deliveryService = (DeliveryService) o;
        Order order = (Order) arg;
        HashMap<Order, ArrayList<MenuItem>> ordersMapped = deliveryService.getOrdersMapped();
        String clientName;
        Date date;
        int price;
        ArrayList<MenuItem> products;
        List<String> productNames;
        clientName = findClientByID(order.getClientID());
        date = order.getOrderDate();
        products = ordersMapped.get(order);
        price = computePrice(products);
        EmployeePopulation employee = new EmployeePopulation(clientName, date, price);
        productNames = getProductNames(products);
        employee.setProducts((ArrayList<String>) productNames);
        employeeTable.getItems().add(employee);
    }
}
