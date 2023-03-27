package app.Server;
import java.sql.*;

public class JDBC {
    public static void main(String[] args) {
        String url= "jdbc:mysql://localhost:3306/project";
        String username= "root";
        String password= "toor";

        String sqlAddMessage = "INSERT INTO Message values ";
        String sqlAddUser = "INSERT INTO User values ";
        String sqlSelectAllUsers = "select * from user";

        try (Connection connection = DriverManager.getConnection(url,username,password);){
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sqlSelectAllUsers);
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1)+" | "+resultSet.getString(2));
            }


        } catch ( SQLException e) {
            System.out.println(e);
        }
    }
}
