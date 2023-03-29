package app.Client;

public class Unsubscribe extends Request{
    String user = null;
    String tag;

    public Unsubscribe(String user, String tag) {
        this.user = user;
        this.tag = tag;
    }
    
    @Override
    String entete() {
        if(user==null){
            return "UNSUBSCRIBE"+" "+"tag:"+" "+tag;
        }
        return "UNSUBSCRIBE"+" "+ "author:"+" "+user;
    }
}
