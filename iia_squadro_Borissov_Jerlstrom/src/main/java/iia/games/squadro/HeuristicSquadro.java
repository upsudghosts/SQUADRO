package iia.games.squadro;

import iia.games.base.IHeuristic;

public class HeuristicSquadro {
	public static final IHeuristic<RoleSquadro, BoardSquadro> hZero = (BoardSquadro, r) -> 0;

	public static final IHeuristic<RoleSquadro, BoardSquadro> hReal = (BoardSquadro, r) -> {
		int scoreAmi = 0;
		int scoreAdv = 0;
		int nbAdvFini = 0;
		int nbAmiFini = 0;
		int nbAmiAl = 0;
		int oldX = -1;
		BoardSquadro bCop = BoardSquadro.copy();
		switch(r) {
		case VERTICAL:
			
			for(int i = 0; i < 10; i++) {
				if(i <=4) {//ADV
					if(bCop.Board.get(i).y==1) {
						scoreAdv += 6 + (6 - bCop.Board.get(i).x);
						if(bCop.Board.get(i).x == 0) scoreAdv += 12;
						
					}else {
						scoreAdv += bCop.Board.get(i).x;
					}
				} else {//AMI
					if(bCop.Board.get(i).y==1) {
						scoreAmi += 6 + (6 - bCop.Board.get(i).x);
						if(bCop.Board.get(i).x == 0) scoreAmi += 12;
						if(i > 5 && bCop.Board.get(i).x != 6) {
							if(bCop.Board.get(i).x == oldX) nbAmiAl++;
						}
						oldX =  bCop.Board.get(i).x;
						
					}else {
						scoreAmi += bCop.Board.get(i).x;
						
						if(i > 5 && bCop.Board.get(i).x != 0) {
							if(bCop.Board.get(i).x == oldX) nbAmiAl++;
						}
						oldX =  bCop.Board.get(i).x;
					}
				}
			}
			
			break;
		case HORIZONTAL:
			
			for(int i = 0; i < 10; i++) {
				if(i <=4) {//AMI
					if(bCop.Board.get(i).y==1) {
						scoreAmi += 6 + (6 - bCop.Board.get(i).x);
						if(bCop.Board.get(i).x == 0) scoreAmi += 12;
						
						if(i > 5 && bCop.Board.get(i).x != 6) {
							if(bCop.Board.get(i).x == oldX) nbAmiAl++;
						}
						oldX =  bCop.Board.get(i).x;
					}else {
						scoreAmi += bCop.Board.get(i).x;
						

						if(i > 5 && bCop.Board.get(i).x != 0) {
							if(bCop.Board.get(i).x == oldX) nbAmiAl++;
						}
						oldX =  bCop.Board.get(i).x;
					}
				} else {//ADV
					if(bCop.Board.get(i).y==1) {
						scoreAdv += 6 + (6 - bCop.Board.get(i).x);
						if(bCop.Board.get(i).x == 0) scoreAdv += 12;
							
						
					
					}else {
						scoreAdv += bCop.Board.get(i).x;
						
						
					}
				}
			}
			
			break;
		}
	
	

		int heuristicValue = scoreAmi - scoreAdv - (10*nbAmiAl); //+ ((2*nbAdvRetour) - nbAmiRetour);
		return heuristicValue;
	};
}
