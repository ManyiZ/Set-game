import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Set game = new Set();
        game.startGame();
        game.currentBoard();

        Scanner scanner = new Scanner(System.in);
        String cards = scanner.nextLine();


        /*
        printDealt();
        printUndealt();
         */
    }

    public static void useLineAsInput( String line){
        Scanner s = new Scanner(line);
    }
}
