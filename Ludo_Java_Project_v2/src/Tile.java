import javafx.scene.shape.Rectangle;

class Tile 
{
	
	//
	String tileTeam;
	Rectangle square;
	int[] position;
	boolean occupied;
	
	// Tile class constructor method
	public Tile(String team, Rectangle square, int[] pos, boolean occupied)
	{
		this.tileTeam = team;
		this.square = square;
		this.position = pos;
		this.occupied = occupied;
	}
}
