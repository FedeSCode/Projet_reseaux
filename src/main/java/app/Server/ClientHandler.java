package app.Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private BufferedReader dataReceived;
    private PrintWriter dataSend;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            dataReceived = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            dataSend = new PrintWriter(clientSocket.getOutputStream(), true);
            String readLine;
            String userName = "";
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
                    userName = readLine.substring(5);
                    System.out.println(">>User::"+userName);
                    dataSend.println("User ok "+"-"+userName+" EndResponse ");

                }
                if (readLine.startsWith("PUBLISH")) {
                    String message = readLine.substring(8);
                    StringBuilder textMessage = new StringBuilder();
                    String[] tableData = message.split(" ");
                    String user = tableData[0];
                    if(user.equals(userName)){
                        for (int i = 1; i < tableData.length; i++) {
                            textMessage.append(tableData[i]).append(' ');
                        }
                        if(textMessage.toString().equals("")){
                            dataSend.println("Nothing to publish EndResponse " );
                        }else {
                            System.out.println(">>" + userName + " Publish: " + textMessage);
                            dataSend.println("Message Published with success!! EndResponse ");
                        }
                    }
                }
                // @todo : apres le readLine.startsWith("RCV_IDS")
                if (readLine.startsWith("RCV_IDS")) {
                    String message = readLine.substring(8);
                    StringBuilder textMessage = new StringBuilder();
                    String[] tableData = message.split(" ");
                    String user = tableData[0];
                    if(user.equals(userName)){
                        for (int i = 1; i < tableData.length; i++) {
                            textMessage.append(tableData[i]).append(' ');
                        }
                        if(textMessage.toString().equals("")){
                            dataSend.println("Nothing to publish EndResponse " );
                        }else {
                            System.out.println(">>" + userName + " Publish: " + textMessage);
                            dataSend.println("Message Published with success!! EndResponse ");
                        }
                    }
                }
                // @todo : apres le readLine.startsWith("RCV_MSG")
                if (readLine.startsWith("RCV_MSG")) {
                    String message = readLine.substring(8);
                    StringBuilder textMessage = new StringBuilder();
                    String[] tableData = message.split(" ");
                    String user = tableData[0];
                    if(user.equals(userName)){
                        for (int i = 1; i < tableData.length; i++) {
                            textMessage.append(tableData[i]).append(' ');
                        }
                        if(textMessage.toString().equals("")){
                            dataSend.println("Nothing to publish EndResponse " );
                        }else {
                            System.out.println(">>" + userName + " Publish: " + textMessage);
                            dataSend.println("Message Published with success!! EndResponse ");
                        }
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
