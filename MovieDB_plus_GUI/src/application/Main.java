package application;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javafx.scene.web.WebView;
import javafx.stage.Stage;

//Movie DB imports
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;


import java.util.*;

/*
 * @Reference: http://www.java2s.com/Tutorials/Java/JavaFX/0640__JavaFX_ListView.htm
 */
public class Main extends Application {
	private Hashtable<String, MovieDb> list = new Hashtable<String, MovieDb>();
	private TmdbRandomizer randomizer;
	private MovieSelection userSelection;
	
	@Override
	public void start(Stage primaryStage) {
		MovieDBAccount user = new MovieDBAccount();
		ObservableList<String> data = FXCollections.observableArrayList();

		ListView<String> listView = new ListView<String>(data);
		listView.setPrefSize(200, 250);
		listView.setEditable(true);

		// webview initialization
		WebView webview = new WebView();
		webview.setPrefSize(1000, 800);
		
		//Create Movie Randomizer
		randomizer = new TmdbRandomizer(user);
		
		//movie selection
		userSelection = new MovieSelection(user);
		
		Button btn1 = new Button("Random Movie");
		Button trailer = new Button("Trailer");

		MovieResultsPage watchlist = user.getWatchList();
		Iterator<MovieDb> iterator = watchlist.iterator();
		while (iterator.hasNext()) {
			MovieDb movie = iterator.next();
			data.add(movie.toString());
			list.put(movie.toString(), movie);
		}

		listView.setItems(data);

		GridPane root = new GridPane();
		root.add(listView, 0, 0);

		
		

		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					randomizer.generatePool();
					MovieDb x = randomizer.getRandomMovie();
					userSelection.setSelectedMovie(x);
					Image img = new Image(userSelection.getImageURL());
					ImageView imgView = new ImageView(img);
					root.add(imgView, 1, 1);
				} catch (RandomNotFoundException re) {
					System.out.println(re.getMessage());
				}
			}
		});
		
		listView.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
			userSelection.setSelectedMovie(list.get(new_val));

			Image img = new Image(userSelection.getImageURL());
			ImageView imgView = new ImageView(img);
			root.add(imgView, 1, 1);
		});
		
		
		trailer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userSelection.generateTrailerWindow(webview);
			}
		});

		root.add(btn1, 0, 1);
		root.add(trailer, 0, 2);

		primaryStage.setScene(new Scene(root, 200, 250));
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
	
	/*public void generateTrailerWindow(MovieDBAccount user, WebView webview){
		
		webview.getEngine().load(user.getVidoeURL());
		
        Stage stage = new Stage();
        
        FlowPane root2 = new FlowPane();
		root2.getChildren().add(webview);
		
        stage.setTitle("TrailerWindow");
        Scene Trailer = new Scene(root2,1000,800);
        stage.setScene(Trailer);
        stage.centerOnScreen();
        stage.show();
	}*/

	public static void main(String[] args) {
		launch(args);
	}
}
