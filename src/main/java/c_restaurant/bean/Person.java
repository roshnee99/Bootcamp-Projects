package c_restaurant.bean;

import c_restaurant.util.RestaurantUtility;

import java.util.Map;

public class Person {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    public MenuItem generateMainCourse(Menu menu) {
        Map<MenuItem, Double> mains = menu.getMains();
        return RestaurantUtility.pickItemFromMap(mains);
    }

}
