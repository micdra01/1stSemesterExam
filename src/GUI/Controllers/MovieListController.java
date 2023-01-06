package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


public class MovieListController implements Initializable {

    public ScrollPane movieListView;
    @FXML
    private MovieModel movieModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        movieListView.setContent(grid);
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //used for placing
        int col = 0;
        int row = 0;
        int count = 0;
        for (int i = 0; movieModel.getMoviesInList().size() > i; i++) {
            GridPane movieCard = null;


            try {
                movieCard = FXMLLoader.load(getClass().getResource("/GUI/Views/MovieCard.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Image img = new Image("/Images/play.PNG");
            ImageView imgView = new ImageView(img);
            imgView.setPreserveRatio(true);
            imgView.fitWidthProperty().bind(movieCard.widthProperty());

            Label lblTitle = new Label(movieModel.getMoviesInList().get(i).getTitle());

            String rating = String.valueOf(movieModel.getMoviesInList().get(i).getImdbRating());
            Label lblRating = new Label(rating);


            movieCard.add(imgView, 0, 0);
            movieCard.add(lblTitle, 0, 1);
            movieCard.add(lblRating, 2, 1);

            grid.add(movieCard, col, row);

            if(col < 3 ){
                col++;
            }else {
                col = 0;
                row++;
            }


            int finalCount = count;
            movieCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Movie m = movieModel.getMoviesInList().get(finalCount);
                    MainController me = new MainController();
                    me.openMovieInfo(m);

                }
            });
            count++;

        }


    }
    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }


}
