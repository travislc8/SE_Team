package GameLogic;

import java.awt.event.*;

import javax.swing.*;


public class GameControl implements MouseListener {


	private int SQUARE_SIZE = 65;
	
	
	
	private GamePanel gp;
	private GameData gd;
	
	public void setGamePanel(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setGameData(GameData gd) {
		this.gd = gd;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
        
        
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//get the panel that was clicked
			JPanel clickedPanel = (JPanel) e.getSource();
			
			
			//DETERMINE WHICH PANEL WAS CLICKED
			

			//IF THE BOARD WAS CLICKED
			if (clickedPanel.equals(gp.getBoardPanel())) {
				System.out.println("Board was clicked");
				
				
				//Clear the board of moves
				gp.clearValidMoves();
				
				//Get the coordinates on the board
				int x = e.getX();
		        int y = e.getY();

		        int col = x / SQUARE_SIZE;
		        int row = y / SQUARE_SIZE;

		        System.out.println("Clicked square: (" + col + ", " + row + ")");
		        
		        
		        Piece[][] objectGrid = gp.getObjectBoard();
		        
		        Piece selectedPiece = objectGrid[col][row];
		        
		        
		        //IN FINAL CODE, MAKE SURE YOU CHECK FOR MOVES BEFORE PIECE SELECTIOn
		        
		        
		        //check if there is a piece
		        if (selectedPiece != null) {
		        	System.out.println("There is a piece here");
		        	
		        	gd = gp.getGameData();
		        	
		        	
		        	gp.showValidMoves(selectedPiece.getAvailableMoves());
		        	
		        	
		        	
		        } else {
		        	System.out.println("There is NOT a piece here");
		        }
		        
			}
				
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
