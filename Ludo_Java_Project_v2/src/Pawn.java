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
	
	// Returns the Pawns position
	public int[] getPosition()
	{
		return this.position;
	}
	
	// Sets the pawns position and updates the positionon the board
	public void setPosition(Team[] players)
	{
		// Sets the pawns position to the next tile ahead
		this.position[1] = this.position[1] + 1;
		if(this.position[1] == 13) //Changes to the next tile board if it hits 13
		{
			// Attempts to update the tile board position, resets to 3 if at 0
			try
			{
				this.position[0] = this.position[0] - 1; 
				this.position[1] = 0;
				players[this.position[0]].tiles[this.position[1]].getChildren().add(this.circle);
			}
			catch(Exception e)
			{
				this.position[0] = 3;
				players[this.position[0]].tiles[this.position[1]].getChildren().add(this.circle);
			}
			
		}
		// If all is good the pawn.circle is placed in the correct tile
		else
		{
			players[this.position[0]].tiles[this.position[1]].getChildren().add(this.circle);
		}
	}
			
			//Method testing blue pawn movement
			public void moveBlue(Team[] players)
			{
				this.setPosition(players);
				System.out.print(this.getPosition());
			}
}
