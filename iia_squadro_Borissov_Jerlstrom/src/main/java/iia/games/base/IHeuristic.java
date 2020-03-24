package iia.games.base;


public interface IHeuristic<R extends IRole, B extends IBoard<?, R, ?>> {

    public int eval(B b, R r);
}
