package gameLogic;

import java.util.HashMap;

public class GameBoard {

	long board;
	boolean gameOver = false;
	int score =0;
	
	HashMap<Integer, Integer> lookupUpLeft;
	HashMap<Integer, Integer> lookupDownRight;
	
	public GameBoard(Long boardSetup){
		this.board = boardSetup;
		createMoveLookupTables();
	}
	
	public long getGameBoard(){
		return board;
	}
	
	public boolean isGameOver(){
		return this.gameOver;
	}
	
	public int getScore(){
		return this.score;
	}
	
	//compute all row lookups and store in hashmap
	//the tables will map start state to end state when a swipe is performed
	// Up and left can be combined
	// down and right can be combined
	private void createMoveLookupTables(){
		lookupUpLeft = new HashMap<Integer, Integer>();
		lookupDownRight = new HashMap<Integer, Integer>();
		
		for (int row = 0; row < 65536; row++) {
	        
	        int[] spacesL = {
	        	((row>>>12)&15),
	        	((row>>>8)&15),
	        	((row>>>4)&15),
	        	((row>>>0)&15)
	        };
	        
	        int[] spacesR = {
		        	((row>>>12)&15),
		        	((row>>>8)&15),
		        	((row>>>4)&15),
		        	((row>>>0)&15)
		        };
	        

	        // execute a move to the left
	        for (int i = 0; i < 3; i++) {
	            int j;
	            for (j = i + 1; j < 4; j++) {
	                if (spacesL[j] != 0) break;
	            }
	            if (j == 4) break; // no more tiles to the right

	            if (spacesL[i] == 0) {
	            	spacesL[i] = spacesL[j];
	            	spacesL[j] = 0;
	                i--; // retry this entry
	            } else if (spacesL[i] == spacesL[j]) {
	                if(spacesL[i] != 0xf) {
	                    // Pretend that 32768 + 32768 = 32768 (representational limit)
	                	spacesL[i]++;
	                }
	                spacesL[j] = 0;
	            }
	        }
	        
	        int left =  ((((spacesL[0] <<12) | (spacesL[1] <<8)) | (spacesL[2] <<4)) | (spacesL[3]));
	        lookupUpLeft.put(row, left);
	        
	       
	        // execute a move to the right
	        for (int i = 3; i >0; i--) {
	            int j;
	            for ( j = i - 1; j > -1; j--) {
	                if (spacesR[j] != 0) break;
	            }
	            if (j == -1) break; // no more tiles to the left

	            if (spacesR[i] == 0) {
	            	spacesR[i] = spacesR[j];
	            	spacesR[j] = 0;
	                i++; // retry this entry
	            } else if (spacesR[i] == spacesR[j]) {
	                if(spacesR[i] != 0xf) {
	                    // Pretend that 32768 + 32768 = 32768 (representational limit)
	                	spacesR[i]++;
	                }
	                spacesR[j] = 0;
	            }
	        }

	       
	        int right =  ((((spacesR[0] <<12) | (spacesR[1] <<8)) | (spacesR[2] <<4)) | (spacesR[3]));
	        lookupDownRight.put(row, right);
	      
		}		
	}
	
	// prints the board to console as hex values.
	// a value of 4 is really 2^4 or 16
	public String printBoard(){
		String printBoardString = Long.toHexString(board);
		while(printBoardString.length() < 16){
			printBoardString = '0' + printBoardString;
		}
		for(int i = 0; i < 4; i++){
			String row = printBoardString.substring(4*i, 4*(i+1));
			for(int j =0; j<4; j++){
		
				System.out.print(row.substring(j,j+1) + " | ");		
			}
			System.out.println();
		}
		System.out.println();
		return printBoardString;
	}
	
	
	// given a position 0-15, returns the integer representation of the value at that position
	public int getValueAtPosition(int p){

		Long shiftBoard = Long.rotateLeft(board, 4*p);
		int valueAtPosition =  Integer.valueOf((int) (shiftBoard>>>60));
		return valueAtPosition;
	}
	
	
	// gets the row r as an integer.
	// example: 0x00001234 would be a row with values 1, 2, 3, and 4 from left to right. 
	private int getRow(int r){
		return (int)(Long.rotateRight(board, 16*r) & 65535);
		
	}
	
	// sets the row r as given by integer newRow. 
	private void setRow(int r, int newRow){
		board = (Long.rotateRight(board, 16*r) & 0xFFFFFFFFFFFF0000L);
		board = board | newRow;
		board = (Long.rotateLeft(board, 16*r));
		
		
	}
	
	// Gets the column c as as integer
	// Example: 0x00001234 where 1, 2, 3, and 4 are stacked with 1 on top
	private int getCol(int c){
		int colInt =0;
		board = Long.rotateLeft(board,c*4 + 4);
		colInt = (int) ((board & 0x000000000000000FL) | colInt);
		for(int i=0; i<3;i++){
			board = Long.rotateLeft(board,16);
			colInt= colInt<< 4;
			colInt = (int) ((board & 0x000000000000000FL) | colInt & 0xFFFFFFF0);
		}
		board = Long.rotateLeft(board,16);
		board = Long.rotateRight(board,c*4 + 4);
			
		return colInt;
	}
	
	// Sets the column c with the integer provided
	private void setCol(int c, int newCol){
		board = Long.rotateRight(board,12-c*4);
		board = board & 0xFFFFFFFFFFFFFFF0L | newCol & 0x0000000FL;
		for(int i=0; i<3;i++){
			board = Long.rotateRight(board,16);
			newCol= newCol>>> 4;
			board = (board & 0xFFFFFFFFFFFFFFF0L) | (newCol & 0x0000000FL);
		}
		board = Long.rotateRight(board,16);
		board = Long.rotateLeft(board,12-c*4);
		
	}
	
	
	// push pieces up on the board
	public void shiftUp(){
		for(int i=0;i<4;i++){
			int col = getCol(i);
			col = lookupUpLeft.get(col);
			setCol(i,col);
		}	
	}
	
	// push pieces down on the board
	public void shiftDown(){
		for(int i=0;i<4;i++){
			int col = getCol(i);
			col = lookupDownRight.get(col);
			setCol(i,col);
		}	
	
	}
	
	//push pieces left on the board
	public void shiftLeft(){
		for(int i=0;i<4;i++){
			int row = getRow(i);
			row = lookupUpLeft.get(row);
			setRow(i,row);
		}	
	}
	
	//push pieces right on the board
	public void shiftRight(){
		for(int i=0;i<4;i++){
			int row = getRow(i);
			row = lookupDownRight.get(row);
			setRow(i,row);
		}	
	}
	
	// adds a 1 or 2 to the board in a random empty spot
	// probability of a 2 is 69%
	public void addPiece(){
		if(checkGameOver()){
			return;
		}
		int rand = (int)(Math.random()*100);
		board = Long.rotateLeft(board, 4*rand);
		int rotateCount =0;
		while((board & 0x000000000000000FL) != 0){
			board = Long.rotateLeft(board, 4);
			rotateCount++;
		}
		if( rand < 70){
			board = board | 0x0000000000000001L;
		}
		else{
			board = board | 0x0000000000000002L;
		}
		
		board = Long.rotateRight(board, 4*rand + 4*rotateCount);
		
	}
	
	// returns true if the game is over
	// checks for empty spaces then if a move is possible
	private boolean checkGameOver(){
		for(int i = 0; i<16; i++){
			if(getValueAtPosition(i) == 0)
				return false;
		}
		for(int i=0;i<4;i++){
			int row = getRow(i);
			int col = getCol(i);
			if(lookupUpLeft.get(row) != row | lookupUpLeft.get(col) != col ){
				return false;
			}
		}
		gameOver = true;
		return true;
	}
	
}
	


