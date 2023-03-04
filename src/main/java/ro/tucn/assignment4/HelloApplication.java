package ro.tucn.assignment4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.tucn.assignment4.Business.Client;
import ro.tucn.assignment4.Business.DeliveryService;
import ro.tucn.assignment4.Business.MenuItem;
import ro.tucn.assignment4.Business.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("employee-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 293, 346);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DeliveryService.getObject().addObserver(loader.getController());

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 293, 346);
        stage.setTitle("Login options");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        DeliveryService deliveryService = DeliveryService.getObject();
        ArrayList<MenuItem> writeItems = new ArrayList<>();
        ArrayList<Client> writeClients = new ArrayList<>();
        HashMap<Order,ArrayList<MenuItem>> writeOrders = new HashMap<>();
        writeItems = (ArrayList<MenuItem>) deliveryService.getMenuItems();
        writeClients = (ArrayList<Client>) deliveryService.getClients();
        writeOrders = deliveryService.getOrdersMapped();
        ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("menuItems.txt"));
        ObjectOutputStream file2 = new ObjectOutputStream(new FileOutputStream("clients.txt"));
        ObjectOutputStream file3 = new ObjectOutputStream(new FileOutputStream("orders.txt"));
        file.writeObject(writeItems);
        file2.writeObject(writeClients);
        file3.writeObject(writeOrders);
        file.close();
        file2.close();
        file3.close();
        super.stop();
    }

    public static void main(String[] args) {
        ArrayList<MenuItem> readItems = new ArrayList<>();
        ArrayList<Client> readClients = new ArrayList<>();
        HashMap<Order, ArrayList<MenuItem>> readOrders = new HashMap<>();
        DeliveryService deliveryService = DeliveryService.getObject();
        try {
            ObjectInputStream file = new ObjectInputStream(new FileInputStream("menuItems.txt"));
            ObjectInputStream file2 = new ObjectInputStream(new FileInputStream("clients.txt"));
            ObjectInputStream file3 = new ObjectInputStream(new FileInputStream("orders.txt"));
            readItems = (ArrayList<MenuItem>) file.readObject();
            readClients = (ArrayList<Client>) file2.readObject();
            readOrders = (HashMap<Order, ArrayList<MenuItem>>) file3.readObject();
            deliveryService.setMenuItems(readItems);
            deliveryService.setClients(readClients);
            deliveryService.setOrdersMapped(readOrders);
            file.close();
            file2.close();
            file3.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        launch();
    }

}