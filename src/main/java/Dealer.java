import java.util.*;

public class Dealer {
    private List<Card> hand;

    public Dealer() {
        this.hand = new ArrayList<>();
    }

    public void receiveCards(List<Card> hand) {
        this.hand = hand;
    }

    public List<Card> getHand() {
        return hand;
    }

    public boolean qualifies() {
        // Dealer must have at least a Queen-high hand to qualify
        return getHandRank() >= 12; // Queen is 12 in rank
    }

    public int getHandRank() {
        // Evaluate dealer's hand, return the highest card rank
        int rank = 0;
        for (Card card : hand) {
            rank = Math.max(rank, card.getCardRank());
        }
        return rank;
    }
}
