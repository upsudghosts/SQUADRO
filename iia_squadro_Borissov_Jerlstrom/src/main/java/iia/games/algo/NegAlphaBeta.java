package iia.games.algo;

import java.util.ArrayList;

import iia.games.base.IAlgo;
import iia.jeux.alg.Heuristique;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class NegAlphaBeta<M extends IMove, R extends IRole, B extends IBoard<M, R, B>> implements IAlgo<M, R, B> {

	/**
	 * La profondeur de recherche par défaut
	 */
	private final static int DEPTHMAXDEFAUT = 4;

	// -------------------------------------------
	// Attributs
	// -------------------------------------------

	/**
	 * La profondeur de recherche utilisée pour l'algorithme
	 */
	private int depthMax = DEPTHMAXDEFAUT;

	/**
	 * L'heuristique utilisée par l'algorithme
	 */
	private Heuristique h;

	/**
	 * Le joueur Min (l'adversaire)
	 */
	private final R roleMin;

	/**
	 * Le joueur Max (celui dont l'algorithme de recherche adopte le point de vue)
	 */
	private final R roleMax;

	/**
	 * Le nombre de noeuds développé par l'algorithme (intéressant pour se faire une
	 * idée du nombre de noeuds développés)
	 */
	private int nbNodes;

	/**
	 * Le nombre de feuilles évaluées par l'algorithme
	 */
	private int nbLeaves;

	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public NegAlphaBeta(Heuristique h, R roleMax, R roleMin) {
		this(h, roleMax, roleMin, PROFMAXDEFAUT);
	}

	public NegAlphaBeta(Heuristique h, R roleMax, R roleMin, int depthMax) {
		this.depthMax = depthMax;
		this.h = h;
		this.roleMin = roleMin;
		this.roleMax = roleMax;
//		System.out.println("Initialisation d'un MiniMax de profondeur " + profMax);
	}

	/*
	 * IAlgo METHODS =============
	 */
	@Override
	public M bestMove(B b, R r, IHeuristic<R, B> h) {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int profondeur = profMax;
		ArrayList<M> possibleMoves = b.possibleMoves(this.roleMax);
		long Max = NegAlphaBeta(b, this.joueurMax, alpha, beta, 1, profondeur);
		M bestMove = possibleMoves.get(0);
		for (M coup : possibleMoves) {
			B temp = b.copy();
			temp = temp.play(bestMove, this.roleMax);
			long NewVal = NegAlphaBeta(temp, this.joueurMax, alpha, beta, 1, (profondeur - 1));

			if (NewVal > Max) {
				System.out.println(
						" validité du coup :" + b.isValidMove(coup, this.joueurMax) + "////" + moveToString(coup));
				bestMove = coup;
				Max = NewVal;
			}
		}
		return bestMove;
	}

	/*
	 * PUBLIC METHODS ==============
	 */

	public String toString() {
		return "NegAlphaBeta(ProfMax=" + depthMax + ")";
	}
	
	/*
	 * PRIVATE METHODS ==============
	 */
	
	    
	private long NegAlphaBeta( B b , R r ,int alpha, int beta , int parite, int profondeur) {
	    ArrayList<CoupJeu> coups = b.possibleMoves(r);
	   	if( profondeur == 0 ) {
	   		h.setJoueurs(this.joueurMax, this.joueurMin);; 
	   		int Max= h.eval(b, r)*parite;
	   	}else {
    		for( M coup : coups ) {
    			B temp = b.copy();
    			temp = temp.play(coup, r);
    			alpha = Math.max(alpha, -NegAlphaBeta(temp, r ,-beta,-alpha,-parite,(profondeur-1)));
    			if (alpha >= beta ) {
    				return beta; 
    			}
    		}
	   	}
	   	return alpha;      	
	}
	
	private String moveToString(M move) {
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
        //System.out.println(moveF);
        return moveF;
    }
}
}
