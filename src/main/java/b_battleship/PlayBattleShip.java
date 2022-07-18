package b_battleship;

import b_battleship.constants.EAnsiColor;
import b_battleship.constants.EOrientation;
import b_battleship.constants.EShipType;
import b_battleship.response.HitResponse;
import b_battleship.response.PlacementResponse;

import java.util.Scanner;
import java.util.Set;

public class PlayBattleShip {

    private static void promptPlayerToSetUpBoard(BattleshipBoard board) {
        System.out.println("We will start by placing ships. You will be prompted to enter ship name, then top left coordinate " +
                "of ship, and then orientation. For example, if you place \"DESTROYER\" at \"d1\" with orientation \"H\", " +
                "it will occupy \"d1\" and \"e1\".");
        while (!board.getShipsStillToPlace().isEmpty()) {
            Scanner in = new Scanner(System.in);
            Set<EShipType> shipsToPlace = board.getShipsStillToPlace();
            shipsToPlace.forEach(ship -> System.out.print(ship + "(" + ship.getSize() +  "), "));
            System.out.println();
            try {
                System.out.println("Enter ship you would like to place: ");
                String shipName = in.nextLine().toUpperCase();
                System.out.println("Enter top left coordinate of ship: ");
                String topLeftCoordinate = in.nextLine().toLowerCase();
                System.out.println("Enter orientation of ship (either H or V): ");
                String orientation = in.nextLine().toUpperCase();
                PlacementResponse response = board.placeShip(EShipType.valueOf(shipName), topLeftCoordinate, EOrientation.getOrientationFromKey(orientation));
                if (response.isPlacedSuccessfully()) {
                    System.out.println("Ship placed successfully!");
                } else {
                    System.out.println("Ship placement failed. Reason: " + response.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Failed to place ship. Reason: " + e.getMessage());
            }
            System.out.println(board.printBoard());
        }
    }

    private static void aiAttemptToHit(BattleshipBoard opponentBoard, BattleShipAI ai) {
        String coordinate = ai.generateNextMove();
        System.out.println("Now it's the computer's turn. It chose: " + coordinate);
        HitResponse response = opponentBoard.attemptToHit(coordinate);
        ai.notifyAiOfResult(coordinate, response);
        if (response.getShipType() == EShipType.NONE) {
            System.out.println("The AI missed!");
        } else {
            printInColor("The AI hit your " + response.getShipType() + "!", EAnsiColor.RED);
            boolean isShipSunk = opponentBoard.isShipSunk(response.getShipType());
            if (isShipSunk) {
                opponentBoard.removeSunkenShip(response.getShipType());
                printInColor("The AI sunk your " + response.getShipType() + "!", EAnsiColor.RED);
            }
        }
    }

    private static void playerAttemptToHit(BattleshipBoard opponentBoard, Scanner scanner) {
        System.out.println("Enter a coordinate to hit: ");
        String coordinate = scanner.nextLine().toLowerCase();
        HitResponse hitResponse = opponentBoard.attemptToHit(coordinate);
        // player attempts to hit computer board, keep prompting until valid coordinate is entered
        while (!hitResponse.isValidMove()) {
            System.out.println(hitResponse.getMessage());
            System.out.println("Enter a coordinate to hit: ");
            coordinate = scanner.nextLine().toLowerCase();
            hitResponse = opponentBoard.attemptToHit(coordinate);
        }
        if (hitResponse.getShipType() != EShipType.NONE) {
            printInColor("Hit a " + hitResponse.getShipType(), EAnsiColor.GREEN);
            boolean isShipSunk = opponentBoard.isShipSunk(hitResponse.getShipType());
            if (isShipSunk) {
                opponentBoard.removeSunkenShip(hitResponse.getShipType());
                printInColor("Sunk a " + hitResponse.getShipType(), EAnsiColor.MAGENTA);
            }
        } else {
            System.out.println("You missed");
        }
    }

    private static void printInColor(String message, EAnsiColor color) {
        System.out.println(color.getCode() + message + EAnsiColor.RESET.getCode());
    }

    public static void main(String[] args) {
        BattleshipBoard playerBoard = new BattleshipBoard();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Battleship! Would you like to place ships manually or automatically (m/a)?");
        String input = scanner.nextLine();
        if (input.equals("a")) {
            playerBoard.setUpRandomShips();
        } else {
            promptPlayerToSetUpBoard(playerBoard);
        }

        // set up ai board
        BattleshipBoard computerBoard = new BattleshipBoard();
        computerBoard.setUpRandomShips();
        BattleShipAI ai = new BattleShipAI(playerBoard);

        System.out.println("All ships placed! It's time to go into battle! You go first.");
        System.out.println(playerBoard.printTwoBoards(computerBoard));

        while (!playerBoard.isGameOver() && !computerBoard.isGameOver()) {
            playerAttemptToHit(computerBoard, scanner);
            // check to ensure computer board is not game over
            if (computerBoard.isGameOver())
                break;
            // let the computer attempt to hit the player board
            aiAttemptToHit(playerBoard, ai);
            System.out.println(playerBoard.printTwoBoards(computerBoard));
        }

        if (playerBoard.isGameOver()) {
            System.out.println("You lost!");
        } else {
            System.out.println("You won!");
        }


    }


}
