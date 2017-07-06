package application;

import info.movito.themoviedbapi.model.core.MovieResultsPage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

public class TmdbRandomizer {
	private List<MovieDb> movieWatchList;
	private List<MovieDb> movieFavorites;
	private ArrayList<MovieDb> moviePool;
	private MovieDBAccount user;
	private TmdbMovies movieObj;

	public TmdbRandomizer(MovieDBAccount usr) {
		this.user = usr;
		this.update();
		
		createMoviesObj(user.getTmdbApi());
		
		generatePool();
	}

	public void update() {
		updateFavorites();
		updateWatchList();
	}

	public MovieDb getRandomMovie() throws RandomNotFoundException {
		if (moviePool == null || moviePool.isEmpty()) {
			try{
			moviePool = createRandomPool();
			}catch (DataBaseConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (moviePool.isEmpty()) {
			throw new RandomNotFoundException("moviePool is empty. Regenerate Pool");
		}
		if (moviePool == null) {
			throw new RandomNotFoundException("moviePool is empty. Regenerate Pool");
		}
		// if there are movies in the pool generate a random movie from the pool
		Random rnd = new Random();
		int index = rnd.nextInt(moviePool.size() - 1);
		return moviePool.get(index);
	}

	public void generatePool()  {
		try{
			moviePool = createRandomPool();
		}catch(RandomNotFoundException rd){
			//generate a pool from new releases and top movies 
			moviePool = createPoolWithoutUserData();
		}catch(DataBaseConnectionException dbe){
			//will need to wait and generate pool
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			moviePool = createPoolWithoutUserData();
			//throw new UserDataExemptException("Error in connection");
		}
		
	}
	
	private ArrayList<MovieDb> createPoolWithoutUserData(){
		List<MovieDb> tmp;
		ArrayList<MovieDb> pool = new ArrayList<MovieDb>();
		tmp = movieObj.getPopularMovies("English", 1).getResults();
		for(MovieDb m : tmp){
			pool.add(m);
		}
		tmp = movieObj.getTopRatedMovies("English", 1).getResults();
		for(MovieDb m : tmp){
			pool.add(m);
		}
		return pool;
	}

	private ArrayList<MovieDb> createRandomPool() throws RandomNotFoundException, DataBaseConnectionException {
		List<MovieDb> tmp;
		ArrayList<MovieDb> pool = new ArrayList<MovieDb>();
		try{
		if (movieWatchList != null && !movieWatchList.isEmpty()) {
			for (MovieDb i : movieWatchList) {

				tmp = movieObj.getSimilarMovies(i.getId(), "English", 1).getResults();

				if (tmp != null && !tmp.isEmpty()) {
					for (MovieDb m : tmp) {
						pool.add(m);
					}
				} 
			}
		} 

		if (movieFavorites != null && !movieFavorites.isEmpty()) {
			for (MovieDb i : movieFavorites) {
				tmp = movieObj.getSimilarMovies(i.getId(), "English", 1).getResults();
				if (tmp != null && !tmp.isEmpty()) {
					for (MovieDb m : tmp) {
						pool.add(m);
					}
				} 
			}
		}
		}catch(NullPointerException e){
			throw new DataBaseConnectionException();
		}

		if (pool == null || pool.isEmpty()){
			throw new RandomNotFoundException("WatchList and Favorites must be empty.");
		}
		return pool;
	}

	private void updateFavorites() {
		movieFavorites = toMovieList(user.getFavorites());
	}

	private void updateWatchList() {
		movieWatchList = toMovieList(user.getWatchList());
	}

	private List<MovieDb> toMovieList(MovieResultsPage rsp) {
		ArrayList<MovieDb> tmp = new ArrayList<MovieDb>();
		Iterator<MovieDb> iterator = rsp.iterator();
		while (iterator.hasNext()) {
			MovieDb movie = iterator.next();
			tmp.add(movie);
		}
		return tmp;
	}

	private void createMoviesObj(TmdbApi Apikey) {
		movieObj = new TmdbMovies(Apikey);
	}
}