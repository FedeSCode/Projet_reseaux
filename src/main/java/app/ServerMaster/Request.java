package app.ServerMaster;

public class Request {
    ClientHandler clientHandler ;
    String request;

    public Request(ClientHandler clientHandler, String request) {
        this.clientHandler = clientHandler;
        this.request = request;
    }


}
