import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Game game = new Game();
        game.startGame();
        game.currentBoard();
        game.findCurrentSets();
        game.automateRound();


        /*
        Scanner scanner = new Scanner(System.in);
        String cards = scanner.nextLine();

        printDealt();
        printUndealt();
         */
    }

    public static void useLineAsInput( String line){
        Scanner s = new Scanner(line);
    }
}
