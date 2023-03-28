package app.Client;

public class Singup extends Request{
    private String username;
    private String password;

    public Singup(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    String entete() {
        return "NewUser"+" "+username+" "+password;
    }
}
