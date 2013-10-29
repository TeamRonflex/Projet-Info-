package Ronflex.V2;

import java.sql.SQLException;

public class User {
	String pseudo;
	public User() {	
	}
	
	public boolean valideUser (String pse, String mdp, GestionBDD con) throws SQLException {
		if (con.identification(pse, mdp))
		{
			System.out.println("Indentifie sous le pseudo : "+ pse);
			pseudo = pse;
			return true;
		}
		else 
		{
			System.out.println("Le couple Utilisateur/Mot de passe ne fonctionne pas. \n");
			return false;
		}
	}
}
