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
	static public Team[] createTiles()
	{
		
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
		Team player1 = new Team("Player 1", Color.BLUE, p1tilePane, p1Tiles);
		Team player2 = new Team("Player 2", Color.YELLOW, p2tilePane, p2Tiles);
		Team player3 = new Team("Player 3", Color.RED, p3tilePane, p3Tiles);
		Team player4 = new Team("Player 4", Color.GREEN, p4tilePane, p4Tiles);
		
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
					
					Text pawnCountTxt = new Text();
					
					
					int[] pos = {j, k};
					
					// Create stackpane for tile and add it to player object
					StackPane stackTile = new StackPane();
					players[i].tiles[pawnCount] = stackTile;
					Rectangle square = new Rectangle(50f, 50f);
					stackTile.getChildren().add(square);
					Tile gameTile = new Tile(players[i].name, stackTile, pos, false);
					// If statement to determine starting tilePane, and colors them accordingly
					if(i == 0 && j == 1 && k == 0 || i == 1 && j == 0 && k == 4 || i == 2 && j == 4 && k == 2 || i == 3 && j == 2 && k == 1)
					{
						square.setFill(players[i].color);
					}
					else 
					{ 
						square.setFill(Color.GREY); 
					}
					
					square.setStroke(players[i].color);
					
					
					// sets padding and margins for gridpanes
					players[i].tilePane.setPadding(new Insets(5));
					players[i].tilePane.setHgap(4);
					players[i].tilePane.setVgap(4);
					
					//adds the tile to the players gameTile gridpane
					players[i].tilePane.add(stackTile, j,  k);
					
					pawnCountTxt.setText(Integer.toString(pawnCount));
					try
					{
						//players[i].tilePane.add(pawnCountTxt,  j,  k);
					}
					catch (Exception e)
					{
						System.out.println(e);
					}
					pawnCount += 1;
				}
				
			}
			//aligns the tilePane to the center
			players[i].tilePane.setAlignment(Pos.CENTER);
		}
		//returns the players array when the method is called
		return players;
	}
	
	// Returns an array of the Blue tilePane in order of tile movement
	static public StackPane[] orderBlueTiles(Team player)
	{
		StackPane[] bluetilePaneRef = player.tiles;
		
		// Uses the bluetilePaneRef to order the rectangles in order of how pawns move across them, allowing easier pawn movement
		player.tiles[0] = bluetilePaneRef[17];
		player.tiles[1] = bluetilePaneRef[14];
		player.tiles[2] = bluetilePaneRef[11];
		player.tiles[3] = bluetilePaneRef[8];
		player.tiles[4] = bluetilePaneRef[5];
		player.tiles[5] = bluetilePaneRef[2];
		player.tiles[6] = bluetilePaneRef[1];
		player.tiles[7] = bluetilePaneRef[0];
		player.tiles[8] = bluetilePaneRef[3];
		player.tiles[9] = bluetilePaneRef[6];
		player.tiles[10] = bluetilePaneRef[9];
		player.tiles[11] = bluetilePaneRef[12];
		player.tiles[12] = bluetilePaneRef[15];
		player.tiles[13] = bluetilePaneRef[4];
		player.tiles[14] = bluetilePaneRef[7];
		player.tiles[15] = bluetilePaneRef[10];
		player.tiles[16] = bluetilePaneRef[13];
		player.tiles[17] = bluetilePaneRef[16];
		
		return player.tiles;
	}
	
	// Returns an array of the Yellow tilePane in order of tile movement
	static public StackPane[] orderYellowTiles(Team player)
	{
		StackPane[] yellowtilePaneRef = player.tiles;
		
		// Uses the bluetilePaneRef to order the rectangles in order of how pawns move across them, allowing easier pawn movement
		player.tiles[0] = yellowtilePaneRef[12];
		player.tiles[1] = yellowtilePaneRef[13];
		player.tiles[2] = yellowtilePaneRef[14];
		player.tiles[3] = yellowtilePaneRef[15];
		player.tiles[4] = yellowtilePaneRef[16];
		player.tiles[5] = yellowtilePaneRef[17];
		player.tiles[6] = yellowtilePaneRef[11];
		player.tiles[7] = yellowtilePaneRef[5];
		player.tiles[8] = yellowtilePaneRef[4];
		player.tiles[9] = yellowtilePaneRef[3];
		player.tiles[10] = yellowtilePaneRef[2];
		player.tiles[11] = yellowtilePaneRef[1];
		player.tiles[12] = yellowtilePaneRef[0];
		player.tiles[13] = yellowtilePaneRef[10];
		player.tiles[14] = yellowtilePaneRef[9];
		player.tiles[15] = yellowtilePaneRef[8];
		player.tiles[16] = yellowtilePaneRef[7];
		player.tiles[17] = yellowtilePaneRef[6];
		
		
		return player.tiles;
	}
		
	// Returns an array of the Red tilePane in order of tile movement
	static public StackPane[] orderRedTiles(Team player)
	{
		StackPane[] redtilePaneRef = player.tiles;
		
		// Uses the bluetilePaneRef to order the rectangles in order of how pawns move across them, allowing easier pawn movement
		player.tiles[0] = redtilePaneRef[0];
		player.tiles[1] = redtilePaneRef[3];
		player.tiles[2] = redtilePaneRef[6];
		player.tiles[3] = redtilePaneRef[9];
		player.tiles[4] = redtilePaneRef[12];
		player.tiles[5] = redtilePaneRef[15];
		player.tiles[6] = redtilePaneRef[16];
		player.tiles[7] = redtilePaneRef[17];
		player.tiles[8] = redtilePaneRef[14];
		player.tiles[9] = redtilePaneRef[11];
		player.tiles[10] = redtilePaneRef[8];
		player.tiles[11] = redtilePaneRef[5];
		player.tiles[12] = redtilePaneRef[2];
		player.tiles[13] = redtilePaneRef[13];
		player.tiles[14] = redtilePaneRef[10];
		player.tiles[15] = redtilePaneRef[7];
		player.tiles[16] = redtilePaneRef[4];
		player.tiles[17] = redtilePaneRef[1];
		
		
		return player.tiles;
	}
				
	// Returns an array of the Blue tilePane in order of tile movement
	static public StackPane[] ordergreenTiles(Team player)
	{
		StackPane[] greentilePaneRef = player.tiles;
		
		// Uses the bluetilePaneRef to order the rectangles in order of how pawns move across them, allowing easier pawn movement
		player.tiles[0] = greentilePaneRef[5];
		player.tiles[1] = greentilePaneRef[4];
		player.tiles[2] = greentilePaneRef[3];
		player.tiles[3] = greentilePaneRef[2];
		player.tiles[4] = greentilePaneRef[1];
		player.tiles[5] = greentilePaneRef[0];
		player.tiles[6] = greentilePaneRef[6];
		player.tiles[7] = greentilePaneRef[12];
		player.tiles[8] = greentilePaneRef[13];
		player.tiles[9] = greentilePaneRef[14];
		player.tiles[10] = greentilePaneRef[15];
		player.tiles[11] = greentilePaneRef[16];
		player.tiles[12] = greentilePaneRef[17];
		player.tiles[13] = greentilePaneRef[7];
		player.tiles[14] = greentilePaneRef[8];
		player.tiles[15] = greentilePaneRef[9];
		player.tiles[16] = greentilePaneRef[10];
		player.tiles[17] = greentilePaneRef[11];
		
		
		return player.tiles;
	}				
		
	
	// Adds squares for final pawn area
	static public GridPane createFinalPane(Team[] players)
	{
		GridPane finalGrid = new GridPane();
		for(int i = 0; i < players.length; i++)
		{
			Rectangle finalSquare = new Rectangle(100, 100);
			finalSquare.setFill(players[i].color);
			players[i].finalSquare = finalSquare;
		}
		finalGrid.add(players[0].finalSquare, 0, 1);
		finalGrid.add(players[1].finalSquare, 1, 2);
		finalGrid.add(players[2].finalSquare, 2, 1);
		finalGrid.add(players[3].finalSquare, 1, 0);
		
		return finalGrid;
	}
		
	
}

