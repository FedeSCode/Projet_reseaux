package app.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Publisher {
    private String idUser;
    private String messageToSend;
    static int PORT = 12345;
    public static void main(String[] args) throws IOException {


        String hostName = InetAddress.getLocalHost().getHostName();

        String separator = "--------------------------------------------------------------------------------------";
        String connectionToServer = "-----------------------------------Connected to Server--------------------------------";
        String fakeBook = "-----------------------------------FakeBook-------------------------------------------";
        String disconnectedFromServer = "------------------------------Disconnected From Server--------------------------------";


        try
            {
                InetSocketAddress localhost = new InetSocketAddress("localhost", PORT);
                Socket clientSocket = new Socket();

                clientSocket.connect(localhost);

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader serverResponse = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

                System.out.println(connectionToServer);
                System.out.println(separator);
                TimeUnit.SECONDS.sleep(2);
                System.out.println(fakeBook);
                System.out.println(separator);

                // identifier
                System.out.print("Enter your username user: ");
                String username = userIn.readLine();
                while (username.length() > 10 || !username.startsWith("@")) {
                    System.out.println("ERROR: Invalid identifier. Please enter an identifier starting with @ " +
                            "\n and with length less than or equal to 10.");
                    System.out.print("Enter your identification: ");
                    username = userIn.readLine();
                }
                System.out.println("Welcome, " + username.substring(1) + "!");
                out.println("USER " +username);
                String response = serverResponse.readLine();
                System.out.println("Server response: " + response);
                System.out.println(separator);
                System.out.println("send a message:");


                //wait for messages
                String userInput;
                while ((userInput = userIn.readLine()) != null) {
                    while (userInput.equals("")){
                        System.out.println("Enter a message min char 1 - max 256:");
                        userInput = userIn.readLine();
                    }

                    /*Verify length of message*/
                    while (userInput.length() > 50) {
                        System.out.println("ERROR: Message too long. Please enter a message with length less than or equal to 256.");
                        userInput = userIn.readLine();
                    }

                    out.println("PUBLISH " + username + " " + userInput);
                    response = serverResponse.readLine();
                    System.out.println("Server response: " + response);
                    System.out.println(separator);

                    if (userInput.equals("$DISC")) {
                        clientSocket.close();
                        System.out.println(disconnectedFromServer);
                        break;
                    }

                }


            } catch ( IOException | InterruptedException e) {
                System.err.println("Couldn't get I/O for the connection to " + hostName);
                System.exit(1);
            }
        }
    }

