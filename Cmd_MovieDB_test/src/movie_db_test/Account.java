package movie_db_test;

import info.movito.themoviedbapi.TmdbAccount;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbAuthentication;
import info.movito.themoviedbapi.model.config.TokenSession;
import info.movito.themoviedbapi.model.core.SessionToken;

public class Account extends TmdbAccount{

	private TmdbApi tmdbApi;
	private SessionToken sessionToken;
	
	Account(TmdbApi tmdbApi) {
		super(tmdbApi);
		this.tmdbApi = tmdbApi;
		
		// TODO Auto-generated constructor stub
	}
	
	public void setApi(final String newApi){
		tmdbApi = new TmdbApi("3c55a927fbd8c6990313cb6d5de43d62");
	}
	
	public SessionToken getSession(){
		return sessionToken;
	}
	
	public TmdbApi getApiKey(){
		return tmdbApi;
	}
	
	public SessionToken createSession(String usrName, String psw){
		TmdbAuthentication tmdbAuth = tmdbApi.getAuthentication();
		TokenSession tokenSession = tmdbAuth.getSessionLogin(usrName, psw);
		System.out.println("Session ID: " + tokenSession.getSessionId());
		sessionToken = new SessionToken(tokenSession.getSessionId());
		return sessionToken;
	}
	
	
}
