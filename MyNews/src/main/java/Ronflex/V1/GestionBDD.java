package Ronflex.V1;

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

// On fait la connexion à la BDD, et l'ajout des éléments.
public class GestionBDD {

	private static Connection conn;
	private static PreparedStatement PSajoutNews;
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
				.getConnection("jdbc:mysql://localhost/mynews?user=root&password=admin");

		PreparedStatement CreationTableNews = conn
				.prepareStatement("CREATE TABLE if not exists News(idNews INT NOT NULL AUTO_INCREMENT, Titre VARCHAR(255) NOT NULL,  `Description` TEXT,  `Lien` TEXT NOT NULL, Publication TEXT,  PRIMARY KEY (`idNews`),  UNIQUE INDEX `idNews_UNIQUE` (`idNews` ASC),  UNIQUE INDEX `Titre_UNIQUE` (`Titre` ASC))");
		CreationTableNews.executeUpdate();

		PSajoutNews = conn.prepareStatement("INSERT INTO News "
				+ "(titre, description, lien, Publication) VALUES (?,?,?,?)");
	}

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
				
				System.out.println("Titre : " + s + "\nDescrition : " + d
						+ "\nLien : " + l + "\n");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(ps);
		}
	}
		public void recuperationNewssite(String site) throws SQLException {
			
			Statement Affichage = conn.createStatement();
			String myQuery = "SELECT DISTINCT * FROM news WHERE lien  like '%" + site +"%'";
			ResultSet Res = Affichage.executeQuery(myQuery);
			while (Res.next()) {				
				String s = Res.getString("titre");
				String l = Res.getString("lien");
				String d = Res.getString("description");
				
				System.out.println("Titre : " + s + "\nDescrition : " + d
						+ "\nLien : " + l + "\n");
			}
		
		
	}

}
