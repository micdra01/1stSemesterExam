package BE;

import java.sql.Timestamp;

public class Movie {

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    private String imdbId;
    private int id;

    private double personalRating, imdbRating;
    private String title, movieFileLink, pictureFileLink, category;
    private Timestamp lastViewed;

    private int yearOfRelease;

    private String movieDescription;

    public String getTopCast() {
        return TopCast;
    }

    public void setTopCast(String topCast) {
        TopCast = topCast;
    }

    public String TopCast;


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
                 String pictureFileLink, Timestamp lastViewed, int yearOfRelease, String movieDescription) {
        this.id = id;
        this.title = title;
        this.personalRating = personalRating;
        this.imdbRating = imdbRating;
        this.movieFileLink = movieFileLink;
        this.pictureFileLink = pictureFileLink;
        this.lastViewed = lastViewed;
        this.yearOfRelease = yearOfRelease;
        this.movieDescription = movieDescription;
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
    public Movie(String title, double personalRating, double imdbRating, String movieFileLink, String pictureFileLink, Timestamp lastViewed, int yearOfRelease, String movieDescription) {
        this.title = title;
        this.personalRating = personalRating;
        this.imdbRating = imdbRating;
        this.movieFileLink = movieFileLink;
        this.pictureFileLink = pictureFileLink;
        this.lastViewed = lastViewed;
        this.yearOfRelease = yearOfRelease;
        this.movieDescription = movieDescription;
    }

    public Movie(String title, double personalRating, double imdbRating, String movieFileLink, String pictureFileLink, Timestamp lastViewed, int yearOfRelease, String movieDescription, String topCast) {
        this.title = title;
        this.personalRating = personalRating;
        this.imdbRating = imdbRating;
        this.movieFileLink = movieFileLink;
        this.pictureFileLink = pictureFileLink;
        this.lastViewed = lastViewed;
        this.yearOfRelease = yearOfRelease;
        this.movieDescription = movieDescription;
        this.TopCast = topCast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        String cap = title.substring(0,1).toUpperCase() + title.substring(1);
        return cap;
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
       // pictureFileLink = pictureFileLink.replaceAll("resources//", "");

        return pictureFileLink.replaceAll("resources\\\\", "");
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

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }
}
