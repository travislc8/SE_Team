package GameLogic;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import Client.GameClient;


public class GameControl implements MouseListener, ActionListener {
	
	private JPanel container;
	private GameClient client;
	
	private Piece selectedPiece;
	
	private GamePanel gp;
	private GameData gd;
	private PlayerType controlPlayer; //Variable to represent the player that is controlling this GUI
	
	/**
	 * Creates a GameControl object
	 * 
	 * @param container - the JPanel containing the GamePanel object associated with this controller
	 * @param client - GameClient object used to send data to the server
	 */
	public GameControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
		client.addGameControl(this);
	}
	
	public void setGamePanel(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setGameData(GameData gd) {
		this.gd = gd;
		gp.setGameData(gd);
	}
	
	public void setControlPlayer(PlayerType controlPlayer) {
		this.controlPlayer = controlPlayer;
	}
	


	/**
	 * Mouse controller that is used to allow user interaction with the board and player inventories
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
		
		if (controlPlayer != gd.activePlayer) {
			System.out.println("Not your turn bozo");
			return;
		}
		
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
		        	if (((selectedPiece.getPlayer() == PlayerType.BLACK && row < 3) || (selectedPiece.getPlayer() == PlayerType.BLACK && selectedPiece.getLocation().getyPos() < 3) || (selectedPiece.getPlayer() == PlayerType.WHITE && row > 5) || (selectedPiece.getPlayer() == PlayerType.WHITE && selectedPiece.getLocation().getyPos() > 5)) && selectedPiece.isOnBoard() && !selectedPiece.isPromoted() && selectedPiece.getPieceType() != PieceType.GOLDGENERAL && selectedPiece.getPieceType() != PieceType.KING) {
		        		
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
			        		int result = gp.promptPromotion(selectedPiece);
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
		        	
		        	//stop the timer
		        	gp.stopLocalTimer();
		        	
		        	//SEND UPDATE TO SERVER
		        	try {
						client.sendToServer(gd);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	
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

	/**
	 * Action Handler used to handle button clicks for "Forfeit" and "Offer Draw"
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		System.out.println("BUTTON PRESEED");
		
		if (command == "Forfeit") {
			forfeit();
		} else if (command == "Offer Draw") {
			offerDraw();
		}
	}
	
	/**
	 * Handles mouse clicks on Player inventories by determining which type of piece in their inventory they would like to select,
	 * then shows all of the valid moves for the selected piece in their inventory
	 * @param e - MouseEvent to get the event data from
	 * @param player - Type of player whose inventory is being interacted with
	 */
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
        	if (piece.getPieceType() == type) {
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
	
	/**
	 * Starts the game by setting up the GamePanel with correct specifications at the beginning of a match
	 */
	public void startGame() {
		gp.startGame();
	}
	
	/**
	 * Updates the visuals of the GamePanel including checks for a new game, the end of a game, or continuation of a game
	 */
	public void updateVisuals() {
		
		System.out.println("Updating visuals");

		
		if (!gd.gameStarted) {
			
			System.out.println("This is a new game");
			
			//a new game has just started
			CardLayout layout = (CardLayout) container.getLayout();
			layout.show(container, "6");
			startGame();
		} else {
			
			System.out.println("This is an ongoing game");
			
			gp.clearEntireBoard();
			gp.updateGamePanel();
		}
		
	}
	
	/**
	 * Handles all incoming GameData objects received from the server
	 * 
	 * @param serverGameData - GameData object received from the server
	 */
	public void handleServerGameData(GameData serverGameData) {
		
		setGameData(serverGameData);
		updateVisuals();
		
		
	}
	
	/**
	 * Prompts the user if they would like to accept a draw offer from their opponent
	 * If the user responds yes, end the game by updating the gameData
	 * If the user responds no, nothing happens
	 */
	public void receiveDrawOffer() {
		int result = gp.promptDrawOffer("receive");
		if (result == JOptionPane.YES_OPTION) {
			
			//user accepts the draw
			//Set the winner to null
			gd.winner = null;
			gd.setGameOver(true);
			
			//send updated GameData to server
			try {
				client.sendToServer(gd);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		} else {
			//user declines the draw
		}
	}
	
	/**
	 * Prompts the user if they would like to offer a draw to their opponent
	 * If the user responds yes, send "OFFER_DRAW" to the server
	 * If the user responds no, nothing happens
	 */
	public void offerDraw() {
		
		int result = gp.promptDrawOffer("send");
		if (result == JOptionPane.YES_OPTION) {

			try {
				client.sendToServer("OFFER_DRAW");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
        } else {
        	//The user chose not to offer the draw
        }
	}
	
	/**
	 * Displays a prompt to the user to ask if they would like to forfeit
	 * If the user responds yes, the player resigns by editing the gameData object, then sends it to the server
	 * If the user responds no, nothing happens
	 */
	public void forfeit() {
		int result = gp.promptForfeit();
		if (result == JOptionPane.YES_OPTION) {
			//The user chose to forfeit
            gd.resign();
			//Send the updated object to the server
			try {
				client.sendToServer(gd);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        } else {
        	//The user chose not to Resign
        }
		
	}
	
	/**
	 * Displays the end game result to the players which changes based on the winner variable in GameData
	 */
	public void displayEndGame() {
		if (!gd.gameOver) {
			//The game is not Over
		} else if (gd.winner == null) {
			//The game ended in a Draw
			gp.displayEndGame("Draw");
		} else if (gd.winner == controlPlayer) {
			gp.displayEndGame("Win");
		} else {
			gp.displayEndGame("Lose");
		}
	}
	
	
	//Unused Mouse listener methods that are required to be present in this class
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	
	
	
	
	

}
