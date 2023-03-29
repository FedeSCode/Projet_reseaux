package app.Client;

public class Reply extends Request{
    private final String idMessage;
    private final String username;

    public Reply(String idMessage, String username,String message) {
        this.idMessage = idMessage;
        this.username = username;
        super.message = message;

    }
    @Override
    String entete() {
        return "REPLY"+" "+"author:"+" "+username+" "+"reply_to_id:"+" "+idMessage;
    }
}
