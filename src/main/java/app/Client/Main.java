package app.Client;

import app.ServerMaster.Login;
import app.ServerMaster.MessageDb;
import app.ServerMaster.UserDb;

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
        String separator =             "--------------------------------------------------------------------------------------";
        String connecting =            "----------------------------------------Connecting------------------------------------";
        String log_in =                "---------------------------------------Login------------------------------------------";
        String sing_up =               "---------------------------------------Sing-up----------------------------------------";
        String connectionToServer =    "-----------------------------------Connected to Server--------------------------------";
        String fakeBook =              "-----------------------------------FakeBook-------------------------------------------";
        String disconnectedFromServer= "------------------------------Disconnected From Server--------------------------------";
        UserDb userDb = new UserDb();

        scanner = new Scanner(System.in);

//        InetSocketAddress localhost = new InetSocketAddress("10.192.27.159", PORT);
        InetSocketAddress localhost = new InetSocketAddress("localhost", PORT);
        Socket clientSocket = new Socket();

        clientSocket.connect(localhost);

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader serverResponse = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        Console console = System.console();

        System.out.println(connectionToServer);
        System.out.println(separator);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(fakeBook);
        System.out.println(separator);


        boolean isAuth = false;
        while(!isAuth){
            System.out.println("1.Login \n2.Sign-up");
            String home = userIn.readLine();
            String password = "";
            String checkPassword = "";
            Login login = new Login(username,password,checkPassword);
            if(home.equals("1")){
                System.out.println(log_in);
                System.out.print("Enter your username user: ");
                username = userIn.readLine();
                while (username.length() > 10 || !username.startsWith("@")) {
                    System.out.println("ERROR: Invalid identifier. Please enter an identifier starting with @ " +
                            "\n and with length less than or equal to 10.");
                    System.out.print("Enter your identification: ");
                    username = userIn.readLine();
                }

                if(username.equals(userDb.getUsername(username))) {
                    System.out.print("Enter your password: ");
                    checkPassword = userDb.getPasswordByUsername(username);
                    password = userIn.readLine();
                    while (password.length() > 10) {
                        System.out.println("ERROR: Invalid password, please enter your password");
                        System.out.print("Enter your identification: ");
                        password = userIn.readLine();
                    }
                    System.out.println(connecting);

                    isAuth = login.isAuth(username, password, checkPassword);
                    if (!isAuth){
                        System.out.println("Username or password not valid");
                    }

                    TimeUnit.SECONDS.sleep(2);
                }else{
                    System.out.println("User not Found\n" +
                            "-------->2.to Sing-up");
                }
            }
            else if (home.equals("2")){
                System.out.println(sing_up);
                Signuper.signuper(userIn,serverResponse,out);
                checkPassword = userDb.getPasswordByUsername(Signuper.getUsername());
                System.out.println("test sing checkPassword: "+ Signuper.getUsername());
                username=Signuper.getUsername();
                System.out.println("test sing auth: "+ Signuper.getIsAuth());
                isAuth = Signuper.getIsAuth();
            }
            else{
                    System.out.println("try again");
            }



        }


        System.out.println("Welcome, " + username.substring(1) + "!");
        Connecter.connecter(userIn,serverResponse,out);
        System.out.println(separator);


//        System.out.println("send a message:");

        String userInput;
        menu();
        while ((userInput = userIn.readLine()) != null){
            System.out.println(separator);
            if (userInput.equals("0")) {
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
                System.out.println("Ok let's replay:");
                Replayer.replayer(userIn,serverResponse,out);
                TimeUnit.SECONDS.sleep(1);
            }
            if( userInput.equals("5")){
                System.out.println("What message you want to republish");
                Republisher.republish(userIn,serverResponse,out);
                TimeUnit.SECONDS.sleep(1);
            }
            if( userInput.equals("6")){
                System.out.println("Ok let's follow:");
                FollowerRequest.followerRequest(userIn,serverResponse,out);
                TimeUnit.SECONDS.sleep(1);
            }if( userInput.equals("7")){
                System.out.println("Ok let's follow:");
                Subscriber.subscriber(userIn,serverResponse,out);
                TimeUnit.SECONDS.sleep(1);
            }if( userInput.equals("8")){
                System.out.println("Ok let's unfollow:");
                Unsubscriber.unSubscriber(userIn,serverResponse,out);
                TimeUnit.SECONDS.sleep(1);
            }

            menu();

        }

    }

    private static void menu(){
        System.out.println("what u want to do today "+username+"??");
        System.out.println("0. To disconnect from server");
        System.out.println("1. Publish a message");
        System.out.println("2. RCV_IDS");
        System.out.println("3. RCV_MSG");
        System.out.println("4. REPLY");
        System.out.println("5. REPUBLISH");
        System.out.println("6. FOLLOWER");
        System.out.println("7. SUBSCRIBE");
        System.out.println("8. UNSUBSCRIBE");
        System.out.println("Chose what u want to do: ");

    }

}
