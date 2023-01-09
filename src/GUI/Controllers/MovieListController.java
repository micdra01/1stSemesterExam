package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

/**
 * todo make a instance variable for movieModel.moviesInList so we can chose the specific list we want shown in our content window (categories, favourite mm)
 */


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

       createContentGrid();
    }

    /**
     * creates the gridPane for movieContent
     * fills it with movies from list
     */
    private void createContentGrid() {
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
     * create the movieCard gridPane and fills it with info from chosen movie
     * picture, title and rating
     * @param numberInList
     * @return
     */
    private GridPane createMovieCard(int numberInList) {
        GridPane movieCard = null;
        //loads the movieCard fxml
        try {
            movieCard = FXMLLoader.load(getClass().getResource("/GUI/Views/MovieCard.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image img = new Image("/Images/play.PNG");//todo get the real image file link when it is saved correct in db
        ImageView imgView = new ImageView(img);//creates a new image view and sets the img in it
        imgView.setPreserveRatio(true);
        imgView.fitWidthProperty().bind(movieCard.widthProperty());//sets it to the cards full size

        //creates a label with the title of the movieCard
        Label lblTitle = new Label(movieModel.getMoviesInList().get(numberInList).getTitle());

        // creates a label with the rating info on
        String rating = String.valueOf(movieModel.getMoviesInList().get(numberInList).getImdbRating());
        Label lblRating = new Label(rating);

        //sets the movieCard information labels on the movieCard gridPane
        movieCard.add(imgView, 0, 0);
        movieCard.add(lblTitle, 0, 1);
        movieCard.add(lblRating, 2, 1);

        return movieCard; // the finished movieCard with information
    }

    /**
     * sets a listener on the movieCard
     * when a user has clicked the card, the movie information fxml gets called with the specified movie
     * @param movieInList number of movie li the shown list
     * @param movieCard
     */
    private void movieCardOnClickListener(int movieInList, GridPane movieCard) {
        //used for counting number in list
        int finalCount = movieInList;
        movieCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //gets the chosen movie and sends it to mainController that opens the information window
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
