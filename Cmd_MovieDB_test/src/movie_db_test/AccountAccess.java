package movie_db_test;

import java.io.IOException;
import javax.swing.JOptionPane;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbAuthentication;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.config.TokenSession;
import info.movito.themoviedbapi.model.core.SessionToken;

public class AccountAccess {
	private static TmdbApi tmdbApi;
	private static SessionToken sessionToken;
	
	public static void main(String[] args){
		tmdbApi = new TmdbApi("3c55a927fbd8c6990313cb6d5de43d62");
		
		sessionToken = getSessionToken();
		
		
		
	}
	
	private TmdbMovies search() throws IOException{
		//System.out.print("Please Enter a Title to Search: ");
		//byte[] ret = new byte[5];
		//System.in.read(ret);
		String retStr = "";
		JOptionPane.showInputDialog(retStr, "Please Enter a Title to Search: ");
		return null;
		
	}
	
	private static SessionToken getSessionToken(){
		
		TmdbAuthentication tmdbAuth = tmdbApi.getAuthentication();
		TokenSession tokenSession = tmdbAuth.getSessionLogin("cutinoj","Gv$u2017Temp");
		System.out.println("Session ID: " + tokenSession.getSessionId());
		SessionToken sessionToken = new SessionToken(tokenSession.getSessionId());
		
		return sessionToken;
	}
}
