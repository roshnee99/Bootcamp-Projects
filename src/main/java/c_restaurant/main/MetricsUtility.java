package c_restaurant.main;

import c_restaurant.bean.Food;
import c_restaurant.bean.MenuItem;
import c_restaurant.bean.Order;
import c_restaurant.bean.Party;

import java.util.*;
import java.util.stream.Collectors;

public class MetricsUtility {

    private List<Party> parties;

    public MetricsUtility(List<Party> parties) {
        this.parties = parties;
    }

    public int totalPartiesThatEnteredRestaurant() {
        return parties.size();
    }

    public int totalPartiesThatGotSeated() {
        return (int) parties.stream().filter(x -> x.getOrder() != null).count();
    }

    public double averageWaitTimeForPartiesThatGotSeated() {
        List<Integer> waitTimes = parties.stream()
                .filter(x -> x.getOrder() != null)
                .map(Party::getMinutesInQueue)
                .collect(Collectors.toList());
        return getMean(waitTimes);
    }

    public double averageWaitTimeForPartiesNotSeated() {
        List<Integer> waitTimes = parties.stream()
                .filter(x -> x.getOrder() == null)
                .map(Party::getMinutesInQueue)
                .collect(Collectors.toList());
        return getMean(waitTimes);
    }

    public Map<String, Integer> getDistributionOfPartiesNeverSeated() {
        List<Party> neverSeatedParties = parties.stream()
                .filter(x -> x.getOrder() == null)
                .collect(Collectors.toList());
        Map<String, Integer> partySizeDistribution = new HashMap<>();
        for (Party party : neverSeatedParties) {
            addToMap(partySizeDistribution, Integer.toString(party.getPartyMembers().size()));
        }
        return partySizeDistribution;
    }

    public double averageSitDownTimeOfPartiesWhoCompletedMeal() {
        List<Integer> sitDownTimes = parties.stream()
                .filter(Party::isDone)
                .map(Party::getTotalMinutesWhileSeated)
                .collect(Collectors.toList());
        return getMean(sitDownTimes);
    }

    public double averageTotalTimeOfFullJourney() {
        List<Integer> totalRestaurantTime = parties.stream()
                .filter(Party::isDone)
                .map(x -> x.getMinutesInQueue() + x.getTotalMinutesWhileSeated())
                .collect(Collectors.toList());
        return getMean(totalRestaurantTime);
    }

    public Map<String, Integer> foodItemDistribution() {
        Map<String, Integer> foodsThatWereUsed = new HashMap<>();
        for (Party party : parties) {
            if (party.getOrder() != null) {
                MenuItem appetizer = party.getOrder().getAppetizer();
                if (appetizer != null) {
                    countIngredientsInMeal(foodsThatWereUsed, appetizer);
                }
                Set<MenuItem> mains = party.getOrder().getMains();
                mains.forEach(x -> countIngredientsInMeal(foodsThatWereUsed, x));
                MenuItem dessert = party.getOrder().getDessert();
                if (dessert != null) {
                    countIngredientsInMeal(foodsThatWereUsed, dessert);
                }
            }
        }
        return foodsThatWereUsed;
    }

    private void countIngredientsInMeal(Map<String, Integer> foodsThatWereUsed, MenuItem menuItem) {
        Map<Food, Integer> ingredients = menuItem.getIngredients();
        ingredients.forEach((food, num) -> {
            // add each food item in quantity it is added to the dish
            for (int i = 0; i < num; i++) {
                addToMap(foodsThatWereUsed, food.getName());
            }
        });
    }

    public List<Map<String, Integer>> getMealItemDistribution() {
        Map<String, Integer> countOfApps = new HashMap<>();
        Map<String, Integer> countOfMains = new HashMap<>();
        Map<String, Integer> countOfDesserts = new HashMap<>();
        for (Party party : parties) {
            Order order = party.getOrder();
            if (order == null)
                continue;
            MenuItem app = order.getAppetizer();
            if (app != null) {
                addToMap(countOfApps, app.getName());
            }
            Set<MenuItem> mains = order.getMains();
            mains.forEach(x -> addToMap(countOfMains, x.getName()));
            MenuItem dessert = order.getDessert();
            if (dessert != null) {
                addToMap(countOfDesserts, dessert.getName());
            }
        }
        return Arrays.asList(countOfApps, countOfMains, countOfDesserts);
    }

    private <T> void addToMap(Map<T, Integer> map, T key) {
        if (map.containsKey(key)) {
            int originalVal = map.get(key);
            map.put(key, originalVal + 1);
        } else {
            map.put(key, 1);
        }
    }

    private double getMean(List<Integer> waitTimes) {
        double sum = 0.0;
        for (int waitTime : waitTimes) {
            sum += waitTime;
        }
        return sum / waitTimes.size();
    }

    public static <T, V> String mapToString(Map<T, V> map) {
        StringBuilder builder = new StringBuilder();
        map.forEach((k, v) -> builder.append(k).append(": ").append(v).append("\n"));
        return builder.toString();
    }


}
