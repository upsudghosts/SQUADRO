package iia.games.algo;

import java.util.ArrayList;

import iia.games.base.IAlgo;
import iia.games.base.IBoard;
import iia.games.base.IHeuristic;
import iia.games.base.IMove;
import iia.games.base.IRole;


public class NegAlphaBeta<M extends IMove, R extends IRole, B extends IBoard<M, R, B>> implements IAlgo<M, R, B> {
	/** La profondeur de recherche par défaut
     */
    private final static int PROFMAXDEFAUT = 4;

   
    // -------------------------------------------
    // Attributs
    // -------------------------------------------
 
    /**  La profondeur de recherche utilisée pour l'algorithme
     */
    private int profMax = PROFMAXDEFAUT;

    /**  Le nombre de noeuds développé par l'algorithme
     * (intéressant pour se faire une idée du nombre de noeuds développés) */
    private int nbnodes;

    /** Le nombre de feuilles évaluées par l'algorithme
     */
    private int nbleaves;


  // -------------------------------------------
  // Constructeurs
  // -------------------------------------------
    public NegAlphaBeta() {
    }

    public NegAlphaBeta(int profMaxi) {
        profMax = profMaxi;
    }




   // -------------------------------------------
  // Méthodes de l'interface AlgoJeu
  // -------------------------------------------
    
   public M bestMove(B b, R r, IHeuristic<R, B> h) {
	   int profondeur=profMax;
	   ArrayList<M> allMoves = b.possibleMoves(r);
	   long  Max = negAB(b , r, Integer.MIN_VALUE, Integer.MAX_VALUE,1,  profondeur, h);
	   M bestMove = allMoves.get(0); 
	   for( M move: allMoves ) {
		   	B next_b = b.play(move, r);
			next_b.play(move, r);
			
			int newMax= negAB(next_b,r,Integer.MIN_VALUE,Integer.MAX_VALUE,1, (profondeur-1), h);
		
			if( newMax > Max  ) { 
				bestMove = move; 
				Max= newMax; 
			}
       }
        return bestMove;

    }
  // -------------------------------------------
  // Méthodes publiques
  // -------------------------------------------
    public String toString() {
        return "MiniMax(ProfMax="+profMax+")";
    }



  // -------------------------------------------
  // Méthodes internes
  // -------------------------------------------

    
    private int negAB( B b , R r ,long alpha, long beta , int parite,int profondeur, IHeuristic<R, B> h) {
    	ArrayList<M> allMoves = b.possibleMoves(r);
    	if( profondeur == 0 ) {
    		return h.eval(b, r)*parite;
    	}else {
    		for( M move : allMoves ) {
    			B next_b = b.play(move, r);
    			next_b.play(move, r);
                alpha = Math.max(alpha, -negAB(next_b,r,-beta,-alpha,-parite,(profondeur-1), h));
                	if (alpha>= beta ) {
                		return (int) beta; 
                }
            }
    	}
		return (int) alpha;  
    	
    }
	
}