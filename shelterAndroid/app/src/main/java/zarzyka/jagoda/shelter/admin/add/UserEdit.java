package zarzyka.jagoda.shelter.admin.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import zarzyka.jagoda.shelter.R;
import zarzyka.jagoda.shelter.admin.AdminActivity;
import zarzyka.jagoda.shelter.models.Address;
import zarzyka.jagoda.shelter.models.LoggedInUser;
import zarzyka.jagoda.shelter.models.ResponseItem;
import zarzyka.jagoda.shelter.models.Shelter;
import zarzyka.jagoda.shelter.models.User;
import zarzyka.jagoda.shelter.requests.shelter.GetAllShelters;
import zarzyka.jagoda.shelter.requests.user.RegisterUser;
import zarzyka.jagoda.shelter.requests.user.UpdateUser;

public class UserEdit extends Fragment {

    private ArrayList<String> adapterList = new ArrayList<>();
    private Gson gson = new Gson();
    private EditText loginInput, passwordInput, nameInput, surnameInput, streetInput, zipCodeInput, houseNumberInput,
            countryInput,
            cityInput;
    private Button save;
    private Spinner shelterSpinner;
    private HashMap<String, String> shelters = new HashMap<>();
    private User user;

    public static UserEdit newInstance(User user) {
        UserEdit fragment = new UserEdit();
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        fragment.setArguments(args);

        return fragment;
    }

    public UserEdit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getArguments() != null ? (User) getArguments().getSerializable("user") : new User();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_user, container, false);

        loginInput = root.findViewById(R.id.loginInput);
        passwordInput = root.findViewById(R.id.passwordInput);
        nameInput = root.findViewById(R.id.nameInput);
        surnameInput = root.findViewById(R.id.surnameInput);
        shelterSpinner = root.findViewById(R.id.shelterInput);
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

        getShelters();

        save.setOnClickListener(view -> saveData());

        if (user != null) {
            injectData();
        } else {
            user = new User();
        }
    }

    private void getShelters() {
        shelters.clear();

        Response.Listener<JSONArray> responseListener = response -> {
            Type listType = new TypeToken<ArrayList<Shelter>>() {}.getType();
            ArrayList<ResponseItem> list = gson.fromJson(response.toString(), listType);

            for (int i = 0; i < list.size(); ++i) {
                Shelter shelter = (Shelter) list.get(i);
                shelters.put(shelter.getName(), shelter.getId());
            }
            adapterList = new ArrayList<>(shelters.keySet());

            setSpinner();

            if (!user.getLogin().isEmpty()) {
                shelterSpinner.setSelection(adapterList.indexOf(getKeyByValue(shelters, user.getShelterId())));
            }

        };

        Response.ErrorListener responseErrorListener = error -> Toast.makeText(
                getContext(),
                getContext().getString(R.string.error_happened),
                Toast.LENGTH_SHORT
        ).show();

        GetAllShelters getSheltersRequest = new GetAllShelters(LoggedInUser.getSessionId(getActivity()),
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getSheltersRequest);
    }

    private void saveData() {
        boolean isNew = user.getLogin().isEmpty();

        user.setLogin(loginInput.getText().toString());
        user.setPassword(passwordInput.getText().toString());
        user.setName(nameInput.getText().toString());
        user.setSurname(surnameInput.getText().toString());
        user.setShelterId(shelters.get(shelterSpinner.getSelectedItem().toString()));
        Address address = new Address();
        address.setStreet(streetInput.getText().toString());
        address.setHouseNumber(houseNumberInput.getText().toString());
        address.setZipCode(zipCodeInput.getText().toString());
        address.setCity(cityInput.getText().toString());
        address.setCountry(countryInput.getText().toString());
        user.setAddress(address);

        String body = gson.toJson(user);

        if (!isNew) {
            update(body);
        } else {
            register(body);
        }
    }

    private void injectData() {
        loginInput.setText(user.getLogin());
        nameInput.setText(user.getName());
        surnameInput.setText(user.getSurname());

        Address address = user.getAddress();
        streetInput.setText(address.getStreet());
        houseNumberInput.setText(address.getHouseNumber());
        zipCodeInput.setText(address.getZipCode());
        cityInput.setText(address.getCity());
        countryInput.setText(address.getCountry());
    }

    private void setSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                adapterList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shelterSpinner.setAdapter(adapter);
    }

    private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
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

        UpdateUser updateUser = new UpdateUser(LoggedInUser.getSessionId(getActivity()),
                body,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(updateUser);
    }

    private void register(String body) {
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

        RegisterUser registerUser = new RegisterUser(
                body,
                responseListener,
                responseErrorListener
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(registerUser);
    }
}
