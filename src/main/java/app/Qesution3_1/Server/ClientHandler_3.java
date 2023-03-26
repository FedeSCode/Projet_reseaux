package app.Qesution3_1.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler_3 extends Thread {
    private final Socket clientSocket;
    private BufferedReader dataReceived;
    private PrintWriter dataSend;

    public ClientHandler_3(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            dataReceived = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            dataSend = new PrintWriter(clientSocket.getOutputStream(), true);
            String readLine;
            while ((readLine = dataReceived.readLine()) != null) {
                String username="";
                String us="";

                if(readLine.startsWith("USER")){
                    StringBuilder text = new StringBuilder();
                    String[] tableData = us.split(" ");
                    String user = tableData[0];
                    for (int i = 0; i < tableData.length; i++) {
                        text.append(tableData[i]).append(' ');
                    }
                    String data = readLine.substring(5);
                    System.out.println(">>>>>>>>>>>>>>>>><data"+data);
                    System.out.println(">>>: "+text);
                    dataSend.println("User ok "+"-"+data+" EndResponse ");

                }
                if (readLine.startsWith("PUBLISH")) {
                    String message = readLine.substring(8);
                    StringBuilder textMessage = new StringBuilder();
                    String[] tableData = message.split(" ");
                    String user = tableData[0];
                    for (int i = 1; i < tableData.length; i++) {
                        textMessage.append(tableData[i]).append(' ');
                    }
                    if(textMessage.toString().equals("")){
                        dataSend.println("Nothing to publish EndResponse " );
                    }else{
                        System.out.println(">>" + user + " Publish: " + textMessage);
                        dataSend.println("Message Published with success!! EndResponse ");
                    }
                }

            }
        }catch(IOException e){
                System.err.println("Error handling client" + e.getMessage());

        } finally{
            try {
                dataReceived.close();
                dataSend.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client connection");
            }
        }

    }
}
