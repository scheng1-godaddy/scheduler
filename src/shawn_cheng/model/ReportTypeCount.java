package shawn_cheng.model;

/**
 * Object to house result of query that retrieves the count of appointment types
 */
public class ReportTypeCount {
    // Count of appointment type
    private int count;
    // Appointment type
    private String description;

    /**
     * Gets the count
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets the description/type
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description/type.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
