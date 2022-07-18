package c_restaurant.bean;

import java.util.Map;

public class MenuItem {

    private String name;
    private EType mealType;
    private Map<Food, Integer> ingredients;
    private int minutesToCook;
    private int minutesToEat;
    private double probabilityOfBeingSentBack;

    public MenuItem() {
        // do nothing
    }

    public MenuItem(String name, EType mealType, Map<Food, Integer> ingredients, int minutesToCook, int minutesToEat, double probabilityOfBeingSentBack) {
        this.name = name;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.minutesToCook = minutesToCook;
        this.minutesToEat = minutesToEat;
        this.probabilityOfBeingSentBack = probabilityOfBeingSentBack;
    }

    public String getName() {
        return name;
    }

    public EType getMealType() {
        return mealType;
    }

    public Map<Food, Integer> getIngredients() {
        return ingredients;
    }

    public int getMinutesToCook() {
        return minutesToCook;
    }

    public int getMinutesToEat() {
        return minutesToEat;
    }

    public double getProbabilityOfBeingSentBack() {
        return probabilityOfBeingSentBack;
    }

    public MenuItem withName(String name) {
        this.name = name;
        return this;
    }

    public MenuItem withMealType(EType mealType) {
        this.mealType = mealType;
        return this;
    }

    public MenuItem withIngredients(Map<Food, Integer> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public MenuItem withMinutesToCook(int minutesToCook) {
        this.minutesToCook = minutesToCook;
        return this;
    }

    public MenuItem withMinutesToEat(int minutesToEat) {
        this.minutesToEat = minutesToEat;
        return this;
    }

    public MenuItem withProbabilityOfBeingSentBack(double probabilityOfBeingSentBack) {
        this.probabilityOfBeingSentBack = probabilityOfBeingSentBack;
        return this;
    }
}
