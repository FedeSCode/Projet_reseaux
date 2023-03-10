package app.Server;

import java.io.IOException;
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
        String serverConnected = "Server_Is_Connected... FinResponse"+'\n';

        OutputStream outputStream = null;
        try {
            outputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write(serverConnected.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true) {
            data = new StringBuilder();
            String[] tableData;
            textString = new StringBuilder();
            String messageReceived="OK FinResponse"+'\n';
            String messageNotReceived="ERROR FinResponse"+'\n';
            String messagePublishReceived="PUBLISH_OK FinResponse"+'\n';
            String commandUnknown = "Command_Not_Recognize FinResponse"+'\n';
            String serverDisconnect = "Server_Disconnected FinResponse"+'\n';



            try {

                InputStream inputStream = clientSocket.getInputStream();

                char charReceive = (char) inputStream.read();

                while (charReceive !=  '\n'){
                    data.append(charReceive);
                    charReceive = (char) inputStream.read();
                }
                tableData= data.toString().split(" ");


                if(tableData[0].equals("PUBLISH")){
                    System.out.println("OK Publish>>: ");
                    //outputStream.write(messagePublishReceived.getBytes());
                    System.out.println("user: "+ tableData[1]);

                    for(int index = 2;index<tableData.length;index++){
                        textString.append(tableData[index]).append(" ");
                    }

                    if(textString.toString().equals("")){
                        System.out.println("ERROR NOTHING TO PUBLISH");
                        outputStream.write(messageNotReceived.getBytes());

                    }else{
                        System.out.println("messageToPublish>>: " + textString);
                        outputStream.write(messageReceived.getBytes());
                    }

                }else{
                    System.out.println("Server disconnected");
                    outputStream.write(serverDisconnect.getBytes());
                    break;
                }







            }catch (Exception e) {
                e.printStackTrace();
            }
        }
   }

}
