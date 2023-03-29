package app.Client;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Republisher {
    public static void republish(BufferedReader userIn, BufferedReader serverResponse, PrintWriter out) {
        String separator = "--------------------------------------------------------------------------------------";
        System.out.println("Enter a message id:");
        try {
            //wait for messages
            String messageId;
            messageId = userIn.readLine();

            while (messageId.equals("")) {
                System.out.println("Enter a valid message id:");
                messageId = userIn.readLine();
            }

            String author;
            author = Main.username;

            Request request = new Republish(author,messageId);
            System.out.println("send req: " + request.sendRequest());

            out.println(request.sendRequest());
            String response = serverResponse.readLine();

//            System.out.println("Server response:\n"+response);

            String[] data = response.split("\\$");
            String originalAuthor=data[3];
            String message = data[5];
            String serverFinalResponse = data[6];

            System.out.println(
                    "user: "+author+" republish:"+
                    "\nmessage: "+message+
                    "\nfrom "+originalAuthor);
            System.out.println("Server response: "+serverFinalResponse);

            System.out.println(separator);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
