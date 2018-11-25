package xyz.theefficientcodes.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
	private int ID;
	private int UNIVERSE;
	private int FAMILY_ID;
	private int POWER;
	private String link;
	
	public Person(){
		this.ID=-1;
	}
	
	public Person(int ID,int UNIVERSE,int FAMILY_ID,int POWER){
		this.ID=ID;
		this.UNIVERSE=UNIVERSE;
		this.FAMILY_ID=FAMILY_ID;
		this.POWER=POWER;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getUNIVERSE() {
		return UNIVERSE;
	}
	public void setUNIVERSE(int uNIVERSE) {
		UNIVERSE = uNIVERSE;
	}
	public int getFAMILY_ID() {
		return FAMILY_ID;
	}
	public void setFAMILY_ID(int fAMILY_ID) {
		FAMILY_ID = fAMILY_ID;
	}
	public int getPOWER() {
		return POWER;
	}
	public void setPOWER(int pOWER) {
		POWER = pOWER;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
	
}
