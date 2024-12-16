public class Card {
    private String suit;   // The suit of the card (e.g., Hearts, Diamonds, etc.)
    private String value;  // The value of the card (e.g., 2, 3, ..., 10, J, Q, K, A)

    // Constructor to initialize a card with a specific suit and value
    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    // Getter method for the suit of the card
    public String getSuit() {
        return suit;
    }

    // Getter method for the value of the card
    public String getValue() {
        return value;
    }

    // Method to get the rank of the card as an integer for comparison purposes
    public int getCardRank() {
        switch (value) {
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            case "10": return 10;
            case "J": return 11;   // Jack
            case "Q": return 12;   // Queen
            case "K": return 13;   // King
            case "A": return 14;   // Ace
            default: return 0;     // Return 0 for invalid or unknown values
        }
    }

    // Override the toString method to provide a readable representation of the card
    @Override
    public String toString() {
        return value + " of " + suit; // Format as "Value of Suit" (e.g., "A of Spades")
    }
}
