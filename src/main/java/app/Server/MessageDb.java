package app.Server;

import app.ConstantsForApp.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageDb {
    Constants constants;
    String sqlSelectAllUsers = "select * from user";
    String sqlInsertUser = "INSERT INTO USERS "+"(id,username) VALUES"+"(?,?);";
    Message message;


    public int NewMessage(Message message){
        int result = 0;
        try (Connection connection = DriverManager.getConnection(constants.getUrl(),constants.getUsername(), constants.getPassword());
             PreparedStatement prepareStatement = connection.prepareStatement(sqlInsertUser)){

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



}
