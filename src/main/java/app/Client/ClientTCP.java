package app.Client;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
    private String user;
    private String messageToSend;
   // Publisher publish = new Publisher(user, messageToSend);
    private static InputStream serverResponse;

    public static void main(String[] args) throws IOException {
        final PrintWriter send;
        final BufferedReader receive;
        Scanner scanner = new Scanner(System.in);

        try {
            int port = 12345;
            Socket clientSocket = new Socket();
            InetSocketAddress localhost = new InetSocketAddress("localhost", port);
            StringBuilder dataToSend = new StringBuilder();

            /*connection server*/
            clientSocket.connect(localhost);
            System.out.println("-------------------------------------------");
            System.out.println("Enter your identification: ");
            /*Identification*/
            String User = '@'+scanner.next();

            while (User.length()>=10)/*(User.length()>=65)*/{

                System.out.println("ERROR IDENTIFIER TOO LONG");
                System.out.println("-------------------------------------------");
                System.out.println("Enter your identification: ");

                User = '@'+scanner.next();
            }
            System.out.println("-------------------------------------------");
            System.out.println("Your identification is: "+User);
            System.out.println("Enter your message: ");


            while (scanner.hasNextLine()) {
                dataToSend.append(scanner.nextLine());
    //            System.out.println(">>>>>>>>: "+ dataToSend);
                /*if (dataToSend.length() >= 256){*/

                if (dataToSend.length() >= 20){
                    System.out.println("ERROR MESSAGE TO LONG");
                    break;
                }

                /*envoyer message a publier*/
                String textScanner = "PUBLISH " + User + " " + dataToSend + "\n";
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(textScanner.getBytes());
                dataToSend = new StringBuilder();
                /*Ecouter le serveur*/
                System.out.println("ecouter mesasge");

                InputStream receiveData = clientSocket.getInputStream();
                char charReceive = (char) receiveData.read();

                StringWriter data = new StringWriter();

                System.out.println("receiveData: ");
                while (charReceive !=  '\n'){
                    data.append(charReceive);
                    charReceive = (char) receiveData.read();
                }
                System.out.println("Server>> " + data);

                System.out.println("fin ecouter mesasge");

            }


            scanner.close();
            clientSocket.close();
        }catch(IOException e){
            e.printStackTrace();

        }

    }
}

