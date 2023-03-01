package app.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerTCP {
    public static void main(String[] args) throws IOException {
        int nbClient = 0;
        if(args.length < 1 || args.length > 3){
            throw new IllegalArgumentException("Mauvais nombre d'argument");
        }
        int port;
        int nb = -1;
        ExecutorService executorService = null;

        if(args[0].equals("-v")){
            System.out.println("POOL VOLEUR");
            port = Integer.parseInt(args[1]);
            executorService = Executors.newWorkStealingPool();
        }else{
            System.out.println("\n -t pool statique "+"\n -d pool dynamique"+"\n -v pool voleur");
            port=12345;
        }

        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            nbClient++;

            Socket sc = serverSocket.accept();



            System.out.println("new client"+nbClient);
            app.Server.ClientHandler handler = new app.Server.ClientHandler(sc);
            executorService.submit(handler);


        }
    }
}
