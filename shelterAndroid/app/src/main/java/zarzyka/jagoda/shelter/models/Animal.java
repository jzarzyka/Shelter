package zarzyka.jagoda.shelter.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Animal implements ResponseItem, Serializable {
    @SerializedName("adoptionUuid")
    private String adoptionId = "";
    private Integer age = 0;
    private String breed = "";
    private String description = "";
    @SerializedName("uuid")
    private String id = "";
    private String name = "";
    @SerializedName("supervisorUuid")
    private String supervisorId = "";

    public String getAdoptionId() {
        return adoptionId;
    }

    public void setAdoptionId(final String adoptionId) {
        this.adoptionId = adoptionId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(final String breed) {
        this.breed = breed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
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

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(final String supervisorId) {
        this.supervisorId = supervisorId;
    }

    @Override
    public String toString() {
        return name;
    }

}
