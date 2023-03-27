package app.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
                String us="";

                if(readLine.startsWith("USER")){
                    StringBuilder text = new StringBuilder();
                    String[] tableData = us.split(" ");
                    String user = tableData[0];
                    for (int i = 0; i < tableData.length; i++) {
                        text.append(tableData[i]).append(' ');
                    }
                    userName = readLine.substring(5);
                    System.out.println(">User::"+userName);
                    dataSend.println("User ok "+"-"+userName);
                    ServerTCP.userToMessagesMap.put(new User(userName),new ArrayList<>());
                }
                else if (readLine.startsWith("PUBLISH")) {
                    String message = readLine.substring(8);
                    StringBuilder textMessage = new StringBuilder();
                    String[] tableData = message.split(" ");
                    /*tableData[0] = userName*/
                    if(tableData[0].equals(userName)){
                        for (int i = 1; i < tableData.length; i++) {
                            textMessage.append(tableData[i]).append(' ');
                        }
                        if(textMessage.toString().equals("")){
                            dataSend.println("Nothing to publish" );
                        }else {
                            User user = new User(userName);
                            Message newMessage = new Message(textMessage.toString(),user);

                            ServerTCP.userToMessagesMap.get(new User(userName)).add(newMessage);
                            ServerTCP.messagesMap.put(newMessage.getId(),newMessage);
                            System.out.println("ServerTCP.userToMessagesMap: "+ServerTCP.userToMessagesMap);
                            System.out.println(">>" + userName + " Publish: " + textMessage);
                            dataSend.println("Message Published with success!!");
                        }
                    }
                }
                else if (readLine.startsWith("RCV_IDS")) {
                    String data = readLine.substring(8);
                    String[] tableData = data.split(" ");
                    String author =null;
                    String tag =null;
                    int since_id =-1;
                    int limit = 5;
                    List<Message> messagesList =null;
                    for(String filter: tableData){
                        String[] filters = filter.split(":");
                        switch (filters[0]) {
                            case "author" -> author = filters[1];
                            case "tag" -> tag = filters[1].substring(1);
                            case "since_id" -> since_id = Integer.parseInt(filters[1]);
                            case "limit" -> limit = Integer.parseInt(filters[1]);
                        }
                        System.out.println("list being filtered...");
                    }
                    if (author != null){
                        messagesList = ServerTCP.userToMessagesMap.get(new User(author));
                        if(messagesList == null){
                            messagesList = new ArrayList<>();
                        }

                    }else{
                        messagesList = new ArrayList<>(ServerTCP.messagesMap.values());
                    }
                    System.out.println(messagesList);

                    if(since_id != -1){
                        ArrayList<Message> newMessagesList = new ArrayList<>();
                        for (Message message : messagesList){
                            if(message.getId() > since_id){
                                newMessagesList.add(message);
                            }
                        }
                        messagesList = newMessagesList;
                        System.out.println(messagesList);
                    }

                    if(tag != null){
                        ArrayList<Message> newMessagesList = new ArrayList<>();
                        for (Message message : messagesList){
                            if(message.contains(tag)){
                                newMessagesList.add(message);
                            }
                        }
                        messagesList = newMessagesList;
                        System.out.println(messagesList);
                    }
                    
                    messagesList.sort(Comparator.comparing(Message::getId).reversed());
                    StringBuilder output = new StringBuilder("MSG_IDS\n");

                    for(int index = 0; index < limit && index < messagesList.size() ; index++){
                        output.append(messagesList.get(index).getId()).append("\n");
                    }
                    dataSend.println(output + "$");
                    System.out.println(output);
                    


                }
                // @todo : apres le readLine.startsWith("RCV_MSG")
                else if (readLine.startsWith("RCV_MSG")) {
                    String message = readLine.substring(8);
                    StringBuilder textMessage = new StringBuilder();
                    String[] tableData = message.split(" ");

                }
                else if (readLine.startsWith("REPLY")) {
                    String message = readLine.substring(6);
                    StringBuilder textMessage = new StringBuilder();
                    String[] tableData = message.split(" ");

                }
                else {
                    dataSend.println("BAD REQUEST!!");
                    dataSend.println("ERROR:" + readLine);
                    System.out.println("ERROR:" + readLine);
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

        System.out.println("fermeture client");

    }
}
