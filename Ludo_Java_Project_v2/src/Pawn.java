import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Pawn {
	
	// Initializing class variables
	int Team;
	int Number;
	int[] pos; 
	Circle circle;
	
	// Pawn class constructor method
	public Pawn(int pawnTeam, int pawnNum, int[] pos, double circleSize, javafx.scene.paint.Color color) 
	{
		this.Team = pawnTeam;
		this.Number = pawnNum;
		this.pos = pos;
		this.circle = new Circle(circleSize, color);
	}
	
	// Returns the Pawns position
	public int[] getPosition()
	{
		return this.pos;
	}
	
}
