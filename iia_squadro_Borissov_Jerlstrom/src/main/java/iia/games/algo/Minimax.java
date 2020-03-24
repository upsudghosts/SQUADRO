package iia.games.algo;

import iia.games.base.IAlgo;
import iia.games.base.IBoard;
import iia.games.base.IHeuristic;
import iia.games.base.IMove;
import iia.games.base.IRole;

import java.util.ArrayList;

public class Minimax<M extends IMove, R extends IRole, B extends IBoard<M, R, B>> implements IAlgo<M, R, B> {

    private final static int DEPTHMAXDEFAUT = 4;
    private int depthMax = DEPTHMAXDEFAUT;
    private final R roleMax;
    private final R roleMin;
    private int nbNodes;
    private int nbLeaves;

    public Minimax(R roleMax, R roleMin){
        this.roleMax = roleMax;
        this.roleMin = roleMin;
    }

    public Minimax(R roleMax, R roleMin, int depthMax){
        this(roleMax, roleMin);
        this.depthMax = depthMax;
    }

    /*
    IAlgo METHODS
    =============
     */

    @Override
    public M bestMove(B b, R r, IHeuristic<R, B> h) {
        System.out.println("[MiniMax]");

        this.nbNodes = 1;
        this.nbLeaves = 0;
        M bestMove = null;
        int bestMoveHeuristicValue = java.lang.Integer.MIN_VALUE;
        // On calcul tous les coups possibles
        ArrayList<M> allMoves = b.possibleMoves(roleMax);
        for (M move : allMoves) {
            int heuristicMove;
            B new_b = b.play(move, roleMax);
            heuristicMove = minmax2(new_b, depthMax - 1, h);
            System.out.println("Le coup " + move + " a pour valeur minimax " + heuristicMove);
            if (heuristicMove > bestMoveHeuristicValue) {
                bestMoveHeuristicValue = heuristicMove;
                System.out.println("Le coup " + move + " est meilleur que " + bestMove);
                bestMove = move;
            }
        }
        if (bestMove == null && !allMoves.isEmpty())
            bestMove = allMoves.get(0);
        return bestMove;
    }



    /*
    PUBLIC METHODS
    ==============
     */

    public String toString() {
        return "MiniMax(ProfMax="+ depthMax +")";
    }


    /*
    PRIVATE METHODS
    ===============
     */
    public int maxmin2(B board, int depth, IHeuristic<R, B> h) {
        // C'est joueurMax qui joue
        if (depth == 0 || board.isGameOver()) {
            nbLeaves++;
            return h.eval(board, roleMax);
        } else {
            nbNodes++;
            int maxValue = java.lang.Integer.MIN_VALUE;
            // System.out.println("Coups Possibles for " + plateau + " :  "+ lesCoups);
            for (B succ : board.successors(roleMax)) {
                maxValue = Math.max(maxValue, minmax2(succ, depth - 1, h) );
                // System.out.println("maxValue for " + nPlateau + " =  "+ maxValue);
            }
            // System.out.println("maxValue " + plateau + " =  "+ maxValue);
            return maxValue;
        }
    }

    public int minmax2(B board, int depth, IHeuristic<R, B> h) {
        // C'est joueurMin qui joue
        if (depth == 0 || board.isGameOver()) {
            nbLeaves++;
            return h.eval(board, roleMin);
        } else {
            nbNodes++;
            int minValue = java.lang.Integer.MAX_VALUE;
            // System.out.println("Coups Possibles for " + plateau + " :  "+ lesCoups);
            for (B succ : board.successors(roleMin)) {
                minValue = Math.min(minValue, maxmin2(succ, depth - 1, h));
                //System.out.println("minValue for " + nPlateau + " =  "+ minValue);
            }

            // System.out.println("minValue = " + minValue);
            return minValue;
        }
    }

}
