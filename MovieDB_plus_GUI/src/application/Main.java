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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


import java.util.Hashtable;
import java.util.Iterator;

import info.movito.themoviedbapi.TmdbSearch.MultiListResultsPage;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

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
		listView.setPrefSize(500, 800);
		listView.setEditable(true);

		// webview initialization
		WebView webview = new WebView();
		webview.setPrefSize(1000, 800);

		// Create Movie Randomizer
		randomizer = new TmdbRandomizer(user);

		// movie selection
		userSelection = new MovieSelection(user);
		
		//search engine
		SearchModule searchEngine = new SearchModule(user.getTmdbApi());

		Button btn1 = new Button("Random Movie");
		Button trailer = new Button("Trailer");
		Button Search = new Button("Search");

		TextField t = new TextField("Search text");
		TextArea tx = new TextArea("Search field");
		
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
		

		Search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				MultiListResultsPage results;
				results = searchEngine.searchByKeyword(t.getText());
				Iterator<Multi> itr = results.iterator();
				while(itr.hasNext()){
					Multi m = itr.next();
					tx.appendText(m.toString()+"\n");
				}
			}
		});

		root.add(btn1, 0, 1);
		root.add(trailer, 0, 2);
		
		
		root.add(tx, 2, 0);
		root.add(Search, 1, 0);
		root.add(t, 2, 2);

		primaryStage.setScene(new Scene(root, 1000, 800));
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
