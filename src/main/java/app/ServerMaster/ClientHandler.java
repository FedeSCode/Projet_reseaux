package app.ServerMaster;

import java.io.*;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private BufferedReader dataReceived;
    private PrintWriter dataSend;
    private User userName;


    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {
            dataReceived = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            dataSend = new PrintWriter(clientSocket.getOutputStream(), true);
            MessageDb messageDb = new MessageDb();
            UserDb userDb = new UserDb();

            String readLine;
            while ((readLine = dataReceived.readLine()) != null) {
                System.out.println(readLine);
                // @done
                /*if(readLine.startsWith("USER")){
                    userName = readLine.substring(5);
                    System.out.println(">User::"+userName);
                    dataSend.println("User ok "+"-"+userName);

//                    ServerTCP.userToMessagesMap.put(new User(userDb.getLastUserId(),userName),new ArrayList<>());
                }
                else*/
                    // @done
                 if (readLine.startsWith("PUBLISH")) {
                    String data = readLine.substring(8);
                    StringBuilder textMessage = new StringBuilder();
                    String[] tableData = data.split(" ");
                    /*tableData[0] = userName*/

                    if(tableData[0].equals(userName.getUsername())){

                        for (int i = 1; i < tableData.length; i++) {
                            textMessage.append(tableData[i]).append(' ');
                        }
                        if(textMessage.toString().equals("")){
                            dataSend.println("Nothing to publish" );
                        }else {
                            User user = userName;
                            Message newMessage = new Message(messageDb.getLastMessageId(),textMessage.toString(),user);

                            //DataBase
                            int value =messageDb.NewMessage(newMessage);
                            if(value == 0){

                                /*Server response*/
                                System.out.println("Failed to write message write in data base");
                                dataSend.println("ERROR: Message not publish");
                            }else{
                                /*Server response*/
                                ServerMasterTCP.userToMessagesMap.get(userName).add(newMessage);
                                ServerMasterTCP.messagesMap.put(newMessage.getId(),newMessage);
                                addToQueue(newMessage,userName);
                                System.out.println(">>>>ServerTCP.userToMessagesMap: "+ ServerMasterTCP.userToMessagesMap);
                                System.out.println(">>>>ServerTCP.messagesMap: "+ ServerMasterTCP.messagesMap);
                                System.out.println("Message write in data base");
                                System.out.println(">>" + userName + " Publish: " + textMessage);
                                dataSend.println("Message Published with success!!");
                            }
                        }
                    }else{
                        System.out.println("ERROR NO ERROR");
                        dataSend.println("ERROR NO ERROR");
                    }
                }
                // @done
                else if (readLine.startsWith("RCV_IDS")) {
                    String data = readLine.substring(8);
                    String[] tableData = data.split(" ");
                    String author =null;
                    String tag =null;
                    int since_id =-1;
                    int limit = 5;
                    List<Message> messagesList;
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
                        messagesList = ServerMasterTCP.userToMessagesMap.get(new User(userDb.getLastUserId(),author));
                        if(messagesList == null){
                            messagesList = new ArrayList<>();
                        }

                    }else{
                        messagesList = new ArrayList<>(ServerMasterTCP.messagesMap.values());
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
                // @done
                else if (readLine.startsWith("RCV_MSG")) {
                    String data = readLine.substring(8);
                    String[] tableData = data.split(":");
                    int msg_id = Integer.parseInt(tableData[1]);
                    String response;

                    if(msg_id != -1) {
                        Message message = ServerMasterTCP.messagesMap.get(msg_id);
                         response = "user that publish: " +message.getUsername() +"$" +
                                    "message: "+message.getMessage();

                        if(message.isRepublish()){
                            response+= " "+ "republished: "+ true;
                        }
                        if(message.getIdReply() != -1){
                            response+=" "+"reply_to_id:"+" "+message.getIdReply();
                        }
                        dataSend.println(response);
                        System.out.println("OK");
                    }
                    else {
                        response = "ERROR: RCV_MSG ";
                        dataSend.println(response);
                        System.out.println("ERROR: RCV_MSG");
                    }


                }

                // @done
                else if (readLine.startsWith("REPLY")) {
                    String data = readLine.substring(6);
                    String[] tableData = data.split(" ");
                    StringBuilder message = new StringBuilder();
                    int msg_id = Integer.parseInt(tableData[3]);
                    String author = tableData[1];
                    String response;
                    System.out.println("length"+tableData.length);
                    for (int i=4; i<tableData.length;i++){
                        message.append(tableData[i]).append(" ");
                    }

                    System.out.println("ici>>>>>>>>>>>>>>>>>:author: "+author+" message Id: "+msg_id);
                    if(msg_id != -1) {
                        response ="user that reply"+"$"+author+"$"+
                                "user that publish:"+"$"+ ServerMasterTCP.messagesMap.get(msg_id).getUsername() +"$"+
                                "message origin:"+"$"+ ServerMasterTCP.messagesMap.get(msg_id).getMessage()+
                                "response:"+"$"+message+"$"+
                                "id reply:"+"$"+msg_id+"$"+"OK";
                        User user = new User(userDb.getLastUserId(),author);
                        Message newMessage = new Message(messageDb.getLastMessageId(),message.toString(),user,msg_id);
                        //DataBase
                        int value =messageDb.NewMessage(newMessage);
                        if(value == 1){
                            ServerMasterTCP.userToMessagesMap.get(new User(userDb.getLastUserId(),author)).add(newMessage);
                            ServerMasterTCP.messagesMap.put(newMessage.getId(),newMessage);
                            addToQueue(newMessage,userName);
                            dataSend.println(response);
                            System.out.println("OK: REPLY AND ADD TO DB");
                            System.out.println(">>>>ServerTCP.userToMessagesMap: "+ ServerMasterTCP.userToMessagesMap);
                            System.out.println(">>>>ServerTCP.messagesMap: "+ ServerMasterTCP.messagesMap);
                        }else{
                            System.out.println("ERROR: REPLY NOT ADD TO DB");
                        }
                    }
                    else {
                        response = "ERROR: REPUBLISH ";
                        dataSend.println(response);
                        System.out.println("ERROR: REPUBLISH");
                    }

                }// @done
                else if (readLine.startsWith("REPUBLISH")) {
                    String data = readLine.substring(10);
                    String[] tableData = data.split(" ");
                    String author = tableData[1];
                    int msg_id = Integer.parseInt(tableData[3])-1;
                    String response;
                    System.out.println("ici>>>>>>>>>>>>>>>>>:author: "+author+" message Id: "+msg_id);
                    if(msg_id != -1) {
                        response ="user that republish"+"$"+author+"$"+
                                "user that publish:"+"$"+ ServerMasterTCP.messagesMap.get(msg_id).getUsername() +"$"+
                                "message:"+"$"+ ServerMasterTCP.messagesMap.get(msg_id).getMessage()+"$"+"OK";

                        User user = new User(userDb.getLastUserId(),author);
                        Message newMessage = new Message(messageDb.getLastMessageId(), ServerMasterTCP.messagesMap.get(msg_id).getMessage(),user,true);

                        //DataBase
                        int value =messageDb.NewMessage(newMessage);
                        if(value == 1){
                            ServerMasterTCP.userToMessagesMap.get(new User(userDb.getLastUserId(),author)).add(newMessage);
                            ServerMasterTCP.messagesMap.put(newMessage.getId(),newMessage);
                            addToQueue(newMessage,userName);
                            dataSend.println(response);
                            System.out.println("OK: REPUBLISH AND ADD TO DB");
                            System.out.println(">>>>ServerTCP.userToMessagesMap: "+ ServerMasterTCP.userToMessagesMap);
                            System.out.println(">>>>ServerTCP.messagesMap: "+ ServerMasterTCP.messagesMap);
                        }else{
                            System.out.println("ERROR: REPUBLISH NOT ADD TO DB");
                        }
                    }
                    else {
                        response = "ERROR: REPUBLISH ";
                        dataSend.println(response);
                        System.out.println("ERROR: REPUBLISH");
                    }
                }
                // @done
                else if(readLine.startsWith("FOLLOW")){
                    String data = readLine.substring(7);
                    String[] authors = data.split(" ");
                    StringBuilder response = new StringBuilder();
//                    System.out.println("here FOLLOW: "+authors[0]);
                    for (String author : authors) {
                        if (author != null) {
                            List<Message> messages = new ArrayList<>();
                            for (Message message : ServerMasterTCP.messagesMap.values()) {
                                if (message.getStrUser().equals(author)) {
                                    messages.add(message);
                                }
                            }
                            System.out.println(messages);
                            response.append(author).append("___");
                            System.out.println("author" + author);
                            for (Message message : messages) {
                                response.append(message.getMessage()).append("___").append(message.getId()).append("___");
                                System.out.println("message: " + message.getMessage()+"id: "+message.getId());
                            }
                            response.append("___");
                            dataSend.println(response);
                        }
                        dataSend.println(response);
                        System.out.println("OK: FOLLOW");
                    }
                }
                else if(readLine.startsWith("CONNECT")){
                    String data = readLine.substring(8);
                    String[] tableData = data.split(" ");


                    boolean responseError=false;
//                    code
                    if (tableData[0] != null){
                        System.out.println("----code----");
                        responseError=true;
                        this.userName = new User(tableData[1]);
                        if(!ServerMasterTCP.userToMessagesMap.containsKey( this.userName )){
                            ServerMasterTCP.clientFollowers.put( this.userName ,new ArrayList<>());
                            ServerMasterTCP.userToMessagesMap.put( this.userName ,new ArrayList<>());
                            ServerMasterTCP.userQueue.put( this.userName ,new ConcurrentLinkedQueue<>());
                        }
                    }
                    if(responseError){
                        removeFromQueue();
                        System.out.println("OK: CONNECT");
                        dataSend.println("OK: CONNECT");
                    }else{
                        System.out.println("ERROR: CONNECT");
                        dataSend.println("ERROR: CONNECT");
                    }
                }
                //@done
                else if(readLine.startsWith("SUBSCRIBE")){
                    String data = readLine.substring(10);
                    String[] tableData = data.split(" ");
                    if(tableData[0].equals("author:")){
                        User author = new User( tableData[1]);
                        //code
                        if(ServerMasterTCP.clientFollowers.containsKey(author)){
                            ServerMasterTCP.clientFollowers.get(author).add(this.userName);
                            System.out.println("ok");
                            dataSend.println("OK: Subscribe");
                        }else{
                            dataSend.println("ERROR: no user found");
                        }

                    }else if(tableData[0].equals("tag:")){
                        Tag tag = new Tag(tableData[1]);
                        //code
                        if(ServerMasterTCP.followersTags.containsKey(tag)){
                            ServerMasterTCP.followersTags.get(tag).add(this.userName);
                            System.out.println("ok");
                            dataSend.println("OK: Subscribe");
                        }else{
                            ServerMasterTCP.followersTags.put(tag,new ArrayList<>());
                            dataSend.println("OK: New tag created");
                        }

                    }
                }
                //@done
                else if(readLine.startsWith("UNSUBSCRIBE")){
                    String data = readLine.substring(12);
                    String[] tableData = data.split(" ");
                     if(tableData[0].equals("author:")){
                         User author = new User( tableData[1]);
                         //code
                         if(ServerMasterTCP.clientFollowers.containsKey(author)){
                             ServerMasterTCP.clientFollowers.get(author).remove(this.userName);
                             System.out.println("ok");
                             dataSend.println("OK: Unsubscribe");
                         }else{
                             dataSend.println("ERROR: no user found");
                         }

                     }else if(tableData[0].equals("tag:")){
                         Tag tag = new Tag(tableData[1]);
                         //code
                         if(ServerMasterTCP.followersTags.containsKey(tag)){
                             ServerMasterTCP.followersTags.get(tag).remove(this.userName);
                             System.out.println("ok");
                             dataSend.println("OK: Unsubscribe");
                         }else{
                             dataSend.println("ERROR: no tag found");
                         }
                     }
                }

                else if(readLine.startsWith("NewUser")){
                    String data = readLine.substring(8);
                    String[] tableData = data.split(" ");
                    String user = tableData[0];
                    String password = tableData[1];
                    System.out.println("test user: "+user+" \ntest password: " +password);
                    System.out.println("(test): "+user.equals(userDb.getUsername(user)));
                    if(!user.equals(userDb.getUsername(user))){
                        User newUser = new User(user,password);
                        //DataBase
                        int valueResponse = userDb.createUser(newUser);
                        if (valueResponse == 0){
                            System.out.println("ERROR: table users not update");
                            dataSend.println("User not created");
                        }
                        System.out.println("new user created");
                        dataSend.println("User created : "+user);

                    }
                }
                else {
                    dataSend.println("BAD REQUEST!!");
                    dataSend.println("ERROR:" + readLine);
                    System.out.println("ERROR:" + readLine);
                }
            }
        }catch(Exception e){
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

        System.out.println("closing client");

    }

    private void addToQueue(Message message,User user){
        System.out.println("ici entre");
        List<User> userFollowers = ServerMasterTCP.clientFollowers.get(user);
        List<Tag>  tagMessages = new ArrayList<>();
        for (User follower: userFollowers) {
            ServerMasterTCP.userQueue.get(follower).add(message);
        }
        System.out.println("tag");
        for(String word: message.getMessage().split(" ")){
            if(word.startsWith("#")){
                tagMessages.add(new Tag(word));
            }
        }
        for(Tag tag: tagMessages){
            List<User> tagFollowers = ServerMasterTCP.followersTags.get(tag);
            for (User follower: tagFollowers) {
                if(!userFollowers.contains(follower)) {
                    ServerMasterTCP.userQueue.get(follower).add(message);
                    userFollowers.add(follower);
                }
            }
        }
        System.out.println("ici sortie");
    }

    private void removeFromQueue(){
        Queue<Message> messages =  ServerMasterTCP.userQueue.get(userName);
        for(Message message = messages.poll(); message != null ; message = messages.poll()){
            dataSend.println("message:" +message);
        }
    }


}
