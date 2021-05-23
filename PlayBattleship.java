package battleship;

import java.util.Scanner;
/**
 * Abstract class for playing the game. Creates an instance of Battleship for both players
 * and switches turns at shooting at each others ships until win condition is met.
 */
abstract class PlayBattleship extends Battleship {
    static Scanner scan = new Scanner(System.in);
    static void play() {
        Battleship player1 = new Battleship();
        Battleship player2 = new Battleship();
//      Only used for quick testing.
//        player1.countShips = 4;
//        player2.countShips = 4;

        // The Start of the game. Does necessary thing to begin game loop.
        System.out.println("Player 1, place your ships on the game field");
        player1.shipField.print();
        player1.placeShips();
        pressEnter();
        System.out.println("Player 2, place your ships on the game field");
        player2.shipField.print();
        player2.placeShips();
        // This is the fundamental game loop.
        // It checks for win condition after every turn.
        do {
            pressEnter();
            player1Turn(player1, player2);

            if (player1.shipField.isGameWon()) {
                player1.fogField.print();
                break;
            } else if (player2.shipField.isGameWon()) {
                player2.fogField.print();
                break;
            }

            pressEnter();
            player2Turn(player1, player2);

            if (player1.shipField.isGameWon()) {
                player2.fogField.print();
                break;
            } else if (player2.shipField.isGameWon()) {
                player1.fogField.print();
                break;
            }
        } while (true);

        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private static void pressEnter() {
        System.out.println("Press Enter and pass the move to another player");
        var temp = scan.nextLine();
    }

    private static void player1Turn(Battleship player1, Battleship player2) {
        System.out.println();
        player2.fogField.printNoSpace();
        System.out.println("---------------------");
        player1.shipField.printNoSpace();
        System.out.println("\nPlayer 1, it's your turn:");
        //Player1 shoots at the field of player2 where the ship locations of player2 are stored.
        player2.shoot();
    }

    private static void player2Turn(Battleship player1, Battleship player2) {
        System.out.println();
        player1.fogField.printNoSpace();
        System.out.println("---------------------");
        player2.shipField.printNoSpace();
        System.out.println("\nPlayer 2, it's your turn:");
        //Player2 shoots at the field of player1 where the ship locations of player1 are stored.
        player1.shoot();
    }
}
