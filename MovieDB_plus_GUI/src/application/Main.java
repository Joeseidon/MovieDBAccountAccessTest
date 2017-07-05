package application;
	
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

//Movie DB imports
import info.movito.themoviedbapi.TmdbAccount.MediaType;
import info.movito.themoviedbapi.TmdbAccount;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbAuthentication;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.ArtworkType;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Video;
import info.movito.themoviedbapi.model.config.Account;
import info.movito.themoviedbapi.model.config.TokenSession;
import info.movito.themoviedbapi.model.core.AccountID;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.ResponseStatus;
import info.movito.themoviedbapi.model.core.SessionToken;

import java.util.*;


/*
 * @Reference: http://www.java2s.com/Tutorials/Java/JavaFX/0640__JavaFX_ListView.htm
 */
public class Main extends Application {
	private Hashtable<String, MovieDb> list = new Hashtable<String, MovieDb>();
	private String imageKey;
	private String videoKey;
	private String youTubeBaseURL = "https://www.youtube.com/watch?v=";
	private String baseImageURL = "https://image.tmdb.org/t/p/w500";
	
	@Override
	public void start(Stage primaryStage) {
		MovieDBAccount user = new MovieDBAccount();
	    ObservableList<String> data = FXCollections.observableArrayList();

	    ListView<String> listView = new ListView<String>(data);
	    listView.setPrefSize(200, 250);
	    listView.setEditable(true);
	    
	    //webview initialization 
	    WebView webview = new WebView();
	    webview.setPrefSize(640, 390);
	    
	    
	    MovieResultsPage watchlist = user.retrieveWatchList();
		Iterator<MovieDb> iterator = watchlist.iterator();
		while (iterator.hasNext()) {
			MovieDb movie = iterator.next();
			/*System.out.println(movie.getTitle());
			System.out.println(movie.getOriginalTitle());
			System.out.println(movie.getReleaseDate());
			System.out.println(movie.getId());
			System.out.println();*/
			data.add(movie.toString());
			list.put(movie.toString(), movie);
		}


	    listView.setItems(data);
	    StackPane root = new StackPane();
	    root.getChildren().add(listView);
	    primaryStage.setScene(new Scene(root, 200, 250));
	    primaryStage.show();
	    listView.getSelectionModel().selectedItemProperty().addListener(
	            (ObservableValue<? extends String> ov, String old_val, 
	                String new_val) -> {
	                	
	                	//use for webview 
	                    /*System.out.println(list.get(new_val).getVideos().get(0).getSite());
	                    webview.getEngine().load(
	                    	      youTubeBaseURL+list.get(new_val).getVideos().get(0).getKey()
	                    	    );
	                    primaryStage.setScene(new Scene(webview));
	                    primaryStage.show();*/
	                	
	                	//image processing (WORKS!!!)
	                
	                	Image img = new Image(baseImageURL+list.get(new_val).getPosterPath());
	                    ImageView imgView = new ImageView(img);
	                    root.getChildren().add(imgView);
	                    Scene scene = new Scene(root);
	                    primaryStage.setScene(scene);
	                    primaryStage.show();
	                	
	                	
	                	/*Does not work. Cannot get youtube key from movie class
	                	 try{
	                		System.out.println(new_val);
	                		System.out.println(list.get(new_val).getTitle());
	                		MovieDb movie = list.get(new_val);
	                		System.out.println(movie.getImdbID());
	                		List<Video> l = movie.getVideos();
	                		System.out.println("length: "+movie.getVideos().size());
	                		for(Video x : l){
	                			System.out.println(x.getKey());
	                			System.out.println(x.getSite());
	                			System.out.println(x.getSize());
	                		}
	                	}catch(Exception e){
	                		System.out.println(e.getMessage());
	                	}
	                	//System.out.println(list.get(new_val).getVideos());
	               */
	        });
	   
		
		/*try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}
	 static class ColorRectCell extends ListCell<String> {
		    @Override
		    public void updateItem(String item, boolean empty) {
		      super.updateItem(item, empty);
		      Rectangle rect = new Rectangle(400, 400);
		      if (item != null) {
		        rect.setFill(Color.RED);
		        setGraphic(rect);
		      }
		    }
		  }
	
	public static void main(String[] args) {
		launch(args);
	}
}
