package com.shihy.springboot;
/***
 * @Title: springstarter
 * @author shy
 * @Description 
 * @data 2019年4月8日 下午4:54:50
 */
public class Card {
	private String cardName;
	private String cardCode;
	private Integer cardValue;
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public Integer getCardValue() {
		return cardValue;
	}
	public void setCardValue(Integer cardValue) {
		this.cardValue = cardValue;
	}
}
