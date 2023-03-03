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
        StringBuilder acceptMessage= new StringBuilder();
        String textString;
        textString = "";

        while (true) {

            try {
                InputStream inputStream = clientSocket.getInputStream();
                char charReceive = (char) inputStream.read();
                while (charReceive !=  '\n'){
                    acceptMessage.append(charReceive);
                    charReceive = (char) inputStream.read();
                }
                if(acceptMessage.toString().equals("PUBLISH")){
                    System.out.println("PUBLISH,OK !!!!");

                    while (true) {
                        charReceive = (char) inputStream.read();
                        if (charReceive == '\n') {
                            System.out.println("messageToPublish>>: " + textString);
                            break;
                        } else {
                            textString += charReceive;
                        }
                    }

                }else{
                    acceptMessage = new StringBuilder();
                    textString = "";
                    System.out.println("ERROR");
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
   }

}
