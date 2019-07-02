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
import zarzyka.jagoda.shelter.models.Animal;
import zarzyka.jagoda.shelter.models.LoggedInUser;
import zarzyka.jagoda.shelter.requests.animal.SaveAnimal;
import zarzyka.jagoda.shelter.requests.animal.UpdateAnimal;

public class EditAnimal extends AppCompatActivity {

    private Animal animal = null;
    private Gson gson = new Gson();
    private EditText nameInput, ageInput, breedInput, descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_animal);

        nameInput = findViewById(R.id.nameInput);
        ageInput = findViewById(R.id.ageInput);
        breedInput = findViewById(R.id.breedInput);
        descriptionInput = findViewById(R.id.descriptionInput);

        Button save = findViewById(R.id.save);
        save.setOnClickListener(view -> saveData());

        if (getIntent().hasExtra("animal")) {
            animal = (Animal) getIntent().getSerializableExtra("animal");
            injectData();
        } else {
            animal = new Animal();
        }
    }

    private void saveData() {
        animal.setName(nameInput.getText().toString());
        animal.setAge(Integer.parseInt(ageInput.getText().toString()));
        animal.setBreed(breedInput.getText().toString());
        animal.setDescription(descriptionInput.getText().toString());

        String body = gson.toJson(animal);

        if (!animal.getId().isEmpty()) {
            update(body);
        } else {
            save(body);
        }
    }

    private void injectData() {
        nameInput.setText(animal.getName());
        ageInput.setText(animal.getAge().toString());
        breedInput.setText(animal.getBreed());
        descriptionInput.setText(animal.getDescription());
    }

    private void update(String body) {
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

        UpdateAnimal updateAnimal = new UpdateAnimal(LoggedInUser.getSessionId(this),
                body,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(updateAnimal);
    }

    private void save(String body) {
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

        SaveAnimal saveAnimal = new SaveAnimal(LoggedInUser.getSessionId(this),
                body,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(saveAnimal);
    }
}
