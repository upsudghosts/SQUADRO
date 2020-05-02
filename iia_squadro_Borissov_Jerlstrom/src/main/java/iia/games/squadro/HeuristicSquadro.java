package iia.games.squadro;

import iia.games.base.IHeuristic;

public class HeuristicSquadro  {
	public static final IHeuristic<RoleSquadro, BoardSquadro> hZero = (boardNim, r) -> 0;
	
	public static final IHeuristic<RoleSquadro, BoardSquadro> hReal = (boardNim, r) -> {
		
		
		return 1;
	};
}
