package GameLogic;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;


public class GameControl implements MouseListener {
	
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
		
		//get the panel that was clicked
			JPanel clickedPanel = (JPanel) e.getSource();
			
			
			//DETERMINE WHICH PANEL WAS CLICKED
			
			if (clickedPanel.equals(gp.getBoardPanel())) {
				//IF THE BOARD WAS CLICKED
				//set the game data
	        	gd = gp.getGameData();
	        	
				//Get the coordinates on the board
				int x = e.getX();
		        int y = e.getY();

		        int col = x / GamePanel.SQUARE_SIZE;
		        int row = y / GamePanel.SQUARE_SIZE;
		        
		        Piece[][] objectGrid = gp.getObjectBoard();
		        JLabel[][] boardGrid = gp.getBoardGrid();
		        
		        JLabel selectedSquare = boardGrid[col][row];
		        
		        //Check to see if a move was selected
		        if (selectedSquare.getBackground().equals(Color.decode(GamePanel.BOARD_COLOR)) ) {
		        	//NO MOVE WAS SELECTED
		        } else  {
		        	//A VALID MOVE WAS SELECTED
		        	//move the selected piece to the new valid location
		        	if (gd.makeMove(new Move(selectedPiece, new PieceLocation(col, row, selectedPiece.getPlayer())))) {
		        	
		        		System.out.println("Move Successful");
		        	}
		        	
		        	
		        	//Clear and update the board (REMOVE ONCE SERVER INTEGRATION IS IMPLEMENTED)
		        	gp.clearEntireBoard();
		        	gp.updateBoard(gd);
		        	
		        	
		        	//SEND UPDATE TO SERVER
		        	//send the updated gameData object to the server
		        	
		        	
		        	//leave this block
		        	return;
		        }
		        
		        //set the new selected piece
		        selectedPiece = objectGrid[col][row];
		        	
				//Clear the board of moves
				gp.clearValidMoves();
		        
		        //check if there is a piece
		        if (selectedPiece != null) {
		        	//show the available moves of the selected piece on the board
		        	gp.showValidMoves(selectedPiece);
		        	
		        } else {
		        	//IF THERE IS NOT A PIECE ON THE SQUARE
		        	
		        }
		        
			} else if (clickedPanel.equals(gp.getWhiteInventoryPanel())) {
				//IF THE WHITE INVENTORY WAS CLICKED
				handleInventoryClick(e, PlayerType.WHITE);
				
			} else if (clickedPanel.equals(gp.getBlackInventoryPanel())) {
				//IF THE BLACK INVENTORY WAS CLICKED
				handleInventoryClick(e, PlayerType.BLACK);
				
				
			}
				
	}
	
	private void handleInventoryClick(MouseEvent e, PlayerType player) {
		//set the game data
    	gd = gp.getGameData();
		//Get the piece row on the inventory
        int y = e.getY();
        int row = y / GamePanel.SQUARE_SIZE;
		
        PieceType type = gp.getInventoryPiece().get(row);
        
      //set the new selected piece
        selectedPiece = null;
        for (Piece piece : gd.getPlayerPieces(player)) {
        	if (piece.getPieceType() == type && !piece.isOnBoard()) {
        		selectedPiece = piece;
        		break;
        	}
        }
        
		//Clear the board of moves
		gp.clearValidMoves();
        
        //check if there is a piece
        if (selectedPiece != null) {
        	//show the available moves of the selected piece on the board
        	gp.showValidMoves(selectedPiece);
        } else {
        	//IF THERE IS NOT A PIECE ON THE SQUARE
        	
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
