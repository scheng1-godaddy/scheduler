package shawn_cheng.model;

public class Address {
    private int addressID;
    private String address;
    private String address2;
    private City city;
    private String postalCode;
    private String phone;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public City getCity() {
        return city;
    }

    public int getAddressID() {
        return addressID;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getPhone() {
        return phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return getAddress() + " " +
                getAddress2() + " " +
                getPostalCode()  + " " +
                getCity();
    }
}
