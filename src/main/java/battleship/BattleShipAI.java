package battleship;

import battleship.constants.ELetterToCoordinate;
import battleship.constants.EOrientation;
import battleship.constants.EShipType;
import battleship.constants.EStringConstants;
import battleship.data_structure.RandomHashSet;
import battleship.response.HitResponse;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class BattleShipAI {

    private Map<EShipType, List<String>> lastHitCoordinatesPerShipType;
    private Set<String> allPossibleCoordinates;
    private Map<EShipType, Set<String>> onHitPossibleCoordinates;
    private final BattleshipBoard opponentBoard;

    public BattleShipAI(BattleshipBoard opponentBoard) {
        this.opponentBoard = opponentBoard;
        initializeOnHitPossibleCoordinates();
        initializeAllPossibleCoordinates();
        initializeLastHitCoordinatesPerShipType();
    }

    private void initializeAllPossibleCoordinates() {
        this.allPossibleCoordinates = new RandomHashSet<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String letter = ELetterToCoordinate.getLetterFromColumn(j);
                String coordinate = letter + i;
                allPossibleCoordinates.add(coordinate);
            }
        }
    }

    private void initializeLastHitCoordinatesPerShipType() {
        this.lastHitCoordinatesPerShipType = new HashMap<>();
        Set<EShipType> shipsOnBoard = this.opponentBoard.getShipsOnBoard();
        for (EShipType shipType : shipsOnBoard) {
            lastHitCoordinatesPerShipType.put(shipType, new ArrayList<>());
        }
    }

    private void initializeOnHitPossibleCoordinates() {
        this.onHitPossibleCoordinates = new HashMap<>();
        Set<EShipType> shipsOnBoard = this.opponentBoard.getShipsOnBoard();
        for (EShipType shipType : shipsOnBoard) {
            onHitPossibleCoordinates.put(shipType, new RandomHashSet<>());
        }
    }

    public String generateNextMove() {
        if (!isPossibleShipToTarget()) {
            String coordinate = ((RandomHashSet<String>) allPossibleCoordinates).getRandomElement();
            allPossibleCoordinates.remove(coordinate); // remove the coordinate from the set of all possible coordinates
            return coordinate;
        }
        EShipType randomShipToTarget = generateRandomShipToTarget();
        RandomHashSet<String> possibleCoordinatesOfShip = (RandomHashSet<String>) onHitPossibleCoordinates.get(randomShipToTarget);
        String coordinate = possibleCoordinatesOfShip.getRandomElement();
        this.onHitPossibleCoordinates.get(randomShipToTarget).remove(coordinate); // remove the coordinate from the set of possible coordinates to choose from
        allPossibleCoordinates.remove(coordinate); // remove the coordinate from the set of all possible coordinates
        return coordinate;
    }

    public void notifyAiOfResult(String coordinate, HitResponse hitResponse) {
        if (hitResponse.getShipType() != EShipType.NONE) { // if hit a ship
            // base case - check if ship is sunk
            if (opponentBoard.isShipSunk(hitResponse.getShipType())) {
                this.onHitPossibleCoordinates.remove(hitResponse.getShipType()); // remove all on-hit possible coordinates
                return;
            }
            // fill in possible coordinates given state
            List<String> lastHitCoordinate = lastHitCoordinatesPerShipType.get(hitResponse.getShipType());
            lastHitCoordinate.add(coordinate);
            if (lastHitCoordinate.size() == 1) {
                onHitPossibleCoordinates.get(hitResponse.getShipType()).addAll(generateRandomAdjacentCoordinates(coordinate));
                // remove all on hit possible coordinates that are not in all possible coordinates
                onHitPossibleCoordinates.get(hitResponse.getShipType()).retainAll(allPossibleCoordinates);
            } else {
                // determine direction of the possible coordinates (H or V?)
                EOrientation orientation = getOrientationOfShipHit(lastHitCoordinate);
                // add adjacent coordinates depending on orientation
                addShipSpecificCoordinates(orientation, lastHitCoordinate, hitResponse.getShipType());
                // remove all on hit possible coordinates that are not in all possible coordinates
                onHitPossibleCoordinates.get(hitResponse.getShipType()).retainAll(allPossibleCoordinates);
            }
        }
        // remove all impossible spots from the set of all possible coordinates
        removeImpossibleSpots();
    }

    private EOrientation getOrientationOfShipHit(List<String> lastHitCoordinate) {
        char columnOfFirstHit = lastHitCoordinate.get(0).charAt(0);
        char columnOfSecondHit = lastHitCoordinate.get(1).charAt(0);
        if (columnOfFirstHit == columnOfSecondHit) {
            return EOrientation.VERTICAL;
        }
        return EOrientation.HORIZONTAL;
    }

    private void addShipSpecificCoordinates(EOrientation orientation, List<String> lastHitCoordinate, EShipType shipHit) {
        this.onHitPossibleCoordinates.get(shipHit).clear();
        if (orientation == EOrientation.HORIZONTAL) {
            for (String coordinate : lastHitCoordinate) {
                List<String> horizontalCoordinates = generateHorizontallyAdjacentCoordinates(coordinate);
                this.onHitPossibleCoordinates.get(shipHit).addAll(horizontalCoordinates);
            }
        } else {
            for (String coordinate : lastHitCoordinate) {
                List<String> verticalCoordinates = generateVerticallyAdjacentCoordinates(coordinate);
                this.onHitPossibleCoordinates.get(shipHit).addAll(verticalCoordinates);
            }
        }
    }

    private List<String> generateRandomAdjacentCoordinates(String lastHitCoordinate) {
        List<String> possibleCoordinates = new ArrayList<>();
        possibleCoordinates.addAll(generateHorizontallyAdjacentCoordinates(lastHitCoordinate));
        possibleCoordinates.addAll(generateVerticallyAdjacentCoordinates(lastHitCoordinate));
        return possibleCoordinates;
    }

    private List<String> generateHorizontallyAdjacentCoordinates(String lastHitCoordinate) {
        List<String> possibleCoordinates = new ArrayList<>();
        int column = ELetterToCoordinate.getColumnFromLetter(String.valueOf(lastHitCoordinate.charAt(0)));
        int row = Integer.parseInt(String.valueOf(lastHitCoordinate.charAt(1)));
        int leftColumn = column - 1;
        int rightColumn = column + 1;
        if (leftColumn >= 0 && leftColumn <= 9) {
            String letter = ELetterToCoordinate.getLetterFromColumn(leftColumn);
            possibleCoordinates.add(letter + row);
        }
        if (rightColumn >= 0 && rightColumn <= 9) {
            String letter = ELetterToCoordinate.getLetterFromColumn(rightColumn);
            possibleCoordinates.add(letter + row);
        }
        return possibleCoordinates;
    }

    private List<String> generateVerticallyAdjacentCoordinates(String lastHitCoordinate) {
        List<String> possibleCoordinates = new ArrayList<>();
        int row = Integer.parseInt(String.valueOf(lastHitCoordinate.charAt(1)));
        int topRow = row - 1;
        int bottomRow = row + 1;
        if (topRow >= 0 && topRow <= 9) {
            possibleCoordinates.add(String.valueOf(lastHitCoordinate.charAt(0)) + topRow);
        }
        if (bottomRow >= 0 && bottomRow <= 9) {
            possibleCoordinates.add(String.valueOf(lastHitCoordinate.charAt(0)) + bottomRow);
        }
        return possibleCoordinates;
    }

    private boolean isPossibleShipToTarget() {
        for (Map.Entry<EShipType, Set<String>> e : this.onHitPossibleCoordinates.entrySet()) {
            if (!e.getValue().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private EShipType generateRandomShipToTarget() {
        List<EShipType> shipTypes = new ArrayList<>();
        for (Map.Entry<EShipType, Set<String>> e : this.onHitPossibleCoordinates.entrySet()) {
            if (!e.getValue().isEmpty()) {
                shipTypes.add(e.getKey());
            }
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(0, shipTypes.size());
        return shipTypes.get(randomIndex);
    }

    private void removeImpossibleSpots() {
        Set<EShipType> shipsOnBoard = this.opponentBoard.getShipsOnBoard();
        int minShipLength = shipsOnBoard.stream().map(EShipType::getSize).min(Integer::compareTo).get();
        // given a start position, determine if there is enough water to place a ship of the given size
        String[][] board = this.opponentBoard.getBoard();
        Set<String> possibleCoordinates = new HashSet<>();
        // given a coordinate, find if ship size of minShipLength can be placed there
        addPossibleCoordinates(minShipLength, board, possibleCoordinates, EOrientation.HORIZONTAL);
        addPossibleCoordinates(minShipLength, board, possibleCoordinates, EOrientation.VERTICAL);
        this.allPossibleCoordinates.retainAll(possibleCoordinates);
        for (Map.Entry<EShipType, Set<String>> e : this.onHitPossibleCoordinates.entrySet()) {
            e.getValue().retainAll(this.allPossibleCoordinates);
        }
    }

    private void addPossibleCoordinates(int minShipLength, String[][] board, Set<String> possibleCoordinates, EOrientation orientation) {
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!visited[i][j] && isPossibleForShipToBeThere(board[i][j])) {
                    visited[i][j] = true;
                    Set<Pair<Integer, Integer>> coordinatesWithPotential = new HashSet<>();
                    coordinatesWithPotential.add(Pair.of(i, j));
                    Stack<Pair<Integer, Integer>> coordinatesToCheck = new Stack<>();
                    coordinatesToCheck.addAll(getAdjacentCoordinates(orientation, i, j));
                    while (!coordinatesToCheck.isEmpty()) {
                        Pair<Integer, Integer> coordinate = coordinatesToCheck.pop();
                        if (!visited[coordinate.getLeft()][coordinate.getRight()]) {
                            visited[coordinate.getLeft()][coordinate.getRight()] = true;
                            if (isPossibleForShipToBeThere(board[coordinate.getKey()][coordinate.getValue()])) {
                                coordinatesWithPotential.add(coordinate);
                                coordinatesToCheck.addAll(getAdjacentCoordinates(orientation, coordinate.getKey(), coordinate.getValue()));
                            }
                        }
                    }
                    if (coordinatesWithPotential.size() >= minShipLength) {
                        coordinatesWithPotential.stream()
                                .map(x -> BattleshipBoard.getStringFromCoordinate(x.getLeft(), x.getRight()))
                                .forEach(possibleCoordinates::add);
                    }
                }
            }
        }
    }

    private List<Pair<Integer, Integer>> getAdjacentCoordinates(EOrientation orientation, int row, int column) {
        List<Pair<Integer, Integer>> adjacentCoordinates = new ArrayList<>();
        if (orientation == EOrientation.HORIZONTAL) {
            int toLeft = row - 1;
            int toRight = row + 1;
            if (toLeft >= 0 && toLeft <= 9) {
                adjacentCoordinates.add(Pair.of(toLeft, column));
            }
            if (toRight >= 0 && toRight <= 9) {
                adjacentCoordinates.add(Pair.of(toRight, column));
            }
        } else {
            int toTop = column - 1;
            int toBottom = column + 1;
            if (toTop >= 0 && toTop <= 9) {
                adjacentCoordinates.add(Pair.of(row, toTop));
            }
            if (toBottom >= 0 && toBottom <= 9) {
                adjacentCoordinates.add(Pair.of(row, toBottom));
            }
        }
        return adjacentCoordinates;
    }

    private boolean isPossibleForShipToBeThere(String printedVal) {
        EStringConstants value = EStringConstants.getTypeFromPrintedVal(printedVal);
        return value == EStringConstants.SHIP || value == EStringConstants.WATER || value == EStringConstants.HIT;
    }

    public static void main(String[] args) {
        BattleshipBoard board = new BattleshipBoard();
        board.setUpRandomShips();
        BattleShipAI ai = new BattleShipAI(board);

        while (!board.isGameOver()) {
            Scanner in = new Scanner(System.in);
            String coordinate = ai.generateNextMove();
            System.out.println("Enter a coordinate to fire at: ");
            System.out.println("AI chose: " + coordinate + ". Press enter to continue.");
            in.nextLine();
            HitResponse response = board.attemptToHit(coordinate);
            ai.notifyAiOfResult(coordinate, response);
            if (response.isValidMove()) {
                if (response.getShipType() != EShipType.NONE) {
                    System.out.println("Hit a " + response.getShipType());
                    boolean isShipSunk = board.isShipSunk(response.getShipType());
                    if (isShipSunk) {
                        board.removeSunkenShip(response.getShipType());
                        System.out.println("Sunk a " + response.getShipType());
                    }
                } else {
                    System.out.println("You missed");
                }
            } else {
                System.out.println(response.getMessage());
            }
            System.out.println(board.printBoard());
        }
    }




}
