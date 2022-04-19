package battleship.response;

import battleship.constants.EShipType;

/**
 * This class is used to represent the response.
 * If !isValidMove, then use "message" to explain why.
 * Otherwise, use shipType to determine which ship was hit.
 */
public class HitResponse {

    private EShipType shipType;
    private boolean isValidMove;
    private String message;

    public HitResponse setShipType(EShipType shipType) {
        this.isValidMove = true;
        this.shipType = shipType;
        return this;
    }

    public HitResponse setInvalidMove() {
        isValidMove = false;
        this.message = "Coordinates were either out of range or already chosen before";
        return this;
    }

    public EShipType getShipType() {
        return shipType;
    }

    public boolean isValidMove() {
        return isValidMove;
    }

    public String getMessage() {
        return message;
    }
}
