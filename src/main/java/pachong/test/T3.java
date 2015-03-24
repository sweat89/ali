package pachong.test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class T3 {
public static void main(String[] args) {
	try {
		//Document doc = Jsoup.connect("http://dotamax.com/player/detail/136384759/").get();
		File file= new File("D:\\dotamax.com_player_detail_136384759_.html");
		Document doc = Jsoup.parse(file, "utf-8", "http://dotamax.com");
		Elements masthead = doc.select("font.color"); 
		System.out.println("===");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
