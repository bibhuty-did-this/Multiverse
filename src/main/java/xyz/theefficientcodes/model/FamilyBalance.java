package xyz.theefficientcodes.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FamilyBalance {
	public int family;
	public String balance;
	
	public FamilyBalance(int family, String balance) {
		super();
		this.family = family;
		this.balance = balance;
	}
	public FamilyBalance() {
		super();
	}
	public int getFamily() {
		return family;
	}
	public void setFamily(int family) {
		this.family = family;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}


}
