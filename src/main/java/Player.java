import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private int anteBet, playBet, pairPlusBet, totalWinnings;

    public Player() {
        hand = new ArrayList<>();
        anteBet = playBet = pairPlusBet = totalWinnings = 0;
    }

    public ArrayList<Card> getHand() { return hand; }
    public void setHand(ArrayList<Card> hand) { this.hand = hand; }
}
