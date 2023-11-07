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
		this.tiles[0].getChildren().add(this.pawns[0].circle);
	}
}
