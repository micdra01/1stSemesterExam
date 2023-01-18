package DAL.Util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class LocalFileHandler {

    /**
     * todo make delete function
     * @param path
     * @throws Exception
     */
    public static void deleteLocalFile(String path) throws Exception {
        Path finalPath = FileSystems.getDefault().getPath("./src/test/resources/newFile.txt");
            Files.delete(Path.of(path));
    }


    /**
     * creates local file from relative path
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

    /**
     * creates local file from url
     * @param imgString
     * @param imdbId
     * @return
     */
    public static Path saveFileFromApi(String imgString, String imdbId){

        Image img = new Image(imgString);
        File file = new File("resources\\images\\" + imdbId + ".jpg");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), "jpg", file);
        } catch (IOException e) {
            throw new RuntimeException("failed to create the movie");
        }
        return Paths.get(file.getPath());
    }
}
