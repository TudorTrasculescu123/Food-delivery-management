package ro.tucn.assignment4.Business;

import javafx.scene.control.Menu;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem {

    private List<MenuItem> listOfBaseProducts;
    private String title;

    public CompositeProduct(){
        listOfBaseProducts = new ArrayList<>();
    }

    public CompositeProduct(String title){
        listOfBaseProducts = new ArrayList<>();
        this.title = title;
    }

    public List<MenuItem> getListOfBaseProducts() {
        return listOfBaseProducts;
    }

    public void setListOfBaseProducts(List<MenuItem> listOfBaseProducts) {
        this.listOfBaseProducts = listOfBaseProducts;
    }

    @Override
    public float computeRating() {
        float sum = 0;
        for(MenuItem menuItem: listOfBaseProducts){
            sum += menuItem.computeRating();
        }
        sum /= listOfBaseProducts.size();
        return sum;
    }

    @Override
    public int computeCalories() {
        int sum = 0;
        for(MenuItem menuItem: listOfBaseProducts){
            sum += menuItem.computeCalories();
        }
        return sum;
    }

    @Override
    public int computeProtein() {
        int sum = 0;
        for(MenuItem menuItem: listOfBaseProducts){
            sum += menuItem.computeProtein();
        }
        return sum;
    }

    @Override
    public int computeFat() {
        int sum = 0;
        for(MenuItem menuItem: listOfBaseProducts){
            sum += menuItem.computeFat();
        }
        return sum;
    }

    @Override
    public int computeSodium() {
        int sum = 0;
        for(MenuItem menuItem: listOfBaseProducts){
            sum += menuItem.computeSodium();
        }
        return sum;
    }

    @Override
    public int computePrice() {
        int sum = 0;
        for(MenuItem menuItem: listOfBaseProducts){
            sum += menuItem.computePrice();
        }
        return sum;
    }

    public String getTitle(){ return this.title; }
    public float getRating(){ return computeRating(); }
    public int getCalories(){ return computeCalories(); }
    public int getProtein(){ return computeProtein(); }
    public int getFat(){ return computeFat(); }
    public int getSodium(){ return computeSodium(); }
    public int getPrice() { return computePrice(); }
}
