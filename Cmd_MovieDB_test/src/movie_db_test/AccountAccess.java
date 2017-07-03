package movie_db_test;

import java.io.IOException;
import java.util.Iterator;


import javax.swing.JOptionPane;


import info.movito.themoviedbapi.TmdbAccount.MediaType;
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



public class AccountAccess {

	private static TmdbApi tmdbApi;
	private static SessionToken sessionToken;
	private static TmdbAccount tmdbAccount;
	private static Account act;
	private static AccountID actId;

	public static void main(String[] args) {
		tmdbApi = new TmdbApi("3c55a927fbd8c6990313cb6d5de43d62");

		sessionToken = getSessionToken();

		tmdbAccount = tmdbApi.getAccount();
		act = tmdbAccount.getAccount(sessionToken);
		actId = new AccountID(act.getId());

		String[] options = new String[] { "Search", "Watchlist", "Add", "Remove" };
		while (true) {
			int response = JOptionPane.showOptionDialog(null, "Message", "Title", JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			
			
			switch (response) {
			case 0:
				MovieResultsPage searchResults;
				//DefaultListModel<String> listModel = new DefaultListModel<>();
				// search provides resutls from a movie db quiery
				try {
					searchResults = search();
					Iterator<MovieDb> iterator = searchResults.iterator();
					while (iterator.hasNext()) {
						MovieDb movie = iterator.next();
						System.out.println(movie.getTitle());
						//listModel.addElement(movie.getTitle());
						System.out.println(movie.getOriginalTitle());
						System.out.println(movie.getReleaseDate());
						System.out.println(movie.getId());
						System.out.println();
					}
					//movieList = new JList<>(listModel);
					// this.add(movieList);
				} catch (IOException e) {
					JOptionPane.showConfirmDialog(null, e.getMessage());
				}
				break;

			case 1:
				int watchlistCount = 0;
				try {
					MovieResultsPage watchlist = retrieveWatchList();
					Iterator<MovieDb> iterator = watchlist.iterator();
					while (iterator.hasNext()) {
						watchlistCount++;
						MovieDb movie = iterator.next();
						System.out.println(movie.getTitle());
						System.out.println(movie.getOriginalTitle());
						System.out.println(movie.getReleaseDate());
						System.out.println(movie.getId());
						System.out.println();
						System.out.println("Count: "+ watchlistCount);
					}
				} catch (Exception e) {
					JOptionPane.showConfirmDialog(null, e.getMessage());
				}
				break;

			case 2:
				addMovieToWatchlist();
				break;

			case 3:
				removeMovieToWatchlist();
				break;
			}
		}
	}
/*
	public class JListEx extends JFrame {

		private static final long serialVersionUID = 1L;

		public JListEx() {
			
		}
		public static void main(String[] args){
			SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new JListEx();
	            }
	        });
		}
	}*/

	private static MovieResultsPage search() throws IOException {
		System.out.println("========================================================");
		System.out.println("                   New Search                           ");
		System.out.println("========================================================");
		
		String retStr = JOptionPane.showInputDialog(null, "Please Enter a Title to Search: ");
		
		TmdbSearch tmdbSearch = tmdbApi.getSearch();

		return tmdbSearch.searchMovie(retStr, 0, "en", false, 0);
	}

	private static ResponseStatus addMovieToWatchlist() {
		String retStr = JOptionPane.showInputDialog(null, "Please Enter a movie ID to remove from watchlist: ");
		System.out.println("========================================================");
		System.out.println("                   Adding movie Id: "+retStr+"          ");
		System.out.println("========================================================");
		
		return tmdbAccount.addToWatchList(sessionToken, actId, Integer.parseInt(retStr), MediaType.MOVIE);
	}
	
	private static ResponseStatus removeMovieToWatchlist() {
		String retStr = JOptionPane.showInputDialog(null, "Please Enter a movie ID to remove from watchlist: ");
		System.out.println("========================================================");
		System.out.println("                   Removing movie Id: "+retStr+"        ");
		System.out.println("========================================================");
		
		return tmdbAccount.removeFromWatchList(sessionToken, actId, Integer.parseInt(retStr), MediaType.MOVIE);
	}

	private static MovieResultsPage retrieveWatchList() {
		// grab watch list
		System.out.println("========================================================");
		System.out.println("                   Printing Watchlist                   ");
		System.out.println("========================================================");
		
		return tmdbAccount.getWatchListMovies(sessionToken, actId, 1);
	}

	private static SessionToken getSessionToken() {
		System.out.println("========================================================");
		System.out.println("                   Creating Session ID                  ");
		System.out.println("========================================================");
		TmdbAuthentication tmdbAuth = tmdbApi.getAuthentication();
		TokenSession tokenSession = tmdbAuth.getSessionLogin("cutinoj", "Gv$u2017Temp");
		System.out.println("Session ID: " + tokenSession.getSessionId());
		SessionToken sessionToken = new SessionToken(tokenSession.getSessionId());

		return sessionToken;
	}
}
