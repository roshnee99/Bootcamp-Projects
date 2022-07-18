package c_restaurant.util;

import c_restaurant.bean.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RestaurantUtility {

    private RestaurantUtility() {
        // restrict instantiation
    }

    public static MenuItem pickItemFromMap(Map<MenuItem, Double> optionsWithProbability) {
        double totalSum = optionsWithProbability.values().stream().mapToDouble(x -> x).sum();
        if (Double.compare(totalSum, 1) != 0) {
            throw new IllegalArgumentException("Probabilities must add to 1");
        }
        List<MenuItem> tempMenuList = getTempMenuList(optionsWithProbability);
        return getRandomElement(tempMenuList);
    }

    /**
     * Returns true weight% of the time.
     *
     * For example, if weight is 0.6, then 60% of the time, this method will return true
     */
    public static boolean weightedCoinFlip(double weight) {
        double randomNumber = Math.random(); // number between [0, 1)
        return randomNumber < weight; // returns true if number is less than weight, otherwise false
    }

    /**
     * Takes into account time to cook and time to send back
     * @param menuItem Menu Item to determine the time to cook. Cannot be null.
     * @return int totalTimeToCook
     */
    public static int totalTimeToCook(MenuItem menuItem) {
        int totalTime = 0;
        int timeToCook = menuItem.getMinutesToCook();
        totalTime += timeToCook;
        // check if appetizer gets sent back
        boolean isSentBack = RestaurantUtility.weightedCoinFlip(menuItem.getProbabilityOfBeingSentBack());
        if (isSentBack) {
            // we assume it will be right the second time
            totalTime += timeToCook;
        }
        return totalTime;
    }

    private static MenuItem getRandomElement(List<MenuItem> tempMenuList) {
        // get random menu item from the list
        Random rand = new Random();
        int randomIndex = rand.nextInt(tempMenuList.size());
        return tempMenuList.get(randomIndex);
    }

    private static List<MenuItem> getTempMenuList(Map<MenuItem, Double> optionsWithProbability) {
        // generate a list of size 100 with entries entered as likely as how they were inserted
        // ex. <Cheese, 0.4>, <Omelette, 0.05>, <Burger, 0.55> would create a list of size 100
        // as follows [Cheese, Cheese, Cheese ...., Cheese, Omelette, ..., Omelette, Burger, .... Burger]
        // with Cheese in the list 40 times, omelette 5 times, and burger 55 times
        List<MenuItem> tempMenuList = new ArrayList<>();
        for (Map.Entry<MenuItem, Double> entry : optionsWithProbability.entrySet()) {
            double probability = entry.getValue();
            int numCount = (int) (probability * 100);
            for (int i = 0; i < numCount; i++) {
                tempMenuList.add(entry.getKey());
            }
        }
        return tempMenuList;
    }

    public static Integer pickItemFromIntMap(Map<Integer, Double> optionsWithProbability) {
        double totalSum = optionsWithProbability.values().stream().mapToDouble(x -> x).sum();
        if (Double.compare(totalSum, 1) != 0) {
            throw new IllegalArgumentException("Probabilities must add to 1");
        }
        List<Integer> tempIntegerList = getTempIntegerList(optionsWithProbability);
        return getRandomIntegerFromList(tempIntegerList);
    }

    private static List<Integer> getTempIntegerList(Map<Integer, Double> optionsWithProbability) {
        List<Integer> tempIntegerList = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : optionsWithProbability.entrySet()) {
            double probability = entry.getValue();
            int numCount = (int) (probability * 100);
            for (int i = 0; i < numCount; i++) {
                tempIntegerList.add(entry.getKey());
            }
        }
        return tempIntegerList;
    }

    private static Integer getRandomIntegerFromList(List<Integer> tempMenuList) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(tempMenuList.size());
        return tempMenuList.get(randomIndex);
    }

    public static <T> T getRandomObjectFromList(List<T> list) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(list.size());
        return list.get(randomIndex);
    }

}
