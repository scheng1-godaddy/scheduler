package shawn_cheng.access;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shawn_cheng.model.User;
import java.sql.*;
import shawn_cheng.MainApp;

/**
 * User Access Object
 */
public class UserAccess {

    // Get database connection
    Connection conn = MainApp.getDBConnection();

    /**
     * Verify login information
     * @param userName
     * @param passWord
     * @return
     */
    public User login (String userName, String passWord) {
        String selectUserQuery = "SELECT * FROM user WHERE userName = ? AND password = ?";
        User fetchedUser = new User();
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

    /**
     * Gets all users
     * @return
     */
    public ObservableList<User> getAllUsers() {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        String getCustomerQuery = "SELECT * FROM user WHERE active = 1";
        try {
            PreparedStatement stmt = conn.prepareStatement(getCustomerQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User fetchedUser = new User();
                fetchedUser.setUserName(rs.getString("userName"));
                fetchedUser.setPassWord(rs.getString("password"));
                fetchedUser.setUserID(rs.getInt("userId"));
                fetchedUser.setActive(rs.getInt("active"));
                usersList.add(fetchedUser);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return usersList;
    }

}
