package c_restaurant.bean;

import java.util.Map;

public class Menu {

    Map<MenuItem, Double> appetizers;
    Map<MenuItem, Double> mains;
    Map<MenuItem, Double> desserts;

    public Menu(Map<MenuItem, Double> appetizers, Map<MenuItem, Double> mains, Map<MenuItem, Double> desserts) {
        this.appetizers = appetizers;
        this.mains = mains;
        this.desserts = desserts;
    }

    public Map<MenuItem, Double> getAppetizers() {
        return appetizers;
    }

    public Map<MenuItem, Double> getMains() {
        return mains;
    }

    public Map<MenuItem, Double> getDesserts() {
        return desserts;
    }

}
