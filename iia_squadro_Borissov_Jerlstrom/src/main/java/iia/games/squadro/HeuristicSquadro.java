package iia.games.squadro;

import iia.games.base.IHeuristic;

public class HeuristicSquadro  {
	public static final IHeuristic<RoleSquadro, BoardSquadro> hZero = (BoardSquadro, r) -> 0;
	
	public static final IHeuristic<RoleSquadro, BoardSquadro> hReal = (BoardSquadro, r) -> {
		int nbPionsFini = 0;
		int nbPionsAlignesAmi = 0;
		int nbPionsAdvDanger = 0;
		int speedAdv = 0;
		BoardSquadro bCop = BoardSquadro.copy();
		
		switch(r) {
		case VERTICAL:
			
			for(int i = 5; i < 10; i++) {
				//calculs pour tout ce qui est AMI
				if(bCop.Board.get(i).y == 1 && bCop.Board.get(i).x == 0) nbPionsFini++;
				//Nb pions ami alignes
				if(i < 9) {
					if(bCop.Board.get(i).x == bCop.Board.get(i+1).x ) nbPionsAlignesAmi++;
				}
				
				//ALLER
				if(bCop.Board.get(i).y == 1 && bCop.Board.get(i).x != 0) {
					//int vitessePion = bCop.vitesse.get(i).x;
					int xCoord = 4 - bCop.Board.get(i).x;
					if(xCoord >= 0) {
						if( i - 4 == bCop.Board.get(xCoord).x)nbPionsAdvDanger++;//regarde si on se trouve pile devant un pion adverse
					}
				//RETOUR	
				} else if(bCop.Board.get(i).y == 0) {
					
					int xCoord = 6 - bCop.Board.get(i).x;
					if(xCoord < 5) {
						if( i - 4 == bCop.Board.get(xCoord).x)nbPionsAdvDanger++;//regarde si on se trouve pile devant un pion adverse
					}
				}
				
				
			}
			
	
			break;
			
		case HORIZONTAL:
			
			for(int i = 0; i < 5; i++) {
				//calculs pour tout ce qui est AMI
				if(bCop.Board.get(i).y == 1 && bCop.Board.get(i).x == 0) nbPionsFini++;
				//Nb pions ami alignes
				if(i < 4) {
					if(bCop.Board.get(i).x == bCop.Board.get(i+1).x ) nbPionsAlignesAmi++;
				}
				
				//ALLER
				if(bCop.Board.get(i).y == 1 && bCop.Board.get(i).x != 0) {
					int xCoord = 5 + bCop.Board.get(i).x;
					if(xCoord <= 9) {
						if( 5 - i  == bCop.Board.get(xCoord).x)nbPionsAdvDanger++;
					}
				//RETOUR
				} else if(bCop.Board.get(i).y == 0) {
					int xCoord = 3 + bCop.Board.get(i).x;
					if(xCoord > 4) {
						if( 5 - i  == bCop.Board.get(xCoord).x)nbPionsAdvDanger++;
					}
				}
				
				
			}
	
			
			break;
		}
	
		int heuristicValue = nbPionsAdvDanger*10;
		System.out.println("HEURISTIC VALUE = " + heuristicValue);
		return heuristicValue;
	};
}
