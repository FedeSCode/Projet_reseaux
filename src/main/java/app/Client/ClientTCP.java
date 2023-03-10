package app.Client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientTCP {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String separator = "--------------------------------------------------------------------------------------";
            String connectionToServer = "-----------------------------------Connected to Server--------------------------------";
            String fakeBook = "-----------------------------------FakeBook-------------------------------------------";
            String disconnectedFromServer = "------------------------------Disconnected From Server--------------------------------";

            int port = 12345;
            Socket clientSocket = new Socket();
            InetSocketAddress localhost = new InetSocketAddress("localhost", port);

            StringBuilder dataToSend = new StringBuilder();

            /*connection server*/
            clientSocket.connect(localhost);
            System.out.println(connectionToServer);
            TimeUnit.SECONDS.sleep(2);
            System.out.println(separator);
            System.out.println(fakeBook);
            System.out.println(separator);

            OutputStream outputStream = clientSocket.getOutputStream();
            InputStream receiveData = clientSocket.getInputStream();

            /*Identification*/
            System.out.print("Enter your identification: ");
            String user = scanner.nextLine();
            while (user.length() > 10 || !user.startsWith("@")) {
                System.out.println("ERROR: Invalid identifier. Please enter an identifier starting with @ " +
                        "\n and with length less than or equal to 10.");
                System.out.print("Enter your identification: ");
                user = scanner.nextLine();
            }

            System.out.println(separator);
            System.out.println("Welcome, " + user.substring(1) + "!");
            String userName = "USER" + user;
            outputStream.write(userName.getBytes());



            System.out.println("Type '$DISC' to disconnect from the server.");
            System.out.println(separator);
            System.out.println("Enter your message: ");

            while (scanner.hasNextLine()) {
                /*ENTER DATA*/
                String input = scanner.nextLine().trim();
                while (input.equals("")){
                    System.out.println("Enter a message min char 1 - max 256:");
                    input = scanner.nextLine().trim();
                }

                /*Disconnect from server*/
                if (input.equals("$DISC")) {
                    break;
                }

                /*Verify length of message*/
                while (input.length() > 10) {
                    System.out.println("ERROR: Message too long. Please enter a message with length less than or equal to 256.");
                    input = scanner.nextLine().trim();
                }


                /*Send message to publish*/
                String messageToSend = "PUBLISH" + user + " " + input + "\n";
                outputStream.write(messageToSend.getBytes());

                /*Listening to server*/
                StringBuilder data = new StringBuilder();
                char charReceive = (char) receiveData.read();
                while (charReceive != '\n') {
                    data.append(charReceive);
                    charReceive = (char) receiveData.read();
                }

                String[] tableData = data.toString().split(" ");

                StringBuilder response = new StringBuilder();
                for (int index=0; index<tableData.length ; index++) {
                    if ( tableData[index].equals("EndResponse")) {
                        tableData = Arrays.copyOfRange(tableData, 0, index);

                        for (String datum : tableData) {
                            response.append(datum).append(' ');
                        }
                        System.out.println("ServerR >>: "+ response);
                        System.out.println(separator);
                        System.out.println("Enter your message: ");
                    } else if (tableData[0].equals("ERROR")) {
                        System.out.println(separator);
                            System.out.println("ERROR: " + data.substring(6));
                            System.out.println("Enter your message: ");
                    }
                }
            }

            System.out.println(separator);
            System.out.println(disconnectedFromServer);
            scanner.close();
            clientSocket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
