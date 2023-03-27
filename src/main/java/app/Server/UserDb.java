package app.Server;

import app.ConstantsForApp.Constants;

import java.sql.*;
import java.util.ArrayList;

public class UserDb {
    Constants constants;
    String sqlSelectAllUsers = "select * from user";
    String sqlInsertUser = "INSERT INTO USERS "+"(id,userName) VALUES"+"(?,?);";
    User user;
    String url= "jdbc:mysql://localhost:3306/project";
    String username= "root";
    String password= "toor";

    public int createUser(User user){

        int result = 0;
        /*try (Connection connection = DriverManager.getConnection(constants.getUrl(),constants.getUsername(), constants.getPassword());
        PreparedStatement prepareStatement = connection.prepareStatement(sqlInsertUser)){*/
        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement prepareStatement = connection.prepareStatement(sqlInsertUser)){

            prepareStatement.setInt(1, user.getId());
            prepareStatement.setString(2,user.getUsername());
            result = prepareStatement.executeUpdate();

        } catch ( SQLException e) {
            System.out.println(e);
        }

        return result;
    }


    public ArrayList<String> getAllUsersFromDb() {
        ArrayList<String> usersList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url,username,password);){
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sqlSelectAllUsers);
            while (resultSet.next()){
                usersList.add(resultSet.getInt(1)+" | "+resultSet.getString(2));
            }


        } catch ( SQLException e) {
            System.out.println(e);
        }
        return usersList;

    }
}
