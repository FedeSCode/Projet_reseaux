package app.Server;

import app.ConstantsForApp.Constants;

import java.sql.*;

public class UserDb {
    Constants constants;
    String sqlSelectAllUsers = "select * from user";
    String sqlInsertUser = "INSERT INTO USERS "+"(id,username) VALUES"+"(?,?);";
    User user;

    public int createUser(User user){

        int result = 0;
        try (Connection connection = DriverManager.getConnection(constants.getUrl(),constants.getUsername(), constants.getPassword());
        PreparedStatement prepareStatement = connection.prepareStatement(sqlInsertUser)){

                prepareStatement.setInt(1, user.getId());
                prepareStatement.setString(2,user.getUsername());
                result = prepareStatement.executeUpdate();

        } catch ( SQLException e) {
            System.out.println(e);
        }
        return result;
    }


}
