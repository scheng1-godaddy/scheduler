package shawn_cheng.access;
import shawn_cheng.model.User;
import java.sql.*;
import shawn_cheng.MainApp;

public class UserAccess {

    Connection conn = MainApp.getDBConnection();
    { System.out.println("Connection is " + conn); }

    public User login (String userName, String passWord) {
        String selectUserQuery = "SELECT * FROM user WHERE userName = ? AND password = ?";
        User fetchedUser = new User();
        /*
        String driver = "com.mysql.jdbc.Driver";
        String db = "U047Jn";
        String url = "jdbc:mysql://52.206.157.109/" + db;
        String user = "U047Jn";
        String pass = "53688170277";
        */
        try {
            System.out.println("Preparing SQL Statement for login");
            PreparedStatement preparedStatement = conn.prepareStatement(selectUserQuery);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passWord);
            System.out.println("Running query: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fetchedUser.setUserName(resultSet.getString("userName"));
                fetchedUser.setPassWord(resultSet.getString("password"));
                fetchedUser.setActive(resultSet.getInt("active"));
                fetchedUser.setUserID(resultSet.getInt("userId"));
                System.out.println("Found the following user " + fetchedUser.getUserName());
            } else {
                System.out.println("Didn't find anything, returning null");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
        return fetchedUser;
    }
}
