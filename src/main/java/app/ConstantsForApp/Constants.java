package app.ConstantsForApp;

public class Constants {


    final String url;
    final String username;
    final String password;
    final String separator;
    final String connectionToServer;
    final String fakeBook;
    final String disconnectedFromServer;
    final String serverOn;


    public Constants(String url, String username, String password,
                     String separator, String connectionToServer, String fakeBook,
                     String disconnectedFromServer, String serverOn) {

        this.url = "jdbc:mysql://localhost:3306/project";
        this.username = "root";
        this.password = "toor";
        this.separator = "--------------------------------------------------------------------------------------";
        this.connectionToServer = "-----------------------------------Connected to Server--------------------------------";
        this.fakeBook  = "-----------------------------------FakeBook-------------------------------------------";
        this.disconnectedFromServer = "------------------------------Disconnected From Server--------------------------------";
        this.serverOn = "-----------------------------------Sever is on----------------------------------------";
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSeparator() {
        return separator;
    }

    public String getConnectionToServer() {
        return connectionToServer;
    }

    public String getFakeBook() {
        return fakeBook;
    }

    public String getDisconnectedFromServer() {
        return disconnectedFromServer;
    }

    public String getServerOn() {
        return serverOn;
    }
}
