import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Team 
{
	// Initializing class variables
	String name;
	Color color;
	GridPane tiles;
	Pawn[] pawns;
	Rectangle finalSquare;
	
	// Team class constructor method
	public Team(String name, Color color, GridPane tiles)
	{
		this.name = name;
		this.color = color;
		this.tiles = tiles;
		this.pawns = new Pawn[4];
		this.finalSquare = null;
	}
}
