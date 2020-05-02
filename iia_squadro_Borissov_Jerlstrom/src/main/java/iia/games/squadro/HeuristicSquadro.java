package iia.games.squadro;

import iia.games.base.IHeuristic;

public class HeuristicSquadro  {
	public static final IHeuristic<RoleSquadro, BoardSquadro> hZero = (boardNim, r) -> 0;
	
	public static final IHeuristic<RoleSquadro, BoardSquadro> hReal = (boardNim, r) -> {
		int nbPionsFini;
		int nbPionsAlignes;
		switch(r) {
		case VERTICAL:
			break;
			
		case HORIZONTAL:
			break;
		}
		
		return 1;
	};
}
