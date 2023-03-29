package app.Client;

public class Connect extends Request{
    String user;

    public Connect(String user) {
        this.user = user;
    }

    @Override
    String entete() {
        return "CONNECT"+" "+"user:"+" "+user;
    }
}
