import javafx.scene.layout.StackPane;

class Tile 
{
	
	//
	String tileTeam;
	StackPane stackTile;
	int[] position;
	boolean occupied;
	
	// Tile class constructor method
	public Tile(String team, StackPane stackTile, int[] pos, boolean occupied)
	{
		this.tileTeam = team;
		this.stackTile = stackTile;
		this.position = pos;
		this.occupied = occupied;
	}
}
