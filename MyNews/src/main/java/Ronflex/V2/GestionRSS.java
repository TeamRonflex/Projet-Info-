package Ronflex.V2;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class GestionRSS {

	static List<News> annuaire = new LinkedList<News>();

	@SuppressWarnings("rawtypes")
	public static void parser(URL url) {
		SyndFeedInput sfi = new SyndFeedInput();
		try {
			SyndFeed feed = sfi.build(new XmlReader(url));
			List entries = new ArrayList();
			entries = feed.getEntries();

			for (int i = 0; i < entries.size(); i++) {
				SyndEntry entry = (SyndEntry) entries.get(i);

				News news = new News();
				news.setTitre(entry.getTitle());
				news.setLien(entry.getLink());
				news.setDate(entry.getPublishedDate().toString());
				String MEF = entry.getDescription().getValue();
				// Enleve les balises HTML
				MEF = MEF.replaceAll("<[^>]*>", " ");
				// Corrige les erreurs HTML pour l'apostrophe
				MEF = MEF.replaceAll("(&#8217;)|(&#39;)", "'");
				// Pour les double quote
				MEF = MEF.replaceAll("&quot;", "\"");
				// Pour les espaces
				MEF = MEF.replaceAll("&nbsp;", " ");
				MEF = MEF.replaceAll("(&#171;)|(&#160;)|(&#187;)", "");
				MEF = MEF.replaceAll("&#215;", "x");
				MEF = MEF.replaceAll("&#224;", "à");
				MEF = MEF.replaceAll("&#232;", "è");
				MEF = MEF.replaceAll("&#233;", "è");
				MEF = MEF.replaceAll("(&raquo;)|(&laquo;)|(      )", "");
				MEF = MEF.replaceAll("     ", "§§");
				MEF = MEF.replaceFirst("(.*)[^§§]§§", "");
				MEF = MEF.replaceFirst("   ", "##");
				MEF = MEF.replaceAll("##(.*)", "");
				MEF = MEF.replaceAll("[\\s\t]+$", "");
				news.setDescription(MEF);
				annuaire.add(news);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static int[] AjoutNews(GestionBDD connection) {
		int ajout = 0;
		int total = 0;
		int result [] = new int [2];
		for (News p : annuaire) {
			if (connection.ajoutNews(p))
				ajout ++;
			total ++;
		}
		result[0] = total;
		result[1] = ajout;
		return result;
	}
	
	public static void AfficherNews() {
		for (News p : annuaire) {
			System.out.println(p);
		}
	}
	
	public static void Vider() {
			annuaire.clear();
	}
}
