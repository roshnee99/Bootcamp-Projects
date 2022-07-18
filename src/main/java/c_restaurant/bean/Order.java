package c_restaurant.bean;

import c_restaurant.util.RestaurantUtility;

import java.util.*;

public class Order {

    private MenuItem appetizer;
    private Set<MenuItem> mains;
    private MenuItem dessert;

    public void setAppetizer(MenuItem appetizer) {
        this.appetizer = appetizer;
    }

    public void addMain(MenuItem main) {
        if (this.mains == null) {
            this.mains = new HashSet<>();
        }
        this.mains.add(main);
    }

    public void setDessert(MenuItem dessert) {
        this.dessert = dessert;
    }

    public MenuItem getAppetizer() {
        return appetizer;
    }

    public Set<MenuItem> getMains() {
        return mains;
    }

    public MenuItem getDessert() {
        return dessert;
    }

    public int totalTimeToCompleteOrder() {
        int totalTime = 0;
        // first generate time for appetizer, if part of the order
        if (this.appetizer != null) {
            totalTime += RestaurantUtility.totalTimeToCook(this.appetizer);
            totalTime += this.appetizer.getMinutesToEat();
        }
        // generate time to cook for mains, with logic understanding all food comes out at the same time
        totalTime += getMaxTimeToCook(this.mains);
        Set<MenuItem> itemsSentBack = itemsSentBack(this.mains);
        totalTime += getMaxTimeToCook(itemsSentBack);
        totalTime += getMaxTimeToEat(this.mains);
        // generate time for dessert
        if (this.dessert != null) {
            totalTime += RestaurantUtility.totalTimeToCook(this.dessert);
            totalTime += this.dessert.getMinutesToEat();
        }
        return totalTime;
    }

    private static int getMaxTimeToCook(Set<MenuItem> items) {
        if (items.isEmpty()) {
            return 0;
        }
        OptionalInt maxTimeToCook = items.stream().mapToInt(MenuItem::getMinutesToCook).max();
        return maxTimeToCook.orElse(0);
    }

    private static int getMaxTimeToEat(Set<MenuItem> items) {
        OptionalInt maxTimeToEat = items.stream().mapToInt(MenuItem::getMinutesToEat).max();
        return maxTimeToEat.orElse(0);
    }

    private static Set<MenuItem> itemsSentBack(Set<MenuItem> items) {
        Set<MenuItem> itemsSentBack = new HashSet<>();
        for (MenuItem item : items) {
            boolean isSentBack = RestaurantUtility.weightedCoinFlip(item.getProbabilityOfBeingSentBack());
            if (isSentBack) {
                itemsSentBack.add(item);
            }
        }
        return itemsSentBack;
    }
}
