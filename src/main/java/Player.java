import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private double anteBet;
    private double pairPlusBet;
    private List<Card> hand;
    private double balance;
    private boolean folded; // New field to track if the player folds

    public Player(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.hand = new ArrayList<>();
        this.folded = false;
    }

    public void placeAnteBet(double amount) {
        if (amount >= 5 && amount <= 25) {
            this.anteBet = amount;
        }
    }

    public void placePairPlusBet(double amount) {
        if (amount >= 5 && amount <= 25) {
            this.pairPlusBet = amount;
        }
    }

    public double getAnteBet() {
        return anteBet;
    }

    public double getPairPlusBet() {
        return pairPlusBet;
    }

    public void receiveCards(List<Card> hand) {
        this.hand = hand;
    }

    public List<Card> getHand() {
        return hand;
    }

    public double getBalance() {
        return balance;
    }

    public void addToBalance(double amount) {
        this.balance += amount;
    }

    public void subtractFromBalance(double amount) {
        this.balance -= amount;
    }

    public void fold() {
        this.folded = true; // Mark player as folded
        this.hand.clear();  // Clear hand when folding
    }

    public boolean hasFolded() {
        return folded;
    }

    public boolean willPlay() {
        return !folded; // Player will play if they have not folded
    }

    // New method to reset the player’s state for the next round
    public void reset() {
        this.hand.clear();
        this.anteBet = 0;
        this.pairPlusBet = 0;
        this.folded = false; // Reset folded status
    }
}
