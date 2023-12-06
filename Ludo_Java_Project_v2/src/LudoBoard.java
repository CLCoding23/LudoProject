import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * This is the LudoBoard class, for creating the LudoBoard GUI using JavaFX
 * 
 * It has a empty constructor
 * 
 * Used to create tiles, pawn spawns, the final pane, and changing player turns
 */



class LudoBoard 
{	
	int distance;
	Team playerTurn;
	Pawn selectPawn;
	
	public LudoBoard() {}
	
	//Creates the tiles and player objects, return them to caller method
	public Team[] createTiles()
	{
		StackPane[] tilesArray = new StackPane[72];
		int tileCount = 0;
		
		// Initialize Gridpanes for tilePane
		GridPane p1tilePane = new GridPane();
		GridPane p2tilePane = new GridPane();
		GridPane p3tilePane = new GridPane();
		GridPane p4tilePane = new GridPane();
		
		// Initialize Stackpane for tiles for players
		StackPane[] p1Tiles = new StackPane[18];
		StackPane[] p2Tiles = new StackPane[18];
		StackPane[] p3Tiles = new StackPane[18];
		StackPane[] p4Tiles = new StackPane[18];
		
		// Creates Team object for each of the 4 players
		Team player1 = new Team("Player 1", 1, Color.BLUE, p1tilePane, p1Tiles);
		Team player2 = new Team("Player 2", 2, Color.GREEN, p2tilePane, p2Tiles);
		Team player3 = new Team("Player 3", 3, Color.RED, p3tilePane, p3Tiles);
		Team player4 = new Team("Player 4", 4, Color.YELLOW, p4tilePane, p4Tiles);
		
		// puts them in an array
		Team players[] = {player1, player2, player3, player4};
		
		// loops 4 times, 1 for each player in players array
		for(int i = 0; i < players.length; i++)
		{
			// variable used to determine pawn number to order list for movement
			int pawnCount = 0;
			
			// int variables to determine tile placements based on the iteration count
			int col;
			int row;
			
			// Determins layout of tile grid
			if(i % 2 == 1)
			{
				col = 3;
				row = 6;
			}
			else
			{
				col = 6;
				row = 3;
			}
			// loops for placing tile objects on player tile boards
			for(int j = 0; j < col; j++)
			{
				for(int k = 0; k < row; k++)
				{
					// Int array used to set tile position
					int[] pos = {j, k};
					
					
					// Creates a gameTile object that is used for pawn movement
					Tile gameTile = new Tile(players[i].name, pos, false);
					
					// Rectangle object representing tile
					Rectangle square = new Rectangle(40f, 40f);
					gameTile.getChildren().add(square);
					
					// Assigns the tile to the respective players tile array
					players[i].tiles[pawnCount] = gameTile;
					
					// If statement to determine starting tilePane, and colors them accordingly
					if(i == 0 && j == 1 && k == 0 || i == 1 && j == 0 && k == 4 || i == 2 && j == 4 && k == 2 || i == 3 && j == 2 && k == 1)
					{
						// Changing starting tile to grey background, but with thick team color border
						square.setFill(Color.GREY);
						square.setStrokeWidth(5);
					}
					else //For normal tiles
					{ 
						square.setFill(players[i].color.darker()); 
					}
					
					square.setStroke(players[i].color);
					
					
					// sets padding and margins for gridpanes
					players[i].tilePane.setPadding(new Insets(5));
					players[i].tilePane.setHgap(4);
					players[i].tilePane.setVgap(4);
					
					//adds the tile to the players gameTile gridpane
					players[i].tilePane.add(gameTile, j,  k);
					
					
					pawnCount += 1;
				}
				
			}
			//aligns the tilePane to the center
			players[i].tilePane.setAlignment(Pos.CENTER);
		}
		//returns the players array when the method is called
		return players;
	}	
	
	// Creates Pawns and pawn spawn areas
	static public void createPawns(Team[] players, StackPane[] stacks) {
		// for each player
		for(int i = 0; i < 4; i++)
		{
			// Creates circle object where pawns are first placed, sets colors
			Circle pawnSpawn = new Circle(100, 100, 100);
			pawnSpawn.setFill(players[i].color.desaturate());
			pawnSpawn.setStroke(Color.BLACK);
			pawnSpawn.setStrokeWidth(2.5);
			
			// Labels each player under Pawn Spawn
			Label playerLbl = new Label(players[i].name);
			playerLbl.setTranslateY(125);
			stacks[i].getChildren().add(playerLbl);
			players[i].pawnSpawn = stacks[i];
			
			// 4 pawns on each team
			for(int j = 0; j < 4; j++)
			{
				// Creates 4 pawns for each team, and puts them in the players pawns array
				int[] pawnPos = {players[i].teamId, j + 1};
				players[i].pawns[j] = new Pawn(players[i], j + 1, pawnPos);
				players[i].pawns[j].setRadius(35f);
				players[i].pawns[j].setFill(players[i].color);
				players[i].pawns[j].setStroke(Color.BLACK);
				players[i].pawns[j].setStrokeWidth(2);			
			}
			
			// Adds pawns to respective stackpanes
			stacks[i].getChildren().addAll(pawnSpawn, players[i].pawns[0], players[i].pawns[1], players[i].pawns[2], players[i].pawns[3]);
			
			
			// Moves the placements of the pawns, so all are displayed separately
			// Translate persists across movement, so must Un-Translate when moving to tiles
			players[i].pawns[0].setTranslateX(-40.00);
			players[i].pawns[0].setTranslateY(40.00);
			
			players[i].pawns[1].setTranslateX(40.00);
			players[i].pawns[1].setTranslateY(40.00);
			
			players[i].pawns[2].setTranslateX(-40.00);
			players[i].pawns[2].setTranslateY(-40.00);
			
			players[i].pawns[3].setTranslateX(40.00);
			players[i].pawns[3].setTranslateY(-40.00);
 
		}
	}
		
	
	// Adds squares for final pawn area
	public GridPane createFinalPane(Team[] players)
	{
		// Creates gridPane to place final squares
		GridPane finalGrid = new GridPane();
		
		// Rectangle array holding final squares
		Rectangle[] finalSquares = new Rectangle[4];
		
		// Creates, places, and formats final squares
		for(int i = 0; i < players.length; i++)
		{
			// Rectangle used for final square areas, contain player's score
			Rectangle finalSquare = new Rectangle(75, 75);

			//Adds border to final Panes
			finalSquare.setStroke(Color.BLACK);
			finalSquare.setStrokeWidth(2.5);
			
			// Rotates and translates each final square accordingly to make the design
			finalSquare.setRotate(45);
			if(i == 0) finalSquare.setTranslateX(20);
			if(i == 1) finalSquare.setTranslateY(-20);
			if(i == 2) finalSquare.setTranslateX(-20);
			if(i == 3) finalSquare.setTranslateY(20);
			finalSquare.setFill(players[i].color); // add team color
			finalSquares[i] = finalSquare; // add to array for use later
			
			
			// Create labels for player scores
			players[i].scoreLabel = new Label("0");
			
			players[i].scoreLabel.setScaleX(4);
			players[i].scoreLabel.setScaleY(4);

			
			
			// Translate score labels so they display in the center of their respective final square panes
			if(i == 0) players[i].scoreLabel.setTranslateX(55);
			if(i == 1)
			{
				players[i].scoreLabel.setTranslateX(35);
				players[i].scoreLabel.setTranslateY(-20);
			}
			if(i == 2) players[i].scoreLabel.setTranslateX(15);
			if(i == 3)
			{
				players[i].scoreLabel.setTranslateY(20);
				players[i].scoreLabel.setTranslateX(35);
			}
			
		}
		
		// adds final squares to the grid
		finalGrid.add(finalSquares[0], 0, 1);
		finalGrid.add(finalSquares[1], 1, 2);
		finalGrid.add(finalSquares[2], 2, 1);
		finalGrid.add(finalSquares[3], 1, 0);
		
		// Places final squares in gridPane
		finalGrid.add(players[0].scoreLabel, 0, 1);
		finalGrid.add(players[1].scoreLabel, 1, 2);
		finalGrid.add(players[2].scoreLabel, 2, 1);
		finalGrid.add(players[3].scoreLabel, 1, 0);
		
		//return finalgrid to method call
		return finalGrid;
	}
	
	public void setDistance(int diceValue)
	{
		this.distance = diceValue; // Sets distance pawn will travel
	}
	
	public int getDistance() 
	{
		return this.distance; // Returns the distance that the pawn will travel
	}
	
	// Sets the current turn to the next player once the current player's turn is over
	public String nextTurn(Team[] players, String string)
	{
		try
    	{	
    		this.playerTurn = players[this.playerTurn.teamId];
    	}
    	catch (IndexOutOfBoundsException e)
    	{
    		this.playerTurn = players[0];
    	}
    	string += "It is now " + this.playerTurn.name + "'s turn";
    	
    	this.distance = 0;
    	
    	return string;
	}
	
}

