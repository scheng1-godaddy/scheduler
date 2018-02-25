package shawn_cheng.model;

public class City {
    private int cityID;
    private String city;
    private Country country;

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public int getCityID() {
        return cityID;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return getCity();
    }
}
