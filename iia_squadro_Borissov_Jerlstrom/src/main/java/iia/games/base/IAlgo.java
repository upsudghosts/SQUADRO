package iia.games.base;

public interface IAlgo<M extends  IMove, R extends IRole, B extends IBoard<M, R, B>> {

    public M bestMove(B b, R r, IHeuristic<R, B> h);

}
