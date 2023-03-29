package app.Client;

public class Follow extends Request{
    String authors;

    public Follow(String authors) {
        this.authors = authors;
    }

    @Override
    String entete() {
        return "FOLLOW"+" "+authors;
    }

}
