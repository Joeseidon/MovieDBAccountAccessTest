package application;

@SuppressWarnings("serial")
public class RandomNotFoundException extends Exception{
	public RandomNotFoundException(){}
	public RandomNotFoundException(String message){
		super(message);
	}
}
