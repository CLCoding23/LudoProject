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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class LudoBoard 
{	
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
					
					//Text pawnCountTxt = new Text();
					
					
					int[] pos = {j, k};
					
					// Create stackpane for tile and add it to player object
					//StackPane stackTile = new StackPane();
					
					
					// Creates a gameTile object that is used for pawn movement
					Tile gameTile = new Tile(players[i].name, pos, false);
					
					// Rectangle object representing tile
					Rectangle square = new Rectangle(40f, 40f);
					gameTile.getChildren().add(square);
					
					
					players[i].tiles[pawnCount] = gameTile;
					
					// If statement to determine starting tilePane, and colors them accordingly
					if(i == 0 && j == 1 && k == 0 || i == 1 && j == 0 && k == 4 || i == 2 && j == 4 && k == 2 || i == 3 && j == 2 && k == 1)
					{
						//square.setFill(players[i].color);
						
						// Changing starting tile to grey background, but with thick team color border
						square.setFill(Color.GREY);
						square.setStrokeWidth(5);
					}
					else 
					{ 
						square.setFill(Color.CORNFLOWERBLUE); 
					}
					
					square.setStroke(players[i].color);
					
					
					// sets padding and margins for gridpanes
					players[i].tilePane.setPadding(new Insets(5));
					players[i].tilePane.setHgap(4);
					players[i].tilePane.setVgap(4);
					
					//adds the tile to the players gameTile gridpane
					players[i].tilePane.add(gameTile, j,  k);
					
					/*pawnCountTxt.setText(Integer.toString(pawnCount));
					try
					{
						//players[i].tilePane.add(pawnCountTxt,  j,  k);
					}
					catch (Exception e)
					{
						System.out.println(e);
					}*/
					pawnCount += 1;
				}
				
			}
			//aligns the tilePane to the center
			players[i].tilePane.setAlignment(Pos.CENTER);
		}
		//returns the players array when the method is called
		return players;
	}	
	
	static public void createPawns(Team[] players, StackPane[] stacks) {
		for(int i = 0; i < 4; i++)
		{
			// Creates circle object where pawns are first placed
			Circle pawnSpawn = new Circle(100, 100, 100);
			pawnSpawn.setFill(Color.BLANCHEDALMOND);
			pawnSpawn.setStroke(Color.BLACK);
			pawnSpawn.setStrokeWidth(2.5);
			
			for(int j = 0; j < 4; j++)
			{
				// Creates 4 pawns for each team, and puts them in the players pawns array
				int[] pawnPos = {players[i].teamId, j + 1};
				players[i].pawns[j] = new Pawn(i, j + 1, pawnPos);
				players[i].pawns[j].setRadius(35f);
				players[i].pawns[j].setFill(players[i].color);
				players[i].pawns[j].setStroke(Color.BLACK);
				players[i].pawns[j].setStrokeWidth(2);			
			}
			
			// Adds pawns to respective stackpanes
			stacks[i].getChildren().addAll(pawnSpawn, players[i].pawns[0], players[i].pawns[1], players[i].pawns[2], players[i].pawns[3]);
			
			
			// Moves the placements of the pawns, so all are displayed separately
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
		//finalGrid.setPadding(new Insets(0));
		
		// Creates, places, and formats final squares
		for(int i = 0; i < players.length; i++)
		{
			Rectangle finalSquare = new Rectangle(75, 75);
			
			BorderPane borderPane = new BorderPane();
			
			//Adds border to final Panes
			finalSquare.setStroke(Color.BLACK);
			finalSquare.setStrokeWidth(2.5);
			
			finalSquare.setRotate(45);
			if(i == 0) borderPane.setStyle("-fx-background-color: #FF0000;");
			if(i == 1) borderPane.setStyle("-fx-background-color: #C0C0C0;");
			if(i == 2) borderPane.setStyle("-fx-background-color: #C0C0C0;");
			if(i == 3) borderPane.setStyle("-fx-background-color: #C0C0C0;");
			finalSquare.setFill(players[i].color);
			players[i].finalBorder = borderPane;
		}
		// Places final squares in gridPane
		finalGrid.add(players[0].finalBorder, 0, 0);
		finalGrid.add(players[1].finalBorder, 1, 1);
		finalGrid.add(players[2].finalBorder, 0, 1);
		finalGrid.add(players[3].finalBorder, 1, 0);
		
		return finalGrid;
	}
	
}

