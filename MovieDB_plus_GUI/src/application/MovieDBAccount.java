package application;

import info.movito.themoviedbapi.TmdbAccount.MediaType;

import java.io.IOException;

import javax.swing.JOptionPane;

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
	  private static TmdbApi tmdbApi;
	  private static SessionToken sessionToken;
	  private static TmdbAccount tmdbAccount;
	  private static Account act;
	  private static AccountID actId;
	  
	  public MovieDBAccount(){
		  tmdbApi = new TmdbApi("3c55a927fbd8c6990313cb6d5de43d62");
		  sessionToken = getSessionToken();
		  tmdbAccount = tmdbApi.getAccount();
		  act = tmdbAccount.getAccount(sessionToken);
		  actId = new AccountID(act.getId());
	  }
	  /*
		 * @author Joseph
		 */
		public MovieResultsPage search() throws IOException {
			System.out.println(
					"========================================================");
			System.out.println(
					"                   New Search                           ");
			System.out.println(
					"========================================================");

			String retStr = JOptionPane.showInputDialog(null,
					"Please Enter a Title to Search: ");

			TmdbSearch tmdbSearch = tmdbApi.getSearch();

			return tmdbSearch.searchMovie(retStr, 0, "en", false, 0);
		}

		/*
		 * @author Joseph
		 */
		public ResponseStatus addMovieToWatchlist() {
			String retStr = JOptionPane.showInputDialog(null,
					"Please Enter a movie ID to remove from watchlist: ");
			System.out.println(
					"========================================================");
			System.out.println(
					"                   Adding movie Id: " + retStr + "          ");
			System.out.println(
					"========================================================");

			return tmdbAccount.addToWatchList(sessionToken, actId,
					Integer.parseInt(retStr), MediaType.MOVIE);
		}

		/*
		 * @author Joseph
		 */
		public ResponseStatus removeMovieFromWatchlist() {
			String retStr = JOptionPane.showInputDialog(null,
					"Please Enter a movie ID to remove from watchlist: ");
			System.out.println(
					"========================================================");
			System.out.println(
					"                   Removing movie Id: " + retStr + "        ");
			System.out.println(
					"========================================================");

			return tmdbAccount.removeFromWatchList(sessionToken, actId,
					Integer.parseInt(retStr), MediaType.MOVIE);
		}

		/*
		 * @author Joseph
		 */
		public MovieResultsPage retrieveWatchList() {
			// grab watch list
			System.out.println(
					"======================================================");
			System.out.println(
					"                   Printing Watchlist                 ");
			System.out.println(
					"======================================================");

			return tmdbAccount.getWatchListMovies(sessionToken, actId, 1);
		}

		/*
		 * @author Joseph
		 */
		public SessionToken getSessionToken() {
			System.out.println(
					"======================================================");
			System.out.println(
					"                   Creating Session ID                ");
			System.out.println(
					"======================================================");
			TmdbAuthentication tmdbAuth = tmdbApi.getAuthentication();
			TokenSession tokenSession = tmdbAuth.getSessionLogin("cutinoj",
					"Gv$u2017Temp");
			System.out.println("Session ID: " + tokenSession.getSessionId());
			SessionToken sessionToken = new SessionToken(
					tokenSession.getSessionId());

			return sessionToken;
		}
}
