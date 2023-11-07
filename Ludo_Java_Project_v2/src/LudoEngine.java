/*
 * Ludo Board Game Project
 * Collin Lane & Reza Naqvi
 * CPT-231-W37
 * FALL23
 */

// Import needed classes
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;

public class LudoEngine extends Application {
	@Override
	public void start(Stage stage) {
		// Initializes players array in main class
		Team[] players = new Team[4];
		
		// calls the createTiles() method from LudoBoard, creating the tiles that the pawns must travel to finish the game
		players = LudoBoard.createTiles();
		
		BorderPane bdrPane = new BorderPane();
		
		
		// creates StackPanes and places them in an array for player pawn spawnpoints
		StackPane stack1 = new StackPane();
		StackPane stack2 = new StackPane();
		StackPane stack3 = new StackPane();
		StackPane stack4 = new StackPane();
		
		StackPane[] stacks = {stack1, stack2, stack3, stack4};
		
		for(int i = 0; i < 4; i++)
		{
			
			
			// Creates circle object where pawns are first placed
			Circle pawnSpawn = new Circle(90, 90, 90);
			for(int j = 0; j < 4; j++)
			{
				// Creates 4 pawns for each team, and puts them in the players pawns array
				int playerPos[] = {i, j};
				players[i].pawns[j] = new Pawn(i, j, playerPos, 30f, players[i].color);
			}
			
			// Adds pawns to respective stackpanes
			stacks[i].getChildren().addAll(pawnSpawn, players[i].pawns[0].circle, players[i].pawns[1].circle, players[i].pawns[2].circle, players[i].pawns[3].circle);
			
			
			// Moves the placements of the pawns, so all are displayed separately
			players[i].pawns[0].circle.setTranslateX(-40.00);
			players[i].pawns[0].circle.setTranslateY(40.00);
			
			players[i].pawns[1].circle.setTranslateX(40.00);
			players[i].pawns[1].circle.setTranslateY(40.00);
			
			players[i].pawns[2].circle.setTranslateX(-40.00);
			players[i].pawns[2].circle.setTranslateY(-40.00);
			
			players[i].pawns[3].circle.setTranslateX(40.00);
			players[i].pawns[3].circle.setTranslateY(-40.00);
 
		}
		
		// Creates the gridpane for the main game board
		GridPane gameBoard = new GridPane();
		
		// adds the pawn spawn circles to the board
		gameBoard.add(stack1, 0, 0);
		gameBoard.add(stack2,  0,  2);
		gameBoard.add(stack3, 2, 2);
		gameBoard.add(stack4,  2,  0);
		
		// addds the tiles needed for pawns to move on to the game board
		gameBoard.add(players[0].tilePane,  0, 1);
		gameBoard.add(players[1].tilePane,  1, 2);
		gameBoard.add(players[2].tilePane,  2, 1);
		gameBoard.add(players[3].tilePane,  1, 0);
		
		players[0].tiles = LudoBoard.orderBlueTiles(players[0]);
		players[1].tiles = LudoBoard.orderYellowTiles(players[1]);
		players[2].tiles = LudoBoard.orderYellowTiles(players[2]);
		players[3].tiles = LudoBoard.orderYellowTiles(players[3]);
		
		
		
		// Creates the HBox with button and text area to roll dice
		HBox diceBar = new HBox();
		
		Random rand = new Random();
		Button btnDice = new Button("Roll Dice");
		
		
		
		
		TextArea rollOutput = new TextArea();
		
		// Event Handler for dice button
		btnDice.setOnAction(e->
		{
			rollOutput.setText(Integer.toString(rand.nextInt(6)));
		});
		
		
		diceBar.getChildren().addAll(btnDice, rollOutput);
		gameBoard.add(diceBar, 1, 3); // adds nodes to gameBoard
		
		
		System.out.print(players[0].name);
		
		gameBoard.add(LudoBoard.createFinalPane(players), 1, 1);
		
		bdrPane.getChildren().add(gameBoard);
		
		
		// Testing pawn movement
		
		players[0].startPawn();
		
		Scene scene = new Scene(bdrPane);
		// sets scene 
		stage.setTitle("Ludo");
		stage.setScene(scene);
		stage.setWidth(1500);
		stage.setHeight(850);
		stage.show();
		
		
	}
	// Main method for launching program
	public static void main(String[] args) {
		launch(args);
	}
	
}