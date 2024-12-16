import java.util.*;

public class Deck {
    private List<Card> cards;      // List to hold the deck of cards
    private Random random;          // Random generator for shuffling the deck

    // Constructor to initialize the deck with 52 cards
    public Deck() {
        random = new Random();
        cards = new ArrayList<>();

        // Define suits and values to create a standard 52-card deck
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        // Loop through suits and values to populate the deck
        for (String suit : suits) {
            for (String value : values) {
                cards.add(new Card(suit, value));
            }
        }
    }

    // Method to shuffle the deck using a random generator
    public void shuffle() {
        Collections.shuffle(cards, random);
    }

    // Method to deal a single card from the top of the deck
    public Card dealCard() {
        return cards.remove(cards.size() - 1); // Remove and return the last card
    }

    // Method to deal a hand of 3 cards
    public List<Card> dealHand() {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hand.add(dealCard()); // Add one card at a time to the hand
        }
        return hand;
    }

    // Method to get the number of remaining cards in the deck
    public int getRemainingCards() {
        return cards.size();
    }
}
