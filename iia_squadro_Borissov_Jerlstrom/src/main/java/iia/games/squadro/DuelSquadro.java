package iia.games.squadro;

import java.util.Random;

import iia.games.algo.AlwaysFirst;

public class DuelSquadro {

    public static void main(String[] args) {
    	PlayerSquadro p1;
        PlayerSquadro p2;
        Random r = new Random();
        if (r.nextInt(2) == 0){
            p1 = new PlayerSquadro("joueur 1", new AlwaysFirst<MoveSquadro, RoleSquadro, BoardSquadro>(), RoleSquadro.HORIZONTAL, HeuristicSquadro.hZero);
            p2 = new PlayerSquadro("joueur 2", new AlwaysFirst<MoveSquadro, RoleSquadro, BoardSquadro>(), RoleSquadro.VERTICAL, HeuristicSquadro.hZero);
        } else {
            p1 = new PlayerSquadro("joueur 1", new AlwaysFirst<MoveSquadro, RoleSquadro, BoardSquadro>(), RoleSquadro.VERTICAL, HeuristicSquadro.hZero);
            p2 = new PlayerSquadro("joueur 2", new AlwaysFirst<MoveSquadro, RoleSquadro, BoardSquadro>(), RoleSquadro.HORIZONTAL, HeuristicSquadro.hZero);
        }
        BoardSquadro game = new BoardSquadro();
       
            while (!game.isGameOver()) {
           
            MoveSquadro move1 = p1.chooseMove(game);
            game = game.play(move1, p1.getRole());
   
            if (game.isGameOver()) {
                break;
            }
            
            MoveSquadro move2 = p2.chooseMove(game);
            game = game.play(move2, p2.getRole());
        
            System.out.println(game.Board);
            System.out.println("============================");
            }
           
            
            System.out.println(" ======= GAME OVER =======");

    }

}
