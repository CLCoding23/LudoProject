/*
 * Ludo Board Game Project
 * Collin Lane & Reza Naqvi
 * CPT-231-W37
 * FALL23
 */

// Import needed classes
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LudoEngine extends Application {
	@Override
	public void start(Stage stage) {
		
		// calls the createTiles() method from LudoBoard, creating the tiles that the pawns must travel to finish the game
		Team[] players = LudoBoard.createTiles();
		
		BorderPane bdrPane = new BorderPane();
		
		
		// creates StackPanes and places them in an array for player pawn spawnpoints
		StackPane stack1 = new StackPane();
		StackPane stack2 = new StackPane();
		StackPane stack3 = new StackPane();
		StackPane stack4 = new StackPane();
		
		StackPane[] stacks = {stack1, stack2, stack3, stack4};
		
		// Creates the pawns using information passed, like the players and stacks array
		LudoBoard.createPawns(players, stacks);
		
		// Creates the gridpane for the main game board
		GridPane gameBoard = new GridPane();
		
		// adds the pawn spawn circles to the board
		gameBoard.add(stack1, 0, 0);
		gameBoard.add(stack2,  0,  2);
		gameBoard.add(stack3, 2, 2);
		gameBoard.add(stack4,  2,  0);
		
		// adds the tiles needed for pawns to move on to the game board
		gameBoard.add(players[0].tilePane,  0, 1);
		gameBoard.add(players[1].tilePane,  1, 2);
		gameBoard.add(players[2].tilePane,  2, 1);
		gameBoard.add(players[3].tilePane,  1, 0);
		
		// Creates the HBox with button and text area to roll dice
		HBox diceBar = new HBox();
		
		//Sets padding for dice bar
		diceBar.setPadding(new Insets(10));
		
		// Creates a button for rolling die
		Random rand = new Random();
		Button btnDice = new Button("Roll Dice");
		btnDice.setMinSize(100, 50);
		
		
		
		// Text area for die value
		TextArea rollOutput = new TextArea();
		
		// Event Handler for dice button
		btnDice.setOnAction(e->
		{
		    int diceRoll = rand.nextInt(6) + 1; //Fixed: Dice now rolls 1-6
            rollOutput.setText(Integer.toString(diceRoll));
		});
		
		// Button for testing moving blue pawn
		Button btnMove = new Button("Move the blue Pawn");
		btnMove.setMinSize(100, 50); // Sets button size
		
		
		// Adds both buttons and diceOutput text area to diceBar
		diceBar.getChildren().addAll(btnDice, rollOutput, btnMove);
		gameBoard.add(diceBar, 1, 3); // adds nodes to gameBoard
		
		
		// Adds a text output area and formats it to the center of the right sight of the scene
		TextArea txtOutput = new TextArea();
		txtOutput.setText("Pick a Pawn!");
		txtOutput.setMaxSize(450, 350);
		txtOutput.setTranslateX(-225);
		txtOutput.setTranslateY(300);
		
		List<Pawn> pawnList = new ArrayList<Pawn>();
		
		for(Team player : players)
		{
			for(Pawn pawn : player.pawns) {
				pawn.circle.setOnMouseClicked(e -> {
					txtOutput.setText("You have chosen pawn " + pawn.number + " of team " + pawn.team );
					Pawn selectPawn = pawn;
					if(pawnList.isEmpty()) 
					{
						pawnList.add(selectPawn);
						for(Pawn jiffypawn : pawnList)
						{
							System.out.println(jiffypawn.number);
						}
					}
					else
					{
						pawnList.remove(0);
						pawnList.add(selectPawn);
						for(Pawn jiffypawn : pawnList)
						{
							System.out.println(jiffypawn.number);
						}
					}
				});
			}
		}
		
		// Event handler for button, calls the moveBlue() function, and prints the affected pawns position to the console
		btnMove.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            	Pawn pawn = new Pawn();
            	try
            	{
            		pawn = pawnList.get(0);
            	}
            	catch(Exception e)
            	{
            		txtOutput.setText("Error: " + e.toString() + "\n"
            						 + "Please pick a pawn!");
            	}
            	
            	// If the pawn is at the spawnpoint, move it to the tile board
            	if (pawnList.get(0).started == false) 
            	{ 
            		pawn.startPawn(players[pawn.team]);
            		pawn.started = true;
            		int[] pawnPos = pawn.getPosition();
            		txtOutput.setText(pawnPos[0] + " " + pawnPos[1]);
            	}
            	// if it has started, move it however many tiles
            	else 
            	{
            		pawn.setPosition(players);
            	}
            }
        }));
		
		// Adds the center squares where pawns will stay when completed
		gameBoard.add(LudoBoard.createFinalPane(players), 1, 1);
		
		// Adds the gameBoard to the base bdrPane
		bdrPane.getChildren().add(gameBoard);
		
		// adds txtOutput to the right side of the bdrpane
		bdrPane.setRight(txtOutput);
		
		Scene scene = new Scene(bdrPane);
		// sets scene 
		stage.setTitle("Ludo");
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) /2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
		

		// Testing starting the pawn and numbering tiles
		for (int i = 0; i < 4; i++)
		{
			players[i].orderTiles();
			//players[i].startPawn(players[i].pawns[0]);
			int tileCount = 0;
			
			for (StackPane pane : players[i].tiles)
			{
				Text tileCountTxt = new Text();

				tileCountTxt.setText(Integer.toString(tileCount));
				
				pane.getChildren().add(tileCountTxt);
				tileCount++;
				//System.out.print(pane + "\n");
			}
			
		}
		
	}
	
	// Main method for launching program
	public static void main(String[] args) {
		launch(args);
	}
	
}

//Bhavya