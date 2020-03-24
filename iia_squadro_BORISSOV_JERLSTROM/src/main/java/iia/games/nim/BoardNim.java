package iia.games.nim;

import iia.games.base.ABoard;

import java.util.ArrayList;

public class BoardNim extends ABoard<MoveNim, RoleNim, BoardNim> {

    private final int n_matches;
    public final static int START_N_MATCHES = 7;
    public final static int MAX_N = 3;

    BoardNim(int n){
        n_matches = n;
    }

    @Override
    public BoardNim play(MoveNim move, RoleNim role) {
        final int new_n =  Math.max(0, n_matches - move.n);
        return new BoardNim(new_n);
    }

    @Override
    public ArrayList<MoveNim> possibleMoves(RoleNim r) {
        ArrayList<MoveNim> all_moves =  new ArrayList<>();
        for (int i=MoveNim.MIN_N; i <= MoveNim.MAX_N && i <= n_matches; i++){
            all_moves.add(new MoveNim(i));
        }
        return all_moves;
    }

    @Override
    public boolean isGameOver() {
        return n_matches == 0;
    }

    @Override
    public boolean isValidMove(MoveNim move, RoleNim role) {
        return move.n <= MAX_N;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BoardNim{ ");
        for (int i=0; i<n_matches; i++)
            sb.append('|');
        sb.append(" }");
        return sb.toString();
    }

    public int getN_matches() {
        return n_matches;
    }
}
