import javafx.scene.layout.StackPane;

//Possibly extend stackpane
class Tile extends StackPane
{
	
	//
	String tileTeam;
	int[] position;
	boolean occupied;
	
	// Tile class constructor method
	public Tile(String team, int[] pos, boolean occupied)
	{
		this.tileTeam = team;
		this.position = pos;
		this.occupied = occupied;
	}
	
	
}
