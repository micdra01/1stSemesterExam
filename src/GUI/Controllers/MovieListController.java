package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * todo make a instance variable for movieModel.moviesInList so we can chose the specific list we want shown in our content window (categories, favourite mm)
 */


public class MovieListController implements Initializable {

    public ScrollPane movieListView, homeView, listAllMovies, listPopular, listTrending;
    @FXML
    private MovieModel movieModel;

    MainController mainController;
    private double minRatingPopular = 8.2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //sets the models
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (movieListView != null) {
            createContentGrid();
        }
        if (homeView != null) {
            //createTrendingList();
            createPopularList();
            createAllMoviesList();
        }
    }

    /**
     * creates the gridPane for the All Movies list
     * fills it with movies from list
     */
    private void createAllMoviesList() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        listAllMovies.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;
        //loop for creating each movieCard and setting movie info
        for (int i = 0; movieModel.getMoviesInList().size() > i; i++) {
            Movie movie = movieModel.getMoviesInList().get(i);

            GridPane movieCard = createMovieCard(movie);//creates the movie card
            grid.add(movieCard, col, row);//adds it to the content gridPane

            //makes a space between all movies
            col++;
            grid.add(new Separator(Orientation.HORIZONTAL), col, row);
            col++;
        }
    }

    /**
     * creates the gridPane for the Popular list
     * fills it with movies from list
     */
    private void createPopularList() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        listPopular.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;
        //loop for creating each movieCard and setting movie info
        for (Movie movie : movieModel.getMoviesInList()) {
            if (movie.getImdbRating() > minRatingPopular) {
                GridPane movieCard = createMovieCard(movie);//creates the movie card
                grid.add(movieCard, col, row);//adds it to the content gridPane

                //makes a space between all movies
                col++;
                grid.add(new Separator(Orientation.HORIZONTAL), col, row);
                col++;
            }
        }
    }

    /**
     * creates the gridPane for movieContent
     * fills it with movies from list
     */
    public void createContentGrid() {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        movieListView.setContent(grid);

        //used for placing
        int col = 0;
        int row = 0;
        //loop for creating each movieCard and setting movie info
        for (int i = 0; movieModel.getMoviesInList().size() > i; i++) {
            Movie movie = movieModel.getMoviesInList().get(i);

            GridPane movieCard = createMovieCard(movie);//creates the movie card
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

        }
    }

    /**
     * create the movieCard gridPane and fills it with info from chosen movie
     * picture, title and rating
     * @param movie, the specific movie in the list
     * @return
     */
    private GridPane createMovieCard(Movie movie) {
        GridPane movieCard = null;
        //loads the movieCard fxml
        try {
            movieCard = FXMLLoader.load(getClass().getResource("/GUI/Views/MovieCard.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Image img = new Image(movie.getPictureFileLink());
        ImageView imgView = new ImageView(img);//creates a new image view and sets the img in it
        imgView.setPreserveRatio(true);
        imgView.setFitWidth(200);
        imgView.setFitHeight(200);

        //creates a label with the title of the movieCard
        Label lblTitle = new Label(movie.getTitle());

        // creates a label with the rating info on
        String rating = String.valueOf(movie.getImdbRating());
        Label lblRating = new Label(rating);

        //sets the movieCard information labels on the movieCard gridPane
        movieCard.add(imgView, 0, 0);
        movieCard.add(lblTitle, 0, 1);
        movieCard.add(lblRating, 2, 1);


        Label lblTitleCard = new Label("");
        lblTitleCard.setFont(Font.font(20));
        lblTitleCard.setWrapText(true);

        Label lblDescriptionCard = new Label("");
        lblDescriptionCard.setMinSize(130,130);
        lblDescriptionCard.setWrapText(true);

        VBox vBox1 = new VBox(lblTitleCard, lblDescriptionCard);
        movieCard.add(vBox1, 0,0);

        Button btnPlay = new Button();
        Button btnInfo = new Button();


        VBox vBox = new VBox(btnPlay, btnInfo);
        btnPlay.setText("play");
        btnInfo.setText("se info");
        vBox.setAlignment(Pos.CENTER);
        movieCard.add(vBox, 2,0);


        btnPlay.setOpacity(0);
        btnInfo.setOpacity(0);

        btnInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                mainController.openMovieInfo(movie);
            }
        });

        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String moviePath = new File(movie.getMovieFileLink()).getAbsolutePath();
                    Desktop.getDesktop().open(new File(moviePath));
                } catch (IOException e) {
                    new Exception("Failed to play movie"+e);
                }
            }
        });
        movieCard.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                imgView.setOpacity(0.3);
                btnPlay.setOpacity(1);
                btnInfo.setOpacity(1);

                lblTitleCard.setText(movie.getTitle());
                lblDescriptionCard.setText("forklaring og info om filmen bla bla bla bla bla bla bla bla bla bla bla  bla bla bla bla bla bla bla bla bla bla bla  bla bla bla bla bla bla bla bla bla bla bla");
            }
        });
        movieCard.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imgView.setOpacity(1);
                btnPlay.setOpacity(0);
                btnInfo.setOpacity(0);
                lblTitleCard.setText("");
                lblDescriptionCard.setText("");
            }
        });
        return movieCard; // the finished movieCard with information
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
