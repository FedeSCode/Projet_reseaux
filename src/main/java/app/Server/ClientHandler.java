package app.Server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket socket){
        this.clientSocket=socket;
    }

    @Override
    public void run() {
        String data;
        String textString;
        String messageRecived;

        while (true) {
            data = "";
            String[] tableData;
            textString = "";

            try {
                InputStream inputStream = clientSocket.getInputStream();
                char charReceive = (char) inputStream.read();

                while (charReceive !=  '\n'){
                    data += charReceive;
                    charReceive = (char) inputStream.read();
                }
                tableData= data.split(" ");

                if(tableData[0].equals("PUBLISH")){
                    System.out.println("PUBLISH,OK !!!!");
                    System.out.println(tableData[1]);

                    for(int index = 2;index<tableData.length;index++){
                        textString += tableData[index] + " ";
                    }
                    System.out.println("messageToPublish>>: " + textString);

                }else{
                    System.out.println("ERROR NOT PUBLISH");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
   }

}
