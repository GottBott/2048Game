package gameLogic;

import java.util.Scanner;

// This class is responsible for setting up and starting a new game of 2048.
public class Game {
	

	public static void main(String args[]){
		GameBoard board = new GameBoard(0L);
		board.printBoard();
		
		Scanner scanner = new Scanner(System.in);
		while(! board.isGameOver()){
			System.out.println("Enter move: ");
			
			String move = scanner.nextLine();
			switch (move){
			case "1":
				board.shiftLeft();
				break;
			case "2":
				board.shiftRight();
				break;
			case "3":
				board.shiftUp();
				break;
			case "4":
				board.shiftDown();
				break;
			
			}
			board.addPiece();
			board.printBoard();
		}
		
	}
	
	
}
