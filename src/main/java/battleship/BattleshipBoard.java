package battleship;

import battleship.constants.ELetterToCoordinate;
import battleship.constants.EOrientation;
import battleship.constants.EShipType;
import battleship.constants.EStringConstants;
import battleship.response.HitResponse;
import battleship.response.PlacementResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class BattleshipBoard {

    private String[][] board;
    private Set<EShipType> shipsOnBoard;
    Map<EShipType, int[][]> shipToCoordinates;

    public BattleshipBoard() {
        this.board = new String[10][10];
        this.shipsOnBoard = new HashSet<>();
        this.shipToCoordinates = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = EStringConstants.WATER.getPrintedVal();
            }
        }
    }

    public void setUpRandomShips() {
        Set<EShipType> shipsToPlace = getShipsStillToPlace();
        for (EShipType shipType : shipsToPlace) {
            String coordinate = generateRandomCoordinate();
            EOrientation orientation = generateRandomOrientation();
            PlacementResponse response = placeShip(shipType, coordinate, orientation);
            while (!response.isPlacedSuccessfully()) {
                coordinate = generateRandomCoordinate();
                orientation = generateRandomOrientation();
                response = placeShip(shipType, coordinate, orientation);
            }
        }
    }

    private String generateRandomCoordinate() {
        List<ELetterToCoordinate> coordinates = ELetterToCoordinate.getValues();
        int randomColumn = ThreadLocalRandom.current().nextInt(0, coordinates.size());
        ELetterToCoordinate randomLetter = coordinates.get(randomColumn);
        int randomRow = ThreadLocalRandom.current().nextInt(0, 10);
        return randomLetter.getLetter() + randomRow;
    }

    private EOrientation generateRandomOrientation() {
        int randomOrientation = ThreadLocalRandom.current().nextInt(0, 2);
        return randomOrientation == 0 ? EOrientation.HORIZONTAL : EOrientation.VERTICAL;
    }

    public String printBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        Arrays.stream(ELetterToCoordinate.values()).forEach(letter -> sb.append(" ").append(letter.getLetter()).append(" "));
        sb.append("\n");
        for (int i = 0; i < 10; i++) {
            sb.append(i).append(" ");
            for (int j = 0; j < 10; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String printOpponentBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        Arrays.stream(ELetterToCoordinate.values()).forEach(letter -> sb.append(" ").append(letter.getLetter()).append(" "));
        sb.append("\n");
        for (int i = 0; i < 10; i++) {
            sb.append(i).append(" ");
            for (int j = 0; j < 10; j++) {
                if (EStringConstants.getTypeFromPrintedVal(board[i][j]) == EStringConstants.SHIP) {
                    sb.append(EStringConstants.WATER.getPrintedVal());
                } else {
                    sb.append(board[i][j]);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Print a string with the player board on the left and the opponent board on the right
     * @param opponentBoard the board the player is playing against
     */
    public String printTwoBoards(BattleshipBoard opponentBoard) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.repeat(" ", 11))
                .append("Your board")
                .append(StringUtils.repeat(" ", 12))
                .append(StringUtils.repeat(" ", 10))
                .append(StringUtils.repeat(" ", 10))
                .append("Opponent board")
                .append("\n");
        sb.append("  ");
        Arrays.stream(ELetterToCoordinate.values()).forEach(letter -> sb.append(" ").append(letter.getLetter()).append(" "));
        sb.append(StringUtils.repeat(" ", 10));
        Arrays.stream(ELetterToCoordinate.values()).forEach(letter -> sb.append(" ").append(letter.getLetter()).append(" "));
        sb.append("\n");
        for (int i = 0; i < 10; i++) {
            sb.append(i).append(" ");
            for (int j = 0; j < 10; j++) {
                sb.append(board[i][j]);
            }
            sb.append(StringUtils.repeat(" ", 10));
            sb.append(i).append(" ");
            for (int j = 0; j < 10; j++) {
                if (EStringConstants.getTypeFromPrintedVal(opponentBoard.getBoard()[i][j]) == EStringConstants.SHIP) {
                    sb.append(EStringConstants.WATER.getPrintedVal());
                } else {
                    sb.append(opponentBoard.getBoard()[i][j]);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    String[][] getBoard() {
        return board;
    }


    /**
     * Places a ship on the board
     * @param shipType the type of ship to place
     * @param topLeftCoordinate the top left coordinate of the ship.
     * @param direction Either horizontal or vertical
     * @return true if the ship was placed successfully, false otherwise
     */
    public PlacementResponse placeShip(EShipType shipType, String topLeftCoordinate, EOrientation direction) {
        if (shipsOnBoard.contains(shipType)) {
            return new PlacementResponse().setPlacedUnsuccessfully("The ship attempted to be placed is already on the board");
        }
        int[] coordinateArray = getCoordinateFromString(topLeftCoordinate);
        int [][] coordinatesToCheck = new int[shipType.getSize()][2]; // [[0, 1], [1, 1], [2, 1]] something like this for where to check
        // determine which coordinates to check based on the orientation and starting point
        if (direction == EOrientation.HORIZONTAL) {
            for (int i = 0; i < shipType.getSize(); i++) {
                coordinatesToCheck[i][0] = coordinateArray[0];
                coordinatesToCheck[i][1] = coordinateArray[1] + i;
            }
        } else {
            for (int i = 0; i < shipType.getSize(); i++) {
                coordinatesToCheck[i][0] = coordinateArray[0] + i;
                coordinatesToCheck[i][1] = coordinateArray[1];
            }
        }
        // check if all coordinates are valid
        for (int[] coordinate : coordinatesToCheck) {
            if (!isCoordinateInBounds(coordinate) || getTypeOfCoordinate(coordinate) != EStringConstants.WATER) {
                return new PlacementResponse().setPlacedUnsuccessfully("Coordinates were either out of range, or not water");
            }
        }
        // place ship
        for (int[] coordinate : coordinatesToCheck) {
            board[coordinate[0]][coordinate[1]] = EStringConstants.SHIP.getPrintedVal();
        }
        this.shipsOnBoard.add(shipType);
        this.shipToCoordinates.put(shipType, coordinatesToCheck);
        return new PlacementResponse().setPlacedSuccessfully();
    }

    /**
     * Checks if the coordinate is valid. If a valid coordinate,
     * @param coordinate
     * @return
     */
    public HitResponse attemptToHit(String coordinate) {
        int[] coordinateArray = getCoordinateFromString(coordinate);
        if (isCoordinateInBounds(coordinateArray) && !isAlreadyChosenBefore(coordinateArray)) {
            if (getTypeOfCoordinate(coordinateArray) == EStringConstants.WATER) {
                board[coordinateArray[0]][coordinateArray[1]] = EStringConstants.MISS.getPrintedVal();
                return new HitResponse().setShipType(EShipType.NONE);
            } else if (getTypeOfCoordinate(coordinateArray) == EStringConstants.SHIP) {
                EShipType shipType = determineShipType(coordinateArray);
                board[coordinateArray[0]][coordinateArray[1]] = EStringConstants.HIT.getPrintedVal();
                return new HitResponse().setShipType(shipType);
            }
        }
        return new HitResponse().setInvalidMove();
    }

    public Set<EShipType> getShipsOnBoard() {
        return shipsOnBoard;
    }

    public Set<EShipType> getShipsStillToPlace() {
        return Arrays.stream(EShipType.values())
                .filter(shipType -> shipType != EShipType.NONE)
                .filter(shipType -> !shipsOnBoard.contains(shipType))
                .collect(Collectors.toSet());
    }

    /**
     * Given coordinate chosen, if coordinate is already been HIT or MISS, return true
     */
    public boolean isAlreadyChosenBefore(int[] coordinate) {
        String printedVal = board[coordinate[0]][coordinate[1]];
        return EStringConstants.getTypeFromPrintedVal(printedVal) == EStringConstants.HIT ||
                EStringConstants.getTypeFromPrintedVal(printedVal) == EStringConstants.MISS;
    }

    private boolean isCoordinateInBounds(int[] coordinate) {
        return coordinate[0] >= 0 && coordinate[0] <= 9 && coordinate[1] >= 0 && coordinate[1] <= 9;
    }

    private EStringConstants getTypeOfCoordinate(int[] coordinate) {
        String boardValue = board[coordinate[0]][coordinate[1]];
        return EStringConstants.getTypeFromPrintedVal(boardValue);
    }

    /**
     * If user types something like d1, this refers to index [1][3] in the battleship board
     * @param coordinate human readable coordinate
     * @return int[] where [0] is the row and [1] is the column
     */
    private int[] getCoordinateFromString(String coordinate) {
        int[] coordinateArray = new int[2];
        coordinateArray[0] = Integer.parseInt(coordinate.substring(1));
        coordinateArray[1] = ELetterToCoordinate.getColumnFromLetter(coordinate.substring(0, 1));
        return coordinateArray;
    }

    public static String getStringFromCoordinate(int row, int column) {
        return ELetterToCoordinate.getLetterFromColumn(column) + row;
    }

    private EShipType determineShipType(int[] coordinate) {
        String boardValue = board[coordinate[0]][coordinate[1]];
        if (EStringConstants.getTypeFromPrintedVal(boardValue) == EStringConstants.SHIP) {
            for (Map.Entry<EShipType, int[][]> entry : shipToCoordinates.entrySet()) {
                int[][] coordinates = entry.getValue();
                // if any of the coordinates match above coordinate, return ship type
                for (int[] coordinateToCheck : coordinates) {
                    if (coordinate[0] == coordinateToCheck[0] && coordinate[1] == coordinateToCheck[1]) {
                        return entry.getKey();
                    }
                }
            }
        }
        return EShipType.NONE;
    }

    /**
     * If ship is determined to have been hit, check if it has sunk
     * @param shipType ship type that was hit
     */
    public boolean isShipSunk(EShipType shipType) {
        int[][] coordinatesOfShip = shipToCoordinates.get(shipType);
        for (int[] coordinate : coordinatesOfShip) {
            if (getTypeOfCoordinate(coordinate) == EStringConstants.SHIP) {
                return false;
            }
        }
        return true;
    }

    public void removeSunkenShip(EShipType shipType) {
        shipsOnBoard.remove(shipType);
    }

    public boolean isGameOver() {
        return shipsOnBoard.size() == 0;
    }



    public static void main(String[] args) {
        BattleshipBoard opponentBoard = new BattleshipBoard();
        opponentBoard.initializeBoard();
        BattleshipBoard playerBoard = new BattleshipBoard();
        playerBoard.initializeBoard();
        System.out.println(playerBoard.printTwoBoards(opponentBoard));


//        opponentBoard.setUpRandomShips();
//        System.out.println("All ships placed. Now it's time to go into battle!");
//        System.out.println(opponentBoard.printOpponentBoard());
//        while (!opponentBoard.isGameOver()) {
//            System.out.println("Enter a coordinate to hit: ");
//            Scanner in = new Scanner(System.in);
//            String coordinate = in.nextLine();
//            HitResponse hitResponse = opponentBoard.attemptToHit(coordinate);
//            if (hitResponse.isValidMove()) {
//                if (hitResponse.getShipType() != EShipType.NONE) {
//                    System.out.println("Hit a " + hitResponse.getShipType());
//                    boolean isShipSunk = opponentBoard.isShipSunk(hitResponse.getShipType());
//                    if (isShipSunk) {
//                        opponentBoard.removeSunkenShip(hitResponse.getShipType());
//                        System.out.println("Sunk a " + hitResponse.getShipType());
//                    }
//                } else {
//                    System.out.println("You missed");
//                }
//            } else {
//                System.out.println(hitResponse.getMessage());
//            }
//            System.out.println(opponentBoard.printOpponentBoard());
//        }




//        bb.placeShip(EShipType.BATTLESHIP, "d1", EOrientation.VERTICAL);
//        bb.placeShip(EShipType.DESTROYER, "i0", EOrientation.HORIZONTAL);
//        shipsToPlace = bb.getShipsStillToPlace();
//        shipsToPlace.forEach(ship -> System.out.print(ship + "(" + ship.getSize() +  "), "));
//        System.out.println(bb.printBoard());
//        List<String> coordinatesToGuess = Arrays.asList("d1", "d1", "a10", "e1", "d2", "d3", "d4");
//        for (String coordinate : coordinatesToGuess) {
//            HitResponse hitResponse = bb.attemptToHit(coordinate);
//            if (hitResponse.isValidMove()) {
//                if (hitResponse.getShipType() != EShipType.NONE) {
//                    System.out.println("Hit a " + hitResponse.getShipType());
//                    boolean isShipSunk = bb.isShipSunk(hitResponse.getShipType());
//                    if (isShipSunk) {
//                        bb.removeSunkenShip(hitResponse.getShipType());
//                        System.out.println("Sunk a " + hitResponse.getShipType());
//                    }
//                } else {
//                    System.out.println("You missed");
//                }
//            } else {
//                System.out.println(hitResponse.getMessage());
//            }
//            System.out.println(bb.printOpponentBoard());
//        }
    }

}
