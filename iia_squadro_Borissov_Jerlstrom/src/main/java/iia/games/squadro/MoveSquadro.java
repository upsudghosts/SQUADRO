package iia.games.squadro;

import iia.games.base.IMove;

public class MoveSquadro implements IMove {
	int piece;
	
	/** Constructeur initialisant la piece */
	public MoveSquadro(int p) {
		piece = p;
	}
	
	/** Getter pour recuperer la valeur d'une piece (l'identifiant)*/
	public int getPiece() {
		return piece;
	}

}
