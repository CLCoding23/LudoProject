import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Pawn {
	
	// Initializing class variables
	int team;
	int number;
	int[] position; 
	Circle circle;
	
	// Pawn class constructor method
	public Pawn(int pawnTeam, int pawnNum, int[] pos, double circleSize, Color color) 
	{
		this.team = pawnTeam;
		this.number = pawnNum;
		this.position = pos;
		this.circle = new Circle(circleSize, color);
	}
	
	
}
