package BE;

public class ImdbInfo {

    private String imdbId;

    private String title;

    private String pictureLink;

    private String yearOfRelase;

    public ImdbInfo(String imdbId, String title, String pictureLink, String yearOfRelase){
        this.imdbId = imdbId;
        this.title = title;
        this.pictureLink = pictureLink;
        this.yearOfRelase = yearOfRelase;
        //prints movie info in console
        System.out.println(imdbId);
        System.out.println(title);
        System.out.println(yearOfRelase);
        System.out.println(pictureLink);
    }
}
