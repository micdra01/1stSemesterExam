package BLL;

import BE.Movie;
import BLL.Interfaces.IMovieManager;
import DAL.Interfaces.IMovieDAO;
import DAL.MovieDAO;

import java.util.List;

public class MovieManager implements IMovieManager {

    private IMovieDAO databaseAccess;

    public MovieManager() {
        databaseAccess = new MovieDAO();
    }

    @Override
    public Movie createMovie(Movie movie) throws Exception {
        return databaseAccess.createMovie(movie) ;
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        databaseAccess.deleteMovie(movie);
    }

    @Override
    public List<Movie> getAllMovies() throws Exception {
        return databaseAccess.getAllMovies();
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
        databaseAccess.updateMovie(movie);
    }

    @Override
    public List<Movie> search(String query) throws Exception {
        return null;
    }
}
