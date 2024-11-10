import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class JavaFXTemplate extends Application {

	private Deck deck;
	private Dealer dealer;
	private Player player1, player2;
	private ThreeCardLogic gameLogic;

	private HBox dealerCardBox;
	private HBox player1CardBox, player2CardBox;
	private Button dealButton, playButton1, foldButton1, playButton2, foldButton2;
	private Button placeAnteBetButton1, placePairPlusBetButton1, placeAnteBetButton2, placePairPlusBetButton2;
	private Label balanceLabel1, winningsLabel1, balanceLabel2, winningsLabel2;
	private TextField anteInput1, pairPlusInput1, anteInput2, pairPlusInput2;
	private double balance1 = 100, balance2 = 100;
	private int anteBet1 = 0, pairPlusBet1 = 0, playBet1 = 0, winnings1 = 0;
	private int anteBet2 = 0, pairPlusBet2 = 0, playBet2 = 0, winnings2 = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Three Card Poker - Two Players");

		// Initialize the deck, dealer, players, and game logic
		deck = new Deck();
		dealer = new Dealer();
		player1 = new Player("Player 1", balance1);
		player2 = new Player("Player 2", balance2);
		gameLogic = new ThreeCardLogic();

		// Shuffle the deck
		deck.shuffle();

		// Dealer's cards
		dealerCardBox = new HBox(10);
		dealerCardBox.setAlignment(Pos.CENTER);
		Label dealerLabel = new Label("Dealer's Cards:");

		// Player 1 and Player 2's cards
		player1CardBox = new HBox(10);
		player1CardBox.setAlignment(Pos.CENTER_LEFT);
		Label playerLabel1 = new Label("Player 1's Cards:");

		player2CardBox = new HBox(10);
		player2CardBox.setAlignment(Pos.CENTER_RIGHT);
		Label playerLabel2 = new Label("Player 2's Cards:");

		// Labels and text fields for balance and winnings for Player 1 and Player 2
		balanceLabel1 = new Label("Balance: $" + balance1);
		winningsLabel1 = new Label("Winnings: $" + winnings1);
		anteInput1 = new TextField();
		anteInput1.setPromptText("Ante Bet ($5-$25)");
		anteInput1.setPrefWidth(80);

		pairPlusInput1 = new TextField();
		pairPlusInput1.setPromptText("Pair Plus Bet ($5-$25)");
		pairPlusInput1.setPrefWidth(80);

		balanceLabel2 = new Label("Balance: $" + balance2);
		winningsLabel2 = new Label("Winnings: $" + winnings2);
		anteInput2 = new TextField();
		anteInput2.setPromptText("Ante Bet ($5-$25)");
		anteInput2.setPrefWidth(80);

		pairPlusInput2 = new TextField();
		pairPlusInput2.setPromptText("Pair Plus Bet ($5-$25)");
		pairPlusInput2.setPrefWidth(80);

		// Buttons for Player 1 and Player 2's bets and actions
		placeAnteBetButton1 = new Button("Place Ante Bet (P1)");
		placeAnteBetButton1.setOnAction(e -> placeAnteBet(player1, anteInput1, 1));

		placePairPlusBetButton1 = new Button("Place Pair Plus Bet (P1)");
		placePairPlusBetButton1.setOnAction(e -> placePairPlusBet(player1, pairPlusInput1, 1));

		playButton1 = new Button("Play (P1)");
		playButton1.setDisable(true);
		playButton1.setOnAction(e -> placePlayBet(1));

		foldButton1 = new Button("Fold (P1)");
		foldButton1.setDisable(true);
		foldButton1.setOnAction(e -> fold(1));

		placeAnteBetButton2 = new Button("Place Ante Bet (P2)");
		placeAnteBetButton2.setOnAction(e -> placeAnteBet(player2, anteInput2, 2));

		placePairPlusBetButton2 = new Button("Place Pair Plus Bet (P2)");
		placePairPlusBetButton2.setOnAction(e -> placePairPlusBet(player2, pairPlusInput2, 2));

		playButton2 = new Button("Play (P2)");
		playButton2.setDisable(true);
		playButton2.setOnAction(e -> placePlayBet(2));

		foldButton2 = new Button("Fold (P2)");
		foldButton2.setDisable(true);
		foldButton2.setOnAction(e -> fold(2));

		// Deal button in the center bottom
		dealButton = new Button("Deal");
		dealButton.setDisable(true); // Enable only after both players place bets
		dealButton.setOnAction(e -> dealCards());

		// Layout for player 1 and player 2 controls
		HBox anteBox1 = new HBox(10, anteInput1, placeAnteBetButton1);
		HBox pairPlusBox1 = new HBox(10, pairPlusInput1, placePairPlusBetButton1);
		VBox player1Box = new VBox(10, balanceLabel1, winningsLabel1, anteBox1, pairPlusBox1, playButton1, foldButton1);
		player1Box.setAlignment(Pos.CENTER_LEFT);

		HBox anteBox2 = new HBox(10, anteInput2, placeAnteBetButton2);
		HBox pairPlusBox2 = new HBox(10, pairPlusInput2, placePairPlusBetButton2);
		VBox player2Box = new VBox(10, balanceLabel2, winningsLabel2, anteBox2, pairPlusBox2, playButton2, foldButton2);
		player2Box.setAlignment(Pos.CENTER_RIGHT);

		// Center the deal button at the bottom of the window
		HBox dealButtonBox = new HBox(dealButton);
		dealButtonBox.setAlignment(Pos.CENTER);

		// Main layout
		BorderPane root = new BorderPane();
		root.setTop(new VBox(10, dealerLabel, dealerCardBox));
		root.setLeft(new VBox(10, playerLabel1, player1CardBox, player1Box));
		root.setRight(new VBox(10, playerLabel2, player2CardBox, player2Box));
		root.setBottom(dealButtonBox);  // Place dealButtonBox at the bottom center

		// Apply green background with a maroon border
		root.setStyle("-fx-background-color: green; -fx-border-color: maroon; -fx-border-width: 10;");

		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void placeAnteBet(Player player, TextField anteInput, int playerNumber) {
		try {
			int bet = Integer.parseInt(anteInput.getText());
			if (bet >= 5 && bet <= 25 && bet <= player.getBalance()) {
				if (playerNumber == 1) {
					anteBet1 = bet;
					balance1 -= anteBet1;
					balanceLabel1.setText("Balance: $" + balance1);
				} else {
					anteBet2 = bet;
					balance2 -= anteBet2;
					balanceLabel2.setText("Balance: $" + balance2);
				}
				anteInput.setDisable(true);
				checkIfReadyToDeal();
			} else {
				System.out.println("Invalid Ante Bet amount for Player " + playerNumber + ". Must be between $5 and $25.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid number for Ante Bet for Player " + playerNumber + ".");
		}
	}

	private void placePairPlusBet(Player player, TextField pairPlusInput, int playerNumber) {
		try {
			int bet = Integer.parseInt(pairPlusInput.getText());
			if (bet >= 5 && bet <= 25 && bet <= player.getBalance()) {
				if (playerNumber == 1) {
					pairPlusBet1 = bet;
					balance1 -= pairPlusBet1;
					balanceLabel1.setText("Balance: $" + balance1);
				} else {
					pairPlusBet2 = bet;
					balance2 -= pairPlusBet2;
					balanceLabel2.setText("Balance: $" + balance2);
				}
				pairPlusInput.setDisable(true);
				checkIfReadyToDeal();
			} else {
				System.out.println("Invalid Pair Plus Bet amount for Player " + playerNumber + ". Must be between $5 and $25.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid number for Pair Plus Bet for Player " + playerNumber + ".");
		}
	}

	private void checkIfReadyToDeal() {
		if (anteBet1 >= 5 && anteBet1 <= 25 && pairPlusBet1 >= 5 && pairPlusBet1 <= 25 &&
				anteBet2 >= 5 && anteBet2 <= 25 && pairPlusBet2 >= 5 && pairPlusBet2 <= 25) {
			dealButton.setDisable(false);
		}
	}


	private void dealCards() {
		dealerCardBox.getChildren().clear();
		player1CardBox.getChildren().clear();
		player2CardBox.getChildren().clear();

		// Shuffle and deal hands
		deck.shuffle();
		dealer.receiveCards(deck.dealHand());
		player1.receiveCards(deck.dealHand());
		player2.receiveCards(deck.dealHand());

		// Display cards
		dealer.getHand().forEach(card -> dealerCardBox.getChildren().add(createCard(card.getSuit(), card.getValue())));
		player1.getHand().forEach(card -> player1CardBox.getChildren().add(createCard(card.getSuit(), card.getValue())));
		player2.getHand().forEach(card -> player2CardBox.getChildren().add(createCard(card.getSuit(), card.getValue())));

		// Enable Play and Fold buttons after dealing
		playButton1.setDisable(false);
		foldButton1.setDisable(false);
		playButton2.setDisable(false);
		foldButton2.setDisable(false);
		dealButton.setDisable(true);
	}

	private void placePlayBet(int playerNumber) {
		if (playerNumber == 1 && balance1 >= anteBet1) {
			playBet1 = anteBet1;
			balance1 -= playBet1;
			balanceLabel1.setText("Balance: $" + balance1);
			evaluateRound(1);
		} else if (playerNumber == 2 && balance2 >= anteBet2) {
			playBet2 = anteBet2;
			balance2 -= playBet2;
			balanceLabel2.setText("Balance: $" + balance2);
			evaluateRound(2);
		}
	}

	private void fold(int playerNumber) {
		if (playerNumber == 1) {
			anteBet1 = pairPlusBet1 = playBet1 = winnings1 = 0;
			resetPlayerControls(1);
		} else if (playerNumber == 2) {
			anteBet2 = pairPlusBet2 = playBet2 = winnings2 = 0;
			resetPlayerControls(2);
		}
	}

	private void evaluateRound(int playerNumber) {
		List<Player> players = new ArrayList<>(List.of(player1, player2));
		gameLogic.playRound(players, dealer, deck);
		balance1 = player1.getBalance();
		balance2 = player2.getBalance();
		winningsLabel1.setText("Winnings: $" + winnings1);
		winningsLabel2.setText("Winnings: $" + winnings2);
		resetGame();
	}

	private void resetGame() {
		anteBet1 = pairPlusBet1 = playBet1 = anteBet2 = pairPlusBet2 = playBet2 = 0;
		dealButton.setDisable(true);
		resetPlayerControls(1);
		resetPlayerControls(2);
	}

	private void resetPlayerControls(int playerNumber) {
		if (playerNumber == 1) {
			anteInput1.clear();
			anteInput1.setDisable(false);
			pairPlusInput1.clear();
			pairPlusInput1.setDisable(false);
			playButton1.setDisable(true);
			foldButton1.setDisable(true);
		} else if (playerNumber == 2) {
			anteInput2.clear();
			anteInput2.setDisable(false);
			pairPlusInput2.clear();
			pairPlusInput2.setDisable(false);
			playButton2.setDisable(true);
			foldButton2.setDisable(true);
		}
	}

	private StackPane createCard(String suit, String rank) {
		Rectangle rect = new Rectangle(100, 150);
		rect.setArcHeight(15);
		rect.setArcWidth(15);
		rect.setFill(Color.LIGHTGRAY);
		rect.setStroke(Color.BLACK);

		Label cardLabel = new Label(suit + " " + rank);
		cardLabel.setTextFill(Color.BLACK);

		StackPane cardPane = new StackPane();
		cardPane.getChildren().addAll(rect, cardLabel);
		cardPane.setAlignment(Pos.CENTER);

		return cardPane;
	}

}
