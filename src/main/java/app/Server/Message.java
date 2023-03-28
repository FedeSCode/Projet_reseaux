package app.Server;
import app.Server.User;

public class Message {
    private final int id;
    private User username;
    private String message;

    public Message(Integer currentId,String message, User username) {
        this.id = currentId;
        this.message = message;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUsername() {
        return username;
    }

    public String getStrUser(){
        return  username.toString();
    }

    public int getId() {
        return id;
    }


    public boolean contains(String tag){
        return message.contains(tag);
    }

    @Override
    public String toString() {
        return message;
    }

}
