package xyz.theefficientcodes.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Family {
	private int UNIVERSE;
	private int FAMILY_ID;
	private int TOTAL_POWER;
	
	public Family(){

	}
	public Family(int UNIVERSE,int FAMILY_ID,int TOTAL_POWER){

		this.UNIVERSE=UNIVERSE;
		this.FAMILY_ID=FAMILY_ID;
		this.TOTAL_POWER=TOTAL_POWER;
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
	public int getTOTAL_POWER() {
		return TOTAL_POWER;
	}
	public void setTOTAL_POWER(int TOTAL_POWER) {
		this.TOTAL_POWER = TOTAL_POWER;
	}
	
	
	
}
