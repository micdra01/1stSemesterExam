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
    ArrayList<ImdbInfo> getSearchResultFromApi(String searchWord) throws Exception;


    /**
     *
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    ArrayList<String> getMovieCategoriesFromApi(String imdbId) throws IOException, InterruptedException;

    /**
     *
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    String getMovieDescriptionFromImdbId(String imdbId) throws IOException, InterruptedException;


    /**
     * gets the rating from imdb api
     * @param imdbId
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    String getImdbRatingFromApi(String imdbId) throws IOException, InterruptedException;



}
