package iia.games.squadro;

import iia.games.base.ABoard;

import java.awt.Point;
import java.util.ArrayList;

public class BoardSquadro extends ABoard<MoveSquadro, RoleSquadro, BoardSquadro> {
	ArrayList<Point> Board;

	/*
	 * INTERFACE
	 */
	public BoardSquadro() {
		Board = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Board.add(new Point(0, 0));
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
