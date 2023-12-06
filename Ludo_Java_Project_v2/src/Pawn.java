import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/*
 * This is the pawn class and extends the circle class of JavaFx
 * 
 * It contains code for creating and moving pawns
 */


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
		
		// Start pawn if player rolled 6
		if (distance == 6 && player.sixTracker < 3)
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
			outputString += this.team.name + " rolled a 6 and Pawn " + this.number + " has been moved on the board! \n";
			
        	outputString += ludoBoard.playerTurn.name + " rolled a 6 and gets to go again!\n";
        	
        	// User can only roll again 2 times after a 6
        	player.sixTracker ++;
        	
        	// if usr rolled 6 thrice, move on to next user
    		if(player.sixTracker > 2)
    		{
    			outputString += this.team.name + " has rolled a 6 3 times \n";
    			
    			player.sixTracker = 0;
    			
    			outputString += ludoBoard.nextTurn(players, outputString);
    		}
		}
		else // when user tries to start pawn without a 6 rolled
		{
			outputString = "You must roll a 6 to start your pawn. \n";
			
			player.sixTracker = 0;

			//next player's turn
			outputString += ludoBoard.nextTurn(players, outputString);
		}
		
		
		//reset distance for next user
		ludoBoard.distance = 0;
		
		//returns the output string for displaying information
		return outputString;
	}
		
	// Returns the Pawns position
	public int[] getPosition()
	{
		return this.position;
	}
	
	// Sets the pawns position and updates the position the board
	public String setPosition(Team[] players, LudoBoard ludoBoard, String outputString)
	{
		
		int loopTracker = 0;
		
		while(loopTracker < ludoBoard.distance)
		{
			int[] posCopy = new int[2];
			System.arraycopy(this.position, 0, posCopy, 0, 2);
			
			// Sets the pawns position to the next tile ahead
			this.position[1] = posCopy[1] + 1;
			
			//Tracking number of tiles crossed to know when to finish
			this.tilesMoved++;
			
			if(this.position[1] == 13 && this.areasPassed < 4) //Changes to the next tile board if it hits 13
			{
				this.areasPassed++;
				
				// Attempts to update the tile board position, resets to 3 if at 0
				if(this.position[0] > 0)
				{
					this.position[0] = posCopy[0] - 1; 
					this.position[1] = 0;
					
					outputString += this.checkNextTile(players, outputString, "block");
					
					players[this.position[0]].tiles[this.position[1]].getChildren().add(this);
				}
				else
				{
					this.position[0] = 3;
					this.position[1] = 0;
					
					outputString += this.checkNextTile(players, outputString, "block");
					
					players[this.position[0]].tiles[this.position[1]].getChildren().add(this);
				}
				
			}
			// IF looped around, send pawn down final stretch
			else if(this.position[1] == 6 && this.areasPassed == 4 || this.position[1] >= 14)
			{
				if (this.finalStretch == true) 
				{	// Move pawn down middle tiles
					try
					{
						this.team.tiles[this.position[1]].getChildren().add(this);
					}
					catch(Exception e)
					{ // Exception is thrown when pawn gets to the end, so use that to add 1 to the score, and hide finishing tiles
						
						//display new score
						this.team.score += 1;
						this.team.scoreLabel.setText(String.valueOf(this.team.score));
						
						// show user they scored a point
						outputString += this.team.name + " scored a point!";
						
						//hide pawn
						this.setVisible(false);
						
						// if user rolls six
						if(ludoBoard.distance == 6) this.team.sixTracker++;
						
						//return string for information
						return outputString;
					}
				}
				else 
				{
					//If user is on the middle tile and ready to finish, place on the middle column
					this.position[1] = 13;
					
					players[this.position[0]].tiles[this.position[1]].getChildren().add(this);
					
					this.finalStretch = true;
				}
				
			}
			// If all is good the pawn.circle is placed in the correct tile
			else if(this.position[1] < 13)
			{
				outputString += this.checkNextTile(players, outputString, "block");
				
				System.out.println(this.position[0]);
				try
				{
					players[this.position[0]].tiles[this.position[1]].getChildren().add(this);
				}
				catch(Exception e)
				{
					outputString += "Your Pawn was blocked!";
					
					ludoBoard.distance = 0;
				}
			}
			
			loopTracker++;
		}
		
		outputString += this.checkNextTile(players, outputString, "cap");
		
		
		// User rolls again since a 6 was rolled, can only happen thrice
		if (ludoBoard.distance == 6 && this.team.sixTracker < 2)
		{
			this.team.sixTracker++;
			
			outputString += "\n" + this.team.name + " rolled a 6 and gets to go again! you can do this " + (3 - this.team.sixTracker) + " more times";
			
		}
		// no 6 was rolled, next player turn
		else if(this.team.sixTracker == 2 || ludoBoard.distance != 6)
		{
			this.team.sixTracker = 0;
			outputString += ludoBoard.nextTurn(players, outputString);
		}
		
    	return outputString;
	}

	/**
	 * @param players
	 * @param outputString
	 * @return
	 */
	public String checkNextTile(Team[] players, String outputString, String blockOrCap) {
		// Variables for position checking
		int pawnsInPos = 0;
		
		Pawn deadPawn = null;
		
		// Checks the new position for other pawns, for resetting or blocking pawn movement
		for (Team player : players)
		{
			for(Pawn pawn : player.pawns)
			{
				// Adds 1 to pawnInPos if equal to current pawns position, and stores the pawn in a variable
				if(pawn.position[0] == this.position[0] && pawn.position[1] == this.position[1] && pawn != this && pawn.started)
				{
					pawnsInPos += 1;
					
					deadPawn = pawn;
				}
			}
		}
		
		// For blocking the current pawn if two opposing pawns are in the tile ahead
		if(blockOrCap == "block")
		{
			if(pawnsInPos >= 2)
			{
				// Tells user what happened
				outputString = deadPawn.team.name + " has two pawns here, and is blocking your pawn from moving. You must wait until at least one moves forward. \n";
				
				// Reset position to tile infront of two pawns, as position is already changed earlier on
				this.position[1] -= 1;
				if(this.position[1] == -1) this.position[1] = 13;
				this.tilesMoved -= 1;
				
				if(this.position[1] == 13) this.position[0] -= 1;
				if(this.position[0] == -1) this.position[0] = 3;
				this.areasPassed -= 1;
					
			}
				
		}
		
		// For sending single pawns back to spawn
		else if(blockOrCap == "cap")
		{
			// if there is 1 pawn in the tile, and its not on the same team, move it back to spawn
			if(pawnsInPos == 1 && deadPawn.team != this.team)
			{
				// adds the dead pawn back to its spawn
				deadPawn.team.pawnSpawn.getChildren().add(deadPawn);
				
				// Translate pawn back to starting position using pawn number 
				if(deadPawn.number == 1)
				{
					deadPawn.setTranslateX(-40.00);
					deadPawn.setTranslateY(40.00);
				}
				else if(deadPawn.number == 2)
				{
					deadPawn.setTranslateX(40.00);
					deadPawn.setTranslateY(40.00);
				}
				else if(deadPawn.number == 3)
				{
					deadPawn.setTranslateX(-40.00);
					deadPawn.setTranslateY(-40.00);
				}
				else if(deadPawn.number == 4)
				{
					deadPawn.setTranslateX(40.00);
					deadPawn.setTranslateY(-40.00);
				}
				
				// Resets pawn stats
				deadPawn.started = false;
				deadPawn.areasPassed = 0;
				deadPawn.tilesMoved = 0;
				
				// Show information to user in text output
				outputString += this.team.name + " Landed on " + deadPawn.team.name + "'s pawn and it has been sent back to the start! \n";				
			}
		}
		
		
		return outputString;
	}
			
	//Method testing blue pawn movement
	public String movePawn(Team[] players, LudoBoard ludo, String string)
	{
		String moveString = this.setPosition(players, ludo, string);
		System.out.print(this.getPosition());
		
		ludo.distance = 0;
		
		return moveString;
	}
}
