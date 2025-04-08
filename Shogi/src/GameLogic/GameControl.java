package GameLogic;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;


public class GameControl implements MouseListener {


	private int SQUARE_SIZE = 65;
	
	private Piece selectedPiece;
	
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
				//Get the coordinates on the board
				int x = e.getX();
		        int y = e.getY();

		        int col = x / SQUARE_SIZE;
		        int row = y / SQUARE_SIZE;
		        
		        Piece[][] objectGrid = gp.getObjectBoard();
		        JLabel[][] boardGrid = gp.getBoardGrid();
		        
		        JLabel selectedSquare = boardGrid[col][row];
		        
		        //IN FINAL CODE, MAKE SURE YOU CHECK FOR MOVES BEFORE PIECE SELECTION
		        if (selectedSquare.getBackground().equals(Color.decode(gp.BOARD_COLOR)) ) {
		        	
		        	//NO MOVE WAS SELECTED
		        	
		        	System.out.println("Non move clicked");
		        	
		        } else  {
		        	
		        	//A VALID MOVE WAS SELECTED
		        	//move the selected piece to the new valid location
		        	selectedPiece.setLocation(new PieceLocation(col, row, selectedPiece.getPlayer()));
		        	System.out.println("Move Selected");
		        	
		        	gp.clearEntireBoard();
		        	gp.updateBoard(gd);
		        	//leave this block
		        	return;
		        	
		        }
		        
		        //set the new selected piece
		        selectedPiece = objectGrid[col][row];
		        	
				//Clear the board of moves
				gp.clearValidMoves();
		        
		        //check if there is a piece
		        if (selectedPiece != null) {
		        	
		        	//set the game data
		        	gd = gp.getGameData();
		        	//show the available moves of the selected piece on the board
		        	gp.showValidMoves(selectedPiece.getAvailableMoves());
		        	
		        } else {
		        	//IF THERE IS NOT A PIECE ON THE SQUARE
		        	
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
