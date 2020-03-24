package iia.games.squadro;

import iia.games.base.IMove;

public class MoveSquadro implements IMove {
	int piece;
	
	public MoveSquadro(int p) {
		piece = p;
	}
	
	public int getPiece() {
		return piece;
	}

}
