package GameLogic;

import java.awt.*;
import java.util.*;

import javax.swing.*;

public class Game {


	public static void main(String [] args) {
		System.out.println("test");
		
		
		//TESTING CODE FOR THE DISPLAY
		//Create the test frame
		JFrame gameFrame = new JFrame();
		gameFrame.setTitle("Shogi");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//create a test layout
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);
		
		//Set up my test GUI
		GameControl gc = new GameControl();
		JPanel View1 = new GamePanel(gc);	
		
		container.add(View1, "1");
		
		cardLayout.show(container, "1");
		
		gameFrame.add(container);
		
		gameFrame.setSize(1000, 1000);
		gameFrame.setVisible(true);
		
		
		//Creating a mock GameData object for testing
		GameData gd = new GameData();
		//create a list of pieces to add to GameData
		Piece bishop = new Piece(PlayerType.BLACK, PieceType.BISHOP, 1, 7);
		Piece king = new Piece(PlayerType.BLACK, PieceType.KING, 4, 8);
		Piece rook = new Piece(PlayerType.BLACK, PieceType.ROOK, 7, 7);
		Piece pawn1 = new Piece(PlayerType.BLACK, PieceType.PAWN, 0, 6);
		Piece pawn2 = new Piece(PlayerType.BLACK, PieceType.PAWN, 1, 6);
		Piece pawn3 = new Piece(PlayerType.BLACK, PieceType.PAWN, 2, 6);
		
		ArrayList<Piece> pieces = new ArrayList<>();
		pieces.add(bishop);
		pieces.add(king);
		pieces.add(rook);
		pieces.add(pawn1);
		pieces.add(pawn2);
		
		pieces.get(0).setPromoted(true);
		pieces.get(4).setPromoted(true);
		
		ArrayList<Piece> whitePieces = new ArrayList<>();
		whitePieces.add(new Piece(PlayerType.WHITE, PieceType.LANCE, 0, 0));
		whitePieces.add(new Piece(PlayerType.WHITE, PieceType.KNIGHT, 1, 0));
		whitePieces.add(new Piece(PlayerType.WHITE, PieceType.SILVERGENERAL, 2, 0));
		whitePieces.add(new Piece(PlayerType.WHITE, PieceType.KING, 4, 0));
		whitePieces.add(new Piece(PlayerType.WHITE, PieceType.BISHOP, 7, 1));
		whitePieces.add(new Piece(PlayerType.WHITE, PieceType.ROOK, 1, 1));
		
		
		gd.setPlayer1Pieces(pieces);
		gd.setPlayer2Pieces(whitePieces);
		
		//calculate moves for 
		for (Piece piece : gd.getPlayer1Pieces()) {
			if (piece.getPieceType() == PieceType.BISHOP) {
				ArrayList<PieceLocation> validMoves = BishopLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.KING) {
				ArrayList<PieceLocation> validMoves = KingLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.ROOK) {
				ArrayList<PieceLocation> validMoves = RookLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.PAWN) {
				ArrayList<PieceLocation> validMoves = PawnLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.LANCE) {
				ArrayList<PieceLocation> validMoves = LanceLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.KNIGHT) {
				ArrayList<PieceLocation> validMoves = KnightLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.SILVERGENERAL) {
				ArrayList<PieceLocation> validMoves = SilverGeneralLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.GOLDGENERAL) {
				ArrayList<PieceLocation> validMoves = GoldGeneralLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			}
		}
		
		for (Piece piece : gd.getPlayer2Pieces()) {
			if (piece.getPieceType() == PieceType.BISHOP) {
				ArrayList<PieceLocation> validMoves = BishopLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.KING) {
				ArrayList<PieceLocation> validMoves = KingLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.ROOK) {
				ArrayList<PieceLocation> validMoves = RookLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.PAWN) {
				ArrayList<PieceLocation> validMoves = PawnLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.LANCE) {
				ArrayList<PieceLocation> validMoves = LanceLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.KNIGHT) {
				ArrayList<PieceLocation> validMoves = KnightLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.SILVERGENERAL) {
				ArrayList<PieceLocation> validMoves = SilverGeneralLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			} else if (piece.getPieceType() == PieceType.GOLDGENERAL) {
				ArrayList<PieceLocation> validMoves = GoldGeneralLogic.calculateMoves(piece, gd);
				piece.setAvailableMoves(validMoves);
			}
		}
		
		//((GamePanel) View1).PrintBeginningBoardTest();
		((GamePanel) View1).updateBoard(gd);
	}
}
