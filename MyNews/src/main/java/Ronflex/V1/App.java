package Ronflex.V1;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
	public static void main(String[] args) throws ClassNotFoundException,FileNotFoundException, SQLException, MalformedURLException {
		
		String choix = "0";
		Scanner Entree = new Scanner(System.in);
		
		GestionBDD connection = new GestionBDD();
		
		List<URL> AboRSS = new LinkedList<URL>();
		AboRSS.add(new URL("https://news.google.fr/news/feeds?pz=1&cf=all&ned=fr&hl=fr&topic=t&output=rss"));
		//AboRSS.add(new URL("http://feeds2.feedburner.com/LeJournalduGeek"));
		AboRSS.add(new URL("http://flux.20minutes.fr/c/32497/f/479493/index.rss?xts=290428&xtor=RSS-1"));
		AboRSS.add(new URL("http://rss.lemonde.fr/c/205/f/3050/index.rss"));
		AboRSS.add(new URL("http://rss.lefigaro.fr/lefigaro/laune"));
		AboRSS.add(new URL("http://rss.allocine.fr/ac/bandesannonces/alaffiche"));
		
		
		while ((choix != "q")) {
			AffichageMenu();

			choix = Entree.next();
			

			switch (choix) {
			case "1": {
				choix = Entree.nextLine();
				
				connection.recuperationNews();
				System.out.println("'Entree' pour revenir au menu");
				choix = Entree.nextLine();

				break;
			}

			case "2": {
				System.out.println("Sites disponibles : ");
				System.out.println("	1. Google Actu");
				System.out.println("	2. 20 Minutes");
				System.out.println("	3. Le monde");
				System.out.println("	4. Le figaro");
				System.out.println("	5. Allocine");
				System.out.println("Choix :");
				String site = Entree.next();
				choix = Entree.nextLine();
					switch (site) {
						
					case "1": {
						connection.recuperationNewssite("news.google");
						break;
						}
					case "2": {
						connection.recuperationNewssite("flux.20minutes");
					break;
					}
					case "3": {
						connection.recuperationNewssite("lemonde.fr");
					break;
					}
					case "4":{
						connection.recuperationNewssite("lefigaro.fr");
					break;
					}
					case "5": {
						connection.recuperationNewssite("allocine");
					break;
					}
					default : {break;}
					}
					
					
					System.out.println("'Entree' pour revenir au menu");
					choix = Entree.nextLine();
					
				break;
			}
			case "3": {				
				System.out.println("Debut de la recuperation, patientez s'il vous plait.");
				for(URL u :AboRSS)
				{
					GestionRSS.parser(u);
				}
				int ajout[] = new int[2];
				System.out.println("Rss recuperes, debut du stockage sur la BDD");
				ajout = GestionRSS.AjoutNews(connection);
				System.out.println("Ajoute : " + ajout[1]
						+ "  Sur un total de : " + ajout[0]);
				GestionRSS.Vider();
				break;
			}
			
			case "Q": {
				choix = "q";
				break;
			}

			case "q": {
				choix = "q";
				Entree.close();

				break;
			}
			default: {
				break;
			}

			}

		}
		System.out.println("\n------------------------------");
		Scanner in = new Scanner(System.in);
		System.out.println("Au revoir !");

	}

	public static void AffichageMenu() {

		System.out.println("------------------------------\n");
		System.out
				.println("Bienvenue sur MyNews, que souhaitez-vous faire ? \n");
		System.out.println("	1. Lire toutes les news disponibles.");
		System.out.println("	2. Lire les news d'un site.");
		System.out.println("	3. Mettre a jour la liste des news.");
		System.out.println("	Q. Quitter.\n");
		System.out.println("Choix : ");
	}
}

