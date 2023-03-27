package app.Server;

import app.ConstantsForApp.Constants;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class MessageDb {
    Constants constants;
    String sqlSelectAllMessages = "select * from messages";
    String sqlInsertMessage = "INSERT INTO messages "+"(id,user,message) VALUES"+"(?,?,?);";
    Message message;

    String url= "jdbc:mysql://localhost:3306/project";
    String username= "root";
    String password= "toor";


    public int NewMessage(Message message){
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url,username, password);
             PreparedStatement prepareStatement = connection.prepareStatement(sqlInsertMessage)){

            prepareStatement.setInt(1, message.getId());
            prepareStatement.setString(2,message.getUsername().toString());
            prepareStatement.setString(3,message.getMessage());
            result = prepareStatement.executeUpdate();

        } catch ( SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR!!");
        }
        return result;
    }


    public String getAllMessagesFromDb(){
        try (Connection connection = DriverManager.getConnection(url,username,password);){
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sqlSelectAllMessages);
            while (resultSet.next()){
                return resultSet.getInt(1)+" | "+resultSet.getString(2)+" | "+resultSet.getString(3);
            }


        } catch ( SQLException e) {
            System.out.println(e);
        }
        return "fin";
    }



}
