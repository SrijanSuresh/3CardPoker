import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;            // Player's name
    private double anteBet;         // Ante bet placed by the player
    private double pairPlusBet;     // Pair Plus bet placed by the player
    private List<Card> hand;        // Player's hand of cards
    private double balance;         // Player's current balance
    private boolean folded;         // Flag to indicate if the player has folded

    // Constructor to initialize a player with a name and starting balance
    public Player(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.hand = new ArrayList<>();  // Initialize an empty hand
        this.folded = false;            // Player starts without folding
    }

    // Method to place an Ante bet, only allowed if amount is between $5 and $25
    public void placeAnteBet(double amount) {
        if (amount >= 5 && amount <= 25) {
            this.anteBet = amount;
        }
    }

    // Method to place a Pair Plus bet, only allowed if amount is between $5 and $25
    public void placePairPlusBet(double amount) {
        if (amount >= 5 && amount <= 25) {
            this.pairPlusBet = amount;
        }
    }

    // Getter for Ante bet amount
    public double getAnteBet() {
        return anteBet;
    }

    // Getter for Pair Plus bet amount
    public double getPairPlusBet() {
        return pairPlusBet;
    }

    // Method to receive a hand of cards and assign it to the player
    public void receiveCards(List<Card> hand) {
        this.hand = hand;
    }

    // Getter for the player's current hand
    public List<Card> getHand() {
        return hand;
    }

    // Getter for the player's current balance
    public double getBalance() {
        return balance;
    }

    // Method to add a specified amount to the player's balance
    public void addToBalance(double amount) {
        this.balance += amount;
    }

    // Method to subtract a specified amount from the player's balance
    public void subtractFromBalance(double amount) {
        this.balance -= amount;
    }

    // Method to mark the player as folded and clear their hand
    public void fold() {
        this.folded = true;    // Mark player as folded
        this.hand.clear();     // Clear the player's hand when folding
    }

    // Method to check if the player has folded
    public boolean hasFolded() {
        return folded;
    }

    // Method to check if the player is still in play (not folded)
    public boolean willPlay() {
        return !folded;
    }

    // Method to reset the player's state for the next round
    public void reset() {
        this.hand.clear();     // Clear the player's hand
        this.anteBet = 0;      // Reset Ante bet
        this.pairPlusBet = 0;  // Reset Pair Plus bet
        this.folded = false;   // Reset folded status
    }
}
