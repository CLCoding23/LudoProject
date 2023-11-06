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
		
		// Initialize Gridpanes for tiles
		GridPane p1Tiles = new GridPane();
		GridPane p2Tiles = new GridPane();
		GridPane p3Tiles = new GridPane();
		GridPane p4Tiles = new GridPane();
		
		// Creates Team object for each of the 4 players
		Team player1 = new Team("Player 1", Color.BLUE, p1Tiles);
		Team player2 = new Team("Player 2", Color.YELLOW, p2Tiles);
		Team player3 = new Team("Player 3", Color.RED, p3Tiles);
		Team player4 = new Team("Player 4", Color.GREEN, p4Tiles);
		
		// puts them in an array
		Team players[] = {player1, player2, player3, player4};
		
		// loops 4 times, 1 for each player in players array
		for(int i = 0; i < players.length; i++)
		{
			// variable used to determine pawn number to order list for movement
			int pawnCount = 0;
			
			// sets padding and margins for gridpanes
			players[i].tiles.setPadding(new Insets(5));
			players[i].tiles.setHgap(4);
			players[i].tiles.setVgap(4);
			
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
					Rectangle square = new Rectangle(50f, 50f);
					Tile gameTile = new Tile(players[i].name, square, pos, false);
					// If statement to determine starting tiles, and colors them accordingly
					if(i == 0 && j == 1 && k == 0 || i == 1 && j == 0 && k == 4 || i == 2 && j == 4 && k == 2 || i == 3 && j == 2 && k == 1)
					{
						gameTile.square.setFill(players[i].color);
					}
					else 
					{ 
						gameTile.square.setFill(Color.GREY); 
					}
					
					gameTile.square.setStroke(players[i].color);
					
					//adds the tile to the players gameTile gridpane
					players[i].tiles.add(gameTile.square, j,  k);
					
					pawnCountTxt.setText(Integer.toString(pawnCount));
					try
					{
						players[i].tiles.add(pawnCountTxt,  j,  k);
					}
					catch (Exception e)
					{
						System.out.println(e);
					}
					pawnCount += 1;
				}
				
			}
			//aligns the tiles to the center
			players[i].tiles.setAlignment(Pos.CENTER);
		}
		//returns the players array when the method is called
		return players;
	}
	
	static public Team orderBlueTiles(Team player)
	{
		ObservableList<Node> blueTilesList = player.tiles.getChildren();
		Object[] blueTilesArray = blueTilesList.toArray();
		System.out.print(blueTilesArray.toString());
		
		return player;
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

