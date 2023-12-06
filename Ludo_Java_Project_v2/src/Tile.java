import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/*
 * This class is for the Tiles. It hold data like its position, if it is occupied, and its team
 */


class Tile extends StackPane
{
	
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
