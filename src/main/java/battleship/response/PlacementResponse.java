package battleship.response;

/**
 * If !isPlacedSuccessfully, then response will have a message to display to user
 */
public class PlacementResponse {

    private boolean isPlacedSuccessfully;
    private String message;

    public PlacementResponse setPlacedSuccessfully() {
        isPlacedSuccessfully = true;
        return this;
    }

    public PlacementResponse setPlacedUnsuccessfully(String message) {
        isPlacedSuccessfully = false;
        this.message = message;
        return this;
    }

    public boolean isPlacedSuccessfully() {
        return isPlacedSuccessfully;
    }

    public String getMessage() {
        return message;
    }
}
