package com.cms.util;

import java.io.Serializable;

public class CardMapLayer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cardNumber;
	private String cardID;
	
	private int moneyValue;
	private int points;
	private String password;
	private int cardType;
	
	private String initDate;
	private String activeDate;
	private String ExpireDate;
	private long initCode;
	
	
	public String printAllInfo(){
		String info="Card number="+cardNumber+"\n"
				+"Card ID="+cardID+"\n"+"\n"
				+"Money value="+moneyValue+"\n"
				+"Points="+points+"\n"+"\n"
				+"Password="+password+"\n"
				+"Card type="+cardType+"\n"+"\n"
				+"Init date="+initDate+"\n"
				+"Active date="+activeDate+"\n"
				+"Expire date="+ExpireDate+"\n"
				+"Init code="+initCode+"\n";
		return info;
	}
	
	
	public String getCardNumber() {
		return cardNumber;
	}
	public String getCardID() {
		return cardID;
	}
	public int getMoneyValue() {
		return moneyValue;
	}
	public int getPoints() {
		return points;
	}
	public String getPassword() {
		return password;
	}
	public int getCardType() {
		return cardType;
	}
	public String getInitDate() {
		return initDate;
	}
	public String getActiveDate() {
		return activeDate;
	}
	public String getExpireDate() {
		return ExpireDate;
	}
	public long getInitCode() {
		return initCode;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public void setMoneyValue(int moneyValue) {
		this.moneyValue = moneyValue;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setCardType(int cardType) {
		this.cardType = cardType;
	}
	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}
	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}
	public void setExpireDate(String expireDate) {
		ExpireDate = expireDate;
	}
	public void setInitCode(long initCode) {
		this.initCode = initCode;
	}
}
