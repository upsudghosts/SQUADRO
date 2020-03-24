package iia.games.nim;

import iia.games.base.IMove;

public class MoveNim implements IMove {
    public static final int MAX_N = 3;
    public static final int MIN_N = 1;
    public int n;

    MoveNim(int n){
        assert n <= MAX_N;
        this.n = n;
    }

    @Override
    public String toString() {
        return Integer.toString(n);
    }
}
