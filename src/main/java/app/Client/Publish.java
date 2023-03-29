package app.Client;

public class Publish extends Request{
    String username;

    public Publish(String username , String message) {
        this.username = username;
        super.message = message;
    }

    @Override
    String entete() {
        return "PUBLISH " + username;
    }

}
