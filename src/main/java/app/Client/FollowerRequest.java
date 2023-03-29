package app.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FollowerRequest {
    String separator = "--------------------------------------------------------------------------------------";
    public static void followerRequest(BufferedReader userIn, BufferedReader serverResponse, PrintWriter out) throws IOException {
        String separator = "--------------------------------------------------------------------------------------";

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
    }
}
