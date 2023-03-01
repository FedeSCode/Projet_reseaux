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
            char charRecive = (char) inputStream.read();
        if (charRecive !=  '\n'){
            textString +=  charRecive; 
                    
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
