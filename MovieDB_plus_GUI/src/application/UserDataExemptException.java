package application;

@SuppressWarnings("serial")
public class UserDataExemptException extends Exception {
	public UserDataExemptException(){}
	public UserDataExemptException(String message){
		super(message);
	}
}
