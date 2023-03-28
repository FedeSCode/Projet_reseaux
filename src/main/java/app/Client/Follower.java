package app.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
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
    //            if(args.length < 1 || args.length > 3){
    //                throw new IllegalArgumentException("Mauvais nombre d'argument");
    //            }

                InetSocketAddress localhost = new InetSocketAddress("localhost", PORT);
                Socket clientSocket = new Socket();
                /*connection to the socket*/
                clientSocket.connect(localhost);

                BufferedReader serverResponse = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                System.out.println(connectionToServer);
                System.out.println(separator);
                TimeUnit.SECONDS.sleep(2);
                System.out.println(fakeBook);
                System.out.println(separator);

                //codeeee

                Scanner scanner = new Scanner(System.in);

                System.out.println("Enter the username(s) you want to follow (separated by a space): ");
                String usernames = scanner.nextLine();
                String[] users = usernames.split(" ");

                // send the user names to the server
                StringBuilder followCommand = new StringBuilder("FOLLOW ");
                for (String user : users) {
                    followCommand.append(user).append(" ");
                }
                System.out.println(followCommand);
                out.println(followCommand);
                System.out.println("here: so im waiting!!");

                // read the responses from the server
                String response;

                response = serverResponse.readLine();
                System.out.println("Server response:\n");
                String[] data = response.split("___");
                System.out.println("User: "+data[0]);
                for (int i = 1; i < data.length ; i+=2) {
                    if ((i+1)!=data.length) {
                        System.out.println("message id: " + data[i + 1] + " message: " + data[i]);
                    }
                }
                System.out.println(separator);
                System.out.println(disconnectedFromServer);
        }
        catch (IOException | InterruptedException e){
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);

        }
    }


}
