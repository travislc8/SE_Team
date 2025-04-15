package GameLogic;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class GamePanel extends JPanel {

	private GameData gd;
	
	//Have a 2d array for the visuals of the board (used below), AND have a parallel 2d array of Pieces for logic side
	//	(when you click a coordinate on visual array, take data from same coordinate of logic array (containing Piece objects)
	private JLabel[][] boardGrid; //2d array used for visually representing the board with JLabls
	private Piece[][] objectBoard; //2d array used for referencing objects for logic
	
	private JLabel[] whiteInventory;
	private ArrayList<Piece> objectWhiteInventory;
	private JLabel[] blackInventory;
	private ArrayList<Piece> objectBlackInventory;
	
	

	

	private JPanel boardPanel; //Panel that will contain the board
	private JPanel whiteInventoryPanel; //Panel that will contain the white player's inventory
	private JPanel blackInventoryPanel; //Panel that will contain the black player's inventory
	private JLabel whiteTimer; //JLabel for the white player's timer
	private JLabel blackTimer; //JLabel for the black player's timer
	private JButton forfeitButton; //button to allow users to forfeit a game
	private JButton offerDrawButton; //button to allow users to offer a draw to their opponent
	
	//Constants for changing visuals of game
	//FRAME SIZING
	static int FRAME_HEIGHT = 750; //MIN = 700 RECOMMENDED = 750
	static int FRAME_WIDTH = 1000; //MIN = 850 RECOMMENDED = 1000
	static int BOARD_PADDING = 20;
	
	//COLORS
	static String BOARD_COLOR = "#A1662F";
	static String VALID_MOVE_COLOR = "#66EE66";
	//FONTS
	private Font timerFont = new Font("Serif", Font.BOLD, 60);
	private Font inventoryFont = new Font("", Font.BOLD, 20);
	private Font buttonFont = new Font("Serif", Font.BOLD, 20);
	
	
	private HashMap<String, ImageIcon> pieceImages;
	
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
	
	
	
	
	
	public GamePanel(GameControl gc) {
		
		//instantiate and fill the pieceImagesArrayList
		pieceImages = new HashMap<>();
		CreatePieceImagesHashMap();
		
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
			blackInventory[counter].setFont(new Font("comic sans", Font.BOLD, 20));
			
			
			blackInventoryPanel.add(blackInventory[counter]);
			
			counter++;
		}
		
		
		///CREATING THE WHITE INVENTORY
		whiteInventoryPanel = new JPanel(new GridLayout(7,1));
		whiteInventory = new JLabel[7];
		
		counter = 0;
		for (PieceType type : PieceType.values()) {
			
			//skip the king
			if (type.equals(PieceType.KING)) continue;
			
			whiteInventory[counter] = new JLabel();
			whiteInventory[counter].setIcon(pieceImages.get("WHITE" + type + "false"));
			whiteInventory[counter].setHorizontalAlignment(SwingConstants.LEFT);
			whiteInventory[counter].setText("0");
			whiteInventory[counter].setHorizontalTextPosition(SwingConstants.LEFT);
			whiteInventory[counter].setVerticalTextPosition(SwingConstants.BOTTOM);
			whiteInventory[counter].setFont(inventoryFont);
			
			
			whiteInventoryPanel.add(whiteInventory[counter]);
			
			counter++;
		}
		
		
		//CREATE THE TIMER LABELS
		//	black
		blackTimer = new JLabel("2:00");
		blackTimer.setFont(timerFont);
		blackTimer.setBorder(new LineBorder(Color.BLACK));
		blackTimer.setBackground(Color.DARK_GRAY);
		//	white
		whiteTimer = new JLabel("2:00");
		whiteTimer.setFont(timerFont);
		whiteTimer.setBorder(new LineBorder(Color.BLACK));
		whiteTimer.setBackground(Color.DARK_GRAY);

		
		//CREATE THE USER BUTTONS (forfeit and offer draw)
		forfeitButton = new JButton("Forfeit");
		forfeitButton.setFont(buttonFont);
		forfeitButton.setPreferredSize(new Dimension(100, 50));
		
		offerDrawButton = new JButton("Offer Draw");
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
		whiteInventoryPanel.setBounds(boardX - (size.width + BOARD_PADDING), boardY, size.width, size.height);
		
		this.add(blackInventoryPanel);
		size = blackInventoryPanel.getPreferredSize();
		blackInventoryPanel.setBounds((boardX + boardSize.width) + (BOARD_PADDING), (boardY + (boardSize.height - size.height)), size.width, size.height);
		
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
		gc.setGamePanel(this);
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
	
	public void clearEntireBoard() {
		clearValidMoves();
		clearPieces();
	}
	
	public void clearValidMoves() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) { 
				boardGrid[col][row].setText("");
				boardGrid[col][row].setBackground(Color.decode(BOARD_COLOR));
			}
		}
	}
	
	public void clearPieces() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) { 
				boardGrid[col][row].setIcon(null);
			}
		}
	}
	
	public void showValidMoves(ArrayList <PieceLocation> validSquares) {
		
		for (PieceLocation square : validSquares) {
			
			int col = square.getxPos();
			int row = square.getyPos();
			
			
			boardGrid[col][row].setBackground(Color.decode(VALID_MOVE_COLOR));
			
			
		}
		
	}
	
	
	
	public void updateBoard(GameData gd) {
		
		this.gd = gd;
		
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
			if (!piece.isOnBoard()) {
				continue;
			}
			
			//generate the key for the image HashMap
			String key = "" + piece.getPlayer() + piece.getPieceType() + piece.isPromoted();
			
			//get the location of the piece
			PieceLocation location = piece.getLocation();
			
			//Display the piece on the game board
			boardGrid[location.getxPos()][location.getyPos()].setIcon(pieceImages.get(key));
			
			objectBoard[location.getxPos()][location.getyPos()] = piece;
			
		}
			
	}
	
	public void updateInvetories(GameData gd) {
		
		//get the black player's pieces /\ (up)
		ArrayList<Piece> blackPieces = gd.getPlayerPieces(PlayerType.BLACK);
		
		//get the white player's pieces \/ (down)
		ArrayList<Piece> whitePieces = gd.getPlayerPieces(PlayerType.WHITE);
		
		
		//loop through each black piece and add it to their inventory
		for (Piece piece : blackPieces) {
			
			//skip pieces that are on the board
			if (piece.isOnBoard()) continue;
			
			
			
		}
		
		//loop through each white piece and add it to their inventory
		for (Piece piece : whitePieces) {
			
			//skip pieces that are on the board
			if (piece.isOnBoard()) continue;
			
			
			
		}
		
		
		
		
		
	}
	
	
	
	
}
