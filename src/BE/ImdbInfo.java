package BE;

import java.util.ArrayList;

public class ImdbInfo {

    private String title, imdbId, pictureLink, yearOfRelease;

    private ArrayList<String> cast;

    public ImdbInfo(String imdbId, String title, String pictureLink, String yearOfRelease, ArrayList<String> cast){
        this.imdbId = imdbId;
        this.title = title;
        this.pictureLink = pictureLink;
        this.yearOfRelease = yearOfRelease;
        this.cast = cast;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public ArrayList<String> getCast() {
        return cast;
    }
}
