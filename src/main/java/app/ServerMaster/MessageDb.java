package app.ServerMaster;

import app.ConstantsForApp.Constants;

import java.sql.*;
import java.util.ArrayList;

public class MessageDb {
    Constants constants;
    Message message;
    String sqlSelectAllMessages = "select * from messages";
    String sqlInsertMessage = "INSERT INTO messages (usernameID, username, message) SELECT id, ?, ? FROM users WHERE username = ?;";
    String sqlSelectIdOfLastMessage = "SELECT max(id) from messages;";
    String url= "jdbc:mysql://localhost:3306/project";
    String username= "root";
    String password= "toor";


    public int NewMessage(Message message){
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url,username, password);
             PreparedStatement prepareStatement = connection.prepareStatement(sqlInsertMessage)){

            prepareStatement.setString(1,message.getUsername().toString());
            prepareStatement.setString(2,message.getMessage());
            prepareStatement.setString(3,message.getUsername().toString());
            result = prepareStatement.executeUpdate();

        } catch ( SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR!!");
        }
        return result;
    }


    public ArrayList<String> getAllMessagesFromDb(){
        ArrayList<String> messagesList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url,username,password);){
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sqlSelectAllMessages);
            while (resultSet.next()){
                messagesList.add( resultSet.getInt(1)+"|"+resultSet.getInt(2)+"|"+
                        resultSet.getString(3)+"|"+resultSet.getString(4));
            }


        } catch ( SQLException e) {
            System.out.println(e);
        }
        return messagesList;
    }

    public int getLastMessageId(){
        int idOfLastMessage = 0;
        try (Connection connection = DriverManager.getConnection(url,username,password);){

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectIdOfLastMessage);
            while(resultSet.next()){
                idOfLastMessage = resultSet.getInt(1);
            }
        } catch ( SQLException e) {
            System.out.println(e);
        }
        return idOfLastMessage;
    }



}
