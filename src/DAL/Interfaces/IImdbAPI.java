package DAL.Interfaces;

import BE.ImdbInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IImdbAPI {

    /**
     * gets the search results from the search word.
     * @param searchWord
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    ArrayList<ImdbInfo> getSearchResultFromApi(String searchWord) throws IOException, InterruptedException;


    /**
     * todo should get the categories from a specific movie and return them as a list of strings
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    List<String> getMovieCategoriesFromApi(String imdbId) throws IOException, InterruptedException;

    /**
     * todo should get a description of the movie from the api using the movie id.
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    String getMovieDescriptionFromImdbId(String imdbId) throws IOException, InterruptedException;


    String getImdbRatingFromApi(String imdbId) throws IOException, InterruptedException;



}