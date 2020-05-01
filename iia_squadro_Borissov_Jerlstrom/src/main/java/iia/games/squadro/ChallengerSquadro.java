package iia.games.squadro;

import java.awt.Point;

import iia.games.algo.Minimax;
import iia.games.base.IChallenger;

public class ChallengerSquadro implements IChallenger {
	RoleSquadro joueur;
	RoleSquadro adversaire;
	BoardSquadro board;

    public MoveSquadro pickMove(MoveSquadro enemyMove) {
        return null;
    }

	@Override
	public String teamName() {
		return "Borissov_Jerlstrom";
	}

	@Override
	public void setRole(String role) {
		//affectation des roles
		switch(role) {
		case "HORIZONTAL":
			joueur = RoleSquadro.HORIZONTAL;
			adversaire = RoleSquadro.VERTICAL;
			break;
		case "VERTICAL":
			joueur = RoleSquadro.VERTICAL;
			adversaire = RoleSquadro.HORIZONTAL;
			break;
		}
		
		board = new BoardSquadro();
		
	}

	@Override
	public void iPlay(String move) {
		board.play(move, joueur.toString());
	}

	@Override
	public void otherPlay(String move) {
		board.play(move, adversaire.toString());
		
	}

	@Override
	public String bestMove() {
		Minimax mimax = new Minimax(joueur, adversaire);
		MoveSquadro move = (MoveSquadro) mimax.bestMove(board, joueur, HeuristicSquadro.hZero);
		return moveToString(move);
	}

	@Override
	public String victory() {
		return "All your base are belong to us";
	}

	@Override
	public String defeat() {
		return "Waving a white flag in a french manner";
	}

	@Override
	public String tie() {
		return "Still counts as a win";
	}
	
	
	
	
	private String moveToString(MoveSquadro move) {
		int piece = move.getPiece();
		int i;
		char charDeb, charFin;
		String m1, m2;
		String moveF = "";
		
		Point avant = board.Board.get(piece);
		
		BoardSquadro boardCopy = board.copy();
		boardCopy.play(move, joueur);
		Point apres = boardCopy.Board.get(piece);
		
		switch(joueur){
			case HORIZONTAL:
				i = piece + 2;
				
				charDeb = board.intToChar(avant.x);
				m1 = charDeb + String.valueOf(i);
				
				charFin = board.intToChar(apres.x);
				m2 = charFin + String.valueOf(i);
				
				moveF = board.combMove(m1, m2);
				break;
				
			case VERTICAL:
				i = (piece%5) + 1;
				
				charDeb = board.intToChar(7 - avant.x);
				m1 = board.intToChar(i)+String.valueOf(charDeb);
				
				charFin = board.intToChar(7 - apres.x);
				m2 = board.intToChar(i)+String.valueOf(charFin);
				
				moveF = board.combMove(m1, m2);
				break;
			
		}
		System.out.println(moveF);
		return moveF;
	}
}
