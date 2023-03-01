package app.Serveur;

import java.io.InputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket socket){
        this.clientSocket=socket;
    }

    @Override
    public void run() {
        String textString ="";

        while (true) {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                char charReceive = (char) inputStream.read();
                if (charReceive !=  '\n'){
                    textString +=  charReceive;
                }else{
                    System.out.println("> " + textString);
                    textString = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
   }

}
