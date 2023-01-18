import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/MainWindowView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setMinWidth(350);
        primaryStage.setMinHeight(350);
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/GUI/CSS/Theme.css").toExternalForm());
        primaryStage.show();

        //Shows alert box when loading main window
        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setContentText("Hover movie to delete");
        alert.setHeaderText("Following movies have not been watched for two or more years" + "\n" + "or have a personal rating of 6 or lower");
        alert.show();
         */
    }
}

