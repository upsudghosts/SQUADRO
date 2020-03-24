package iia.games.base;

public interface IPlayer<M extends IMove, R extends IRole, B extends IBoard<M, R, B>> {

    public M chooseMove(B b);
}
