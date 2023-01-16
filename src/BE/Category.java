package BE;

public class Category {
    private String title;
    private int id;

    public Category(int id, String title){
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
