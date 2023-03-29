package app.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerTCP {
    private static final int PORT = 12345;
    static HashMap<Integer,Message> messagesMap = new HashMap<>();
    static HashMap<User,List<Message>> userToMessagesMap = new HashMap<>();
    static MessageDb messageDb = new MessageDb();
    static UserDb userDb = new UserDb();
    static HashMap<User,List<User>> clientFollowers = new HashMap<>();
    static HashMap<Tag,List<User>> followersTags = new HashMap<>();
    static HashMap<User,Queue<Message>> userQueue = new HashMap<>();

    public static void main(String[] args) throws IOException {


        String serverOn = "-----------------------------------Sever is on----------------------------------------";
        String separator ="--------------------------------------------------------------------------------------";

        /*loading Db*/
        LoadFromDb();

        /*Server*/
        int nbClient = 0;

        int PORT;
        ExecutorService executorService = null;

        if(args.length == 2){
            PORT = Integer.parseInt(args[1]);
        }else{
            PORT=12345;
        }

        executorService = Executors.newWorkStealingPool();

        System.out.println(serverOn);
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("FakeBook started on port >>"+" "+PORT);
            while (true) {
                nbClient++;
                Socket sc = serverSocket.accept();
                System.out.println("new client: " +"Port: "+sc.getPort());
                ClientHandler handler = new ClientHandler(sc);
                assert executorService != null;
                executorService.submit(handler);
            }
        }catch (IOException e){
            System.err.println("Error starting the server,"+ e.getMessage());
        }
    }


    public static void LoadFromDb(){
        /*base des donnes*/
        ArrayList<String> currentMessagesOnDb;
        ArrayList<String> currentUsersOnDb;

        /*Users on Db*/
        currentUsersOnDb = userDb.getAllUsersFromDb();
        currentMessagesOnDb = messageDb.getAllMessagesFromDb();

        for (String userOnDb:currentUsersOnDb) {
            String[] dataUser = userOnDb.split("\\|");
            userToMessagesMap.put(new User(userDb.getLastUserId(),dataUser[1]),new ArrayList<>());
            clientFollowers.put(new User(userDb.getLastUserId(),dataUser[1]),new ArrayList<>());
            userQueue.put(new User(userDb.getLastUserId(),dataUser[1]),new ConcurrentLinkedQueue<>());
            System.out.println("---userToMessagesMap:  "+userToMessagesMap);

            for (String messageOnDb:currentMessagesOnDb) {

                String[] dataMessage = messageOnDb.split("\\|");
                if (dataUser[1].equals(dataMessage[2])) {
                    User user = new User(Integer.parseInt(dataUser[0]), dataUser[1]);
                    Message newMessage = new Message(Integer.parseInt(dataMessage[0]), dataMessage[3], user);
                    messagesMap.put(newMessage.getId(),newMessage);
                    userToMessagesMap.get(new User(userDb.getLastUserId(),dataUser[1])).add(newMessage);
                }


            }
        }
        System.out.println(">>>>ServerTCP.userToMessagesMap: "+userToMessagesMap);
        System.out.println(">>>>ServerTCP.messagesMap: "+messagesMap);
    }
}
