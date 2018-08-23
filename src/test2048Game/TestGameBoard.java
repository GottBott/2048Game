package test2048Game;

import static org.junit.Assert.*;
import gameLogic.GameBoard;

import org.junit.Test;

public class TestGameBoard {


	@Test
	public void testPrintBoard() {
		GameBoard gb;
		
		gb = new GameBoard(0x0000000000000000L);
		assertEquals(gb.printBoard().length(),16);
		
		gb = new GameBoard(0x0000000000000001L);
		assertEquals(gb.printBoard().length(),16);
		
		gb = new GameBoard(0xFFFFFFFFFFFFFFFFL);
		assertEquals(gb.printBoard().length(),16);
			
	   }
	
	@Test
	public void testGetValueAtPosition() {
		GameBoard gb;
		
		gb = new GameBoard(0x0000000000000000L);
		for(int i =0; i < 16; i++){
			assertEquals(gb.getValueAtPosition(i),0);
		}
		
		gb = new GameBoard(0x0000000000000001L);
		assertEquals(gb.getValueAtPosition(15),1);
		
		gb = new GameBoard(0x1000000000000000L);
		assertEquals(gb.getValueAtPosition(0),1);
		
		gb = new GameBoard(0x00F00000000000D0L);
		assertEquals(gb.getValueAtPosition(2),0xF);
		assertEquals(gb.getValueAtPosition(14),0xD);
		
	}
	
	@Test
	public void testShiftLeft() {
		GameBoard gb;
		
		//test moving from space 15 to space 12
		gb = new GameBoard(0x0000000000000001L);
		assertEquals(gb.getValueAtPosition(15),1);
		gb.shiftLeft();
		assertEquals(gb.getValueAtPosition(15),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(12),1);
		
		//test moving from space 14 to space 12
		gb = new GameBoard(0x0000000000000010L);
		assertEquals(gb.getValueAtPosition(14),1);
		gb.shiftLeft();
		assertEquals(gb.getValueAtPosition(15),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(12),1);
		
		//test moving from space 13 to space 12
		gb = new GameBoard(0x0000000000000100L);
		assertEquals(gb.getValueAtPosition(13),1);
		gb.shiftLeft();
		assertEquals(gb.getValueAtPosition(15),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(12),1);
		
		//test nothing moves when in space 12
		gb = new GameBoard(0x0000000000001000L);
		assertEquals(gb.getValueAtPosition(12),1);
		gb.shiftLeft();
		assertEquals(gb.getValueAtPosition(15),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(12),1);
		
		//test that every row shifts left
		gb = new GameBoard(0x0001000100010001L);
		assertEquals(gb.getValueAtPosition(15),1);
		assertEquals(gb.getValueAtPosition(11),1);
		assertEquals(gb.getValueAtPosition(7),1);
		assertEquals(gb.getValueAtPosition(3),1);
		gb.shiftLeft();
		assertEquals(gb.getValueAtPosition(15),0);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(3),0);
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(4),1);
		assertEquals(gb.getValueAtPosition(8),1);
		assertEquals(gb.getValueAtPosition(12),1);
		
		// test collisions same value different columns
		gb = new GameBoard(0x1001020200334400L);
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(1),0);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),1);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(5),0x2);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0x2);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0x3);
		assertEquals(gb.getValueAtPosition(11),0x3);
		assertEquals(gb.getValueAtPosition(12),0x4);
		assertEquals(gb.getValueAtPosition(13),0x4);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),0);
		gb.shiftLeft();
		assertEquals(gb.getValueAtPosition(0),0x2);
		assertEquals(gb.getValueAtPosition(1),0);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),0);
		assertEquals(gb.getValueAtPosition(4),0x3);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(8),0x4);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(12),0x5);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),0);
		
		//test collision different values
		gb = new GameBoard(0x1200304005600078L);
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(1),0x2);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),0);
		assertEquals(gb.getValueAtPosition(4),0x3);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0x4);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0x5);
		assertEquals(gb.getValueAtPosition(10),0x6);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0x7);
		assertEquals(gb.getValueAtPosition(15),0x8);
		gb.shiftLeft();
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(1),0x2);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),0);
		assertEquals(gb.getValueAtPosition(4),0x3);
		assertEquals(gb.getValueAtPosition(5),0x4);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(8),0x5);
		assertEquals(gb.getValueAtPosition(9),0x6);
		assertEquals(gb.getValueAtPosition(10),0);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(12),0x7);
		assertEquals(gb.getValueAtPosition(13),0x8);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),0);
	}
	
	@Test
	public void testShiftRight() {
		GameBoard gb;
		
		//test moving from space 12 to space 15
		gb = new GameBoard(0x0000000000001000L);
		assertEquals(gb.getValueAtPosition(12),1);
		gb.shiftRight();
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),1);
		
		//test moving from space 13 to space 15
		gb = new GameBoard(0x0000000000000100L);
		assertEquals(gb.getValueAtPosition(13),1);
		gb.shiftRight();
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),1);
		
		//test moving from space 14 to space 5
		gb = new GameBoard(0x0000000000000010L);
		assertEquals(gb.getValueAtPosition(14),1);
		gb.shiftRight();
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),1);
		
		//test nothing moves when in space 15
		gb = new GameBoard(0x0000000000000001L);
		assertEquals(gb.getValueAtPosition(15),1);
		gb.shiftRight();
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),1);
		
		//test that every row shifts right
		gb = new GameBoard(0x1000100010001000L);
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(4),1);
		assertEquals(gb.getValueAtPosition(8),1);
		assertEquals(gb.getValueAtPosition(12),1);
		gb.shiftRight();
		assertEquals(gb.getValueAtPosition(15),1);
		assertEquals(gb.getValueAtPosition(11),1);
		assertEquals(gb.getValueAtPosition(7),1);
		assertEquals(gb.getValueAtPosition(3),1);
		assertEquals(gb.getValueAtPosition(0),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(12),0);
		
		// test collisions same value different columns
		gb = new GameBoard(0x1001020200334400L);
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(1),0);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),1);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(5),0x2);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0x2);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0x3);
		assertEquals(gb.getValueAtPosition(11),0x3);
		assertEquals(gb.getValueAtPosition(12),0x4);
		assertEquals(gb.getValueAtPosition(13),0x4);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),0);
		gb.shiftRight();
		assertEquals(gb.getValueAtPosition(0),0);
		assertEquals(gb.getValueAtPosition(1),0);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),0x2);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0x3);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0);
		assertEquals(gb.getValueAtPosition(11),0x4);
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),0x5);
		
		//test collision different values
		gb = new GameBoard(0x1200304005600078L);
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(1),0x2);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),0);
		assertEquals(gb.getValueAtPosition(4),0x3);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0x4);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0x5);
		assertEquals(gb.getValueAtPosition(10),0x6);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0x7);
		assertEquals(gb.getValueAtPosition(15),0x8);
		gb.shiftRight();
		assertEquals(gb.getValueAtPosition(0),0);
		assertEquals(gb.getValueAtPosition(1),0);
		assertEquals(gb.getValueAtPosition(2),1);
		assertEquals(gb.getValueAtPosition(3),0x2);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0x3);
		assertEquals(gb.getValueAtPosition(7),0x4);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0x5);
		assertEquals(gb.getValueAtPosition(11),0x6);
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0x7);
		assertEquals(gb.getValueAtPosition(15),0x8);
	}
	
	@Test
	public void testShiftUp() {
		GameBoard gb;
		
		//test moving from space 12 to space 0
		gb = new GameBoard(0x0000000000004000L);
		assertEquals(gb.getValueAtPosition(12),0x4);
		gb.shiftUp();
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(0),0x4);
		
		
		//test moving from space 8 to space 0
		gb = new GameBoard(0x0000000040000000L);
		assertEquals(gb.getValueAtPosition(8),0x4);
		gb.shiftUp();
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(0),0x4);
		
		//test moving from space 4 to space 0
		gb = new GameBoard(0x0000400000000000L);
		assertEquals(gb.getValueAtPosition(4),0x4);
		gb.shiftUp();
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(0),0x4);
		
		//test nothing happens when in space 0
		gb = new GameBoard(0x4000000000000000L);
		assertEquals(gb.getValueAtPosition(0),0x4);
		gb.shiftUp();
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(0),0x4);
		

		//test that every column shifts up
		gb = new GameBoard(0x0000000000001234L);
		assertEquals(gb.getValueAtPosition(12),1);
		assertEquals(gb.getValueAtPosition(13),0x2);
		assertEquals(gb.getValueAtPosition(14),0x3);
		assertEquals(gb.getValueAtPosition(15),0x4);
		gb.shiftUp();
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(1),0x2);
		assertEquals(gb.getValueAtPosition(2),0x3);
		assertEquals(gb.getValueAtPosition(3),0x4);
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),0);
		
		// test collisions same value different rows
		gb = new GameBoard(0x0000123400001234L);
		assertEquals(gb.getValueAtPosition(0),0);
		assertEquals(gb.getValueAtPosition(1),0);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),0);
		assertEquals(gb.getValueAtPosition(4),1);
		assertEquals(gb.getValueAtPosition(5),0x2);
		assertEquals(gb.getValueAtPosition(6),0x3);
		assertEquals(gb.getValueAtPosition(7),0x4);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(12),1);
		assertEquals(gb.getValueAtPosition(13),0x2);
		assertEquals(gb.getValueAtPosition(14),0x3);
		assertEquals(gb.getValueAtPosition(15),0x4);
		gb.shiftUp();
		assertEquals(gb.getValueAtPosition(0),0x2);
		assertEquals(gb.getValueAtPosition(1),0x3);
		assertEquals(gb.getValueAtPosition(2),0x4);
		assertEquals(gb.getValueAtPosition(3),0x5);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),0);
		
		//test collision different values
		gb = new GameBoard(0x1234000056780000L);
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(1),0x2);
		assertEquals(gb.getValueAtPosition(2),0x3);
		assertEquals(gb.getValueAtPosition(3),0x4);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(8),0x5);
		assertEquals(gb.getValueAtPosition(9),0x6);
		assertEquals(gb.getValueAtPosition(10),0x7);
		assertEquals(gb.getValueAtPosition(11),0x8);
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),0);
		gb.shiftUp();
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(1),0x2);
		assertEquals(gb.getValueAtPosition(2),0x3);
		assertEquals(gb.getValueAtPosition(3),0x4);
		assertEquals(gb.getValueAtPosition(4),0x5);
		assertEquals(gb.getValueAtPosition(5),0x6);
		assertEquals(gb.getValueAtPosition(6),0x7);
		assertEquals(gb.getValueAtPosition(7),0x8);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),0);
	}
	
	@Test
	public void testShiftDown() {
		GameBoard gb;
		
		//test moving from space 0 to space 12
		gb = new GameBoard(0xF000000000000000L);
		assertEquals(gb.getValueAtPosition(0),0xF);
		gb.shiftDown();
		assertEquals(gb.getValueAtPosition(12),0xF);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(0),0);
		
		
		//test moving from space 4 to space 12
		gb = new GameBoard(0x0000400000000000L);
		assertEquals(gb.getValueAtPosition(4),0x4);
		gb.shiftDown();
		assertEquals(gb.getValueAtPosition(12),0x4);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(0),0);
		
		//test moving from space 8 to space 12
		gb = new GameBoard(0x00000000D0000000L);
		assertEquals(gb.getValueAtPosition(8),0xD);
		gb.shiftDown();
		assertEquals(gb.getValueAtPosition(12),0xD);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(0),0);
		
		//test nothing happens when in space 12
		gb = new GameBoard(0x000000000000C000L);
		assertEquals(gb.getValueAtPosition(12),0xC);
		gb.shiftDown();
		assertEquals(gb.getValueAtPosition(12),0xC);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(0),0);
		

		//test that every column shifts down
		gb = new GameBoard(0x1234000000000000L);
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(1),0x2);
		assertEquals(gb.getValueAtPosition(2),0x3);
		assertEquals(gb.getValueAtPosition(3),0x4);
		gb.shiftDown();
		assertEquals(gb.getValueAtPosition(0),0);
		assertEquals(gb.getValueAtPosition(1),0);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(12),0x1);
		assertEquals(gb.getValueAtPosition(13),0x2);
		assertEquals(gb.getValueAtPosition(14),0x3);
		assertEquals(gb.getValueAtPosition(15),0x4);
		
		// test collisions same value different rows
		gb = new GameBoard(0x0000123400001234L);
		assertEquals(gb.getValueAtPosition(0),0);
		assertEquals(gb.getValueAtPosition(1),0);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),0);
		assertEquals(gb.getValueAtPosition(4),1);
		assertEquals(gb.getValueAtPosition(5),0x2);
		assertEquals(gb.getValueAtPosition(6),0x3);
		assertEquals(gb.getValueAtPosition(7),0x4);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(12),1);
		assertEquals(gb.getValueAtPosition(13),0x2);
		assertEquals(gb.getValueAtPosition(14),0x3);
		assertEquals(gb.getValueAtPosition(15),0x4);
		gb.shiftDown();
		assertEquals(gb.getValueAtPosition(0),0);
		assertEquals(gb.getValueAtPosition(1),0);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(8),0);
		assertEquals(gb.getValueAtPosition(9),0);
		assertEquals(gb.getValueAtPosition(10),0);
		assertEquals(gb.getValueAtPosition(11),0);
		assertEquals(gb.getValueAtPosition(12),0x2);
		assertEquals(gb.getValueAtPosition(13),0x3);
		assertEquals(gb.getValueAtPosition(14),0x4);
		assertEquals(gb.getValueAtPosition(15),0x5);
		
		//test collision different values
		gb = new GameBoard(0x1234000056780000L);
		assertEquals(gb.getValueAtPosition(0),1);
		assertEquals(gb.getValueAtPosition(1),0x2);
		assertEquals(gb.getValueAtPosition(2),0x3);
		assertEquals(gb.getValueAtPosition(3),0x4);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(8),0x5);
		assertEquals(gb.getValueAtPosition(9),0x6);
		assertEquals(gb.getValueAtPosition(10),0x7);
		assertEquals(gb.getValueAtPosition(11),0x8);
		assertEquals(gb.getValueAtPosition(12),0);
		assertEquals(gb.getValueAtPosition(13),0);
		assertEquals(gb.getValueAtPosition(14),0);
		assertEquals(gb.getValueAtPosition(15),0);
		gb.shiftDown();
		assertEquals(gb.getValueAtPosition(0),0);
		assertEquals(gb.getValueAtPosition(1),0);
		assertEquals(gb.getValueAtPosition(2),0);
		assertEquals(gb.getValueAtPosition(3),0);
		assertEquals(gb.getValueAtPosition(4),0);
		assertEquals(gb.getValueAtPosition(5),0);
		assertEquals(gb.getValueAtPosition(6),0);
		assertEquals(gb.getValueAtPosition(7),0);
		assertEquals(gb.getValueAtPosition(8),0x1);
		assertEquals(gb.getValueAtPosition(9),0x2);
		assertEquals(gb.getValueAtPosition(10),0x3);
		assertEquals(gb.getValueAtPosition(11),0x4);
		assertEquals(gb.getValueAtPosition(12),0x5);
		assertEquals(gb.getValueAtPosition(13),0x6);
		assertEquals(gb.getValueAtPosition(14),0x7);
		assertEquals(gb.getValueAtPosition(15),0x8);
	}
	

	@Test
	public void testAddPeice() {
		GameBoard gb;
		
		// test that a piece is added
		Long boardSetup = 0x0000000000000000L;
		gb = new GameBoard(boardSetup);
		assertEquals(gb.getGameBoard(),boardSetup,0);
		gb.addPiece();
		assertNotEquals(gb.getGameBoard(),boardSetup,0);
		
		// test a piece  makes it to the last open spot
		boardSetup = 0xFFFFFFFFFFFF0FFFL;
		gb = new GameBoard(boardSetup);
		gb.addPiece();
		assertTrue(gb.getGameBoard() == 0xFFFFFFFFFFFF1FFFL || gb.getGameBoard() == 0xFFFFFFFFFFFF2FFFL );
		
		// test every empty space is eligible
		boardSetup = 0x0000000000000000L;
		gb = new GameBoard(boardSetup);
		for(int i = 0; i < 16; i++){
			gb.addPiece();
		}
		for (int i=0; i <16; i++){
			assertTrue(gb.getValueAtPosition(i) == 0x1 || gb.getValueAtPosition(i) == 0x2 );
		}
		
		//test when game is over;
		gb = new GameBoard(0x1234567812345678L);
		assertFalse(gb.isGameOver());
		gb.addPiece();
		assertTrue(gb.isGameOver());
		
		
		
	}
}
