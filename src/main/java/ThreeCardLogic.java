import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreeCardLogic {

    public void playRound(List<Player> players, Dealer dealer, Deck deck) {
        if (dealer.getHand() == null || dealer.getHand().isEmpty()) {
            dealer.receiveCards(deck.dealHand());
        }

        int playerNumber = 1;
        for (Player player : players) {
            String playerLabel = "Player " + playerNumber;

            // Step 1: Deduct initial bets
            player.subtractFromBalance(player.getAnteBet());
            if (player.getPairPlusBet() > 0) {
                player.subtractFromBalance(player.getPairPlusBet());
            }
            System.out.println(playerLabel + " balance after initial bets deducted: " + player.getBalance());

            if (!player.willPlay()) {
                System.out.println(playerLabel + " folds.");
                playerNumber++;
                continue;
            }

            if (player.getHand() == null || player.getHand().isEmpty()) {
                player.receiveCards(deck.dealHand());
            }

            player.subtractFromBalance(player.getAnteBet());
            System.out.println(playerLabel + " balance after play bet deducted: " + player.getBalance());

            if (!dealer.qualifies()) {
                System.out.println("Dealer does not qualify. " + playerLabel + " wins play bet.");
                player.addToBalance(player.getAnteBet());
                System.out.println(playerLabel + " balance after dealer does not qualify: " + player.getBalance());
            } else {
                int playerRank = evaluateHand(player.getHand());
                int dealerRank = dealer.getHandRank();

                if (playerRank > dealerRank) {
                    System.out.println(playerLabel + " wins!");
                    player.addToBalance(2 * player.getAnteBet());
                    player.addToBalance(2 * player.getAnteBet());
                    System.out.println(playerLabel + " balance after winning: " + player.getBalance());
                } else {
                    System.out.println("Dealer wins against " + playerLabel);
                }
            }

            if (player.getPairPlusBet() > 0) {
                int pairPlusPayout = getPairPlusPayout(player.getHand());
                if (pairPlusPayout > 0) {
                    System.out.println(playerLabel + " wins Pair Plus bet!");
                    player.addToBalance(player.getPairPlusBet() * pairPlusPayout);
                    System.out.println(playerLabel + " balance after Pair Plus bet win: " + player.getBalance());
                } else {
                    System.out.println(playerLabel + " loses Pair Plus bet.");
                }
            }
            playerNumber++;
        }
    }

    // Determine hand rank for payout calculations
    private int getPairPlusPayout(List<Card> hand) {
        if (isStraightFlush(hand)) return 40;
        if (isThreeOfAKind(hand)) return 30;
        if (isStraight(hand)) return 6;
        if (isFlush(hand)) return 3;
        if (isPair(hand)) return 1;
        return 0; // No payout if less than a pair
    }

    private boolean isStraightFlush(List<Card> hand) {
        return isFlush(hand) && isStraight(hand);
    }

    private boolean isThreeOfAKind(List<Card> hand) {
        return hand.get(0).getCardRank() == hand.get(1).getCardRank() &&
                hand.get(1).getCardRank() == hand.get(2).getCardRank();
    }

    private boolean isStraight(List<Card> hand) {
        List<Integer> ranks = new ArrayList<>();
        for (Card card : hand) {
            ranks.add(card.getCardRank());
        }
        Collections.sort(ranks);
        return (ranks.get(2) - ranks.get(1) == 1) && (ranks.get(1) - ranks.get(0) == 1);
    }

    private boolean isFlush(List<Card> hand) {
        String suit = hand.get(0).getSuit();
        return hand.stream().allMatch(card -> card.getSuit().equals(suit));
    }

    private boolean isPair(List<Card> hand) {
        return hand.get(0).getCardRank() == hand.get(1).getCardRank() ||
                hand.get(1).getCardRank() == hand.get(2).getCardRank() ||
                hand.get(0).getCardRank() == hand.get(2).getCardRank();
    }

    private int evaluateHand(List<Card> hand) {
        int rank = 0;
        for (Card card : hand) {
            rank = Math.max(rank, card.getCardRank());
        }
        return rank;
    }
}
