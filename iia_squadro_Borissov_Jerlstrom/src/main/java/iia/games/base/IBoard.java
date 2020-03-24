package iia.games.base;

import java.util.ArrayList;

public interface IBoard<M extends IMove, R extends IRole, B extends IBoard<M, R, B>> {

    public B play(M move, R role);

    public ArrayList<B> successors(R r);

    public ArrayList<M> possibleMoves(R r);

    public boolean isGameOver();

    public boolean isValidMove(M move, R role);
}

