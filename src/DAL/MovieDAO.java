package DAL;

import BE.Movie;
import DAL.Interfaces.IMovieDAO;
import DAL.Util.FileType;
import DAL.Util.LocalFileHandler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDAO {

    private DatabaseConnector databaseConnector;

    public MovieDAO() {
        databaseConnector = new DatabaseConnector();
    }

    /**
     * Creates a Movie.
     * @param movie The movie to create.
     * @return Returns the newly created movie.
     * @throws Exception If it fails to create the movie.
     */
    @Override
    public Movie createMovie(Movie movie) throws Exception {
        //sql string for creating a movie in Movies table
        String sql = "INSERT INTO Movies (Title, PersonalRating, ImdbRating, MovieFileLink, PictureFileLink, LastView, YearOfRelease, MovieDescription) VALUES (?,?,?,?,?,?,?,?) ;";
        //get connection with database
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            Path relativeCoverPath;

            if(movie.getPictureFileLink().startsWith("https:")){
                relativeCoverPath = !movie.getPictureFileLink().isEmpty() ? LocalFileHandler.saveFileFromApi(movie.getPictureFileLink(), movie.getImdbId()) : null;
                System.out.println("it works");
            }else {
                relativeCoverPath = !movie.getPictureFileLink().isEmpty() ? LocalFileHandler.createLocalFile(movie.getPictureFileLink(), FileType.IMAGE) : null;
            }

            Path relativeMoviePath = !movie.getMovieFileLink().isEmpty() ? LocalFileHandler.createLocalFile(movie.getMovieFileLink(), FileType.MOVIE) : null;


            //gets all variables from movie and saves
            String title = movie.getTitle();
            double pR = movie.getPersonalRating();
            double iR = movie.getImdbRating();
            String movieLink = relativeMoviePath != null ? String.valueOf(relativeMoviePath) : "";
            String pictureLink = relativeCoverPath != null ? String.valueOf(relativeCoverPath) : "";
            Timestamp tS = movie.getLastViewed();
            int yearOfRelease = movie.getYearOfRelease();
            String movieDescription = movie.getMovieDescription();

            //binds all movie variables to statement
            statement.setString(1, title);
            statement.setDouble(2, pR);
            statement.setDouble(3, iR);
            statement.setString(4, movieLink);
            statement.setString(5, pictureLink);
            statement.setTimestamp(6, tS);
            statement.setInt(7, yearOfRelease);
            statement.setString(8, movieDescription);

            statement.executeUpdate();//execute statement

            int id = 0;//variable for the movie id
            ResultSet resultSet = statement.getGeneratedKeys();//gets the movie id back from db
            if (resultSet.next()) {
                id = resultSet.getInt(1);//saves the movie id as id
            }

            //creates the new movie object and sends it back
            Movie generatedMovie =new Movie(id, title, pR, iR, movieLink, pictureLink, tS, yearOfRelease, movieDescription);
            return generatedMovie;

        }catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create movie", e);
        }
    }

    /**
     * Deletes a movie.
     * @param movie The movie to delete.
     * @throws Exception If it fails to delete the movie.
     */
    @Override
    public void deleteMovie(Movie movie) throws Exception {
        String sql = "DELETE FROM Movies WHERE Id = ?;";

        //gets connection to db
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // Bind parameters
            statement.setInt(1, movie.getId());

            // Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete movie", e);
        }

    }

    /**
     * Returns all movies
     * @return A list of all movies.
     * @throws Exception If it fails to retrieve all movies.
     */
    @Override
    public List<Movie> getAllMovies() throws Exception {

        ArrayList<Movie> allMovies = new ArrayList<>(); //list of all movies

        try(Connection connection = databaseConnector.getConnection();
        Statement statement = connection.createStatement()){
            //creates and executes the sql string
            String sql = "SELECT * FROM Movies;";
            ResultSet rs = statement.executeQuery(sql);

            //loop that takes all information from movie table in database and creates movie objects from it
            while(rs.next()) {
                int id = rs.getInt("Id");
                String title =rs.getString("Title");
                double personalRating = rs.getDouble("PersonalRating");
                double imdbRating = rs.getDouble("ImdbRating");
                String movieFileLink = rs.getString("MovieFileLink");

                String pictureFileLink = rs.getString("PictureFileLink");
                if(!Files.exists(Path.of(pictureFileLink))){
                    pictureFileLink = "images/ImageNotFound.jpg";
                }

                Timestamp lastView = rs.getTimestamp("LastView");
                int yearOfRelease = rs.getInt("yearOfRelease");
                String movieDescription = rs.getString("MovieDescription");

                //creates the movie and add it to the list allMovies
                Movie movie = new Movie(id, title,personalRating,imdbRating,movieFileLink,pictureFileLink, lastView, yearOfRelease, movieDescription);
                allMovies.add(movie);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Failed to retrieve movies", e);
        }
        //returns a list of all movies
        return allMovies;
    }

    /**
     * Update/Edit a Movie
     * @param movie, the selected movie to update
     * @throws Exception If it fails to update the movie.
     */
    @Override
    public void updateMovie(Movie movie) throws Exception {
        //UPDATE movies sql string
        String sql = "UPDATE Movies SET  Title=?, PersonalRating=?, ImdbRating=?, MovieFileLink=?, PictureFileLink=?, LastView=?, YearOfRelease=?, movieDescription=? WHERE Id=?;";

        //get connection with database
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            //binds the movie info to statement
            statement.setString(1, movie.getTitle());
            statement.setDouble(2, movie.getPersonalRating());
            statement.setDouble(3, movie.getImdbRating());
            statement.setString(4, movie.getMovieFileLink());
            statement.setString(5, movie.getPictureFileLink());
            statement.setTimestamp(6, movie.getLastViewed());
            statement.setInt(7, movie.getYearOfRelease());
            statement.setString(8, movie.getMovieDescription());
            statement.setInt(9,movie.getId());
            //execute statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to edit the movie", e);
        }
    }

    /**
     * gets a movie from the id
     * @param movieId the movie id
     * @return the found movie object from id.
     * @throws Exception
     */
    @Override
    public Movie getMovieFromId(int movieId) throws Exception {
        //SELECT * FROM Movies WHERE [condition]
        String sql = " SELECT * FROM Movies WHERE Id=?;";

        Movie movie;//movie that is returned from db

        //get connection with database
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1,movieId);//bind the movie id to sql statement


            ResultSet rs = statement.executeQuery();//execute the statement and get movie result

            while (rs.next()) {
                //get all variables from result set
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                double personalRating = rs.getDouble("PersonalRating");
                double imdbRating = rs.getDouble("ImdbRating");
                String movieFileLink = rs.getString("MovieFileLink");
                String pictureFileLink = rs.getString("PictureFileLink");
                Timestamp lastView = rs.getTimestamp("LastView");
                int yearOfRelease = rs.getInt("YearOfRelease");
                String movieDescription = rs.getString("MovieDescription");



                //creates the movie and add it to the list allMovies
                movie = new Movie(id, title, personalRating, imdbRating, movieFileLink, pictureFileLink, lastView, yearOfRelease, movieDescription);
                return movie;//returns the found movie
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to find movie", e);
        }
        return  null;
    }
}
