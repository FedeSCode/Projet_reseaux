package app.Client;

public class Publisher {
    private String user;
    private String textMessageToSend;

    public Publisher(String user, String textMessageToSend) {
        this.user = '@'+user;
        this.textMessageToSend = textMessageToSend;
    }

    public String messageToSend(String message){
        return message;
    }







}
