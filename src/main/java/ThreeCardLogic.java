import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreeCardLogic {

    // Method to play a round of Three Card Poker with multiple players and a dealer
    public void playRound(List<Player> players, Dealer dealer, Deck deck) {
        // Deal a hand to the dealer if they haven't received cards yet
        if (dealer.getHand() == null || dealer.getHand().isEmpty()) {
            dealer.receiveCards(deck.dealHand());
        }

        int playerNumber = 1;
        // Loop through each player to handle their turn
        for (Player player : players) {
            String playerLabel = "Player " + playerNumber;

            // Step 1: Deduct initial Ante and Pair Plus bets from the player's balance
            player.subtractFromBalance(player.getAnteBet());
            if (player.getPairPlusBet() > 0) {
                player.subtractFromBalance(player.getPairPlusBet());
            }
            System.out.println(playerLabel + " balance after initial bets deducted: " + player.getBalance());

            // Check if player has folded
            if (!player.willPlay()) {
                System.out.println(playerLabel + " folds.");
                playerNumber++;
                continue; // Skip to the next player if they folded
            }

            // Deal a hand to the player if they haven't received cards yet
            if (player.getHand() == null || player.getHand().isEmpty()) {
                player.receiveCards(deck.dealHand());
            }

            // Deduct the play bet from the player's balance
            player.subtractFromBalance(player.getAnteBet());
            System.out.println(playerLabel + " balance after play bet deducted: " + player.getBalance());

            // Check if the dealer qualifies (needs Queen-high or better)
            if (!dealer.qualifies()) {
                System.out.println("Dealer does not qualify. " + playerLabel + " wins play bet.");
                player.addToBalance(player.getAnteBet()); // Refund play bet to player
                System.out.println(playerLabel + " balance after dealer does not qualify: " + player.getBalance());
            } else {
                // Compare player and dealer hands if the dealer qualifies
                int playerRank = evaluateHand(player.getHand());
                int dealerRank = dealer.getHandRank();

                if (playerRank > dealerRank) {
                    // Player wins against the dealer
                    System.out.println(playerLabel + " wins!");
                    player.addToBalance(2 * player.getAnteBet()); // Payout for winning the play bet
                    System.out.println(playerLabel + " balance after winning: " + player.getBalance());
                } else {
                    // Dealer wins against the player
                    System.out.println("Dealer wins against " + playerLabel);
                }
            }

            // Step 3: Handle Pair Plus bet payouts if player placed a Pair Plus bet
            if (player.getPairPlusBet() > 0) {
                int pairPlusPayout = getPairPlusPayout(player.getHand());
                if (pairPlusPayout > 0) {
                    // Player wins the Pair Plus bet
                    System.out.println(playerLabel + " wins Pair Plus bet!");
                    player.addToBalance(player.getPairPlusBet() * pairPlusPayout);
                    System.out.println(playerLabel + " balance after Pair Plus bet win: " + player.getBalance());
                } else {
                    // Player loses the Pair Plus bet
                    System.out.println(playerLabel + " loses Pair Plus bet.");
                }
            }
            playerNumber++;
        }
    }

    // Method to calculate the payout multiplier for the Pair Plus bet based on the player's hand
    private int getPairPlusPayout(List<Card> hand) {
        if (isStraightFlush(hand)) return 40;     // Straight flush payout
        if (isThreeOfAKind(hand)) return 30;      // Three of a kind payout
        if (isStraight(hand)) return 6;           // Straight payout
        if (isFlush(hand)) return 3;              // Flush payout
        if (isPair(hand)) return 1;               // Pair payout
        return 0; // No payout if the hand has less than a pair
    }

    // Helper method to check if a hand is a straight flush (consecutive cards of the same suit)
    private boolean isStraightFlush(List<Card> hand) {
        return isFlush(hand) && isStraight(hand);
    }

    // Helper method to check if a hand is three of a kind (all cards have the same rank)
    private boolean isThreeOfAKind(List<Card> hand) {
        return hand.get(0).getCardRank() == hand.get(1).getCardRank() &&
                hand.get(1).getCardRank() == hand.get(2).getCardRank();
    }

    // Helper method to check if a hand is a straight (consecutive cards of any suit)
    private boolean isStraight(List<Card> hand) {
        List<Integer> ranks = new ArrayList<>();
        for (Card card : hand) {
            ranks.add(card.getCardRank());
        }
        Collections.sort(ranks);
        return (ranks.get(2) - ranks.get(1) == 1) && (ranks.get(1) - ranks.get(0) == 1);
    }

    // Helper method to check if a hand is a flush (all cards are of the same suit)
    private boolean isFlush(List<Card> hand) {
        String suit = hand.get(0).getSuit();
        return hand.stream().allMatch(card -> card.getSuit().equals(suit));
    }

    // Helper method to check if a hand has a pair (two cards of the same rank)
    private boolean isPair(List<Card> hand) {
        return hand.get(0).getCardRank() == hand.get(1).getCardRank() ||
                hand.get(1).getCardRank() == hand.get(2).getCardRank() ||
                hand.get(0).getCardRank() == hand.get(2).getCardRank();
    }

    // Method to evaluate a hand and determine the highest card rank (used for dealer-player comparisons)
    private int evaluateHand(List<Card> hand) {
        int rank = 0;
        for (Card card : hand) {
            rank = Math.max(rank, card.getCardRank()); // Find the highest rank in the hand
        }
        return rank;
    }
}
