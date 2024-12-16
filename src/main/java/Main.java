import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Initialize the player, dealer, deck, and game logic
        Player player = new Player("TestPlayer", 100);  // Starting balance of 100
        Dealer dealer = new Dealer();
        Deck deck = new Deck();
        ThreeCardLogic game = new ThreeCardLogic();

        // Place initial Ante and Pair Plus bets
        player.placeAnteBet(10);
        player.placePairPlusBet(10);

        // Display initial balance before any deductions
        System.out.println("Initial Balance: " + player.getBalance());  // Expect 100

        // Deduct the Ante and Pair Plus bets from the player's balance
        player.subtractFromBalance(player.getAnteBet());
        player.subtractFromBalance(player.getPairPlusBet());
        System.out.println("Balance after initial bets deducted: " + player.getBalance());  // Expect 80

        // Manually deal specific cards to player and dealer to test qualification logic
        // Player’s hand: 10, 9, 8 (high cards)
        player.receiveCards(Arrays.asList(new Card("Hearts", "10"), new Card("Clubs", "9"), new Card("Diamonds", "8")));
        // Dealer’s hand: 2, 3, 5 (low cards, dealer does not qualify)
        dealer.receiveCards(Arrays.asList(new Card("Hearts", "2"), new Card("Clubs", "3"), new Card("Diamonds", "5")));

        // Player chooses to play, so deduct the play bet from the balance
        player.subtractFromBalance(player.getAnteBet());
        System.out.println("Balance after play bet deducted: " + player.getBalance());  // Expect 70

        // Check if the dealer qualifies (requires Queen-high or better)
        if (!dealer.qualifies()) {
            System.out.println("Dealer does not qualify. Player wins play bet.");
            player.addToBalance(player.getAnteBet()); // Return play bet to player's balance
            System.out.println("Balance after dealer does not qualify (play bet returned): " + player.getBalance());  // Expect 80
        }

        // Display the final balance after all operations
        System.out.println("Final Balance: " + player.getBalance());  // Expect 80
    }
}
