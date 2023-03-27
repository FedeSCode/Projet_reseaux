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
            do{
                response = serverResponse.readLine();
                if(response.equals("$")){
                    response = "End response";
                }
                System.out.println("Server response: " + response);
            }while(!(response.equals("End response")));

            System.out.println(separator);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
