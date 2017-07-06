package application;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MovieSelection {
	private MovieDb selectedMovie;
	private MovieDBAccount user;
	private TmdbMovies movieObj;
	private String youTubeBaseURL = "https://www.youtube.com/watch?v=";
	private String baseImageURL = "https://image.tmdb.org/t/p/w500";

	public MovieSelection(MovieDBAccount user, MovieDb selection) {
		this.setUser(user);
		selectedMovie = selection;
		createMoviesObject(this.user.getTmdbApi());
	}
	public MovieSelection(MovieDBAccount user){
		this.setUser(user);
		this.selectedMovie = null;
		createMoviesObject(this.user.getTmdbApi());
	}

	public MovieDb getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(MovieDb selected) {
		this.selectedMovie = selected;
	}

	public String getVidoeURL() {
		return youTubeBaseURL + movieObj.getVideos(this.getSelectedMovie().getId(), "English").get(0).getKey();
	}

	public String getImageURL() {
		return baseImageURL + this.getSelectedMovie().getPosterPath();
	}

	public void generateTrailerWindow(WebView webview) {

		webview.getEngine().load(this.getVidoeURL());

		Stage stage = new Stage();

		FlowPane root2 = new FlowPane();
		root2.getChildren().add(webview);

		stage.setTitle("TrailerWindow");
		Scene Trailer = new Scene(root2, 1000, 800);
		stage.setScene(Trailer);
		stage.centerOnScreen();
		stage.show();
	}

	private void createMoviesObject(TmdbApi Apikey) {
		movieObj = new TmdbMovies(Apikey);
	}

	public MovieDBAccount getUser() {
		return user;
	}

	public void setUser(MovieDBAccount user) {
		this.user = user;
	}
}
