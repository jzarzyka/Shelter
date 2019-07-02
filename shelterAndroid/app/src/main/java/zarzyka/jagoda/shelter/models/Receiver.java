package zarzyka.jagoda.shelter.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Receiver implements Serializable {

    private Address address = new Address();
    private String email = "";
    private String name = "";
    @SerializedName("phoneNumber")
    private String phone = "";
    private String surname = "";

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }
}
