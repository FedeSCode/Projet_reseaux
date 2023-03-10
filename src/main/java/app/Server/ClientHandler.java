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
        StringBuilder data;
        StringBuilder textString;


        while (true) {
            data = new StringBuilder();
            String[] tableData;
            textString = new StringBuilder();
            String messageReceived="OK";
            String messageNotReceived="ERROR";

            try {
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                char charReceive = (char) inputStream.read();

                while (charReceive !=  '\n'){
                    data.append(charReceive);
                    charReceive = (char) inputStream.read();
                }
                tableData= data.toString().split(" ");

                if(tableData[0].equals("PUBLISH")){
//                  System.out.println("PUBLISH,OK !!!!");
                    System.out.println("user: "+ tableData[1]);
                    for(int index = 2;index<tableData.length;index++){
                        textString.append(tableData[index]).append(" ");
                    }
                    System.out.println("messageToPublish>>: " + textString);

                    outputStream.write(messageReceived.getBytes());
                }else{
                    System.out.println("ERROR NOT PUBLISH");
                    outputStream.write(messageNotReceived.getBytes());
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
   }

}
