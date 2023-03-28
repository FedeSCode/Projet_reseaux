package app.Server;

import app.ConstantsForApp.Constants;

import java.sql.*;
import java.util.ArrayList;

public class UserDb {
    Constants constants;
    User user;

    String sqlSelectAllUsers = "select * from users";
    String sqlInsertUser = "INSERT INTO USERS (username,password) VALUES(?,?)";

    String url= "jdbc:mysql://localhost:3306/project";
    String username= "root";
    String password= "toor";

    public int createUser(User user){

        int result = 0;
        /*try (Connection connection = DriverManager.getConnection(constants.getUrl(),constants.getUsername(), constants.getPassword());
        PreparedStatement prepareStatement = connection.prepareStatement(sqlInsertUser)){*/
        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement prepareStatement = connection.prepareStatement(sqlInsertUser)){

            prepareStatement.setString(2,user.getUsername());
//            prepareStatement.setString(3,user.getPassword());
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
                usersList.add(resultSet.getInt(1)+" | "+resultSet.getString(2)+" | "+resultSet.getString(3));
            }


        } catch ( SQLException e) {
            System.out.println(e);
        }
        return usersList;

    }
}
