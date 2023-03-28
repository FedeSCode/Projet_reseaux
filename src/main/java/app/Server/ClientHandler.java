package app.Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
            MessageDb messageDb = new MessageDb();
            String readLine;
            String userName = "";



            while ((readLine = dataReceived.readLine()) != null) {
                String us="";

                if(readLine.startsWith("USER")){
                    StringBuilder text = new StringBuilder();
                    String[] tableData = us.split(" ");
                    String user = tableData[0];
                    for (String tableDatum : tableData) {
                        text.append(tableDatum).append(' ');
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

                            //DataBase
                            int value =messageDb.NewMessage(newMessage);
                            if(value == 0){
                                System.out.println("Failed to write message write in data base");
                            }else{
                                System.out.println("Message write in data base");
                            }

                            /*Server response*/
                            ServerTCP.userToMessagesMap.get(new User(userName)).add(newMessage);
                            ServerTCP.messagesMap.put(newMessage.getId(),newMessage);
                            System.out.println("ServerTCP.userToMessagesMap: "+ServerTCP.userToMessagesMap);
                            System.out.println("ServerTCP.userToMessagesMap: "+ServerTCP.messagesMap);

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
                        System.out.println("since_id messagesList: "+messagesList);
                    }

                    if(tag != null){
                        ArrayList<Message> newMessagesList = new ArrayList<>();
                        for (Message message : messagesList){
                            if(message.contains(tag)){
                                newMessagesList.add(message);
                            }
                        }
                        messagesList = newMessagesList;
                        System.out.println("tag messagesList: "+messagesList);
                    }
                    
                    messagesList.sort(Comparator.comparing(Message::getId).reversed());
                    StringBuilder output = new StringBuilder("MSG_IDS $");

                    for(int index = 0; index < limit && index < messagesList.size() ; index++){
                        output.append(messagesList.get(index).getId()).append("$");
                    }
                    dataSend.println(output);
                    System.out.println(output);
                    


                }
                else if (readLine.startsWith("RCV_MSG")) {
                    String data = readLine.substring(8);
                    String[] tableData = data.split(":");
                    List<Message> messagesList = new ArrayList<>(ServerTCP.messagesMap.values());
                    int msg_id = Integer.parseInt(tableData[1]);
                    String response = null;
                    if(msg_id != -1) {
                         response = "user that publish: " +messagesList.get(msg_id).getUsername() +"$" +
                                    "message: "+messagesList.get(msg_id).getMessage();
                    }

                    dataSend.println(response);
                    System.out.println("End RCV_MSG");
                }

                // @todo : apres le readLine.startsWith("REPLY")
                else if (readLine.startsWith("REPLY")) {
                    String message = readLine.substring(6);
                    StringBuilder textMessage = new StringBuilder();
                    String[] tableData = message.split("");

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
