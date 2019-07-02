package zarzyka.jagoda.shelter.admin.add;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONObject;
import zarzyka.jagoda.shelter.R;
import zarzyka.jagoda.shelter.admin.AdminActivity;
import zarzyka.jagoda.shelter.models.Address;
import zarzyka.jagoda.shelter.models.LoggedInUser;
import zarzyka.jagoda.shelter.models.Shelter;
import zarzyka.jagoda.shelter.requests.shelter.SaveShelter;
import zarzyka.jagoda.shelter.requests.shelter.UpdateShelter;

public class ShelterEdit extends Fragment {

    private Gson gson = new Gson();
    private EditText nameInput, vacancyInput, streetInput, zipCodeInput, houseNumberInput, countryInput,
            cityInput;
    private Button save;
    private Shelter shelter;

    public static ShelterEdit newInstance(Shelter shelter) {
        ShelterEdit fragment = new ShelterEdit();
        Bundle args = new Bundle();
        args.putSerializable("shelter", shelter);
        fragment.setArguments(args);

        return fragment;
    }

    public ShelterEdit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shelter = getArguments() != null ? (Shelter) getArguments().getSerializable("shelter") : new Shelter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_shelter, container, false);

        nameInput = root.findViewById(R.id.nameInput);
        vacancyInput = root.findViewById(R.id.vacancyInput);
        streetInput = root.findViewById(R.id.streetInput);
        houseNumberInput = root.findViewById(R.id.houseNumberInput);
        zipCodeInput = root.findViewById(R.id.zipCodeInput);
        cityInput = root.findViewById(R.id.cityInput);
        countryInput = root.findViewById(R.id.countryInput);
        save = root.findViewById(R.id.save);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        save.setOnClickListener(view -> saveData());

        if (shelter != null) {
            injectData();
        } else {
            shelter = new Shelter();
        }
    }

    private void saveData() {
        shelter.setName(nameInput.getText().toString());
        shelter.setAnimalVacancy(
                TextUtils.isEmpty(vacancyInput.getText()) ? 0 : Integer.parseInt(vacancyInput.getText().toString()));
        Address address = new Address();
        address.setStreet(streetInput.getText().toString());
        address.setHouseNumber(houseNumberInput.getText().toString());
        address.setZipCode(zipCodeInput.getText().toString());
        address.setCity(cityInput.getText().toString());
        address.setCountry(countryInput.getText().toString());
        shelter.setAddress(address);

        String body = gson.toJson(shelter);

        if (!shelter.getId().isEmpty()) {
            update(body);
        } else {
            save(body);
        }
    }

    private void injectData() {
        nameInput.setText(shelter.getName());
        vacancyInput.setText(shelter.getAnimalVacancy().toString());

        Address address = shelter.getAddress();
        streetInput.setText(address.getStreet());
        houseNumberInput.setText(address.getHouseNumber());
        zipCodeInput.setText(address.getZipCode());
        cityInput.setText(address.getCity());
        countryInput.setText(address.getCountry());
    }

    private void update(String body) {
        Response.Listener<JSONObject> responseListener = response -> {
            Toast.makeText(
                    getContext(),
                    getContext().getString(R.string.success),
                    Toast.LENGTH_SHORT).show();

            Intent login = new Intent(getContext(), AdminActivity.class);
            startActivity(login);
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getContext(),
                getContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        UpdateShelter updateShelter = new UpdateShelter(LoggedInUser.getSessionId(getActivity()),
                body,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(updateShelter);
    }

    private void save(String body) {
        Response.Listener<JSONObject> responseListener = response -> {
            Toast.makeText(
                    getContext(),
                    getContext().getString(R.string.success),
                    Toast.LENGTH_SHORT).show();

            Intent login = new Intent(getContext(), AdminActivity.class);
            startActivity(login);
        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getContext(),
                getContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        SaveShelter saveShelter = new SaveShelter(LoggedInUser.getSessionId(getActivity()),
                body,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(saveShelter);
    }
}
