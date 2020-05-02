package iia.games.algo;

import java.util.ArrayList;

import iia.games.base.IAlgo;
import iia.games.base.IBoard;
import iia.games.base.IHeuristic;
import iia.games.base.IMove;
import iia.games.base.IRole;


public class NegAlphaBeta<M extends IMove, R extends IRole, B extends IBoard<M, R, B>> implements IAlgo<M, R, B> {

	/**
	 * La profondeur de recherche par défaut
	 */
	private final static int DEPTHMAXDEFAUT = 2;

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
	public NegAlphaBeta(R roleMax, R roleMin) {
		this.roleMax = roleMax;
		this.roleMin = roleMin;
		
	}

	public NegAlphaBeta(R roleMax, R roleMin, int depthMax) {
		this.depthMax = depthMax;
		this.roleMin = roleMin;
		this.roleMax = roleMax;
//		System.out.println("Initialisation d'un MiniMax de profondeur " + profMax);
	}

	/*
	 * IAlgo METHODS =============
	 */
	@Override
	public M bestMove (B b, R r, IHeuristic<R, B> h) {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int profondeur = depthMax;
		ArrayList<M> possibleMoves = b.possibleMoves(this.roleMax);
		long Max = NegAB(b, this.roleMax, alpha, beta, 1, profondeur);
		M bestMove = possibleMoves.get(0);
		for (M coup : possibleMoves) {
			B temp = b.play(bestMove, this.roleMax);
			long NewVal = NegAB(temp, this.roleMax, alpha, beta, 1, (profondeur - 1));

			if (NewVal > Max) {
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
	
	    
	private long NegAB( B b , R r ,int alpha, int beta , int parite, int profondeur) {
	    ArrayList<M> coups = b.possibleMoves(r);
    		for( M coup : coups ) {
    			B temp = b.play(coup, r);
    			alpha = (int) Math.max(alpha, -NegAB(temp, r ,-beta,-alpha,-parite,(profondeur-1)));
    			if (alpha >= beta ) {
    				return beta; 
    			}
    		}
	   	return alpha;      	
	}
	
	
}