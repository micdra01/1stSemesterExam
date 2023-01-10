package BE;

import java.sql.Timestamp;

public class Movie {

    private int id;

    private double personalRating, imdbRating;
    private String title, movieFileLink, pictureFileLink, category;
    private Timestamp lastViewed;


    /**
     * constructor with id, should be used a standard
     * @param id
     * @param title
     * @param personalRating
     * @param imdbRating
     * @param movieFileLink
     * @param pictureFileLink
     * @param lastViewed
     */
    public Movie(int id, String title, double personalRating, double imdbRating, String movieFileLink,
                 String pictureFileLink, Timestamp lastViewed) {
        this.id = id;
        this.title = title;
        this.personalRating = personalRating;
        this.imdbRating = imdbRating;
        this.movieFileLink = movieFileLink;
        this.pictureFileLink = pictureFileLink;
        this.lastViewed = lastViewed;
    }

    /**
     * constructor without id
     * used for making a movie, so we can send the new movie down to db where it gets the id.
     * @param title
     * @param personalRating
     * @param imdbRating
     * @param movieFileLink
     * @param pictureFileLink
     * @param lastViewed
     */
    public Movie(String title, double personalRating, double imdbRating, String movieFileLink,
                 String pictureFileLink, Timestamp lastViewed) {
        this.title = title;
        this.personalRating = personalRating;
        this.imdbRating = imdbRating;
        this.movieFileLink = movieFileLink;
        this.pictureFileLink = pictureFileLink;
        this.lastViewed = lastViewed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(double personalRating) {
        this.personalRating = personalRating;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getMovieFileLink() {
        return movieFileLink;
    }

    public void setMovieFileLink(String movieFileLink) {
        this.movieFileLink = movieFileLink;
    }

    public String getPictureFileLink() {
        pictureFileLink = pictureFileLink.replaceAll("resources//", "");
        pictureFileLink = pictureFileLink.replaceAll("resources\\\\", "");
        return pictureFileLink;
    }

    public void setPictureFileLink(String pictureFileLink) {
        this.pictureFileLink = pictureFileLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Timestamp getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(Timestamp lastViewed) {
        this.lastViewed = lastViewed;
    }


}
