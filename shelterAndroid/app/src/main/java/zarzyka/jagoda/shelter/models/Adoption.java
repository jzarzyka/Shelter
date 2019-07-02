package zarzyka.jagoda.shelter.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Adoption implements Serializable {

    @SerializedName("animalUuid")
    private String animalId = "";
    private String date = "";
    private String description = "";
    private Receiver receiver = new Receiver();

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(final String animalId) {
        this.animalId = animalId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(final Receiver receiver) {
        this.receiver = receiver;
    }
}
