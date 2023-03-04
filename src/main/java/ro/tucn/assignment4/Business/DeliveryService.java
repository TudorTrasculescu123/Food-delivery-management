package ro.tucn.assignment4.Business;

import javafx.collections.ObservableArrayBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.stage.Stage;
import ro.tucn.assignment4.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @invariant wellFormed()
 */
public class DeliveryService extends Observable implements IDeliveryServiceProcessing{
    private List<Client> clients;
    private List<MenuItem> menuItems;
    private static DeliveryService deliveryService;
    private HashMap<Order, ArrayList<MenuItem>> ordersMapped;

    public DeliveryService(){
        menuItems = new ArrayList<>();
        clients = new ArrayList<>();
        ordersMapped = new HashMap<>();
    }

    public void populateWIthData(String pathToCSV){
        //preconditions
        assert(pathToCSV != null);
        assert(menuItems != null);

        Path path = Paths.get(pathToCSV);
        if(Files.exists(path)){
            try (Stream<String> lines = Files.lines(path)) {
                menuItems = lines
                        .skip(1)
                        .map(line -> {
                            StringBuilder s = new StringBuilder(line.split(",")[0]);
                            s.delete(s.length()-1, s.length()-1);
                            String title = s.toString();
                            float rating = Float.parseFloat(line.split(",")[1]);
                            int calories = Integer.parseInt(line.split(",")[2]);
                            int protein = Integer.parseInt(line.split(",")[3]);
                            int fat = Integer.parseInt(line.split(",")[4]);
                            int sodium = Integer.parseInt(line.split(",")[5]);
                            int price = Integer.parseInt(line.split(",")[6]);
                            MenuItem menuItem = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
                            return menuItem;
                        })
                        .distinct()
                        .collect(Collectors.toList());
            }catch(IOException e){
                System.out.println("Something went wrong when reading from the .csv file");
            }
        }else{
            System.out.println("File doesn't exist");
        }
        wellFormed();

        //postconditions
        assert(menuItems.size() > 0);
    }

    public static DeliveryService getObject(){
        if(deliveryService == null){
            deliveryService = new DeliveryService();
        }
        return deliveryService;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void updateBaseProducts(BaseProduct oldProduct, BaseProduct newProduct){
        //preconditions
        assert(oldProduct != null);
        assert(newProduct != null);

        for(MenuItem menuItem: menuItems){
            if(menuItem instanceof BaseProduct){
                if(isEqual(oldProduct, (BaseProduct) menuItem)){
                    ((BaseProduct) menuItem).setTitle(newProduct.getTitle());
                    ((BaseProduct) menuItem).setCalories(newProduct.getCalories());
                    ((BaseProduct) menuItem).setFat(newProduct.getFat());
                    ((BaseProduct) menuItem).setProtein(newProduct.getProtein());
                    ((BaseProduct) menuItem).setPrice(newProduct.getPrice());
                    ((BaseProduct) menuItem).setRating(newProduct.getRating());
                    ((BaseProduct) menuItem).setSodium(newProduct.getSodium());
                }
            }
        }
        wellFormed();
    }

    public List<MenuItem> filterClient(String title, float ratingMin, float ratingMax, int caloriesMin, int caloriesMax, int fatMin,
                             int fatMax, int proteinMin, int proteinMax, int priceMin, int priceMax, int sodiumMin, int sodiumMax){
        //preconditions
        assert(menuItems != null);

        List<MenuItem> newList = menuItems
                .stream()
                .filter(menuItem -> {return menuItem.computeRating() >= ratingMin;})
                .filter(menuItem -> {return menuItem.computeRating() <= ratingMax;})
                .filter(menuItem -> {return menuItem.computeCalories() >= caloriesMin;})
                .filter(menuItem -> {return menuItem.computeCalories() <= caloriesMax;})
                .filter(menuItem -> {return  menuItem.computeFat() >= fatMin;})
                .filter(menuItem -> {return menuItem.computeFat() <= fatMax;})
                .filter(menuItem -> {return menuItem.computeProtein() >= proteinMin;})
                .filter(menuItem -> {return menuItem.computeProtein() <= proteinMax;})
                .filter(menuItem -> {return menuItem.computePrice() >= priceMin;})
                .filter(menuItem -> {return menuItem.computePrice() <= priceMax;})
                .filter(menuItem -> {return menuItem.computeSodium() >= sodiumMin;})
                .filter(menuItem -> {return menuItem.computeSodium() <= sodiumMax;})
                .filter(menuItem -> {return menuItem.getTitle().toLowerCase().contains(title.toLowerCase()); })
                .collect(Collectors.toList());
        wellFormed();

        //postconditions
        assert(newList.size() <= menuItems.size());

        return newList;
    }

    public List<EmployeePopulation> filterReport1(List<EmployeePopulation> lst, int minHour, int maxHour, int minMinute, int maxMinute){
        //preconditions
        wellFormed();

        List<EmployeePopulation> updateLst = lst
                .stream()
                .filter(employeePopulation -> {return employeePopulation.getDate().getHours() >= minHour;})
                .filter(employeePopulation -> {return employeePopulation.getDate().getHours() <= maxHour;})
                .collect(Collectors.toList());

        //postconditions
        assert(updateLst.size() <= lst.size());

        return updateLst;
    }

    public List<MenuItem> filterReport2(int minNumber){
        //preconditions
        wellFormed();

        List<MenuItem> updateList = menuItems
                .stream()
                .filter(menuItem -> {
                    List<Order> orders = ordersMapped.keySet()
                            .stream()
                            .filter(order -> {
                                return ordersMapped.get(order).contains(menuItem);
                            })
                            .collect(Collectors.toList());
                    return minNumber <= orders.size();
                })
                .collect(Collectors.toList());

        //postconditions
        assert(updateList.size() <= menuItems.size());

        return updateList;
    }

    public List<Client> filterReport3(int price, int numberOfTimes){
        //preconditions
        wellFormed();

        List<Client> updateList = clients
                .stream()
                .filter(client -> {
                    List<Order> orders = ordersMapped.keySet()
                            .stream()
                            .filter(order -> {
                                return order.getClientID() == client.getClientID();
                            })
                            .filter(order -> {
                                return order.computePrice() >= price;
                            })
                            .collect(Collectors.toList());
                    if(orders.size() > 0)
                        return orders.size() >= numberOfTimes;
                    return false;
                })
                .collect(Collectors.toList());

        //postconditions
        assert(updateList.size() <= clients.size());

        return updateList;
    }

    public HashMap<MenuItem, Integer> filterReport4(int day, int month){
        //preconditions
        wellFormed();

        HashMap<MenuItem, Integer> updateMap = new HashMap<>();
        List<MenuItem>items = menuItems
                .stream()
                .filter(menuItem -> {
                    List<Order> orders= ordersMapped.keySet()
                            .stream()
                            .filter(order -> {
                                return ordersMapped.get(order).contains(menuItem)
                                        && Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth() == day
                                        && Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate().getMonth().getValue() == month;
                            })
                            .collect(Collectors.toList());
                    if(orders.size() != 0)
                        updateMap.put(menuItem, orders.size());
                    return orders.size() > 0;
                })
                .collect(Collectors.toList());
        return updateMap;
    }

    public boolean isEqual(BaseProduct p1, BaseProduct p2){
        if(p1.getTitle() != p2.getTitle()) return false;
        if(p1.getCalories() != p2.getCalories()) return false;
        if(p1.getSodium() != p2.getSodium()) return false;
        if(p1.getFat() != p2.getFat()) return false;
        if(p1.getProtein() != p2.getProtein()) return false;
        if(p1.getRating() != p2.getRating()) return false;
        if(p1.getPrice() != p2.getPrice()) return false;
        return true;
    }

    private boolean wellFormed(){
        assert(ordersMapped != null);
        assert(clients != null);
        assert(menuItems != null);

        Date currentDate = new Date(System.currentTimeMillis());
        for(Order order: ordersMapped.keySet()){
            assert(order.getOrderDate().before(currentDate));
        }

        for(Client client: clients){
            assert(!client.getUsername().isEmpty());
        }

        return true;
    }


    public void makeOrder(Order order, ArrayList<MenuItem> menuItems){
        //preconditions
        assert(order != null);
        assert(menuItems != null);
        int sizeBefore = ordersMapped.size();

        ordersMapped.put(order, menuItems);
        this.setChanged();
        notifyObservers(order);
        wellFormed();

        //postconditions
        int sizeAfter = ordersMapped.size();
        assert(sizeBefore + 1 == sizeAfter);
        assert(ordersMapped.containsKey(order));
        assert(ordersMapped.containsValue(menuItems));
    }

    public HashMap<Order, ArrayList<MenuItem>> getOrdersMapped() {
        return ordersMapped;
    }

    public void setOrdersMapped(HashMap<Order, ArrayList<MenuItem>> ordersMapped) {
        this.ordersMapped = ordersMapped;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}