package GameLogic;

import java.awt.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;
import javax.swing.border.*;

public class GamePanel extends JPanel {

	private GameData gd;
	
	//Have a 2d array for the visuals of the board (used below), AND have a parallel 2d array of Pieces for logic side
	//	(when you click a coordinate on visual array, take data from same coordinate of logic array (containing Piece objects)
	private JLabel[][] boardGrid; //2d array used for visually representing the board with JLabls
	private Piece[][] objectBoard; //2d array used for referencing objects for logic
	
	private JLabel[] whiteInventory;
	private JLabel[] blackInventory;
	
	private JPanel boardPanel; //Panel that will contain the board
	private JPanel whiteInventoryPanel; //Panel that will contain the white player's inventory
	private JPanel blackInventoryPanel; //Panel that will contain the black player's inventory
	
	private JLabel whiteTimer; //JLabel for the white player's timer
	private int whiteTime; //local time variable to use for timer display
	private JLabel blackTimer; //JLabel for the black player's timer
	private int blackTime; //local time variable to use for timer display

	private Timer localTimer; //timer utility used for visual count down
	
	private JButton forfeitButton; //button to allow users to forfeit a game
	private JButton offerDrawButton; //button to allow users to offer a draw to their opponent
	
	private GameControl gc;

	
	//Constants for changing visuals of game
	//FRAME SIZING
	static int FRAME_HEIGHT = 750; //MIN = 700 RECOMMENDED = 750
	static int FRAME_WIDTH = 1000; //MIN = 850 RECOMMENDED = 1000
	static int BOARD_PADDING = 20;
	static int SQUARE_SIZE = 65;
	
	//COLORS
	static String BOARD_COLOR = "#A1662F";
	static String VALID_MOVE_COLOR = "#66EE66";
	static String SELECTED_PIECE_COLOR = "#006400";
	static String CHECK_COLOR = "#CC3333";
	//FONTS
	private Font timerFont = new Font("Serif", Font.BOLD, 60);
	private Font inventoryFont = new Font("", Font.BOLD, 20);
	private Font buttonFont = new Font("Serif", Font.BOLD, 20);
	
	
	private HashMap<String, ImageIcon> pieceImages; //HashMap that contains all of the images for every piece
	private HashMap<PieceType, Integer> inventoryIndex; //HashMap that gives the index based on piece
	private HashMap<Integer, PieceType> inventoryPiece; //HashMap that gives the piece based on index
	
	//GETTERS AND SETTERS
	public GameData getGameData() {
		return gd;
	}
	
	public Piece[][] getObjectBoard() {
		return objectBoard;
	}
	
	public JLabel[][] getBoardGrid() {
		return boardGrid;
	}
	
	public JPanel getBoardPanel() {
		return boardPanel;
	}
	
	public JPanel getWhiteInventoryPanel() {
		return whiteInventoryPanel;
	}
	public JPanel getBlackInventoryPanel() {
		return blackInventoryPanel;
	}
	
	public HashMap<Integer, PieceType> getInventoryPiece() {
		return inventoryPiece;
	}
	
	public HashMap<PieceType, Integer> getInventoryIndex() {
		return inventoryIndex;
	}
	
	public void setGameData(GameData gd) {
		this.gd = gd;
	}
	
	
	/**
	 * GamePanel constructor used to create a GamePanel object
	 * @param gc - GameControl object associated with this GamePanel
	 */
	public GamePanel(GameControl gc) {
		
		//instantiate and fill the pieceImages HashMap
		pieceImages = new HashMap<>();
		CreatePieceImagesHashMap();
		
		//instantiate and fill the inventoryIndex HashMap
		inventoryIndex = new HashMap<>();
		inventoryPiece = new HashMap<>();
		CreateInventoryHashMaps();
		
		//Create the panel to hold the board
		boardGrid = new JLabel[9][9];
		objectBoard = new Piece[9][9];
		
		boardPanel = new JPanel(new GridLayout(9,9));
		boardPanel.addMouseListener(gc);
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) { 
				boardGrid[col][row] = new JLabel();
				boardGrid[col][row].setBorder(new LineBorder(Color.BLACK));
				boardGrid[col][row].setOpaque(true);
				boardGrid[col][row].setBackground(Color.decode(BOARD_COLOR));
				boardPanel.add(boardGrid[col][row]);
			}
		}
		
		
		///CREATING THE BLACK INVENTORY
		blackInventoryPanel = new JPanel(new GridLayout(7,1));
		blackInventoryPanel.addMouseListener(gc);
		blackInventory = new JLabel[7];
		
		int counter = 0;
		for (PieceType type : PieceType.values()) {
			
			//skip the king
			if (type.equals(PieceType.KING)) continue;
			
			blackInventory[counter] = new JLabel();
			blackInventory[counter].setIcon(pieceImages.get("BLACK" + type + "false"));
			blackInventory[counter].setHorizontalAlignment(SwingConstants.LEFT);
			blackInventory[counter].setText("0");
			blackInventory[counter].setHorizontalTextPosition(SwingConstants.RIGHT);
			blackInventory[counter].setVerticalTextPosition(SwingConstants.BOTTOM);
			blackInventory[counter].setFont(inventoryFont);
			blackInventory[counter].setOpaque(true);
			
			
			blackInventoryPanel.add(blackInventory[counter]);
			
			counter++;
		}
		
		
		///CREATING THE WHITE INVENTORY
		whiteInventoryPanel = new JPanel(new GridLayout(7,1));
		whiteInventoryPanel.addMouseListener(gc);
		whiteInventory = new JLabel[7];
		
		counter = 0;
		for (PieceType type : PieceType.values()) {
			
			//skip the king
			if (type.equals(PieceType.KING)) continue;
			
			whiteInventory[counter] = new JLabel();
			whiteInventory[counter].setIcon(pieceImages.get("WHITE" + type + "false"));
			whiteInventory[counter].setHorizontalAlignment(SwingConstants.RIGHT);
			whiteInventory[counter].setText("0");
			whiteInventory[counter].setHorizontalTextPosition(SwingConstants.LEFT);
			whiteInventory[counter].setVerticalTextPosition(SwingConstants.BOTTOM);
			whiteInventory[counter].setFont(inventoryFont);
			whiteInventory[counter].setOpaque(true);
			
			
			whiteInventoryPanel.add(whiteInventory[counter]);
			
			counter++;
		}
		
		
		//CREATE THE TIMER LABELS
		//	black
		blackTimer = new JLabel("5:00");
		blackTimer.setFont(timerFont);
		blackTimer.setBorder(new LineBorder(Color.BLACK));
		blackTimer.setBackground(Color.DARK_GRAY);
		//	white
		whiteTimer = new JLabel("5:00");
		whiteTimer.setFont(timerFont);
		whiteTimer.setBorder(new LineBorder(Color.BLACK));
		whiteTimer.setBackground(Color.DARK_GRAY);

		
		//CREATE THE USER BUTTONS (forfeit and offer draw)
		forfeitButton = new JButton("Forfeit");
		forfeitButton.addActionListener(gc);
		forfeitButton.setFont(buttonFont);
		forfeitButton.setPreferredSize(new Dimension(100, 50));
		
		offerDrawButton = new JButton("Offer Draw");
		offerDrawButton.addActionListener(gc);
		offerDrawButton.setFont(buttonFont);
		offerDrawButton.setPreferredSize(new Dimension(150, 50));
		
		
		boardPanel.setPreferredSize(new Dimension(585, 585));
		
		this.setLayout(null);
		
		this.add(boardPanel);
		
		Dimension boardSize = boardPanel.getPreferredSize();
		int boardX = (FRAME_WIDTH/2) - (boardSize.width/2);
		int boardY = BOARD_PADDING;
		boardPanel.setBounds(boardX, boardY, boardSize.width, boardSize.height);
		
		this.add(whiteInventoryPanel);
		Dimension size = whiteInventoryPanel.getPreferredSize();
		whiteInventoryPanel.setBounds(boardX - (size.width + BOARD_PADDING) - 15, boardY, size.width + 15, size.height);
		
		this.add(blackInventoryPanel);
		size = blackInventoryPanel.getPreferredSize();
		blackInventoryPanel.setBounds((boardX + boardSize.width) + (BOARD_PADDING), (boardY + (boardSize.height - size.height)), size.width + 15, size.height);
		
		this.add(whiteTimer);
		size = whiteTimer.getPreferredSize();
		whiteTimer.setBounds((boardX + boardSize.width) + (BOARD_PADDING), boardY, size.width, size.height);
		
		this.add(blackTimer);
		size = blackTimer.getPreferredSize();
		blackTimer.setBounds(boardX - (size.width + BOARD_PADDING), boardY + (boardSize.height - size.height), size.width, size.height);
		
		this.add(forfeitButton);
		size = forfeitButton.getPreferredSize();
		forfeitButton.setBounds(boardX, (boardY + boardSize.height) + BOARD_PADDING, size.width, size.height);
		
		this.add(offerDrawButton);
		size = offerDrawButton.getPreferredSize();
		offerDrawButton.setBounds(boardX + (boardSize.width - size.width), (boardY + boardSize.height) + BOARD_PADDING, size.width, size.height);
		
		
		//give the control a reference to this Panel
		this.gc = gc;
		gc.setGamePanel(this);
	}
	
	private void CreateInventoryHashMaps() {
		inventoryIndex.put(PieceType.ROOK, 0);
		inventoryIndex.put(PieceType.BISHOP, 1);
		inventoryIndex.put(PieceType.GOLDGENERAL, 2);
		inventoryIndex.put(PieceType.SILVERGENERAL, 3);
		inventoryIndex.put(PieceType.KNIGHT, 4);
		inventoryIndex.put(PieceType.LANCE, 5);
		inventoryIndex.put(PieceType.PAWN, 6);
		
		inventoryPiece.put(0, PieceType.ROOK);
		inventoryPiece.put(1, PieceType.BISHOP);
		inventoryPiece.put(2, PieceType.GOLDGENERAL);
		inventoryPiece.put(3, PieceType.SILVERGENERAL);
		inventoryPiece.put(4, PieceType.KNIGHT);
		inventoryPiece.put(5, PieceType.LANCE);
		inventoryPiece.put(6, PieceType.PAWN);
	}
	
	private void CreatePieceImagesHashMap() {
		
		//Creating all of the black pieces
			//KING
		ImageIcon blackKing = new ImageIcon(getClass().getResource("/images/blackKing.png"));
		pieceImages.put("BLACK" + "KING" + "false", blackKing);
			//GOLD GENERAL
		ImageIcon blackGoldGeneral = new ImageIcon(getClass().getResource("/images/blackGoldGeneral.png"));
		pieceImages.put("BLACK" + "GOLDGENERAL" + "false", blackGoldGeneral);
			//PAWN
		ImageIcon blackPawn = new ImageIcon(getClass().getResource("/images/blackPawn.png"));
		pieceImages.put("BLACK" + "PAWN" + "false", blackPawn);
		ImageIcon promotedBlackPawn = new ImageIcon(getClass().getResource("/images/blackPromotedPawn.png"));
		pieceImages.put("BLACK" + "PAWN" + "true", promotedBlackPawn);
			//BISHOP
		ImageIcon blackBishop = new ImageIcon(getClass().getResource("/images/blackBishop.png"));
		pieceImages.put("BLACK" + "BISHOP" + "false", blackBishop);
		ImageIcon promotedBlackBishop = new ImageIcon(getClass().getResource("/images/blackPromotedBishop.png"));
		pieceImages.put("BLACK" + "BISHOP" + "true", promotedBlackBishop);
			//ROOK
		ImageIcon blackRook = new ImageIcon(getClass().getResource("/images/blackRook.png"));
		pieceImages.put("BLACK" + "ROOK" + "false", blackRook);
		ImageIcon promotedBlackRook = new ImageIcon(getClass().getResource("/images/blackPromotedRook.png"));
		pieceImages.put("BLACK" + "ROOK" + "true", promotedBlackRook);
			//SILVER GENERAL
		ImageIcon blackSilverGeneral = new ImageIcon(getClass().getResource("/images/blackSilverGeneral.png"));
		pieceImages.put("BLACK" + "SILVERGENERAL" + "false", blackSilverGeneral);
		ImageIcon promotedBlackSilverGeneral = new ImageIcon(getClass().getResource("/images/blackPromotedSilverGeneral.png"));
		pieceImages.put("BLACK" + "SILVERGENERAL" + "true", promotedBlackSilverGeneral);
			//KNIGHT
		ImageIcon blackKnight = new ImageIcon(getClass().getResource("/images/blackKnight.png"));
		pieceImages.put("BLACK" + "KNIGHT" + "false", blackKnight);
		ImageIcon promotedBlackKnight = new ImageIcon(getClass().getResource("/images/blackPromotedKnight.png"));
		pieceImages.put("BLACK" + "KNIGHT" + "true", promotedBlackKnight);
			//LANCE
		ImageIcon blackLance = new ImageIcon(getClass().getResource("/images/blackLance.png"));
		pieceImages.put("BLACK" + "LANCE" + "false", blackLance);
		ImageIcon promotedBlackLance = new ImageIcon(getClass().getResource("/images/blackPromotedLance.png"));
		pieceImages.put("BLACK" + "LANCE" + "true", promotedBlackLance);
		
		
		//creating all of the white pieces
			//KING
		ImageIcon whiteKing = new ImageIcon(getClass().getResource("/images/whiteKing.png"));
		pieceImages.put("WHITE" + "KING" + "false", whiteKing);
			//GOLD GENERAL
		ImageIcon whiteGoldGeneral = new ImageIcon(getClass().getResource("/images/whiteGoldGeneral.png"));
		pieceImages.put("WHITE" + "GOLDGENERAL" + "false", whiteGoldGeneral);
			//PAWN
		ImageIcon whitePawn = new ImageIcon(getClass().getResource("/images/whitePawn.png"));
		pieceImages.put("WHITE" + "PAWN" + "false", whitePawn);
		ImageIcon promotedWhitePawn = new ImageIcon(getClass().getResource("/images/whitePromotedPawn.png"));
		pieceImages.put("WHITE" + "PAWN" + "true", promotedWhitePawn);
			//BISHOP
		ImageIcon whiteBishop = new ImageIcon(getClass().getResource("/images/whiteBishop.png"));
		pieceImages.put("WHITE" + "BISHOP" + "false", whiteBishop);
		ImageIcon promotedWhiteBishop = new ImageIcon(getClass().getResource("/images/whitePromotedBishop.png"));
		pieceImages.put("WHITE" + "BISHOP" + "true", promotedWhiteBishop);
			//ROOK
		ImageIcon whiteRook = new ImageIcon(getClass().getResource("/images/whiteRook.png"));
		pieceImages.put("WHITE" + "ROOK" + "false", whiteRook);
		ImageIcon promotedWhiteRook = new ImageIcon(getClass().getResource("/images/whitePromotedRook.png"));
		pieceImages.put("WHITE" + "ROOK" + "true", promotedWhiteRook);
			//SILVER GENERAL
		ImageIcon whiteSilverGeneral = new ImageIcon(getClass().getResource("/images/whiteSilverGeneral.png"));
		pieceImages.put("WHITE" + "SILVERGENERAL" + "false", whiteSilverGeneral);
		ImageIcon promotedWhiteSilverGeneral = new ImageIcon(getClass().getResource("/images/whitePromotedSilverGeneral.png"));
		pieceImages.put("WHITE" + "SILVERGENERAL" + "true", promotedWhiteSilverGeneral);
			//KNIGHT
		ImageIcon whiteKnight = new ImageIcon(getClass().getResource("/images/whiteKnight.png"));
		pieceImages.put("WHITE" + "KNIGHT" + "false", whiteKnight);
		ImageIcon promotedWhiteKnight = new ImageIcon(getClass().getResource("/images/whitePromotedKnight.png"));
		pieceImages.put("WHITE" + "KNIGHT" + "true", promotedWhiteKnight);
			//LANCE
		ImageIcon whiteLance = new ImageIcon(getClass().getResource("/images/whiteLance.png"));
		pieceImages.put("WHITE" + "LANCE" + "false", whiteLance);
		ImageIcon promotedWhiteLance = new ImageIcon(getClass().getResource("/images/whitePromotedLance.png"));
		pieceImages.put("WHITE" + "LANCE" + "true", promotedWhiteLance);

		
	}
	
	/**
	 * clears the entire board empty of everything (board colors and piece images)
	 */
	public void clearEntireBoard() {
		clearValidMoves();
		clearPieces();
	}
	
	/**
	 * clears all of the valid move colors (and selected piece color) from the board
	 */
	public void clearValidMoves() {
		//clear board
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) { 
				boardGrid[col][row].setText("");
				boardGrid[col][row].setBackground(Color.decode(BOARD_COLOR));
			}
		}
		//clear inventory
		for (int row = 0; row < 7; row++) {
			whiteInventory[row].setBackground(whiteInventoryPanel.getBackground());
			blackInventory[row].setBackground(whiteInventoryPanel.getBackground());
		}
		
		if (gd.isInCheck()) displayCheck();
	}
	
	/**
	 * clears all of the piece images from the board
	 */
	private void clearPieces() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) { 
				boardGrid[col][row].setIcon(null);
				objectBoard[col][row] = null;
			}
		}
	}
	
	/**
	 * Changes the colors of the board squares to show the selected piece and its available moves
	 * @param selectedPiece - the piece selected by the user
	 */
	public void showValidMoves(Piece selectedPiece) {
		
		//change background color of selected piece
		highlightPiece(selectedPiece);
		
		//change the color of the valid move squares
		ArrayList <PieceLocation> validSquares = selectedPiece.getAvailableMoves();
		for (PieceLocation square : validSquares) {
			//get the position of the square
			int col = square.getxPos();
			int row = square.getyPos();
			//change visual color of square on the board
			boardGrid[col][row].setBackground(Color.decode(VALID_MOVE_COLOR));
		}
		
	}
	
	/**
	 * Changes the color of the board/inventory square behind the selected piece  
	 * @param selectedPiece - piece selected by the user to be highlighted
	 */
	private void highlightPiece(Piece selectedPiece) {
		
		if (selectedPiece.isOnBoard()) {
			//change background color of selected piece
			PieceLocation location = selectedPiece.getLocation();
			boardGrid[location.getxPos()][location.getyPos()].setBackground(Color.decode(SELECTED_PIECE_COLOR));
			
		} else {
			
			if (selectedPiece.getPlayer() == PlayerType.BLACK) {
				
				int index = inventoryIndex.get(selectedPiece.getPieceType());
				
				blackInventory[index].setBackground(Color.decode(SELECTED_PIECE_COLOR));
				
				
			} else if (selectedPiece.getPlayer() == PlayerType.WHITE) {
				
				int index = inventoryIndex.get(selectedPiece.getPieceType());
				
				whiteInventory[index].setBackground(Color.decode(SELECTED_PIECE_COLOR));
			}
		}
	}
	
	/**
	 * Sets the color of the square under the king in check to be the CHECK_COLOR to show the user they are in check
	 */
	private void displayCheck() {
		//loop through the player's pieces to find the king
		for (Piece piece : gd.getPlayerPieces(gd.getActivePlayer())) {
			if (piece.getPieceType() == PieceType.KING) {
				piece.getLocation().getxPos();
				boardGrid[piece.getLocation().getxPos()][piece.getLocation().getyPos()].setBackground(Color.decode(CHECK_COLOR));
				//boardGrid[piece.getLocation().getxPos()][piece.getLocation().getyPos()].setBorder(new LineBorder(Color.decode(CHECK_COLOR)));
				return;
			}
		}
	}
	
	
	/**
	 * Runs all necessary methods to update all the visuals of the GamePanel
	 */
	public void updateGamePanel() {
		updateBoard();
		
		if (gd.isInCheck()) {
			displayCheck();
		}
		
		updateInventories();
		updateTimersServer();
	}
	
	
	/**
	 * Updates the players' timers from the data collected sent by the server and starts the clock of the active player
	 */
	private void updateTimersServer() {
		
		stopLocalTimer();
		
		whiteTime = gd.getPlayer2Time();
		int seconds = whiteTime % 60;
		int minutes = whiteTime / 60;
		
		if (seconds < 10) {
			whiteTimer.setText(Integer.toString(minutes) + ":0" + Integer.toString(seconds));
		} else {
			whiteTimer.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
		}
		
		
		blackTime = gd.getPlayer1Time();
		seconds = blackTime % 60;
		minutes = blackTime / 60;
		
		if (seconds < 10) {
			blackTimer.setText(Integer.toString(minutes) + ":0" + Integer.toString(seconds));
		} else {
			blackTimer.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
		}


		
	    
		//start the local timer
		if (gd.getActivePlayer() == PlayerType.BLACK) {
			startLocalTimer("black", blackTimer);
		} else {
			startLocalTimer("white", whiteTimer);
		}
		
	}
	
	/**
	 * Runs the necessary visual updates to start the game of Shogi
	 */
	public void startGame() {
		//create timer
		localTimer = new Timer();
		updateBoard();
		updateInventories();
	}

	/**
	 * Starts the local timer used to update the graphical display
	 * @param color - which player's timer needs to be started ("black" or "white")
	 * @param timerPanel - the timerPanel corresponding to the color passed in
	 */
    private void startLocalTimer(String color, JLabel timerPanel) {
    	localTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            	int totalSeconds;
            	if (color == "white") totalSeconds = whiteTime;
            	else totalSeconds = blackTime;
                if (totalSeconds > 0) {
                	int seconds = totalSeconds % 60;
            		int minutes = totalSeconds / 60;
            		
            		if (seconds < 10) {
            			timerPanel.setText(Integer.toString(minutes) + ":0" + Integer.toString(seconds));
            		} else {
            			timerPanel.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
            		}
            		if (color == "white") whiteTime--;
            		else blackTime--;
            		
                } else {
                	timerPanel.setText("0:00");
                    System.out.println("Time's up!");
                    localTimer.cancel();
                    gc.timeUpForfeit();
                }
            }
        };
        
        localTimer.scheduleAtFixedRate(task, 0, 1000); // Schedule the task to run every 1000ms (1 second)
    }
    
    /**
     * Stops the local timer used to update the graphical display
     */
    public void stopLocalTimer() {
    	if (localTimer == null) {
    		return;
    	}
    	localTimer.cancel();
    }
	
	
	/**
	 * Updates the visual board by placing the images of the pieces in the grid of JPanels
	 */
	private void updateBoard() {
		
		//get the black player's pieces /\ (up)
		ArrayList<Piece> blackPieces = gd.getPlayerPieces(PlayerType.BLACK);
		
		//get the white player's pieces \/ (down)
		ArrayList<Piece> whitePieces = gd.getPlayerPieces(PlayerType.WHITE);
		
		ArrayList<Piece> allPieces = new ArrayList<>();
		allPieces.addAll(blackPieces);
		allPieces.addAll(whitePieces);
		
		
		//loop through each piece and place it on the board
		for (Piece piece : allPieces) {
			
			//skip pieces that are not on the board
			//if (!piece.isOnBoard()) continue;
			
			//generate the key for the image HashMap
			String key = "" + piece.getPlayer() + piece.getPieceType() + piece.isPromoted();
			
			//get the location of the piece
			PieceLocation location = piece.getLocation();
			
			//Display the piece on the game board
			boardGrid[location.getxPos()][location.getyPos()].setIcon(pieceImages.get(key));
			
			objectBoard[location.getxPos()][location.getyPos()] = piece;
			
		}
			
	}
	
	/**
	 * Updates both players' inventories
	 */
	private void updateInventories() {
		
		//get the black player's pieces /\ (up)
		ArrayList<Piece> blackPieces = gd.getPlayerHand(PlayerType.BLACK);
		//update the black inventory
		updateInventory(blackInventory, blackPieces);
		
		//get the white player's pieces \/ (down)
		ArrayList<Piece> whitePieces = gd.getPlayerHand(PlayerType.WHITE);
		//update the white inventory
		updateInventory(whiteInventory, whitePieces);
		
	}
	
	/**
	 * Updates the visuals for the inventory panel passed in
	 * @param visualInventory - inventory panel to update
	 * @param pieceList - list of pieces that belongs to the same player as the inventory
	 */
	private void updateInventory(JLabel[] visualInventory, ArrayList<Piece> pieceList) {
		
		//create counting structure
		HashMap<PieceType, Integer> pieceCount = new HashMap<>();
		
		//loop through each piece, add it to the inventory, and count up totals
		for (Piece piece : pieceList) {
			
			//skip pieces that are on the board
			//if (piece.isOnBoard()) continue;
			
			//increment the count of the pieceType
			if (pieceCount.get(piece.getPieceType()) == null) {
				pieceCount.put(piece.getPieceType(), 1);
			} else {
				pieceCount.put(piece.getPieceType(), (pieceCount.get(piece.getPieceType()) + 1));
			}
		}
		
		//Update the number display of the visualInventory
		int counter = 0;
		for (PieceType type : PieceType.values()) {
			//skip the king
			if (type.equals(PieceType.KING)) continue;
			
			//update the text
			if (pieceCount.get(type) == null) visualInventory[counter].setText("0");
			else visualInventory[counter].setText(Integer.toString(pieceCount.get(type)));
			
			//increment counter
			counter++;
		}
		
	}
	
	/**
	 * Prompts the player to ask if they would like to promote a piece that is eligible
	 * @param piece - the piece that is eligible for promotion
	 * @return - result of the user's choice to promote or not
	 */
	public int promptPromotion(Piece piece) {
		
		//generate the key for the image HashMap
		String key = "" + piece.getPlayer() + piece.getPieceType() + true;
		ImageIcon icon = pieceImages.get(key);
		
		//prompt the user if they want to promote
		int result = JOptionPane.showConfirmDialog(this, "Would you like to promote your " + piece.getPieceType() + "?", "Promotion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
		
		return result;
	}
	
	/**
	 * Prompts the user to confirm their decision to offer a draw to their opponent or accept a draw from their opponent
	 * @param direction - String defining whether this player is sending, or receiving a draw offer
	 * @return - result of the player's decision to draw or not
	 */
	public int promptDrawOffer(String direction) {
		
		if (direction == "send") {
			//prompt the user if they want to promote
			int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to offer a Draw?", "Draw Offer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
			
			return result;
		} else {
			//prompt the user if they want to promote
			int result = JOptionPane.showConfirmDialog(this, "Your opponent has offered a Draw.\nWould you like to Accept?", "Draw Offer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
			
			return result;
		}
	}
	
	/**
	 * Prompts the user to confirm their decision to forfeit the match
	 * @return - result of the user's decision whether to forfeit or not
	 */
	public int promptForfeit() {
				
		//prompt the user if they want to promote
		int result = JOptionPane.showConfirmDialog(this, "Would you like to forfeit?", "Forfeit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		return result;
	}
	
	/**
	 * Displays a pop-up displaying the result of the game for this Player.
	 * @param result - a string of the result for this client ("Win", "Lose", or "Draw")
	 * @return - return value from the Dialog Pop-up box used to determine when the user dismisses the pop-up
	 */
	public int displayEndGame(String result) {
		
		if (result == "Draw") {
			int confirm = JOptionPane.showConfirmDialog(this, "The game ended in a Draw", "Draw", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null);
			return confirm;
		} else if (result == "Win") {
			int confirm = JOptionPane.showConfirmDialog(this, "YOU WIN!", "WINNER IS YOU", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null);
			return confirm;
		} else if (result == "Lose") {
			int confirm = JOptionPane.showConfirmDialog(this, "YOU LOSE!", "Better luck next time", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null);
			return confirm;
		} else {
			System.out.println("No known end game result was found");
			return 1;
		}
		
	}
	
	
	
}
