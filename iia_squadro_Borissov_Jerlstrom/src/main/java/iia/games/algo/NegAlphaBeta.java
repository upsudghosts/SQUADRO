package iia.games.algo;

import iia.games.base.IAlgo;

public class NegAlphaBeta <M extends IMove, R extends IRole, B extends IBoard<M, R, B>> implements IAlgo<M, R, B> {
	private final static int DEPTHMAXDEFAUT = 4;
    private int depthMax = DEPTHMAXDEFAUT;
    private final R roleMax;
    private final R roleMin;
    private int nbNodes;
    private int nbLeaves;
    
    public NegAlphaBeta(R roleMax, R roleMin){
        this.roleMax = roleMax;
        this.roleMin = roleMin;
    }

    public NegAlphaBeta(R roleMax, R roleMin, int depthMax){
        this(roleMax, roleMin);
        this.depthMax = depthMax;
    }
    
	/*
    IAlgo METHODS
    =============
     */
    @Override
    public M bestMove() {
        System.out.println("[NegAlphaBeta]");
        float alpha = Float.NEGATIVE_INFINITY;
        float beta = FLoat.POSITIVE_INFINITY;
        /*
        if feuille(n) {
        	bestMove = f eval(n);
        }
        else {
        	bestMove = - Math.infinity;
        	for
        }*/
        return bestMove;
    }

    /*
    PUBLIC METHODS
    ==============
     */

    public String toString() {
        return "NegAlphaBeta(ProfMax="+ depthMax +")";
    }
}
