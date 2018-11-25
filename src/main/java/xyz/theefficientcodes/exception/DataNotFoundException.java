package xyz.theefficientcodes.exception;

public class DataNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID=-99999999;
	
	public DataNotFoundException(String message){
		super(message);
	}
	
}
