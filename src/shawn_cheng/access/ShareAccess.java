package shawn_cheng.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Holds code that will be used by other Access Objects
 * @author Shawn Cheng
 */
public class ShareAccess {

    /**
     * Runs query and retrieves a single result from first column.
     * @param id
     * @param query
     * @param statement
     * @return
     */
    static int getId(int id, String query, Statement statement) {
        try {
            System.out.println("Preparing SQL Statement to retrieve all ID");
            Statement stmt = statement;
            ResultSet result = stmt.executeQuery(query);

            if (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
        return id;
    }
}
