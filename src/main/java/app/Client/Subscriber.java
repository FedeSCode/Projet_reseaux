package app.Client;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Subscriber {
    public static void subscriber(BufferedReader userIn, BufferedReader serverResponse , PrintWriter out){
        String separator = "--------------------------------------------------------------------------------------";


        try {

            System.out.println("Subscribe\n1. TAG\n2. USER");
            String userChoice = userIn.readLine();
            String text = "";
            Request request= null;
            if(userChoice.equals("1")){
                System.out.println("What tag do u want to follow?:");
                text = userIn.readLine();
                request = new Subscribe(null,text);


            }
            if(userChoice.equals("2")){
                System.out.println("What user do u want to follow?:");
                text = userIn.readLine();
                request = new Subscribe(text,null);
            }
            //wait for messages
            assert request != null;
//            System.out.println("send req: " + request.sendRequest());

            out.println(request.sendRequest());

            String response = serverResponse.readLine();
            System.out.println("send"+(serverResponse));
            System.out.println("Server response: \n" + response);
            System.out.println(separator);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
