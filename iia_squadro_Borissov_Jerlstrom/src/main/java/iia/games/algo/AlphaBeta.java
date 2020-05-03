package iia.games.algo;

import java.util.ArrayList;

import iia.games.base.IAlgo;
import iia.games.base.IBoard;
import iia.games.base.IHeuristic;
import iia.games.base.IMove;
import iia.games.base.IRole;

public class AlphaBeta<M extends IMove, R extends IRole, B extends IBoard<M, R, B>> implements IAlgo<M, R, B> {

	/**
	 * La profondeur de recherche par défaut
	 */
	private final static int PROFMAXDEFAUT = 12;

	// -------------------------------------------
	// Attributs
	// -------------------------------------------

	/**
	 * La profondeur de recherche utilisée pour l'algorithme
	 */
	private int profMax = PROFMAXDEFAUT;

	/**
	 * L'heuristique utilisée par l'algorithme
	 */

	/**
	 * Le joueur Min (l'adversaire)
	 */
	private R roleMin;

	/**
	 * Le joueur Max (celui dont l'algorithme de recherche adopte le point de vue)
	 */
	private R roleMax;

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
	public AlphaBeta(R roleMax, R roleMin) {
		this.roleMin = roleMin;
		this.roleMax = roleMax;
	}

	public AlphaBeta(R roleMax, R roleMin, int profMaxi) {
		this.roleMin = roleMin;
		this.roleMax = roleMax;
		profMax = profMaxi;
//		System.out.println("Initialisation d'un MiniMax de profondeur " + profMax);
	}

	// -------------------------------------------
	// Méthodes de l'interface AlgoJeu
	// -------------------------------------------
	@Override
	public M bestMove(B b, R r, IHeuristic<R, B> h) {
		System.out.println("[AlphaBeta]");
		
		this.nbNodes = 1;
	    this.nbLeaves = 0;
		/* A vous de compléter le corps de ce fichier */
		ArrayList<M> allMoves = b.possibleMoves(roleMax);
		int Max = Integer.MIN_VALUE;
		M bestMove = null;
		if (allMoves.size() > 0)
			bestMove = allMoves.get(0);
		for (M move : allMoves) {
			B new_b = b.play(move, roleMax);
			
			int newMax = minMax(new_b, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, h);
			System.out.println("newMAX = " + newMax);
			System.out.println("MAX = " + Max);
			System.out.println("------------------------");
			if (newMax > Max) {
				bestMove = move;
				Max = newMax;
			}
		}
		return bestMove;
	}

	// -------------------------------------------
	// Méthodes publiques
	// -------------------------------------------
	public String toString() {
		return "AlphaBeta(ProfMax=" + profMax + ")";
	}

	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------
	public int min(int a, int b) {
		return (a < b) ? a : b;
	}

	public int max(int a, int b) {
		return (a > b) ? a : b;
	}

	public int maxMin(B b, int pCurr, int alpha, int beta, IHeuristic<R, B> h ) {
		if (pCurr >= profMax) {
			nbLeaves++;
			return h.eval(b, roleMax);
		} else {
			for (M move : b.possibleMoves(roleMax)) {
				nbNodes++; // compte aussi les feuilles comme noeuds attention
				B next_b = b.play(move, roleMax);
				alpha = max(alpha, minMax(next_b, pCurr + 1, alpha, beta, h));
				if (alpha >= beta)
					return beta;
			}
			return alpha;
		}
	}

	public int minMax(B b, int pCurr, int alpha, int beta, IHeuristic<R, B> h) {
		if (pCurr >= profMax) {
			nbLeaves++;
			return h.eval(b, roleMin);
		} else {
			for (M move : b.possibleMoves(roleMin)) {
				nbNodes++; // compte aussi les feuilles comme noeuds attention
				B next_b = b.play(move, roleMin);
				beta = min(beta, maxMin(next_b, pCurr + 1, alpha, beta, h));
				if (alpha >= beta)
					return alpha;
			}
			return beta;
		}
	}

}
