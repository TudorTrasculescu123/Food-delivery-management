package ro.tucn.assignment4.Business;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {

    private int orderID;
    private int clientID;
    private Date orderDate;

    public Order(int orderID, int clientID) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = new Date(System.currentTimeMillis());
    }

    public int computePrice(){
        int sum = 0;
        List<MenuItem> menuItems = DeliveryService.getObject().getOrdersMapped().get(this);
        for(MenuItem menuItem: menuItems){
            sum += menuItem.computePrice();
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID);
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

}
