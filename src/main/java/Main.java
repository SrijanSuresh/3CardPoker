import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Player player = new Player("TestPlayer", 100);  // Starting balance of 100
        Dealer dealer = new Dealer();
        Deck deck = new Deck();
        ThreeCardLogic game = new ThreeCardLogic();

        // Place initial bets
        player.placeAnteBet(10);
        player.placePairPlusBet(10);

        System.out.println("Initial Balance: " + player.getBalance());  // Expect 100

        // Deduct bets from balance at the start of playRound
        player.subtractFromBalance(player.getAnteBet());
        player.subtractFromBalance(player.getPairPlusBet());

        System.out.println("Balance after initial bets deducted: " + player.getBalance());  // Expect 80

        // Manually deal specific cards to ensure dealer does not qualify
        player.receiveCards(Arrays.asList(new Card("Hearts", "10"), new Card("Clubs", "9"), new Card("Diamonds", "8")));
        dealer.receiveCards(Arrays.asList(new Card("Hearts", "2"), new Card("Clubs", "3"), new Card("Diamonds", "5")));

        // Player chooses to play, so deduct the play bet
        player.subtractFromBalance(player.getAnteBet());
        System.out.println("Balance after play bet deducted: " + player.getBalance());  // Expect 70

        // Dealer does not qualify (since highest card is lower than Queen)
        if (!dealer.qualifies()) {
            System.out.println("Dealer does not qualify. Player wins play bet.");
            player.addToBalance(player.getAnteBet()); // Return play bet
            System.out.println("Balance after dealer does not qualify (play bet returned): " + player.getBalance());  // Expect 80
        }

        // Check final balance to confirm result
        System.out.println("Final Balance: " + player.getBalance());  // Expect 80
    }

}
