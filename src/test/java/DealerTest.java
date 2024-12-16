import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class DealerTest {

    private Dealer dealer;

    @BeforeEach
    void setUp() {
        dealer = new Dealer();
    }

    @Test
    void testReceiveCardsStoresHand() {
        List<Card> hand = Arrays.asList(
                new Card("Hearts", "10"),
                new Card("Spades", "Q"),
                new Card("Diamonds", "K")
        );
        dealer.receiveCards(hand);
        assertEquals(hand, dealer.getHand(), "Dealer's hand should match the received cards.");
    }

    @Test
    void testGetHandRankReturnsHighestCardRank() {
        List<Card> hand = Arrays.asList(
                new Card("Hearts", "2"),
                new Card("Spades", "Q"),
                new Card("Diamonds", "7")
        );
        dealer.receiveCards(hand);
        assertEquals(12, dealer.getHandRank(), "Highest rank in the hand is Queen (12).");
    }

    @Test
    void testDealerQualifiesWithQueenHighOrBetter() {
        List<Card> hand = Arrays.asList(
                new Card("Clubs", "10"),
                new Card("Diamonds", "Q"),
                new Card("Spades", "2")
        );
        dealer.receiveCards(hand);
        assertTrue(dealer.qualifies(), "Dealer should qualify with Queen high.");
    }

    @Test
    void testDealerDoesNotQualifyWithoutQueenHigh() {
        List<Card> hand = Arrays.asList(
                new Card("Clubs", "10"),
                new Card("Diamonds", "J"),
                new Card("Spades", "9")
        );
        dealer.receiveCards(hand);
        assertFalse(dealer.qualifies(), "Dealer should not qualify without Queen high.");
    }

    @Test
    void testGetHandRankReturnsCorrectRankWithAceHigh() {
        List<Card> hand = Arrays.asList(
                new Card("Hearts", "A"),
                new Card("Diamonds", "7"),
                new Card("Spades", "5")
        );
        dealer.receiveCards(hand);
        assertEquals(14, dealer.getHandRank(), "Highest rank in the hand is Ace (14).");
    }

    @Test
    void testQualifiesWithAceHigh() {
        List<Card> hand = Arrays.asList(
                new Card("Clubs", "A"),
                new Card("Diamonds", "3"),
                new Card("Spades", "6")
        );
        dealer.receiveCards(hand);
        assertTrue(dealer.qualifies(), "Dealer should qualify with Ace high.");
    }

    @Test
    void testHandRankWithMixedCardValues() {
        List<Card> hand = Arrays.asList(
                new Card("Clubs", "4"),
                new Card("Diamonds", "K"),
                new Card("Spades", "3")
        );
        dealer.receiveCards(hand);
        assertEquals(13, dealer.getHandRank(), "Highest rank in the hand should be King (13).");
    }

    @Test
    void testEmptyHandReturnsZeroRank() {
        dealer.receiveCards(Arrays.asList());
        assertEquals(0, dealer.getHandRank(), "An empty hand should return a rank of 0.");
    }

    @Test
    void testNonQualifyingLowHand() {
        List<Card> hand = Arrays.asList(
                new Card("Clubs", "5"),
                new Card("Diamonds", "7"),
                new Card("Spades", "9")
        );
        dealer.receiveCards(hand);
        assertFalse(dealer.qualifies(), "Dealer should not qualify with all cards below Queen.");
    }

    @Test
    void testHighCardDeterminationWithEqualRankCards() {
        List<Card> hand = Arrays.asList(
                new Card("Clubs", "K"),
                new Card("Diamonds", "K"),
                new Card("Spades", "J")
        );
        dealer.receiveCards(hand);
        assertEquals(13, dealer.getHandRank(), "Highest rank in the hand should be King (13) with two Kings and a Jack.");
    }
}