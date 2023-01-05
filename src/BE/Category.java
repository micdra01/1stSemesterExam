package BE;


import java.util.ArrayList;

public class Category {

    private String title;

    private int id;
    private ArrayList<Movie> movieList;

    public Category(int id, String title, ArrayList<Movie> movieList){
        this.id = id;
        this.title = title;
        this.movieList = movieList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }
}
