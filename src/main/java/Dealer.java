import java.util.ArrayList;

public class Dealer {
    private Deck theDeck;
    private ArrayList<Card> dealersHand;

    public Dealer() {
        theDeck = new Deck();
        dealersHand = new ArrayList<>();
    }

    public ArrayList<Card> dealHand() {
        if (theDeck.size() < 35) theDeck.newDeck();
        ArrayList<Card> hand = new ArrayList<>(theDeck.subList(0, 3));
        theDeck.removeAll(hand);
        return hand;
    }
}
