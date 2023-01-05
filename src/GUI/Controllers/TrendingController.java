package GUI.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TrendingController implements Initializable {

    @FXML
    private ScrollPane trending;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GridPane grid = new GridPane();
        trending.setContent(grid);
        Image img = new Image("/Images/play.PNG");
        ImageView imgV1 = new ImageView(img);
        imgV1.setSmooth(true);
        imgV1.setPreserveRatio(true);
        imgV1.setFitWidth(200);
        ImageView imgV2 = new ImageView(img);

        grid.add(imgV1, 0, 0);
        grid.add(new Label(new String("Uhh?")), 0, 1);

        grid.add(new Separator(), 1,0);

        grid.add(imgV2, 7, 0);
        grid.add(new Label(new String("Ahhhh!")), 2, 1);
    }
}
