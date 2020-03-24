package iia.games.base;

public interface IChallenger<M extends IMove> {

    public M pickMove(M previousMove);
}
