package GUI.Models;

import BE.ImdbInfo;
import BLL.ImdbManager;
import BLL.Interfaces.IImdbManager;

import java.io.IOException;
import java.util.ArrayList;

public class ImdbInfoModel {

    private IImdbManager imdbManager;

    public ImdbInfoModel(){
        imdbManager = new ImdbManager();
    }


    public ArrayList<ImdbInfo> getSearchResultFromApi(String searchWord) throws IOException, InterruptedException {
        return imdbManager.getSearchResultFromApi(searchWord);
    }

    public ArrayList<String> getMovieCategoriesFromApi(String imdbId) throws IOException, InterruptedException {
        return imdbManager.getMovieCategoriesFromApi(imdbId);
    }


    public String getMovieDescriptionFromImdbId(String imdbId) throws IOException, InterruptedException {
        return imdbManager.getMovieDescriptionFromImdbId(imdbId);
    }


    public String getImdbRatingFromApi(String imdbId) throws IOException, InterruptedException {
        return imdbManager.getImdbRatingFromApi(imdbId);
    }
}
