package iia.games.squadro;

import iia.games.base.IHeuristic;

public class HeuristicSquadro {
	public static final IHeuristic<RoleSquadro, BoardSquadro> hZero = (BoardSquadro, r) -> 0;

	public static final IHeuristic<RoleSquadro, BoardSquadro> hReal = (BoardSquadro, r) -> {
		int scoreAmi = 0;
		int scoreAdv = 0;
		BoardSquadro bCop = BoardSquadro.copy();
		switch(r) {
		case VERTICAL:
			
			for(int i = 0; i < 10; i++) {
				if(i <=4) {//ADV
					if(bCop.Board.get(i).y==1) {
						scoreAdv += 6 + (6 - bCop.Board.get(i).x);
					}else {
						scoreAdv += bCop.Board.get(i).x;
					}
				} else {//AMI
					if(bCop.Board.get(i).y==1) {
						scoreAmi += 6 + (6 - bCop.Board.get(i).x);
					}else {
						scoreAmi += bCop.Board.get(i).x;
					}
				}
			}
			
			break;
		case HORIZONTAL:
			
			for(int i = 0; i < 10; i++) {
				if(i <=4) {//AMI
					if(bCop.Board.get(i).y==1) {
						scoreAmi += 6 + (6 - bCop.Board.get(i).x);
					}else {
						scoreAmi += bCop.Board.get(i).x;
					}
				} else {//ADV
					if(bCop.Board.get(i).y==1) {
						scoreAdv += 6 + (6 - bCop.Board.get(i).x);
					}else {
						scoreAdv += bCop.Board.get(i).x;
					}
				}
			}
			
			break;
		}
	
		

		int heuristicValue = scoreAmi - scoreAdv;
		System.out.println("HEURISTIC VALUE = " + heuristicValue);
		return heuristicValue;
	};
}
