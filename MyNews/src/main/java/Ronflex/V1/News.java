package Ronflex.V1;


public class News {

	private String titre, lien, description, date;


	public News(String titre, String lien, String description, String date) {
		super();
		this.titre = titre;
		this.lien = lien;
		this.description = description;
		this.date = date;
	}
	
	public News() {}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getLien() {
		return lien;
	}

	public void setLien(String lien) {
		this.lien = lien;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString(){
		return new StringBuffer("Titre : ").append(titre).append("\n     Description : ").append(description).append("\n         Lien : ").append(lien).append("\n").toString();
	}
}
