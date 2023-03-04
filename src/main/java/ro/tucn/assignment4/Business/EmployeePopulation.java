package ro.tucn.assignment4.Business;

import javafx.scene.control.Menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeePopulation {
    private String clientName;
    private Date date;
    private int price;
    private ArrayList<String> products;

    public EmployeePopulation(String clientName, Date date, int price) {
        this.clientName = clientName;
        this.date = date;
        this.price = price;
        products = new ArrayList<>();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }
}
