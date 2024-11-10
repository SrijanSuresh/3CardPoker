import java.util.*;

public class Deck {
    private List<Card> cards;
    private Random random;

    public Deck() {
        random = new Random();
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String value : values) {
                cards.add(new Card(suit, value));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards, random);
    }

    public Card dealCard() {
        return cards.remove(cards.size() - 1);
    }

    public List<Card> dealHand() {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hand.add(dealCard());
        }
        return hand;
    }
    public int getRemainingCards() {
        return cards.size();
    }


}
