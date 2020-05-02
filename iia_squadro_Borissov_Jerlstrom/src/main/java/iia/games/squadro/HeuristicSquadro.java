package iia.games.squadro;

import iia.games.base.IHeuristic;

public class HeuristicSquadro {
	public static final IHeuristic<RoleSquadro, BoardSquadro> hZero = (BoardSquadro, r) -> 0;

	public static final IHeuristic<RoleSquadro, BoardSquadro> hReal = (BoardSquadro, r) -> {
		int nbPionsFini = 0;
		int nbPionsAlignesAmi = 0;
		int nbPionsAdvDanger = 0;
		int speedAdv = 0;
		BoardSquadro bCop = BoardSquadro.copy();

		switch (r) {
		case VERTICAL:

			for (int i = 5; i < 10; i++) {
				// calculs pour tout ce qui est AMI
				if (bCop.Board.get(i).y == 1 && bCop.Board.get(i).x == 0)
					nbPionsFini++;
				// Nb pions ami alignes
				if (i < 9) {
					if (bCop.Board.get(i).x == bCop.Board.get(i + 1).x)
						nbPionsAlignesAmi++;
				}

				// ALLER
				int vitessePion = bCop.vitesse.get(i).x;
				for (int j = 0; j <= vitessePion; j++) {

					if (bCop.Board.get(i).y == 0) {

						int advCoord = 4 - bCop.Board.get(i).x - j;
						if (advCoord >= 0) {

							if (i - 4 == bCop.Board.get(advCoord).x)
								nbPionsAdvDanger++;// regarde si on se trouve pile devant un pion adverse
						}
						// RETOUR
					} else if (bCop.Board.get(i).y == 1 && bCop.Board.get(i).x != 0) {

						int advCoord = 6 - bCop.Board.get(i).x + j;
						if (advCoord < 5) {
							if (i - 4 == bCop.Board.get(advCoord).x)
								nbPionsAdvDanger++;// regarde si on se trouve pile devant un pion adverse
						}
					}
				}

			}

			break;

		case HORIZONTAL:

			for (int i = 0; i < 5; i++) {
				// calculs pour tout ce qui est AMI
				if (bCop.Board.get(i).y == 1 && bCop.Board.get(i).x == 0)
					nbPionsFini++;
				// Nb pions ami alignes
				if (i < 4) {
					if (bCop.Board.get(i).x == bCop.Board.get(i + 1).x)
						nbPionsAlignesAmi++;
				}

				int vitessePion = bCop.vitesse.get(i).x;
				for (int j = 0; j <= vitessePion; j++) {
					// ALLER
					if (bCop.Board.get(i).y == 0) {
						int advCoord = 5 + bCop.Board.get(i).x + j;
						if (advCoord <= 9) {
							if (5 - i == bCop.Board.get(advCoord).x)
								nbPionsAdvDanger++;
						}
						// RETOUR
					} else if (bCop.Board.get(i).y == 1 && bCop.Board.get(i).x != 0) {
						int advCoord = 3 + bCop.Board.get(i).x - j;
						if (advCoord > 4) {
							if (5 - i == bCop.Board.get(advCoord).x)
								nbPionsAdvDanger++;
						}
					}

				}
			}

			break;
		}

		int heuristicValue = nbPionsAdvDanger * 10;
		System.out.println("HEURISTIC VALUE = " + heuristicValue);
		return heuristicValue;
	};
}
