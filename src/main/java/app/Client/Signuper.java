package app.Client;

import app.ServerMaster.Login;
import app.ServerMaster.UserDb;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Signuper {
    static String username;
    static boolean boolValue = false;

    public static void signuper(BufferedReader userIn, BufferedReader serverResponse , PrintWriter out) {

        String separator = "--------------------------------------------------------------------------------------";
        String responseUserName="";
        try {
            UserDb userDb = new UserDb();

            //wait for messages
            System.out.print("Enter your username user: ");
            String password = "";
            String checkPassword = "";
            username = userIn.readLine();
            Login login = new Login(username,password,checkPassword);
            while (username.length() > 10 || !username.startsWith("@")) {
                System.out.println("ERROR: Invalid identifier. Please enter an identifier starting with @ " +
                        "\n and with length less than or equal to 10.");
                System.out.print("Enter your identification: ");
                username = userIn.readLine();
            }
            if(!username.equals(userDb.getUsername(username))) {
                System.out.print("Enter a valid password: ");
                password = userIn.readLine();
                while (password.length() > 10) {
                    System.out.println("ERROR: Invalid password, please enter your password");
                    System.out.print("Enter a valid password: ");
                    password = userIn.readLine();
                }
                System.out.print("Check your password: ");
                checkPassword = userIn.readLine();
                while (checkPassword.length() > 10) {
                    System.out.println("ERROR: Invalid password, please enter your password");
                    System.out.print("Check your password: ");
                    password = userIn.readLine();
                }

                if (password.equals(checkPassword)) {
                    login.isAuth(username, password, checkPassword);
                    Request request = new Signup(username,password);
                    System.out.println("send req: " + request.sendRequest());
                    out.println(request.sendRequest());
                    String response;
                    response = serverResponse.readLine();
                    System.out.println("Server response:\n"+response);
                    System.out.println("User created...");
                    boolValue=true;
                    System.out.println(separator);
                }
            }else{
                boolValue=false;
                System.out.println("user already exist!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
    public static String getUsername(){
        return username;
    }
    public static boolean getIsAuth(){
        return boolValue;
    }
}
