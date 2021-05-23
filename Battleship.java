package battleship;

import java.util.*;

class Battleship {
    Scanner scan = new Scanner(System.in);
    protected int countShips;
    protected Battleship() { }
    protected GameField shipField = new GameField();
    protected GameField fogField = new GameField();

    /**
     * Method for placing all 5 ships. It progresses through the different
     * ship types automatically after a valid input has been made and a ship
     * was placed.
     */
    protected void placeShips() {
        switch (countShips) {
            case 0:
                System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
                parseCoordinates(5);
                shipField.print();
                countShips++;
            case 1:
                System.out.println("Enter the coordinates of the Battleship (4 cells):");
                parseCoordinates(4);
                shipField.print();
                countShips++;
            case 2:
                System.out.println("Enter the coordinates of the Submarine (3 cells):");
                parseCoordinates(3);
                shipField.print();
                countShips++;
            case 3:
                System.out.println("Enter the coordinates of the Cruiser (3 cells):");
                parseCoordinates(3);
                shipField.print();
                countShips++;
            case 4:
                System.out.println("Enter the coordinates of the Destroyer (2 cells):");
                parseCoordinates(2);
                shipField.print();
                countShips++;
        }
    }

    /**
     * In the big while loop the coordinates are being processed and if the
     * user input is valid, the direction of the ship is determined (vertical
     * or horizontal). There is also a lot of Error handling here: If one occurs,
     * the loop starts again and the user is being asked for new input.
     * @param shipSize size of the ship in cells.
     */
    private void parseCoordinates(int shipSize) {
        int pos1;
        int pos2;
        int pos3;
        int pos4;
        boolean parsingInput = true;
        boolean upright;
        boolean horizontal;
        while (parsingInput) {
            try {
                String[] coordinates = convertCoordinates(scan.nextLine().trim());
                pos1 = Integer.parseInt(coordinates[0]);
                pos2 = Integer.parseInt(coordinates[1]);
                pos3 = Integer.parseInt(coordinates[2]);
                pos4 = Integer.parseInt(coordinates[3]);
                if (pos2 < 0 || pos4 < 0 || pos2 > 10 ||  pos4 > 10) {
                    System.out.println("Error! Number too high or to low. Stay within the grid. Try again:");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Error! Incorrect input format. Try again:");
                continue;
            }

            try {
                upright = pos2 - pos4 == 0 &&
                        Math.abs(pos1 - pos3) == shipSize - 1;
                horizontal = pos1 == pos3 &&
                        Math.abs(pos2 - pos4) == shipSize - 1;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error! Incorrect characters or wrong size. Try again:");
                continue;
            }

            if (upright == horizontal) {
                System.out.println("Error! Invalid input. Try again:");
                continue;
            }

            int startingPoint;

            if (upright) {
                startingPoint = Math.min(pos1, pos3); // Vertical is at pos2 & pos4 (they are same value)
                if (!(checkProximityUpright(startingPoint, pos2, shipSize))) {
                    System.out.println("Error! You placed it too close or on top of another one. Try again:");
                    continue;
                }
                for (int i = 0; i < shipSize; i++) {
                    shipField.setCell(startingPoint + i, pos2, 'O');
                }
                parsingInput = false;
            }

            if (horizontal) {
                startingPoint = Math.min(pos2, pos4);
                if (!(checkProximityCross(startingPoint, pos1, shipSize))) {
                    System.out.println("Error! You placed it too close or on top of another one. Try again:");
                    continue;
                }
                for (int i = 0; i < shipSize; i++) {
                    shipField.setCell(pos1, startingPoint + i, 'O');
                }
                parsingInput = false;
            }
        }
    }

    /**
     * The user input is being converted to numbers only for ease of use.
     * @param rawCoordinates user input which is still containing letters.
     * @return returns a String[] with numbers in proper format which can be
     *         used to access arrays.
     */
    private String[] convertCoordinates(String rawCoordinates) {
        return rawCoordinates
                    .replace("A", "0 ").replace("B", "1 ")
                    .replace("C", "2 ").replace("D", "3 ")
                    .replace("E", "4 ").replace("F", "5 ")
                    .replace("G", "6 ").replace("H", "7 ")
                    .replace("I", "8 ").replace("J", "9 ").split("\\s");
    }

    /**
     * Used if the ship the user wants to place is in a vertical position.
     * Checks if the proximity is clear.
     * @param startingPoint startingPoint of the ship.
     * @param verticalAt this is the position where the vertical is located (number which occurs
     *                   twice in the coordinates).
     * @param shipSize size of the ship in cells.
     * @return returns false if the ship cant be placed because the proximity isn't clear.
     *         Error handling is done in the parseCoordinates method.
     *         Otherwise returns true.
     */
    private boolean checkProximityUpright(int startingPoint, int verticalAt, int shipSize) {
        try {
            for (int i = -1; i < shipSize + 1; i++) {
                if (shipField.getCharAtCell(startingPoint + i, verticalAt) == 'O' ||
                        shipField.getCharAtCell(startingPoint + i, verticalAt + 1) == 'O' ||
                        shipField.getCharAtCell(startingPoint + i, verticalAt -1) == 'O') {
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return true;
    }
    /**
     * Used if the ship the user wants to place is in a horizontal position.
     * Checks if the proximity is clear.
     * @param startingPoint startingPoint of the ship.
     * @param horizontalAt this is the position where the horizontal is located (number which occurs
     *                     twice in the coordinates).
     * @param shipSize size of the ship in cells.
     * @return returns false if the ship cant be placed because the proximity isn't clear.
     *         Error handling is done in the parseCoordinates method.
     *         Otherwise returns true.
     */
    private boolean checkProximityCross(int startingPoint, int horizontalAt, int shipSize) {
        try {
            for (int i = -1; i < shipSize + 1; i++) {
                if (shipField.getCharAtCell(horizontalAt, startingPoint + i) == 'O' ||
                       shipField.getCharAtCell(horizontalAt + 1, startingPoint + i) == 'O' ||
                        shipField.getCharAtCell(horizontalAt - 1, startingPoint + i) == 'O') {
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return true;
    }

    /**
     * Here we check if the input for the shooting-phase of the game aligns with a ship
     * that was placed before. If it doesn't, the shot is considered a miss.
     * If it does however, the shot is considered a hit and the method isShipSunk is called
     * to figure out if this shot caused a ship to sink.
     * If a ship was sunk, the output reflects that and asks the user to pick a new target.
     * Additionally the method checks after every shot if the win condition is met, in which
     * case the the output is being done my the method shootAllShips.
     */
    protected void shoot() {
        int pos1;
        int pos2;

        while (true) {
            String[] coordinates = convertCoordinates(scan.nextLine().trim());
            try {
                pos1 = Integer.parseInt(coordinates[0]);
                pos2 = Integer.parseInt(coordinates[1]);

                if (pos1 > 9 || pos2 > 10) {
                    System.out.println("Error! You entered the wrong coordinates. Try again:");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Error! You entered the wrong coordinates. Try again:");
            }
        }

        /*
          Evaluate if the user input was a hit.
          If the user shoots at a cell where he previously registered a hit again its
          considered a miss!
          "shot on already-shot coordinate" should not prompt a new shot.
         */
        if (shipField.getCharAtCell(pos1, pos2) == 'O') {
            shipField.setCell(pos1, pos2, 'X');
            fogField.setCell(pos1, pos2, 'X');
            if (!shipField.isGameWon()) {
                //Checks if the hit caused a ship to sink.
                if (isShipSunk(pos1, pos2)) {
                    System.out.println("You sank a ship!");
                } else {
                    System.out.println("You hit a ship!");
                }
            }
        } else {
            // Need to consider if the user shot at a cell where a shot was already registered.
            // The value of the cell isn't changed in that case.
            if (shipField.getCharAtCell(pos1, pos2) != 'X') {
                shipField.setCell(pos1, pos2, 'M');
                fogField.setCell(pos1, pos2, 'M');
            }
            System.out.println("You missed.");
        }
    }

    /**
     *
     * @param pos1 indicates the row.
     * @param pos2 indicates the column.
     * @return returns true if the registered hit caused the ship so sink.
     */
    private boolean isShipSunk(int pos1, int pos2) {
          boolean shipSunk = false;
          try {
              if (pos1 == 0) {
                  shipSunk = shipField.getCharAtCell(pos1 + 1, pos2) != 'O' &&
                          shipField.getCharAtCell(pos1, pos2 - 1) != 'O' &&
                          shipField.getCharAtCell(pos1, pos2 + 1) != 'O';
              } else if (pos1 == 9) {
                  shipSunk = shipField.getCharAtCell(pos1 -1, pos2) != 'O' &&
                          shipField.getCharAtCell(pos1, pos2 - 1) != 'O' &&
                          shipField.getCharAtCell(pos1, pos2 + 1) != 'O';
              } else {
                  shipSunk = shipField.getCharAtCell(pos1 - 1, pos2) != 'O' &&
                          shipField.getCharAtCell(pos1 + 1, pos2) != 'O' &&
                          shipField.getCharAtCell(pos1, pos2 - 1) != 'O' &&
                          shipField.getCharAtCell(pos1, pos2 + 1) != 'O';
              }
          } catch (IndexOutOfBoundsException ignored) {
          }
          return shipSunk;
    }
}

//Try implementing custom Exception later
//class PosTooHighOrLowException extends Exception {
//
//}
