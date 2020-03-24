package iia.games.base;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IPartie2 {
	
	/** initialise un plateau `a partir d’un fichier texte * @param fileName le nom du fichier `a lire
	 * @throws FileNotFoundException 
	 * @throws IOException 
	*/
	public void setFromFile(String fileName) throws FileNotFoundException, IOException;
	
	/** sauve la configuration de l’ ́etat courant (plateau et pi`eces restantes) * dans un fichier
	* @param fileName le nom du fichier `a sauvegarder
	* Le format doit ^etre compatible avec celui utilis ́e pour la lecture.
	 * @throws IOException 
	  */
	public void saveToFile(String fileName) throws IOException;
	
	/** indique si le coup <move> est valide pour le joueur <player>
	* sur le plateau courant
	* @param move le coup `a jouer, sous la forme "A4-C4"
	* @param player le joueur qui joue, repr ́esent ́e par "vertical" ou "horizontal" */
	public boolean isValidMove(String move, String player);
	
	/** calcule les coups possibles pour le joueur <player> sur le plateau courant * @param player le joueur qui joue, repr ́esent ́e par "vertical" ou "horizontal" */
	public String[] possibleMoves(String player);
	
	/** modifie le plateau en jouant le coup move avec la pi`ece choose
	* @param move le coup `a jouer, sous la forme "A4-C4"
	* @param player le joueur qui joue, repr ́esent ́e par "vertical" ou "horizontal" */
	public void play(String move, String role);
	
	/** vrai lorsque le plateau correspond `a une fin de partie */
	public boolean gameOver();
}
