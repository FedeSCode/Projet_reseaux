package app.ServerMaster;

public class Message {
    private final int id;
    private final User username;
    private String message;
    private boolean republish =false;
    private int idReply = -1;

    public Message(Integer currentId,String message, User username) {
        this.id = currentId;
        this.message = message;
        this.username = username;
    }

    public Message(int id, String message, User username, int idReply) {
        this.id = id;
        this.username = username;
        this.message = message;
        this.idReply = idReply;
    }

    public Message(int id, String message, User username, boolean republish) {
        this.id = id;
        this.username = username;
        this.message = message;
        this.republish = republish;
    }

    public boolean isRepublish() {
        return republish;
    }

    public int getIdReply() {
        return idReply;
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
         if(isRepublish()){
             return message + " "+ "republish: "+ true;
         }
         if(idReply != -1){
             return message +" "+"id reply"+ " "+idReply;
         }
         return message;
    }

}
