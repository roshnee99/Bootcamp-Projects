package c_restaurant.main;

import c_restaurant.bean.*;

import java.util.List;
import java.util.Optional;

public class RestaurantMainLogging {

    public static void main(String[] args) throws InterruptedException {
        List<Table> tables = TableListGenerator.generateTables(12, 10, 8);
        Menu menu = MenuGenerator.generateMenu();
        Restaurant restaurant = new Restaurant(tables, menu);

        for (int i = 0; i < 120; i++) {
            System.out.println("Incrementing time for parties currently in queue");
            restaurant.incrementPartyWaitTime();
            System.out.println("Checking if parties enter restaurant");
            Optional<Party> newParty = PartyGenerator.generateParty();
            if (newParty.isPresent()) {
                System.out.println("A new customer! Of size: " + newParty.get().getPartyMembers().size());
                restaurant.addParty(newParty.get());
            }
            System.out.println("Decrementing the time for all parties currently seated");
            List<Table> occupiedTables = restaurant.getOccupiedTables();
            for (Table table : occupiedTables) {
                table.getParty().decrementTimeLeftToCompletion();
                System.out.println("Checking if the party is done eating");
                if (table.getParty().isDone()) {
                    System.out.println("Party is done! Unseating from table");
                    table.unseatParty();
                }
            }
            System.out.println("Seeing if any party can be seated");
            List<Table> unoccupiedTables = restaurant.getUnoccupiedTables();
            for (Table table : unoccupiedTables) {
                ETableType tableType = table.getTableType();
                Optional<Party> nextParty = restaurant.getNextParty(tableType);
                if (nextParty.isPresent()) {
                    System.out.println("A party can be seated!");
                    table.seatParty(nextParty.get());
                    System.out.println("The party is generated an order");
                    nextParty.get().generateOrder(restaurant.getMenu());
                }
            }
            System.out.println("On to the next minute");
            Thread.sleep(3000);
        }
    }

}
