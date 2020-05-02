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
	private final static int PROFMAXDEFAUT = 4;

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
	private R joueurMin;

	/**
	 * Le joueur Max (celui dont l'algorithme de recherche adopte le point de vue)
	 */
	private R joueurMax;

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
	public AlphaBeta(R joueurMax, R joueurMin) {
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
	}

	public AlphaBeta(R joueurMax, R joueurMin, int profMaxi) {
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
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
		ArrayList<M> allMoves = b.possibleMoves(joueurMax);
		int Max = Integer.MIN_VALUE;
		M bestMove = null;
		if (allMoves.size() > 0)
			bestMove = allMoves.get(0);
		for (M move : allMoves) {
			B new_b = b.play(move, joueurMax);
			
			int newMax = minMax(new_b, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, h);
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
			return h.eval(b, joueurMax);
		} else {
			for (M move : b.possibleMoves(joueurMax)) {
				nbNodes++; // compte aussi les feuilles comme noeuds attention
				B next_b = b.play(move, joueurMax);
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
			return h.eval(b, joueurMin);
		} else {
			for (M move : b.possibleMoves(joueurMin)) {
				nbNodes++; // compte aussi les feuilles comme noeuds attention
				B next_b = b.play(move, joueurMin);
				beta = min(beta, maxMin(next_b, pCurr + 1, alpha, beta, h));
				if (alpha >= beta)
					return alpha;
			}
			return beta;
		}
	}

}
