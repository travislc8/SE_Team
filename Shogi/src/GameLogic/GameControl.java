package GameLogic;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;


public class GameControl implements MouseListener, ActionListener {
	
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
		        if (!selectedSquare.getBackground().equals(Color.decode(GamePanel.VALID_MOVE_COLOR)) ) {
		        	//NO MOVE WAS SELECTED
		        } else  {
		        	//A VALID MOVE WAS SELECTED
		        	
		        	//if the selected move is in the promotion zone
		        	if (((selectedPiece.getPlayer() == PlayerType.BLACK && row < 3) || (selectedPiece.getPlayer() == PlayerType.BLACK && selectedPiece.getLocation().getyPos() < 3) || (selectedPiece.getPlayer() == PlayerType.WHITE && row > 5) || (selectedPiece.getPlayer() == PlayerType.WHITE && selectedPiece.getLocation().getyPos() > 5)) && selectedPiece.isOnBoard() && !selectedPiece.isPromoted()) {
		        		
		        		
		        		//check for Forced Promotions
		        		if ((selectedPiece.getPieceType() == PieceType.PAWN || selectedPiece.getPieceType() == PieceType.LANCE) && (row == 0 || row == 8)) {
		        			//if a pawn or lance is placed on the final row (no more moves left if not promoted)
		        			if ((selectedPiece.getPlayer() == PlayerType.BLACK && row == 0) || (selectedPiece.getPlayer() == PlayerType.WHITE && row == 8)) {
			        			System.out.println("FORCED PROMOTION OF LANCE/PAWN");
			        			selectedPiece.setPromoted(true);
		        			}
		        			
		        		} else if (selectedPiece.getPieceType() == PieceType.KNIGHT && (row < 2 || row > 6)) {
		        			//if a Knight moves to the last 2 rows (no more moves left if not promoted)
		        			if ((selectedPiece.getPlayer() == PlayerType.BLACK && row < 2) || (selectedPiece.getPlayer() == PlayerType.WHITE && row > 6) ) {
		        				System.out.println("FORCED PROMOTION OF KNIGHT");
		        				selectedPiece.setPromoted(true);
		        			}
		        		} else {
		        			//prompt the user if they want to promote
			        		int result = gp.PromptPromotion(selectedPiece);
			        		if (result == JOptionPane.YES_OPTION) {
			        			//The user chose to promote
			                    selectedPiece.setPromoted(true);
			                } else {
			                	//The user chose not to Promote
			                }
		        		}
		        		
		        	}
		        	
		        	//move the selected piece to the new valid location
		        	if (!gd.makeMove(new Move(selectedPiece, new PieceLocation(col, row, selectedPiece.getPlayer())))) {
		        		System.out.println("ERROR: unable to move piece");
		        	
		        	}
		        	
		        	
		        	//Clear and update the board (REMOVE ONCE SERVER INTEGRATION IS IMPLEMENTED)
		        	//gp.clearEntireBoard();
		        	//gp.updateGamePanel(gd);
		        	
		        	
		        	//SEND UPDATE TO SERVER
		        	//send the updated gameData object to the server
		        	
		        	
		        	//Simulate the server sending back a new UPDATED GameData object
		        	gd.changeTurn();
		        	MoveCalculator mc = new MoveCalculator();
		        	mc.calculateAvailableMoves(gd);
		        	
		        	setGameData(gd);
		        	gp.setGameData(gd);
		        	
		        	
		        	
		        	//update again type beat
		        	gp.clearEntireBoard();
		        	gp.updateGamePanel(gd);
		        	
		        	
		        	//leave this block
		        	return;
		        }
		        
		        //set the new selected piece
		        selectedPiece = objectGrid[col][row];
		        
				//Clear the board of moves
				gp.clearValidMoves();
		        
		        //check if there is a piece and it is controlled (owned) by the active player
		        if (selectedPiece != null && selectedPiece.getPlayer().equals(gd.getActivePlayer())) {
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
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
        for (Piece piece : gd.getPlayerHand(player)) {
        	if (piece.getPieceType() == type && !piece.isOnBoard()) {
        		selectedPiece = piece;
        		break;
        	}
        }
        
		//Clear the board of moves
		gp.clearValidMoves();
        
        //check if there is a piece
        if (selectedPiece != null && selectedPiece.getPlayer().equals(gd.getActivePlayer())) {
        	//show the available moves of the selected piece on the board
        	gp.showValidMoves(selectedPiece);
        } else {
        	//IF THERE IS NOT A PIECE ON THE SQUARE
        	
        }
        
	}

}
