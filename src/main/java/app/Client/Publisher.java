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

    public static void publisher(BufferedReader userIn, BufferedReader serverResponse , PrintWriter out) {

        String separator = "--------------------------------------------------------------------------------------";
        System.out.println("Entrez un message a publier: \n");

        try {

            //wait for messages
            String userInput;
            userInput = userIn.readLine();

            while (userInput.equals("")) {
                System.out.println("Enter a message min char 1 - max 256:");
                userInput = userIn.readLine();
            }

            /*Verify length of message*/
            while (userInput.length() > 50) {
                System.out.println("ERROR: Message too long. Please enter a message with length less than or equal to 256.");
                userInput = userIn.readLine();
            }

//                    out.println("PUBLISH" + username + " " + userInput);
            Request request = new Publish(Main.username, userInput);
            System.out.println("send req: " + request.sendRequest());

            out.println(request.sendRequest());
            System.out.println("send"+(serverResponse));
            String response = serverResponse.readLine();
            System.out.println("Server response: " + response);
            System.out.println(separator);




        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
}


