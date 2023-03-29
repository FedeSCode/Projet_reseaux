package app.ServerMaster;



import java.sql.*;
import java.util.ArrayList;

public class UserDb {



    String sqlSelectAllUsers = "select * from users;";
    String sqlInsertUser = "INSERT INTO USERS (username,password) VALUES(?,?);";
    String sqlSelectIdOfLastUser = "SELECT max(id) from users;";
    String sqlSearchUser="SELECT username from users where username = ?;";
    String sqlSearchPasswordByUsername="SELECT password from users where username = ?;";

    String url= "jdbc:mysql://localhost:3306/project";
    String username= "root";
    String password= "toor";

    public int createUser(User user){

        int result = 0;

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement prepareStatement = connection.prepareStatement(sqlInsertUser)){
            prepareStatement.setString(1,user.getUsername());
            prepareStatement.setString(2,user.getPassword());
            result = prepareStatement.executeUpdate();

        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public ArrayList<String> getAllUsersFromDb() {
        ArrayList<String> usersList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url,username,password)){
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sqlSelectAllUsers);
            while (resultSet.next()){
                usersList.add(resultSet.getInt(1)+"|"+resultSet.getString(2)+"|"+resultSet.getString(3));
            }


        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return usersList;

    }

    public int getLastUserId(){
        int idOfLastUser = 0;
        try (Connection connection = DriverManager.getConnection(url,username,password)){

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectIdOfLastUser);
            while(resultSet.next()){
                idOfLastUser = resultSet.getInt(1);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return idOfLastUser;
    }

    public String getUsername(String user){
        String result = null;
        try (Connection connection = DriverManager.getConnection(url,username,password)){
            PreparedStatement statement = connection.prepareStatement(sqlSearchUser);
            statement.setString(1,user);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                result = resultSet.getString(1);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public String getPasswordByUsername(String user) {
        String result = null;
        try (Connection connection = DriverManager.getConnection(url,username,password)){
            PreparedStatement statement = connection.prepareStatement(sqlSearchPasswordByUsername);
            statement.setString(1,user);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                result = resultSet.getString(1);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
