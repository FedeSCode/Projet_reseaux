package app.Client;

public class Signup extends Request{
    private String username;
    private String password;

    public Signup(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    String entete() {
        return "NewUser"+" "+username+" "+password;
    }
}
