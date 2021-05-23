package battleship;

     class GameField {
    // Here we are establishing the "playground" in an 2D Array.
    private final char[][] fieldTemplate = {
            {'A', '~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}, //0
            {'B', '~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}, //1
            {'C', '~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}, //2
            {'D', '~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}, //3
            {'E', '~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}, //4
            {'F', '~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}, //5
            {'G', '~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}, //6
            {'H', '~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}, //7
            {'I', '~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}, //8
            {'J', '~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}  //9
    };

    /**
     * These method will mainly be used by other methods to print the
     * updated field array.
     */
    private void printTemplate() {
        StringBuilder oneRow = new StringBuilder();
        for (char[] row : fieldTemplate) {
            for (char cell : row) {
                oneRow.append(cell).append(" ");
            }
            System.out.println(oneRow.toString().trim());
            oneRow = new StringBuilder();
        }
    }

     void print() {
        System.out.println("\n  1 2 3 4 5 6 7 8 9 10");
        printTemplate();
        System.out.println();
    }

    void printNoSpace() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        printTemplate();
    }

    // We can use this to change a cell after certain events in the game.
    void setCell(int pos1, int pos2, char value) {
        fieldTemplate[pos1][pos2] = value;
    }
    // We can use this to get the character located at the specified position in the array.
    char getCharAtCell(int pos1, int pos2) {
        return fieldTemplate[pos1][pos2];
    }

    /**
     * Checks if the win condition of the game is met.
     * Only to be used by the array which contains the ship placements.
     * @return returns true if the game is won and all ships have been eliminated.
     */
      boolean isGameWon() {
        for (char[] row : fieldTemplate) {
            for (char cell : row) {
                if (cell == 'O') {
                    return false;
                }
            }
        }
        return true;
    }
}
