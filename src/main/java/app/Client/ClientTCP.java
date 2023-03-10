package app.Client;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ClientTCP {

    private static InputStream serverResponse;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        try {
            String separator =          "--------------------------------------------------------------------------------------";
            String connectionToServer = "-----------------------------------Connected to Server--------------------------------";
            String disconnectedFromServer = "------------------------------Disconnected From Server--------------------------------";


            int port = 12345;
            Socket clientSocket = new Socket();
            InetSocketAddress localhost = new InetSocketAddress("localhost", port);



            StringBuilder dataToSend = new StringBuilder();


            /*connection server*/
            clientSocket.connect(localhost);
            System.out.println(connectionToServer);

            OutputStream outputStream = clientSocket.getOutputStream();
            InputStream receiveData = clientSocket.getInputStream();


            /*Identification*/
            System.out.println(separator);
            System.out.println("Enter your identification: ");
            String User = '@'+scanner.next();

            while (User.length()>=10)/*(User.length()>=65)*/{
                System.out.println("ERROR IDENTIFIER TOO LONG");
                System.out.println(separator);
                System.out.println("Enter your identification: ");
                User = '@'+scanner.next();
            }


            while (scanner.hasNextLine()) {

                System.out.println(separator);
                System.out.println("Your identification is: "+User);
                System.out.println("Enter your message: ");
                dataToSend.append(scanner.nextLine());
                /*Disconnect from server*/
                if(dataToSend.toString().equals("$DISC")){
                    break;
                }

                /*Verify length from data*/
                while (dataToSend.length() >= 50)/*if (dataToSend.length() >= 256){*/{
                    System.out.println("ERROR MESSAGE TO LONG");
                    System.out.println("Enter your message les than 256 char: ");
                    dataToSend.append(scanner.nextLine());
                }

                /*Send message to publish*/
                String messageToSend = "PUBLISH " + User + " " + dataToSend + "\n";
                outputStream.write(messageToSend.getBytes());

                /*Listening to server*/
                StringBuilder data = new StringBuilder();
                char charReceive = (char) receiveData.read();
                while (charReceive != '\n') {
                    data.append(charReceive);
                    charReceive = (char) receiveData.read();
                }

                String[] tableData = data.toString().split(" ");

                for (String tableDatum : tableData) {
                    if (Objects.equals(tableDatum, "FinResponse")) {
                        System.out.println("Server >> " + tableData[0]);
                    }
                }
                dataToSend = new StringBuilder();
            }

            System.out.println(disconnectedFromServer);
            scanner.close();
            clientSocket.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }
}



