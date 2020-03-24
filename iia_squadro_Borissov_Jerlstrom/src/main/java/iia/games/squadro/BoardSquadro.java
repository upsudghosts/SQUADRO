package iia.games.squadro;

import iia.games.base.ABoard;

import java.awt.Point;
import java.util.ArrayList;

public class BoardSquadro extends ABoard<MoveSquadro, RoleSquadro, BoardSquadro> {
	ArrayList<Point> Board;
    /*
        INTERFACE
     */
	public BoardSquadro() {
		Board = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Board.add(new Point(0, 0));
		}
	}
	
	public BoardSquadro(ArrayList<Point> b) {
		Board = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Board.add(b.get(i));
		}
	}
	
	public BoardSquadro copy() {
		return new BoardSquadro(this.Board);
	}

    @Override
    public BoardSquadro play(MoveSquadro move, RoleSquadro role) {
        return null;
    }

    @Override
    public ArrayList<MoveSquadro> possibleMoves(RoleSquadro role) {
    	ArrayList<MoveSquadro> moves = new ArrayList<MoveSquadro>();

    	switch(role) {
    	
    	case HORIZONTAL:
    		for(int i = 0; i < 5; i++) {
    			
    			if(!(Board.get(i).x == 0 && Board.get(i).y == 1)) {
    				moves.add(new MoveSquadro(i));
    				
    			}
    		}
    		break;
    		
    	case VERTICAL:
    		for(int i = 5; i < 10; i++) {
    			
    			if(!(Board.get(i).x == 0 && Board.get(i).y == 1)) {
    				moves.add(new MoveSquadro(i));
    			}
    			
    		}
    		break;
    		
    	}
    	
        return moves;
    }


    @Override
    public boolean isGameOver() {
    	int j1 = 0; 
    	int j2 = 0;
    	
    	for(int i = 0; i < 10; i++) {
    		if(j1 >= 4|| j2 >= 4) {
    			return true;
    		}
    		if(i < 5 && Board.get(i).x == 0 && Board.get(i).y == 1) j1++;
    		if(i >= 5 && Board.get(i).x == 0 && Board.get(i).y == 1) j2++;
    	}
    	
    	return false;
    }

    @Override
    public boolean isValidMove(MoveSquadro move, RoleSquadro role) {
    		ArrayList<MoveSquadro> moves = possibleMoves(role);
    		
    		if(moves.contains(move)) {
    			return true;
    		}
    		
            return false;
    }

}
