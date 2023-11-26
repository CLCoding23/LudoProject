import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Pawn extends Circle {
	
	// Initializing class variables
	int team;
	int number;
	boolean started;
	int[] position;
	int tilesMoved = 0;
	
	public Pawn() {} //base constructor
	
	// Pawn class constructor method
	public Pawn(int pawnTeam, int pawnNum, int[] pos) 
	{
		this.team = pawnTeam;
		this.number = pawnNum;
		this.started = false;
		this.position = pos;
	}
	
	// Gets the first pawn from the player's pawns and places it on it's starting tile
	public void startPawn(Team player)
	{
		player.tiles[8].getChildren().add(this);
		
		// Sets player position
		this.position[0] = player.teamId;
		this.position[1] = 8;
		this.setRadius(20);
		this.setTranslateX(0);
		this.setTranslateY(0);
		
	}
		
	// Returns the Pawns position
	public int[] getPosition()
	{
		return this.position;
	}
	
	// Sets the pawns position and updates the position the board
	public void setPosition(Team[] players)
	{
		int[] posCopy = new int[2];
		System.arraycopy(this.position, 0, posCopy, 0, 2);
		
		// Sets the pawns position to the next tile ahead
		this.position[1] = posCopy[1] + 1;
		if(this.position[1] == 13) //Changes to the next tile board if it hits 13
		{
			// Attempts to update the tile board position, resets to 3 if at 0
			try
			{
				this.position[0] = posCopy[0] - 1; 
				this.position[1] = 0;
				players[this.position[0]].tiles[this.position[1]].getChildren().add(this);
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				this.position[0] = 3;
				players[this.position[0]].tiles[this.position[1]].getChildren().add(this);
				System.out.println(e);
			}
			
		}
		// If all is good the pawn.circle is placed in the correct tile
		else if(this.position[1] < 13)
		{
			System.out.println(this.position[0]);
			players[this.position[0]].tiles[this.position[1]].getChildren().add(this);
		}
		else if(this.tilesMoved == 48)
		{
			
		}
		else
		{
			
		}
	}
			
			//Method testing blue pawn movement
			public void movePawn(Team[] players)
			{
				this.setPosition(players);
				System.out.print(this.getPosition());
			}
}
