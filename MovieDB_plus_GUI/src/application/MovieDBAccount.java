package application;

import info.movito.themoviedbapi.TmdbAccount.MediaType;

import java.io.IOException;

//import javax.swing.JOptionPane;

import info.movito.themoviedbapi.TmdbAccount;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbAuthentication;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.config.Account;
import info.movito.themoviedbapi.model.config.TokenSession;
import info.movito.themoviedbapi.model.core.AccountID;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.ResponseStatus;
import info.movito.themoviedbapi.model.core.SessionToken;

public class MovieDBAccount {
	private TmdbApi tmdbApi;
	private SessionToken sessionToken;
	private TmdbAccount tmdbAccount;
	private Account act;
	private AccountID actId;
	private String youTubeBaseURL = "https://www.youtube.com/watch?v=";
	private String baseImageURL = "https://image.tmdb.org/t/p/w500";
	
	private MovieDb selectedMovie;
	
	private TmdbMovies movieObj;
	
	public MovieDBAccount() {
		tmdbApi = new TmdbApi("3c55a927fbd8c6990313cb6d5de43d62");
		sessionToken = getSessionToken();
		tmdbAccount = tmdbApi.getAccount();
		act = tmdbAccount.getAccount(sessionToken);
		actId = new AccountID(act.getId());
		createMoviesObj(tmdbApi);
	}

	/*
	 * @author Joseph
	 */
	public MovieResultsPage search(String seachString) throws IOException {
		/*System.out.println("========================================================");
		System.out.println("                   New Search                           ");
		System.out.println("========================================================");

		String retStr = JOptionPane.showInputDialog(null, "Please Enter a Title to Search: ");
		*/

		TmdbSearch tmdbSearch = tmdbApi.getSearch();

		return tmdbSearch.searchMovie(seachString, 0, "en", false, 0);
	}

	/*
	 * @author Joseph
	 */
	public ResponseStatus addMovieToWatchlist(MovieDb mToAdd) {
		/*String retStr = JOptionPane.showInputDialog(null, "Please Enter a movie ID to remove from watchlist: ");
		System.out.println("========================================================");
		System.out.println("                   Adding movie Id: " + retStr + "          ");
		System.out.println("========================================================");
		*/
		return tmdbAccount.addToWatchList(sessionToken, actId, mToAdd.getId(), MediaType.MOVIE);
	}

	/*
	 * @author Joseph
	 */
	public ResponseStatus removeMovieFromWatchlist(MovieDb mToRemove) {
		/*
		String retStr = JOptionPane.showInputDialog(null, "Please Enter a movie ID to remove from watchlist: ");
		System.out.println("========================================================");
		System.out.println("                   Removing movie Id: " + retStr + "        ");
		System.out.println("========================================================");
		 
		 */
		return tmdbAccount.removeFromWatchList(sessionToken, actId, mToRemove.getId(), MediaType.MOVIE);
	}

	/*
	 * @author Joseph
	 */
	public MovieResultsPage getWatchList() {
		/*
		// grab watch list
		System.out.println("======================================================");
		System.out.println("                   Printing Watchlist                 ");
		System.out.println("======================================================");
		*/

		return tmdbAccount.getWatchListMovies(sessionToken, actId, 1);
	}

	public MovieResultsPage getFavorites() {
		// grab watch list
		/*System.out.println("======================================================");
		System.out.println("                   Printing Favorites                 ");
		System.out.println("======================================================");
		*/

		return tmdbAccount.getFavoriteMovies(sessionToken, actId);
	}

	/*
	 * @author Joseph
	 */
	private SessionToken getSessionToken() {
		/*
		System.out.println("======================================================");
		System.out.println("                   Creating Session ID                ");
		System.out.println("======================================================");
		*/
		TmdbAuthentication tmdbAuth = tmdbApi.getAuthentication();
		TokenSession tokenSession = tmdbAuth.getSessionLogin("cutinoj", "Gv$u2017Temp");
		System.out.println("Session ID: " + tokenSession.getSessionId());
		SessionToken sessionToken = new SessionToken(tokenSession.getSessionId());

		return sessionToken;
	}
	public TmdbApi getTmdbApi(){
		return tmdbApi;
	}
	
	public MovieDb getSelectedMovie(){
		return selectedMovie;
	}
	
	public void setSelectedMovie(MovieDb selected){
		this.selectedMovie = selected;
	}
	
	public String getVidoeURL(){
		return youTubeBaseURL + movieObj.getVideos(this.getSelectedMovie().getId(), "English").get(0).getKey();
	}
	
	public String getImageURL(){
		return baseImageURL + this.getSelectedMovie().getPosterPath();
	}
	
	private void createMoviesObj(TmdbApi Apikey){
		movieObj = new TmdbMovies(Apikey);
	}
	
}
