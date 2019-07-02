package zarzyka.jagoda.shelter.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class User implements ResponseItem, Serializable {

    private Address address = new Address();
    private String login = "";
    private String name = "";
    private String password = "";
    @SerializedName("shelterUuid")
    private String shelterId = "";
    private String surname = "";

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getShelterId() {
        return shelterId;
    }

    public void setShelterId(final String shelterId) {
        this.shelterId = shelterId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name;
    }

}
