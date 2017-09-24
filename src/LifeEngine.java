
public class LifeEngine {
	
	/*
	 * 	BoardInput[X Coordinate][Y Coordinate]
	 * 	
	 * 	Coordinates [0][0]: Top Left
	 * 
	 * 	X Coordinate: 		0	= Left
	 * 	X Coordinate: Max Cord	= Right
	 * 	
	 * 	Y Coordinate: 		0	= Top
	 * 	Y Coordinate: Max Cord	= Bottom
	 */
	
	private static boolean[][] outputBoard; // Stores updated board 
	
	private static boolean[][] inputBoard; // Takes input of board and computes changes to outputBoard
	
	public static void updateBoard(boolean[][] input, boolean loop){
		inputBoard = new boolean[input[0].length][input[1].length];
		outputBoard = new boolean[input[0].length][input[1].length];
		for (int x = 0; x < input[0].length; x++) {
			for (int y = 0; y < input[1].length; y++) {
				inputBoard[x][y] = input[x][y]; // Takes in input for computing
			}
		}
		
		runThroughLoop(loop);
		
		for (int x = 0; x < input[0].length; x++) {
			for (int y = 0; y < input[1].length; y++) {
				input[x][y] = outputBoard[x][y]; // Takes in input for computing
			}
		}
	}
	
	private static void runThroughLoop(boolean loop){
		for(int x = 0; x < inputBoard[0].length; x++){
			for(int y = 0; y < inputBoard[1].length; y++){
				outputBoard[x][y] = checkSpotLife(x, y, loop);
			}
		}
	}
	
	/*
	 * 	1.	Any live cell with fewer than two live neighbors dies, as if caused by underpopulation.
	 * 	2.	Any live cell with two or three live neighbors lives on to the next generation.
	 * 	3.	Any live cell with more than three live neighbors dies, as if by overpopulation.
	 * 
	 * 	4.	Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
	 */
	private static boolean checkSpotLife(int x, int y, boolean loop){ // Rules above will be checked for the spot
		
		int neighbors = countNeighbors(x, y, loop);
		
		
		// These apply to the rules
		if(inputBoard[x][y]) { // A Live Cell
			
			if(neighbors == 2) { return true; }
			else if (neighbors == 3) { return true; }
			else { return false; }
			
		} else { // A Dead Cell
			
			if(neighbors == 3) { return true; }
			else { return false; } 
			
		}
	}
	
	private static int countNeighbors(int x, int y, boolean loop){
		int neighborCount = 0;
		int cX, cY; // Cords that it will check
		
		for(int mX = -1; mX < 2; mX++){
			for(int mY = -1; mY < 2; mY++){
				
				// Checks cells around it, but not its self
				if(!(mX == 0 && mY == 0)){ 
				
					cX = x + mX;
					cY = y + mY;
					
					// Checks to see if where it's checking is out of bounds
					if(loop){
						if(inputBoard[(cX+inputBoard[0].length)%inputBoard[0].length][(cY+inputBoard[1].length)%inputBoard[1].length]){
							neighborCount++;
						} 
					} else {
						try {
							if(inputBoard[cX][cY]){
								neighborCount++;
							} 
						} catch (Exception e){
							
						} 
					}
				}
			}
		}
		
		return neighborCount;
	}
}






