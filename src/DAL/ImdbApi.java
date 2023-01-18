package DAL;

import BE.ImdbInfo;
import DAL.Interfaces.IImdbAPI;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class ImdbApi implements IImdbAPI {

    private static final String PROP_FILE = "data/database.settings";
    String apiKey;

    public ImdbApi() throws Exception {
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(new File(PROP_FILE)));
        apiKey = databaseProperties.getProperty("ApiKey");
    }

    /**
     * gets all search result from imdb api
     * makes an object with info
     */
   public ArrayList<ImdbInfo> getSearchResultFromApi(String searchWord) throws Exception {

       String searchWordString = searchWord.replace(" ", "%20");

       HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create("https://imdb8.p.rapidapi.com/title/find?q=" + searchWordString))
               .header("X-RapidAPI-Key", apiKey)
               .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
               .method("GET", HttpRequest.BodyPublishers.noBody())
               .build();

       HttpResponse<String> response = null;
       try {
           response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
       } catch (Exception e) {
           throw new Exception("Failed to retrieve search result from IMDB", e);
       }
       return getInfoFromResultString(response);
   }

    /**
     * gets a list of categories from the chosen movie from imdb api
     */
    @Override
    public ArrayList<String> getMovieCategoriesFromApi(String imdbId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/get-genres?tconst=" + imdbId))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new Exception("Failed to retrieve movie categories from IMDB", e);
        }

        return getCategoryFromApiString(response);

    }

    /**
     * gets the description from a movie from imdb api
     */
    @Override
    public String getMovieDescriptionFromImdbId(String imdbId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=" + imdbId + "&currentCountry=DK"))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new Exception("Failed to retrieve movie description from IMDB", e);
        }
        String responseString = response.body();
        String description ="no description";
        if(responseString.contains(",\"text\":\"")) {
            String descriptionRaw = responseString.substring(responseString.lastIndexOf(",\"text\":\"") + 9);
            description = descriptionRaw.substring(0, descriptionRaw.indexOf("\"}"));
        }
        return description;
    }

    /**
     * gets the rating from imdb api
     */
    @Override
    public String getImdbRatingFromApi(String imdbId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/get-ratings?tconst=" + imdbId))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new Exception("Failed to retrieve movie rating from IMDB", e);
        }

        String responseString = response.body();
        if (responseString.contains(",\"rating\":")) {
            String ratingRaw = responseString.substring(responseString.lastIndexOf(",\"rating\":") + 10);
            return ratingRaw.substring(0, ratingRaw.indexOf(",\""));
        }
        return "1";
    }

    /**
     * gets the categories from imdb api
     */
    private ArrayList<String> getCategoryFromApiString(HttpResponse<String> response){
        String categoryRaw = response.body();
        String categoryWithoutStart = categoryRaw.substring(categoryRaw.lastIndexOf("[\"") + 2);
        String categoryWithoutEnd = categoryWithoutStart.substring(0, categoryWithoutStart.indexOf("\"]"));

        //adds each category to list
        String[] segments = categoryWithoutEnd.split("\",\"");
        return new ArrayList<>(Arrays.asList(segments));
    }

    /**
     * gets all the information from each movie in the result string, and creates an object from it.
     */
    private ArrayList<ImdbInfo> getInfoFromResultString(HttpResponse<String> response){
        String[] segments = response.body().split("\"id\":\"/title/");
        ArrayList<ImdbInfo> resultList = new ArrayList<>();

        for (String httpResult : segments) {
            //gets a string for each object on the list
            //get all the information from each movie from string
            if (httpResult.contains("\"titleType\":\"movie\"")) {//only selects movies from the list

                //movie id in api database
                String movieIdRaw = httpResult.substring(httpResult.lastIndexOf("\"id\":\"/title/") + 1);
                String movieId = movieIdRaw.substring(0, movieIdRaw.indexOf("/"));
                //string title
                String titleRaw = httpResult.substring(httpResult.lastIndexOf("title\":\"") + 8);
                String title = titleRaw.substring(0, titleRaw.indexOf("\","));
                //picture link
                String picture = "";
                if (httpResult.contains("url\":\"")) {
                    String pictureLinkRaw = httpResult.substring(httpResult.lastIndexOf("url\":\"") + 6);
                    picture = pictureLinkRaw.substring(0, pictureLinkRaw.indexOf("\","));
                }
                //year of release
                String yearOfReleaseRaw = httpResult.substring(httpResult.lastIndexOf("\",\"year\":") + 9);
                String yearOfRelease = yearOfReleaseRaw.substring(0, 4);
                //gets the names from cast
                String[] castStringsRaw = httpResult.split("\"name\":\"");
                ArrayList<String> castList = new ArrayList<>();
                for (int j = 1; castStringsRaw.length > j; j++) {
                    String castString = castStringsRaw[j].substring(0, castStringsRaw[j].indexOf("\","));
                    castList.add(castString);
                }

                //makes imdbInfo object from response info
                ImdbInfo imdbInfo = new ImdbInfo(movieId, title, picture, yearOfRelease, castList);
                resultList.add(imdbInfo);
            }
        }
        return resultList;
    }
}
