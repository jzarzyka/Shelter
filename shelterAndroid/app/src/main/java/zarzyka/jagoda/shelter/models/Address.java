package zarzyka.jagoda.shelter.models;

import java.io.Serializable;

public class Address implements Serializable {
    private String city = "";
    private String country = "";
    private String houseNumber = "";
    private String id = "";
    private String street = "";
    private String zipCode = "";

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(final String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    public String toString() {
        return street + " " + houseNumber + "\n" + zipCode + " " + city + ", " + country;
    }
}
