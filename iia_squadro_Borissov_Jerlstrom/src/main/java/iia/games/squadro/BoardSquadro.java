package iia.games.squadro;

import iia.games.base.ABoard;

import java.awt.Point;
import java.util.ArrayList;

public class BoardSquadro extends ABoard<MoveSquadro, RoleSquadro, BoardSquadro> {
	ArrayList<Point> Board;
	static ArrayList<Point> vitesse = new ArrayList<Point>();

	/*
	 * INTERFACE
	 */
	public BoardSquadro() {
		Board = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Board.add(new Point(0, 0));
			if(i == 0 || i == 4 || i == 6 || i == 8) vitesse.add(new Point(1, 3));
			if(i == 1 || i == 3 || i == 5 || i == 9) vitesse.add(new Point(3, 1));
			if(i == 2 || i == 7) vitesse.add(new Point(2, 2));
			
		}
	}

	public BoardSquadro(ArrayList<Point> b) {
		Board = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Board.add(b.get(i));
		}
	}

	public BoardSquadro copy() {
		return new BoardSquadro(this.Board);
	}

	public ArrayList<Integer> getAdv(MoveSquadro move, RoleSquadro role) {// nb adv consecutifs
		ArrayList<Integer> adv = new ArrayList<Integer>();
		int piece = move.getPiece();
		boolean consec = true;// si les pions sont consec
		int xy = Board.get(piece).x + 1;
		int AR = Board.get(piece).y;//Aller ou Retour

		switch (role) {
		
		case HORIZONTAL:
			switch(AR) {
			
			case 0:
				for (int i = 4 + xy; i < 10; i++) {
					if (Board.get(i).x == 5 - piece && consec) {
						adv.add(i);
					} else {
						consec = false;
					}
	
				}
				break;
				
			case 1:
				for (int i = 4 + xy; i > 0; i--) {
					if (Board.get(i).x == 5 - piece && consec) {
						adv.add(i);
					} else {
						consec = false;
					}
	
				}
				break;
			
			}
			break;
			

		case VERTICAL:
			switch(AR) {
			
			case 0:
				for (int i = 0; i < 5 - xy; i++) {
					if (Board.get(i).x == piece - 4 && consec) {
						adv.add(i);
					} else {
						consec = false;
					}
				}
				break;
				
			case 1:
				for (int i = 5-xy; i > 0; i--) {
					if (Board.get(i).x == piece - 4 && consec) {
						adv.add(i);
					} else {
						consec = false;
					}
				}
				break;
			}
			
			break;
		}
		return adv;
	}
	
	@Override
	public BoardSquadro play(MoveSquadro move, RoleSquadro role) {
		BoardSquadro BoardInt = this.copy();
		int piece = move.getPiece();
		int AR = Board.get(piece).y;
		int speed;
		if(AR == 0) {
			speed = vitesse.get(piece).x;
		} else {
			speed = vitesse.get(piece).y;
		}
		
		ArrayList<Integer> ListPieceAdv = new ArrayList<Integer>();
		int nbAdv;
	
		switch (role) {

		case HORIZONTAL:
			
			for(int i = 0; i < speed; i++) {
				
				int x = BoardInt.Board.get(piece).x;
				ListPieceAdv = getAdv(move, role);
				nbAdv = ListPieceAdv.size();
				
				if(ListPieceAdv.size() == 0) {
					if(x+1 > 6) BoardInt.Board.set(piece, new Point(6, 1));
					else BoardInt.Board.set(piece, new Point(x+1, 0));
					
				} else {
					if(x+nbAdv > 6) BoardInt.Board.set(piece, new Point(6, 1));
					else BoardInt.Board.set(piece, new Point(x+nbAdv, 0));
					for(int adv : ListPieceAdv) {
						if(BoardInt.Board.get(adv).y == 1 )BoardInt.Board.set(adv, new Point(6, 1));
						else BoardInt.Board.set(adv, new Point(0, 0));
					}
					
					break;
				}
			}
			break;

		case VERTICAL:
			for(int i = 0; i < speed; i ++) {
				
				int x = BoardInt.Board.get(piece).x;
				ListPieceAdv = getAdv(move, role);
				nbAdv = ListPieceAdv.size();
				if(ListPieceAdv.size() == 0) {
					if(x+1 > 6) ;
				}
				
			}
			break;
		}
		return BoardInt;
	}

	@Override
	public ArrayList<MoveSquadro> possibleMoves(RoleSquadro role) {
		ArrayList<MoveSquadro> moves = new ArrayList<MoveSquadro>();

		switch (role) {

		case HORIZONTAL:
			for (int i = 0; i < 5; i++) {

				if (!(Board.get(i).x == 0 && Board.get(i).y == 1)) {
					moves.add(new MoveSquadro(i));

				}
			}
			break;

		case VERTICAL:
			for (int i = 5; i < 10; i++) {

				if (!(Board.get(i).x == 0 && Board.get(i).y == 1)) {
					moves.add(new MoveSquadro(i));
				}

			}
			break;

		}

		return moves;
	}

	@Override
	public boolean isGameOver() {
		int j1 = 0;
		int j2 = 0;

		for (int i = 0; i < 10; i++) {
			if (j1 >= 4 || j2 >= 4) {
				return true;
			}
			if (i < 5 && Board.get(i).x == 0 && Board.get(i).y == 1)
				j1++;
			if (i >= 5 && Board.get(i).x == 0 && Board.get(i).y == 1)
				j2++;
		}

		return false;
	}

	@Override
	public boolean isValidMove(MoveSquadro move, RoleSquadro role) {
		ArrayList<MoveSquadro> moves = possibleMoves(role);

		if (moves.contains(move)) {
			return true;
		}

		return false;
	}

}
