package com.shihy.springboot;

import java.util.ArrayList;
import java.util.List;
/***
 * @Title: springstarter
 * @author shy
 * @Description 
 * @data 2019年4月8日 下午4:54:23
 */
public class Person {
	private String username;
	private String email;
	private String sex;
	private int age;
	private List<Card> cardList=new ArrayList<Card>();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<Card> getCardList() {
		return cardList;
	}
	public void setCardList(List<Card> cardList) {
		this.cardList = cardList;
	}
 
}
