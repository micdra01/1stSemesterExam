package DAL;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class ImdbApi {

    public ImdbApi() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://1mdb-data-searching.p.rapidapi.com/om?s=far%20til"))
                .header("X-RapidAPI-Key", "758854346fmshb2e7f684695dca5p1c89b6jsn2aa78bdfb8af")
                .header("X-RapidAPI-Host", "1mdb-data-searching.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        //gets the title of the movie
        String segments[] = response.body().split(":");

        System.out.println(response.body());

        System.out.println(getResultsFromTitleSearch(segments[2]));
        System.out.println(getResultsFromTitleSearch(segments[3]));
        System.out.println(getResultsFromTitleSearch(segments[5]));

       //getResultsFromTitleSearch(segments[3]);


    }


    public String getResultsFromTitleSearch(String str)
    {

        String yearTrim = str.substring(0, str.indexOf(","));

        // Creating a StringBuilder object
        StringBuilder sb = new StringBuilder(yearTrim);

        // Removing the last character
        // of a string
        sb.deleteCharAt(yearTrim.length() - 1);

        // Removing the first character
        // of a string
        sb.deleteCharAt(0);

        // Converting StringBuilder into a string
        // and return the modified string
        return sb.toString();
    }
}
