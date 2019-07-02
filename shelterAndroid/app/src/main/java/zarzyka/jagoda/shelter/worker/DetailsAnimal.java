package zarzyka.jagoda.shelter.worker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONObject;
import zarzyka.jagoda.shelter.R;
import zarzyka.jagoda.shelter.models.Adoption;
import zarzyka.jagoda.shelter.models.Animal;
import zarzyka.jagoda.shelter.models.LoggedInUser;
import zarzyka.jagoda.shelter.models.Receiver;
import zarzyka.jagoda.shelter.requests.adoption.GetAdoption;
import zarzyka.jagoda.shelter.requests.animal.GetOneAnimal;

public class DetailsAnimal extends AppCompatActivity {

    private Animal animal = new Animal();
    private Button edit, adopt;
    private Gson gson = new Gson();
    private TextView name, age, breed, description, date, receiverName, receiverSurname, receiverPhone, receiverEmail,
            receiverAddress, adoptionDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_animal);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        breed = findViewById(R.id.breed);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        receiverName = findViewById(R.id.receiverName);
        receiverSurname = findViewById(R.id.receiverSurname);
        receiverPhone = findViewById(R.id.receiverPhone);
        receiverEmail = findViewById(R.id.receiverEmail);
        receiverAddress = findViewById(R.id.receiverAddress);
        adoptionDescription = findViewById(R.id.adoptionDescription);
        edit = findViewById(R.id.edit);
        adopt = findViewById(R.id.adopt);

        edit.setOnClickListener(view -> {
            Intent details = new Intent(getApplicationContext(), EditAnimal.class);
            details.putExtra("animal", animal);
            startActivity(details);
        });

        adopt.setOnClickListener(view -> {
            Intent details = new Intent(getApplicationContext(), AdoptAnimal.class);
            details.putExtra("animalId", animal.getId());
            startActivity(details);
        });

        getAnimal(getIntent().getStringExtra("animalId"));
    }

    private void getAnimal(String animalId) {
        Response.Listener<JSONObject> responseListener = response -> {
            animal = gson.fromJson(response.toString(), Animal.class);
            setDetails();
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getApplicationContext(),
                getApplicationContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        GetOneAnimal getShelterUsersRequest = new GetOneAnimal(LoggedInUser.getSessionId(this),
                animalId,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(getShelterUsersRequest);
    }

    private void setDetails() {
        name.setText(getResources().getString(R.string.name_input, animal.getName()));
        age.setText(getResources().getString(R.string.age_input, animal.getAge()));
        breed.setText(getResources().getString(R.string.breed_input, animal.getBreed()));
        description.setText(getResources().getString(R.string.description_input, animal.getDescription()));

        if (animal.getAdoptionId() != null) {
            edit.setVisibility(View.INVISIBLE);
            adopt.setVisibility(View.INVISIBLE);

            getAdoption();
        }
    }

    private void getAdoption() {
        Response.Listener<JSONObject> responseListener = response -> {
            Adoption adoption = gson.fromJson(response.toString(), Adoption.class);
            Receiver receiver = adoption.getReceiver();

            date.setText(getResources().getString(R.string.date_input, adoption.getDate()));
            receiverName.setText(getResources().getString(R.string.name_input, adoption.getReceiver().getName()));
            receiverSurname.setText(getResources().getString(R.string.surname_input, receiver.getSurname()));
            receiverPhone.setText(getResources().getString(R.string.phone_input, receiver.getPhone()));
            receiverEmail.setText(getResources().getString(R.string.email_input, receiver.getEmail()));
            receiverAddress
                    .setText(getResources().getString(R.string.address_input, receiver.getAddress().toString()));
            adoptionDescription
                    .setText(getResources().getString(R.string.description_input, adoption.getDescription()));

            LinearLayout adoptionLayout = findViewById(R.id.adoption);
            adoptionLayout.setVisibility(View.VISIBLE);
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getApplicationContext(),
                getApplicationContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        GetAdoption getAdoptionRequest = new GetAdoption(LoggedInUser.getSessionId(this),
                animal.getAdoptionId(),
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(getAdoptionRequest);
    }
}
