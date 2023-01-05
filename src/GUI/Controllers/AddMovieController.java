package GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddMovieController {
    public TextField textImageFile, textTrailerFile, textIMDBRating, textCategory, textTitle, textMovieFile;
    public Button btnMovieFile, btnTrailerFile, btnImageFile, btnSave;
    public Label lblImageFile, lblTrailerFile, lblIMDBRating, lblCategory, lblMovieFile, lblTitle;


    public void handleMovieFile(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser mFileChooser = new FileChooser();
        mFileChooser.showOpenDialog(stage);
    }


    public void handleTrailerFile(ActionEvent event) {
    }


    public void handleImageFile(ActionEvent event) {
    }

    public void handleSave(ActionEvent event) {
    }


}
