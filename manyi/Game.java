import java.lang.reflect.Array;
import java.util.*;


public class Game {

    private Deck deck;
    private Table table;

    public Game(){
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

    void findCurrentSets(){
        System.out.println("finding sets...");
        table.findAllSets();
    }

    void automateRound(){
        table.printSetsFound();
        table.autoRemoveSet();
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
    public static final int NUMBER = 0;
    public static final int COLOR = 1;
    public static final int SHAPE = 2;
    public static final int FILL = 3;

    Card[] table;
    HashSet<List<Integer>> sets;
    HashSet<List<Integer>> alrFoundSets;
    Deck deck;

    boolean noSetOnTable;

    public Table(Deck deck){
        this.deck = deck;
        sets = new HashSet<>();
        alrFoundSets = new HashSet<>();
        table = new Card[16];
        noSetOnTable = false;

    }

    /** Checks whether the 3 cards selected by the player make up a set.
     * X in the string denotes the column, # denotes the row. [#][X]
     *
     * @param c1 str in format X#
     * @param c2 str in format X#
     * @param c3 str in format X#
     */
    boolean checkSet(String c1, String c2, String c3){


        return true;
    }

    void deal(){
        for (int i = 0; i < 12; i++){
            if (table[i]== null){
                table[i] = deck.deal();
                return;}
        }
    }

    void deal(int index){
        table[index] = deck.deal();
    }
    //TODO fix this, the error is happening bc the stuff in sets is being removed while it is running in this for loop
    void autoRemoveSet(){
        for (List<Integer> i : sets){
            removeSelectedSet(i);
        }
    }

    void removeSelectedSet(List<Integer> cs){
        if (noSetOnTable){
            System.out.println("No set exists :(");
            return;
        } else if (!sets.contains(cs)){
            System.out.println("Not valid set");
            return;
        }

        alrFoundSets.add(cs);
        sets.remove(cs);

        HashSet<List<Integer>> toRemove = new HashSet<>();

        for (List<Integer> l: sets){
            for (int i: cs){
                if(l.contains(i))
                    toRemove.add(l);
            }
        }
        sets.removeAll(toRemove);

        for (int i = 0; i < cs.size(); i++){
            System.out.print("card " + (i + 1) + " removed " + Arrays.toString(table[cs.get(i)].getCard()));
            table[cs.get(i)] = null;
//            deal(cs.get(i));
            deal();
        }
        System.out.println();

        printTable();
        noSetOnTable = sets.isEmpty();
    }
    void removeSelectedSet(int[] cardsSelected){
        removeSelectedSet(arrayToList(cardsSelected));
    }

    void findAllSets(){
        int[] numAttributes = getAttributeArray(NUMBER);
        int[] colorAttributes = getAttributeArray(COLOR);
        int[] shapeAttributes = getAttributeArray(SHAPE);
        int[] fillAttributes = getAttributeArray(FILL);

        HashSet<List<Integer>>[] nums = findAllSets(numAttributes);
        HashSet<List<Integer>>[] colors = findAllSets(colorAttributes);
        HashSet<List<Integer>>[] shapes = findAllSets(shapeAttributes);
        HashSet<List<Integer>>[] fills = findAllSets(fillAttributes);
//        System.out.println("num attributes on table: " + Arrays.toString(numAttributes));

        HashSet<List<Integer>> possibleSet = new HashSet<>();
//        System.out.println("checking for overlap between diffs");
        runIntersection(nums[0], colors, possibleSet);
//        System.out.println("checking for overlap between sames");
        runIntersection(nums[1], colors, possibleSet);
        runIntersection(possibleSet, shapes, possibleSet);
        runIntersection(possibleSet, fills, possibleSet);

        noSetOnTable = possibleSet.isEmpty();
        sets = possibleSet;
    }
    /**Helper function. finds all the cards that have 0s 1s and 2s and then makes combo sets of threes
     * that are all the same or all different.
     *
     * @param attArray array containing a specific attribute of the cards on the board
     * @return two hashsets containing the sets of different(0) and same(1) of a given attribute
     */
    private HashSet<List<Integer>>[] findAllSets(int[] attArray){
        ArrayList<Integer> zeros = new ArrayList<>();
        ArrayList<Integer> ones = new ArrayList<>();
        ArrayList<Integer> twos = new ArrayList<>();

        for (int i = 0; i < attArray.length; i++){
            if (attArray[i] == 0)
                zeros.add(i);
            else if (attArray[i] == 1)
                ones.add(i);
            else if (attArray[i] == 2)
                twos.add(i);
        }
//        System.out.println("zeros " + zeros);
//        System.out.println("ones " + ones);
//        System.out.println("twos " + twos);
        HashSet<List<Integer>> differents = new HashSet<>();
        HashSet<List<Integer>> sames = new HashSet<>();

        sames.addAll(generate3s(zeros));
        sames.addAll(generate3s(ones));
        sames.addAll(generate3s(twos));

        for (Integer zero : zeros) {
            for (Integer one : ones) {
                for (Integer two : twos) {
//                    System.out.println("Diff combo: " + Arrays.toString(temp));
                    differents.add(arrayToList(new int[]{zero, one, two}));
                }
            }
        }

        return new HashSet[]{differents, sames};
    }
    /**Helper function for findAllSets(), checks for overlapping sets
     * @param set1 set to compare
     * @param sets2 sets to compare
     * @param out set to put all the overlapping sets
     */
    private void runIntersection(HashSet<List<Integer>> set1, HashSet<List<Integer>>[] sets2, HashSet<List<Integer>> out){
//        HashSet<List<Integer>> intersection1 = new HashSet<>(set1);
//        HashSet<List<Integer>> intersection2 = new HashSet<>(set1);
//        intersection1.retainAll(sets2[0]);
//        intersection2.retainAll(sets2[1]);
//
//        debutIntHashArray(set1, "overlap 0");
//        debutIntHashArray(sets2[0], "overlap 0.2");
        HashSet<List<Integer>> intersection = new HashSet<>();

        for (List<Integer> i : set1){
            if (sets2[0].contains(i)){
                intersection.add(i);
            }
            if (sets2[1].contains(i)){
                intersection.add(i);
            }
        }
//
//        debutIntHashArray(intersection1, "any overlap?");
//        debutIntHashArray(intersection2, "any overlap?");
        out.clear();
        out.addAll(intersection);
//
//        for (List<Integer> i : out){
//            System.out.println(Arrays.toString(new List[]{i}));
//        }
    }
    /**
     * resource: <a href="https://www.geeksforgeeks.org/dsa/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/">...</a>
     */
    private HashSet<List<Integer>> generate3s(ArrayList<Integer> sames){
        HashSet<List<Integer>> set = new HashSet<>();
        if (sames.size() < 3)
            return set;

        for (int i = 0; i < sames.size() - 2; i++){
            for (int j = 1; j < sames.size() - 1; j++){
                for (int k = 2; k < sames.size(); k++) {
                    if (!(i == j || j == k || i == k)) {
//                        System.out.println("Sames combo: " + Arrays.toString(temp));
                        set.add(arrayToList(new int[]{sames.get(i), sames.get(j), sames.get(k)}));
                    }
                }
            }
        }
//        debutIntHashArray(set, "sames");
        return set;
    }
    private int[] getAttributeArray(int at){
        int[] attArray = new int[16];
        int counter = 0;
        for (int i = 0; i < 16; i++){
            if (table[i] == null)
                attArray[counter] = -1;
            else {
                attArray[counter] = table[i].getCard()[at];
                //System.out.print(table[i].getCard()[at] + " " );
                //System.out.println(attArray[counter]);
            }
            counter++;
        }
//        System.out.println(Arrays.toString(attArray));
        return attArray;
    }

    /**
     * prints the current table of cards in a 3x4 grid
     */
    void printTable(){
        int counter = 0;
        System.out.println("------------------ Current Table ------------------");
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                System.out.print(Arrays.toString(table[counter].getCard()) + " ");
                counter++;
            }
            System.out.println();
            if (counter < 15 && table[counter] == null)
                break;
        }
        System.out.println("---------------------------------------------------");
    }
    void printSetsFound(){
        if (noSetOnTable) {
            System.out.println("No Sets on current board!");
        }
        else
            System.out.println("Sets found!");
//        for (List<Integer> s: sets){
//            System.out.println(s);
//        }
    }
    private List<Integer> arrayToList(int[] array){
        List<Integer> a = new ArrayList<>();
        for (int i: array){
            a.add(i);
        }
        Collections.sort(a);
        return a;
    }

    /**
     * for debugging HashSet<List<Integer>>
     */
    private void debugIntHashArray(HashSet<List<Integer>> a){
        for (List<Integer> i : a){
            System.out.println(i.toString());
        }
    }

    private void debugIntHashArray(HashSet<List<Integer>> a, String msg){
        for (List<Integer> i : a){
            System.out.println(msg + ": " + i.toString());
        }
    }

}
