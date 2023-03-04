package ro.tucn.assignment4.Business;

import java.io.Serializable;
import java.util.Objects;

public abstract class MenuItem implements Serializable {

    private String title;

    public String getTitle(){ return this.title; }

    @Override
    public boolean equals(Object o) {
        if(o instanceof MenuItem)
            return getTitle().equals(((MenuItem) o).getTitle());
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public abstract float computeRating();
    public abstract int computeCalories();
    public abstract int computeProtein();
    public abstract int computeFat();
    public abstract int computeSodium();
    public abstract int computePrice();

}
