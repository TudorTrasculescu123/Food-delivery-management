package ro.tucn.assignment4.Business;

public class Report4Product {
    private String product;
    private int numberOfOrders;

    public Report4Product(String product, int numberOfOrders) {
        this.product = product;
        this.numberOfOrders = numberOfOrders;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
}
