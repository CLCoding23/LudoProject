/*
 * Ludo Board Game Project
 * Collin Lane & Reza Naqvi
 * CPT-231-W37
 * FALL23
 */


// Main class of the Ludo project, contains code putting all classes together to create Ludoboard Game, with main method at the bottom


// Import needed classes
import java.awt.Toolkit;
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
		
		LudoBoard ludoBoard = new LudoBoard();
		
		
		// calls the createTiles() method from LudoBoard, creating the tiles that the pawns must travel to finish the game
		Team[] players = ludoBoard.createTiles();
		
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
		
		
		// Adds a text output area and formats it to the center of the right sight of the scene
		TextArea txtOutput = new TextArea();
		txtOutput.setText("Roll the dice then pick a Pawn!");
		//txtOutput.setMaxSize(450, 350);
		txtOutput.setMaxSize(400, 100);
		txtOutput.setEditable(false);
		
		// Text area for die value
		TextArea rollOutput = new TextArea();
		rollOutput.setEditable(false);
		
		// Minimize as it was causing formatting issues
		rollOutput.setMaxSize(50, 50);
		
		
		// FOR ROLLING DICE
		btnDice.setOnAction(e->
		{
			// Generate number between 1 and 6
		    int diceRoll = rand.nextInt(6) + 1; //Fixed: Dice now rolls 1-6
		    
		    // Show users dice output/value
            rollOutput.setText(Integer.toString(diceRoll));
            
            ludoBoard.setDistance(diceRoll); // Set property in ludoBoard object so data persists across action events
            
            btnDice.setDisable(true); // Only allow user to roll dice once a turn
            
            // If no pawn is selected, tell user to select pawn
            if(ludoBoard.playerTurn != null)
            {
            	txtOutput.setText(ludoBoard.playerTurn.name + " has rolled a " + ludoBoard.distance + ", please select one of your pawns!");
            }
            
            
          //Automatically skips over player if they have no pawns started and did not roll a 6
			boolean hasPawnStarted = false;
			
			for(Pawn p : ludoBoard.playerTurn.pawns)
			{
				if(p.started == true)
				{
					hasPawnStarted = true;
					break;
				}
			}
			// If no pawns have started user cannot roll without a 6, so skip to next player
            if(hasPawnStarted == false  && ludoBoard.distance != 6)
			{
				txtOutput.setText(ludoBoard.nextTurn(players, "You must enter a 6 to start a pawn! \n\n"));
				
				btnDice.setDisable(false);
				
			}
            
		});
		
		
		
		// Adds button and diceOutput text area to diceBar
		diceBar.getChildren().addAll(btnDice, rollOutput);
		
		// Spacing
		diceBar.setSpacing(3);
		
		// Loops through each pawn of each player and adds an event listener for moving the pawns
		for(Team player : players)
		{
			for(Pawn pawn : player.pawns) {
				pawn.setOnMouseClicked(e -> {
					if(ludoBoard.distance == 0) // If dice hasn't been rolled, tell user to roll dice
					{
						txtOutput.setText("Please roll the dice before selecting which pawn to move");
					}
					else //dice has value
					{	// If it is the first pawn, or it is a pawn of the current player's turn
						if(pawn.team == ludoBoard.playerTurn || ludoBoard.selectPawn == null)
						{
							// Tell user selected Pawn
							txtOutput.setText("You have chosen pawn " + pawn.number + " of " + pawn.team.name);
							
							// Set selected pawn
							ludoBoard.selectPawn = pawn;
							
							// Set player turn if beginning 
							if(ludoBoard.playerTurn == null)
							{
								ludoBoard.playerTurn = player;
							}
							
							// If pawn has not started and dice value isn't 6, user turn is skipped
							if(pawn.started == false && ludoBoard.distance != 6)
							{
								txtOutput.setText(ludoBoard.nextTurn(players, "You must enter a 6 to start your pawn! \n"));
								
								btnDice.setDisable(false);
							}
							
							// Starts pawn
							else if(pawn.started == false && ludoBoard.distance == 6) 
							{
								// Starts the pawn and sets the txtOutput
								txtOutput.setText(pawn.startPawn(player, players, ludoBoard.distance, ludoBoard, ""));
								
								// pawn is selected, enable dice rolling
								btnDice.setDisable(false);
							}
							// Normal pawn movement if pawn matches current team
							else if(pawn.team == ludoBoard.playerTurn)
							{
								// Move pawn and set the text
								txtOutput.setText(pawn.movePawn(players, ludoBoard, ""));

								// Enable dice button
								btnDice.setDisable(false);
							}
						}
						else // If wrong team's pawn is clicked
						{
							txtOutput.setText("It is " + ludoBoard.playerTurn.name + "'s turn \n" + 
									"You have selected " + pawn.team.name);
						}
						
						if(player.score == 4) // If score is 4, player wins
						{
							txtOutput.setText(player.name + " has won the game!");
							btnDice.setDisable(true); //Disable dice button
						}
					}
					
				});
			}
		}
		// Adds the center squares where pawns will stay when completed
		gameBoard.add(ludoBoard.createFinalPane(players), 1, 1);
		
		
		// Adds the gameBoard to the base bdrPane
		//bdrPane.getChildren().add(gameBoard);
		bdrPane.setLeft(gameBoard);
		
		// adds txtOutput to the right side of the bdrpane
		bdrPane.setRight(txtOutput);
		bdrPane.setAlignment(txtOutput, Pos.BOTTOM_CENTER); // Centered vertically
		txtOutput.setPadding(new Insets(10));
		
		
		
		// Gets the screen resolution
		var screenRes = Toolkit.getDefaultToolkit().getScreenSize();
		
		// Move dicebar to bottom of bdrPane, fixes formatting issues with gameBoard
		bdrPane.setBottom(diceBar);
		
		
		
		
		// Add bdrPane to scene
		Scene scene = new Scene(bdrPane);
		
		// sets scene 
		stage.setTitle("Ludo");
		
		
		// Set stage
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show(); //Shows stage on screen
		
		// Moves the txtOutputnode to the left dynamically depending on Screen resolution
		txtOutput.setTranslateX(-(stage.getWidth() / 25));
		txtOutput.setTranslateY(stage.getHeight() / 25);
		
		// Orders the tiles to make pawn movement easier in code
		for (int i = 0; i < 4; i++)
		{
			players[i].orderTiles();
		}
	}
	
	// Main method for launching program
	public static void main(String[] args) {
		launch(args);
	}
	
}
