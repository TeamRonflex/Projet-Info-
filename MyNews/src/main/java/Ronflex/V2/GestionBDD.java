package Ronflex.V2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// On fait la connexion à la BDD, et l'ajout des éléments.
public class GestionBDD {

	private static Connection conn;
	private static PreparedStatement PSajoutNews;
	private static PreparedStatement PSajoututilisateurs;
	private static FileOutputStream fos;
	private static PrintStream ps;

	public GestionBDD() throws ClassNotFoundException, SQLException,
			FileNotFoundException {
		// Déjà on charge le fichier de log
		fos = new FileOutputStream(new File("log.txt"));
		ps = new PrintStream(fos);

		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		conn = DriverManager
				.getConnection("jdbc:mysql://localhost/mynews?user=root&password=123456");

		PreparedStatement CreationTableNews = conn
				.prepareStatement("CREATE TABLE if not exists News(idNews INT NOT NULL AUTO_INCREMENT, Titre VARCHAR(255) NOT NULL,  Description TEXT,  Lien TEXT NOT NULL, Publication TEXT,  PRIMARY KEY (`idNews`),  UNIQUE INDEX `idNews_UNIQUE` (`idNews` ASC),  UNIQUE INDEX `Titre_UNIQUE` (`Titre` ASC))");
		CreationTableNews.executeUpdate();

		PreparedStatement CreationTableUtilisateurs = conn
				.prepareStatement("CREATE TABLE if not exists Utilisateurs( idUtilisateurs INT NOT NULL AUTO_INCREMENT PRIMARY KEY, Pseudo VARCHAR(255) NOT NULL, mdp TEXT NOT NULL, UNIQUE INDEX `pseudo_UNIQUE` (`pseudo` ASC), UNIQUE INDEX `idUtilisateurs_UNIQUE` (`idUtilisateurs` ASC))");
		CreationTableUtilisateurs.executeUpdate();

		PSajoutNews = conn
				.prepareStatement("INSERT INTO News (Titre, Description, Lien, Publication) VALUES (?,?,?,?)");

		PSajoututilisateurs = conn
				.prepareStatement("INSERT INTO Utilisateurs (Pseudo, Mdp)	VALUES(?,?)");
	}

	// ------------------------------------------------
	// ---------------		NEWS	-------------------
	// ------------------------------------------------

	public boolean ajoutNews(News n) {
		try {
			PSajoutNews.setString(1, n.getTitre());
			PSajoutNews.setString(2, n.getDescription());
			PSajoutNews.setString(3, n.getLien());
			PSajoutNews.setString(4, n.getDate());
			PSajoutNews.executeUpdate();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(ps);
			return false;
		}
	}

	public void recuperationNews() {
		try {
			Statement Affichage = conn.createStatement();
			String myQuery = "SELECT DISTINCT * FROM news";
			ResultSet Res = Affichage.executeQuery(myQuery);
			while (Res.next()) {
				String s = Res.getString("titre");
				String l = Res.getString("lien");
				String d = Res.getString("description");
				System.out.println("Titre : " + s + "\n	Descrition : " + d
						+ "\n 		Lien : " + l + "\n");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(ps);
		}
	}

	public void recuperationNewssite(String site) throws SQLException {

		Statement Affichage = conn.createStatement();
		String myQuery = "SELECT DISTINCT * FROM news WHERE lien  like '%"
				+ site + "%'";
		ResultSet Res = Affichage.executeQuery(myQuery);
		while (Res.next()) {
			String s = Res.getString("titre");
			String l = Res.getString("lien");
			String d = Res.getString("description");
			System.out.println("Titre : " + s + "\n	Descrition : " + d
					+ "\n 		Lien : " + l + "\n");
		}

	}

	
	// ------------------------------------------------
	// ---------------		USER	-------------------
	// ------------------------------------------------
	
	// ID
	public boolean identification(String pse, String mdp) throws SQLException {
		Statement Affichage = conn.createStatement();
		String myQuery = "SELECT pseudo, mdp FROM utilisateurs";
		ResultSet Res = Affichage.executeQuery(myQuery);
		boolean pasTrouve = true;
		while (Res.next() && pasTrouve) {
			if (pse.equals(Res.getString("pseudo"))
					&& mdp.equals(Res.getString("mdp")))
				pasTrouve = false;
		}
		return !pasTrouve;
	}
	
	// Inscription ( A toi de jouer)
	public boolean Inscription() throws SQLException 
	{   Scanner Entree = new Scanner(System.in);
		String pse;
		String mdp;
		System.out.println("create votre pseudo, il doit etre au moin 6 caractères alphanumériques");
		pse=Entree.nextLine();
		while(pse.length()<6)                     //garantir le pse a plus que 5 characters
		{
			System.out.println("pse doit etre au mois 6etrencaractères alphanumériques");
			pse=Entree.nextLine();
		}
	    while(!pse.matches("^[a-zA-Z0-9]+$"))     //garantir le pse est alphanumerique
		{
				System.out.println("pse doit etre caractères alphanumériques");
				pse=Entree.nextLine();
		}
		System.out.println("create votre passmod, il doit etre au moin 6 caractères alphanumériques");
		mdp=Entree.nextLine();
		while(!mdp.matches("^[a-zA-Z0-9]+$"))
		{
			System.out.println("mdp doit etre caractères alphanumériques");
			mdp=Entree.nextLine();
		}
	    while(mdp.length()<6)
		    {
		    	System.out.println("mdp doit etre au mois 6etrencaractères alphanumériques");
		    	mdp=Entree.nextLine();
		    }
		//mdp=Entree.nextLine();
		//mdp=Entree.nextLine();
		System.out.println("Entrer le password encore une fois : ");      // garantir les utilisateurs peut se souvenir le mode de pass.
		String mdpencore=Entree.nextLine();
		while(!mdp.matches(mdpencore)){
			System.out.println("les deux passmodes que vous avez saisi est defferent,mettre en place votre passmod encore une fois");
			System.out.println("Entrer le password : ");
			mdp = Entree.nextLine();
			System.out.println("Entrer le password encore une fois: ");
		    mdpencore=Entree.nextLine();
		}
		
		
		try{
		PSajoututilisateurs.setString(1, pse);
		PSajoututilisateurs.setString(2, mdp);
		PSajoututilisateurs.executeUpdate();
	    return true;
		}
	    catch (SQLException e) {
	    System.out.println("cet pseudo est deja utilise vous devez changer votre pseudo");
		// TODO Auto-generated catch block
		e.printStackTrace(ps);// verify si cet pseudo a deja utilise.
		return false;
	   }
	}
   public void obtenirmdp(String pse) throws SQLException
   {
	   Statement Affichage = conn.createStatement();
	   String myQuery="select * from utilisateurs where pseudo='"+pse+"'";
	   ResultSet rs=Affichage.executeQuery(myQuery);
	   if(rs.next()){
	  String mdp=rs.getString("mdp");
	   System.out.println(mdp);
	   }
	   //return mdp;
	   
   }
}
