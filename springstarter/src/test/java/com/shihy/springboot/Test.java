package com.shihy.springboot;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Test {
	
	public static void main(String[] args) {
		
		System.out.println("-----------------------------------------------------------------------------------1");
		String json = fun();
		JSONObject obj = JSONObject.parseObject(json);
		JSONArray array = (JSONArray) obj.get("cardList");
		String jsonString = array.toJSONString();
		System.out.println(jsonString);
		int size = array.size();
		for (int i = 0; i < size; i++) {
			JSONObject object = (JSONObject) array.get(i);
			System.out.println(object.get("cardCode"));
		}
		/*System.out.println("-----------------------------------------------------------------------------------2");
		fun1();
		System.out.println("-----------------------------------------------------------------------------------3");
		fun2();*/
	}
	
	public static String fun() {
		Person person = getData();
		String json = JSON.toJSON(person).toString();
		return json;
	}
	
	public static Person getData() {
		// 1。通过对象生成JSON串，对象里包含对象数组转成JSON串。
		Person person = new Person();
		person.setUsername("xiejava");
		person.setSex("man");
		person.setAge(38);
		person.setEmail("xiejava@ishareread.com");
		Card card1 = new Card();
		card1.setCardName("bankCard1");
		card1.setCardCode("888888888");
		card1.setCardValue(99999999);
		Card card2 = new Card();
		card2.setCardName("bankCard1");
		card2.setCardCode("999999999");
		card2.setCardValue(222222222);
		// 对象数组
		List<Card> cards = new ArrayList<Card>();
		cards.add(card1);
		cards.add(card2);
		person.setCardList(cards);
		return person;
	}
	
	public static void fun1() {
		Person person = getData();
		List<Card> cards = person.getCardList();
		// 2.通过JSON对象生成JSON串
		JSONObject jObject = new JSONObject();
		jObject.put("username", "xiejava");
		jObject.put("sex", "man");
		jObject.put("age", 38);
		jObject.put("email", "xiejava@ishareread.com");
		// 通过JSONArray包装对象数组
		JSONArray jArray = new JSONArray();
		jArray.addAll(cards);
		jObject.put("cardList", jArray);
		String json2 = jObject.toJSONString();
		System.out.println(json2);
	}
	
	public static void fun2() {
		// 3.通过JSON对象生成JSON串
		JSONObject jObject2 = new JSONObject();
		jObject2.put("username", "xiejava");
		jObject2.put("sex", "man");
		jObject2.put("age", 38);
		jObject2.put("email", "xiejava@ishareread.com");
		// 构造JSON字符串
		String cardjsonStr1 = "{\"cardName\":\"bankCard1\",\"cardCode\":\"888888888\",\"cardValue\":99999999}";
		String cardjsonStr2 = "{\"cardName\":\"bankCard2\",\"cardCode\":\"999999999\",\"cardValue\":222222222}";
		JSON.parseObject(cardjsonStr1);
		JSONArray jArray2 = new JSONArray();
		// 将JSON字符串转成JSON对象，加入到JSONArray，[注意一定要用JSON.parseObject()方法转换成JSON对象，否则还是字符串，转成JSON串的时候会带双引号。]
		jArray2.add(JSON.parseObject(cardjsonStr1));
		jArray2.add(JSON.parseObject(cardjsonStr2));
		jObject2.put("cardList", jArray2);
		String json3 = jObject2.toJSONString();
		System.out.println(json3);
	}
}
