package BE;

import java.sql.Timestamp;

public class Movie {

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    private int id, yearOfRelease;

    private double personalRating, imdbRating;

    private String imdbId, title, movieFileLink, pictureFileLink, category, TopCast, movieDescription;

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


    public void setTopCast(String topCast) {
        TopCast = topCast;
    }

    public String getTopCast() {
        return TopCast;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title.substring(0,1).toUpperCase() + title.substring(1);
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

    public String getMovieFileLink() {
        return movieFileLink;
    }

    public String getPictureFileLink() {return pictureFileLink.replaceAll("resources\\\\", "");}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLastViewed(Timestamp lastViewed) {
        this.lastViewed = lastViewed;
    }

    public Timestamp getLastViewed() {
        return lastViewed;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public String getMovieDescription() {
        return movieDescription;
    }
}
