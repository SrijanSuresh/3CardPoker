import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void testDeckInitialization() {
        assertEquals(52, deck.getRemainingCards(), "Deck should initially have 52 cards.");
    }

    @Test
    void testDeckShuffleDoesNotChangeSize() {
        deck.shuffle();
        assertEquals(52, deck.getRemainingCards(), "Shuffling should not change the number of cards in the deck.");
    }

    @Test
    void testDealCardReducesDeckSize() {
        deck.dealCard();
        assertEquals(51, deck.getRemainingCards(), "Dealing one card should reduce deck size to 51.");
    }

    @Test
    void testDealHandReducesDeckSizeByThree() {
        deck.dealHand();
        assertEquals(49, deck.getRemainingCards(), "Dealing a hand should reduce deck size by three.");
    }

    @Test
    void testDeckContains52UniqueCards() {
        Set<Card> uniqueCards = new HashSet<>();
        while (deck.getRemainingCards() > 0) {
            uniqueCards.add(deck.dealCard());
        }
        assertEquals(52, uniqueCards.size(), "The deck should contain 52 unique cards.");
    }

    @Test
    void testDealHandReturnsThreeCards() {
        List<Card> hand = deck.dealHand();
        assertEquals(3, hand.size(), "Dealing a hand should return exactly three cards.");
    }

    @Test
    void testDeckIsEmptyAfterDealing52Cards() {
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        assertEquals(0, deck.getRemainingCards(), "Deck should be empty after dealing all 52 cards.");
    }

    @Test
    void testCannotDealMoreThan52Cards() {
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        assertThrows(IndexOutOfBoundsException.class, () -> deck.dealCard(), "Dealing a card from an empty deck should throw an exception.");
    }

    @Test
    void testDealHandFromShuffledDeckReturnsThreeCards() {
        deck.shuffle();
        List<Card> hand = deck.dealHand();
        assertEquals(3, hand.size(), "After shuffling, dealing a hand should still return three cards.");
    }

    @Test
    void testRemainingCardsAfterDealingMultipleHands() {
        deck.dealHand(); // -3 cards
        deck.dealHand(); // -3 cards
        deck.dealCard(); // -1 card
        assertEquals(45, deck.getRemainingCards(), "Deck should have 45 cards after dealing two hands and one additional card.");
    }
}