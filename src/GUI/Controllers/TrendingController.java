package GUI.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrendingController implements Initializable {

    @FXML
    private ScrollPane trending;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Create a grid in the ScrollPane to hold all movies
        GridPane grid = new GridPane();
        trending.setContent(grid);

        try {
            //Loads in the MovieCard fxml
            GridPane movieCard1 = FXMLLoader.load(getClass().getResource("/GUI/Views/MovieCard.fxml"));
            GridPane movieCard2 = FXMLLoader.load(getClass().getResource("/GUI/Views/MovieCard.fxml"));
            GridPane movieCard3 = FXMLLoader.load(getClass().getResource("/GUI/Views/MovieCard.fxml"));
            GridPane movieCard4 = FXMLLoader.load(getClass().getResource("/GUI/Views/MovieCard.fxml"));


            //Creates an image & imageview, and fits the size (only for the first movie card)
            Image img1 = new Image("/Images/play.PNG");
            ImageView imgV1 = new ImageView(img1);
            imgV1.setPreserveRatio(true);
            imgV1.fitWidthProperty().bind(movieCard1.widthProperty());

            //Adds the image to the movie card
            movieCard1.add(imgV1,0,0);

            //Adds the MovieCard to the grid
            grid.add(movieCard1,0,0);
            grid.add(movieCard2,1,1);
            grid.add(movieCard3,2,2);
            grid.add(movieCard4,3,3);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
