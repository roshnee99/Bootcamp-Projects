package c_restaurant.bean;

import java.util.*;
import java.util.stream.Collectors;

public class Restaurant {

    private List<Table> tables;
    private Menu menu;
    /**
     * We have a separate queue depending on which table type they are waiting for
     */
    private Map<ETableType, Queue<Party>> partiesInQueue;

    public Restaurant(List<Table> tables, Menu menu) {
        this.tables = tables;
        this.menu = menu;
        this.partiesInQueue = new HashMap<>();
    }

    public void addParty(Party party) {
        ETableType tableType = ETableType.getTableTypeForParty(party.getPartyMembers().size());
        // add the party to the queue
        if (!this.partiesInQueue.containsKey(tableType)) {
            this.partiesInQueue.put(tableType, new ArrayDeque<>());
        }
        this.partiesInQueue.get(tableType).add(party);
    }

    public void incrementPartyWaitTime() {
        for (Queue<Party> queue : partiesInQueue.values()) {
            for (Party party : queue) {
                party.stillInQueue();
            }
        }
    }

    /**
     * Grab the next available person in the appropriate queue for that table.
     * If the table
     * @param tableType
     * @return
     */
    public Optional<Party> getNextParty(ETableType tableType) {
        return Optional.ofNullable(this.partiesInQueue.getOrDefault(tableType, new ArrayDeque<>()).poll());
    }

    public List<Table> getUnoccupiedTables() {
        return this.tables.stream().filter(x -> !x.isTaken()).collect(Collectors.toList());
    }

    public List<Table> getOccupiedTables() {
        return this.tables.stream().filter(Table::isTaken).collect(Collectors.toList());
    }

    public Menu getMenu() {
        return menu;
    }
}
