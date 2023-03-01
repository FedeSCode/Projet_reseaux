import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.BreakIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServeurTCP  {
    public static void main(String[] args) throws IOException {
        int nbClient = 0;
        if(args.length < 1 || args.length > 3){
            throw new IllegalArgumentException("Mauvais nombre d'argument");
        }
        int port;
        int nb = -1;
        ExecutorService executorService = null;
        if(args[0].equals("-t")){
            System.out.println("POOL STATIQUE");
            port = Integer.parseInt(args[2]);
            nb = Integer.parseInt(args[1]);
            executorService = Executors.newFixedThreadPool(nb);

        }if(args[0].equals("-d")){
            System.out.println("POOL DINAMIQUE");
            port = Integer.parseInt(args[1]);
            executorService = Executors.newCachedThreadPool();

        }if(args[0].equals("-v")){
            System.out.println("POOL VOLEUR");
            port = Integer.parseInt(args[1]);
            executorService = Executors.newWorkStealingPool();
        }else{
            System.out.println("\n -t pool dinamique "+"\n -d pool dynamique"+"\n -v pool voleur");                
            port=1234;
        }

        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            nbClient++;

            Socket sc = serverSocket.accept();

            System.out.println("new client"+nbClient);
            ClientHandler handler = new ClientHandler(sc);
            executorService.submit(handler);
        }
    }
}





class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket){
        this.clientSocket = socket;
    }

    @Override
    public void run(){
        InputStream inputStream = null;
        try {
            inputStream = this.clientSocket.getInputStream();
            StringBuilder textString = new StringBuilder();
            do {
                char c = (char) inputStream.read();
                textString.append(c);
                if (c == '\n') {
                    System.out.print(">>" + textString);
                    textString = new StringBuilder();
                }
            } while (!textString.toString().equals("@ServDis"));
            inputStream.close();
            this.clientSocket.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}