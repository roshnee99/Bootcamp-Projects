package c_restaurant.bean;

public class Table {

    private ETableType tableType;
    private Party party;

    public Table(ETableType tableType) {
        this.tableType = tableType;
    }

    public void seatParty(Party party) {
        this.party = party;
    }

    public void unseatParty() {
        this.party = null;
    }

    public ETableType getTableType() {
        return tableType;
    }

    public boolean isTaken() {
        return this.party != null;
    }

    public Party getParty() {
        return party;
    }
}
