package app.Client;

import java.io.BufferedReader;

public class Rcv_msg extends Request{
    private String msgId;

    public Rcv_msg(String msgId) {
        this.msgId = msgId;
    }

    @Override
    String entete() {
        return "RCV_MSG " + "msg_id:"+ this.msgId;
    }

}
