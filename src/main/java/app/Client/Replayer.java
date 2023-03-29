package app.Client;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Replayer {

    public static void replayer(BufferedReader userIn, BufferedReader serverResponse , PrintWriter out) {

        String separator = "--------------------------------------------------------------------------------------";


        try {

            //wait for messages
            String idMessage = "";
            String username = "";
            String message;

            System.out.println("Replay to message Id:");
            idMessage = userIn.readLine();
            //verify that user put and messageId and that message id exist
            while(idMessage.equals(" ")){
                System.out.println("Enter an Verify message id");
                idMessage = userIn.readLine();
            }

            username = Main.username;

            System.out.println("Enter a message to publish:");
            message = userIn.readLine();

            while (message.equals("")) {
                System.out.println("Enter a message min char 1 - max 256:");
                message = userIn.readLine();
            }
            /*Verify length of message*/
            while (message.length() > 50) {
                System.out.println("ERROR: Message too long. Please enter a message with length less than or equal to 256.");
                message = userIn.readLine();
            }

            Request request = new Reply(idMessage,username,message);
//            System.out.println("send req: " + request.sendRequest());

            out.println(request.sendRequest());

            String response = serverResponse.readLine();
//            System.out.println("send"+(serverResponse));
//            System.out.println("Server response: \n" + response);

            String[] data = response.split("\\$");
            String originalAuthor=data[3];
            String originalMessage=data[5];
            String messageResponse = data[7];
            String idOriginalMessage=data[9];
            String serverFinalResponse = data[10];

            System.out.println(
                    separator+"\n"+"original message Id: "+idOriginalMessage+" User that publish: "+originalAuthor+" \n"+
                            "message: "+originalMessage+" \n"+
                            "Replay: \n"+
                            messageResponse+"\n"
                    );
            System.out.println("Server response: "+serverFinalResponse);

            System.out.println(separator);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


    }

}
