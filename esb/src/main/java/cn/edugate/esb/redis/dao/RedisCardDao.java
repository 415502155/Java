package cn.edugate.esb.redis.dao;

import java.util.List;

import cn.edugate.esb.entity.Card;

public interface RedisCardDao {
	boolean add(Card tr);
	
	boolean add(List<Card> trs);
	
	boolean delete(Card tr);

	boolean deleteAll();

	Integer getStudId(String card_no);

	boolean clearStudId(String card_no, Integer stud_id);
}
