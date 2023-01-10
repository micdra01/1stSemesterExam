package GUI.Controllers;

import BE.Movie;
import GUI.Models.MovieModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;

public class MovieCardController {

    @FXML
    private GridPane movieCard;
    @FXML
    private Label lblMovieCard;

    private Parent borderPane;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private MainController mainController;

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    private MovieModel movieModel;

    /**
     * create the movieCard gridPane and fills it with info from chosen movie
     * picture, title and rating
     * todo when new images are in place sizing should be changed
     * @param movie, the specific movie in the list
     * @return
     */
    public GridPane createMovieCard(Movie movie) {
        GridPane movieCard = new GridPane();
        //creates image
        Image img = new Image(movie.getPictureFileLink());
        ImageView imgView = new ImageView(img);//creates a new image view and sets the img in it
        imgView.setPreserveRatio(true);
        imgView.setFitWidth(200);
        imgView.setFitHeight(200);


        //creates a label with the title of the movieCard
        Label lblTitle = new Label(movie.getTitle());
        lblTitle.setPrefWidth(140);

        // creates a label with the rating info on
        String rating = String.valueOf(movie.getImdbRating());
        Label lblRating = new Label(rating);
        lblRating.setPrefWidth(60);
        lblRating.setAlignment(Pos.CENTER);

        Label lblTitleCard = new Label("");
        lblTitleCard.setFont(Font.font(20));
        lblTitleCard.setWrapText(true);

        Label lblDescriptionCard = new Label("");
        lblDescriptionCard.setWrapText(true);

        VBox vBox1 = new VBox(lblTitleCard, lblDescriptionCard);
        vBox1.setMaxSize(130,130);


        Button btnPlay = new Button();
        btnPlay.setText("play");
        btnPlay.setOpacity(0);

        Button btnInfo = new Button();
        btnInfo.setText("se info");
        btnInfo.setOpacity(0);


        VBox vBox = new VBox(btnPlay, btnInfo, lblRating);
        vBox.setMaxSize(70,130);
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(vBox1, vBox);
        hBox.setMaxSize(200, 200);
        hBox.setAlignment(Pos.CENTER);

        //sets the movieCard information labels on the movieCard gridPane
        movieCard.add(imgView, 0, 0);
        movieCard.add(lblTitle, 0, 1);
        movieCard.add(hBox, 0, 0);




        btnInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             mainController.openMovieInfo(movie);

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




}
