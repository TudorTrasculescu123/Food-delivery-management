package ro.tucn.assignment4.Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IDeliveryServiceProcessing {
    /**
     * @pre pathToCSV != null && menuItems != null
     * @post menuItems.size() > 0
     * @param pathToCSV
     */
    void populateWIthData(String pathToCSV);

    /**
     * @pre oldProduct && newProduct != null
     * @param oldProduct
     * @param newProduct
     */
    void updateBaseProducts(BaseProduct oldProduct, BaseProduct newProduct);

    /**
     * @pre menuItems != null
     * @post all items filtered by the conditions, i.e rating between ratingMin and ratingMax and all the other, also newList.size() <= menuItems
     * @param title
     * @param ratingMin
     * @param ratingMax
     * @param caloriesMin
     * @param caloriesMax
     * @param fatMin
     * @param fatMax
     * @param proteinMin
     * @param proteinMax
     * @param priceMin
     * @param priceMax
     * @param sodiumMin
     * @param sodiumMax
     * @return
     */
    List<MenuItem> filterClient(String title, float ratingMin, float ratingMax, int caloriesMin, int caloriesMax, int fatMin,
                                       int fatMax, int proteinMin, int proteinMax, int priceMin, int priceMax, int sodiumMin, int sodiumMax);


    /**
     * @pre menuItems != null && order != null
     * @post orderMapped.size.pre + 1 == orderMapped.size.post && orderMapped.contains(order)
     * @param order
     * @param menuItems
     */
    void makeOrder(Order order, ArrayList<MenuItem> menuItems);

    /**
     * @pre wellFormed()
     * @post prevLst.size() >= updatedLst.size()
     * @param lst
     * @param minHour
     * @param maxHour
     * @param minMinute
     * @param maxMinute
     * @return
     */
    List<EmployeePopulation> filterReport1(List<EmployeePopulation> lst, int minHour, int maxHour, int minMinute, int maxMinute);

    /**
     * @pre wellFormed()
     * @post prevLst.size() >= updatedLst.size()
     * @param minNumber
     * @return
     */
    List<MenuItem> filterReport2(int minNumber);

    /**
     * @pre wellFormed()
     * @post prevLst.size() >= updatedLst.size()
     * @param price
     * @param numberOfTimes
     * @return
     */
    List<Client> filterReport3(int price, int numberOfTimes);

    HashMap<MenuItem, Integer> filterReport4(int day, int month);

}
