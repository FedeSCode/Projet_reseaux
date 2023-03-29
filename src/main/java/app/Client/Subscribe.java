package app.Client;

public class Subscribe extends Request{
    String user = null;
    String tag;

    public Subscribe(String user, String tag) {
        this.user = user;
        this.tag = tag;
    }

    @Override
    String entete() {
        if(user==null){
            return "SUBSCRIBE"+" "+"tag:"+" "+tag;
        }
        return "SUBSCRIBE"+" "+ "author:"+" "+user;
    }
}
