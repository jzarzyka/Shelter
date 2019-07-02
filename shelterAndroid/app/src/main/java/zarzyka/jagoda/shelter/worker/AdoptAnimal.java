package zarzyka.jagoda.shelter.worker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONObject;
import zarzyka.jagoda.shelter.R;
import zarzyka.jagoda.shelter.models.Address;
import zarzyka.jagoda.shelter.models.Adoption;
import zarzyka.jagoda.shelter.models.LoggedInUser;
import zarzyka.jagoda.shelter.models.Receiver;

public class AdoptAnimal extends AppCompatActivity {

    private String animalId = "";
    private EditText emailInput, descriptionInput, phoneInput, nameInput, surnameInput, streetInput, zipCodeInput,
            houseNumberInput,
            countryInput, cityInput;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_animal);

        phoneInput = findViewById(R.id.phoneInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        emailInput = findViewById(R.id.emailInput);
        nameInput = findViewById(R.id.nameInput);
        surnameInput = findViewById(R.id.surnameInput);
        streetInput = findViewById(R.id.streetInput);
        houseNumberInput = findViewById(R.id.houseNumberInput);
        zipCodeInput = findViewById(R.id.zipCodeInput);
        cityInput = findViewById(R.id.cityInput);
        countryInput = findViewById(R.id.countryInput);

        animalId = getIntent().getStringExtra("animalId");

        final Button adopt = findViewById(R.id.adopt);
        adopt.setOnClickListener(view -> saveData());
    }

    private void saveData() {
        Adoption adoption = new Adoption();
        Receiver receiver = new Receiver();
        Address address = new Address();

        adoption.setAnimalId(animalId);
        adoption.setDescription(descriptionInput.getText().toString());
        receiver.setName(nameInput.getText().toString());
        receiver.setSurname(surnameInput.getText().toString());
        receiver.setEmail(emailInput.getText().toString());
        receiver.setPhone(phoneInput.getText().toString());
        address.setStreet(streetInput.getText().toString());
        address.setHouseNumber(houseNumberInput.getText().toString());
        address.setZipCode(zipCodeInput.getText().toString());
        address.setCity(cityInput.getText().toString());
        address.setCountry(countryInput.getText().toString());
        receiver.setAddress(address);
        adoption.setReceiver(receiver);

        String body = gson.toJson(adoption);

        adopt(body);
    }

    private void adopt(String body) {
        Response.Listener<JSONObject> responseListener = response -> {
            Toast.makeText(
                    getApplicationContext(),
                    getApplicationContext().getString(R.string.success),
                    Toast.LENGTH_SHORT).show();

            Intent login = new Intent(getApplicationContext(), WorkerActivity.class);
            startActivity(login);
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getApplicationContext(),
                getApplicationContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        zarzyka.jagoda.shelter.requests.adoption.AdoptAnimal adoptAnimal
                = new zarzyka.jagoda.shelter.requests.adoption.AdoptAnimal(
                LoggedInUser.getSessionId(this),
                body,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(adoptAnimal);
    }
}
