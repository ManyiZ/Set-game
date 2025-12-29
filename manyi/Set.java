import javax.management.DescriptorAccess;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Set {
    public static final int ATTRIBUTE1 = 0;
    public static final int ATTRIBUTE2 = 1;
    public static final int ATTRIBUTE3 = 2;

    public static final int NUMBER = 0;
    public static final int COLOR = 1;
    public static final int SHAPE = 2;
    public static final int FILL = 3;

    private Deck deck;
    private Table table;

    public Set(){
        this.deck = new Deck();
        this.table = new Table(this.deck);

        this.deck.shuffle();
        startGame();
    }

    /**
     * Starts the game by calling the deal fun in Table to deal 12 cards into the first 12 slots
     */
    void startGame(){
        for (int i = 0; i < 12; i++){
            table.deal();
        }
    }

    /**
     * prints the current table of cards in a 3x4 grid by calling printTable()
     */
    void currentBoard(){
        table.printTable();
    }

    void printUndealt(){
        deck.printUndealtDeck();
    }

    void printDealt(){
        deck.printDealtDeck();
    }
}

class Table{
    Card[] r1;
    Card[] r2;
    Card[] r3;
    Card[] r4; // back up
    Card[][] table;

    Deck deck;

    public Table(Deck deck){
        r1 = new Card[4];
        r2 = new Card[4];
        r3 = new Card[4];
        r4 = new Card[4];

        this.deck = deck;

        table = new Card[][]{r1, r2, r3, r4};
    }

    void deal(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 4; j++){
                if (table[i][j] == null){
                    table[i][j] = deck.deal();
                    return;
                }
            }
        }
    }

    /** X in the string denotes the column, # denotes the row. [#][X]
     *
     * @param c1 str in format X#
     * @param c2 str in format X#
     * @param c3 str in format X#
     */
    boolean checkSet(String c1, String c2, String c3){
        int[] card1 = table[(int) c1.charAt(1) - 1][((int) c1.charAt(0)) - 97].getCard();
        int[] card2 = table[(int) c2.charAt(1) - 1][((int) c2.charAt(0)) - 97].getCard();
        int[] card3 = table[(int) c3.charAt(1) - 1][((int) c3.charAt(0)) - 97].getCard();

        for (int i = 0; i < 4; i++){
            boolean check12 = card1[i] != card2[i];
            boolean check23 = card2[i] != card3[i];
            boolean check13 = card1[i] != card3[i];
            boolean allDiff = check12 && check23 && check13;
            boolean allSame = !check12 && !check23 && !check13;

            if (!allDiff || !allSame)
                return false;

        }

        return true;
    }

    /**
     * prints the current table of cards in a 3x4 grid
     */
    void printTable(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 4; j++){
                System.out.print(Arrays.toString(table[i][j].getCard()) + " ");
            }
            System.out.println();
        }
    }

}