package crawella.web;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawella.file.FileHandler;

public class Crawler {
	private static Set<String> foundLinks = new HashSet<String>();

	public static final String LINKS_FILE = "links.txt";

	public void crawl(String url, int depth) {
		FileHandler fh = new FileHandler();
		
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select("a[href]");

			URL connectableUrl = new URL(url);
			connectableUrl.toURI();

			String splitUrl[] = url.split("/");

			String protocalAndDomains = splitUrl[0] + "//" + splitUrl[2] + "/";

			Iterator<Element> it = elements.iterator();
			while (it.hasNext()) {
				Element el = it.next();
				String link = el.attr("href");
				try {
					URL validationUrl = new URL(link);
					validationUrl.toURI();
				} catch (URISyntaxException | MalformedURLException e) {
					link = protocalAndDomains + link;
					e.printStackTrace();
				}
				if (!foundLinks.contains(link)) {
					fh.append(LINKS_FILE, link + "\n");
					foundLinks.add(link);
					if (depth > 0) {
						crawl(link, depth - 1);
					}
				}
			}
			System.out.println(doc.title());
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
