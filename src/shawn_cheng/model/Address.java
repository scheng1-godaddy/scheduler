package shawn_cheng.model;

/**
 * Object to house Address information
 *
 * @author Shawn Cheng
 */
public class Address {

    // Instance variables
    private int addressID;
    private String address;
    private String address2;
    private City city;
    private String postalCode;
    private String phone;

    /**
     * Sets the street address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets street address 2
     * @param address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Sets address ID
     * @param addressID
     */
    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    /**
     * Sets city object
     * @param city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Sets the phone number
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets the postal code
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the city object
     * @return
     */
    public City getCity() {
        return city;
    }

    /**
     * Gets the address ID
     * @return
     */
    public int getAddressID() {
        return addressID;
    }

    /**
     * Gets the address
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets address line 2
     * @return
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Gets the phone number
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the postal code
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Returns the full address in string format
     * @return
     */
    @Override
    public String toString() {
        return getAddress() + " " +
                getAddress2() + " " +
                getPostalCode()  + " " +
                getCity().getCity() + " " +
                getCity().getCountry().getCountry();
    }
}
