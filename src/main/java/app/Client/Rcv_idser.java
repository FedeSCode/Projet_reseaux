package app.Client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Rcv_idser {

    public static void rcv_idser(BufferedReader userIn, BufferedReader serverResponse , PrintWriter out) {

        String separator = "--------------------------------------------------------------------------------------";

        try {
            Request request = new Rcv_ids(userIn);
//            System.out.println("send req: " + request.sendRequest());
            out.println(request.sendRequest());
            String response;

            response = serverResponse.readLine();
            String[] receivedData = response.split("\\$");
            System.out.println("Server response:");
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
