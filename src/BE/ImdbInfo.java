package BE;

import java.util.ArrayList;

public class ImdbInfo {

    private String imdbId;

    private String title;

    private String pictureLink;

    private String yearOfRelease;

    private ArrayList<String> cast = new ArrayList<>();

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

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
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

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(String yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }
}
