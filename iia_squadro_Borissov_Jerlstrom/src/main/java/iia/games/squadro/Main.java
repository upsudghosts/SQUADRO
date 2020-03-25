package iia.games.squadro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import iia.games.algo.AlwaysFirst;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Random rand = new Random(); 
		
		BoardSquadro squadro = new BoardSquadro();
		PlayerSquadro p_hor = new PlayerSquadro(
				"Yellow",
				new AlwaysFirst<MoveSquadro, RoleSquadro, BoardSquadro>(),
				RoleSquadro.HORIZONTAL, 
				HeuristicSquadro.hZero);
		PlayerSquadro p_vert = new PlayerSquadro(
				"Red",
				new AlwaysFirst<MoveSquadro, RoleSquadro, BoardSquadro>(),
				RoleSquadro.VERTICAL,
				HeuristicSquadro.hZero);
		
		System.out.println("Game starts.");
		PlayerSquadro currPlayer = p_hor;
		int i = 0;
		while(!squadro.isGameOver()) {
			ArrayList<MoveSquadro> moves = squadro.possibleMoves(currPlayer.getRole());
			squadro = squadro.play(moves.get(rand.nextInt(moves.size())),currPlayer.getRole());
			System.out.println(currPlayer.getRole().toString() + " made a move.");
			if(currPlayer.getRole() == p_hor.getRole()) {
				currPlayer = p_vert;
			}else {
				currPlayer = p_hor;
			}
			try {
				squadro.saveToFile("./Saves/Sauvegarde"+i+".txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		System.out.println("Game ends.");
		
		
	}

}
