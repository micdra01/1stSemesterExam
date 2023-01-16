package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieCardController {
    private MainController mainController;

    private MovieModel movieModel;

    public MovieCardController(){
    }

    /**
     * create the movieCard gridPane and fills it with info from chosen movie
     * picture, title and rating
     * @param movie, the specific movie in the list
     * @return
     */
    public GridPane createMovieCard(Movie movie, MovieModel movieModel) {
        GridPane movieCard = null;
        this.movieModel = movieModel;
        //loads the movieCard fxml
        try {
            movieCard = FXMLLoader.load(getClass().getResource("/GUI/Views/MovieCard.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image img = new Image(movie.getPictureFileLink());
        ImageView imgView = new ImageView(img);//creates a new image view and sets the img in it
        imgView.setPreserveRatio(true);
        imgView.setFitWidth(130);
        imgView.setFitHeight(200);

        //creates a label with the title of the movieCard
        Label lblTitle = new Label(movie.getTitle());

        // creates a label with the rating info on
        String rating = "⭐" + movie.getImdbRating() + "  ❤" + movie.getPersonalRating();

        Label lblRating = new Label(rating);

        //sets the movieCard information labels on the movieCard gridPane
        movieCard.add(imgView, 0, 0);
        movieCard.add(lblTitle, 0, 1);
        movieCard.add(lblRating, 1, 1);

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

        btnInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                openMovieInfo(movie);
            }
        });
        movieCard.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                imgView.setOpacity(0.3);
                btnPlay.setOpacity(1);
                btnInfo.setOpacity(1);

                lblTitleCard.setText(movie.getTitle());
                lblDescriptionCard.setText(movie.getMovieDescription());
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

    /**
     * Loads MovieView FXML with a Movie object if button is cliked.
     * @param movie
     */
    public void openMovieInfo(Movie movie){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MovieView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
            //System.out.println(root);

        } catch (IOException e) {
            new Exception("Failed to show 'movie info'", e);
        }

        Stage stage = new Stage();
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Movie info: " + movie.getTitle());
        stage.setScene(new Scene(root));
        stage.show();
        MovieViewController controller = loader.getController();
        controller.setMovieModel(movieModel);
        controller.setMovieContent(movie);
    }

}
