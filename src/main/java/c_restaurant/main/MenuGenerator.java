package c_restaurant.main;

import c_restaurant.bean.EType;
import c_restaurant.bean.Food;
import c_restaurant.bean.Menu;
import c_restaurant.bean.MenuItem;
import c_restaurant.util.RestaurantUtility;

import java.util.HashMap;
import java.util.Map;

public class MenuGenerator {

    private MenuGenerator() {
        // restrict instantiate
    }

    public static Menu generateMenu() {
        Map<MenuItem, Double> appetizers = generateAppetizers();
        Map<MenuItem, Double> mains = generateMains();
        Map<MenuItem, Double> desserts = generateDesserts();
        return new Menu(appetizers, mains, desserts);
    }

    private static Map<MenuItem, Double> generateAppetizers() {
        // create appetizers
        MenuItem nachos = generateNachos();
        MenuItem soup = generateSoup();
        MenuItem salad = generateSalad();
        MenuItem calamari = generateCalamari();
        // populate appetizer map with probabilities
        Map<MenuItem, Double> appetizerOptions = new HashMap<>();
        appetizerOptions.put(nachos, 0.35);
        appetizerOptions.put(soup, 0.2);
        appetizerOptions.put(salad, 0.25);
        appetizerOptions.put(calamari, 0.2);
        return appetizerOptions;
    }

    private static Map<MenuItem, Double> generateMains() {
        MenuItem meatballSub = generateMeatballSub();
        MenuItem salmon = generateSalmon();
        MenuItem cheeseRavioli = generateCheeseRavioli();
        MenuItem chickenParm = generateChickenParm();
        MenuItem grilledPrawns = generateGrilledPrawns();
        MenuItem mushroomChicken = generateMushroomChicken();
        // populate main map with probabilities
        Map<MenuItem, Double> mainOptions = new HashMap<>();
        mainOptions.put(meatballSub, 0.15);
        mainOptions.put(salmon, 0.24);
        mainOptions.put(cheeseRavioli, 0.1);
        mainOptions.put(chickenParm, 0.26);
        mainOptions.put(grilledPrawns, 0.2);
        mainOptions.put(mushroomChicken, 0.05);
        return mainOptions;
    }

    private static Map<MenuItem, Double> generateDesserts() {
        MenuItem cremeBrulee = generateCremeBrulee();
        MenuItem cheesecake = generateCheesecake();
        MenuItem souffle = generateChocolateSouffle();
        // populate dessert map with probabilities
        Map<MenuItem, Double> dessertOptions = new HashMap<>();
        dessertOptions.put(cremeBrulee, 0.5);
        dessertOptions.put(cheesecake, 0.35);
        dessertOptions.put(souffle, 0.15);

        return dessertOptions;
    }

    private static MenuItem generateChocolateSouffle() {
        Map<Food, Integer> souffleIngredients = new HashMap<>();
        souffleIngredients.put(new Food("Heavy Cream"), 1);
        souffleIngredients.put(new Food("Egg"), 1);
        souffleIngredients.put(new Food("Chocolate"), 3);
        souffleIngredients.put(new Food("Sugar"), 2);
        souffleIngredients.put(new Food("Milk"), 1);
        souffleIngredients.put(new Food("Flour"), 1);
        souffleIngredients.put(new Food("Raspberry"), 1);
        return new MenuItem()
                .withName("Chocolate Souffle")
                .withMealType(EType.DESSERT)
                .withIngredients(souffleIngredients)
                .withMinutesToCook(25)
                .withMinutesToEat(15)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateCheesecake() {
        Map<Food, Integer> cheesecakeIngredients = new HashMap<>();
        cheesecakeIngredients.put(new Food("Heavy Cream"), 1);
        cheesecakeIngredients.put(new Food("Cheese"), 3);
        cheesecakeIngredients.put(new Food("Egg"), 1);
        cheesecakeIngredients.put(new Food("Vanilla"), 1);
        cheesecakeIngredients.put(new Food("Butter"), 1);
        cheesecakeIngredients.put(new Food("Sugar"), 2);
        cheesecakeIngredients.put(new Food("Graham Cracker"), 1);
        cheesecakeIngredients.put(new Food("Raspberry"), 1);
        return new MenuItem()
                .withName("Cheesecake")
                .withMealType(EType.DESSERT)
                .withIngredients(cheesecakeIngredients)
                .withMinutesToCook(2)
                .withMinutesToEat(15)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateCremeBrulee() {
        Map<Food, Integer> cremeBruleeIngredients = new HashMap<>();
        cremeBruleeIngredients.put(new Food("Heavy Cream"), 2);
        cremeBruleeIngredients.put(new Food("Sugar"), 1);
        cremeBruleeIngredients.put(new Food("Egg"), 1);
        cremeBruleeIngredients.put(new Food("Vanilla"), 1);
        return new MenuItem()
                .withName("Creme Brulee")
                .withMealType(EType.DESSERT)
                .withIngredients(cremeBruleeIngredients)
                .withMinutesToCook(5)
                .withMinutesToEat(15)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateMushroomChicken() {
        Map<Food, Integer> mushroomChickenIngredients = new HashMap<>();
        mushroomChickenIngredients.put(new Food("Chicken"), 3);
        mushroomChickenIngredients.put(new Food("Mushrooms"), 1);
        mushroomChickenIngredients.put(new Food("Heavy Cream"), 3);
        mushroomChickenIngredients.put(new Food("Garlic"), 1);
        return new MenuItem()
                .withName("Mushroom Chicken")
                .withMealType(EType.MAIN)
                .withIngredients(mushroomChickenIngredients)
                .withMinutesToCook(20)
                .withMinutesToEat(20)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateGrilledPrawns() {
        Map<Food, Integer> grilledPrawnIngredients = new HashMap<>();
        grilledPrawnIngredients.put(new Food("Prawns"), 2);
        grilledPrawnIngredients.put(new Food("Butter"), 1);
        grilledPrawnIngredients.put(new Food("Herbs"), 1);
        grilledPrawnIngredients.put(new Food("Lemon"), 1);
        grilledPrawnIngredients.put(new Food("Broccoli"), 1);
        grilledPrawnIngredients.put(new Food("Garlic"), 1);
        return new MenuItem()
                .withName("Grilled Prawn")
                .withMealType(EType.MAIN)
                .withIngredients(grilledPrawnIngredients)
                .withMinutesToCook(15)
                .withMinutesToEat(20)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateChickenParm() {
        Map<Food, Integer> chickenParmIngredients = new HashMap<>();
        chickenParmIngredients.put(new Food("Flour"), 1);
        chickenParmIngredients.put(new Food("Egg"), 1);
        chickenParmIngredients.put(new Food("Chicken"), 3);
        chickenParmIngredients.put(new Food("Cheese"), 5);
        chickenParmIngredients.put(new Food("Tomato"), 1);
        chickenParmIngredients.put(new Food("Garlic"), 1);
        return new MenuItem()
                .withName("Chicken Parmesan")
                .withMealType(EType.MAIN)
                .withIngredients(chickenParmIngredients)
                .withMinutesToCook(30)
                .withMinutesToEat(20)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateCheeseRavioli() {
        Map<Food, Integer> ravioliIngredients = new HashMap<>();
        ravioliIngredients.put(new Food("Flour"), 1);
        ravioliIngredients.put(new Food("Egg"), 1);
        ravioliIngredients.put(new Food("Cheese"), 3);
        ravioliIngredients.put(new Food("Tomato"), 1);
        ravioliIngredients.put(new Food("Garlic"), 1);
        return new MenuItem()
                .withName("Cheese Ravioli")
                .withMealType(EType.MAIN)
                .withIngredients(ravioliIngredients)
                .withMinutesToCook(25)
                .withMinutesToEat(20)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateSalmon() {
        Map<Food, Integer> salmonIngredients = new HashMap<>();
        salmonIngredients.put(new Food("Salmon"), 2);
        salmonIngredients.put(new Food("Broccoli"), 4);
        salmonIngredients.put(new Food("Lemon"), 1);
        salmonIngredients.put(new Food("Rice"), 1);
        return new MenuItem()
                .withName("Salmon")
                .withMealType(EType.MAIN)
                .withIngredients(salmonIngredients)
                .withMinutesToCook(10)
                .withMinutesToEat(15)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateMeatballSub() {
        Map<Food, Integer> meatballSubIngredients = new HashMap<>();
        meatballSubIngredients.put(new Food("Bread"), 2);
        meatballSubIngredients.put(new Food("Pork"), 4);
        meatballSubIngredients.put(new Food("Garlic"), 1);
        meatballSubIngredients.put(new Food("Bread Crumbs"), 1);
        meatballSubIngredients.put(new Food("Egg"), 1);
        meatballSubIngredients.put(new Food("Tomato"), 1);
        meatballSubIngredients.put(new Food("Cheese"), 1);
        return new MenuItem()
                .withName("Meatball Sub")
                .withMealType(EType.MAIN)
                .withIngredients(meatballSubIngredients)
                .withMinutesToCook(20)
                .withMinutesToEat(20)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateCalamari() {
        Map<Food, Integer> calamariIngredients = new HashMap<>();
        calamariIngredients.put(new Food("Squid"), 2);
        calamariIngredients.put(new Food("Tomato"), 1);
        calamariIngredients.put(new Food("Garlic"), 1);
        calamariIngredients.put(new Food("Flour"), 1);
        calamariIngredients.put(new Food("Lemon"), 1);
        calamariIngredients.put(new Food("Milk"), 1);
        return new MenuItem()
                .withName("Calamari")
                .withMealType(EType.APPETIZER)
                .withIngredients(calamariIngredients)
                .withMinutesToCook(20)
                .withMinutesToEat(10)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateSalad() {
        Map<Food, Integer> saladIngredients = new HashMap<>();
        saladIngredients.put(new Food("Lettuce"), 2);
        saladIngredients.put(new Food("Tomato"), 1);
        saladIngredients.put(new Food("Cucumber"), 1);
        saladIngredients.put(new Food("Onion"), 1);
        saladIngredients.put(new Food("Ranch"), 1);
        return new MenuItem()
                .withName("Salad")
                .withMealType(EType.APPETIZER)
                .withIngredients(saladIngredients)
                .withMinutesToCook(5)
                .withMinutesToEat(10)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateSoup() {
        Map<Food, Integer> soupIngredients = new HashMap<>();
        soupIngredients.put(new Food("Tomato"), 5);
        soupIngredients.put(new Food("Red Pepper"), 1);
        soupIngredients.put(new Food("Heavy Cream"), 2);
        soupIngredients.put(new Food("Onion"), 1);
        soupIngredients.put(new Food("Garlic"), 1);
        soupIngredients.put(new Food("Butter"), 1);
        return new MenuItem()
                .withName("Soup")
                .withMealType(EType.APPETIZER)
                .withIngredients(soupIngredients)
                .withMinutesToCook(15)
                .withMinutesToEat(10)
                .withProbabilityOfBeingSentBack(0);
    }

    private static MenuItem generateNachos() {
        // NACHOS
        Map<Food, Integer> nachoIngredients = new HashMap<>();
        nachoIngredients.put(new Food("Chips"), 1);
        nachoIngredients.put(new Food("Tomato"), 1);
        nachoIngredients.put(new Food("Chicken"), 2);
        nachoIngredients.put(new Food("Cheese"), 5);
        nachoIngredients.put(new Food("Sour Cream"), 1);
        nachoIngredients.put(new Food("Black bean"), 1);
        return new MenuItem()
                .withName("Nachos")
                .withMealType(EType.APPETIZER)
                .withIngredients(nachoIngredients)
                .withMinutesToCook(10)
                .withMinutesToEat(15)
                .withProbabilityOfBeingSentBack(0);
    }

    public static void main(String[] args) {
        Menu menu = generateMenu();
        Map<String, Integer> countOfItems = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            MenuItem app = RestaurantUtility.pickItemFromMap(menu.getDesserts());
            if (countOfItems.containsKey(app.getName())) {
                int oldCount = countOfItems.get(app.getName());
                countOfItems.put(app.getName(), oldCount+1);
            } else {
                countOfItems.put(app.getName(), 1);
            }
        }
        countOfItems.forEach((k, v) -> System.out.println(k + ": " + v));
    }

}
