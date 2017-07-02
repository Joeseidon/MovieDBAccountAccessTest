package movie_db_test;

import java.io.IOException;
import java.util.Iterator;

import javax.swing.JOptionPane;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbAuthentication;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.config.TokenSession;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.SessionToken;

public class AccountAccess {
	private static TmdbApi tmdbApi;
	private static SessionToken sessionToken;
	
	public static void main(String[] args){
		tmdbApi = new TmdbApi("3c55a927fbd8c6990313cb6d5de43d62");
		
		sessionToken = getSessionToken();
		MovieResultsPage searchResults;
		//search provides resutls from a movie db quiery
		try{
		searchResults = search();
		Iterator<MovieDb> iterator = searchResults.iterator();
		while (iterator.hasNext()) {
			MovieDb movie = iterator.next();
			System.out.println(movie.getTitle());
			System.out.println(movie.getOriginalTitle());
			System.out.println(movie.getReleaseDate());
			System.out.println();
		}
		}catch(IOException e){
			JOptionPane.showConfirmDialog(null, e.getMessage());
		}
		
		
	}
	
	private static MovieResultsPage search() throws IOException{
		//System.out.print("Please Enter a Title to Search: ");
		//byte[] ret = new byte[5];
		//System.in.read(ret);
		String retStr = JOptionPane.showInputDialog(null, "Please Enter a Title to Search: ");
		
		TmdbSearch tmdbSearch = tmdbApi.getSearch();
		
		return tmdbSearch.searchMovie(retStr,0,"en",false,0);
	}
	
	private static MovieResultsPage retieveWatchList(){
		
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
