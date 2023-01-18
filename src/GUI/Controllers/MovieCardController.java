package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import GUI.Util.ErrorDisplayer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class MovieCardController {

    private MovieModel movieModel;

    public MovieCardController(){
    }

    /**
     * create the movieCard gridPane and fills it with info from chosen movie
     * picture, title and rating
     * @param movie, the specific movie in the list
     */
    public GridPane createMovieCard(Movie movie, MovieModel movieModel) {
        GridPane movieCard = null;
        this.movieModel = movieModel;
        //loads the movieCard fxml
        try {
            movieCard = FXMLLoader.load(getClass().getResource("/GUI/Views/MovieCard.fxml"));
        } catch (IOException e) {
            ErrorDisplayer.displayError(new Exception("Failed to create Movie Card", e));
        }


        Image img = new Image(movie.getPictureFileLink());
        ImageView imgView = new ImageView(img);//creates a new image view and sets the img in it
        imgView.setPreserveRatio(true);
        imgView.setFitWidth(130);
        imgView.setFitHeight(190);

        //creates a label with the title of the movieCard
        Label lblTitle = new Label(movie.getTitle());

        // creates a label with the rating info on
        String rating = "⭐" + movie.getImdbRating() + "  ❤" + movie.getPersonalRating();

        Label lblRating = new Label(rating);

        //sets the movieCard information labels on the movieCard gridPane
        movieCard.add(imgView, 0, 0);
        movieCard.add(lblTitle, 0, 1);
        movieCard.add(lblRating, 1, 1);

        GridPane.setMargin(movieCard, new Insets(0, 10, 0, 10));

        Label lblTitleCard = new Label("");
        lblTitleCard.setFont(Font.font(20));
        lblTitleCard.setWrapText(true);

        Label lblDescriptionCard = new Label("");
        lblDescriptionCard.setMinSize(130,140);
        lblDescriptionCard.setWrapText(true);

        Button btnPlay = new Button();
        Button btnInfo = new Button();

        HBox hBox = new HBox(btnPlay, btnInfo);
        btnPlay.setText("play");
        btnInfo.setText("se info");
        hBox.setAlignment(Pos.CENTER);

        VBox vBox1 = new VBox(lblTitleCard, lblDescriptionCard, hBox);
        movieCard.add(vBox1, 0,0);

        btnPlay.setOpacity(0);
        btnInfo.setOpacity(0);

        btnInfo.setOnAction(event -> openMovieInfo(movie));

        vBox1.setOnMouseEntered(event -> {

            imgView.setOpacity(0.3);
            btnPlay.setOpacity(1);
            btnInfo.setOpacity(1);

            lblTitleCard.setText(movie.getTitle());
            lblDescriptionCard.setText(movie.getMovieDescription());
        });

        vBox1.setOnMouseExited(event -> {
            imgView.setOpacity(1);
            btnPlay.setOpacity(0);
            btnInfo.setOpacity(0);
            lblTitleCard.setText("");
            lblDescriptionCard.setText("");
        });


        btnPlay.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(new File(movie.getMovieFileLink()).getAbsolutePath()));
            } catch (Exception e) {
                ErrorDisplayer.displayError(new Exception("Failed to play movie"));
            }

            movie.setLastViewed(new Timestamp(System.currentTimeMillis()));
            try {
                movieModel.updateMovie(movie);
            } catch (Exception e) {
                ErrorDisplayer.displayError(e);
            }
        });
        return movieCard; // the finished movieCard with information
    }



    /**
     * Loads MovieView FXML with a Movie object if button is clicked.
     */
    public void openMovieInfo(Movie movie){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
            //System.out.println(root);

        } catch (IOException e) {
            ErrorDisplayer.displayError(new Exception("Failed to show 'movie info'", e));
        }

        Stage stage = new Stage();
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Movie info: " + movie.getTitle());
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add(getClass().getResource("/GUI/CSS/Theme.css").toExternalForm());
        stage.show();
        MovieViewController controller = loader.getController();
        controller.setMovieModel(movieModel);
        controller.setMovieContent(movie);
    }

}
