import java.lang.reflect.Array;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Team 
{
	// Initializing class variables
	String name;
	Color color;
	GridPane tilePane;
	StackPane[] tiles;
	Pawn[] pawns;
	Rectangle finalSquare;
	
	// Team class constructor method
	public Team(String name, Color color, GridPane tilePane, StackPane[] tiles)
	{
		this.name = name;
		this.color = color;
		this.tilePane = tilePane;
		this.tiles = tiles;
		this.pawns = new Pawn[4];
		this.finalSquare = null;
	}
	
	public void startPawn()
	{
		this.tiles[8].getChildren().add(this.pawns[0].circle);
		this.pawns[0].circle.setTranslateX(0);
		this.pawns[0].circle.setTranslateY(0);
		
	}
	
	// Returns an array of the players' tilePanes in order of tile movement, order is determined by Team.playername in an if/else statement
	public StackPane[] orderTiles()
	{
		StackPane[] tilePaneRef = new StackPane[18];
		System.arraycopy(this.tiles, 0, tilePaneRef, 0, 18);
		if(this.name == "Player 1")
		{	
			// Uses the tilePaneRef to order the rectangles in order of how pawns move across them, allowing easier pawn movement
			this.tiles[0] = tilePaneRef[17];
			this.tiles[1] = tilePaneRef[14];
			this.tiles[2] = tilePaneRef[11];
			this.tiles[3] = tilePaneRef[8];
			this.tiles[4] = tilePaneRef[5];
			this.tiles[5] = tilePaneRef[2];
			this.tiles[6] = tilePaneRef[1];
			this.tiles[7] = tilePaneRef[0];
			this.tiles[8] = tilePaneRef[3];
			this.tiles[9] = tilePaneRef[6];
			this.tiles[10] = tilePaneRef[9];
			this.tiles[11] = tilePaneRef[12];
			this.tiles[12] = tilePaneRef[15];
			this.tiles[13] = tilePaneRef[4];
			this.tiles[14] = tilePaneRef[7];
			this.tiles[15] = tilePaneRef[10];
			this.tiles[16] = tilePaneRef[13];
			this.tiles[17] = tilePaneRef[16];
			
			return this.tiles;
		}
		else if(this.name == "Player 2")
		{	
			// Uses the tilePaneRef to order the rectangles in order of how pawns move across them, allowing easier pawn movement
			this.tiles[0] = tilePaneRef[12];
			this.tiles[1] = tilePaneRef[13];
			this.tiles[2] = tilePaneRef[14];
			this.tiles[3] = tilePaneRef[15];
			this.tiles[4] = tilePaneRef[16];
			this.tiles[5] = tilePaneRef[17];
			this.tiles[6] = tilePaneRef[11];
			this.tiles[7] = tilePaneRef[5];
			this.tiles[8] = tilePaneRef[4];
			this.tiles[9] = tilePaneRef[3];
			this.tiles[10] = tilePaneRef[2];
			this.tiles[11] = tilePaneRef[1];
			this.tiles[12] = tilePaneRef[0];
			this.tiles[13] = tilePaneRef[10];
			this.tiles[14] = tilePaneRef[9];
			this.tiles[15] = tilePaneRef[8];
			this.tiles[16] = tilePaneRef[7];
			this.tiles[17] = tilePaneRef[6];
			
			
			return this.tiles;
		}
		else if(this.name == "Player 3")
		{
			// Uses the tilePaneRef to order the rectangles in order of how pawns move across them, allowing easier pawn movement
			this.tiles[0] = tilePaneRef[0];
			this.tiles[1] = tilePaneRef[3];
			this.tiles[2] = tilePaneRef[6];
			this.tiles[3] = tilePaneRef[9];
			this.tiles[4] = tilePaneRef[12];
			this.tiles[5] = tilePaneRef[15];
			this.tiles[6] = tilePaneRef[16];
			this.tiles[7] = tilePaneRef[17];
			this.tiles[8] = tilePaneRef[14];
			this.tiles[9] = tilePaneRef[11];
			this.tiles[10] = tilePaneRef[8];
			this.tiles[11] = tilePaneRef[5];
			this.tiles[12] = tilePaneRef[2];
			this.tiles[13] = tilePaneRef[13];
			this.tiles[14] = tilePaneRef[10];
			this.tiles[15] = tilePaneRef[7];
			this.tiles[16] = tilePaneRef[4];
			this.tiles[17] = tilePaneRef[1];
			
			
			return this.tiles;
		}
		else 
		{
			// Uses the tilePaneRef to order the rectangles in order of how pawns move across them, allowing easier pawn movement
			this.tiles[0] = tilePaneRef[5];
			this.tiles[1] = tilePaneRef[4];
			this.tiles[2] = tilePaneRef[3];
			this.tiles[3] = tilePaneRef[2];
			this.tiles[4] = tilePaneRef[1];
			this.tiles[5] = tilePaneRef[0];
			this.tiles[6] = tilePaneRef[6];
			this.tiles[7] = tilePaneRef[12];
			this.tiles[8] = tilePaneRef[13];
			this.tiles[9] = tilePaneRef[14];
			this.tiles[10] = tilePaneRef[15];
			this.tiles[11] = tilePaneRef[16];
			this.tiles[12] = tilePaneRef[17];
			this.tiles[13] = tilePaneRef[7];
			this.tiles[14] = tilePaneRef[8];
			this.tiles[15] = tilePaneRef[9];
			this.tiles[16] = tilePaneRef[10];
			this.tiles[17] = tilePaneRef[11];
			
			
			return this.tiles;
		}
		
	}
}
