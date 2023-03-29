package app.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Connecter {
    public static void connecter(BufferedReader userIn, BufferedReader serverResponse, PrintWriter out) throws IOException {
        String separator = "--------------------------------------------------------------------------------------";

        try {


            String author;
            author = Main.username;

            Request request = new Connect(author);
            System.out.println("send req: " + request.sendRequest());

            out.println(request.sendRequest());
            String response = serverResponse.readLine();

            while(!response.startsWith("OK")) {
                System.out.println("Message received: " + response);
                response = serverResponse.readLine();
            }

            System.out.println(separator);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


}
