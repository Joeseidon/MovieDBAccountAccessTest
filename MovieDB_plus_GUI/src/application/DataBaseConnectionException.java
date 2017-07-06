package application;

@SuppressWarnings("serial")
public class DataBaseConnectionException extends Exception {
	public DataBaseConnectionException(){}
	public DataBaseConnectionException(String message){
		super(message);
	}
}
