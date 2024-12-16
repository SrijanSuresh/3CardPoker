import java.util.*;

public class Dealer {
    private List<Card> hand;  // List to hold the dealer's hand of cards

    // Constructor to initialize the dealer with an empty hand
    public Dealer() {
        this.hand = new ArrayList<>();
    }

    // Method for the dealer to receive a hand of cards
    public void receiveCards(List<Card> hand) {
        this.hand = hand;
    }

    // Getter to retrieve the dealer's current hand
    public List<Card> getHand() {
        return hand;
    }

    // Method to determine if the dealer qualifies to play
    // Dealer qualifies only if they have at least a Queen-high hand
    public boolean qualifies() {
        return getHandRank() >= 12; // A Queen has a rank of 12
    }

    // Method to evaluate the rank of the dealer's hand by finding the highest card rank
    public int getHandRank() {
        int rank = 0;
        for (Card card : hand) {
            rank = Math.max(rank, card.getCardRank()); // Keep the highest rank in hand
        }
        return rank;
    }
}
