package shawn_cheng.model;

/**
 * This object represents the user of the scheduling application
 *
 * @author shawncheng
 */
public class User {

    // Unique ID
    private int userID;
    // User's username
    private String userName;
    // User's password
    private String passWord;
    // 0 for inactive and 1 for active
    private int active;

    /**
     * Sets userID
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Sets user name
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets Password
     * @param passWord
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * Sets if user is active
     * @param active
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * Gets UserName
     * @return user name
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Gets Password
     * @return password
     */
    public String getPassWord() {
        return this.passWord;
    }

    /**
     * Returns active value
     * @return active value
     */
    public int getActive() {
        return this.active;
    }

    /**
     * Validates the user
     * @return
     */
    public boolean validateUser () {
        System.out.println("User Name is " + this.userName);
        try {
            assert !this.userName.equals("");
            assert !this.passWord.equals("");
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public String toString() {
        return getUserName();
    }

}
