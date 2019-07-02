package zarzyka.jagoda.shelter.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Shelter implements ResponseItem, Serializable {
    private Address address = new Address();
    private Integer animalVacancy = 0;
    @SerializedName("uuid")
    private String id = "";
    private String name = "";

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public Integer getAnimalVacancy() {
        return animalVacancy;
    }

    public void setAnimalVacancy(final Integer animalVacancy) {
        this.animalVacancy = animalVacancy;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
