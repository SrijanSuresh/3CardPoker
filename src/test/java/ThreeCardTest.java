import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import java.util.Arrays;
import java.util.List;

class ThreeCardTest {
	private Player player;
	private Dealer dealer;
	private Deck deck;
	private ThreeCardLogic game;

	@BeforeEach
	void setUp() {
		player = new Player("TestPlayer", 100);
		dealer = new Dealer();
		deck = new Deck();
		game = new ThreeCardLogic();
	}

	// Helper function to set predefined cards for controlled testing
	private List<Card> createHand(String card1Value, String card1Suit, String card2Value, String card2Suit, String card3Value, String card3Suit) {
		return Arrays.asList(
				new Card(card1Suit, card1Value),
				new Card(card2Suit, card2Value),
				new Card(card3Suit, card3Value)
		);
	}

	@Test
	void testDealerDoesNotQualify() {
		player.placeAnteBet(10);
		// No Pair Plus bet placed in this test

		// Predefine hands
		player.receiveCards(createHand("10", "Hearts", "9", "Clubs", "8", "Diamonds"));  // Player's hand
		dealer.receiveCards(createHand("2", "Hearts", "3", "Clubs", "5", "Diamonds"));    // Dealer's hand (does not qualify)

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 90 after returning play bet only
		assertEquals(90, player.getBalance(), "Balance should be 90 after dealer does not qualify.");
	}
	@Test
	void testDealerQualifiesAndPlayerWins() {
		player.placeAnteBet(10);
		// No Pair Plus bet placed in this test
		player.receiveCards(createHand("K", "Hearts", "Q", "Clubs", "10", "Diamonds")); // Strong hand for player
		dealer.receiveCards(createHand("Q", "Spades", "J", "Clubs", "9", "Diamonds")); // Dealer qualifies with Queen high

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 120 after winning both ante and play bets
		assertEquals(120, player.getBalance(), "Player balance should increase by 20 if player wins against dealer");
	}


	@Test
	void testDealerQualifiesAndDealerWins() {
		player.placeAnteBet(10);
		player.receiveCards(createHand("3", "Hearts", "4", "Clubs", "5", "Diamonds")); // Low hand for player
		dealer.receiveCards(createHand("Q", "Spades", "J", "Clubs", "10", "Diamonds")); // Dealer qualifies with a stronger hand

		game.playRound(Arrays.asList(player), dealer, deck);

		assertEquals(80, player.getBalance(), "Player balance should decrease by 20 if dealer wins against player");
	}
	@Test
	void testDealerDoesNotQualify_WithPairPlus() {
		player.placeAnteBet(10);
		player.placePairPlusBet(10);

		// Predefine hands
		player.receiveCards(createHand("10", "Hearts", "9", "Clubs", "8", "Diamonds"));  // Player's hand - Straight
		dealer.receiveCards(createHand("2", "Hearts", "3", "Clubs", "5", "Diamonds"));    // Dealer's hand (does not qualify)

		game.playRound(Arrays.asList(player), dealer, deck);

		assertEquals(140, player.getBalance(), "Balance should be 140 after dealer does not qualify and player wins Pair Plus.");
	}

	@Test
	void testDealerQualifiesAndDealerWins_WithPairPlus() {
		player.placeAnteBet(10);
		player.placePairPlusBet(10);

		// Predefine hands
		player.receiveCards(createHand("K", "Hearts", "K", "Clubs", "9", "Diamonds"));  // Player's hand - Pair of Kings
		dealer.receiveCards(createHand("A", "Spades", "Q", "Clubs", "10", "Diamonds")); // Dealer qualifies and wins with a higher hand

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 80 after losing ante and play bets but winning Pair Plus with a Pair (1:1 payout)
		assertEquals(80, player.getBalance(), "Balance should be 80 after dealer qualifies and wins, with player winning Pair Plus.");
	}


	@Test
	void testPairPlusBetWin() {
		player.placeAnteBet(10);
		player.placePairPlusBet(10);

		// Predefine hands
		player.receiveCards(createHand("10", "Hearts", "9", "Hearts", "8", "Hearts"));  // Player's hand - Straight Flush
		dealer.receiveCards(createHand("2", "Spades", "3", "Clubs", "5", "Diamonds"));  // Dealer's hand (does not qualify)

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 480 after returning play bet and winning Pair Plus with a Straight Flush (40:1 payout)
		assertEquals(480, player.getBalance(), "Balance should be 480 after player wins Pair Plus with a Straight Flush.");
	}


	@Test
	void testPairPlusBetLoss() {
		player.placeAnteBet(10);
		player.placePairPlusBet(10);

		// Predefine hands
		player.receiveCards(createHand("3", "Hearts", "5", "Clubs", "9", "Diamonds"));  // Player's hand - No Pair or higher
		dealer.receiveCards(createHand("2", "Spades", "3", "Clubs", "5", "Diamonds"));  // Dealer's hand (does not qualify)

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 80 after losing Pair Plus bet and returning play bet
		assertEquals(80, player.getBalance(), "Balance should be 80 after player loses Pair Plus bet with no qualifying hand.");
	}


	@Test
	void testPlayerFolds() {
		// Create foldingPlayer with an initial balance of 100
		Player foldingPlayer = new Player("FoldingPlayer", 100) {
			@Override
			public boolean willPlay() {
				return false; // Player decides to fold
			}
		};

		// Place bets on foldingPlayer
		foldingPlayer.placeAnteBet(10);
		foldingPlayer.placePairPlusBet(10);

		// Predefine hands, though they should not matter as the player folds
		foldingPlayer.receiveCards(createHand("3", "Hearts", "5", "Clubs", "9", "Diamonds"));  // Player's hand
		dealer.receiveCards(createHand("A", "Spades", "K", "Clubs", "Q", "Diamonds"));        // Dealer's hand

		// Run the game round with foldingPlayer
		game.playRound(Arrays.asList(foldingPlayer), dealer, deck);

		// Expected balance is 80 after forfeiting ante and Pair Plus bets due to folding
		assertEquals(80, foldingPlayer.getBalance(), "Balance should be 80 after player folds and forfeits bets.");
	}


	@Test
	void testStraightFlushInPairPlusBet() {
		player.placeAnteBet(10);
		player.placePairPlusBet(10);

		// Predefine hands
		player.receiveCards(createHand("10", "Hearts", "9", "Hearts", "8", "Hearts"));  // Player's hand - Straight Flush
		dealer.receiveCards(createHand("A", "Spades", "K", "Clubs", "Q", "Diamonds"));  // Dealer's hand - Qualified

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 470 after losing ante and play bets but winning Pair Plus with a Straight Flush (40:1 payout)
		assertEquals(470, player.getBalance(), "Balance should be 470 after player wins Pair Plus with a Straight Flush.");
	}

	@Test
	void testThreeOfAKindInPairPlusBet() {
		player.placeAnteBet(10);
		player.placePairPlusBet(10);

		// Predefine hands
		player.receiveCards(createHand("5", "Hearts", "5", "Clubs", "5", "Diamonds"));  // Player's hand - Three of a Kind
		dealer.receiveCards(createHand("A", "Spades", "K", "Clubs", "Q", "Diamonds"));  // Dealer's hand - Qualified

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 370 after losing ante and play bets but winning Pair Plus with Three of a Kind (30:1 payout)
		assertEquals(370, player.getBalance(), "Balance should be 370 after player wins Pair Plus with Three of a Kind.");
	}
	@Test
	void testTwoPlayerGameWithPairPlusAndDealer() {
		// Create the deck and dealer
		Deck deck = new Deck();
		deck.shuffle();
		Dealer dealer = new Dealer();

		// Create two players with initial balances and place bets
		Player player1 = new Player("Player 1", 100);
		player1.placeAnteBet(10);
		player1.placePairPlusBet(10);

		Player player2 = new Player("Player 2", 100);
		player2.placeAnteBet(10);
		player2.placePairPlusBet(10);

		// Predefine hands for players and dealer (can adjust based on scenarios)
		player1.receiveCards(createHand("5", "Hearts", "5", "Diamonds", "5", "Clubs")); // Three of a Kind
		player2.receiveCards(createHand("10", "Hearts", "9", "Hearts", "8", "Hearts")); // Straight Flush
		dealer.receiveCards(createHand("A", "Spades", "K", "Clubs", "Q", "Diamonds")); // Dealer qualifies with Ace high

		// Add players to a list and pass to playRound
		List<Player> players = Arrays.asList(player1, player2);
		game.playRound(players, dealer, deck);

		// Assertions for final balances
		assertEquals(370, player1.getBalance(), "Player 1 balance should be 370 after winning Pair Plus with Three of a Kind.");
		assertEquals(470, player2.getBalance(), "Player 2 balance should be 470 after winning Pair Plus with Straight Flush.");
	}

	@Test
	void testDealerQualifiesPlayerLosesWithLowerHand() {
		player.placeAnteBet(10);

		// Predefine hands
		player.receiveCards(createHand("4", "Hearts", "6", "Diamonds", "7", "Clubs")); // Player's hand - Low cards
		dealer.receiveCards(createHand("Q", "Spades", "J", "Clubs", "10", "Diamonds")); // Dealer qualifies with Queen high

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 80 after losing both ante and play bets
		assertEquals(80, player.getBalance(), "Balance should be 80 after player loses both ante and play bets with a lower hand.");
	}

	@Test
	void testDealerDoesNotQualify_PairPlusOnly() {
		player.placeAnteBet(10);
		player.placePairPlusBet(10);

		// Predefine hands
		player.receiveCards(createHand("5", "Hearts", "5", "Diamonds", "5", "Clubs")); // Player's hand - Three of a Kind
		dealer.receiveCards(createHand("2", "Spades", "3", "Clubs", "6", "Diamonds")); // Dealer does not qualify

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 380 after ante returned and winning Pair Plus with Three of a Kind (30:1 payout)
		assertEquals(380, player.getBalance(), "Balance should be 380 after dealer does not qualify and player wins Pair Plus.");
	}

	@Test
	void testPairPlusBetLoss_NoQualifyingHand() {
		player.placeAnteBet(10);
		player.placePairPlusBet(10);

		// Predefine hands
		player.receiveCards(createHand("2", "Hearts", "5", "Diamonds", "8", "Clubs")); // Player's hand - No pair or higher
		dealer.receiveCards(createHand("A", "Spades", "K", "Clubs", "Q", "Diamonds")); // Dealer qualifies

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 70 after losing Pair Plus bet and ante bet if player loses
		assertEquals(70, player.getBalance(), "Balance should be 70 after player loses Pair Plus bet with no qualifying hand.");
	}
	@Test
	void testDealerAndPlayerBothHaveStraightFlush_PlayerWinsTie() {
		player.placeAnteBet(10);
		player.placePairPlusBet(10);

		// Predefine hands
		player.receiveCards(createHand("10", "Hearts", "9", "Hearts", "8", "Hearts")); // Player's hand - Straight Flush
		dealer.receiveCards(createHand("9", "Spades", "8", "Spades", "7", "Spades")); // Dealer's hand - Lower Straight Flush

		game.playRound(Arrays.asList(player), dealer, deck);

		// Expected balance is 480 after winning Pair Plus and play bets (40:1 payout for Straight Flush)
		assertEquals(480, player.getBalance(), "Balance should be 480 after player wins with a higher Straight Flush.");
	}
	@Test
	void testPlayerFoldsWithPairPlusBet() {
		Player foldingPlayer = new Player("FoldingPlayer", 100) {
			@Override
			public boolean willPlay() {
				return false; // Player decides to fold
			}
		};

		foldingPlayer.placeAnteBet(10);
		foldingPlayer.placePairPlusBet(10);

		foldingPlayer.receiveCards(createHand("3", "Diamonds", "6", "Clubs", "8", "Spades")); // Player's hand
		dealer.receiveCards(createHand("Q", "Spades", "J", "Clubs", "9", "Diamonds")); // Dealer qualifies

		game.playRound(Arrays.asList(foldingPlayer), dealer, deck);

		// Expected balance is 80 after forfeiting ante and Pair Plus bets due to folding
		assertEquals(80, foldingPlayer.getBalance(), "Balance should be 80 after player folds and forfeits bets.");
	}

	@Test
	void testBothPlayersWinAgainstDealer() {
		Player player1 = new Player("Player 1", 100);
		Player player2 = new Player("Player 2", 100);

		player1.placeAnteBet(10);
		player1.placePairPlusBet(10);
		player2.placeAnteBet(10);
		player2.placePairPlusBet(10);

		player1.receiveCards(createHand("K", "Hearts", "Q", "Diamonds", "J", "Clubs")); // High hand for player 1
		player2.receiveCards(createHand("10", "Spades", "10", "Hearts", "10", "Diamonds")); // Three of a kind for player 2
		dealer.receiveCards(createHand("Q", "Spades", "J", "Clubs", "9", "Diamonds")); // Dealer qualifies

		game.playRound(Arrays.asList(player1, player2), dealer, deck);

		// Assertions for final balances
		assertEquals(170, player1.getBalance(), "Player 1 balance should be 170 after winning against dealer.");
		assertEquals(370, player2.getBalance(), "Player 2 balance should be 370 after winning against dealer with Three of a Kind and Pair Plus.");
	}

	@Test
	void testBothPlayersWinWithPairPlusDealerDoesNotQualify() {
		// Set up players and dealer
		Player player1 = new Player("Player 1", 100);
		Player player2 = new Player("Player 2", 100);
		Dealer dealer = new Dealer();

		// Place bets
		player1.placeAnteBet(10);
		player1.placePairPlusBet(10);
		player2.placeAnteBet(10);
		player2.placePairPlusBet(10);

		// Predefine hands
		player1.receiveCards(createHand("3", "Hearts", "7", "Hearts", "10", "Hearts")); // Player 1's hand - Flush
		player2.receiveCards(createHand("9", "Spades", "10", "Spades", "J", "Spades")); // Player 2's hand - Straight Flush
		dealer.receiveCards(createHand("2", "Diamonds", "4", "Clubs", "5", "Diamonds")); // Dealer's hand - Does not qualify

		// Play the round
		game.playRound(Arrays.asList(player1, player2), dealer, deck);

		// Assert final balances
		assertEquals(110, player1.getBalance(), "Player 1 balance should be 110 after winning Pair Plus with a Flush (3:1).");
		assertEquals(480, player2.getBalance(), "Player 2 balance should be 480 after winning Pair Plus with a Straight Flush (40:1).");
	}
	@Test
	void testDealerQualifiesPlayer1WinsWithThreeOfAKind_Player2LosesWithLowHand() {
		// Set up players and dealer
		Player player1 = new Player("Player 1", 100);
		Player player2 = new Player("Player 2", 100);
		Dealer dealer = new Dealer();

		// Place bets
		player1.placeAnteBet(10);
		player1.placePairPlusBet(10);
		player2.placeAnteBet(10);
		player2.placePairPlusBet(10);

		// Predefine hands
		player1.receiveCards(createHand("7", "Clubs", "7", "Diamonds", "7", "Hearts")); // Player 1's hand - Three of a Kind
		player2.receiveCards(createHand("3", "Spades", "5", "Diamonds", "6", "Clubs")); // Player 2's hand - Low cards
		dealer.receiveCards(createHand("Q", "Spades", "J", "Hearts", "10", "Diamonds")); // Dealer's hand - Qualifies with Queen high

		// Play the round
		game.playRound(Arrays.asList(player1, player2), dealer, deck);

		// Assert final balances
		assertEquals(370, player1.getBalance(), "Player 1 balance should be 370 after winning with Three of a Kind and Pair Plus (30:1).");
		assertEquals(70, player2.getBalance(), "Player 2 balance should be 70 after losing both play and Pair Plus bets.");
	}
	@Test
	void testPlayer1FoldsAfterPairPlus_Player2WinsWithStraight() {
		// Set up players and dealer
		Player player1 = new Player("Player 1", 100) {
			@Override
			public boolean willPlay() {
				return false; // Player 1 decides to fold
			}
		};
		Player player2 = new Player("Player 2", 100);
		Dealer dealer = new Dealer();

		// Place bets
		player1.placeAnteBet(10);
		player1.placePairPlusBet(10);
		player2.placeAnteBet(10);
		player2.placePairPlusBet(10);

		// Predefine hands
		player1.receiveCards(createHand("4", "Hearts", "5", "Clubs", "8", "Diamonds")); // Player 1's hand
		player2.receiveCards(createHand("6", "Hearts", "7", "Diamonds", "8", "Clubs")); // Player 2's hand - Straight
		dealer.receiveCards(createHand("Q", "Spades", "9", "Clubs", "10", "Diamonds")); // Dealer's hand - Qualifies with Queen high

		// Play the round
		game.playRound(Arrays.asList(player1, player2), dealer, deck);

		// Assert final balances
		assertEquals(80, player1.getBalance(), "Player 1 balance should be 80 after folding and losing both ante and Pair Plus bets.");
		assertEquals(130, player2.getBalance(), "Player 2 balance should be 130 after winning with a Straight and Pair Plus (6:1 payout).");
	}

}