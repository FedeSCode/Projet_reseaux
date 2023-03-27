package app.Client;

import app.Server.Message;

public abstract class Request {
    abstract String entete();
    String message;

    String sendRequest(){
        if (message== null){
            return entete();
        }
        return entete() + " " + message +" ";
    };

}
