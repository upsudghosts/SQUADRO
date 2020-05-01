package iia.games.squadro;

import java.awt.Point;

import iia.games.algo.Minimax;
import iia.games.base.IAlgo;
import iia.games.base.IChallenger;

public class ChallengerSquadro implements IChallenger {
	RoleSquadro joueur;
	RoleSquadro adversaire;
	BoardSquadro board;
	IAlgo<MoveSquadro, RoleSquadro, BoardSquadro> algo;

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
		this.algo = new Minimax<MoveSquadro, RoleSquadro, BoardSquadro>(joueur, adversaire);
		board = new BoardSquadro();
		
	}

	@Override
	public void iPlay(String move) {
		MoveSquadro realMove = stringToMove(move, joueur);
		board = board.play(realMove, joueur);
		
		System.out.println("==============================================================");
		System.out.println(board.Board);
	}

	@Override
	public void otherPlay(String move) {
		MoveSquadro realMove = stringToMove(move, adversaire);
		board = board.play(realMove, adversaire);
		
	}

	@Override
	public String bestMove() {
		MoveSquadro move = algo.bestMove(board, joueur, HeuristicSquadro.hZero);
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
	
	
	
	public MoveSquadro stringToMove(String move, RoleSquadro joueur) {
		String[] moveS = move.split("");
		String pieceABouger;
		int piece = -1;
		switch(joueur) {
		case HORIZONTAL:
			pieceABouger = moveS[1];
			piece = Integer.parseInt(pieceABouger) - 2;
			break;
		case VERTICAL:
			pieceABouger = moveS[0];
			piece = board.stringToInt(pieceABouger);
			break;
		}
		
		
		MoveSquadro moveToRet = new MoveSquadro(piece);
		
		return moveToRet;
	}
	
	
	private String moveToString(MoveSquadro move) {
		int piece = move.getPiece();
		int i;
		char charDeb, charFin;
		String m1, m2;
		String moveF = "";
		
		//System.out.println("AVANT:" + board.Board.get(piece).y);
		Point avant = board.Board.get(piece);
		
		BoardSquadro boardCopy = board.copy();
		
		boardCopy = boardCopy.play(move, joueur);
		//System.out.println("APRES:" + boardCopy.Board.get(piece).y);
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
				
				
				m1 = board.intToChar(i)+String.valueOf(7 - avant.x);
				
				m2 = board.intToChar(i)+String.valueOf(7 - apres.x);
				
				moveF = board.combMove(m1, m2);
				break;
			
		}
		System.out.println(moveF);
		return moveF;
	}
}
