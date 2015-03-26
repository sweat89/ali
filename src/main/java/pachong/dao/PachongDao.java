package pachong.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pachong.vo.Dota;

@Repository
public class PachongDao {
	@Autowired
	private JdbcTemplate jdbcPachong;
	
	
	
	
	public void save(Dota dota) {
		String sql = "insert into dota2(userid,pic,hero,number,level,score) values(?,?,?,?,?,?)";
		Object[] data = new Object[] { dota.getUserid(), dota.getPic(),
				dota.getHero(), dota.getNumber(), dota.getLevel(),
				dota.getScore() };
		jdbcPachong.update(sql, data);
	}
}
