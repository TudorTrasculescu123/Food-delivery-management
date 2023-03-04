package ro.tucn.assignment4.Business;

public class BaseProduct extends MenuItem{
    private String title;
    private float rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;

    public BaseProduct(String title, float rating, int calories, int proteins, int fats, int sodium, int price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = proteins;
        this.fat = fats;
        this.sodium = sodium;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int proteins) {
        this.protein = proteins;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fats) {
        this.fat = fats;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int computePrice(){
        return getPrice();
    }

    @Override
    public float computeRating() {
        return getRating();
    }

    @Override
    public int computeCalories() {
        return getCalories();
    }

    @Override
    public int computeProtein() {
        return getProtein();
    }

    @Override
    public int computeFat() {
        return getFat();
    }

    @Override
    public int computeSodium() {
        return getSodium();
    }
}
