package iia.games.squadro;

import iia.games.base.ABoard;
import iia.games.base.IPartie2;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BoardSquadro extends ABoard<MoveSquadro, RoleSquadro, BoardSquadro> implements IPartie2 {
	ArrayList<Point> Board;
	static ArrayList<Point> vitesse = new ArrayList<Point>();
	RoleSquadro joueur = RoleSquadro.HORIZONTAL;

	/*
	 * INTERFACE
	 */

	/**
	 * Constructeur de la classe board, initialise notre plateau de jeu ainsi que
	 * notre table contenant les vitesses
	 */
	public BoardSquadro() {
		Board = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Board.add(new Point(0, 0));// on ajoute les positions de depart de tout les pions
			if (i == 0 || i == 4 || i == 6 || i == 8)// les pions qui ont une vitesse de 1 puis 3
				vitesse.add(new Point(1, 3));
			if (i == 1 || i == 3 || i == 5 || i == 9)// les pions qui ont une vitesse de 3 puis 1
				vitesse.add(new Point(3, 1));
			if (i == 2 || i == 7)// les pions qui ont une vitesse de 2 puis 2
				vitesse.add(new Point(2, 2));
		}
		
	}

	/** Methode cr�ant un board a partir d'un ArrayList de points */
	public BoardSquadro(ArrayList<Point> b) {
		Board = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Board.add(b.get(i));
		}
	}

	/** Methode renvoyant une copie du plateau actuel */
	public BoardSquadro copy() {
		return new BoardSquadro(this.Board);
	}

	/**
	 * Methode renvoyant un ArrayList contenant les pieces adverse se trouvant a la
	 * suite en fonction de qui a jou� et quelle piece a ete bougee
	 */
	public ArrayList<Integer> getAdv(MoveSquadro move, RoleSquadro role, int coord) {// nb adv consecutifs
		ArrayList<Integer> adv = new ArrayList<Integer>();
		int piece = move.getPiece();// recupere la piece bougee
		boolean consec = true;// si les pions sont consec
		int xy = coord;// la coordonnee ou se trouve notre piece en ce moment
		int AR = Board.get(piece).y;// Aller ou Retour

		switch (role) {

		case HORIZONTAL:// dans le cas ou c'est le joueur Horizontal
			switch (AR) {

			case 0:// cas ou on est sur un aller
				for (int i = 4 + xy; i < 10; i++) {// on parcours toutes les pieces adverses
					if (Board.get(i).x == 5 - piece && consec) {// on regarde si la coordonnee des pieces est bien sur
																// la meme ligne que se deplace le pion
						adv.add(i);// on ajoute cette piece a notre tableau
					} else {
						consec = false;// si ils ne sont pas a la suite on ne cherche plus a aller plus loin
					}

				}
				break;

			case 1:// cas ou on est sur un retour
				for (int i = 4 + xy; i > 4; i--) {// on parcours les pieces adverses (dans "l'autre sens")
					if (Board.get(i).x == 5 - piece && consec) {// si jamais ils se situent sur la meme ligne que la
																// piece jouee
						adv.add(i);// on ajoute cette piece a notre tableau
					} else {
						consec = false;// sinon elle n'est pas a la suite et il ne sert a rien d'aller plus loin
					}

				}
				break;

			}
			break;

		case VERTICAL:// dans le cas ou c'est le joueur vertical
			switch (AR) {

			case 0:// si on est sur un aller
				for (int i = 5 - xy; i >= 0; i--) {// on parcours les pieces adverses
					if (Board.get(i).x == piece - 4 && consec) {// si jamais elles se trouvent sur la meme ligne que la
																// piece jouee
						adv.add(i);// on la rajoute au tableau
					} else {
						consec = false;// sinon elle ne sont pas a la suite, et donc cela ne sert a rien de continuer
					}
				}
				break;

			case 1:// si on est sur un retour
				for (int i = 5 - xy; i < 5; i++) {// on parcours les pieces adverses
					if (Board.get(i).x == piece - 4 && consec) {// si jamais elles se trouvent sur la meme ligne que la
																// piece jouee
						adv.add(i);// on la rajoute au tableau
					} else {
						consec = false;// sinon elle ne sont pas a la suite, et donc cela ne sert a rien de continuer
					}
				}
				break;
			}

			break;
		}
		return adv;
	}

	/**
	 * Methode permettant de jouer un coup, renvoyant le nouveau board apres l'avoir
	 * joue
	 */
	@Override
	public BoardSquadro play(MoveSquadro move, RoleSquadro role) {
		BoardSquadro BoardInt = this.copy();// on travaille sur une copie
		int piece = move.getPiece();// on recupere la piece a bouger
		System.out.println("PIECE CHOISIE = " + piece);

		int AR = Board.get(piece).y;// determine si on est sur un aller ou un retour (0 aller, 1 retour)
		int speed;// la vitesse == le nombre de pas a faire
		if (AR == 0) {// en fonction de la direction
			speed = vitesse.get(piece).x;// on determine la vitesse de la piece
		} else {
			speed = vitesse.get(piece).y;
		}

		ArrayList<Integer> ListPieceAdv = new ArrayList<Integer>();// ArrayList qui va contenir les adversaires se
																	// trouvant a la suite a une coordonnee
		int nbAdv;// le nombre d'adversaire

		if (AR == 0) {// pour l'aller
			for (int i = 0; i < speed; i++) {// on fait le nombre de pas necessaire (== speed)

				int x = BoardInt.Board.get(piece).x;// on recupere la coordonnee sur laquelle on se situe
				ListPieceAdv = getAdv(move, role, x + 1);// on regarde les adversaire qui sont a la suite lorsque l'on a
															// fait un pas
				nbAdv = ListPieceAdv.size();// on a le nombre d'adversaires a la suite

				if (nbAdv == 0) {// si aucun adversaire
					if (x + 1 >= 6)// on regarde juste si on ne va pas trop loin
						BoardInt.Board.set(piece, new Point(6, 1));// si oui, on reste a 6 et on change le sens en
																	// mettant Y a 1
					else
						BoardInt.Board.set(piece, new Point(x + 1, 0));// si non, on garde Y a 0 et on fait juste un pas

				} else {// si il y a des adversaires
					if (x + nbAdv >= 6)// on regarde que le nombre de cases sautees ne nous fait pas sortir du tableau
						BoardInt.Board.set(piece, new Point(6, 1));// si oui, on reste a 6 et on change le sens en
																	// mettant Y a 1
					else
						BoardInt.Board.set(piece, new Point(x + nbAdv, 0));// si non, on garde Y a 0 et on fait saute
																			// juste tout les adversaires
					for (int adv : ListPieceAdv) {// on parcours toutes les pieces adversaires qui ont ete sautee
						if (BoardInt.Board.get(adv).y == 1)// si la piece etait sur le chemin du retour, on la remet a 6
							BoardInt.Board.set(adv, new Point(6, 1));
						else// si la piece etait sur le chemin de l'aller, on la remet a 0
							BoardInt.Board.set(adv, new Point(0, 0));
					}

					break;// si on a saut� des adversaires, on n'avance plus
				}

			}

		} else {// pour le retour
			for (int i = 0; i < speed; i++) {// on fait le nombre de pas necessaires

				int x = BoardInt.Board.get(piece).x;// la position ou l'on se situe en ce moment
				ListPieceAdv = getAdv(move, role, x - 1);// recupere les adversaires se trouvant a la suite
				nbAdv = ListPieceAdv.size();// et leur nombre
				if (nbAdv == 0) {// si aucun adversaire a sauter
					if (x - 1 <= 0)// on verifie que l'on ne sort pas du tableau
						BoardInt.Board.set(piece, new Point(0, 1));// si oui, on se met bien a la bonne case
					else
						BoardInt.Board.set(piece, new Point(x - 1, 1));// si non, on fait juste un pas
				} else {// si il y a des adversaires a sauter
					if (x - nbAdv <= 0)// on regarde si le nombre d'adversaires sautes ne nous sort pas du tableau
						BoardInt.Board.set(piece, new Point(0, 1));// si c'est le cas, on se met bien a 0
					else
						BoardInt.Board.set(piece, new Point(x - nbAdv, 1));// si non, on saute le nombre d'adversaire
					for (int adv : ListPieceAdv) {// on parcours les adversaires sautes
						if (BoardInt.Board.get(adv).y == 1)// si ils etaient sur le chemin du retour
							BoardInt.Board.set(adv, new Point(6, 1));// on les remets a 6
						else
							BoardInt.Board.set(adv, new Point(0, 0));// sinon on les remet a 0
					}

					break;// si on a saute des adversaires, on n'a plus le droit de bouger

				}

			}

		}

		return BoardInt;
	}

	/**
	 * Methode renvoyant un ArrayList de MoveSquadro contenant tout les moves
	 * possibles pour un joueur (vertical ou horizontal)
	 */
	@Override
	public ArrayList<MoveSquadro> possibleMoves(RoleSquadro role) {
		ArrayList<MoveSquadro> moves = new ArrayList<MoveSquadro>();

		switch (role) {

		case HORIZONTAL:// si c'est le joueur horizontal
			for (int i = 0; i < 5; i++) {// on parcours toutes ses pieces

				if (!(Board.get(i).x == 0 && Board.get(i).y == 1)) {// si ce n'est pas une piece qui a fait un aller
																// retour
					moves.add(new MoveSquadro(i));// on l'ajoute dans les pieces pouvant etre bougees
					
				}
			}
			System.out.println("ON A PRIS UN MOVE DES HORIZONTALS");
			break;// on s'arrette

		case VERTICAL:// si c'est le joueur vertical
			for (int i = 5; i < 10; i++) {// on parcours toutes les pieces du joueur vertical

				if (!(Board.get(i).x == 0 && Board.get(i).y == 1)) {// si ce n'est pas une piece qui a fait un aller
																	// retour
					moves.add(new MoveSquadro(i));// on l'ajoute dans les pieces pouvant etre bougees
					
				}

			}
			System.out.println("ON A PRIS UN MOVE DES VERTICALS");
			break;// on s'arrette
		}
		//System.out.println(moves.size());
		return moves;// on renvoie l'ArrayList contenant toutes les pieces pouvant etre bougees
	}

	/** Procedure verifiant si la partie est finie */
	@Override
	public boolean isGameOver() {
		int j1 = 0;// nb pieces ayant fait un aller retour pour le joueur 1
		int j2 = 0;// nb pieces ayant fait un aller retour pour le joueur 2

		for (int i = 0; i < 10; i++) {// on parcours tout le tableau
			if (j1 >= 4 || j2 >= 4) {// si l'un des deux joueurs a 4 pions qui ont fait un aller retour
				return true;// la partie est finie
			}
			// si dans les 5 premiers points du tableau, il y a												// des pieces ayant fait un aller retour
						
			if (i < 5 && Board.get(i).x == 0 && Board.get(i).y == 1) {
				j1++;// le joueur 1 "gagne" un point
			}
			// si dans les 5 points suivant du tableau, il y a
			// des pieces ayant fait un aller retour
			if (i >= 5 && Board.get(i).x == 0 && Board.get(i).y == 1) {
				j2++;// le joueur 2 "gagne" un point
			}
		}				
		//System.out.println("J1 : " + j1 + " | J2 : " + j2);
		return false;// si aucun des deux joueurs n'a 4 pieces ayant fait un aller retour, la partie
						// n'est pas finie
	}

	/** Methode verifiant si un coup est valide */
	@Override
	public boolean isValidMove(MoveSquadro move, RoleSquadro role) {
		ArrayList<MoveSquadro> moves = possibleMoves(role);// on recupere les moves possibles
		System.out.println("YOUHOUUUUU");
		if (moves.contains(move)) {// si jamais l'ArrayList contient ce move,...
			
			return true;// ... il s'agit d'un coup valide
		}

		return false;// sinon c'est un coup invalide, TRICHE!
	}


	/**Methode permettant de definir un joueur**/
	public void setJoueur(RoleSquadro role) {
		joueur = role;
	}
	
	/**Methode permettant de récupere le joueur actuel**/
	public RoleSquadro getJoueur() {
		return joueur;
	}
	
	
	@Override
	public void setFromFile(String fileName) throws IOException {
		for (int i = 0; i < 10; i++) {
			Board.add(new Point(0, 0));// on ajoute les positions de depart de tout les pions
		}
		// the file to be opened for reading
		String currL;
		
		//initialise le buffer pour lecture du fichier
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        
        //tant qu'on lit des lignes
        while ((currL = br.readLine()) != null) {
        	if(currL.charAt(0) == '%') {
        		//On ne fait rien : ligne commentaire
        	}
        	
        	//On verifie si nos lignes contiennent les caracteres voulus
        	if(currL.contains("horizontal")) { //Dernier coup horizontal
        		joueur = RoleSquadro.VERTICAL; //Celui qui joue est donc vertical
        	} 
        	else if(currL.contains("vertical")) { //L'inverse
        		joueur = RoleSquadro.HORIZONTAL;	
        	} 
        	
        	// On verifie où sont nos pions 3 et 10 correspondent aux charIndex
        	// du debut du tableau de jeu dans le fichier .txt
        	for(int i = 3; i < 10; i++) {
        		int y = Integer.parseInt(String.valueOf(currL.charAt(2)));
        		//retour horizontal
        		if (currL.charAt(i) == '<') {
        			Board.add(y-2, new Point(i-3,1));
        		}
        		//aller horizontal
				if (currL.charAt(i) == '>') {
					Board.add(y - 2, new Point(i - 3, 0));
				}
				// aller vertical }
				if (currL.charAt(i) == '^') {
					Board.add(i + 1, new Point(y - 1, 0));
				}
				// retour horizontal
				if (currL.charAt(i) == 'v') {
					Board.add(i + 1, new Point(y - 1, 1));
				}
			}
		}
		// On ferme notre buffer
		br.close();
	}

	@Override
	public void saveToFile(String fileName) throws IOException {
		//On cree notre fichier 

		File myFile = new File(fileName);
		// on déclare les variables Point horizontaux et verticaux pour
		// y voir plus clair par la suite
		Point hor, vert;

		
		//On verifie que le fichier a bien ete ouvert correctement
	    if (myFile.createNewFile()) {
	    	System.out.println("File created: " + myFile.getName());
	    } else {
	        System.out.println("File already exists.");
	    }
	    
	    //Initialisation du filewriter
	    FileWriter myWriter = new FileWriter(fileName);
	    
	    //Boucle for pour le nombre de lignes à écrire
	    for(int i = 0; i < 11; i++) {
	    	//Premiere ligne
	    	if(i == 0) {
	    		myWriter.write("% Sauvegarde\n");
	    	} 
	    	//Deuxieme et avant derniere
	    	else if(i == 1 || i == 9) {
	    		myWriter.write("% ABCDEFG\n");
	    	}
	    	//Derniere ligne
	    	else if(i == 10) {
	    		myWriter.write(joueur.toString()+"\n");
	    	}
	    	//Les autres correspondant au plateau de jeu ( coord y )
	    	else if(i > 1 && i < 9) {
	    		//On rajoute le numéro de la ligne en début ... 
	    		myWriter.write("0"+(i-1)+" ");
	    	
	    		// ( coord x )
	    		for(int j = 0; j < 7; j++) {
	    			//Lorsque les coordonnées corresoondent a tel ou tel pion 
	    			//on les rajoute dans le fichier texte avec le symbole correspondant.
	    			for(int pt = 0; pt < 5; pt++) {
	    				hor = Board.get(pt);
	    				vert = Board.get(pt+5);
	    				if(hor.x == j) {
	    					//On vérifie le sens de déplacement
	    					if(hor.y == 0) myWriter.write(">");
	    					if(hor.y == 1) myWriter.write("<");
	    					break;
	    				}
	    				if(vert.x == i) {
	    					if(vert.y == 0) myWriter.write("^");
	    					if(vert.y == 1) myWriter.write("v");
	    					break;
	    				}
	    				//Sinon on écrit un point.
	    				else {
	    					myWriter.write(".");
	    					break;
	    				}
	    			}
	    		}
	    		//...Et fin du plateau.
	    		myWriter.write("0"+(i-1)+" \n");
	    	 } 
	      }
	    //On ferme le writer
	    myWriter.close();
	    System.out.println("Successfully wrote to the file.");

	}

	/** Methode verifiant si un coup est valide */
	@Override
	public boolean isValidMove(String move, String player) {
		String[] moves = possibleMoves(player);// on recupere les moves possibles
		for (int i = 0; i < moves.length; i++) {// on parcours les moves
			if (moves[i] == move)
				return true;// si le move figure dans le tableau, c'est un move valide
		}

		return false;// sinon c'est un coup invalide, TRICHE!
	}

	/**
	 * Methode renvoyant un ArrayList de MoveSquadro contenant tout les moves
	 * possibles pour un joueur (vertical ou horizontal)
	 */
	@Override
	public String[] possibleMoves(String player) {
		char charDeb, charFin;// les caracteres qu'on va utiliser dans le string
		String res1, res2, resfinal;// les strings intermediaires ainsi que le string final
		int intDeb, intFin;// les entiers du move de debut et du move de fin
		String[] moves = new String[5];// un tableau de string contenant tout les moves (au max 5)

		switch (player) {
		case "VERTICAL":// si c'est le joueur vertical qui joue
			for (int i = 5; i < 10; i++) {// on parcours les 5 dernieres cases de la liste
				if (!(Board.get(i).x == 0 && Board.get(i).y == 1)) {// si ce n'est pas une piece qui a fait un aller
																	// retour
					intDeb = 7 - Board.get(i).x;// on joue en vertical, les lettres ne changent pas, uniquement les chiffres
					if (Board.get(i).y == 0) {// on verifie que l'on est sur un aller
						intFin = intDeb + vitesse.get(i).x;
						if (intFin > 6)
							intFin = 6;// on verifie que l'on ne sort pas du tableau
					} else {// si on est sur un retour
						intFin = intDeb - vitesse.get(i).y;
						if (intFin < 0)
							intFin = 0;// on verifie que on ne sort pas du tableau
					}

					charDeb = intToChar(i % 5);// on met un modulo 5 pour avoir les bonnes lettres
					intDeb = intDeb + 1;// les int du debut prennent +1 car on commence a 1 et pas 0
					intFin = intFin + 1;// les int de fin prennent +1 car on commence a 1 et pas 0

					res1 = String.valueOf(charDeb + intDeb);// on concatene pour le move de depart
					res2 = String.valueOf(charDeb + intFin);// on concatene pour le move d'arrivee

					resfinal = combMove(res1, res2);// on concatene les 2 pour avoir le move sous la forme correcte
					moves[i % 5] = resfinal;// on l'ajoute au bon endroit dans la liste
				}
			}
			break;// on s'arrette qd on a fait tout les pieces

		case "HORIZONTAL":// si c'est le joueur horizontal
			int interm;
			for (int i = 0; i < 5; i++) {// on parcours les 5 premieres cases du tableau
				if (!(Board.get(i).x == 0 && Board.get(i).y == 1)) {// si ce n'est pas une piece qui a deja fait un
																	// aller retour
					intDeb = i;// le debut prends la valeur i, on est en horizontal, seul les caracteres
								// changes
					charDeb = intToChar(Board.get(i).x);// on transforme le caractere de debut
					if (Board.get(i).y == 0) {// si on est sur un aller
						interm = Board.get(i).x + vitesse.get(i).x;
						if (interm > 6)
							interm = 6;// on verifie que l'on ne sort pas du tableau
					} else {
						interm = Board.get(i).x - vitesse.get(i).x;
						if (interm < 0)
							interm = 0;// on verifie que l'on ne sort pas du tableau
					}
					charFin = intToChar(interm);// on transforme en caractere

					intDeb += 1;// la valeur du debut prends +1 car on commence a 1 et pas 0
					res1 = String.valueOf(charDeb + intDeb);// on creer le premier move
					res2 = String.valueOf(charFin + intDeb);// on creer le deuxieme move

					resfinal = combMove(res1, res2);// on transforme sous la bonne forme
					moves[i] = resfinal;// on l'ajoute au bon endroit
				}

			}
			break;
		}

		return null;
	}

	/** Methode renvoyant un caractere pour un entier donne */
	public char intToChar(int a) {
		char res = ' ';
		switch (a) {
		case 0:
			res = 'A';
			break;
		case 1:
			res = 'B';
			break;
		case 2:
			res = 'C';
			break;
		case 3:
			res = 'D';
			break;
		case 4:
			res = 'E';
			break;
		case 5:
			res = 'F';
			break;
		case 6:
			res = 'G';
			break;
		default:
			break;

		}
		return res;
	}

	/** Methode fusionant 2 strings afin d'avoir la bonne forme */
	public String combMove(String m1, String m2) {
		return m1 + "-" + m2;
	}

	/**
	 * Methode permettant de jouer un coup, renvoyant le nouveau board apres l'avoir
	 * joue
	 */
	public void play(String move, String role) {
		

		String[] moves = move.split("");
		int case1 = stringToInt(moves[0]);// on recupere l'equivalent en int des lettres
		
		int case2 = stringToInt(moves[3]);// on recupere l'equivalent en int des lettres
		
		int int1 = Integer.parseInt(moves[1]) - 1;// on recupere les entiers

		int int2 = Integer.parseInt(moves[4]) - 1;// on recupere les entiers
		

		boolean adv = false;// on initialise les adversaires a false
		switch (role) {
		case "HORIZONTAL":// si c'est le joueur horizontal qui joue
			if (Board.get(int1-1).y == 0) {//si on est en train de faire un aller
				if (adv) {//si il n'y a pas d'adversaire, on avance sans se soucier
					if (case2 == 6)//on verifie si on a fini notre trajet
						Board.set(int1-1, new Point(case2, 1));
					else
						Board.set(int1-1, new Point(case2, 0));
				} else {
					//TODO
				}
				
			} else {//si on est en train de faire un retour
				if (adv) {//si il n'y a pas d'adversaire
					if (case2 == 0)//on verifie si on a fini notre trajet
						Board.set(int1-1, new Point(case2, 1));
					else
						Board.set(int1-1, new Point(case2, 1));
				} else {
					
					//TODO
				}

			}
			break;
			
			
			
			
		case "VERTICAL"://si c'est le joueur vertical qui joue, uniquement les chiffres changent
			/*
			System.out.println("CASE1: " + int1);
			System.out.println("CASE2: " + int2);
			*/
			if (Board.get(case1 + 4).y == 0) {//si on est en train de faire un aller
				if (adv) {//si il n'y a pas d'adversaire, on avance sans se soucier
					if (int2 == 0)//on verifie si on a fini notre trajet
						Board.set(case1, new Point(6, 1));
					else
						Board.set(case1, new Point(6-int2, 0));
				} else {//si il y a des adversaires a sauter
					//TODO
				}
			} else {//si on est sur le retour
				if (adv) {//si il n'y a pas d'adversaire
					if (int2 == 6)//on verifie si on a fini notre trajet
						Board.set(case1, new Point(0, 1));
					else
						Board.set(case1, new Point(6-int2, 1));
				} else {//si il y a des adversaires a sauter
					
					//TODO
				}
			}
			break;
		}
		//DANS LES TODO, ON NE SAIT PAS ENCORE QUOI FAIRE CAR IL Y A UN DOUTE SUR COMMENT AGIR LORSQU'IL Y A UNE COLLISION ENTRE PIECES: EST CE ICI
		//OU DANS LA METHODE POSSIBLE MOVES QU'IL FAUT S'EN OCCUPER
		
	}

	/** Methode renvoyant un entier pour un string donne */
	public int stringToInt(String a) {
		int res = 0;
		switch (a) {
		case "A":
			res = 0;
			break;
		case "B":
			res = 1;
			break;
		case "C":
			res = 2;
			break;
		case "D":
			res = 3;
			break;
		case "E":
			res = 4;
			break;
		case "F":
			res = 5;
			break;
		case "G":
			res = 6;
			break;

		}
		return res;
	}

	@Override
	public boolean gameOver() {
		//TODO, je ne comprends pas vraiment la difference avec l'autre game over???
		return false;
	}

}
