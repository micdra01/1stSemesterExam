package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import javafx.scene.control.Separator;
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

    MainController mainController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //sets the models
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

       createMovieContentGrid();
    }

    /**
     * todo write comment
     */
    private void createMovieContentGrid() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        movieListView.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;
        //loop for creating each movieCard and setting movie info
        for (int i = 0; movieModel.getMoviesInList().size() > i; i++) {

            GridPane movieCard = createMovieCard(i);//creates the movie card
            grid.add(movieCard, col, row);//adds it to the content gridPane

            //makes a space between all movies
            col++;
            grid.add(new Separator(Orientation.HORIZONTAL), col, row);

            //loop for positioning movieCards in grid
            if(col < 6 ){
                col++;
            }else {
                col = 0;
                row++;
                grid.add(new Separator(Orientation.VERTICAL), col, row);
                row++;
            }

            movieCardOnClickListener(i, movieCard);//creates the listener for each card
        }
    }



    /**
     * todo write comment
     * @param numberInLinst
     * @return
     */
    private GridPane createMovieCard(int numberInLinst) {
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

        Label lblTitle = new Label(movieModel.getMoviesInList().get(numberInLinst).getTitle());

        String rating = String.valueOf(movieModel.getMoviesInList().get(numberInLinst).getImdbRating());
        Label lblRating = new Label(rating);

        movieCard.add(imgView, 0, 0);
        movieCard.add(lblTitle, 0, 1);
        movieCard.add(lblRating, 2, 1);
        return movieCard;
    }

    /**
     * todo write comment
     * @param movieInList
     * @param movieCard
     */
    private void movieCardOnClickListener(int movieInList, GridPane movieCard) {

        int finalCount = movieInList;
        movieCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Movie m = movieModel.getMoviesInList().get(finalCount);
                mainController.openMovieInfo(m);
            }
        });
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }



}
