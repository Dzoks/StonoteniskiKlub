package application.util;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Downloader {

	private static Path downloadFile(String sourceURL, String targetDirectory) throws IOException
	{
	    URL url = new URL(sourceURL);
	    String fileName = sourceURL.substring(sourceURL.lastIndexOf('/') + 1, sourceURL.length());
	    Path targetPath = new File(targetDirectory + File.separator + fileName).toPath();
	    Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
	    return targetPath;
	}
	
	public static File preuzmiListu(String urlListe,String kategorija) throws IOException {
		Document htmlDocument=Jsoup.connect(urlListe).get();
		Elements links=htmlDocument.getElementsByTag("a");
		String downloadLink=null;
		System.out.println("HTML inspect");
		for (Element link:links) {
			String href=link.attr("href");
			if (href.endsWith(".xls")) {
				downloadLink=href;
				break;
			}
		}
		Path path=downloadFile("http://www.stsbih.com.ba"+downloadLink,"rangListe");
		Files.move(path, path.resolveSibling(kategorija+".xls"),StandardCopyOption.REPLACE_EXISTING);
		return path.resolveSibling(kategorija+".xls").toFile();
 	}

}
