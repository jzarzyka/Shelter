package zarzyka.jagoda.shelter.worker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import zarzyka.jagoda.shelter.R;
import zarzyka.jagoda.shelter.lists.CustomListAdapter;
import zarzyka.jagoda.shelter.models.Animal;
import zarzyka.jagoda.shelter.models.LoggedInUser;
import zarzyka.jagoda.shelter.models.ResponseItem;
import zarzyka.jagoda.shelter.models.Shelter;
import zarzyka.jagoda.shelter.models.User;
import zarzyka.jagoda.shelter.requests.animal.GetAllAnimals;
import zarzyka.jagoda.shelter.requests.shelter.GetOneShelter;
import zarzyka.jagoda.shelter.requests.user.GetOneUser;

public class WorkerActivity extends AppCompatActivity {

    private CustomListAdapter adapter;
    private ArrayList<ResponseItem> animals = new ArrayList<>();
    private Gson gson = new Gson();
    private Shelter shelter = null;
    private TextView shelterName, shelterVacancy, shelterAddress;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        shelterName = findViewById(R.id.name);
        shelterVacancy = findViewById(R.id.vacancy);
        shelterAddress = findViewById(R.id.address);
        final ListView listView = findViewById(R.id.list);
        final FloatingActionButton add = findViewById(R.id.add);

        String userLogin = LoggedInUser.getUserLogin(this);
        getDetails(userLogin);

        adapter = new CustomListAdapter(Objects.requireNonNull(getApplicationContext()), animals);
        listView.setAdapter(adapter);

        getAnimals();

        add.setOnClickListener(view -> {
            Intent editAnimal = new Intent(getApplicationContext(), EditAnimal.class);
            startActivity(editAnimal);
        });
    }

    private void getDetails(String userLogin) {
        Response.Listener<JSONObject> responseListener = response -> {
            user = gson.fromJson(response.toString(), User.class);
            getShelter(user.getShelterId());
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getApplicationContext(),
                getApplicationContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        GetOneUser getShelterUsersRequest = new GetOneUser(LoggedInUser.getSessionId(this),
                userLogin,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(getShelterUsersRequest);
    }

    private void getAnimals() {
        animals.clear();

        Response.Listener<JSONArray> responseListener = response -> {
            Type listType = new TypeToken<ArrayList<Animal>>() {}.getType();
            ArrayList<ResponseItem> list = gson.fromJson(response.toString(), listType);
            animals.addAll(list);
            adapter.notifyDataSetChanged();
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getApplicationContext(),
                getApplicationContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        GetAllAnimals getAnimalsRequest = new GetAllAnimals(LoggedInUser.getSessionId(this),
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(getAnimalsRequest);
    }

    private void getShelter(String shelterId) {
        Response.Listener<JSONObject> responseListener = response -> {
            shelter = gson.fromJson(response.toString(), Shelter.class);

            shelterName.setText(getResources().getString(R.string.name_input, shelter.getName()));
            shelterVacancy.setText(getResources().getString(R.string.vacancy_input, shelter.getAnimalVacancy()));
            shelterAddress.setText(getResources().getString(R.string.address_input, shelter.getAddress().toString()));
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getApplicationContext(),
                getApplicationContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        GetOneShelter getShelterUsersRequest = new GetOneShelter(LoggedInUser.getSessionId(this),
                shelterId,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(getShelterUsersRequest);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
