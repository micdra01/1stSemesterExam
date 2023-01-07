package BLL.Util;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {
    /**
     * Filter the list of movies using a search query
     * @param searchBase, the list of all movies
     * @param query, the string input used to filter
     * @return a list of movies matching the query in title
     */
    public List<Movie> search(List<Movie> searchBase, String query) {
        List<Movie> searchResult = new ArrayList<>();

        for (Movie movie: searchBase) {
            if(compareToTitle(query, movie)) {
                searchResult.add(movie);
            }
        }

        return searchResult;
    }

    /**
     * Helper method to check if the movie title matches the search query
     * @param query, the string input to search for
     * @param movie, the movie to check
     * @return true if there is a match, false if not.
     */
    private boolean compareToTitle(String query, Movie movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }
}
