/*
 * Ludo Board Game Project
 * Collin Lane & Reza Naqvi
 * CPT-237-W37
 * FALL23
 */

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class LudoEngine extends Application {
   
   private MediaPlayer mediaPlayer;
   public boolean isMuted = false;
   private Button muteButton;


	@Override
	public void start(Stage stage) {
		
		LudoBoard ludoBoard = new LudoBoard();
      
      //Loads Audio File
      String musicFile = "BGM_03.wav";
      Media sound = new Media(getClass().getClassLoader().getResource(musicFile).toString());
      
      // Create a media player
      mediaPlayer = new MediaPlayer(sound);

      // Set the media player to loop indefinitely
      mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      mediaPlayer.setVolume(0.5); // Set volume to 50%

      // Start playing the background music
      mediaPlayer.play();
		
		
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
      
      // Mute button
      muteButton = new Button("Mute");
      muteButton.setMinSize(100, 50);
      muteButton.setOnAction(e -> toggleMute());
		
		// Creates a button for rolling die
		Random rand = new Random();
		Button btnDice = new Button("Roll Dice");
		btnDice.setMinSize(100, 50);
      
      //Exit Button
      Button exitButton = new Button("Exit");
      exitButton.setMinSize(100, 50);
      exitButton.setOnAction(e -> closeApplication());
		
		// Button for testing moving blue pawn
		Button btnMove = new Button("Move Pawn");
		btnMove.setMinSize(100, 50); // Sets button size
		
		// Adds a text output area and formats it to the center of the right sight of the scene
		TextArea txtOutput = new TextArea();
		txtOutput.setText("Roll the dice then pick a Pawn!");
		//txtOutput.setMaxSize(450, 350);
		txtOutput.setMaxSize(400, 100);
		txtOutput.setEditable(false);
		
		// Text area for die value
		TextArea rollOutput = new TextArea();
		
		// Minimize as it was causing formatting issues
		rollOutput.setMaxSize(50, 50);
		
		
		// FOR ROLLING DICE
		btnDice.setOnAction(e->
		{
		    int diceRoll = rand.nextInt(6) + 1; //Fixed: Dice now rolls 1-6
		    
            rollOutput.setText(Integer.toString(diceRoll));
            
            ludoBoard.setDistance(diceRoll); // Set property in ludoBoard object so data persists across action events
            
            btnDice.setDisable(true); // Only allow user to roll dice once a turn
            
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
			
            if(hasPawnStarted == false  && ludoBoard.distance != 6)
			{
				txtOutput.setText(ludoBoard.nextTurn(players, "You must enter a 6 to start a pawn! \n\n"));
				
				btnDice.setDisable(false);
				
			}
            
		});
		btnMove.setDisable(true);
		
		
		
		// Adds both buttons and diceOutput text area to diceBar
		diceBar.getChildren().addAll(btnDice, rollOutput, btnMove, muteButton, exitButton);
		
		diceBar.setSpacing(3);
		
		//gameBoard.add(diceBar, 1, 3); // adds nodes to gameBoard
		
		
		
		
		List<Pawn> pawnList = new ArrayList<Pawn>();
		
		for(Team player : players)
		{
			for(Pawn pawn : player.pawns) {
				pawn.setOnMouseClicked(e -> {
					if(ludoBoard.distance == 0)
					{
						txtOutput.setText("Please roll the dice before selecting which pawn to move");
					}
					else
					{
						if(pawn.team == ludoBoard.playerTurn || ludoBoard.selectPawn == null)
						{
							txtOutput.setText("You have chosen pawn " + pawn.number + " of " + pawn.team.name);
							
							ludoBoard.selectPawn = pawn;
							
							if(ludoBoard.playerTurn == null)
							{
								ludoBoard.playerTurn = player;
							}
							
							if(pawn.started == false && ludoBoard.distance != 6)
							{
								txtOutput.setText(ludoBoard.nextTurn(players, "You must enter a 6 to start your pawn! \n"));
								
								btnDice.setDisable(false);
							}
							
							else if(pawn.started == false && ludoBoard.distance == 6) 
							{
								txtOutput.setText(pawn.startPawn(player, players, ludoBoard.distance, ludoBoard, ""));
								
								// Dice must be rolled first to move
								if(ludoBoard.distance == 0) btnMove.setDisable(true);
								// pawn is selected, enable dice rolling
								btnDice.setDisable(false);
							}
							else if(pawn.team == ludoBoard.playerTurn)
							{
								txtOutput.setText(pawn.movePawn(players, ludoBoard.distance, ludoBoard, ""));
								
								System.out.println(ludoBoard.selectPawn.number);

								btnDice.setDisable(false);
							}
						}
						else
						{
							txtOutput.setText("It is " + ludoBoard.playerTurn.name + "'s turn \n" + 
									"You have selected " + pawn.team.name);
						}
						
					}
					
				});
			}
		}
		
		// Event handler for button, calls the moveBlue() function, and prints the affected pawns position to the console
		btnMove.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            	String outputString = "It was " + ludoBoard.playerTurn.name + "'s turn \n";
            	
            	Pawn pawn = new Pawn();
            	if(ludoBoard.selectPawn == null)
            	{
            		txtOutput.setText("Please pick a pawn before moving!");
            	}
            	else
            	{
            		// If the pawn is at the spawnpoint, move it to the tile board
                	if (pawn.started == false) 
                	{ 
                		txtOutput.setText(pawn.startPawn(pawn.team, players, ludoBoard.distance, ludoBoard, outputString));
                		
                	}
                	// if it has started, move it however many tiles
                	else 
                	{
                		String moveString = pawn.movePawn(players, ludoBoard.getDistance(), ludoBoard, outputString);
                		
                		int[] pawnPos = pawn.getPosition();
                		
                		txtOutput.setText("The pawn is at Tile Area " + pawnPos[0] + " Tile " + pawnPos[1]);
                	}
                	
                	//Enable dice button after movement, prevent cheating
                	btnDice.setDisable(false);
                	
                	// Disable move button as it serves no purpose at the given time
                	btnMove.setDisable(true);
                	
                	// Used to make sure both buttons don't get disabled
                	ludoBoard.distance = 0;
            	}
            }
        }));
		
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
		
		
		
		//gameBoard.setPadding(new Insets(screenRes.getWidth() / 50 ));
		
		// Move dicebar to bottom of bdrPane, fixes formatting issues with gameBoard
		bdrPane.setBottom(diceBar);
		
		
		
		
		// Add bdrPane to scene
		Scene scene = new Scene(bdrPane);
		
		// sets scene 
		stage.setTitle("Ludo");
		
		
		
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
		
		// Moves the txtOutputnode to the left dynamically depending on Screen resolution
		txtOutput.setTranslateX(-(stage.getWidth() / 2));
		txtOutput.setTranslateY(stage.getHeight() / 15);
		
		//TODO - Delete Testing  method
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
   
   private void toggleMute() //Called from muteButton in order for mute button to work
   {
      isMuted = !isMuted;
      if (isMuted) 
      {
         mediaPlayer.setVolume(0.0); //Mutes music
         muteButton.setText("Unmute"); //Changes text
      } else 
      {
         mediaPlayer.setVolume(0.5); // Unmutes Music
         muteButton.setText("Mute"); //Changes Text
      }
   }
   
   private void closeApplication() { //Called from exitButton in order for exit button to work
        System.exit(0);
    }
	
	// Main method for launching program
	public static void main(String[] args) {
		launch(args);
	}
	
}
