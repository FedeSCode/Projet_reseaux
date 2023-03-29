package app.Client;

public class Republish extends Request{
    String messageId;
    String author;

    public Republish(String author,String messageId) {
        this.author = author;
        this.messageId = messageId;
    }

    @Override
    String entete() {
        return "REPUBLISH"+" "+"author:"+" "+author+" "+"msg_id:"+" "+ messageId;
    }
}
