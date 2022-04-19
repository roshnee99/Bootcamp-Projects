package battleship.constants;

public enum EShipType {

    CARRIER(5),
    BATTLESHIP(4),
    CRUISER(3),
    SUBMARINE(3),
    DESTROYER(2),
    NONE(0);

    private int size;

    EShipType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
