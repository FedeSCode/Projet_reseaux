package app.Client;

import app.ConstantsForApp.Constants;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static Scanner scanner;
    static int PORT = 12345;
    static String username;

    public static void main(String[] args) throws IOException, InterruptedException {
        String separator = "--------------------------------------------------------------------------------------";
        String connectionToServer = "-----------------------------------Connected to Server--------------------------------";
        String fakeBook = "-----------------------------------FakeBook-------------------------------------------";
        String disconnectedFromServer = "------------------------------Disconnected From Server--------------------------------";

        scanner = new Scanner(System.in);



        InetSocketAddress localhost = new InetSocketAddress("localhost", PORT);
        Socket clientSocket = new Socket();

        clientSocket.connect(localhost);

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader serverResponse = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

        System.out.println(connectionToServer);
        System.out.println(separator);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(fakeBook);
        System.out.println(separator);


        // identifier
        System.out.print("Enter your username user: ");
        username = userIn.readLine();
        while (username.length() > 10 || !username.startsWith("@")) {
            System.out.println("ERROR: Invalid identifier. Please enter an identifier starting with @ " +
                    "\n and with length less than or equal to 10.");
            System.out.print("Enter your identification: ");
            username = userIn.readLine();
        }
        System.out.println("Welcome, " + username.substring(1) + "!");
        out.println("USER " + username);
        String response = serverResponse.readLine();
        System.out.println("Server response: " + response);
        System.out.println(separator);
//        System.out.println("send a message:");

        String userInput;
        menu();
        while ((userInput = userIn.readLine()) != null){
            System.out.println(separator);
            if( userInput.equals("1")){
                System.out.print("Ok, lets publish something!!");
                Publisher.publisher(userIn,serverResponse,out);
                TimeUnit.SECONDS.sleep(1);
            }
            if( userInput.equals("2")){
                System.out.print("Ok,let's find IDS!!");
                Rcv_idser.rcv_idser(userIn,serverResponse,out);
                TimeUnit.SECONDS.sleep(1);
            }
            if( userInput.equals("3")){
                System.out.print("Enter a msg ID:\n");
                Rcv_msger.rcv_msger(userIn,serverResponse,out);
                TimeUnit.SECONDS.sleep(1);
            }
            if( userInput.equals("4")){
                System.out.println("4 Works");
                TimeUnit.SECONDS.sleep(1);

            }
            if( userInput.equals("5")){
                System.out.println("5 Works");
                TimeUnit.SECONDS.sleep(1);

            }

            if (userInput.equals("6")) {
//                $DISC
                System.out.println("are u sure??");
                System.out.println("-yes -no");
                userInput = userIn.readLine();

                while(!userInput.toLowerCase().contains("yes") && !userInput.toLowerCase().contains("no") ){
                    System.out.println("-yes -no");
                    userInput = userIn.readLine();
                }

                if(userInput.toLowerCase().contains("yes")){
                    clientSocket.close();
                    System.out.println(disconnectedFromServer);
                    break;
                }
                else if(userInput.toLowerCase().contains("no")){
                    System.out.println("OK.");
                }

            }

            menu();

        }






    }

    private static void menu(){
        System.out.println("what u want to do today "+username+"??");
        System.out.println("1. Publish a message");
        System.out.println("2. RCV_IDS");
        System.out.println("3. RCV_MSG");
        System.out.println("4. REPLY");
        System.out.println("5. REPUBLISH");
        System.out.println("6. To disconnect from server");
        System.out.println("Chose what u want to do: ");

    }

}
