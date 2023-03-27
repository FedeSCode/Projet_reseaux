package app.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerTCP {
    private static final int PORT = 12345;
    static HashMap<Integer,Message> messagesMap = new HashMap<>();
    static HashMap<User,List<Message>> userToMessagesMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        String serverOn = "-----------------------------------Sever is on----------------------------------------";
        String separator ="--------------------------------------------------------------------------------------";

        int nbClient = 0;
        if(args.length < 1 || args.length > 3){
            throw new IllegalArgumentException("Mauvais nombre d'argument");
        }
        int PORT;
        ExecutorService executorService = null;

        if(args[0].equals("-v")){
//            System.out.println("POOL VOLEUR");
            PORT = Integer.parseInt(args[1]);
            executorService = Executors.newWorkStealingPool();
        }else{
//            System.out.println("-v pool voleur");
            PORT=12345;
        }

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
}
