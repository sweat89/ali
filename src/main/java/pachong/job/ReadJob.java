package pachong.job;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

import pachong.dao.PachongDao;
import pachong.vo.Dota;

public class ReadJob {
	@Autowired
	private PachongDao pachongDao;
	
	public void doJob() throws IOException {
		List<String> userList = new ArrayList<String>();
		userList.add("136384759");  //lijian
		userList.add("136496469");  //niao
		userList.add("220314893");
		for(String userid:userList){
	    
		File file = new File("D:\\dotamax.com_player_detail_"+userid+".html");
		Document doc = Jsoup.parse(file, "utf-8", "http://dotamax.com");
		Element el1 = doc.select("div.flat-grey-box").get(1);
		Element elLev = el1.select("td").get(5);
		Element elScore = el1.select("td").get(4);
		Element el2 = el1.select("tbody.table-player-detail").first();
		Element elName = el2.select("td").first();
		Element elNumber = el2.select("td").get(1);
		Element elDetail = elName.select("a[href]").first();
		Element elPic = elDetail.select("img").first();
		Dota dota = new Dota();
		dota.setPic(elPic.attr("src"));
		dota.setHero(elName.text());
		dota.setNumber(elNumber.text());
		dota.setLevel(elLev.text());
		dota.setScore(elScore.text());
		pachongDao.save(dota);
		}
	}
}
