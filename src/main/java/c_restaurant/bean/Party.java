package c_restaurant.bean;

import c_restaurant.constant.Config;
import c_restaurant.util.RestaurantUtility;

import java.util.List;

public class Party {

    private List<Person> partyMembers;
    private int minutesInQueue;
    private Order order;
    private int totalMinutesWhileSeated;
    private int timeLeft = Integer.MAX_VALUE; // initializing to random value so that it is not 0

    public Party(List<Person> partyMembers) {
        this.partyMembers = partyMembers;
    }

    public List<Person> getPartyMembers() {
        return partyMembers;
    }

    public boolean orderAppetizer() {
        return RestaurantUtility.weightedCoinFlip(Config.PROBABILITY_APPETIZER_ORDERED);
    }

    public boolean orderDessert() {
        return RestaurantUtility.weightedCoinFlip(Config.PROBABILITY_DESSERT_ORDERED);
    }

    /**
     * For the entire party, generates the order.
     * Using this, we can determine the time that the party is seated.
     */
    public Order generateOrder(Menu menu) {
        this.order = new Order();
        if (this.orderAppetizer()) {
            order.setAppetizer(RestaurantUtility.pickItemFromMap(menu.getAppetizers()));
        }
        for (Person partyMember : partyMembers) {
            order.addMain(partyMember.generateMainCourse(menu));
        }
        if (this.orderDessert()) {
            order.setDessert(RestaurantUtility.pickItemFromMap(menu.getDesserts()));
        }
        this.totalMinutesWhileSeated = this.order.totalTimeToCompleteOrder();
        this.timeLeft = this.totalMinutesWhileSeated;
        return order;
    }

    public Order getOrder() {
        return order;
    }

    public int getMinutesInQueue() {
        return minutesInQueue;
    }

    public int getTotalMinutesWhileSeated() {
        return totalMinutesWhileSeated;
    }

    /**
     * Use this method to continue to track time the party is in the queue.
     * Every minute they are not seated, this will be incremented
     */
    public void stillInQueue() {
        this.minutesInQueue += 1;
    }

    /**
     * Use this method to track every minute as the party makes it through their order
     */
    public void decrementTimeLeftToCompletion() {
        this.timeLeft -= 1;
    }

    /**
     * This will return true when the party is ready to leave the table
     */
    public boolean isDone() {
        return this.timeLeft == 0;
    }

}
