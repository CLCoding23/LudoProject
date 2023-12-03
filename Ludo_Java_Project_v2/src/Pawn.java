import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Pawn extends Circle {
	
	// Initializing class variables
	Team team;
	int number;
	boolean started;
	int[] position;
	int tilesMoved = 0;
	int areasPassed = 0;
	boolean finalStretch = false;
	
	public Pawn() {} //base constructor
	
	// Pawn class constructor method
	public Pawn(Team pawnTeam, int pawnNum, int[] pos) 
	{
		this.team = pawnTeam;
		this.number = pawnNum;
		this.started = false;
		this.position = pos;
	}
	
	// Gets the first pawn from the player's pawns and places it on it's starting tile
	public String startPawn(Team player, Team[] players, int distance, LudoBoard ludoBoard, String outputString)
	{
		// Passed to txtOutput of LudoEngine
		String returnString;
		
		// Start pawn if player rolled 6
		//TODO allow player to roll again after pawnsart
		if (distance == 6)
		{
			player.tiles[8].getChildren().add(this); // add to respective players starting tile
			
			// Sets pawn position at starting point
			this.position[0] = player.teamId - 1;
			this.position[1] = 8;
			this.setRadius(20);
			this.setTranslateX(0);
			this.setTranslateY(0);
			
			// so program knows to move across tiles instead of start
			this.started = true;
			
			// Informs users the pawn has started
			returnString = this.team.name + " rolled a 6 and Pawn " + this.number + " has been moved on the board! \n";
			
        	returnString += ludoBoard.playerTurn.name + " rolled a 6 and gets to go again!\n";
		}
		else // when user tries to start pawn without a 6 rolled
		{
			returnString = "You must roll a 6 to start your pawn. \n";

			//next player's turn
			returnString += ludoBoard.nextTurn(players, returnString);
		}
		
		
		
		ludoBoard.distance = 0;
		
		return returnString;
	}
		
	// Returns the Pawns position
	public int[] getPosition()
	{
		return this.position;
	}
	
	// Sets the pawns position and updates the position the board
	public String setPosition(Team[] players, int distance, LudoBoard ludoBoard, String outputString)
	{
		
		int loopTracker = 0;
		
		while(loopTracker < distance)
		{
			int[] posCopy = new int[2];
			System.arraycopy(this.position, 0, posCopy, 0, 2);
			
			// Sets the pawns position to the next tile ahead
			this.position[1] = posCopy[1] + 1;
			
			//Tracking number of tiles crossed to know when to finish
			this.tilesMoved++;
			
			if(this.position[1] == 13) //Changes to the next tile board if it hits 13
			{
				this.areasPassed++;
				
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
			// IF looped around, send pawn down final stretch
			else if(this.tilesMoved == 50 && this.areasPassed == 4)
			{
				if (this.finalStretch == true) 
				{
					try
					{
						players[this.position[0]].tiles[this.position[1]].getChildren().add(this);
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						//ludoBoard
					}
				}
				else 
				{
					this.position[1] = 14;
					
					players[this.position[0]].tiles[this.position[1]].getChildren().add(this);
					
					this.finalStretch = true;
				}
				
			}
			// Maybe where to add capturing/Blocking code?
			else
			{
				
			}
			loopTracker++;
		}
		
		// If rolled 6 3 times in a row, next persons turn
		int sixTracker = 0;
		
		// User rolls again since a 6 was rolled, can only happen thrice
		if (ludoBoard.distance == 6 && sixTracker < 3)
		{
			outputString += "\n" + this.team.name + " rolled a 6 and gets to go again! you can do this " + (3 - sixTracker) + " more times";
			
			sixTracker++;
			
		}
		// no 6 was rolled, next player turn
		else
		{
			outputString += ludoBoard.nextTurn(players, outputString);
		}
    	
    	return outputString;
	}
			
	//Method testing blue pawn movement
	public String movePawn(Team[] players, int distance, LudoBoard ludo, String string)
	{
		String moveString = this.setPosition(players, distance, ludo, string);
		System.out.print(this.getPosition());
		
		ludo.distance = 0;
		
		return moveString;
	}
}
