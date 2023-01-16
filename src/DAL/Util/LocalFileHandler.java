package DAL.Util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class LocalFileHandler {


    /**
     * todo make delete function
     * @param path
     * @throws Exception
     */
    public static void deleteLocalFile(String path) throws Exception {}


    /**
     * todo skriv kommentar
     * @param path
     * @param fileType
     * @return
     * @throws Exception
     */
    public static Path createLocalFile(String path, FileType fileType) throws Exception {
        try {
            File localFilePath = new File(path);
            String fileName = localFilePath.getName();

            String relativeFilePath = "resources//";
            String folder = "";

            if (fileType == FileType.MOVIE) folder += "movies//";

            if (fileType == FileType.TRAILER) folder += "trailers//";

            if (fileType == FileType.IMAGE) folder += "images//";


            Path originalFile = Paths.get(path);
            File file = new File(relativeFilePath+=folder+=fileName);
            Path finalFile = Paths.get(file.getPath());
            Files.copy(originalFile, finalFile, REPLACE_EXISTING);


            return finalFile;

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to create movie", e);
        }
    }

    public static Path saveFileFromApi(String imgString, String imdbId){

        Image img = new Image(imgString);
        File file = new File("resources\\images\\" + imdbId + ".jpg");


        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), "jpg", file);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Paths.get(file.getPath());
    }
}
