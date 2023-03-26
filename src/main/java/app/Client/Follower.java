package app.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class Follower {
    static int PORT = 12345;
    public static void main(String[] args) throws IOException {
        String separator = "--------------------------------------------------------------------------------------";
        String connectionToServer = "-----------------------------------Connected to Server--------------------------------";
        String fakeBook = "-----------------------------------FakeBook-------------------------------------------";
        String disconnectedFromServer = "------------------------------Disconnected From Server--------------------------------";
        String hostName = InetAddress.getLocalHost().getHostName();
        try {



            InetSocketAddress localhost = new InetSocketAddress("localhost", PORT);
            Socket clientSocket = new Socket();
            /*connection to the socket*/
            clientSocket.connect(localhost);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader serverResponse = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(connectionToServer);
            System.out.println(separator);
            TimeUnit.SECONDS.sleep(2);
            System.out.println(fakeBook);
            System.out.println(separator);


            }
        catch (IOException | InterruptedException e){
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);

        }
    }


}
