package app.Client;

public class Publisher {
    private String idUser;
    private String textMessageToSend;

    public Publisher(String user, String textMessageToSend) {
        this.idUser = '@'+user;
        this.textMessageToSend = textMessageToSend;
    }

    public String messageToSend(String message){
        return message;
    }










}
