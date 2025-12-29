import java.util.ArrayList;

public class Deck {
    ArrayList<Card> undealtCards;
    ArrayList<Card> dealtCards;

    public Deck(){
        this.undealtCards = new ArrayList<>();
        this.dealtCards = new ArrayList<>();
        newCards(0, 0, 0, 0);
    }

    /**
     * Deals 1 card at a time
     * @return Card at the bottom of the pile
     */
    Card deal(){
        Card c = undealtCards.get(0);
        undealtCards.remove(0);
        dealtCards.add(c);
        return c;
    }

    /** Recursive function that sets up a deck of 81 unique cards
     *
     * @param num the number value of the card
     * @param col the color value of the card
     * @param shape the shape value of the card
     * @param fill the fill type of the card
     */
    void newCards(int num, int col, int shape, int fill){
        //debugging:
        //System.out.println(num + " " + col + " " + shape + " " + fill);
        if (num == 2 && col == 2 && shape == 2 && fill >= 2) {
            undealtCards.add(new Card(num, col, shape, fill));
            return;
        }else if (col == 2 && shape == 2 && fill >= 2)
            newCards(num + 1, 0, 0, 0);
        else if (shape == 2 && fill >= 2)
            newCards(num, col + 1, 0, 0);
        else if (fill >= 2)
            newCards(num, col, shape + 1, 0);
        else
            newCards(num, col, shape, fill + 1);

        undealtCards.add(new Card(num, col, shape, fill));
    }

    /**
     * shuffles the cards only in the undealt deck. Will work with any deck size.
     */
    void shuffle(){
        int cardsToShuffle = undealtCards.size();
        ArrayList<Card> temp = new ArrayList<>(undealtCards);
        undealtCards.clear();

        for(int i = 1; i <= cardsToShuffle; i++){
            int c = (int) (Math.random() * (cardsToShuffle - i));
            undealtCards.add(temp.get(c));
            temp.remove(c);
        }
        undealtCards.addAll(temp);
        temp.clear();
    }

    /**
     * @return number of cards in the undealt pile
     */
    int getDeckSize(){
        return undealtCards.size();
    }
    void printDealtDeck(){
        for(Card c: dealtCards){
            System.out.println(c.getName());
        }
    }
    void printUndealtDeck(){
        for(Card c: undealtCards){
            System.out.println(c.getName());
        }
    }
}

class Card {
    private final int number;
    private final int color;
    private final int shape;
    private final int fill;

    final String[] NUMBER_STR = {"one", "two", "three"};
    final String[] COLOR_STR = {"red", "green", "purple"};
    final String[] SHAPE_STR = {"diamond", "oval", "squiggle"};
    final String[] FILL_STR = {"blank", "stripes", "filled"};

    /**
     * @param number Defines the number value of the card
     * @param color Defines the color value of the card
     * @param shape Defines the shapes of the card
     * @param fill Defines the fill type of the card
     */
    public Card(int number, int color, int shape, int fill){
        this.number = number;
        this.color = color;
        this.shape = shape;
        this.fill = fill;
    }

    public int[] getCard(){
        return new int[]{number, color, shape, fill};
    }

    public String getName(){
        return NUMBER_STR[number] + " " +
                COLOR_STR[color] + " " +
                SHAPE_STR[shape] + " " +
                FILL_STR[fill]+ " "                 ;
    }
}
