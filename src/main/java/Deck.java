import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card> {
    public Deck() { newDeck(); }

    public void newDeck() {
        this.clear();
        char[] suits = {'C', 'D', 'S', 'H'};
        for (char suit : suits) {
            for (int i = 2; i <= 14; i++) {
                this.add(new Card(suit, i));
            }
        }
        Collections.shuffle(this);
    }
}
