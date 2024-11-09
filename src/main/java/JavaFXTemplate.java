//import javafx.animation.FadeTransition;
//import javafx.animation.PauseTransition;
//import javafx.animation.RotateTransition;
//import javafx.animation.SequentialTransition;
//import javafx.application.Application;
//
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//
//public class JavaFXTemplate extends Application {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		launch(args);
//	}
//
//	//feel free to remove the starter code from this method
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		// TODO Auto-generated method stub
//		primaryStage.setTitle("Welcome to JavaFX");
//
//		 Rectangle rect = new Rectangle (100, 40, 100, 100);
//	     rect.setArcHeight(50);
//	     rect.setArcWidth(50);
//	     rect.setFill(Color.VIOLET);
//
//	     RotateTransition rt = new RotateTransition(Duration.millis(5000), rect);
//	     rt.setByAngle(270);
//	     rt.setCycleCount(4);
//	     rt.setAutoReverse(true);
//	     SequentialTransition seqTransition = new SequentialTransition (
//	         new PauseTransition(Duration.millis(500)),
//	         rt
//	     );
//	     seqTransition.play();
//
//	     FadeTransition ft = new FadeTransition(Duration.millis(5000), rect);
//	     ft.setFromValue(1.0);
//	     ft.setToValue(0.3);
//	     ft.setCycleCount(4);
//	     ft.setAutoReverse(true);
//
//	     ft.play();
//	     BorderPane root = new BorderPane();
//	     root.setCenter(rect);
//
//	     Scene scene = new Scene(root, 700,700);
//			primaryStage.setScene(scene);
//			primaryStage.show();
//
//
//
//	}
//
//}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {
	private Player playerOne = new Player();
	private Player playerTwo = new Player();
	private Dealer theDealer = new Dealer();

	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Three Card Poker");

		// UI components
		Button startGameButton = new Button("Start Game");
		Button playButton = new Button("Play");
		Button dealButton = new Button("Deal");
		Button foldButton = new Button("Fold");

		startGameButton.setOnAction(e -> startGame());
		playButton.setOnAction(e -> play());
		dealButton.setOnAction(e -> deal());
		foldButton.setOnAction(e -> fold());

		HBox controls = new HBox(10, startGameButton, playButton, dealButton, foldButton);

		BorderPane root = new BorderPane();
		root.setBottom(controls);

		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void startGame() {
		// Reset game state and hands
		playerOne.setHand(theDealer.dealHand());
		playerTwo.setHand(theDealer.dealHand());
		// Update UI based on hands
	}

	private void play() {
		// Logic for player to play the game (place bets, evaluate hand)
	}

	private void deal() {
		// Logic for dealing cards to the dealer and players
	}

	private void fold() {
		// Logic for player to fold and reset for a new round
	}
}
