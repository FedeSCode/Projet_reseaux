package app.Client;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Rcv_msger {

    public static void rcv_msger(BufferedReader userIn, BufferedReader serverResponse , PrintWriter out) {

        String separator = "--------------------------------------------------------------------------------------";

        try {

            //wait for messages
            String userInput;
            userInput = userIn.readLine();

            while (userInput.equals("")) {
                System.out.println("Enter an message ID");
                userInput = userIn.readLine();
            }

            /*Verify length of message*/
            while (userInput.length() > 5) {
                System.out.println("ERROR: Message Id too long. Please enter an id message valid");
                userInput = userIn.readLine();
            }

                    int var = Integer.parseInt(userInput);

//            Request request = new Rcv_msg(userInput);
            Request request = new Rcv_msg(String.valueOf(var));


            System.out.println("send req: " + request.sendRequest());
            out.println(request.sendRequest());
            String response;

            response = serverResponse.readLine();

            String[] receivedData = response.split("\\$");
            System.out.println("Server response:");
            System.out.println("Here is the message: ");
            for (String s: receivedData) {
                System.out.println(s);
            }

            System.out.println(separator);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
