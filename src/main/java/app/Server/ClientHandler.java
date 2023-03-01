package app.Server;

import java.io.InputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket socket){
        this.clientSocket=socket;
    }

    @Override
    public void run() {
        String acceptMessage="";
        String textString ="";

        while (true) {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                char charReceive = (char) inputStream.read();
                System.out.println("PUBLISH?: ");
                while (charReceive !=  '\n'){
                    acceptMessage +=  charReceive;
                    charReceive = (char) inputStream.read();
                }
                if(acceptMessage.equals("PUBLISH")){
                    System.out.println("ON A COMPRIS TON PUBLISH!!!!");
                    textString += charReceive;
                }
                else{
                    System.out.println("ERROR");
                    System.out.println("> " + textString);
                    textString = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
   }

}
